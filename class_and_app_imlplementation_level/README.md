# Ясный код (уровень класса и приложения)
Ниже на примерах рассмотрим какие плохие моменты встречаются в реальных проектах с точки зрения проектирования иерархии классов.
Все случаи в данной заметке разделены на те, которые расположены на уровне классов, и тех которые расположены на уровне приложения. 

## Уровень классов

### 1. Класс слишком большой или в программе создаётся слишком много его инстансов
В примере ниже представлен адаптер для взаимодействия с API гитлаба в разрезе проектов. Этот адаптер стал достаточно объемным, 
что не редкость для архитектур, базирующихся на DDD. При такой организации проекта легко упустить момент когда адаптеры становятся самым
"толстым" слоем в приложении и начинают аккумулировать в себе всё больше логики + файлы растут в размере без дополнительного разделения
на более мелкие сущности. По возможности стоит отсматривать адаптеры на наличие каких-то избыточных вспомогательных методов и методов которые
можно вынести в отдельную более узкую сущность.

Если же в проекте существует класс количество инстансов которого можно расценить как избыточное - возможно это из-за
того что класс получился слишком универальным и на него теперь завязана большая часть программы что может привести к избыточно большой
сцепленности кода.

```python
class GitLabProjectAdapter:

    def __init__(
        self,
        base_url: str = str(settings.gitlab.base_url),
        client: AbstractHttpClient = dependency_injector.wiring.Provide[
            HttpClientContainer.http_client
        ],
        limited_client: AbstractHttpClient = dependency_injector.wiring.Provide[
            LimitedHttpClientContainer.http_client
        ],
    ) -> None:
        self.__base_url = base_url
        self.__graphql_url: str = str(settings.gitlab.graphql_url)
        self.__headers = {
            "PRIVATE-TOKEN": settings.gitlab.access_token,
        }
        self.__client = client
        self.__limited_client = limited_client

    async def compare(
        self,
        project_id: int,
        from_branch: str,
        to_branch: str,
    ) -> typing.Any:
        ...

    async def get_project_groups(
        self,
        project_id: int,
    ) -> list[model.Group]:
        ...

    async def get_project_push_rules(
        self,
        project_id: int,
    ) -> model.PushRule | None:
        ...

    async def get_project_branches(
        self,
        project_id: int,
    ) -> list[dict[str, Any]]:
        ...

    async def get_project_merge_requests(
        self,
        project_id: int,
    ) -> list[dict]:
        ...

    async def get_project_default_branch(
        self,
        project_id: int,
    ) -> str:
        ...

    async def get_project_merge_request_commits(
        self,
        project_id: int,
        mr_iid: int,
    ) -> Any:
        ...

    async def get_project_branch_commits(
        self,
        project_id: int,
        branch_name: str,
    ) -> list[dict]:
        ...

    async def get_project_merge_request_changes(
        self,
        project_id: int,
        mr_iid: int,
    ) -> Any:
        ...

    async def get_project_freeze_periods(
        self,
        project_id: int,
    ) -> Any:
        ...

    async def get_projects_with_mm_integration(
        self,
    ) -> list[int]:
        ...

    async def get_project_environments(
        self,
        project_id: int,
    ) -> list[dict]:
        ...

    async def get_project_visibility(
        self,
        project_path: str,
    ) -> str | None:
        ...

    async def get_repository_filenames(
        self,
        project_path: str,
    ) -> list[str]:
        ...

    async def get_project_last_activity_at(
        self,
        project_path: str,
    ) -> datetime | None:
        ...

    async def get_project_releases_tag_names(
        self,
        project_path: str,
    ) -> list[str]:
        ...

    async def get_project_topics(
        self,
        project_path: str,
    ) -> list[str]:
        ...

    async def get_project_description(
        self,
        project_path: str,
    ) -> str | None:
        ...

    async def get_project_pipelines(
        self,
        project_path: str,
        updated_after: str | None = None,
        status: str | None = None,
    ) -> list[model.PipelineEntity]:
        ...

    async def get_merge_requests_enabled_setting(
        self,
        project_id: int,
    ) -> bool:
        ...

    async def get_file_characters_count(
        self,
        project_id: int,
        file_name: str,
    ) -> int | None:
        ...

    async def get_issues_enabled_setting(
        self,
        project_id: int,
    ) -> bool:
        ...

    async def get_star_count(
        self,
        project_id: int,
    ) -> int:
        ...

    async def get_forks_count(
        self,
        project_id: int,
    ) -> int:
        ...

    async def get_project_issues_authors(
        self,
        project_path: str,
    ) -> set[str]:
        ...

    async def get_project_templates(
        self,
        project_id: int,
        template_type: str,
    ) -> list[str] | None:
        ...

    async def get_project_languages(
        self,
        project_id: int,
    ) -> dict:
        ...

    async def get_project_last_deployments(
        self,
        project_id: int,
        finished_after: date,
        finished_before: date,
        environment: str | None = None,
        per_page: int | None = None,
    ) -> list[model.Deployment]:
        ...

    async def get_project_production_deployments(
        self,
        project_id: int,
        finished_after: date,
        finished_before: date,
        environment: list[model.Environment],
    ) -> list[model.Deployment]:
        ...
```

