# Type Inheritance is useful in the following cases:
# -- it implies several criteria for classifying some parent entity, and it is not possible to single out
#    the single most important one of them;
# -- it implies an active combination of these criteria (different values of positions and work agreements);
# -- the parent entity of each hierarchy plays a very serious role in the project, and it is very important
#    to think through its AD specification qualitatively.


# The main hierarchy is the type of game edition
class AbstractGameEdition:
    ...


class RegularGameEdition(AbstractGameEdition):
    ...


class UltraGameEdition(AbstractGameEdition):
    ...


# Additional separate hierarchy - Game platform (PC, Xbox, Playstation, etc.)
class AbstractGamePlatform:
    ...


class PCPlatform(AbstractGamePlatform):
    ...


class XboxPlatform(AbstractGamePlatform):
    ...


# Implementation of Type Inheritance
class Dishonored2Game(UltraGameEdition):
    def __init__(self):
        self.platform = XboxPlatform

