# Совмещаем несовместимое
В текущей небольшой заметки попробуем исследовать тему комментариев в коде и "самодокументирующегося" кода. 
Не смотря на то что идея "самодокументирующегося" кода без единого дополнительного комментария всё также остается одной из концепций к которой многие разработчики продолжают 
стремиться, на практике сложно уложить верхнеуровневый внутренний контекст в сам код. Ниже, в трёх примерах постарался привести пример
подобного комментария (раскрывающий информацию глобального характера). В каждом из примеров, комментарий идет первой строчкой и описывает класс под ним.
В данном случае выбран формат комментария через "#", однако это не является знаком того что этот формат лучше чем например doc-strings для донесения информацию глобального характера.  

## Пример №1
```python
# Хранит в себе перечень всех актуальных к сбору и расчету метрик
class MetricCollectorRegistry:
    def __init__(self, collectors: Iterable[MetricCollector]) -> None:
        self._collectors: dict[str, MetricCollector] = {}
        for collector in collectors:
            if collector.metric_slug in self._collectors:
                raise DuplicateMetricCollectorError(collector.metric_slug)
            self._collectors[collector.metric_slug] = collector

    def get(self, metric_slug: Metric) -> MetricCollector:
        collector = self._collectors.get(metric_slug)
        if collector is None:
            raise MetricCollectorNotFoundError(metric_slug)
        return collector

    def get_optional(self, metric_slug: str) -> MetricCollector | None:
        return self._collectors.get(metric_slug)

    def slugs(self) -> list[str]:
        return sorted(self._collectors.keys())
```

## Пример №2
```python
# Триггер end2end процесса по сбору множества метрик
@dataclass(slots=True)
class CollectMetricHandler:
    registry: MetricCollectorRegistry
    uow_factory: Callable[[], SqlAlchemyUnitOfWork]
    timezone: str = settings.app.timezone

    async def execute(
        self,
        entity_type: Scope,
        entity_id: str,
        metric_slug: Metric,
        measure_date: date | None = None,
    ) -> CollectMetricResponse:
        target_date = measure_date or self._yesterday_in_tz()
        collector = self.registry.get(metric_slug)
        collected = await collector.collect(
            entity_type=entity_type,
            entity_id=entity_id,
            measure_date=target_date,
        )

        async with self.uow_factory() as uow:
            await self._persist(
                uow=uow,
                entity_type=entity_type,
                entity_id=entity_id,
                metric_slug=collector.metric_slug,
                measure_date=target_date,
                source=collector.source_code,
                collected=collected,
            )
            await uow.commit()

        return CollectMetricResponse(
            entity_type=entity_type,
            entity_id=entity_id,
            measure_date=target_date,
            metric_slug=collector.metric_slug,
            source=collector.source_code,
            storage_type=collected.storage_type,
            items_count=self._items_count(collected),
        )

    async def _persist(
        self,
        uow: SqlAlchemyUnitOfWork,
        entity_type: str,
        entity_id: str,
        metric_slug: str,
        measure_date: date,
        source: str,
        collected: CollectedMetricValue,
    ) -> None:
        if collected.storage_type == "event":
            if not isinstance(collected.value, list):
                raise UnsupportedStorageTypeError(collected.storage_type)

            await uow.event_metrics.upsert_daily_event_metric(
                entity_type=entity_type,
                entity_id=entity_id,
                metric_slug=metric_slug,
                measure_date=measure_date,
                source=source,
                value=collected.value,
            )
            return

        if collected.storage_type == "numeric":
            if isinstance(collected.value, bool) or not isinstance(
                collected.value,
                (int, float),
            ):
                raise UnsupportedStorageTypeError(collected.storage_type)

            await uow.numeric_metrics.upsert_daily_numeric_metric(
                entity_type=entity_type,
                entity_id=entity_id,
                metric_slug=metric_slug,
                measure_date=measure_date,
                source=source,
                value=float(collected.value),
            )
            return

        if collected.storage_type == "bool":
            if not isinstance(collected.value, bool):
                raise UnsupportedStorageTypeError(collected.storage_type)

            await uow.bool_metrics.upsert_daily_bool_metric(
                entity_type=entity_type,
                entity_id=entity_id,
                metric_slug=metric_slug,
                measure_date=measure_date,
                source=source,
                value=collected.value,
            )
            return

        if collected.storage_type == "str":
            if not isinstance(collected.value, str):
                raise UnsupportedStorageTypeError(collected.storage_type)

            await uow.str_metrics.upsert_daily_str_metric(
                entity_type=entity_type,
                entity_id=entity_id,
                metric_slug=metric_slug,
                measure_date=measure_date,
                source=source,
                value=collected.value,
            )
            return

        raise UnsupportedStorageTypeError(collected.storage_type)

    def _yesterday_in_tz(self) -> date:
        now = datetime.now(ZoneInfo(self.timezone))
        return (now - timedelta(days=1)).date()

    @staticmethod
    def _items_count(collected: CollectedMetricValue) -> int:
        if collected.storage_type == "event" and isinstance(collected.value, list):
            return len(collected.value)

        return 1
```