### 2. Класс слишком маленький или делает слишком мало
Следующий пример это flux_client в директории services, класс FluxClient сам по себе достаточно маленький и не делает каких-то важных вещей 
чтобы полноценно оправдать добавленную абстракцию. Класс действует исключительно как прокси, без дополнительного функционала по логированию или валидации.
В целом можно заменить этот класс на единственный метод без какой-либо потери функционала, от обертки логики в класс - дополнительной ценности не появляется.

```python
class AbstractFluxClient(typing.Protocol):
    async def push(
        self,
        oci: str,
        path: str,
        revision: str,
        creds: str,
    ) -> typing.Any:
        return await self._push(oci, path, revision, creds)

    async def _push(
        self,
        oci: str,
        path: str,
        revision: str,
        creds: str,
    ) -> typing.Any:
        raise NotImplementedError


class FluxClient(AbstractFluxClient):
    def __init__(self) -> None:
        self.command = (
            "flux push artifact {oci} "
            '--path="{path}" '
            '--source="$CI_COMMIT_REF_NAME:$CI_COMMIT_SHA" '
            '--revision="{revision}" '
            '--creds="{creds}"'
        )

    async def _push(
        self,
        oci: str,
        path: str,
        revision: str,
        creds: str,
    ) -> str:
        command = self.command.format(
            oci=oci,
            path=path,
            revision=revision,
            creds=creds,
        )

        proc = await asyncio.create_subprocess_shell(
            command,
            stdout=asyncio.subprocess.PIPE,
            stderr=asyncio.subprocess.PIPE,
        )

        stdout, stderr = await proc.communicate()

        if stderr and ARTIFACT_PUSHED not in stderr:
            raise exceptions.FluxPushError(
                f"Couldn't push bundle, err: {stderr}"
            )

        extracted_hash = extract_sha256_hash(stderr)

        if not extracted_hash:
            raise exceptions.FluxPushError(
                f"Couldn't extract hash from bundle, err: {stderr}"
            )

        return extracted_hash
```


### 3. В классе есть метод, который выглядит более подходящим для другого класса
В текущем примере мы рассмотрим тот же класс который был представлен в первом пункте (класс слишком большой...), в этом классе можно обнаружить 
метод compare который возвращает diff между состанием проекта в двух произвольных ветках на момент совершения запроса. Однако данная реализация 
может быть воспринята как попытка упростить метод с целью поместить его в GitLabProjectAdapter, более логично выделить под эту логику отдельный 
сервис реализация которого может меняться и где состояние проекта пользователь может получать отдельно - будь то состаяние ветки на текущий момент времени или
более детализированный стейт по хэшу коммита к примеру.

