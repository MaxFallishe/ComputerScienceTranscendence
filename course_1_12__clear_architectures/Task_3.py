"""Simple program in an object-oriented style for controlling a cleaning robot."""

from __future__ import annotations

import math
from abc import abstractmethod, ABC
from dataclasses import dataclass
from enum import StrEnum
from typing import Any


# --- robot entities ---
class RobotCleanMode(StrEnum):
    WATER = "water"
    SOAP = "soap"
    BRUSH = "brush"


class CleanRobot:
    def __init__(self):
        self.x_coordinate: float = 0.0
        self.y_coordinate: float = 0.0
        self.current_direction_angle: int = 0
        self.clean_mode: RobotCleanMode = RobotCleanMode.WATER
        self.available_commands: list[type[AbstractRobotCommand]] = [
            MoveCommand,
            TurnCommand,
            SetCleanMethodCommand,
            StartCleanCommand,
            StopCleanCommand,
        ]

    def do_instruction(self, instruction: str) -> None:
        commands = self._parse_instruction(instruction)
        for command in commands:
            command.do_action(robot=self)

    def _parse_instruction(self, instruction: str) -> list[AbstractRobotCommand]:
        commands = []
        for line in instruction.splitlines():
            if not line.split():
                continue
            if command := self._parse_command(line):
                commands.append(command)
        return commands

    def _parse_command(self, line: str) -> AbstractRobotCommand | None:
        tokens = line.split()
        if len(tokens) == 0:
            return None

        command_name = tokens[0]
        command_arguments = tokens[1:]
        if command_name not in [command.name for command in self.available_commands]:
            return None

        for available_command in self.available_commands:
            if command_name != available_command.name:
                continue
            return available_command(command_arguments)  # we can here instead of raise make just some error print

        return None


# --- commands entities ---
@dataclass
class RobotCommandArgument:
    name: str
    type_: type[int] | type[float] | type[str] | type[RobotCleanMode]


class AbstractRobotCommand(ABC):
    _registered_names: set[str] = set()

    def __init__(self, raw_arguments: list[Any]):
        self._validate_raw_arguments(raw_arguments)
        self.arguments: dict[str, Any] = self._generate_command_arguments(raw_arguments)

    def __init_subclass__(cls, **kwargs):
        super().__init_subclass__(**kwargs)

        if cls.name in cls._registered_names:
            raise TypeError(f"Command name '{cls.name}' must be unique")

        cls._registered_names.add(cls.name)

    @property
    @abstractmethod
    def name(self) -> str:
        ...

    @property
    @abstractmethod
    def required_arguments(self) -> list[RobotCommandArgument]:
        ...

    @abstractmethod
    def do_action(self, robot: CleanRobot) -> None:
        ...

    def _validate_raw_arguments(self, raw_arguments) -> None:
        if len(raw_arguments) != len(self.required_arguments):
            raise ValueError("Count of mandatory arguments for command should be equal to count of required_arguments.")

    def _generate_command_arguments(self, raw_arguments) -> dict[str, Any]:
        arguments: dict[str, Any] = {}
        for argument, argument_template in zip(raw_arguments, self.required_arguments):
            arguments[argument_template.name] = argument_template.type_(argument)
        return arguments


class MoveCommand(AbstractRobotCommand):
    name: str = "move"
    required_arguments: list[RobotCommandArgument] = [
        RobotCommandArgument("distance__meters", float),
    ]

    def do_action(self, robot: CleanRobot) -> None:
        current_angle__radians = robot.current_direction_angle * (math.pi / 180)
        robot.x_coordinate += self.arguments["distance__meters"] * math.cos(current_angle__radians)
        robot.y_coordinate += self.arguments["distance__meters"] * math.sin(current_angle__radians)
        robot.x_coordinate = round(robot.x_coordinate, 2)
        robot.y_coordinate = round(robot.y_coordinate, 2)
        print(f"POS {robot.x_coordinate},{robot.y_coordinate}")


class TurnCommand(AbstractRobotCommand):
    name: str = "turn"
    required_arguments: list[RobotCommandArgument] = [
        RobotCommandArgument("angle__degrees", int),
    ]

    def do_action(self, robot: CleanRobot) -> None:
        robot.current_direction_angle = (robot.current_direction_angle + self.arguments["angle__degrees"]) % 360
        print(f"TURN {robot.current_direction_angle}")


class SetCleanMethodCommand(AbstractRobotCommand):
    name: str = "set"
    required_arguments: list[RobotCommandArgument] = [
        RobotCommandArgument("clean_mode", RobotCleanMode),
    ]

    def do_action(self, robot: CleanRobot) -> None:
        robot.clean_mode = self.arguments["clean_mode"]
        print(f"STATE {robot.clean_mode}")


class StartCleanCommand(AbstractRobotCommand):
    name: str = "start"
    required_arguments: list[RobotCommandArgument] = []

    def do_action(self, robot: CleanRobot) -> None:
        print(f"START WITH {robot.clean_mode}")


class StopCleanCommand(AbstractRobotCommand):
    name: str = "stop"
    required_arguments: list[RobotCommandArgument] = []

    def do_action(self, robot: CleanRobot) -> None:
        print(f"STOP")


instruction_tetta = """
move 100
turn -90
set soap
start
move 50
stop
"""

clean_robot = CleanRobot()
clean_robot.do_instruction(instruction_tetta)
