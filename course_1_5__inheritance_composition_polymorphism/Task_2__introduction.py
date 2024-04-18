from typing import Self
# The formal approach to inheritance implies three fundamentally different possibilities:
# -- extension of the parent class (the heir sets a more general case of the parent);
# -- specialization of the parent class (the heir sets a more specialized case of the parent);
# -- a combination of several parent classes.


# The initial class that we will inherit from and demonstrate different types of inheritance in practice
# This class represents a generalized unicellular organism that has some attributes,
# such as how thick the membrane is, and methods such as division.
class UnicellularOrganism:
    def __init__(self,
                 membrane_size__micron: float = 1.0,
                 cell_division_speed__microseconds: float = 2.3,
                 motion_speed__micron_microsec: float = 5.1):
        self.membrane_size__micron = membrane_size__micron
        self.cell_division_speed__microseconds = cell_division_speed__microseconds
        self.motion_speed__micron_microsec = motion_speed__micron_microsec
        ...

    def absorb_element(self, element) -> bool:
        ...

    def regurgitate_element(self, element: str) -> bool:
        ...

    def divide(self) -> (Self, Self):
        ...


# The amoeba class in this case is an example of specialization inheritance,
# since we clarified that it is not just a single-celled organism, but already a specific single-celled animal.
# The amoeba differs in that it has a method that allows it to absorb other unicellular.
class Amoeba(UnicellularOrganism):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)

    def absorb_another_cell(self) -> bool:
        ...


# The cell class represents the inheritance of the extension of the parent class, since now the
# Abstract cell data type potentially accommodates not only unicellular organisms, but also cells of multicellular
# creatures.  What is the difference between a cell in this case, for example,
# in that it has the function of self-destruction - for the benefit of other cells of a multicellular organism
class Cell(UnicellularOrganism):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)

    def self_destruct(self) -> None:
        ...
