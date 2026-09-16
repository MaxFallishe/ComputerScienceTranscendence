from operator import ifloordiv

# Божественная линия кода
## Введение
Иногда в кодовой базе можно встретить строки которые буквально содержат в себе гораздо большее количество действий чем полагается, 
внутри этих строк часто происходят хитрые промежуточные вычисления или несколько вызовов функций. Такие случаи можно рассмотреть 
как нарушение SRP (Single Responcibility Principle) в рамках одной физической строки кода. В некоторых случаях ситуации с такими длинными и сложными
строками усугбляется со временем, когда новые правки вносятся внутрь этой строки заместо выноса логики в отдельные переменные.

## Посмотрим на практике
В данном разделе рассмотрим 6 примеров, когда строки python кода в продакшен коде получились сложнее чем следовало бы. 
Перечисленные ниже примеры подбирались с целью показать достаточно уникальные причины которые заставили разработчиков вместить значимый объем кода в одну строку.

### Пример №1
В текущем примере самая громоздкая конструкция с точки зрения размере - формирование списка `logins_to_enrich`. Легко представить, что изначально `logins_to_enrich` был более компактным, 
однако на каком-то этапе добавилась необходимость добавить условие `if login` и условие `(not user.first_name or not user.last_name)` из-за чего списковый comprehension сделался больше чем предполагалось.
Разложив логику в цикл с `continue` стопперами, получилось снизить когнитивную сложность кода, теперь он явно ситается сверху вниз + сделали небольшую оптимизацию через замену списка на множество.  

#### Вариант "ДО"

```python
...

async def _enrich_users_with_home_employee_portal(
    self,
    users_by_login: dict[str, InsiderUserInfo],
) -> dict[str, InsiderUserInfo]:
    logins_to_enrich = [
        login for login, user in users_by_login.items() if login and (not user.first_name or not user.last_name)  # <----- переусложненная строка
    ]

    unique_logins_to_enrich = list(dict.fromkeys(logins_to_enrich))

    if not unique_logins_to_enrich:
        return users_by_login

    portal_users_by_login = await self._profile_adapter.get_home_employee_portal_users_by_logins(
        unique_logins_to_enrich,
    )

    for login, portal_user in portal_users_by_login.items():
        current_user = users_by_login[login]
...
```

#### Вариант "ПОСЛЕ"

```python
...

async def _enrich_users_with_home_employee_portal(
    self,
    users_by_login: dict[str, InsiderUserInfo],
) -> dict[str, InsiderUserInfo]:
    unique_logins_to_enrich: set = set()
    
    for login, user in users_by_login.items():    # <----- переусложненная строка разложена в цикл
        if not login:
            continue
        if user.first_name and user.last_name:
            continue
        unique_logins_to_enrich.add(login)

    if not unique_logins_to_enrich:
        return users_by_login
    
    portal_users_by_login = await self._profile_adapter.get_home_employee_portal_users_by_logins(
        list(unique_logins_to_enrich),
    )

    for login, portal_user in portal_users_by_login.items():
        current_user = users_by_login[login]
...
```

### Пример №2
В текущем примере представлена проблема парсинга json ответов из словаря от внешних API, из-за чего часто используется паттерн с последовательным вызовом конструкции`.get() or {}`. 
Такую конструкцию можно заменить использую синтаксис компонента `match` (как показано в блоке "ПОСЛЕ"). В случае потенциально ещё более длинной цепочки вызовов, лучше использовать модели pydantic.

#### Вариант "ДО"

```python
...

search_response = response.json()

search_results = (((search_response.get("data") or {}).get("searchServiceSearchByWords") or {}).get("portalUsers") or {}).get("searchResults") or []    # <----- переусложненная строка 

users: list[InsiderUserInfo] = []
seen_logins: set[str] = set()

...
```

#### Вариант "ПОСЛЕ"

```python
...

search_response = response.json()

match search_results:                      # <----- заменено на match конструкцию
    case {"data":
          {"searchServiceSearchByWords": 
           {"portalUsers": 
            {"searchResults": search_results}
            }
           }
          }:
        pass
    case _:
        search_results = []        

users: list[InsiderUserInfo] = []
seen_logins: set[str] = set()

...
```

### Пример №3
В этом примере можно увидеть как логика определяющая значение `extra_filters` была помещена внутрь вызова функции в качестве параметра. С одной стороны сложно назвать
эту строку очень длинной, с другой нет ничего сложного чтобы вынести эту логику в формат просто кода, легко-читающийся сверху вниз, без необходимости переносить внимание слева направо 
в исходном варианте.

#### Вариант "ДО"

```python
...
if include_components:
    filters.append("AND ci.components && :include_components")
    params["include_components"] = include_components

if exclude_components:
    filters.append("AND NOT (ci.components && :exclude_components)")
    params["exclude_components"] = exclude_components

sql = TOTAL_ISSUES_SQL.format(extra_filters=" " + " ".join(filters) if filters else "")    # <----- переусложненная строка

try:
    result = await self.session.execute(text(sql), params=params)
except exc.OperationalError as ex:
    raise exceptions.DatabaseConnectionError from ex

value = result.scalar_one_or_none()
return int(value or 0)
...
```

#### Вариант "ПОСЛЕ"

