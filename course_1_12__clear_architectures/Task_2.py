"""Simple program in a procedural style for controlling a cleaning robot."""
import math
from typing import Any


def init_clean_robot() -> dict:
    clean_robot_state = {
        "x_coordinate": 0.0,
        "y_coordinate": 0.0,
        "current_direction_angle": 0,
        "clean_mode": "WATER",
        "is_cleaning": False,
    }
    return clean_robot_state


def parse_clean_robot_instruction(instruction: str) -> list[tuple[str, Any]]:
    commands = []
    for line in instruction.splitlines():
        if not (tokens := line.split()):
            continue
        command_name = tokens[0]
        command_arg = tokens[1] if len(tokens) == 2 else None
        commands.append((command_name, command_arg))
    return commands


def execute_commands_by_robot(robot: dict, commands: list, ) -> None:
    for command in commands:
        match command[0]:
            case "move":
                robot_move(robot, float(command[1]))
            case "turn":
                robot_turn(robot, int(command[1]))
            case "set":
                set_clean_method(robot, str(command[1]))
            case "start":
                start_clean_process_by_robot(robot)
            case "stop":
                end_clean_process_by_robot(robot)
            case _:
                print(f"this robot do not know command: {command.name}")


def robot_move(robot: dict, distance__meters: float) -> None:
    current_angle__radians = robot["current_direction_angle"] * (math.pi / 180)
    robot["x_coordinate"] += distance__meters * math.cos(current_angle__radians)
    robot["y_coordinate"] += distance__meters * math.sin(current_angle__radians)
    robot["x_coordinate"] = round(robot["x_coordinate"], 2)
    robot["y_coordinate"] = round(robot["y_coordinate"], 2)
    print(f"POS {robot["x_coordinate"]},{robot["y_coordinate"]}")


def robot_turn(robot: dict, angle__degrees: int) -> None:
    robot["current_direction_angle"] = (robot["current_direction_angle"] + angle__degrees) % 360
    print(f"TURN {robot["current_direction_angle"]}")


def set_clean_method(robot: dict, clean_mode: str) -> None:
    if clean_mode not in (avaialable_modes := ["WATER", "SOAP", "BRUCH"]):
        print(f"STATE cannot be set to {clean_mode}, it can only be {avaialable_modes}")
        return
    robot["clean_mode"] = clean_mode
    print(f"STATE {robot["clean_mode"]}")


def start_clean_process_by_robot(robot: dict) -> None:
    robot["is_cleaning"] = True
    print(f"START WITH {robot.get("clean_mode")}")


def end_clean_process_by_robot(robot: dict) -> None:
    robot["is_cleaning"] = False
    print(f"STOP")


instruction_tetta = """
move 100
turn -90
set soap
start
move 50
stop
"""

clean_robot = init_clean_robot()
robot_commands = parse_clean_robot_instruction(instruction_tetta)
execute_commands_by_robot(clean_robot, robot_commands)
