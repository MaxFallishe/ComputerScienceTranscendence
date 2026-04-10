"""Script that simulates a simple DSL for controlling a cleaning robot."""
from dataclasses import dataclass
from enum import StrEnum


@dataclass
class RobotCommandArgument:
    name: str
    type_: type

    def __post_init__(self):
        if not isinstance(self.name, str):
            raise TypeError("'name' must be str")
        if not isinstance(self.type_, type):
            raise TypeError("'type_' must be type")


@dataclass
class RobotCommand:
    name: str
    argument: RobotCommandArgument | None


class RobotCleanMode(StrEnum):
    WATER = "water"
    SOAP = "soap"
    BRUSH = "brush"


class Robot:
    def __init__(self):
        self.x_coordinate: int = 0
        self.y_coordinate: int = 0
        self.current_direction_angle: int = 0  # 0 - North, 90 - east, 180 - South, 270 - west
        self.clean_mode: RobotCleanMode = RobotCleanMode.WATER

    def execute_instruction(self, instruction: str):
        commands = self._parse_instruction(instruction)
        for command in commands:
            match command.name:
                case "move":
                    self.move(int(command.argument))
                case "turn":
                    self.turn(int(command.argument))
                case "set":
                    self.set_clean_method(RobotCleanMode(command.argument))
                case "start":
                    self.start_clean_process()
                case "stop":
                    self.end_clean_process()
                case _:
                    print(f"this robot do not know command: {command.name}")

    @staticmethod
    def _parse_instruction(instruction: str) -> list[RobotCommand]:
        commands = []
        for line in instruction.splitlines():
            if not line.split():
                continue
            command_name, *argument = line.split()
            argument = argument.pop() if argument else None
            commands.append(RobotCommand(command_name, argument))

        return commands

    def move(self, distance__meters: int) -> None:
        if distance__meters <= 0:
            raise ValueError("distance__meters must be positive")
        if self.current_direction_angle == 0:
            self.x_coordinate += distance__meters
        elif self.current_direction_angle == 90:
            self.y_coordinate += distance__meters
        elif self.current_direction_angle == 180:
            self.x_coordinate -= distance__meters
        elif self.current_direction_angle == 270:
            self.y_coordinate -= distance__meters
        print(f"POS {self.x_coordinate},{self.y_coordinate}")

    def turn(self, angle__degrees: int) -> None:
        if angle__degrees % 90 != 0:
            raise ValueError("angle__degrees must be multiple of 90")
        self.current_direction_angle = (self.current_direction_angle + angle__degrees) % 360
        print(f"TURN {self.current_direction_angle}")

    def set_clean_method(self, clean_mode: RobotCleanMode):
        self.clean_mode = clean_mode
        print(f"STATE {self.clean_mode.name}")

    def start_clean_process(self):
        print(f"START WITH {self.clean_mode}")

    @staticmethod
    def end_clean_process():
        print(f"STOP")


robot = Robot()
instruction_tetta = """
move 100
turn -90
set soap
start
move 50
stop
"""
robot.execute_instruction(instruction=instruction_tetta)