```python
...
if include_components:
    filters.append("AND ci.components && :include_components")
    params["include_components"] = include_components

if exclude_components:
    filters.append("AND NOT (ci.components && :exclude_components)")
    params["exclude_components"] = exclude_components

extra_filters = ""                                              # <----- переусложненная строка разложена на простые конструкции
if filters:
    extra_filters = " " + " ".join(filters)
sql = TOTAL_ISSUES_SQL.format(extra_filters=extra_filters)

try:
    result = await self.session.execute(text(sql), params=params)
except exc.OperationalError as ex:
    raise exceptions.DatabaseConnectionError from ex

value = result.scalar_one_or_none()
return int(value or 0)
...
```


### Пример №4
Очередной пример относительно небольшой строки содержащей в себе несколько вложенных функций из-за чего её может быть сложно прочитать. 
Разложив её на основные операции получилось сделать её более легкой для понимания.

#### Вариант "ДО"

```python
...

def normalize_logins(logins: list[str]) -> list[str]:
    """Strip logins, remove empty values, and deduplicate them while preserving order."""
    return list(
        dict.fromkeys(login.strip() for login in logins if login and login.strip()),
    )

...
```

#### Вариант "ПОСЛЕ"

```python
...

def normalize_logins(logins: list[str]) -> list[str]:
    """Strip logins, remove empty values, and deduplicate them while preserving order."""
    normalized_logins = []
    for login in logins:
        if not login:
            continue
        if not (stripped_logins := login.strip()):
            continue
        normalized_logins += stripped_logins
        
    return normalized_logins

...
```


### Пример №5
Данный пример является достаточно спорным, так как одна строка превращается в почти в 20 строк. Здесь следует ориентироваться на существующий стиль проекта, в блоке "ПОСЛЕ"
пример того как могла бы выглядеть логика без спискового comrehension. Также можно заметить что это строка глобально выполняет одну функцию - извлечение ответов из вложенной структуры в плоскую, 
поэтому сложно сказать строка делает слишком разрозненный вид работы. 

#### Вариант "ДО"

```python
...
@inject
async def broker_handle(
    data: dict,
    bus: MessageBus = Provide[MessageBusContainer.message_bus],
) -> bool | None:
    survey_event_cmd = commands.UpsertSurveyEvent(
        survey_id=data.get("event", {}).get("id"),
        event_payload=data,
        answer_ids=[question.get("answer", {}).get("id") for question in data.get("event", {}).get("questions", [])],
    )

    await bus.handle(survey_event_cmd)

    for result in mapping_data(data):
...
```

#### Вариант "ПОСЛЕ"

```python
...

@inject
async def broker_handle(
    data: dict,
    bus: MessageBus = Provide[MessageBusContainer.message_bus],
) -> bool | None:
    match data:
        case {
            "event": {
                "questions": questions
            }
        }:
            pass
        case _:
            questions = []
        
    answer_ids = []
    
    for question in questions:
        match question:
            case {"answer": {"id": answer_id}}:
                answer_ids.append(answer_id)
            case _:
                answer_ids.append(None)
        
    survey_event_cmd = commands.UpsertSurveyEvent(
        survey_id=data.get("event", {}).get("id"),
        event_payload=data,
        answer_ids=[question.get("answer", {}).get("id") for question in data.get("event", {}).get("questions", [])],
    )

    await bus.handle(survey_event_cmd)

    for result in mapping_data(data):
```


### Пример №6
В это примере можно увидеть излишнюю длину у параметра `properties` в мапперах, нельзя однозначно сказать что эти строки обязательно должны быть изменены,
однако если представить что будет необходимо указать дополнительные параметры, контекст вызовов действительно может вырасти в слишком большую конструкцию.

#### Вариант "ДО"

```python
...

def start_mappers() -> None:
    mapper_registry.map_imperatively(
        model.RequestOC,
        orm.Request.__table__,
        properties={
            "response": sqlalchemy.orm.relationship(RequestResponseOC, back_populates="request", uselist=False)
        },
    )

    mapper_registry.map_imperatively(
        model.RequestResponseOC,
        orm.RequestResponse.__table__,
        properties={"request": sqlalchemy.orm.relationship(RequestOC, back_populates="response", uselist=False)},
    )
...
```
#### Вариант "ПОСЛЕ"

```python
...

def start_mappers() -> None:
    response_property = sqlalchemy.orm.relationship(RequestResponseOC, back_populates="request", uselist=False)
    request_property = sqlalchemy.orm.relationship(RequestOC, back_populates="response", uselist=False)
    
    mapper_registry.map_imperatively(
        model.RequestOC,
        orm.Request.__table__,
        properties={
            "response": response_property
        },
    )
    
    mapper_registry.map_imperatively(
        model.RequestResponseOC,
        orm.RequestResponse.__table__,
        properties={
            "request": request_property
        },
    )
...
```


## Вывод
В продакшн коде не рекдко можно встретить излишне усложненные строки, и от них конечно нужно избавляться, так как чаще всего их наличие ничем не обосновано. 
Причем также стоит четко понимать что под длинными строками имеются те, которые действительно чаще всего вмещаются в одну строку и плохо переносятся на несколько строк. 
К примеру, длинные конструкции sqlalchemy тоже можно считать за одну строку, но это не корректно (хоть и излишне большие блоки запроса тоже не всегда уместны). Важно ставить 
в приоритет тех кто будет читать и редактировать ваш код, а обычно это сводится к упрощению флоу кода - чтобы не приходилось слишком часто бегать глазами в разные места. 
Также важно не забывать про компактность, излишне раздувать код тоже не всегда оправдано.