## Пример №3
```python
# Единый узел преобразования сущности для агрегации в один из шаблонов (MetricValueSelector) для дальнейшей обработки
class TargetResolver:
    _EXACT_TARGET_TO_SCOPE: ClassVar[dict[AggregationScope, Scope]] = {
        AggregationScope.PROJECT: Scope.PROJECT,
        AggregationScope.TEAM: Scope.TEAM,
        AggregationScope.SYSTEM: Scope.SYSTEM,
    }

    _SUPPORTED_TARGETS_BY_SCOPE: ClassVar[
        dict[Scope, set[AggregationScope]]
    ] = {
        Scope.PROJECT: {
            AggregationScope.PROJECT,
            AggregationScope.TEAM,
            AggregationScope.SYSTEM,
            AggregationScope.DOMAIN,
            AggregationScope.BANK,
        },
        Scope.TEAM: {
            AggregationScope.TEAM,
            AggregationScope.DOMAIN,
            AggregationScope.BANK,
        },
        Scope.SYSTEM: {
            AggregationScope.SYSTEM,
            AggregationScope.BANK,
        },
    }

    def resolve(
        self,
        aggregation_scope: AggregationScope,
        entity_id: str | int | None,
        collector_scope: Scope,
    ) -> MetricValueSelector:
        supported_targets = self._SUPPORTED_TARGETS_BY_SCOPE.get(
            collector_scope
        )
        if supported_targets is None:
            raise InvalidMetricTargetError(
                f"Unsupported metric scope '{collector_scope}'"
            )

        if aggregation_scope not in supported_targets:
            raise InvalidMetricTargetError(
                f"aggregation_scope '{aggregation_scope}' "
                f"is not compatible with metric scope '{collector_scope}'",
            )

        if self._is_exact_target(
            aggregation_scope=aggregation_scope,
            collector_scope=collector_scope,
        ):
            return self._resolve_exact_target(
                aggregation_scope=aggregation_scope,
                entity_id=entity_id,
                collector_scope=collector_scope,
            )

        if (
            collector_scope == Scope.PROJECT
            and aggregation_scope == AggregationScope.TEAM
        ):
            return self._resolve_team_aggregate_target(entity_id=entity_id)

        if (
            collector_scope == Scope.PROJECT
            and aggregation_scope == AggregationScope.SYSTEM
        ):
            return self._resolve_system_aggregate_target(entity_id=entity_id)

        if aggregation_scope == AggregationScope.DOMAIN:
            return self._resolve_domain_target(
                entity_id=entity_id,
                collector_scope=collector_scope,
            )

        if aggregation_scope == AggregationScope.BANK:
            return self._resolve_bank_target(
                entity_id=entity_id,
                collector_scope=collector_scope,
            )

        raise InvalidMetricTargetError(
            f"Unsupported aggregation_scope '{aggregation_scope}'"
        )

    def _is_exact_target(
        self,
        aggregation_scope: AggregationScope,
        collector_scope: Scope,
    ) -> bool:
        return (
            self._EXACT_TARGET_TO_SCOPE.get(aggregation_scope)
            == collector_scope
        )

    def _resolve_exact_target(
        self,
        aggregation_scope: AggregationScope,
        entity_id: str | int | None,
        collector_scope: Scope,
    ) -> MetricValueSelector:
        expected_scope = self._EXACT_TARGET_TO_SCOPE[aggregation_scope]

        if expected_scope != collector_scope:
            raise InvalidMetricTargetError(
                f"aggregation_scope '{aggregation_scope}' "
                f"is not compatible with metric scope '{collector_scope}'",
            )

        if entity_id is None:
            raise InvalidMetricTargetError(
                f"entity_id is required for aggregation scope "
                f"'{aggregation_scope}'"
            )

        normalized_entity_id = str(entity_id)

        if aggregation_scope == AggregationScope.TEAM:
            normalized_entity_id = normalized_entity_id.lower()

        return MetricValueSelector(
            metric_scope=collector_scope,
            aggregation_scope=aggregation_scope,
            filter=ExactEntityFilter(
                entity_id=normalized_entity_id,
            ),
        )
```

## Заключение
При размышлении на тему формата комментариев (или докстрингов), которые могли бы не просто описать то что происходит внутри функции/класса и каким именно образом, в первую очередь задумываешься о том, в каких обстоятельствах разработчик будет данные комментарии читать.
Если разработчик начинает изучение проекта с нуля и уже прочел README, в котором однако не было информации ни про архитектуру приложения/проекта, ни про flow данных которые через приложение проходят, видится что комментарии следует оставлять в каждом блоке кода которые можно посчитать управляющей конструкцией в том или ином виде. 
Ведь от описания блоков кода которые отличаются от похожих лишь тем, что содержат иную логику обработки другого типа значений - смысла не много. 
С другой же стороны, если из README проекта разработчики могут достаточно понять контекст архитектуру приложения и flow-данных, возможно единственный необходимый тип комментарием который есть смысл использовать - комментарии которые поясняют управляющие конструкии вне известных шаблонов/паттернов и комментарии которые явно объясняют причины решений, которые кажутся на первый взгляд не оптимальными и странными.
Хотя, мне нравится идея с "дневником" проекта, в котором подобные решения/обсуждения явно упоминаются, для частичного покрытия второго случая. 
Подведя итоги, могу подтвердить ещё один интересный момент с которым успел ознакомиться в рамках текущей темы комментариев в коде - комментариям в действительно хороший показатель того насколько предыдущий разработчик озаботился о комфорте разработчиков из будущего, через то, что отметил все те места в которых новоприбывшему и не только будет скорее всего сложно разобраться/понять заложенную высокоуровневую логику дизайна.
