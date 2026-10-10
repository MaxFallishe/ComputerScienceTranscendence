# Правила простого проектного дизайна 
Очень полезно задумываться о защите своего проекта от ошибок архитектурного дизайна, заранее 
представлять насколько удобно будет его развивать и сопровождать. Особенно актуально это становится
после встречи с сомнительными архитектурными решениями на практике, в таких ситуациях бывает сложно
сразу понять что дело именно в дизайне проекта, а не в ваших навыках или подходе. Ниже попробуем разобрать три
правила которые могут помочь избегать ситуаций когда заложенная логика проекта по реализации
возложенных на него задач принуждает разработчиков к сомнительному стилю, попутно накладывая на них
множество ненужных ограничений. Рассматриваться будут три правила по улучшению дизайна класса с точки 
зрения защиты от ошибок - избавление от точек генерации исключений, отказ от дефолтных конструкторов без параметров, 
избегание чрезмерного использования примитивных типов. В каждом разделе будет описана практика 
с примерами кода "до" и "после" на python. 

## Избавляться от точек генерации исключений
Наверное основное по своей полезности правило из представленных трёх, здесь мы пытаемся запретить
потенциальное ошибочное поведение на уровне интерфейсе класса. При проектировании интерфейса, следует 
тщатально определить множество возможностей и их комбинации, которые дают публичные методы - и как
итог предусмотрельно сделать невозможными нецелевые варианты использования класса приводящие к исключениям.

### Пример №1
Редактируем класс по работе с MCP файлами через явное указание группы потребителей для которых можно выполнить
изменение видимости файла. В первом случае выдается исключаение при нежелательном исходе, в то время как во втором дизайн 
не подразумевает выход за рамки заложенного множества сущностей.

#### Существующая версия кода
```python
class MCPFileService:
    def hide_file(self, file_id: str, audience: Audience) -> None:
        if audience == Audience.ADMIN:
            raise ValueError("Admins must see all non-deleted files")

        self.repository.hide(file_id, audience)
        
    ...
```

#### Обновленная версия кода
```python
class MCPFileService:
    def hide_file(
        self,
        file_id: str,
        audience: Literal[Audience.USER],
    ) -> None:
        self.repository.hide(file_id, audience)
        
    ...
```

### Пример №2
Убираем аргумент timestamp на уровне метода по записи данных датчика, так как не имеет смысла заставлять разработчика 
вручную реализовывать механизм получения таймстемпа, когда логика приложения подразумевает единообразное вычисление таймстемпа
при записи в базу данных без дополнительных вмешательств.

#### Существующая версия кода
```python
class SensorDataService:
    def write(
        self,
        sensor_id: str,
        value: float,
        timestamp: datetime,
    ) -> None:
        self.repository.save(sensor_id, value, timestamp)
    
    ...
```

#### Обновленная версия кода
```python
class SensorDataService:
    def write(self, sensor_id: str, value: float) -> None:
        self.repository.save(
            sensor_id,
            value,
            datetime.now(timezone.utc),
        )
    
    ...
```

## Отказаться от дефолтных конструкторов без параметров
Отказавшись от дефолтных конструкторов без параметров появляется возможность автоматически 
избежать проблем с неинициализированными внутренними объектами класса к которым класс будет пытаться обратиться.
Кроме того данная концепция хорошо ложится в парадигму "dependency injection" где в контейнерах (containers) 
(на примере библиотеки dependency_injector) явно указывается класс с какими параметрами является составляющей зависимостью
для другого класса (например для класса сервиса).

### Пример №1
Пример из проекта где существует множество метрик команд и множество практик которые просчитываются на основе метрик.
В разнице между существующей версией кодаи обновленной можно видеть ереход от неявного к явному объявлению нужных метрик в конструкторе практики, во избежание ситуаций
в которых разработчик забудет/не передаст метрики для расчета практики.

#### Существующая версия кода
```python
class DeliveryPractice:
    def __init__(self):
        self.metrics = {}

    def add_metric(self, name: str, metric: Metric):
        self.metrics[name] = metric

    def calculate(self, data):
        return {
            name: metric.calculate(data)
            for name, metric in self.metrics.items()
        }
```

#### Обновленная версия кода
```python
class DeliveryPractice:
    def __init__(
        self,
        lead_time: Metric,
        deploy_frequency: Metric,
        failure_rate: Metric,
    ):
        self.metrics = {
            "lead_time": lead_time,
            "deploy_frequency": deploy_frequency,
            "failure_rate": failure_rate,
        }

    def calculate(self, data):
        return {
            name: metric.calculate(data)
            for name, metric in self.metrics.items()
        }
```

