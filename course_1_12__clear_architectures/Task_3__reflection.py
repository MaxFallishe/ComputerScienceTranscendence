# Reflection on the completed second task - Implement robot control using OOP, the way you understood it.

# In implementing the cleaning robot program through OOP, I tried to implement the outline of the program with maximum
# expectation of scalability and understandable use of the created logic. I have identified two entities - a Robot and
# a Command for work, designed them into separate classes. In the case of the robot, I did not create an abstract class,
# although in general it will not be difficult to do this with the current setup. The internal content of the robot
# class turned out to be quite compact - only pre-defined characteristics of the robot (degree of rotation, position
# on the x/y-axis, etc.), a method for starting the execution of instructions, a method for parsing instructions
# as a whole with separate lines (implying a command + arguments) and a method for parsing the selected line into
# a full-fledged command. The main logic inside the robot is to parse instructions into specific commands,
# all commands that we allow the robot to execute through the "self.available_commands" attribute, and thanks
# to automatic verification of the correctness of arguments (their number and type) on the side of the command class,
# we reject all incorrect commands (for example, with missing mandatory arguments). The essence of the commands was
# implemented through an abstract class, where in the abstract class I laid out the methods and fields necessary for
# implementation in the heirs. The main logic is centered around validating arguments through "_validate_raw_arguments"
# and "_generate_command_arguments". Inside each class, we list the names and type of arguments we need
# for a specific type of command, for example, in the case of the "move" command,
# we need the float parameter "distance__meters", then in the main "do_action" method of the command,
# we implement the command logic. Also, through the __init_subclass__ dander method, the uniqueness
# of the AbstractRobotCommand heirs is checked to avoid ambiguity when, for example, one of the developers wants
# to give a new command a keyword that already exists, for example, "move" again. Probably the main advantage
# of the current implementation is its extensibility. With the current setup, we can create new robots with our own
# unique commands based on the implementation of the current cleaning robot and easily add new commands through
# the built-in structure and logic. The disadvantages in the current implementation can be understood as the size
# and complexity of the architecture, it is difficult to store all the code in only one file, and understanding
# the underlying logic for implementing a new command requires some time to comprehend, I would also subjectively call
# the current system of storing arguments and accessing them a disadvantage
# now it looks like this - "self.arguments["distance__meters"]" I would like to implement them as full-fledged
# class attributes, although you need to understand that they are dynamic in nature from the point of view
# of the abstract parent.
