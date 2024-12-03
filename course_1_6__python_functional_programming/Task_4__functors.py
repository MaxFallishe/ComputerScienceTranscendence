import math

from pymonad.list import ListMonad
from pymonad.maybe import Maybe, Just, Nothing
from pymonad.tools import curry


@curry(2)
def add(x, y):
    return x + y


@curry(1)
def add10(x):
    return add(10)(x)


# A Powerful feature of functors is the ability to use them in deferred application.
# Based on the add() function, we will make the add10() function with one Just argument,
# which adds 10 to the Just or List argument.

deca_value = Maybe.apply(add10).to_arguments(Just(2))
print(deca_value)  # Output: 12

deca_values = ListMonad(1, 2, 3, 4).map(add10)
print(deca_values)  # Output: [11, 12, 13, 14]
