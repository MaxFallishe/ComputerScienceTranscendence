# Минимизируем шаги изменений
Кент Бек высказывал постулат о том что каждый раз когда тесты завершились успешно, вы должны делать коммит кода.
В этой заметке изучим подход лежащий под этим высказыванием на нескольких случайных примерах которые будут написаны с использованием 
предложенной методики - писать код и тесты к нему, а если тесты завершились с ошибкой, то код необходимо откатить к ближайшей ревизии.
  

## Пример №1
### Существующая версия кода
В небольшой задаче требуется убрать замену реальных уникальных/не запланированных значений на "Свой ответ"
```python
async def _build_single_feature_report(self, blueprint, inspection_rows):
    recorded_features = []
    showcase_rows = []
    approved_features = set(blueprint.available_features or [])

    for inspection in inspection_rows:
        selected_feature = None

        if inspection.resident_features:
            decoded_features = self._parse_building_feature_value(
                inspection.resident_features
            )
            if decoded_features:
                selected_feature = decoded_features[0]
        elif inspection.notes_text:
            decoded_features = self._parse_building_feature_value(
                inspection.notes_text
            )
            if decoded_features:
                selected_feature = decoded_features[0]

        if selected_feature:
            if selected_feature not in approved_features:
                selected_feature = "_unregistered_feature_"

            recorded_features.append(selected_feature)
            showcase_rows.append((inspection, selected_feature))

    feature_counter = Counter(recorded_features)

    unused_approved_features = approved_features - set(recorded_features)
    feature_counter.update(
        dict.fromkeys(unused_approved_features, 0)
    )

    total_inspections = sum(feature_counter.values())

    feature_entries = [
        BuildingSingleFeatureSummary.Entry(
            feature=feature,
            count=count,
            percentage=(
                count * 100 / total_inspections
                if total_inspections
                else 0.0
            ),
        )
        for feature, count in feature_counter.items()
    ]

    showcase_samples = [
        BuildingSingleFeatureSample(
            resident_id=inspection.resident_id,
            resident_login=inspection.resident_login or None,
            building_name=inspection.building_name or None,
            selected_feature=selected_feature,
        )
        for inspection, selected_feature
        in showcase_rows[: self.SHOWCASE_LIMIT]
    ]

    return BuildingSingleFeatureReport(
        blueprint_id=blueprint.blueprint_id,
        blueprint_text=blueprint.blueprint_text,
        feature_type=BuildingFeatureType.SINGLE,
        available_features=blueprint.available_features,
        summary=BuildingSingleFeatureSummary(
            total_inspections=total_inspections,
            entries=feature_entries,
        ),
        showcase_samples=showcase_samples,
    )
```

### Обновленная версия кода
```python
async def _build_single_feature_report(self, blueprint, inspection_rows):
    recorded_features = []
    showcase_rows = []
    approved_features = set(blueprint.available_features or [])

    for inspection in inspection_rows:
        raw_feature = None

        if inspection.resident_features:
            decoded_features = self._parse_building_feature_value(
                inspection.resident_features
            )
            if decoded_features:
                raw_feature = decoded_features[0]
        elif inspection.notes_text:
            decoded_features = self._parse_building_feature_value(
                inspection.notes_text
            )
            if decoded_features:
                raw_feature = decoded_features[0]

        if not raw_feature:
            continue

        registered_feature = (
            raw_feature
            if raw_feature in approved_features
            else "_unregistered_feature_"
        )

        recorded_features.append(registered_feature)
        showcase_rows.append((inspection, raw_feature))

    feature_counter = Counter(recorded_features)

    unused_approved_features = approved_features - set(recorded_features)
    feature_counter.update(
        dict.fromkeys(unused_approved_features, 0)
    )

    total_inspections = sum(feature_counter.values())

    feature_entries = [
        BuildingSingleFeatureSummary.Entry(
            feature=feature,
            count=count,
            percentage=(
                count * 100 / total_inspections
                if total_inspections
                else 0.0
            ),
        )
        for feature, count in feature_counter.items()
    ]

    showcase_samples = [
        BuildingSingleFeatureSample(
            resident_id=inspection.resident_id,
            resident_login=inspection.resident_login or None,
            building_name=inspection.building_name or None,
            selected_feature=raw_feature,
        )
        for inspection, raw_feature
        in showcase_rows[: self.SHOWCASE_LIMIT]
    ]

    return BuildingSingleFeatureReport(
        blueprint_id=blueprint.blueprint_id,
        blueprint_text=blueprint.blueprint_text,
        feature_type=BuildingFeatureType.SINGLE,
        available_features=blueprint.available_features,
        summary=BuildingSingleFeatureSummary(
            total_inspections=total_inspections,
            entries=feature_entries,
        ),
        showcase_samples=showcase_samples,
    )
```

