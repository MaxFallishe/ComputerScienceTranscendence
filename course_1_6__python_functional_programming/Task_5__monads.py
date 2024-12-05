from pymonad.operators.list import ListMonad
from pymonad.operators.maybe import Just, Nothing, Maybe
from pymonad.tools import curry

# Solving the problem

# A certain tightrope walker walks a rope with a pole, but birds like to sit on the pole, and then they can fly away.
# A tightrope walker can keep his balance if the difference in the number of birds on the sides of the pole is no more
# than 4. Well, a tightrope walker can simply fall slipping on banana peel. It is necessary to simulate a simulation
# of a sequence of events with the output of the result in the form of "fell"/"I didn't fall."
banana = lambda x: Nothing
begin = lambda: Just((0, 0))


@curry(2)
def to_left(num, left_and_right):
    if abs(left_and_right[0] + num - left_and_right[1]) > 4:
        return Nothing
    return Just((left_and_right[0] + num, left_and_right[1]))


@curry(2)
def to_right(num, left_and_right):
    if abs((left_and_right[1] + num) - left_and_right[0]) > 4:
        return Nothing
    return Just((left_and_right[0], left_and_right[1] + num))


@curry(1)
def show(x):
    if x == Nothing:
        print("Fell")
        return
    print("Didn't fell")


# Fell
show(
    begin()
    .bind(to_right(1))
    .bind(to_right(2))
    .bind(banana)  # Fell here
)

# Didn't fell
show(
    begin()
    .bind(to_right(1))
    .bind(to_right(2))
    .bind(to_left(3))
)

# Fell
show(
    begin()
    .bind(to_right(1))
    .bind(to_right(4))  # Fell here
    .bind(to_right(2))
)
