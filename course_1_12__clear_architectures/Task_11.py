from dataclasses import dataclass
from functools import partial, reduce
from typing import Callable

import pure_robot


@dataclass
class RobotMonad:
    state: pure_robot.RobotState
    func: Callable[..., pure_robot.RobotState]

    def __post_init__(self) -> None:
        if self.state is None:
            self.state = pure_robot.RobotState(0.0, 0.0, 0, pure_robot.WATER)


instructions = (
    'move 100',
    'turn -90',
    'set soap',
    'start',
    'move 50',
    'stop',
)

monads_chain = []

for line in instructions:
    tokens = line.split(' ')
    if tokens[0] == 'move':
        premonad_func = partial(pure_robot.move, transfer=pure_robot.transfer_to_cleaner, dist=int(tokens[1]))
    elif tokens[0] == 'turn':
        premonad_func = partial(pure_robot.turn, transfer=pure_robot.transfer_to_cleaner, turn_angle=int(tokens[1]))
    elif tokens[0] == 'set':
        premonad_func = partial(pure_robot.set_state, transfer=pure_robot.transfer_to_cleaner,
                                new_internal_state=tokens[1])
    elif tokens[0] == 'start':
        premonad_func = partial(pure_robot.start, transfer=pure_robot.transfer_to_cleaner)
    elif tokens[0] == 'stop':
        premonad_func = partial(pure_robot.stop, transfer=pure_robot.transfer_to_cleaner)
    else:
        continue

    monads_chain.append(RobotMonad(state=None, func=premonad_func))

monad_chain_executor = reduce(lambda x, y: RobotMonad(state=x.func(state=x.state), func=y.func),
                              monads_chain + [RobotMonad(func=None, state=None)])
print("result: ", monad_chain_executor.state)
