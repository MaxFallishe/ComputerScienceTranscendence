# In this file, you can look at an example of dynamic linking in python. As experimental classes, let's take the class
# of a Screwdriver and a Drill, where the latter is the heir. The rest of the comments and explanations below are in
# the file also in the comment format.

import functools


class Screwdriver:
    def __init__(self,
                 manufacturer_title: str,
                 revolutions__number_per_second: float,
                 durability__num_of_uses: int = 5
                 ):
        self.manufacturer_title = manufacturer_title
        self.revolutions__number_per_second = revolutions__number_per_second
        self.durability__num_of_uses = durability__num_of_uses
        self.remains_num_of_uses = durability__num_of_uses

    def durability_validation(func):
        @functools.wraps(func)
        def wrapper(self, *args, **kwargs):
            if self.remains_num_of_uses <= 0:
                print(f"The {self.__class__.__name__} is worn out. Durability: {self.remains_num_of_uses}/{self.durability__num_of_uses}")
                return
            return func(self, *args, **kwargs)
        return wrapper

    @durability_validation
    def use(self):
        self.remains_num_of_uses -= 1
        print("You have successfully screwed in the screw")


class Puncher(Screwdriver):
    @Screwdriver.durability_validation
    def use(self):
        self.remains_num_of_uses -= 1
        print("You have successfully drilled a hole in the wall.")


# In this situation, you can see that after changing the value in the variable, the program dynamically replaced the
# object with the correct one and the output will be "You have successfully drilled a hole in the wall."
first_tool = Screwdriver('makita', 5)
second_tool = Puncher('makita', 7)
first_tool = second_tool
first_tool.use()

# However, in python as a whole there is no static linking and only dynamic linking works



