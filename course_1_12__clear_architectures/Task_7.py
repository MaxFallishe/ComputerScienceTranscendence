import pure_robot


class ClientCleanerApi:

    def __init__(self, robot_service):
        self.robot_service = robot_service
        self.cleaner_state = self.robot_service.RobotState(0.0, 0.0, 0, pure_robot.WATER)

    def transfer_to_cleaner(self, message):
        print(message)

    def get_x(self):
        return self.cleaner_state.x

    def get_y(self):
        return self.cleaner_state.y

    def get_angle(self):
        return self.cleaner_state.angle

    def get_state(self):
        return self.cleaner_state.state

    def activate_cleaner(self, code):
        for command in code:
            cmd = command.split(' ')
            if cmd[0] == 'move':
                self.cleaner_state = self.robot_service.move(self.transfer_to_cleaner, int(cmd[1]), self.cleaner_state)
            elif cmd[0] == 'turn':
                self.cleaner_state = self.robot_service.turn(self.transfer_to_cleaner, int(cmd[1]), self.cleaner_state)
            elif cmd[0] == 'set':
                self.cleaner_state = self.robot_service.set_state(self.transfer_to_cleaner, cmd[1], self.cleaner_state)
            elif cmd[0] == 'start':
                self.cleaner_state = self.robot_service.start(self.transfer_to_cleaner, self.cleaner_state)
            elif cmd[0] == 'stop':
                self.cleaner_state = self.robot_service.stop(self.transfer_to_cleaner, self.cleaner_state)


cleaner_api = ClientCleanerApi(robot_service=pure_robot)

cleaner_api.activate_cleaner(
    ('move 100',
     'turn -90',
     'set soap',
     'start',
     'move 50',
     'stop')
)

print(cleaner_api.get_x(), cleaner_api.get_y(), cleaner_api.get_angle(), cleaner_api.get_state())
