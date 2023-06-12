import unittest
from ..Task_1 import *


class TestTask1(unittest.TestCase):
    # ### TEST GET_NUMBER_IN_POWER() FUNCTION
    def test_get_number_in_power__1(self):
        result = get_number_in_power(2, 4)
        self.assertEqual(result, 16)

    def test_get_number_in_power__2(self):
        result = get_number_in_power(3, 4)
        self.assertEqual(result, 81)

    def test_get_number_in_power__3(self):
        result = get_number_in_power(0, 4)
        self.assertEqual(result, 0)

    def test_get_number_in_power__4(self):
        result = get_number_in_power(0, 0)
        self.assertEqual(result, 1)

    def test_get_number_in_power__5(self):
        result = get_number_in_power(-2, 4)
        self.assertEqual(result, 16)

    def test_get_number_in_power__6(self):
        result = get_number_in_power(-2, 5)
        self.assertEqual(result, -32)

    def test_get_number_in_power__7(self):
        result = get_number_in_power(-2, -3)
        self.assertEqual(result, -0.125)

    def test_get_number_in_power__8(self):
        result = get_number_in_power(2, -3)
        self.assertEqual(result, 0.125)

    def test_get_number_in_power__9(self):
        result = get_number_in_power(4, -3)
        self.assertEqual(result, 0.015625)

    def test_get_number_in_power__10(self):
        result = get_number_in_power(5, -5)
        result = round(result, 5)
        self.assertEqual(result, 0.00032)


if __name__ == '__main__':
    unittest.main()
