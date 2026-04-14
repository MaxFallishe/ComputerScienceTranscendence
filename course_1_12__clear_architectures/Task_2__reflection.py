# Reflection on the completed second task - implement robot control now using functions, the way you understood it.

# When implementing the task in a procedural style, I wanted to literally put all the logic into the procedures,
# even those related to potentially global values, such as the current state of work -
# what are its current coordinates, cleaning mode, etc. Therefore, I transferred the creation of the robot entity
# with all its characteristics to a separate function "init_clean_robot()" and then passed the resulting dictionary
# with values to each of the functions used. Globally, the same algorithm turned out as in the previous solution -
# to parse the existing text into specific commands, execute each of the commands in the prescribed manner.
# Compared to the proposed implementation option, it's nice that it turned out to fit everything exclusively
# inside procedures without a global state - each of the command procedures accepts the "robot" argument
# with the current characteristics of the cleaning robot. And yet, despite the fairly good structure
# of the resulting program, it is very much lacking, in my opinion, to add a clearer definition
# of the characteristics and logic of entities through types.
