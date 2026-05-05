import pure_robot
from types import ModuleType


class RobotApi:

    def setup(self, robot_funcs: ModuleType):
        self.funcs = robot_funcs

    def make(self, command):
        if not hasattr(self, 'cleaner_state'):
            self.cleaner_state = pure_robot.RobotState(0.0, 0.0, 0, pure_robot.WATER)

        cmd = command.split(' ')
        if cmd[0] == 'move':
            self.cleaner_state = self.funcs.move(self.funcs.transfer_to_cleaner, int(cmd[1]), self.cleaner_state)
        elif cmd[0] == 'turn':
            self.cleaner_state = self.funcs.turn(self.funcs.transfer_to_cleaner, int(cmd[1]), self.cleaner_state)
        elif cmd[0] == 'set':
            self.cleaner_state = self.funcs.set_state(self.funcs.transfer_to_cleaner, cmd[1], self.cleaner_state)
        elif cmd[0] == 'start':
            self.cleaner_state = self.funcs.start(self.funcs.transfer_to_cleaner, self.cleaner_state)
        elif cmd[0] == 'stop':
            self.cleaner_state = self.funcs.stop(self.funcs.transfer_to_cleaner, self.cleaner_state)
        return self.cleaner_state

    def __call__(self, command):
        return self.make(command)


api = RobotApi()
api.setup(pure_robot)

# from cleaner_api import api

api('move 100')
api('turn -90')
api('set soap')
api('start')
api('move 50')
s = api('stop')