## Пример №2
По примеру первой задачи требуется убрать подстановку значений "Свой ответ" вместо реальных значений
### Существующая версия кода
```python
async def _build_multiple_feature_report(
    self,
    blueprint,
    inspection_rows,
):
    recorded_features = []
    showcase_rows = []
    approved_features = set(blueprint.available_features or [])

    for inspection in inspection_rows:
        selected_features = []

        if inspection.resident_features:
            selected_features = self._parse_building_feature_value(
                inspection.resident_features
            )
        elif inspection.notes_text:
            selected_features = self._parse_building_feature_value(
                inspection.notes_text
            )

        if not selected_features:
            continue

        selected_features = [
            feature
            if feature in approved_features
            else "_unregistered_feature_"
            for feature in selected_features
        ]

        if not selected_features:
            continue

        recorded_features.extend(selected_features)
        showcase_rows.append((inspection, selected_features))

    feature_counter = Counter(recorded_features)

    unused_approved_features = approved_features - set(recorded_features)
    feature_counter.update(
        dict.fromkeys(unused_approved_features, 0)
    )

    total_inspections = len(showcase_rows)
    total_selected_features = sum(feature_counter.values())

    feature_entries = [
        BuildingMultipleFeatureSummary.Entry(
            feature=feature,
            count=count,
            percentage=(
                count * 100 / total_selected_features
                if total_selected_features
                else 0.0
            ),
        )
        for feature, count in feature_counter.items()
    ]

    showcase_samples = [
        BuildingMultipleFeatureSample(
            resident_id=inspection.resident_id,
            resident_login=inspection.resident_login or None,
            building_name=inspection.building_name or None,
            selected_features=", ".join(selected_features),
        )
        for inspection, selected_features
        in showcase_rows[: self.SHOWCASE_LIMIT]
    ]

    return BuildingMultipleFeatureReport(
        blueprint_id=blueprint.blueprint_id,
        blueprint_text=blueprint.blueprint_text,
        feature_type=BuildingFeatureType.MULTIPLE,
        available_features=blueprint.available_features,
        summary=BuildingMultipleFeatureSummary(
            total_inspections=total_inspections,
            entries=feature_entries,
        ),
        showcase_samples=showcase_samples,
    )
```

### Обновленная версия кода
```python
async def _build_multiple_feature_report(
    self,
    blueprint,
    inspection_rows,
):
    recorded_features = []
    showcase_rows = []
    approved_features = set(blueprint.available_features or [])

    for inspection in inspection_rows:
        raw_features = []

        if inspection.resident_features:
            raw_features = self._parse_building_feature_value(
                inspection.resident_features
            )
        elif inspection.notes_text:
            raw_features = self._parse_building_feature_value(
                inspection.notes_text
            )

        if not raw_features:
            continue

        registered_features = [
            feature
            if feature in approved_features
            else "_unregistered_feature_"
            for feature in raw_features
        ]

        if not registered_features:
            continue

        recorded_features.extend(registered_features)
        showcase_rows.append((inspection, raw_features))

    feature_counter = Counter(recorded_features)

    unused_approved_features = approved_features - set(recorded_features)
    feature_counter.update(
        dict.fromkeys(unused_approved_features, 0)
    )

    total_inspections = len(showcase_rows)
    total_selected_features = sum(feature_counter.values())

    feature_entries = [
        BuildingMultipleFeatureSummary.Entry(
            feature=feature,
            count=count,
            percentage=(
                count * 100 / total_selected_features
                if total_selected_features
                else 0.0
            ),
        )
        for feature, count in feature_counter.items()
    ]

    showcase_samples = [
        BuildingMultipleFeatureSample(
            resident_id=inspection.resident_id,
            resident_login=inspection.resident_login or None,
            building_name=inspection.building_name or None,
            selected_features=", ".join(raw_features),
        )
        for inspection, raw_features
        in showcase_rows[: self.SHOWCASE_LIMIT]
    ]

    return BuildingMultipleFeatureReport(
        blueprint_id=blueprint.blueprint_id,
        blueprint_text=blueprint.blueprint_text,
        feature_type=BuildingFeatureType.MULTIPLE,
        available_features=blueprint.available_features,
        summary=BuildingMultipleFeatureSummary(
            total_inspections=total_inspections,
            entries=feature_entries,
        ),
        showcase_samples=showcase_samples,
    )
```

## Заключение
В рамках выбранных задач произошел неожиданный исход, не смотря на то что проект имеет 80+ процентов покрытия тестами и задачи по сути являются исправлениями текущего функционала (логики),
изменения кода (1-ая задача) без модификации тестов с 4-ой попытки прошли проверку тестами, что скорее говорит о плохом содержании юнит/интеграционных тестов. 
После этого тесты в проекте для затрагиваемых методов были исправлены. Относительно самого подхода, он действительно заставляет решать задачу с минимизацией количества изменений с необходимостью 
полностью понимать смысл внесенных изменений + логику которыми тесты проверяют изменяемый функционал. При удалении неудавшейся версии кода происходит неприятный откат, однако фокусировка 
на небольшой участке кода (приходится делать изменения маленькими и емкими) позволяет сохранить контекст и память о только что удаленном решении.
Из замеченных минусов данного подхода - уменьшается мотивация исправлять плохой дизайн существующих элементов, так как удаление и написание нового компонента может привести к непрохождению 
тестов с большей вероятностью. В обычном рабочем формате (из наблюдения) коммит кода просходит только после выполнения всего скоупа задач, отладки всех тестов, после чего на этапе обнаружения и исправления некоректного поведения на тестовом контуре.
В обычном рабочем процессе работы точно стоит стараться минимизировать вносимые изменения если это возможно, при этом понимая логику того как планируется проверять этот код сейчас и в будущем.
