# cleaner_api.py
from functools import reduce
from types import ModuleType

import pure_robot


def get_robot_container() -> ModuleType:
    return pure_robot


def transfer_to_cleaner(message):
    print(message)


def activate_cleaner(code: tuple[str]) -> None:
    commands = [command.split(' ') for command in code] + [None]
    reduce(apply_command, commands)


def apply_command(acc, cmd):
    if not isinstance(acc, get_robot_container().RobotState):
        acc = execute_robot_action(acc)
    if cmd is None:
        return acc
    return execute_robot_action(cmd, robot_state=acc)


def execute_robot_action(cmd: list,
                         robot_state: get_robot_container().RobotState = None) -> get_robot_container().RobotState:
    if robot_state is None:
        robot_state = get_robot_container().RobotState(0.0, 0.0, 0, get_robot_container().WATER)

    if cmd[0] == 'move':
        robot_state = get_robot_container().move(transfer_to_cleaner, int(cmd[1]), robot_state)
    elif cmd[0] == 'turn':
        robot_state = get_robot_container().turn(transfer_to_cleaner, int(cmd[1]), robot_state)
    elif cmd[0] == 'set':
        robot_state = get_robot_container().set_state(transfer_to_cleaner, cmd[1], robot_state)
    elif cmd[0] == 'start':
        robot_state = get_robot_container().start(transfer_to_cleaner, robot_state)
    elif cmd[0] == 'stop':
        robot_state = get_robot_container().stop(transfer_to_cleaner, robot_state)

    return robot_state


# client.py
activate_cleaner(('move 100', 'turn -90', 'set soap', 'start', 'move 50', 'stop'))
