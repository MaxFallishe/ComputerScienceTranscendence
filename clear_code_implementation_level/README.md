# Ясный код (уровень реализации)
_"Сложность разработки убивает производительность, мотивацию и время."_
Часто в коде можно встретить элементы плохого стиля на уровне реализации, давайте рассмотрим на примерах какие бывают.
Для каждой категории будет пример с кодом "до" и кодом "после".

## Методы, которые используются только в тестах
В приведенном примере используется функция которая использовалась исключительно в одном тесте `test_can_create_inspections` + была достаточно плохо спроектирована с точки зрения добавления новый полей данных,  
поэтому в исправленной версии было решено оставить фикстуру с реалистичными значениями для будущих нужд, а сам тест сократить, по возможности избавившись от всех избыточных конструкций. 
Например был напрямую переиспользован существующий метод  `create_inspection()`.

### Существующая версия кода
```python
# conftest.py
class ConstructionService:
    @staticmethod
    async def create_repository_with_inspections(
        session: sqlalchemy.ext.asyncio.AsyncSession,
        *inspections_data: dict,
    ) -> tuple[SqlAlchemyInspectionRepository, list[Inspection]]:
        repo = SqlAlchemyInspectionRepository(session)

        inspections: list[Inspection] = []

        for record in inspections_data:
            inspection = Inspection(
                project_id=record["project_id"],
                inspection_item_id=record["inspection_item_id"],
                measurement_value=record["measurement_value"],
                note_value=record["note_value"],
                inspector_login=record["inspector_login"],
                site_zone=record["site_zone"],
                crew_id=record["crew_id"],
                crew_name=record["crew_name"],
                inspector_role=record["inspector_role"],
                inspection_timestamp=record["inspection_timestamp"],
                site_start_date=record["site_start_date"],
                project_name=record["project_name"],
                allowed_statuses=record["allowed_statuses"],
                selected_statuses=record["selected_statuses"],
                inspection_period_start=record.get("inspection_period_start"),
                inspection_period_end=record.get("inspection_period_end"),
                work_category=record.get("work_category"),
                work_subcategory=record.get("work_subcategory"),
                inspection_item_name=record.get("inspection_item_name"),
                inspection_item_type=record.get("inspection_item_type"),
                priority_weight=record.get("priority_weight"),
                min_grade=record["min_grade"],
                max_grade=record["max_grade"],
                inspection_id=record["inspection_id"],
                item_number=record["item_number"],
                items_count=record["items_count"],
                contractor_team_id=record["contractor_team_id"],
                is_skipped=record["is_skipped"],
                inspector_comment=record["inspector_comment"],
                predefined_status_options=record[
                    "predefined_status_options"
                ],
                selected_status=record["selected_status"],
                selected_flags=record["selected_flags"],
                custom_status=record["custom_status"],
                quality_rating=record["quality_rating"],
                quality_comment=record["quality_comment"],
                site_events=record.get("site_events", []),
            )

            inspections.append(inspection)
            inspection.id = secrets.randbelow(1000)

            await repo.create_inspection(inspection)

        return repo, inspections
    
# test_...
class TestCreateInspection:
    async def test_can_create_inspections(
        self,
        session_factory_mock: sqlalchemy.ext.asyncio.async_sessionmaker,
    ) -> None:
        async with session_factory_mock() as session:
            repo, inspections = (
                await ConstructionService.create_repository_with_inspections(
                    session,
                    {
                        "inspector_login": "engineer1",
                        "crew_id": 12,
                        "crew_name": "North Site Crew",
                        "site_zone": "foundation",
                        "site_start_date": date(2025, 4, 10),
                        "allowed_statuses": tuple(),
                        "selected_statuses": tuple(),
                        "inspector_role": "Site Engineer",
                        "project_id": 101,
                        "inspection_item_id": 7,
                        "note_value": None,
                        "measurement_value": 8.5,
                        "inspection_period_start": date(2025, 5, 1),
                        "inspection_period_end": date(2025, 5, 31),
                        "work_category": "Structural",
                        "work_subcategory": "Foundation",
                        "inspection_item_name": "Concrete strength",
                        "inspection_item_type": "measurement",
                        "priority_weight": 5,
                        "inspection_timestamp": date(2025, 5, 24),
                        "project_name": "Riverside Complex",
                        "min_grade": "1",
                        "max_grade": "10",
                        "inspection_id": 347,
                        "item_number": 2,
                        "items_count": 12,
                        "contractor_team_id": 567,
                        "is_skipped": None,
                        "inspector_comment": None,
                        "predefined_status_options": None,
                        "selected_status": None,
                        "selected_flags": None,
                        "custom_status": None,
                        "quality_rating": None,
                        "quality_comment": None,
                    },
                )
            )

            assert inspections[0].inspector_login == "engineer1"
```

