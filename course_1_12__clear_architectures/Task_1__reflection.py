# Reflection on the completed first task - implement the cleaning robot program in any way you like.

# In the process of initially thinking about the structure of the program, I intuitively wanted to immediately define
# several basic entities through which it would be possible to automatically validate the correctness of the program
# entered by the user and easily expand the current program logic. Regarding validation, it immediately seemed to me
# that it was necessary to set the boundaries/restrictions that each "command" for the robot should correspond to,
# and besides this it would be possible to accurately check the types so that a runtime error would not occur when
# the robot stopped after the "move yyy" command, as it would expect an argument simply in the form of a number.
# From the point of view of extensibility, it seemed convenient to me to create a class with methods and several
# dataclasses as a clearer designation of the main entities of the program. Compared to the presented implementation
# of the first version of the program, my solution has a different implementation of motion logic - the robot can only
# move to the right, left, forward and backward relative to the starting point.