### Пример №2
Переход от скрытых зависимостей внутри класса (конкретые реализации) к явным зависимостям внутри 
контейнера на базе dependency_injector.containers.DeclarativeContainer. Самое важное - заметить что в
обновленной версии внутри конструктора PracticeService появились `practice_repo: PracticeRepository` и `metric_repo: MetricRepository`.

#### Существующая версия кода
```python
class PracticeService:
    def __init__(self):
        self.practice_repo = PracticeRepository()
        self.metric_repo = MetricRepository()

    def get_metrics(self, practice_id: int):
        practice = self.practice_repo.get(practice_id)
        return self.metric_repo.get_by_practice(practice.id)


service = PracticeService()
```

#### Обновленная версия кода
```python
from dependency_injector import containers, providers


class PracticeService:
    def __init__(
        self,
        practice_repo: PracticeRepository,
        metric_repo: MetricRepository,
    ):
        self.practice_repo = practice_repo
        self.metric_repo = metric_repo

    def get_metrics(self, practice_id: int):
        practice = self.practice_repo.get(practice_id)
        return self.metric_repo.get_by_practice(practice.id)


class Container(containers.DeclarativeContainer):
    practice_repo = providers.Factory(PracticeRepository)
    metric_repo = providers.Factory(MetricRepository)

    practice_service = providers.Factory(
        PracticeService,
        practice_repo=practice_repo,
        metric_repo=metric_repo,
    )


container = Container()
service = container.practice_service()
```

## Избегать увлечения примитивными типами данных
Простой, но эффективный способ сузить множество данных с которым придется работать классу - это разработвать
прикладную систему типов на смысловом уровне моделирующую предметную область.

### Пример №1
В примере ниже производим замену базового типа `str` для поля модели контроллера на тип `SensorModel(StrEnum)`, 
StrEnum остается совместим со строками (`str`), однако отрезаем возможность опечатке или поврежденным данным с датчиком 
добавить необъяснимую модель контроллера (хороший observability сразу оповестит о возникших проблемах с неучтенными моделями).

#### Существующая версия кода
```python
class SensorService:
    def register(self, sensor_id: str, model: str) -> None:
        self.repository.create(sensor_id, model)
```

#### Обновленная версия кода
```python
class SensorModel(StrEnum):
    ESP32 = "ESP32"
    ESP8266 = "ESP8266"
    STM32 = "STM32"


class SensorService:
    def register(
        self,
        sensor_id: str,
        model: SensorModel,
    ) -> None:
        self.repository.create(sensor_id, model)
```

### Пример №2
Моделируем специальный тип для работы с данными где все необходимые проверки сразу же зашиты на уровне типа,
из-за чего отпадает нужда в проверках на каждом этапе преобразования/изменения значения.

#### Существующая версия кода
```python
class USDTPaymentService:
    def create_payment(
        self,
        wallet_id: str,
        amount: int,
    ) -> None:
        if amount < 0:
            raise ValueError("Payment amount cannot be negative")

        self.repository.save(wallet_id, amount)
```

#### Обновленная версия кода
```python
@dataclass(frozen=True)
class NonNegativeInt:
    value: intчтобы избежать 

    def __post_init__(self):
        if type(self.value) is not int or self.value < 0:
            raise ValueError("Value must be a non-negative integer")


class USDTPaymentService:
    def create_payment(
        self,
        wallet_id: str,
        amount: NonNegativeInt,
    ) -> None:
        self.repository.save(wallet_id, amount.value)


service.create_payment(
    wallet_id="wallet-123",
    amount=NonNegativeInt(500),
)
```

# Выводы
Систему рарабатываемая не только для выполнения целевых задач, но и приспособленная к процессу её эволюции 
с точки зрения разработчиков - это то что хотел бы найти каждый разработчик в очередном проекте в качестве фундаментально 
заложенного принципа на уровне архитектуры системы. Все пункты из текущей заметки можно объединить с точки зрения
уменьшения потенциально незапланированных и ненужных исходов которые могут случиться в программе, что в следствии заставит отдельно
подсвечивать в документации или устно особые правила которые следует разработчикам держать в голове во избежание определенного 
множества проблем. Гораздо более правильно заранее думать об архитектуре системы с точки зрения облегчения её сопроваждения, 
держа её настолько функциальной насколько этого требуют реалии эксплуатации, не раздувая множество состояний данных внутри, но при этом
гарантируя системе возможность развиваться и расширяться в своем функционале.