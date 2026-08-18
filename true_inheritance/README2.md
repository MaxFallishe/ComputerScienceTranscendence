from contextlib import asynccontextmanager

# Истинное наследование (продолжение)
Часть проблемы решаемые с помощью паттерна Visitor в ООП также присутствуют и в Функциональном Программировании.
Однако в функциональном программировании решение жто функции второго порядка, например когда основной функции (условный обход дерева)
передается функция предикат которая регулирует обработку листа дерева. В некотором смысле эта функция эквивалента паттерну
Visitor так как мы делегируем конкретную логику другой сущности. В ООП схожий подход реализуется через миксины, которые могут
применяться сразу к нескольким суперклассам. В идеале миксины stateless.

## Пример миксинов в Python

### Пример миксина для логирования
```python
import logging

class AppLoggingMixin:
    @property
    def logger(self) -> logging.Logger:
        return logging.getLogger(...)
    
    def log(self, message: str) -> None:
        self.logger.info(...)

class RealService(AppLoggingMixin):
    def create_entity(self, entity_details: dict):
        ...
        self.log("some log message")  # can use .log() from mixin
```

### Пример миксина для отрисовки компонента в консоли
```python
from abc import ABC, abstractmethod

class ComponentViewMixin(ABC):
    @property
    @abstractmethod
    def component_name(self) -> str:
        ...

    @abstractmethod
    def component_info(self) -> dict[str, str]:
        ...
    
    def __rich__(self) -> ...:
        rich_view = ...
        return rich_view

class RabbitMQClient(ComponentViewMixin):
    def __init__(self):
        ...

console = Console()
rabbitmq = RabbitMQ(...)

@asynccontextmanager
async def lifespan(app: FastAPI):
    ...
    console.print(rabbitmq)
    yield

app = FastAPI(lifespan=lifespan)
```

## Заключение
Миксины в python это легитимная конструкция, однако отдельного синтаксиса под них нет. Миксинов в классах может быть больше одного,
но важно понимать что их порядок вызова определяется через __mro__, соответственно порядок наследования имеет значение.
В Django миксины распространенная концепция, например для добавления view проверки, а в fastapi миксины как правило используются
не часто, особенно в сравнении с depends и class dependencies.
