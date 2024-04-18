import unittest
from course_1_5__inheritance_composition_polymorphism.Task_2__introduction import UnicellularOrganism, Amoeba, Cell


class TestUnicellularOrganism(unittest.TestCase):
    def test_attributes__1(self):
        alien_unicellular = UnicellularOrganism()
        result = alien_unicellular.membrane_size__micron
        self.assertEqual(result, 1.0)

    def test_attributes__2(self):
        alien_unicellular = UnicellularOrganism(membrane_size__micron=5.3)
        result = alien_unicellular.membrane_size__micron
        self.assertEqual(result, 5.3)

    def test_absorb_element_operation__1(self):
        alien_unicellular = UnicellularOrganism(cell_division_speed__microseconds=2.3)
        result = alien_unicellular.absorb_element('rock')
        self.assertEqual(result, None)

    def test_absorb_element_operation__2(self):
        alien_unicellular = UnicellularOrganism()
        result = alien_unicellular.absorb_element('rock')
        self.assertEqual(result, None)


class TestAmoeba(unittest.TestCase):
    def test_attributes__1(self):
        alien_unicellular = Amoeba()
        result = alien_unicellular.membrane_size__micron
        self.assertEqual(result, 1.0)

    def test_attributes__2(self):
        alien_unicellular = Amoeba(membrane_size__micron=5.3)
        result = alien_unicellular.membrane_size__micron
        self.assertEqual(result, 5.3)

    def test_absorb_another_cell__1(self):
        alien_unicellular = Amoeba()
        result = alien_unicellular.absorb_another_cell()
        self.assertEqual(result, None)

    def test_absorb_another_cell__2(self):
        alien_unicellular = Amoeba()
        alien_unicellular.absorb_element('rock')
        result = alien_unicellular.absorb_another_cell()
        self.assertEqual(result, None)

    def test_absorb_element__1(self):
        alien_unicellular = Amoeba()
        result = alien_unicellular.absorb_element('rock')
        self.assertEqual(result, None)


class TestCell(unittest.TestCase):
    def test_attributes__1(self):
        alien_unicellular = Cell()
        result = alien_unicellular.membrane_size__micron
        self.assertEqual(result, 1.0)

    def test_attributes__2(self):
        alien_unicellular = Cell(membrane_size__micron=5.3)
        result = alien_unicellular.membrane_size__micron
        self.assertEqual(result, 5.3)

    def test_absorb_element__1(self):
        alien_unicellular = Cell()
        result = alien_unicellular.absorb_element('rock')
        self.assertEqual(result, None)
