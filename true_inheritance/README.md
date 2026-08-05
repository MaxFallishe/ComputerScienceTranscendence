# Истинное наследование
Истинное наследование по сути означает что методы классов по иерархии снизу вверх не модифицируются для добавления нового поведения.
Не "истинным" же наследование будет если оно не включает вызов методов суперкласса в подклассах. Яркий пример паттерна предлагающий хорошее использование
истинного наследование - Visitor (архитектурный пример когда объектно-ориентированный подход является самым простым и наглядным). 
Ниже рассмотрим пример где применяется не "истинное" наследование с последующей его модификаций с помощью паттерна Visitor в истинное наследование.

## Пример

### Существующая версия кода
```python
class MetricCollector(ABC):
    metric_slug: Metric
    source_code: SourceCode
    storage_type: StorageType
    default_agg_method: StrAgg | BoolAgg | NumericAgg | EventAgg
    scope: Scope
    large_aggregation_category: LargeAggregationCategory | None = None

    async def collect(
        self,
        entity_type: Scope,
        entity_id: str,
        measure_date: date,
    ) -> CollectedMetricValue:
        raise NotImplementedError

    def aggregate(
        self,
        raw_values: list,
        agg_method: StrAgg | BoolAgg | NumericAgg | EventAgg | None = None,
        percentile: float | int | None = None,
    ) -> AggregationResult:
        method = agg_method or self.default_agg_method

        match self.storage_type:
            case StorageType.NUMERIC:
                values = [v for v in raw_values if v is not None]
                nm, np = normalize_aggregation_method(
                    method=str(method),
                    percentile=percentile,
                )

                return AggregationResult(
                    value=aggregate_numeric(
                        values=values,
                        method=nm,
                        percentile=np,
                    ),
                    agg_method=nm,
                    percentile=np,
                    samples_count=len(values),
                )

            case StorageType.BOOL:
                values = [v for v in raw_values if v is not None]
                nm = normalize_bool_aggregation_method(str(method))

                return AggregationResult(
                    value=aggregate_bool(
                        values=values,
                        method=nm,
                    ),
                    agg_method=nm,
                    percentile=None,
                    samples_count=len(raw_values),
                )

            case StorageType.STR:
                nm = normalize_str_aggregation_method(str(method))

                return AggregationResult(
                    value=aggregate_str(
                        values=raw_values,
                        method=nm,
                    ),
                    agg_method=nm,
                    percentile=None,
                    samples_count=len(raw_values),
                )

            case StorageType.EVENT:
                raise EventAggregationNotImplementedError(self.metric_slug)

            case _:
                raise UnsupportedStorageTypeError(self.storage_type)


class AllurNumericMetricCollector(MetricCollector):
    async def collect(
        self,
        entity_type: Scope,
        entity_id: str,
        measure_date: date,
    ) -> CollectedMetricValue:
        project_id = validate_entity_type(
            entity_id=entity_id,
            entity_type=entity_type,
            target_type=self.scope,
            metric_slug=self.metric_slug,
        )

        items = await self._git_client.get_metrics_chunk(
            project_id=project_id,
            date_from=measure_date,
            date_to=measure_date,
        )

        return CollectedMetricValue(
            storage_type=self.storage_type,
            value=self._build_events(items),
        )

    def _build_events(
        self,
        items: list[AllureDTO],
    ) -> list[dict]:
        events = []

        for item in items:
            value = self._extract_value(item)

            if value is None:
                continue

            events.append(
                {
                    "value": value,
                }
            )

        return events

    @abstractmethod
    def _extract_value(
        self,
        item: AllureDTO,
    ) -> int | float | None:
        raise NotImplementedError

    @override
    def aggregate(
        self,
        raw_values: list[dict],
        agg_method: StrAgg | BoolAgg | NumericAgg | EventAgg | None = None,
        percentile: float | int | None = None,
    ) -> AggregationResult:
        values = extract_numeric_from_event(raw_values)
        method = agg_method or self.default_agg_method

        return AggregationResult(
            value=aggregate_numeric(
                values=values,
                method=str(method),
                percentile=percentile,
            ),
            agg_method=str(method),
            percentile=percentile,
            samples_count=len(values),
        )
```

### Обновленная версия кода
```python
class AggregationVisitor(ABC):
    @abstractmethod
    def visit_metric_collector(
        self,
        collector: "MetricCollector",
        raw_values: list,
        agg_method: StrAgg | BoolAgg | NumericAgg | EventAgg | None,
        percentile: float | int | None,
    ) -> AggregationResult:
        raise NotImplementedError

    @abstractmethod
    def visit_allur_numeric_metric_collector(
        self,
        collector: "AllrNumericMetricCollector",
        raw_values: list[dict],
        agg_method: StrAgg | BoolAgg | NumericAgg | EventAgg | None,
        percentile: float | int | None,
    ) -> AggregationResult:
        raise NotImplementedError


class MetricCollector(ABC):
    metric_slug: Metric
    source_code: SourceCode
    storage_type: StorageType
    default_agg_method: StrAgg | BoolAgg | NumericAgg | EventAgg
    scope: Scope
    large_aggregation_category: LargeAggregationCategory | None = None
    
    def accept(
        self,
        visitor: AggregationVisitor,
        raw_values: list,
        agg_method: StrAgg | BoolAgg | NumericAgg | EventAgg | None = None,
        percentile: float | int | None = None,
    ) -> AggregationResult:
        return visitor.visit_metric_collector(
            collector=self,
            raw_values=raw_values,
            agg_method=agg_method,
            percentile=percentile,
        )
    async def collect(
        self,
        entity_type: Scope,
        entity_id: str,
        measure_date: date,
    ) -> CollectedMetricValue:
        raise NotImplementedError


class AllurNumericMetricCollector(MetricCollector):
    def accept(
        self,
        visitor: AggregationVisitor,
        raw_values: list[dict],
        agg_method: StrAgg | BoolAgg | NumericAgg | EventAgg | None = None,
        percentile: float | int | None = None,
    ) -> AggregationResult:
        return visitor.visit_allr_numeric_metric_collector(
            collector=self,
            raw_values=raw_values,
            agg_method=agg_method,
            percentile=percentile,
        )

class DefaultAggregationVisitor(AggregationVisitor):
    def visit_metric_collector(
        self,
        collector: MetricCollector,
        raw_values: list,
        agg_method: StrAgg | BoolAgg | NumericAgg | EventAgg | None,
        percentile: float | int | None,
    ) -> AggregationResult:
        ...
    
    def visit_allr_numeric_metric_collector(
            self,
            collector: AllrNumericMetricCollector,
            raw_values: list[dict],
            agg_method: StrAgg | BoolAgg | NumericAgg | EventAgg | None,
            percentile: float | int | None,
        ) -> AggregationResult:
        ...
```

## Заключение
Изначальный код был модифицирован с помощью паттерна Visitor (accept() в MetricCollector(ABC)). Благодаря этому получилось избавиться
от существующей необходимости к переопределению метода aggregate в зависимости от поступающих "сырых" данных. Отредактированная версия кода
включает в себя дополнительные сущности (прим. AggregationVisitor, DefaultAggregationVisitor). Можно заключить что в рамках текущего примера
паттерна Visitor показал себя эффективно в достаточной мере, но необходимо понимать что такую правку придется обосновывать и не факт что её плюсы
будет легко интуитивно принять если до этого не было опыта применения данного паттерна для разрешения проблем с применимостью одной и той же процедуры для
объектов рахличных классов. Также важно упоминуть что в данном случае в качестве родительского класса выступает абстрактный класс, однако проблему с переопределением
метода aggregate это не смягчает.