### Обновленная версия версия кода
```python
@pytest.fixture
def site_inspection_example() -> dict:
    return {
        "project_id": 101,
        "inspection_item_id": 7,
        "measurement_value": 8.5,
        "note_value": "reinforcement checked",
        "inspector_login": "site_engineer",
        "site_zone": "building_a",
        "crew_id": 12,
        "crew_name": "structural_crew",
        "inspector_role": "engineer",
        "inspection_timestamp": datetime(2025, 5, 12, 10, 30, 0),
        "site_start_date": date(2025, 4, 1),
        "project_name": "riverside_complex",
        "allowed_statuses": ("approved", "requires_rework"),
        "selected_statuses": ("approved",),
        "inspection_period_start": date(2025, 5, 1),
        "inspection_period_end": date(2025, 5, 31),
        "work_category": "structural",
        "work_subcategory": "concrete",
        "inspection_item_name": "foundation_quality",
        "inspection_item_type": "measurement",
        "priority_weight": 1,
        "min_grade": "low",
        "max_grade": "high",
        "inspection_id": 1,
        "item_number": 1,
        "items_count": 1,
        "contractor_team_id": 21,
        "is_skipped": False,
        "inspector_comment": "work completed according to plan",
        "predefined_status_options": (
            "approved",
            "requires_rework",
        ),
        "selected_status": "approved",
        "selected_flags": ("reinforcement_checked",),
        "custom_status": "ready for next stage",
        "quality_rating": "approved",
        "quality_comment": "no major defects",
        "site_events": [],
    }

@pytest.mark.asyncio
async def test_can_create_inspection(
    session_factory_mock: async_sessionmaker,
    site_inspection_example: dict,
):
    async with session_factory_mock() as session:
        repo = SqlAlchemyInspectionRepository(session)

        inspection = Inspection(**site_inspection_example)
        inspection.id = secrets.randbelow(1000)

        await repo.create_inspection(inspection)

        created_inspection = await repo.retrieve_inspection_by_id(
            inspection.id
        )

        assert created_inspection is not None

        for field in (
            "id",
            "project_id",
            "inspection_item_id",
            "inspector_login",
            "site_zone",
            "crew_id",
            "crew_name",
        ):
            assert getattr(created_inspection, field) == getattr(
                inspection,
                field,
            )
```


## Цепочки методов. Метод вызывает другой метод, который вызывает другой метод, который вызывает другой метод, который вызывает другой и т.д.
На примере ниже (пусть и не с самым большим количеством вызываемых методов, но всё же) можно увидеть как возможно убрать сразу несколько вызовов 
отказавшись от ненужного вызова метода и заменив его на точечный вызов узкой функции для получения временных границ. 
Ниже продемонстрированы цепочки вызовов методов "до" и "после" ухода от метода get_survey_metainfo_list().

```sh
# От версии
# endpoint.fetch_organization_analytics() ->
# service.get_organization_analytics() ->
# rr_service.enrich_analytics() ->
# answer_repository.calc_search_date() ->
# answer_repository.get_survey_metainfo_list() ->
# answer_repository.fetch_survey_date_bounds() ->
# answer_repository.to_date()

# К версии
# endpoint.fetch_organization_analytics() ->
# service.get_organization_analytics() ->
# rr_service.enrich_analytics() ->
# answer_repository.calc_search_date()
```

