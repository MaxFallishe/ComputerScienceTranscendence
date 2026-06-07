from __future__ import annotations

import math
from abc import ABC
from dataclasses import dataclass
from enum import Enum


@dataclass
class RobotState:
    x: float
    y: float
    angle: int
    clean_mode: CleaningMode


class CleaningMode(Enum):
    WATER = 1
    SOAP = 2
    BRUSH = 3


class BaseRobotASTInterpretator:
    @staticmethod
    def run(cmd: Command):
        actual_cmd = cmd
        robot_state = RobotState(0, 0, 0, CleaningMode.WATER)
        while True:
            if type(actual_cmd) is StopCommand:
                return actual_cmd.interpret(robot_state)

            robot_state = actual_cmd.interpret(robot_state)
            actual_cmd = actual_cmd.next_cmd


class Command(ABC):
    def __init__(self):
        self.next_cmd = None

    def interpret(self, state: RobotState) -> RobotState:
        ...

    def next_(self, cmd: Command) -> Command:
        self.next_cmd = cmd
        return self


class MoveCommand(Command):
    def __init__(self, distance: int):
        super().__init__()
        self.distance = distance

    def interpret(self, old_state: RobotState) -> RobotState:
        angle_rads = old_state.angle * (math.pi / 180.0)
        new_state = RobotState(
            x=old_state.x + self.distance * math.cos(angle_rads),
            y=old_state.y + self.distance * math.sin(angle_rads),
            angle=old_state.angle,
            clean_mode=old_state.clean_mode
        )
        print(f"MOVE: {new_state.__dict__}")
        return new_state


class TurnCommand(Command):
    def __init__(self, angle: int):
        super().__init__()
        self.angle = angle

    def interpret(self, old_state: RobotState) -> RobotState:
        new_state = RobotState(
            old_state.x,
            old_state.y,
            old_state.angle + self.angle,
            old_state.clean_mode,
        )
        print(f"TURN: {new_state.__dict__}")
        return new_state


class SetCleanModeCommand(Command):
    def __init__(self, clean_mode: CleaningMode):
        super().__init__()
        self.clean_mode = clean_mode

    def interpret(self, old_state: RobotState) -> RobotState:
        new_state = RobotState(
            old_state.x,
            old_state.y,
            old_state.angle,
            self.clean_mode,
        )
        print(f"SET CLEAТ MODE: {new_state.__dict__}")
        return new_state


class StartCommand(Command):
    def __init__(self):
        super().__init__()

    def interpret(self, old_state: RobotState) -> RobotState:
        print(f"START: {old_state.__dict__}")
        return old_state


class StopCommand(Command):
    def __init__(self):
        super().__init__()

    def interpret(self, old_state: RobotState) -> RobotState:
        print(f"STOP: {old_state.__dict__}")
        return old_state


start_command = StartCommand()

start_command.next_(
    MoveCommand(distance=90).next_(
        TurnCommand(angle=180).next_(
            MoveCommand(distance=5).next_(
                SetCleanModeCommand(clean_mode=CleaningMode.SOAP).next_(
                    MoveCommand(distance=50).next_(
                        StopCommand()
                    )
                )
            )
        )
    )
)

interpretator = BaseRobotASTInterpretator()
interpretator.run(start_command)
