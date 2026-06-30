# Увидеть ясную структуру дизайна
Чтобы прийти к принципу соответствия логического дизайна и кода его реализующего, действительно необходимо в некотором смысле забыть о текущем (уже существующем) коде, который в целом механически уже реализует необходимую логику. 
Без этого сложно заставить себя подняться на абстракцию выше чтобы понять истинный "запрос" с точки зрения дизайна, особенно на проектах с которыми работаешь на ежедневной основе.
Ниже постарался привести несколько примеров на базе кода из существующих проектов. Каждый из проектов реализует уникальную функциональность, но при этом существуют в рамках одного продукта.

## Пример №1
### Существующая версия кода
```python
"""Amqp client."""

from ...domain.exceptions import EnvValueError
from ...settings import AppSettings
from .listener import BuildingListener, BuildingListenerState


def get_building_client(state: BuildingListenerState) -> BuildingListener:
    host_list = AppSettings.broker_host.split(",")
    port_list = AppSettings.broker_port.split(",")
    if not len(host_list) == len(port_list):
        raise EnvValueError("Количество переменных host-port должны совпадать")
    BUILDING_AMQP_URL = []  # noqa: N806
    ssl = False

    for host, port in zip(host_list, port_list):
        if port == "61616":
            url = f"amqp://{host}:{port}"
        else:
            url = f"amqps://{host}:{port}"
            ssl = True
        if AppSettings.extend_params:
            url += f"?{AppSettings.extend_params}"
        BUILDING_AMQP_URL.append(url)

    return BuildingListener(
        amqp_urls=BUILDING_AMQP_URL,
        address=AppSettings.queue_address,
        queue=AppSettings.queue_name,
        user=AppSettings.broker_user,
        password=AppSettings.broker_password,
        ssl=ssl,
        state=state,
    )


"""Listen module."""

import asyncio
import json
import shlex
from dataclasses import dataclass
from datetime import UTC, datetime
from typing import Any, Final

import structlog
from proton import Event, SSLDomain  # type: ignore  # noqa: PGH003
from proton._reactor import Backoff
from proton.handlers import MessagingHandler  # type: ignore  # noqa: PGH003
from proton.reactor import DurableSubscription  # noqa: F401, RUF100

from . import handler
from ...domain.exceptions import CreateError, InconsistentMessageError
from ...settings import AppSettings

logger = structlog.get_logger("BuildingListener")
DEFAULT_HEARTBEAT_SECONDS: Final[int] = 60
LIVENESS_FAILURE_AFTER_SECONDS: Final[int] = 300


@dataclass
class BuildingListenerState:
    is_started: bool = False
    is_connected: bool = False
    is_receiver_open: bool = False
    is_failed: bool = False
    last_error: str | None = None
    updated_at: datetime | None = None
    disconnected_since: datetime | None = None

    def reset(self) -> None:
        self.is_started = False
        self.is_connected = False
        self.is_receiver_open = False
        self.is_failed = False
        self.last_error = None
        self.updated_at = None
        self.disconnected_since = None

    def mark_starting(self) -> None:
        self.is_started = True
        self.is_failed = False
        self.last_error = None
        self.updated_at = datetime.now(UTC)

    def mark_connected(self) -> None:
        self.is_started = True
        self.is_connected = True
        self.is_receiver_open = True
        self.is_failed = False
        self.last_error = None
        self.disconnected_since = None
        self.updated_at = datetime.now(UTC)

    def mark_disconnected(self, error: str | None = None) -> None:
        now = datetime.now(UTC)

        self.is_started = True
        self.is_connected = False
        self.is_receiver_open = False
        self.last_error = error
        self.disconnected_since = self.disconnected_since or now
        self.updated_at = now

    def mark_failed(self, error: str) -> None:
        now = datetime.now(UTC)

        self.is_started = True
        self.is_connected = False
        self.is_receiver_open = False
        self.is_failed = True
        self.last_error = error
        self.disconnected_since = self.disconnected_since or now
        self.updated_at = now


class BuildingListener(MessagingHandler):
    @staticmethod
    def is_building_listener_ready(state: BuildingListenerState) -> bool:
        return not state.is_started or (
            state.is_connected and state.is_receiver_open and not state.is_failed
        )

    @staticmethod
    def is_building_listener_live(state: BuildingListenerState) -> bool:
        if state.is_failed:
            return False
        if BuildingListener.is_building_listener_ready(state) or not state.disconnected_since:
            return True
        disconnected_for = datetime.now(UTC) - state.disconnected_since
        return disconnected_for.total_seconds() < LIVENESS_FAILURE_AFTER_SECONDS

    def __init__(
        self,
        amqp_urls: list,
        address: str,
        queue: str,
        user: str,
        password: str,
        ssl: bool = False,
        *,
        backoff_initial: float = 1.0,
        backoff_max: float = 60.0,
        backoff_factor: float = 2.0,
        heartbeat: int = DEFAULT_HEARTBEAT_SECONDS,
        state: BuildingListenerState,
    ) -> None:
        super(BuildingListener, self).__init__()  # noqa: UP008
        self.urls = amqp_urls
        self.address = address
        self.queue_name = queue
        self.user = user
        self.password = password
        self.conn: Any | None = None
        self.receiver: Any | None = None
        self.ssl = ssl
        self.heartbeat = heartbeat
        self.state = state
        self._backoff = Backoff(initial=backoff_initial, max_delay=backoff_max, factor=backoff_factor)

    @staticmethod
    def _condition_info(condition: Any) -> dict[str, str | None]:
        if not condition:
            return {"name": None, "description": None}
        return {
            "name": str(getattr(condition, "name", None)),
            "description": str(getattr(condition, "description", None)),
        }

    @classmethod
    def _event_error_info(cls, event: Event) -> dict[str, Any]:
        return {
            "connection_condition": cls._condition_info(
                getattr(getattr(event, "connection", None), "remote_condition", None),
            ),
            "transport_condition": cls._condition_info(
                getattr(getattr(event, "transport", None), "condition", None),
            ),
        }

    def _connect(self, event: Event) -> Any:
        kwargs = {
            "urls": self.urls,
            "user": self.user,
            "password": shlex.quote(self.password),
            "address": self.address,
            "reconnect": self._backoff,
            "heartbeat": self.heartbeat,
        }
        if self.ssl:
            sdomain = SSLDomain(SSLDomain.MODE_CLIENT)
            sdomain.set_trusted_ca_db("/etc/ssl/certs/ca-certificates.crt")
            kwargs["ssl_domain"] = sdomain
        return event.container.connect(**kwargs)

    def on_start(self, event: Event) -> None:
        self.state.mark_starting()
        self.conn = self._connect(event)

    def on_connection_opened(self, event: Event) -> None:
        # создаём/воссоздаём receiver на открытом соединении
        if not self.receiver:
            self.receiver = event.container.create_receiver(
                event.connection,
                source=self.address,
                name=AppSettings.queue_name_part2,
                options=DurableSubscription(),
            )
        self.state.mark_connected()
        logger.info("Connection opened and receiver ensured", heartbeat=self.heartbeat)

    def on_connection_closed(self, event: Event) -> None:
        error_info = self._event_error_info(event)
        self.receiver = None
        self.conn = None
        self.state.mark_disconnected("Connection closed")
        logger.warning("Connection closed", **error_info)

    def on_transport_error(self, event: Event) -> None:
        error_info = self._event_error_info(event)
        self.receiver = None
        self.conn = None
        self.state.mark_disconnected("Transport error")
        logger.error("Transport error", **error_info)

    def on_disconnected(self, event: Event) -> None:
        error_info = self._event_error_info(event)
        self.receiver = None
        self.conn = None
        self.state.mark_disconnected("Disconnected")
        logger.warning("Disconnected from broker", **error_info)

    def on_message(self, event: Event) -> None:
        try:
            raw = event.message.body

            if not raw:
                logger.warning("Empty AMQP message body; rejecting")
                self.reject(event.delivery)
                return

            if isinstance(raw, (bytes, bytearray)):
                try:
                    raw = raw.decode("utf-8")
                except UnicodeDecodeError as e:
                    logger.exception(f"Non-UTF8 message body; rejecting. {e}")
                    self.reject(event.delivery)
                    return

            try:
                message_body = json.loads(raw)
            except json.JSONDecodeError as e:
                logger.error(f"Invalid JSON body; rejecting: {e}")
                self.reject(event.delivery)
                return

            logger.info(f"Received message: {message_body}")

            try:
                loop = asyncio.get_running_loop()
                task = loop.create_task(handler.building_handle(message_body))

                def _log_task_result(t: asyncio.Task) -> None:
                    exc = t.exception()
                    if exc:
                        logger.error("building_handle failed", error=str(exc))

                task.add_done_callback(_log_task_result)
            except RuntimeError:
                # нет активного event loop — выполняем синхронно
                asyncio.run(handler.building_handle(message_body))

            self.accept(event.delivery)
            logger.info("Message accepted and acknowledged")

        except (ConnectionError, CreateError, InconsistentMessageError) as e:
            logger.error(f"Error processing message: {e}")
            self.reject(event.delivery)

    def close_connection(self) -> None:
        if self.receiver:
            try:
                self.receiver.close()
            except Exception as exc:  # noqa: BLE001
                logger.warning("Failed to close AMQP receiver", error=str(exc))
            finally:
                self.receiver = None
        if self.conn:
            try:
                self.conn.close()
            except Exception as exc:  # noqa: BLE001
                logger.warning("Failed to close AMQP connection", error=str(exc))
            finally:
                self.conn = None


"""Handler."""

from datetime import datetime
from typing import Generator

import structlog
from annotated_types.test_cases import cases
from dependency_injector.wiring import Provide, inject

from src.services.utils.func import convert_date_from_timestamp, require_field
from ..categories_resolver import resolve_room_zones

from ...containers.message_bus import MessageBusContainer
from ...domain import commands, model
from ..message_bus import MessageBus

logger = structlog.get_logger("building_handle")


def mapping_building_data(data: dict) -> Generator[dict, None, None]:
    building_data = data.get("event", {})

    if (rooms := building_data.get("rooms", [])) is None:
        rooms = []

    for room in rooms:
        resident = building_data.get("resident", {})

        inspection = require_field(room, "inspection", "event.rooms[].inspection")
        inspection_created_at = require_field(inspection, "createdAt", "event.rooms[].inspection.createdAt")

        total_dict = {
            "building_id": building_data.get("id"),
            "building_name": building_data.get("title"),
            "start_date_building": convert_date_from_timestamp(x) if (x := building_data.get("startDate")) is not None else x,
            "end_date_building": building_data.get("endDate"),
            "text_value": None,
            "number_value": None,
            "note_value": None,
            "selection_or_rating_predefined_options": room.get("choices") or room.get("ratingOptions") or None,
            "single_choice_value_from_predefined": None,
            "multi_choice_value_from_predefined": None,
            "choice_value_custom": None,
            "rating_value_from_predefined": None,
            "rating_comment": None,
            "login": resident.get("login"),
            "domain": resident.get("domain"),
            "crew_id": resident.get("crewId"),
            "crew_name": resident.get("crewName"),
            "role": resident.get("role"),
            "start_date_job": datetime.strptime(
                resident.get("experience") or datetime.today().strftime("%Y-%m-%d"),
                "%Y-%m-%d",
            ).date(),
            "room_id": room.get("id"),
            "room_type": room.get("type"),
            "room_name": room.get("text"),
            "sub_zone": room.get("subZone"),
            "weight": 2,
            "min_label": room.get("minRatingLabel"),
            "max_label": room.get("maxRatingLabel"),
            "inspection_id": inspection.get("id"),
            "room_number": None,
            "rooms_count": len(rooms),
            "building_crew_id": resident.get("buildingCrewId"),
            "all_options": room.get("choices"),
            "resident_options": tuple(),
            "zone": room.get("zone"),
            "inspection_timestamp": datetime.fromisoformat(inspection_created_at),
            "is_not_applicable": inspection.get("notApplicable"),
        }

        # Processing room types
        match room.get("type"):
            case "LIKERT":
                if inspection["standardInspection"] and str(inspection["standardInspection"][0]).isdigit():
                    total_dict["number_value"] = int(inspection["standardInspection"][0])
                    total_dict["rating_value_from_predefined"] = inspection["standardInspection"][0]
                if rating_comment := inspection.get("ratingComment"):
                    total_dict["text_value"] = rating_comment
                    total_dict["rating_comment"] = rating_comment
                if rating_options := room.get("ratingOptions"):
                    total_dict["all_options"] = rating_options

            case "RADIO":
                if standard_inspection := inspection["standardInspection"]:
                    total_dict["text_value"] = [inspection["standardInspection"][0]]
                    total_dict["single_choice_value_from_predefined"] = standard_inspection
                if custom_inspection := inspection.get("customInspection"):
                    total_dict["choice_value_custom"] = custom_inspection
                    if total_dict["text_value"]:
                        total_dict["text_value"].append(custom_inspection)
                    else:
                        total_dict["text_value"] = [custom_inspection]

            case "CHECKBOX":
                total_dict["text_value"] = [s for s in inspection["standardInspection"] if isinstance(s, str)]
                if standard_inspection := inspection["standardInspection"]:
                    total_dict["multi_choice_value_from_predefined"] = standard_inspection
                if custom_inspection := inspection.get("customInspection"):
                    total_dict["text_value"].append(custom_inspection)
                    total_dict["choice_value_custom"] = custom_inspection

            case "TEXT" | "TEXTAREA":
                if standard_inspection := inspection["standardInspection"]:
                    total_dict["text_value"] = standard_inspection[0]
                    total_dict["note_value"] = standard_inspection[0]

            case _:
                total_dict["text_value"] = inspection["standardInspection"]

        yield total_dict


@inject
async def building_handle(
    data: dict,
    bus: MessageBus = Provide[MessageBusContainer.message_bus],
) -> bool | None:
    building_event_cmd = commands.UpsertBuildingEvent(
        building_id=data.get("event", {}).get("id"),
        event_payload=data,
        inspection_ids=[room.get("inspection", {}).get("id") for room in data.get("event", {}).get("rooms", [])],
    )
    await bus.handle(building_event_cmd)

    for result in mapping_building_data(data):
        message = model.Inspections.from_dict(result)

        zone = message.zone
        sub_zone = message.sub_zone
        if not (zone or sub_zone):
            room_zones = resolve_room_zones(
                building_id=message.building_id,
                room_id=message.room_id,
            )
            if room_zones:
                zone = room_zones["zone"]
                sub_zone = room_zones["sub_zone"]

        cmd = commands.CreateInspection(
            building_id=message.building_id,
            room_id=message.room_id,
            number_value=message.number_value,
            text_value=message.text_value,
            login=message.login,
            domain=message.domain,
            crew_id=message.crew_id,
            crew_name=message.crew_name,
            inspection_timestamp=message.inspection_timestamp,
            role=message.role,
            start_date_job=message.start_date_job,
            building_name=message.building_name,
            all_options=message.all_options,
            resident_options=message.resident_options,
            start_date_building=message.start_date_building,
            end_date_building=message.end_date_building,
            zone=zone,
            sub_zone=sub_zone,
            room_name=message.room_name,
            room_type=message.room_type,
            weight=message.weight,
            min_label=message.min_label,
            max_label=message.max_label,
            inspection_id=message.inspection_id,
            room_number=message.room_number,
            rooms_count=message.rooms_count,
            building_crew_id=message.building_crew_id,
            is_not_applicable=message.is_not_applicable,
            note_value=message.note_value,
            selection_or_rating_predefined_options=message.selection_or_rating_predefined_options,
            single_choice_value_from_predefined=message.single_choice_value_from_predefined,
            multi_choice_value_from_predefined=message.multi_choice_value_from_predefined,
            choice_value_custom=message.choice_value_custom,
            rating_value_from_predefined=message.rating_value_from_predefined,
            rating_comment=message.rating_comment,
        )

        result = await bus.handle(cmd)
        logger.info(f"Data {result} was saved")
    else:  # noqa: PLW0120
        return True
```
### Обновленная версия кода
```python
# services/amqp/handlers.py

from typing import Any, Protocol

import structlog
from pydantic import BaseModel


class MessageContext(BaseModel):
    topic_name: str
    address: str
    receiver_name: str

    class Config:
        arbitrary_types_allowed = True


class ArtemisMessageHandler(Protocol):
    name: str

    async def handle(
        self,
        message: dict[str, Any],
        context: MessageContext,
        logger: structlog.stdlib.BoundLogger,
    ) -> None:
        ...


# services/amqp/config.py
from dataclasses import dataclass, field
from typing import Final

from .handlers import ArtemisMessageHandler


DEFAULT_HEARTBEAT_SECONDS: Final[int] = 60
LIVENESS_FAILURE_AFTER_SECONDS: Final[int] = 300


@dataclass(frozen=True)
class ArtemisTopicConfig:
    name: str
    address: str
    receiver_name: str
    handler: ArtemisMessageHandler
    enabled: bool = True
    durable: bool = True


@dataclass(frozen=True)
class ArtemisQueueConfig:
    urls: list[str]
    user: str
    password: str
    ssl: bool = False
    heartbeat: int = DEFAULT_HEARTBEAT_SECONDS
    backoff_initial: float = 1.0
    backoff_max: float = 60.0
    backoff_factor: float = 2.0
    topics: list[ArtemisTopicConfig] = field(default_factory=list)


# services/amqp/topic.py
import json
from dataclasses import dataclass
from datetime import UTC, datetime
from typing import Any

import structlog

from .config import ArtemisTopicConfig
from .handlers import MessageContext


class MessageRejectError(Exception):
    pass


@dataclass
class ArtemisTopicState:
    is_enabled: bool = True
    is_receiver_open: bool = False
    is_failed: bool = False
    last_error: str | None = None
    updated_at: datetime | None = None

    def mark_opened(self) -> None:
        self.is_receiver_open = True
        self.is_failed = False
        self.last_error = None
        self.updated_at = datetime.now(UTC)

    def mark_closed(self, error: str | None = None) -> None:
        self.is_receiver_open = False
        self.last_error = error
        self.updated_at = datetime.now(UTC)

    def mark_failed(self, error: str) -> None:
        self.is_receiver_open = False
        self.is_failed = True
        self.last_error = error
        self.updated_at = datetime.now(UTC)


class ArtemisTopic:
    def __init__(
        self,
        config: ArtemisTopicConfig,
        base_logger: structlog.stdlib.BoundLogger,
    ) -> None:
        self.config = config
        self.receiver: Any | None = None
        self.state = ArtemisTopicState(is_enabled=config.enabled)

        self.logger = base_logger.bind(
            topic=config.name,
            address=config.address,
            receiver_name=config.receiver_name,
            handler=config.handler.name,
        )

    def attach_receiver(self, receiver: Any) -> None:
        self.receiver = receiver
        self.state.mark_opened()
        self.logger.info("artemis.topic.receiver_opened")

    def detach_receiver(self, error: str | None = None) -> None:
        self.receiver = None
        self.state.mark_closed(error)
        self.logger.warning("artemis.topic.receiver_closed", error=error)

    def decode_body(self, raw_body: Any) -> str:
        if not raw_body:
            raise MessageRejectError("Empty AMQP message body")

        if isinstance(raw_body, (bytes, bytearray)):
            try:
                return raw_body.decode("utf-8")
            except UnicodeDecodeError as exc:
                raise MessageRejectError("Non-UTF8 message body") from exc

        if isinstance(raw_body, str):
            return raw_body

        raise MessageRejectError(f"Unsupported message body type: {type(raw_body)!r}")

    def parse_message(self, raw_body: str) -> dict[str, Any]:
        try:
            message = json.loads(raw_body)
        except json.JSONDecodeError as exc:
            raise MessageRejectError("Invalid JSON body") from exc

        if not isinstance(message, dict):
            raise MessageRejectError("JSON body must be an object")

        return message

    async def process(self, raw_body: Any) -> None:
        self.logger.info("artemis.message.received")

        raw_text = self.decode_body(raw_body)
        message = self.parse_message(raw_text)

        context = MessageContext(
            topic_name=self.config.name,
            address=self.config.address,
            receiver_name=self.config.receiver_name,
        )

        self.logger.info("artemis.message.processing_started")

        await self.config.handler.handle(
            message=message,
            context=context,
            logger=self.logger,
        )

        self.logger.info("artemis.message.processing_finished")


# services/amqp/client.py
import asyncio
import shlex
from dataclasses import dataclass
from datetime import UTC, datetime
from typing import Any

import structlog
from proton import Event, SSLDomain  # type: ignore
from proton._reactor import Backoff
from proton.handlers import MessagingHandler  # type: ignore
from proton.reactor import DurableSubscription

from .config import ArtemisQueueConfig, LIVENESS_FAILURE_AFTER_SECONDS
from .topic import ArtemisTopic, MessageRejectError


@dataclass
class ArtemisQueueState:
    is_started: bool = False
    is_connected: bool = False
    is_failed: bool = False
    last_error: str | None = None
    disconnected_since: datetime | None = None
    updated_at: datetime | None = None

    def reset(self) -> None:
        self.is_started = False
        self.is_connected = False
        self.is_failed = False
        self.last_error = None
        self.disconnected_since = None
        self.updated_at = None

    def mark_starting(self) -> None:
        self.is_started = True
        self.is_failed = False
        self.last_error = None
        self.updated_at = datetime.now(UTC)

    def mark_connected(self) -> None:
        self.is_started = True
        self.is_connected = True
        self.is_failed = False
        self.last_error = None
        self.disconnected_since = None
        self.updated_at = datetime.now(UTC)

    def mark_disconnected(self, error: str | None = None) -> None:
        now = datetime.now(UTC)

        self.is_started = True
        self.is_connected = False
        self.last_error = error
        self.disconnected_since = self.disconnected_since or now
        self.updated_at = now

    def mark_failed(self, error: str) -> None:
        now = datetime.now(UTC)

        self.is_started = True
        self.is_connected = False
        self.is_failed = True
        self.last_error = error
        self.disconnected_since = self.disconnected_since or now
        self.updated_at = now


class ArtemisQueueClient(MessagingHandler):
    def __init__(
        self,
        config: ArtemisQueueConfig,
        state: ArtemisQueueState,
    ) -> None:
        super().__init__()

        self.config = config
        self.state = state
        self.conn: Any | None = None

        self.logger = structlog.get_logger("artemis_queue")

        self._backoff = Backoff(
            initial=config.backoff_initial,
            max_delay=config.backoff_max,
            factor=config.backoff_factor,
        )

        self.topics = [
            ArtemisTopic(topic_config, self.logger)
            for topic_config in config.topics
            if topic_config.enabled
        ]

        self._receiver_to_topic: dict[int, ArtemisTopic] = {}

    @staticmethod
    def is_ready(state: ArtemisQueueState) -> bool:
        return not state.is_started or (
            state.is_connected and not state.is_failed
        )

    @staticmethod
    def is_live(state: ArtemisQueueState) -> bool:
        if state.is_failed:
            return False

        if ArtemisQueueClient.is_ready(state) or not state.disconnected_since:
            return True

        disconnected_for = datetime.now(UTC) - state.disconnected_since
        return disconnected_for.total_seconds() < LIVENESS_FAILURE_AFTER_SECONDS

    @staticmethod
    def _condition_info(condition: Any) -> dict[str, str | None]:
        if not condition:
            return {"name": None, "description": None}

        return {
            "name": str(getattr(condition, "name", None)),
            "description": str(getattr(condition, "description", None)),
        }

    @classmethod
    def _event_error_info(cls, event: Event) -> dict[str, Any]:
        return {
            "connection_condition": cls._condition_info(
                getattr(getattr(event, "connection", None), "remote_condition", None),
            ),
            "transport_condition": cls._condition_info(
                getattr(getattr(event, "transport", None), "condition", None),
            ),
        }

    def _connect(self, event: Event) -> Any:
        kwargs = {
            "urls": self.config.urls,
            "user": self.config.user,
            "password": shlex.quote(self.config.password),
            "reconnect": self._backoff,
            "heartbeat": self.config.heartbeat,
        }

        if self.config.ssl:
            ssl_domain = SSLDomain(SSLDomain.MODE_CLIENT)
            ssl_domain.set_trusted_ca_db("/etc/ssl/certs/ca-certificates.crt")
            kwargs["ssl_domain"] = ssl_domain

        return event.container.connect(**kwargs)

    def on_start(self, event: Event) -> None:
        self.state.mark_starting()
        self.conn = self._connect(event)

        self.logger.info(
            "artemis.connection.starting",
            topics=[topic.config.name for topic in self.topics],
        )

    def on_connection_opened(self, event: Event) -> None:
        for topic in self.topics:
            receiver = event.container.create_receiver(
                event.connection,
                source=topic.config.address,
                name=topic.config.receiver_name,
                options=DurableSubscription() if topic.config.durable else None,
            )

            topic.attach_receiver(receiver)
            self._receiver_to_topic[id(receiver)] = topic

        self.state.mark_connected()

        self.logger.info(
            "artemis.connection.opened",
            enabled_topics=[topic.config.name for topic in self.topics],
        )

    def on_message(self, event: Event) -> None:
        topic = self._receiver_to_topic.get(id(event.receiver))

        if not topic:
            self.logger.error("artemis.message.unknown_receiver")
            self.reject(event.delivery)
            return

        try:
            loop = asyncio.get_running_loop()
        except RuntimeError:
            self._process_message_sync(
                topic=topic,
                raw_body=event.message.body,
                delivery=event.delivery,
            )
            return

        task = loop.create_task(topic.process(event.message.body))
        task.add_done_callback(
            lambda finished_task: self._finish_async_message(
                task=finished_task,
                topic=topic,
                delivery=event.delivery,
            ),
        )

    def _process_message_sync(
        self,
        topic: ArtemisTopic,
        raw_body: Any,
        delivery: Any,
    ) -> None:
        try:
            asyncio.run(topic.process(raw_body))
        except MessageRejectError as exc:
            topic.logger.warning("artemis.message.rejected", error=str(exc))
            self.reject(delivery)
        except Exception as exc:
            topic.state.mark_failed(str(exc))
            topic.logger.exception("artemis.message.processing_failed", error=str(exc))
            self.reject(delivery)
        else:
            self.accept(delivery)
            topic.logger.info("artemis.message.accepted")

    def _finish_async_message(
        self,
        task: asyncio.Task,
        topic: ArtemisTopic,
        delivery: Any,
    ) -> None:
        try:
            task.result()
        except MessageRejectError as exc:
            topic.logger.warning("artemis.message.rejected", error=str(exc))
            self.reject(delivery)
        except Exception as exc:
            topic.state.mark_failed(str(exc))
            topic.logger.exception("artemis.message.processing_failed", error=str(exc))
            self.reject(delivery)
        else:
            self.accept(delivery)
            topic.logger.info("artemis.message.accepted")

    def on_connection_closed(self, event: Event) -> None:
        error_info = self._event_error_info(event)

        for topic in self.topics:
            topic.detach_receiver("Connection closed")

        self._receiver_to_topic.clear()
        self.conn = None
        self.state.mark_disconnected("Connection closed")

        self.logger.warning("artemis.connection.closed", **error_info)

    def on_transport_error(self, event: Event) -> None:
        error_info = self._event_error_info(event)

        for topic in self.topics:
            topic.detach_receiver("Transport error")

        self._receiver_to_topic.clear()
        self.conn = None
        self.state.mark_disconnected("Transport error")

        self.logger.error("artemis.transport.error", **error_info)

    def on_disconnected(self, event: Event) -> None:
        error_info = self._event_error_info(event)

        for topic in self.topics:
            topic.detach_receiver("Disconnected")

        self._receiver_to_topic.clear()
        self.conn = None
        self.state.mark_disconnected("Disconnected")

        self.logger.warning("artemis.connection.disconnected", **error_info)

    def close_connection(self) -> None:
        for topic in self.topics:
            if topic.receiver:
                try:
                    topic.receiver.close()
                except Exception as exc:
                    topic.logger.warning(
                        "artemis.topic.receiver_close_failed",
                        error=str(exc),
                    )
                finally:
                    topic.detach_receiver("Manual close")

        if self.conn:
            try:
                self.conn.close()
            except Exception as exc:
                self.logger.warning(
                    "artemis.connection.close_failed",
                    error=str(exc),
                )
            finally:
                self.conn = None


# services/amqp/factory.py
from ...domain.exceptions import EnvValueError
from ...settings import AppSettings

from .client import ArtemisQueueClient, ArtemisQueueState
from .config import ArtemisQueueConfig, ArtemisTopicConfig
from ..amqp_topic_handlers.building_events import BuildingEventsHandler
from ..amqp_topic_handlers.building_inspections import BuildingInspectionsHandler


def build_artemis_urls() -> tuple[list[str], bool]:
    host_list = AppSettings.broker_host.split(",")
    port_list = AppSettings.broker_port.split(",")

    if len(host_list) != len(port_list):
        raise EnvValueError("Количество переменных host-port должны совпадать")

    urls = []
    ssl = False

    for host, port in zip(host_list, port_list):
        if port == "61616":
            url = f"amqp://{host}:{port}"
        else:
            url = f"amqps://{host}:{port}"
            ssl = True

        if AppSettings.extend_params:
            url += f"?{AppSettings.extend_params}"

        urls.append(url)

    return urls, ssl


def get_artemis_client(state: ArtemisQueueState) -> ArtemisQueueClient:
    urls, ssl = build_artemis_urls()

    config = ArtemisQueueConfig(
        urls=urls,
        user=AppSettings.broker_user,
        password=AppSettings.broker_password,
        ssl=ssl,
        topics=[
            ArtemisTopicConfig(
                name="building_events",
                address=AppSettings.building_events_queue_address,
                receiver_name=AppSettings.building_events_queue_name,
                handler=BuildingEventsHandler(),
                enabled=AppSettings.building_events_enabled,
                durable=True,
            ),
            ArtemisTopicConfig(
                name="building_inspections",
                address=AppSettings.building_inspections_queue_address,
                receiver_name=AppSettings.building_inspections_queue_name,
                handler=BuildingInspectionsHandler(),
                enabled=AppSettings.building_inspections_enabled,
                durable=True,
            ),
        ],
    )

    return ArtemisQueueClient(
        config=config,
        state=state,
    )


# services/amqp_topic_handlers/building_events.py
from typing import Any

import structlog
from dependency_injector.wiring import Provide, inject

from src.containers.message_bus import MessageBusContainer
from src.domain import commands
from src.services.amqp.handlers import MessageContext
from src.services.message_bus import MessageBus


class BuildingEventsHandler:
    name = "building_events_handler"

    @inject
    def __init__(
        self,
        bus: MessageBus = Provide[MessageBusContainer.message_bus],
    ) -> None:
        self.bus = bus

    async def handle(
        self,
        message: dict[str, Any],
        context: MessageContext,
        logger: structlog.stdlib.BoundLogger,
    ) -> None:
        logger = logger.bind(
            handler=self.name,
            topic=context.topic_name,
            building_id=message.get("event", {}).get("id"),
        )

        logger.info("building_events.handle_started")

        command = commands.UpsertBuildingEvent(
            building_id=message.get("event", {}).get("id"),
            event_payload=message,
        )

        result = await self.bus.handle(command)

        logger.info(
            "building_events.handle_finished",
            result=result,
        )


# services/amqp_topic_handlers/building_inspections.py
from datetime import datetime
from typing import Any, Generator

import structlog
from dependency_injector.wiring import Provide, inject

from src.containers.message_bus import MessageBusContainer
from src.domain import commands, model
from src.services.amqp.handlers import MessageContext
from src.services.message_bus import MessageBus
from src.services.utils.func import convert_date_from_timestamp, require_field

from ..room_zones_resolver import resolve_room_zones


class BuildingInspectionsHandler:
    name = "building_inspections_handler"

    @inject
    def __init__(
        self,
        bus: MessageBus = Provide[MessageBusContainer.message_bus],
    ) -> None:
        self.bus = bus

    async def handle(
        self,
        message: dict[str, Any],
        context: MessageContext,
        logger: structlog.stdlib.BoundLogger,
    ) -> None:
        logger = logger.bind(
            handler=self.name,
            topic=context.topic_name,
            building_id=message.get("event", {}).get("id"),
        )

        logger.info("building_inspections.handle_started")

        building_event_cmd = commands.UpsertBuildingEvent(
            building_id=message.get("event", {}).get("id"),
            event_payload=message,
            inspection_ids=[
                room.get("inspection", {}).get("id")
                for room in message.get("event", {}).get("rooms", [])
            ],
        )
        await self.bus.handle(building_event_cmd)

        for result in map_building_inspection_data(message):
            inspection = model.BuildingInspection.from_dict(result)

            zone = inspection.zone
            sub_zone = inspection.sub_zone

            if not (zone or sub_zone):
                room_zones = resolve_room_zones(
                    building_id=inspection.building_id,
                    room_id=inspection.room_id,
                )

                if room_zones:
                    zone = room_zones["zone"]
                    sub_zone = room_zones["sub_zone"]

            command = commands.CreateBuildingInspection(
                building_id=inspection.building_id,
                room_id=inspection.room_id,
                number_value=inspection.number_value,
                text_value=inspection.text_value,
                inspector_login=inspection.inspector_login,
                inspector_domain=inspection.inspector_domain,
                maintenance_team_id=inspection.maintenance_team_id,
                maintenance_team_name=inspection.maintenance_team_name,
                inspection_timestamp=inspection.inspection_timestamp,
                inspector_role=inspection.inspector_role,
                start_date_job=inspection.start_date_job,
                building_name=inspection.building_name,
                predefined_options=inspection.predefined_options,
                selected_options=inspection.selected_options,
                start_date_building=inspection.start_date_building,
                end_date_building=inspection.end_date_building,
                zone=zone,
                sub_zone=sub_zone,
                room_name=inspection.room_name,
                room_type=inspection.room_type,
                weight=inspection.weight,
                min_label=inspection.min_label,
                max_label=inspection.max_label,
                inspection_id=inspection.inspection_id,
                room_number=inspection.room_number,
                rooms_count=inspection.rooms_count,
                responsible_team_id=inspection.responsible_team_id,
                is_not_applicable=inspection.is_not_applicable,
                text_or_textarea_value=inspection.text_or_textarea_value,
                radio_or_checkbox_or_likert_predefined_options=inspection.radio_or_checkbox_or_likert_predefined_options,
                radio_value_from_predefined=inspection.radio_value_from_predefined,
                checkbox_value_from_predefined=inspection.checkbox_value_from_predefined,
                radio_or_checkbox_value_custom=inspection.radio_or_checkbox_value_custom,
                likert_value_from_predefined=inspection.likert_value_from_predefined,
                likert_comment=inspection.likert_comment,
            )

            saved_result = await self.bus.handle(command)

            logger.info(
                "building_inspections.inspection_saved",
                result=saved_result,
                building_id=inspection.building_id,
                room_id=inspection.room_id,
                inspection_id=inspection.inspection_id,
            )

        logger.info("building_inspections.handle_finished")


def map_building_inspection_data(
    data: dict[str, Any],
) -> Generator[dict[str, Any], None, None]:
    building_data = data.get("event", {})

    if (rooms := building_data.get("rooms", [])) is None:
        rooms = []

    for room in rooms:
        inspector = building_data.get("inspector", {})

        inspection = require_field(
            room,
            field_name="inspection",
            field_path="event.rooms[].inspection",
        )
        inspection_created_at = require_field(
            inspection,
            field_name="createdAt",
            field_path="event.rooms[].inspection.createdAt",
        )

        total_dict = {
            "building_id": building_data.get("id"),
            "building_name": building_data.get("title"),
            "start_date_building": convert_date_from_timestamp(x) if (x := building_data.get("startDate")) is not None else x,
            "end_date_building": building_data.get("endDate"),
            "text_value": None,
            "number_value": None,
            "text_or_textarea_value": None,
            "radio_or_checkbox_or_likert_predefined_options": room.get("choices") or room.get("likertChoices") or None,
            "radio_value_from_predefined": None,
            "checkbox_value_from_predefined": None,
            "radio_or_checkbox_value_custom": None,
            "likert_value_from_predefined": None,
            "likert_comment": None,
            "inspector_login": inspector.get("login"),
            "inspector_domain": inspector.get("domain"),
            "maintenance_team_id": inspector.get("teamId"),
            "maintenance_team_name": inspector.get("teamName"),
            "inspector_role": inspector.get("role"),
            "start_date_job": datetime.strptime(
                inspector.get("experience") or datetime.today().strftime("%Y-%m-%d"),
                "%Y-%m-%d",
            ).date(),
            "room_id": room.get("id"),
            "room_type": room.get("type"),
            "room_name": room.get("text"),
            "sub_zone": room.get("subZone"),
            "weight": 2,
            "min_label": room.get("minLikertLabel"),
            "max_label": room.get("maxLikertLabel"),
            "inspection_id": inspection.get("id"),
            "room_number": None,
            "rooms_count": len(rooms),
            "responsible_team_id": inspector.get("subTeamId"),
            "predefined_options": room.get("choices"),
            "selected_options": tuple(),
            "zone": room.get("zone"),
            "inspection_timestamp": datetime.fromisoformat(inspection_created_at),
            "is_not_applicable": inspection.get("notApplicable"),
        }

        match room.get("type"):
            case "LIKERT":
                if inspection["standardInspection"] and str(inspection["standardInspection"][0]).isdigit():
                    total_dict["number_value"] = int(inspection["standardInspection"][0])
                    total_dict["likert_value_from_predefined"] = inspection["standardInspection"][0]

                if likert_comment := inspection.get("likertComment"):
                    total_dict["text_value"] = likert_comment
                    total_dict["likert_comment"] = likert_comment

                if likert_choices := room.get("likertChoices"):
                    total_dict["predefined_options"] = likert_choices

            case "RADIO":
                if standard_inspection := inspection["standardInspection"]:
                    total_dict["text_value"] = [inspection["standardInspection"][0]]
                    total_dict["radio_value_from_predefined"] = standard_inspection

                if custom_inspection := inspection.get("customInspection"):
                    total_dict["radio_or_checkbox_value_custom"] = custom_inspection

                    if total_dict["text_value"]:
                        total_dict["text_value"].append(custom_inspection)
                    else:
                        total_dict["text_value"] = [custom_inspection]

            case "CHECKBOX":
                total_dict["text_value"] = [
                    item
                    for item in inspection["standardInspection"]
                    if isinstance(item, str)
                ]

                if standard_inspection := inspection["standardInspection"]:
                    total_dict["checkbox_value_from_predefined"] = standard_inspection

                if custom_inspection := inspection.get("customInspection"):
                    total_dict["text_value"].append(custom_inspection)
                    total_dict["radio_or_checkbox_value_custom"] = custom_inspection

            case "TEXT" | "TEXTAREA":
                if standard_inspection := inspection["standardInspection"]:
                    total_dict["text_value"] = standard_inspection[0]
                    total_dict["text_or_textarea_value"] = standard_inspection[0]

            case _:
                total_dict["text_value"] = inspection["standardInspection"]

        yield total_dict
```
### Комментарий
В изначальной версии данного кода в обработку сообщений не была заложена удобная функциональность по работе с несколькими топиками в рамках одной Artemis очереди. 
В новом дизайне была попытка свести логику сервиса (его предназначение) с реализацией через разбиение процесса по взаимодействию с очередью и данными из неё на большее количество абстракций, позволяющих наиболее точно настраивать логику обработки как разных топиков, так и обработчиков сообщений.