```python
class GitLabProjectAdapter:

    def __init__(
        self,
        base_url: str = str(settings.gitlab.base_url),
        client: AbstractHttpClient = dependency_injector.wiring.Provide[
            HttpClientContainer.http_client
        ],
        limited_client: AbstractHttpClient = dependency_injector.wiring.Provide[
            LimitedHttpClientContainer.http_client
        ],
    ) -> None:
        self.__base_url = base_url
        self.__graphql_url: str = str(settings.gitlab.graphql_url)
        self.__headers = {
            "PRIVATE-TOKEN": settings.gitlab.access_token,
        }
        self.__client = client
        self.__limited_client = limited_client

    async def compare(  # <------ Suspicion method is here
        self,
        project_id: int,
        from_branch: str,
        to_branch: str,
    ) -> typing.Any:
        ...

    async def get_project_groups(
        self,
        project_id: int,
    ) -> list[model.Group]:
        ...

    async def get_project_push_rules(
        self,
        project_id: int,
    ) -> model.PushRule | None:
        ...

    async def get_project_branches(
        self,
        project_id: int,
    ) -> list[dict[str, Any]]:
        ...

    async def get_project_merge_requests(
        self,
        project_id: int,
    ) -> list[dict]:
        ...

    async def get_project_default_branch(
        self,
        project_id: int,
    ) -> str:
        ...

    async def get_project_merge_request_commits(
        self,
        project_id: int,
        mr_iid: int,
    ) -> Any:
        ...

    async def get_project_branch_commits(
        self,
        project_id: int,
        branch_name: str,
    ) -> list[dict]:
        ...

    async def get_project_merge_request_changes(
        self,
        project_id: int,
        mr_iid: int,
    ) -> Any:
        ...

    async def get_project_freeze_periods(
        self,
        project_id: int,
    ) -> Any:
        ...

    async def get_projects_with_mm_integration(
        self,
    ) -> list[int]:
        ...

    async def get_project_environments(
        self,
        project_id: int,
    ) -> list[dict]:
        ...

    async def get_project_visibility(
        self,
        project_path: str,
    ) -> str | None:
        ...

    async def get_repository_filenames(
        self,
        project_path: str,
    ) -> list[str]:
        ...

    async def get_project_last_activity_at(
        self,
        project_path: str,
    ) -> datetime | None:
        ...

    async def get_project_releases_tag_names(
        self,
        project_path: str,
    ) -> list[str]:
        ...

    async def get_project_topics(
        self,
        project_path: str,
    ) -> list[str]:
        ...

    async def get_project_description(
        self,
        project_path: str,
    ) -> str | None:
        ...

    async def get_project_pipelines(
        self,
        project_path: str,
        updated_after: str | None = None,
        status: str | None = None,
    ) -> list[model.PipelineEntity]:
        ...

    async def get_merge_requests_enabled_setting(
        self,
        project_id: int,
    ) -> bool:
        ...

    async def get_file_characters_count(
        self,
        project_id: int,
        file_name: str,
    ) -> int | None:
        ...

    async def get_issues_enabled_setting(
        self,
        project_id: int,
    ) -> bool:
        ...

    async def get_star_count(
        self,
        project_id: int,
    ) -> int:
        ...

    async def get_forks_count(
        self,
        project_id: int,
    ) -> int:
        ...

    async def get_project_issues_authors(
        self,
        project_path: str,
    ) -> set[str]:
        ...

    async def get_project_templates(
        self,
        project_id: int,
        template_type: str,
    ) -> list[str] | None:
        ...

    async def get_project_languages(
        self,
        project_id: int,
    ) -> dict:
        ...

    async def get_project_last_deployments(
        self,
        project_id: int,
        finished_after: date,
        finished_before: date,
        environment: str | None = None,
        per_page: int | None = None,
    ) -> list[model.Deployment]:
        ...

    async def get_project_production_deployments(
        self,
        project_id: int,
        finished_after: date,
        finished_before: date,
        environment: list[model.Environment],
    ) -> list[model.Deployment]:
        ...
```

### 4. Класс хранит данные, которые загоняются в него в множестве разных мест в программе
В этом примере можно увидеть достаточно распространненый пример с инвентарем игрока, где инвентарь представляется один большим объектом, 
который, к тому же, может менять любой участок программы напрямую. Давать возможность напрямую редактировать переменные такого объеекта - не лучшая идея, 
также сам такой большой объект лучше попытаться декомпозировать на что-то более атомизированное - отдельно кошелек игрока (хранящий золото),
отдельно ресурсы и т.д. + не забыть про выделение публичного интерфейса.

```python
@dataclass
class Inventory:
    items: dict[str, int] = ...
    gold: int = ...
    resources: dict[str, int] = ...
```

### 5. Класс зависит от деталей реализации других классов 
Хороший пример для демонстрации вреда от зависимости от деталей реализации другого класса, это когда внутри класса начинают появляться проверки
на устройство какого-то внешнего класса с которым приходится работать. На примере ниже показано какие неявные инструкции могут быть порождены
зависимостью от типа лайфтайма объекта.

```python
class OrderService:
    def __init__(self):
        self.payment_client = PaymentClient()

    def close(self):
        # PaymentClient - Singleton, не закрывать сессию здесь никогда
        pass
```