### Существующая версия кода
```python
# Цепочка вызова методов
# endpoint.fetch_organization_analytics() ->
# service.get_organization_analytics() ->
# rr_service.enrich_analytics() ->
# answer_repository.calc_search_date() ->
# answer_repository.get_survey_metainfo_list() ->
# answer_repository.fetch_survey_date_bounds() ->
# answer_repository.to_date()

async def calc_search_date(self, survey_id: int) -> datetime.date:
    """Calculate the most fresh search_date by which should be get data about survey."""
    meta_infos = await self.get_survey_metainfo_list()
    meta_infos_map = {info.survey_id: info for info in meta_infos}
    survey_metainfo = meta_infos_map.get(str(survey_id))
    search_date = (
        survey_metainfo.end_date
        if datetime.date.today() > survey_metainfo.end_date
        else datetime.date.today() - datetime.timedelta(days=1)
    )
    return search_date


async def get_survey_metainfo_list(self) -> list[SurveyMetaInfo]:
    versions: list = []

    for survey_setting in AppSettings.engineering_culture_surveys:
        survey_id = survey_setting.survey_id

        # The beginning of this block is partially outside the screenshot.
        # start_date / end_date / response_date are used below.

        quarter_ref_date = end_date or start_date

        year_n_quarter = None
        if quarter_ref_date:
            q_num = (quarter_ref_date.month - 1) // 3 + 1
            year_n_quarter = f"{quarter_ref_date.year}Q{q_num}"

        meta_info = SurveyMetaInfo(
            survey_id=survey_id,
            start_date=start_date,
            end_date=end_date,
            response_rate_date=response_date,
            quarter=year_n_quarter,
            threshold_size=survey_setting.threshold_size,
        )
        versions.append(meta_info)

    return versions

async def fetch_survey_date_bounds(
    self,
    survey_id: int,
) -> tuple[datetime.date | None, datetime.date | None]:
    query = (
        select(
            func.min(answers_table.c.start_date_survey),
            func.max(answers_table.c.end_date_survey),
            func.min(answers_table.c.answer_timestamp),
            func.max(answers_table.c.answer_timestamp),
        )
        .where(answers_table.c.survey_id == survey_id)
    )

    result = await self._session.execute(query)
    row = result.one()

    r_start_survey, r_end_survey, ts_min, ts_max = row

    start_date = r_start_survey if r_start_survey else ts_min
    end_date = r_end_survey if r_end_survey else ts_max

    def to_date(val):
        if val is None:
            return None
        if isinstance(val, datetime.datetime):
            return val.date()
        return val

    return to_date(start_date), to_date(end_date)
```

### Обновленная версия версия кода
```python
# Цепочка вызова методов
# endpoint.fetch_organization_analytics() ->
# service.get_organization_analytics() ->
# rr_service.enrich_analytics() ->
# answer_repository.calc_search_date()

async def calc_search_date(
    self,
    survey_id: int,
) -> datetime.date:
    _, end_date = await self.fetch_survey_date_bounds(survey_id)
    yesterday = datetime.date.today() - datetime.timedelta(days=1)
    if end_date and datetime.date.today() > end_date:
        return end_date
    return yesterday


async def fetch_survey_date_bounds(
    self,
    survey_id: int,
) -> tuple[datetime.date | None, datetime.date | None]:
    query = select(
        func.coalesce(
            func.min(answers_table.c.start_date_survey),
            cast(func.min(answers_table.c.answer_timestamp), Date),
        ).label("start_date"),
        func.coalesce(
            func.max(answers_table.c.end_date_survey),
            cast(func.max(answers_table.c.answer_timestamp), Date),
        ).label("end_date"),
    ).where(
        answers_table.c.survey_id == survey_id,
    )
    result = await self._session.execute(query)
    row = result.one()

    return row.start_date, row.end_date
```


## У метода слишком большой список параметров.
Очень часто в методах по созданию или апдейту каких-либо сущностей можно встретить огромный список параметров, однако в большинстве случаев лучше заменить их 
на целевую модель. В примере ниже можно убедиться как сильно можно упростить логику и размер операции использую модель вместо списка параметров. Саму операцию в sqlalchemy
синтаксисе также можно упростить, но реализация зависит от желаемой логики перезаписи данных сущности.

### Существующая версия кода
```python
async def update_answer(
    self,
    answer_id: int,
    survey_id: int,
    question_id: int,
    login: str,
    domain: str,
    team_id: int,
    team_name: str,
    role: str,
    number_value: float | None,
    text_value: str | None,
    answer_timestamp: datetime.datetime,
    start_date_job: datetime.date | None,
    survey_name: str,
    category: str | None,
    sub_category: str | None,
    question_name: str | None,
    question_type: str | None,
    weight: int | None,
) -> None:
    query = (
        update(answers_table)
        .where(answers_table.c.id == answer_id)
        .values(
            survey_id=survey_id,
            question_id=question_id,
            login=login,
            domain=domain,
            team_id=team_id,
            team_name=team_name,
            role=role,
            number_value=number_value,
            text_value=text_value,
            answer_timestamp=answer_timestamp,
            start_date_job=start_date_job,
            survey_name=survey_name,
            category=category,
            sub_category=sub_category,
            question_name=question_name,
            question_type=question_type,
            weight=weight,
        )
    )

    await self._session.execute(query)
```

### Обновленная версия версия кода
```python
async def update_answer(
    self,
    answer: Answer,
) -> None:
    values = answer.model_dump(exclude={"id"})

    query = (
        update(answers_table)
        .where(answers_table.c.id == answer.id)
        .values(**values)
    )

    await self._session.execute(query)
```


## Когда несколько методов используются для решения одной и той же проблемы, создавая несогласованность.
При недостаточной погруженности в проект, легко продублировать функционал который уже существует или же специально продублировать метод не желая размышлять над возможностью универсализации кода. В примере ниже, в изначальной версии был создан по сути дубликат метода `_take_unique_respondent_samples`,
однако достаточно понятный шаг в данном случае - обобщить логику обоих методов в один и при необходимости совершать нужную трансформацию через лямбду в одном из параметров метода.

