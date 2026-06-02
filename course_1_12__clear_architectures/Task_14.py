import math
from abc import ABC, abstractmethod
from dataclasses import dataclass
from enum import Enum
from typing import Protocol, Optional


@dataclass
class RobotState:
    x: float
    y: float
    angle: float
    state: int


class CleaningMode(Enum):
    WATER = 1
    SOAP = 2
    BRUSH = 3


class AbstractProcessor(ABC):
    @abstractmethod
    def apply(self, state: RobotState) -> RobotState:
        pass

    @abstractmethod
    def get_event_type(self) -> str:
        pass


@dataclass
class RobotMovedProcessor(AbstractProcessor):
    distance: float

    def apply(self, state: RobotState) -> RobotState:
        angle_rads = state.angle * (math.pi / 180.0)
        return RobotState(
            x=state.x + self.distance * math.cos(angle_rads),
            y=state.y + self.distance * math.sin(angle_rads),
            angle=state.angle,
            state=state.state
        )

    def get_event_type(self) -> str:
        return f'ROBOT_MOVED {self.distance}'


@dataclass
class RobotTurnedProcessor(AbstractProcessor):
    angle: float

    def apply(self, state: RobotState) -> RobotState:
        return RobotState(
            x=state.x,
            y=state.y,
            angle=state.angle + self.angle,
            state=state.state
        )

    def get_event_type(self) -> str:
        return f'ROBOT_TURNED {self.angle}'


@dataclass
class RobotStateChangedProcessor(AbstractProcessor):
    new_state: CleaningMode

    def apply(self, state: RobotState) -> RobotState:
        return RobotState(
            x=state.x,
            y=state.y,
            angle=state.angle,
            state=self.new_state.value
        )

    def get_event_type(self) -> str:
        return f'ROBOT_STATE_CHANGED {self.new_state.name}'


@dataclass
class RobotStartedProcessor(AbstractProcessor):
    def apply(self, state: RobotState) -> RobotState:
        return state

    def get_event_type(self) -> str:
        return 'ROBOT_STARTED'


@dataclass
class RobotStoppedProcessor(AbstractProcessor):
    def apply(self, state: RobotState) -> RobotState:
        return state

    def get_event_type(self) -> str:
        return 'ROBOT_STOPPED'


class Command(Protocol):
    def handle(self) -> list[AbstractProcessor]:
        pass

    def get_command_type(self) -> str:
        pass


@dataclass
class MoveCommand:
    distance: float

    def handle(self) -> list[AbstractProcessor]:
        return [RobotMovedProcessor(self.distance)]

    def get_command_type(self) -> str:
        return f'MOVE {self.distance}'


@dataclass
class TurnCommand:
    angle: float

    def handle(self) -> list[AbstractProcessor]:
        return [RobotTurnedProcessor(self.angle)]

    def get_command_type(self) -> str:
        return f'TURN {self.angle}'


@dataclass
class SetStateCommand:
    new_state: CleaningMode

    def handle(self) -> list[AbstractProcessor]:
        return [RobotStateChangedProcessor(self.new_state)]

    def get_command_type(self) -> str:
        return f'SET_STATE {self.new_state.name}'


@dataclass
class StartCommand:
    def handle(self) -> list[AbstractProcessor]:
        return [RobotStartedProcessor()]

    def get_command_type(self) -> str:
        return 'START'


@dataclass
class StopCommand:
    def handle(self) -> list[AbstractProcessor]:
        return [RobotStoppedProcessor()]

    def get_command_type(self) -> str:
        return 'STOP'


class EventStore:
    def __init__(self):
        self._events: dict[str, list[AbstractProcessor]] = {}
        self._state: RobotState = RobotState(0.0, 0.0, 0, CleaningMode.WATER.value)

    def append_events(self, robot_id: str, events: list[AbstractProcessor]) -> None:
        if robot_id not in self._events:
            self._events[robot_id] = []
        self._events[robot_id].extend(events)

    def get_events(self, robot_id: str) -> list[AbstractProcessor]:
        return self._events.get(robot_id, [])


class CommandHandler:
    def __init__(self, event_store: EventStore):
        self._event_store = event_store
        self.subscribed_processors: list[Command] = []

    def handle_command(self, robot_id: str, command: Command):
        new_event = command.handle()
        if not new_event:
            return
        if type(new_event[0]) not in self.subscribed_processors:
            print("SKIP: No processors that subscribed to this event")
            return
        self._event_store.append_events(robot_id, new_event)


def main():
    event_store = EventStore()
    command_handler = CommandHandler(event_store)
    command_handler.subscribed_processors += [
        RobotMovedProcessor,
        RobotTurnedProcessor,
        RobotStateChangedProcessor,
        RobotStartedProcessor,
        RobotStoppedProcessor,
    ]

    robot_id = "robot_001"

    commands = [
        MoveCommand(100),
        TurnCommand(-90),
        SetStateCommand(CleaningMode.SOAP),
        StartCommand(),
        MoveCommand(50),
        StopCommand()
    ]

    for i, cmd in enumerate(commands):
        print(f"Cmd {i + 1}: {cmd.get_command_type()}")
        command_handler.handle_command(robot_id, cmd)

    events = event_store.get_events(robot_id)
    for i, event in enumerate(events):
        print(f"Event {i + 1}: {event.get_event_type()}")