### 6. Приведение типов вниз по иерархии 
Здесь представлен небольшой отрывок из программы где игроку позволено эволюционировать главного персонажа от простейшей клетки
до многоклеточного существа (человек, растение и т.д.). При приведении типов вниз по иерархии возможна ситуация как в примере ниже -
возрастает связанность кода, появляется больше проверок на определение типа, распространяется знание о внутреннем устройстве каждого типа (иначе другие компоненты могут не знать какие методы могут вызвать для каждого типа)  

```python
class Plant(Organism):
    def photosynthesize(self) -> None:
        ...

class Animal(Organism):
    def move(self) -> None:
        ...

class Mammal(Animal):
    def feed_milk(self) -> None:
        ...

class Human(Mammal):
    def use_tools(self) -> None:
        ...

    
class EvolutionService:
    def process_day(self, organism: Organism) -> None:
        organism.live()

        if isinstance(organism, Plant):
            organism.photosynthesize()

        elif isinstance(organism, Reptile):
            organism.regulate_body_temperature()

        elif isinstance(organism, Mammal):
            organism.maintain_body_temperature()
            organism.feed_offspring()

        if isinstance(organism, Human):
            organism.use_tools()
```

### 7. Когда создаётся класс-наследник для какого-то класса, приходится создавать классы-наследники и для некоторых других классов
В этом примере пытаются через миксин реализовать апгрейд классов услуг (TaxiService и FoodDeliveryService) до уровня VIP, однако такой подход заставляет
при добавлении нового уровня - заставляет также создавать новый класс для каждого услуги каждого типа. Получается заложенный дизайн не оставляет 
выбора кроме как плодить вручную новую классы при изменении логики услуг или их уровня. В подобных ситуациях лучше использовать композицию.

```python
class TaxiService(Service):
    async def start(self):
        ...

    async def stop(self):
        ...

    async def change_destination(self, destination: dict):
        ...
    
    async def change_car_model(self, car_model: dict):
        ...

class FoodDeliveryService(Service):
    async def start(self):
        ...

    async def stop(self):
        ...

    async def change_food(self, food: dict):
        ...


class VIPServiceMixin:
    async def assign_personal_manager(self):
        ...

    async def enable_priority_processing(self):
        ...

    async def apply_vip_support(self):
        ...
```

### 8. Дочерние классы не используют методы и атрибуты родительских классов, или переопределяют родительские методы
На примере кода из шестого примера также возможно продемонстрировать ситуацию когда атрибуты родительских классов бессмыслено кочуют в 
классы-наследники. Из-за того что классы Plant и Animal являются прямыми наследниками класса Organism - они унаследуют аттрибуты описывающие устройства
клетки, и бесполезные для дальнейших стадий. Поэтому стоит с осторожностью слепо перекладывать концепты реального мира в наследование, даже если они 
казалось бы являются репрезентацией друг друга. Лучше ориентироваться скорее на поведенческое отношение.

```python
class Organism:
    def __init__(self):
        self.flagella = 2
        self.membrane = True
        self.can_divide = True

        
class Plant(Organism):
    def photosynthesize(self) -> None:
        ...

class Animal(Organism):
    def move(self) -> None:
        ...

class Mammal(Animal):
    def feed_milk(self) -> None:
        ...

class Human(Mammal):
    def use_tools(self) -> None:
        ...

    
class EvolutionService:
    def process_day(self, organism: Organism) -> None:
        organism.live()

        if isinstance(organism, Plant):
            organism.photosynthesize()

        elif isinstance(organism, Reptile):
            organism.regulate_body_temperature()

        elif isinstance(organism, Mammal):
            organism.maintain_body_temperature()
            organism.feed_offspring()

        if isinstance(organism, Human):
            organism.use_tools()
```

## Уровень приложения

### 9. Одна модификация требует внесения изменений в несколько классов.
Большая ошибка явно оставлять в проекте множество дублирующих/промежутчных структур данных которые так или иначе будут задействованы 
при изменении основной структуры данных. Ниже можно на примере рассмотреть как избыточная архитектура с множественными "перекладываниями" значений
заставляет разработчика дублировать поле вплоть до 8 раз не считая тестов (количество аттрибутов у сущности answer уменшено с 34 до 5). В примере ниже, одна и та структура "answer" так или иначе 
"переклыдывается" с ручным указанием аттрибутов в следующих файла - command_handler.py, handler.py, model.py (мапперы), answer_repository.py, commands.py
\+ orm.py и schemata.py.