### Существующая версия кода
```python
def _take_unique_respondent_samples(self, rows):
    samples = []
    seen_respondent_ids = set()

    for row in rows:
        respondent_id = getattr(row, "respondent_id", None)

        if respondent_id is None:
            continue

        if respondent_id in seen_respondent_ids:
            continue

        seen_respondent_ids.add(respondent_id)
        samples.append(row)

        if len(samples) >= self.SAMPLES_LIMIT:
            break

    return samples


def _take_unique_respondent_tuple_samples(self, rows):
    samples = []
    seen_respondent_ids = set()

    for item in rows:
        row = item[0]
        respondent_id = getattr(row, "respondent_id", None)

        if respondent_id is None:
            continue

        if respondent_id in seen_respondent_ids:
            continue

        seen_respondent_ids.add(respondent_id)
        samples.append(item)

        if len(samples) >= self.SAMPLES_LIMIT:
            break

    return samples
```

### Обновленная версия версия кода
```python
def _take_unique_respondent_samples(
    self,
    rows: list[Any],
    respondent_id_accessor: Callable[[Any], int | str | None],
) -> list[Any]:
    samples = []
    seen_respondent_ids = set()

    for item in rows:
        respondent_id = respondent_id_accessor(item)

        if respondent_id is None:
            continue

        if respondent_id in seen_respondent_ids:
            continue

        seen_respondent_ids.add(respondent_id)
        samples.append(item)

        if len(samples) >= self.SAMPLES_LIMIT:
            break

    return samples
```


## Чрезмерный результат. Метод возвращает больше данных, чем нужно вызывающему его компоненту.
В примере, который берет тот же участок кода что и пример с длинными цепочками методов, можно усмотреть что оригинальный дизайн через метод `get_survey_metainfo_list()`
возвращает слишком много методов, так как методу `calc_search_date()` требуется только информация об `survey_metainfo.end_date`. Поэтому, с учетом наличия метода `fetch_survey_date_bounds()`
для получения границ дат для опроса, можно безопасно отказаться от изначальной схемы с получением избыточного количества данных.

### Существующая версия кода
```python
async def calc_search_date(self, survey_id: int) -> datetime.date:
    """Calculate the most fresh search_date by which should be get data about survey."""
    meta_infos = await self.get_survey_metainfo_list()
    meta_infos_map = {info.survey_id: info for info in meta_infos}
    survey_metainfo = meta_infos_map.get(str(survey_id))
    search_date = (
        survey_metainfo.end_date
        if datetime.date.today() > survey_metainfo.end_date
        else datetime.date.today() - datetime.timedelta(days=1)
    )
    return search_date


async def get_survey_metainfo_list(self) -> list[SurveyMetaInfo]:
    versions: list = []

    for survey_setting in AppSettings.engineering_culture_surveys:
        survey_id = survey_setting.survey_id

        # The beginning of this block is partially outside the screenshot.
        # start_date / end_date / response_date are used below.

        quarter_ref_date = end_date or start_date

        year_n_quarter = None
        if quarter_ref_date:
            q_num = (quarter_ref_date.month - 1) // 3 + 1
            year_n_quarter = f"{quarter_ref_date.year}Q{q_num}"

        meta_info = SurveyMetaInfo(
            survey_id=survey_id,
            start_date=start_date,
            end_date=end_date,
            response_rate_date=response_date,
            quarter=year_n_quarter,
            threshold_size=survey_setting.threshold_size,
        )
        versions.append(meta_info)

    return versions
```

### Обновленная версия версия кода
```python
async def calc_search_date(
    self,
    survey_id: int,
) -> datetime.date:
    _, end_date = await self.fetch_survey_date_bounds(survey_id)
    yesterday = datetime.date.today() - datetime.timedelta(days=1)
    if end_date and datetime.date.today() > end_date:
        return end_date
    return yesterday


async def fetch_survey_date_bounds(
    self,
    survey_id: int,
) -> tuple[datetime.date | None, datetime.date | None]:
    query = select(
        func.coalesce(
            func.min(answers_table.c.start_date_survey),
            cast(func.min(answers_table.c.answer_timestamp), Date),
        ).label("start_date"),
        func.coalesce(
            func.max(answers_table.c.end_date_survey),
            cast(func.max(answers_table.c.answer_timestamp), Date),
        ).label("end_date"),
    ).where(
        answers_table.c.survey_id == survey_id,
    )
    result = await self._session.execute(query)
    row = result.one()

    return row.start_date, row.end_date
```