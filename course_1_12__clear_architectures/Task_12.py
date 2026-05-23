from functools import partial, reduce
import pure_robot


def instructions_to_commands(robot_instructions: tuple) -> list[partial]:
    robot_commands_sequence = []
    for line in robot_instructions:
        tokens = line.split()
        if tokens[0] == 'move':
            command = partial(pure_robot.move, transfer=pure_robot.transfer_to_cleaner, dist=int(tokens[1]))
        elif tokens[0] == 'turn':
            command = partial(pure_robot.turn, transfer=pure_robot.transfer_to_cleaner, turn_angle=int(tokens[1]))
        elif tokens[0] == 'set':
            command = partial(pure_robot.set_state, transfer=pure_robot.transfer_to_cleaner,
                              new_internal_state=tokens[1])
        elif tokens[0] == 'start':
            command = partial(pure_robot.start, transfer=pure_robot.transfer_to_cleaner)
        elif tokens[0] == 'stop':
            command = partial(pure_robot.stop, transfer=pure_robot.transfer_to_cleaner)
        else:
            continue
        robot_commands_sequence.append(command)
    return robot_commands_sequence


def run_robot_commands(robot_commands: list[partial]) -> None:
    initial_state = pure_robot.RobotState(0.0, 0.0, 0, pure_robot.WATER)
    return reduce(lambda x, y: y(state=x), [initial_state] + robot_commands)


instructions = (
    'move 100',
    'turn -90',
    'set soap',
    'start',
    'move 50',
    'stop',
)

commands = instructions_to_commands(instructions)
run_robot_commands(commands)
