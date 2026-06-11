import math
from dataclasses import dataclass, replace
from enum import StrEnum
from typing import Callable

from pymonad.state import State
from pymonad.tools import curry


class RobotCleanMode(StrEnum):
    WATER = "water"
    SOAP = "soap"
    BRUSH = "brush"


@dataclass(frozen=True)
class RobotCleanerState:
    x: float
    y: float
    angle__degrees: int
    clean_mode: RobotCleanMode
    is_started: bool = False
    error: str | None = None


Predicate = Callable[[RobotCleanerState], bool]


def is_started(state: RobotCleanerState) -> bool:
    return state.is_started


def is_stopped(state: RobotCleanerState) -> bool:
    return not state.is_started


def pure(state: RobotCleanerState, message: str) -> State:
    return State(lambda log: (state, log))


def fail(old_state: RobotCleanerState, message: str) -> State:
    def state_computation(log):
        new_state = replace(old_state, error=message)
        new_log = log + [f"ERROR: {message}"]
        return new_state, new_log
    return State(state_computation)


def require(
    command_key: str,
    command_name: str,
    action: Callable[[RobotCleanerState], State]
):
    def guarded(old_state: RobotCleanerState) -> State:
        if old_state.error is not None:
            return pure(old_state)

        predicate = ALLOWED_MODE[command_key]
        if not predicate(old_state):
            return fail(
                old_state,
                f'command {command_name} is not allowed with current state'
            )

        return action(old_state)
    return guarded



ALLOWED_MODE: dict[str, Predicate] = {
    "start": is_stopped,
    "stop": is_started,
    "move": lambda x: True,
    "turn": lambda x: True,
    "set_clean_mode": lambda x: True,
}


def transfer_to_cleaner(message):
    print(message)
    return None


@curry(2)
def move(dist, old_state) -> State:
    def action(state: RobotCleanerState) -> State:
        def state_computation(log):
            angle_rads = state.angle__degrees * (math.pi / 180.0)

            new_state = RobotCleanerState(
                x=state.x + dist * math.cos(angle_rads),
                y=state.y + dist * math.sin(angle_rads),
                angle__degrees=state.angle__degrees,
                clean_mode=state.clean_mode,
                is_started=state.is_started,
                error=state.error,
            )

            command = "POS(%d,%d)" % (new_state.x, new_state.y)
            new_log = log + [command]

            return new_state, new_log
        return State(state_computation)
    return require("move", f"move {dist}", action)(old_state)


@curry(2)
def turn(turn_angle, old_state) -> State:
    def action(state: RobotCleanerState) -> State:
        def state_computation(log):
            new_state = RobotCleanerState(
                x=state.x,
                y=state.y,
                angle__degrees=state.angle__degrees + turn_angle,
                clean_mode=state.clean_mode,
                is_started=state.is_started,
                error=state.error,
            )

            command = "ANGLE %d" % new_state.angle__degrees
            new_log = log + [command]

            return new_state, new_log
        return State(state_computation)
    return require("turn", f"turn {turn_angle} degrees", action)(old_state)


@curry(2)
def set_state(cleaner_state, old_state) -> State:
    def action(state: RobotCleanerState) -> State:
        def state_computation(log):
            new_state = RobotCleanerState(
                x=state.x,
                y=state.y,
                angle__degrees=state.angle__degrees,
                clean_mode=cleaner_state,
            )

            command = "CLEANER STATE %s" % cleaner_state
            new_log = log + [command]

            return new_state, new_log
        return State(state_computation)
    return require("set_clean_mode", f"clean mode set to {cleaner_state}", action)(old_state)



def start(old_state):
    def action(state: RobotCleanerState) -> State:
        def state_computation(log):
            new_state = RobotCleanerState(
                x=state.x,
                y=state.y,
                angle__degrees=state.angle__degrees,
                clean_mode=state.clean_mode,
                is_started=True,
                error=state.error,
            )
            new_log = log + ["START"]

            return new_state, new_log
        return State(state_computation)
    return require("start", f"start", action)(old_state)


def stop(old_state):
    def action(state: RobotCleanerState) -> State:
        def state_computation(log):
            new_state = RobotCleanerState(
                x=state.x,
                y=state.y,
                angle__degrees=state.angle__degrees,
                clean_mode=state.clean_mode,
                is_started=False,
                error=state.error,
            )
            new_log = log + ["STOP"]

            return new_state, new_log
        return State(state_computation)
    return require("stop", f"stop", action)(old_state)


x = (
    State.insert(RobotCleanerState(0.0, 0.0, 0, RobotCleanMode.WATER))
    .bind(move(100))
    .bind(turn(-90))
    .bind(set_state(RobotCleanMode.SOAP))
    .bind(start)
    .bind(move(50))
    .bind(stop)
    .bind(stop)
)

robot_state, commands = x.run([])

print(robot_state)
print("\n".join(commands))
