from __future__ import annotations

import math
from dataclasses import dataclass
from enum import StrEnum


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


class RobotCleaner:
    def __init__(
            self,
            x: float = 0.0,
            y: float = 0.0,
            angle__degrees: int = 0,
            clean_mode: RobotCleanMode = RobotCleanMode.WATER
    ):
        self.__robot_state = RobotCleanerState(x=x, y=y, angle__degrees=angle__degrees, clean_mode=clean_mode)

    def move(self, distance: int) -> RobotCleaner:
        angle_rads = self.__robot_state.angle__degrees * (math.pi / 180.0)
        print(f"MOVE SUCCEED")
        return RobotCleaner(
            x=self.__robot_state.x + distance * math.cos(angle_rads),
            y=self.__robot_state.y + distance * math.sin(angle_rads),
            angle__degrees=self.__robot_state.angle__degrees,
            clean_mode=self.__robot_state.clean_mode,
        )

    def turn(self, angle__degrees) -> RobotCleaner:
        print(f"TURN SUCCEED")
        return RobotCleaner(
            x=self.__robot_state.x,
            y=self.__robot_state.y,
            angle__degrees=(self.__robot_state.angle__degrees + angle__degrees) % 360,
            clean_mode=self.__robot_state.clean_mode,
        )

    def change_clean_mode(self, clean_mode: RobotCleanMode) -> RobotCleaner:
        print(f"CHANGE CLEAN MODE ON {self.__robot_state.clean_mode}")
        return RobotCleaner(
            x=self.__robot_state.x,
            y=self.__robot_state.y,
            angle__degrees=self.__robot_state.angle__degrees,
            clean_mode=clean_mode,
        )

    def start(self) -> RobotCleaner:
        print(f"START WITH {self.__robot_state.clean_mode}")
        return RobotCleaner(
            x=self.__robot_state.x,
            y=self.__robot_state.y,
            angle__degrees=self.__robot_state.angle__degrees,
            clean_mode=self.__robot_state.clean_mode,
        )

    def stop(self) -> RobotCleaner:
        print(f"STOP")
        return RobotCleaner(
            x=self.__robot_state.x,
            y=self.__robot_state.y,
            angle__degrees=self.__robot_state.angle__degrees,
            clean_mode=self.__robot_state.clean_mode,
        )

    def get_x_value(self) -> float:
        return self.__robot_state.x

    def get_y_value(self) -> float:
        return self.__robot_state.y

    def get_angle_value(self) -> int:
        return self.__robot_state.angle__degrees

    def get_clean_mode_value(self) -> RobotCleanMode:
        return self.__robot_state.clean_mode


robot_zeldo = RobotCleaner()
robot_zeldo_after_quick_clean = (robot_zeldo
                                 .move(100)
                                 .turn(300)
                                 .move(20)
                                 .change_clean_mode(RobotCleanMode.SOAP)
                                 .start()
                                 .move(30)
                                 .stop()
                                 )

final_state = robot_zeldo_after_quick_clean.move(99).move(99).move(99)

print(final_state.get_x_value(), final_state.get_y_value(), final_state.get_clean_mode_value())
