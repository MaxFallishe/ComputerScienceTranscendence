from typing import overload, override, Self, Callable


# Implementation inheritance
class ChessPawn: ...

class ChessKing: ...


class ChessGame:
    def __init__(self, cells_xy_num: tuple[int, int], cells_types_num: list[type], figures_types: list[type]):
        self.cells_xy_num: tuple = cells_xy_num
        self.cells_types_num: list[type] = cells_types_num
        self.figures_types: list[type] = figures_types

    def move_figure(self):
        ...

    def _create_field(self) -> list[list]:
        return [['-' for i in range(self.cells_xy_num[0])] for _ in range(self.cells_xy_num[1])]


class DynamicChessGame(ChessGame):  # Chess the playing field in which is constantly changing
    def __init__(self, init_cells_xy_num: tuple[int, int], cells_types_num: list[type], figures_types: list[type]):
        super().__init__(init_cells_xy_num, cells_types_num, figures_types)

    def _displacement_figures_to_field(self):
        ...

    def _change_field(self):
        new_field = self._create_field()
        self._displacement_figures_to_field()


# Facility inheritance
# The ancestor class contains some standard set of components combined into one for convenience.
# His descendants implement specific special cases.
class Ant:
    def __init__(self):
        self.height__mm: float = 5.0

    def do_jaws_bite(self) -> None:
        pass

    def grab_load(self, load) -> None:
        pass

    def leave_load(self) -> None:
        pass

    def reproduce_offspring(self) -> Callable[[Self], None]:
        pass


class AntWorker(Ant):
    def __init__(self):
        super().__init__()
        self.load = None

    @override
    def grab_load(self, load):
        self.load = load

    @override
    def leave_load(self):
        if not self.load:
            pass
        self.load = None


class AntQueen(Ant):
    def __init__(self):
        super().__init__()
        self.height__mm: float = 12.0

    @override
    def reproduce_offspring(self) -> Callable[[Self], None]:
        return AntWorker()
