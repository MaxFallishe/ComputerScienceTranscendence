from typing import Any, Callable

import pure_robot


class ClientCleanerApi:

    def __init__(self, robot_service):
        self.robot_service = robot_service
        self.cleaner_state = self.robot_service.RobotState(0.0, 0.0, 0, pure_robot.WATER)

    def transfer_to_cleaner(self, message):
        print(message)

    def activate_cleaner(self, stream):
        tokens = stream.split(' ')
        argument = None
        for token in tokens:
            if token == 'move':
                func_to_execute = self.robot_service.move
            elif token == 'turn':
                func_to_execute = self.robot_service.turn
            elif token == 'set':
                func_to_execute = self.robot_service.set_state
            elif token == 'start':
                func_to_execute = self.robot_service.start
            elif token == 'stop':
                func_to_execute = self.robot_service.stop
            else:
                argument = token
                continue

            self.cleaner_state = self.run_command(func_to_execute, argument)
            argument = None

    def run_command(self, command: Callable, argument: Any):
        if not argument:
            return command(self.transfer_to_cleaner, self.cleaner_state)
        if command.__name__ in ["turn", "move"]:
            argument = int(argument)
        return command(self.transfer_to_cleaner, argument, self.cleaner_state)


cleaner_api = ClientCleanerApi(robot_service=pure_robot)

commands_stream = "100 move -90 turn soap set start 50 move stop"
cleaner_api.activate_cleaner(commands_stream)