## Пример №2
### Существующая версия кода
```python
from datetime import timezone, datetime

from fastapi import Depends
from admin.schemas.room import (
    RoomGetSchema, RoomCreateSchema, RoomShortSchema, RoomFiltersSchema,
    RoomUpdateSchema, RoomStatisticFiltersSchema, RoomShortWithZoneSchema, RoomStatisticGetSchema
)
from admin.repositories.room import RoomRepository
from admin.repositories.sensor_data import SensorDataRepository
from admin.services.access import RoomAccessService
from admin.db.tables import Room


class RoomService:
    def __init__(
            self,
            repository: RoomRepository = Depends(),
            access_service: RoomAccessService = Depends(),
            sensor_data_repository: SensorDataRepository = Depends()
    ):
        self.repository = repository
        self.access_service = access_service
        self.sensor_data_repository = sensor_data_repository

    async def create(self, schema: RoomCreateSchema) -> RoomShortSchema:
        model = Room(**schema.model_dump())
        model = await self.repository.create(model)
        return RoomShortSchema.model_validate(model)

    async def get_one(self, room_id: int) -> RoomGetSchema:
        model = await self.repository.get_one(room_id=room_id)
        return RoomGetSchema.model_validate(model)

    async def get_many(self, filters: RoomFiltersSchema) -> list[RoomShortWithZoneSchema]:
        filters = filters.model_dump(exclude_none=True)
        models = await self.repository.get_many(**filters, select_in_load=[Room.zone])
        models = await self.access_service.filter_get_many_response(models)
        return [
            RoomShortWithZoneSchema.model_validate(model)
            for model in models
        ]

    async def update(self, room_id: int, schema: RoomUpdateSchema) -> RoomShortSchema:
        fields = schema.model_dump(exclude_none=True)
        model = await self.repository.update(room_id, **fields)
        return RoomShortSchema.model_validate(model)

    async def delete(self, room_id: int) -> None:
        await self.repository.delete(room_id)

    async def get_sensors_statistic(self, room_id: int, filters: RoomStatisticFiltersSchema) -> RoomStatisticGetSchema:
        filters = filters.model_dump(exclude_none=True)
        models = await self.sensor_data_repository.get_room_statistic(room_id=room_id, **filters)
        return RoomStatisticGetSchema.model_validate({'data': models})

    # New methods for aggregated statistics
    async def get_sensors_statistic_per_minute(self, room_id: int, filters: RoomStatisticFiltersSchema):
        filters.from_datetime = ensure_utc(filters.from_datetime)
        filters.to_datetime = ensure_utc(filters.to_datetime)
        models = await self.repository.get_sensors_statistic(room_id, filters, "sensor_data_per_minute")
        return RoomStatisticGetSchema.model_validate({'data': models})

    async def get_sensors_statistic_per_hour(self, room_id: int, filters: RoomStatisticFiltersSchema):
        filters.from_datetime = ensure_utc(filters.from_datetime)
        filters.to_datetime = ensure_utc(filters.to_datetime)
        models = await self.repository.get_sensors_statistic(room_id, filters, "sensor_data_per_hour")
        return RoomStatisticGetSchema.model_validate({'data': models})

    async def get_sensors_statistic_per_day(self, room_id: int, filters: RoomStatisticFiltersSchema):
        filters.from_datetime = ensure_utc(filters.from_datetime)
        filters.to_datetime = ensure_utc(filters.to_datetime)
        models = await self.repository.get_sensors_statistic(room_id, filters, "sensor_data_per_day")
        return RoomStatisticGetSchema.model_validate({'data': models})

    async def get_sensors_statistic_per_week(self, room_id: int, filters: RoomStatisticFiltersSchema):
        filters.from_datetime = ensure_utc(filters.from_datetime)
        filters.to_datetime = ensure_utc(filters.to_datetime)
        models = await self.repository.get_sensors_statistic(room_id, filters, "sensor_data_per_week")
        return RoomStatisticGetSchema.model_validate({'data': models})



def ensure_utc(dt: datetime) -> datetime:
    """Ensure the datetime is timezone-aware and in UTC."""
    if dt.tzinfo is None:  # If naive, assume it's in UTC
        return dt.replace(tzinfo=timezone.utc)
    return dt.astimezone(timezone.utc)  # Convert to UTC if it has a timezone

```
### Обновленная версия кода
```python
class RoomService:
    def __init__(
            self,
            repository: RoomRepository = Depends(),
            access_service: RoomAccessService = Depends(),
            sensor_data_repository: SensorDataRepository = Depends(),
            space_constructor: RoomSpaceConstructor = Depends(),
    ):
        self.repository = repository
        self.access_service = access_service
        self.sensor_data_repository = sensor_data_repository
        self.space_constructor = space_constructor

    async def create(self, schema: RoomCreateSchema) -> RoomShortSchema:
        model = Room(**schema.model_dump())
        model = await self.repository.create(model)
        return RoomShortSchema.model_validate(model)

    async def get_one(self, room_id: int) -> RoomGetSchema:
        model = await self.repository.get_one(room_id=room_id)
        return RoomGetSchema.model_validate(model)

    async def get_many(self, filters: RoomFiltersSchema) -> list[RoomShortWithZoneSchema]:
        filters = filters.model_dump(exclude_none=True)
        models = await self.repository.get_many(**filters, select_in_load=[Room.zone])
        models = await self.access_service.filter_get_many_response(models)
        return [
            RoomShortWithZoneSchema.model_validate(model)
            for model in models
        ]

    async def update(self, room_id: int, schema: RoomUpdateSchema) -> RoomShortSchema:
        fields = schema.model_dump(exclude_none=True)
        model = await self.repository.update(room_id, **fields)
        return RoomShortSchema.model_validate(model)

    async def delete(self, room_id: int) -> None:
        await self.repository.delete(room_id)

    async def get_sensors_statistic(
        self,
        room_id: int,
        filters: RoomStatisticFiltersSchema,
    ) -> RoomStatisticGetSchema:
        filters = filters.model_dump(exclude_none=True)
        models = await self.sensor_data_repository.get_room_statistic(room_id=room_id, **filters)
        return RoomStatisticGetSchema.model_validate({'data': models})

    async def get_sensors_statistic_per_minute(
        self,
        room_id: int,
        filters: RoomStatisticFiltersSchema,
    ) -> RoomStatisticGetSchema:
        return await self._get_sensors_statistic_by_period(
            room_id=room_id,
            filters=filters,
            period=SensorStatisticPeriod.MINUTE,
        )

    async def get_sensors_statistic_per_hour(
        self,
        room_id: int,
        filters: RoomStatisticFiltersSchema,
    ) -> RoomStatisticGetSchema:
        return await self._get_sensors_statistic_by_period(
            room_id=room_id,
            filters=filters,
            period=SensorStatisticPeriod.HOUR,
        )

    async def get_sensors_statistic_per_day(
        self,
        room_id: int,
        filters: RoomStatisticFiltersSchema,
    ) -> RoomStatisticGetSchema:
        return await self._get_sensors_statistic_by_period(
            room_id=room_id,
            filters=filters,
            period=SensorStatisticPeriod.DAY,
        )

    async def get_sensors_statistic_per_week(
        self,
        room_id: int,
        filters: RoomStatisticFiltersSchema,
    ) -> RoomStatisticGetSchema:
        return await self._get_sensors_statistic_by_period(
            room_id=room_id,
            filters=filters,
            period=SensorStatisticPeriod.WEEK,
        )

    async def _get_sensors_statistic_by_period(
        self,
        room_id: int,
        filters: RoomStatisticFiltersSchema,
        period: SensorStatisticPeriod,
    ) -> RoomStatisticGetSchema:
        filters.from_datetime = ensure_utc(filters.from_datetime)
        filters.to_datetime = ensure_utc(filters.to_datetime)

        models = await self.repository.get_sensors_statistic(
            room_id,
            filters,
            SENSOR_STATISTIC_TABLE_BY_PERIOD[period],
        )

        return RoomStatisticGetSchema.model_validate({'data': models})

    async def calculate_space(
        self,
        room_id: int,
        config: dict[str, Any],
    ) -> dict[str, Any]:
        await self.repository.get_one(room_id=room_id)

        return self.space_constructor.calculate(config)

    async def validate_space(
        self,
        room_id: int,
        config: dict[str, Any],
    ) -> dict[str, Any]:
        calculation = await self.calculate_space(room_id=room_id, config=config)

        return {
            "is_valid": not calculation["warnings"],
            "warnings": calculation["warnings"],
            "calculation": calculation,
        }
```
### Комментарий
В данном примере получилось рассмотреть реализацию отдельного сервиса который отвечает за расчеты внутри помещения. 
В целом первичная реализация выглядит достаточно исчерпывающей на первый взгляд, однако если подумать об этом сервисе с точки зрения его природы и цели, появляется желание обратить его в удобный изолированный блок кода который позволяет представить все расчеты с точки зрения геометрического 3D пространста, в котором расположены какие-либо объекты на определенных координатах (на текущий момент только сенсоры).


## Заключение
Реализация кода в соответствии с логикой дизайна с одной стороны показалось не совсем сложным концептом как со стороны осознания, так и со стороны реализации - достаточно подумать о системе более высокоуровнево, после чего откроется возможность продумать потоки данных и способы их контроля с точки зрения кода. 
С другой же стороны, абстрагирование от текущей реализации и примерка нового дизайна на дизайн существующий, это то что заставляет мысли немного покипеть, так как чаще всего необходимо произвести операцию встраивания какого-то изменения в существующую систему, а так как изменение происходит на более высоком уровне чем просто код, переложить эту миграцию дизайна на код не является операцией быстрой.
На опыте выполнения данной задачи, было не легко попробовать переосмыслить дизайн в уже сформированных сущностях (репозиториев, сервисов, адаптерров и т.д.), но всё же не невозможно. 
Стоит также упоминуть моменты когда не удавалось найти материал для переосмысления дизайна так как объем кода был менее сотни строк, искать хорошие примеры из реальной практики в подобных случаях это большая работа и удача.    

