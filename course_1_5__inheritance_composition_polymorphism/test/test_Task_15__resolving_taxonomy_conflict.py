import sys
import unittest
from io import StringIO

from ..Task_15__resolving_taxonomy_conflict import *


class TestMartian(unittest.TestCase):
    def test_init(self):
        martian = Martian()
        self.assertEqual(martian.home_planet, "Mars")

    def test_talk(self):
        martian = Martian()
        captured_output = StringIO()
        sys.stdout = captured_output
        martian.talk()
        sys.stdout = sys.__stdout__
        self.assertEqual(captured_output.getvalue().strip(), "blop-blop, I'm from Mars")

    def test_walk(self):
        captured_output = StringIO()
        sys.stdout = captured_output
        martian = Martian()
        martian.walk()
        sys.stdout = sys.__stdout__
        self.assertEqual(captured_output.getvalue().strip(), "This creature just walking")


class TestVenusian(unittest.TestCase):
    def test_init(self):
        venusian = Venusian()
        self.assertEqual(venusian.home_planet, "Venus")

    def test_talk(self):
        venusian = Venusian()
        captured_output = StringIO()
        sys.stdout = captured_output
        venusian.talk()
        sys.stdout = sys.__stdout__
        self.assertEqual(captured_output.getvalue().strip(), "blop-blop, I'm from Venus")

    def test_eye_lasers_shot(self):
        captured_output = StringIO()
        sys.stdout = captured_output
        venusian = Venusian()
        venusian.eye_lasers_shot()
        sys.stdout = sys.__stdout__
        self.assertEqual(captured_output.getvalue().strip(), "Poof")


class TestPlutonian(unittest.TestCase):
    def test_init(self):
        plutonian = Plutonian()
        self.assertEqual(plutonian.home_planet, "Pluto")

    def test_talk(self):
        plutonian = Plutonian()
        captured_output = StringIO()
        sys.stdout = captured_output
        plutonian.talk()
        sys.stdout = sys.__stdout__
        self.assertEqual(captured_output.getvalue().strip(), "c#1!* (#(@$, I'm from Pluto")


if __name__ == '__main__':
    unittest.main()