```python
def mapping_data(data: dict):
    ...


@inject
async def broker_handle(
    data: dict,
    bus: MessageBus = Provide[MessageBusContainer.message_bus],
) -> bool | None:
    survey_event_cmd = commands.UpsertSurveyEvent(
        survey_id=data.get("survey", {}).get("id"),
        event_payload=data,
        answer_ids=[
            question.get("answer", {}).get("id")
            for question in data.get("event", {}).get("questions", [])
        ],
    )

    await bus.handle(survey_event_cmd)

    for result in mapping_data(data):
        message = model.Answer.from_dict(result)

        category = message.category

        cmd = commands.CreateAnswer(
            survey_id=message.survey_id,
            question_id=message.question_id,
            value=message.value,
            login=message.login,
            category=category,
            answer_timestamp=message.answer_timestamp,
        )

        result = await bus.handle(cmd)
        logger.info(f"Data {result} was saved")

    return True


import typing
from dataclasses import dataclass, field
from datetime import datetime


@dataclass
class Answer:
    survey_id: int
    question_id: int
    value: str | float | None
    login: str
    category: str | None
    answer_timestamp: datetime

    id: int | None = field(default=None, init=False)

    @classmethod
    def from_dict(cls, data: dict) -> "Answer":
        return cls(
            survey_id=data["survey_id"],
            question_id=data["question_id"],
            value=data.get("value"),
            login=data["login"],
            category=data.get("category"),
            answer_timestamp=data["answer_timestamp"],
            ...
        )

    def set_id(self, value: int) -> None:
        self.id = value

    def __hash__(self) -> int:
        return hash(self.id)

    def __eq__(self, other: object) -> bool:
        if isinstance(other, Answer):
            return self.id == other.id

        return NotImplemented


@dataclass
class SurveyEvent:
    survey_id: int
    event_payload: typing.Mapping[str, typing.Any]
    answer_ids: list[str]


"""Command handlers."""

import typing


async def create_answer(
    cmd: commands.CreateAnswer,
    uow: AbstractUnitOfWork,
) -> str | int | None:
    async with uow:
        answer = Answer(
            survey_id=cmd.survey_id,
            question_id=cmd.question_id,
            value=cmd.value,
            login=cmd.login,
            category=cmd.category,
            answer_timestamp=cmd.answer_timestamp,
            ...
        )

        result = await uow.answer_repository.add(answer)
        await uow.commit()

        return result


async def update_answer(
    cmd: commands.UpdateAnswer,
    uow: AbstractUnitOfWork,
) -> None:
    async with uow:
        answer = Answer(
            survey_id=cmd.survey_id,
            question_id=cmd.question_id,
            value=cmd.value,
            login=cmd.login,
            category=cmd.category,
            answer_timestamp=cmd.answer_timestamp,
            ...
        )

        answer.set_id(cmd.id)

        await uow.answer_repository.update(answer)
        await uow.commit()


async def delete_answer(
    cmd: commands.DeleteAnswer,
    uow: AbstractUnitOfWork,
) -> None:
    async with uow:
        await uow.answer_repository.delete_answer(cmd.id)
        await uow.commit()


async def upsert_survey_event(
    cmd: commands.UpsertSurveyEvent,
    uow: AbstractUnitOfWork,
) -> None:
    async with uow:
        response = SurveyEvent(
            survey_id=cmd.survey_id,
            event_payload=cmd.event_payload,
            answer_ids=cmd.answer_ids,
            ...
        )

        await uow.survey_event_repository.upsert_survey_event(response)
        await uow.commit()


COMMAND_HANDLERS: dict[
    type[commands.Command],
    typing.Callable[[commands.Command, AbstractUnitOfWork], typing.Any],
] = {
    commands.CreateAnswer: create_answer,
    commands.UpdateAnswer: update_answer,
    commands.DeleteAnswer: delete_answer,
    commands.UpsertSurveyEvent: upsert_survey_event,
}
```

