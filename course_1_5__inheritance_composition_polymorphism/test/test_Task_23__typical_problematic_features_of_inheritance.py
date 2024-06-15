# In the course of inheritance, preconditions may weaken, and postconditions, on the contrary, may strengthen.
#
# For an example of these principles, we can consider a hierarchy where the square is inherited from the parallelogram.
# In this case, we can afford to weaken the postcondition for the "calculate_area" operation of the square, which
# in the original (the parallelogram sounds like "The width and length of the figure must be positive numbers",
# now this postcondition may sound like "The width or length of the figure must be positive numbers" since the width
# of the square is equal to the length - it cannot there may be situations when either the width will be negative or
# the length. Accordingly, the "calculate_area" method will always work correctly even in a polymorphic object.
#
# To demonstrate the impossibility of weakening postconditions and possibly strengthening them, it is possible to give
# the following example: The "get_sides_ratio" method, in the same hierarchy where the square is inherited from the
# parallelogram, the postcondition will return a positive rational number, while the square postcondition for the
# "get_sides_ratio" method will change to return an integer - one.
