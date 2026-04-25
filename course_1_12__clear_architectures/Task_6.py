from functools import reduce

import pure_robot


def transfer_to_cleaner(message):
    print(message)


def activate_cleaner(code: tuple[str]) -> None:
    commands = [command.split(' ') for command in code] + [None]
    reduce(apply_command, commands)


def apply_command(acc, cmd):
    if not isinstance(acc, pure_robot.RobotState):
        acc = execute_robot_action(acc)
    if cmd is None:
        return acc
    return execute_robot_action(cmd, robot_state=acc)


def execute_robot_action(cmd: list, robot_state: pure_robot.RobotState = None) -> pure_robot.RobotState:
    if robot_state is None:
        robot_state = pure_robot.RobotState(0.0, 0.0, 0, pure_robot.WATER)

    if cmd[0] == 'move':
        robot_state = pure_robot.move(transfer_to_cleaner, int(cmd[1]), robot_state)
    elif cmd[0] == 'turn':
        robot_state = pure_robot.turn(transfer_to_cleaner, int(cmd[1]), robot_state)
    elif cmd[0] == 'set':
        robot_state = pure_robot.set_state(transfer_to_cleaner, cmd[1], robot_state)
    elif cmd[0] == 'start':
        robot_state = pure_robot.start(transfer_to_cleaner, robot_state)
    elif cmd[0] == 'stop':
        robot_state = pure_robot.stop(transfer_to_cleaner, robot_state)

    return robot_state


activate_cleaner((
    'move 100',
    'turn -90',
    'set soap',
    'start',
    'move 50',
    'stop'
))
