import unittest
from ..Task_10 import *


class TestTask10(unittest.TestCase):
    # ### TEST FIND_MEET_CHANCE() FUNCTION
    def test_find_meet_chance__1(self):
        result = find_meet_chance(32)
        self.assertEqual(result, 0.0625)

    def test_find_meet_chance__2(self):
        result = find_meet_chance(16)
        self.assertEqual(result, 0.125)

    def test_find_meet_chance__3(self):
        result = find_meet_chance(8)
        self.assertEqual(result, 0.25)

    def test_find_meet_chance__4(self):
        result = find_meet_chance(4)
        self.assertEqual(result, 0.5)

    def test_find_meet_chance__5(self):
        result = find_meet_chance(2)
        self.assertEqual(result, 1)

    def test_find_meet_chance__6(self):
        result = find_meet_chance(64)
        self.assertEqual(result, 0.03125)


if __name__ == '__main__':
    unittest.main()