### 10. Использование сложных паттернов проектирования там, где можно использовать более простой и незамысловатый дизайн
Использую тот же самый код что и для примера девять (одна модификация требует внесения изменений в несколько классов), рассмотрим то почему 
паттерн Commands в этом примере привносит только сложность и служит лишь переносчиком данных через слои. По итогу использования
паттерна Commands - не добавляется больше полезной гибкости (по крайней мере она не используется в коде), но при это кратно растет количество 
классов. Здесь было бы достаточно паттерна MessageBus с обработкой данных в отдельных сервисах, для проекта с одной/двумя очередями
текущий сетап проекта можно посчитать оверинжинирингом с аффектом в когнитивнюую сложность.

```python
def mapping_data(data: dict):
    ...


@inject
async def broker_handle(
    data: dict,
    bus: MessageBus = Provide[MessageBusContainer.message_bus],
) -> bool | None:
    survey_event_cmd = commands.UpsertSurveyEvent(
        survey_id=data.get("survey", {}).get("id"),
        event_payload=data,
        answer_ids=[
            question.get("answer", {}).get("id")
            for question in data.get("event", {}).get("questions", [])
        ],
    )

    await bus.handle(survey_event_cmd)

    for result in mapping_data(data):
        message = model.Answer.from_dict(result)

        category = message.category

        cmd = commands.CreateAnswer(
            survey_id=message.survey_id,
            question_id=message.question_id,
            value=message.value,
            login=message.login,
            category=category,
            answer_timestamp=message.answer_timestamp,
        )

        result = await bus.handle(cmd)
        logger.info(f"Data {result} was saved")

    return True


import typing
from dataclasses import dataclass, field
from datetime import datetime


@dataclass
class Answer:
    survey_id: int
    question_id: int
    value: str | float | None
    login: str
    category: str | None
    answer_timestamp: datetime

    id: int | None = field(default=None, init=False)

    @classmethod
    def from_dict(cls, data: dict) -> "Answer":
        return cls(
            survey_id=data["survey_id"],
            question_id=data["question_id"],
            value=data.get("value"),
            login=data["login"],
            category=data.get("category"),
            answer_timestamp=data["answer_timestamp"],
            ...
        )

    def set_id(self, value: int) -> None:
        self.id = value

    def __hash__(self) -> int:
        return hash(self.id)

    def __eq__(self, other: object) -> bool:
        if isinstance(other, Answer):
            return self.id == other.id

        return NotImplemented


@dataclass
class SurveyEvent:
    survey_id: int
    event_payload: typing.Mapping[str, typing.Any]
    answer_ids: list[str]


"""Command handlers."""

import typing


async def create_answer(
    cmd: commands.CreateAnswer,
    uow: AbstractUnitOfWork,
) -> str | int | None:
    async with uow:
        answer = Answer(
            survey_id=cmd.survey_id,
            question_id=cmd.question_id,
            value=cmd.value,
            login=cmd.login,
            category=cmd.category,
            answer_timestamp=cmd.answer_timestamp,
            ...
        )

        result = await uow.answer_repository.add(answer)
        await uow.commit()

        return result


async def update_answer(
    cmd: commands.UpdateAnswer,
    uow: AbstractUnitOfWork,
) -> None:
    async with uow:
        answer = Answer(
            survey_id=cmd.survey_id,
            question_id=cmd.question_id,
            value=cmd.value,
            login=cmd.login,
            category=cmd.category,
            answer_timestamp=cmd.answer_timestamp,
            ...
        )

        answer.set_id(cmd.id)

        await uow.answer_repository.update(answer)
        await uow.commit()


async def delete_answer(
    cmd: commands.DeleteAnswer,
    uow: AbstractUnitOfWork,
) -> None:
    async with uow:
        await uow.answer_repository.delete_answer(cmd.id)
        await uow.commit()


async def upsert_survey_event(
    cmd: commands.UpsertSurveyEvent,
    uow: AbstractUnitOfWork,
) -> None:
    async with uow:
        response = SurveyEvent(
            survey_id=cmd.survey_id,
            event_payload=cmd.event_payload,
            answer_ids=cmd.answer_ids,
            ...
        )

        await uow.survey_event_repository.upsert_survey_event(response)
        await uow.commit()


COMMAND_HANDLERS: dict[
    type[commands.Command],
    typing.Callable[[commands.Command, AbstractUnitOfWork], typing.Any],
] = {
    commands.CreateAnswer: create_answer,
    commands.UpdateAnswer: update_answer,
    commands.DeleteAnswer: delete_answer,
    commands.UpsertSurveyEvent: upsert_survey_event,
}
```