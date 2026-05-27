from dataclasses import dataclass, field
from enum import Enum
import math
from typing import Protocol


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


class RobotEvent(Protocol):
    def execute(self, state: RobotState) -> RobotState:
        pass

    def log(self) -> None:
        pass


@dataclass
class MoveRobotCommand:
    distance: float

    def execute(self, state: RobotState) -> RobotState:
        angle_rads = state.angle * (math.pi / 180.0)
        return RobotState(
            x=state.x + self.distance * math.cos(angle_rads),
            y=state.y + self.distance * math.sin(angle_rads),
            angle=state.angle,
            state=state.state
        )

    def log(self) -> None:
        print(f'MOVE {self.distance}')


@dataclass
class TurnRobotCommand:
    angle: float

    def execute(self, state: RobotState) -> RobotState:
        return RobotState(
            x=state.x,
            y=state.y,
            angle=state.angle + self.angle,
            state=state.state
        )

    def log(self) -> None:
        print(f'TURN {self.angle}')


@dataclass
class SetStateRobotCommand:
    new_state: CleaningMode

    def execute(self, state: RobotState) -> RobotState:
        return RobotState(
            x=state.x,
            y=state.y,
            angle=state.angle,
            state=self.new_state.value
        )

    def log(self) -> None:
        print(f'SET_STATE {self.new_state.name}')


@dataclass
class StartRobotCommand:
    def execute(self, state: RobotState) -> RobotState:
        return state

    def log(self) -> None:
        print('START')


@dataclass
class StopRobotCommand:
    def execute(self, state: RobotState) -> RobotState:
        return state

    def log(self) -> None:
        print('STOP')


@dataclass
class RobotEventStore:
    events: list[RobotEvent] = field(default_factory=list)


class RobotCommandHandler:

    def __init__(self):
        self.event_store = RobotEventStore()

    def push_event(self, event: RobotEvent):
        self.event_store.events.append(event)

    def render_events(self):
        state = RobotState(0.0, 0.0, 0, CleaningMode.WATER.value)
        for event in self.event_store.events:
            new_state = event.execute(state=state)
            event.log()
            state = new_state
        print("<-------------------- RENDER END -------------------->")



def main():
    robot = RobotCommandHandler()
    robot.push_event(MoveRobotCommand(100))
    robot.push_event(TurnRobotCommand(-90))
    robot.push_event(SetStateRobotCommand(CleaningMode.SOAP))
    robot.push_event(StartRobotCommand())
    robot.push_event(MoveRobotCommand(50))
    robot.push_event(StopRobotCommand())

    robot.render_events()

    robot.push_event(StartRobotCommand())
    robot.push_event(MoveRobotCommand(50))
    robot.push_event(MoveRobotCommand(100))
    robot.push_event(StopRobotCommand())

    robot.render_events()

