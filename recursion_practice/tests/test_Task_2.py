import unittest
from ..Task_2 import *


class TestTask2(unittest.TestCase):
    # ### TEST GET_NUMBER_DIGITS_SUM() FUNCTION
    def test_get_number_digits_sum__1(self):
        result = get_number_digits_sum(1925)
        self.assertEqual(result, 17)

    def test_get_number_digits_sum__2(self):
        result = get_number_digits_sum(1)
        self.assertEqual(result, 1)

    def test_get_number_digits_sum__3(self):
        result = get_number_digits_sum(2000)
        self.assertEqual(result, 2)

    def test_get_number_digits_sum__4(self):
        result = get_number_digits_sum(99_999)
        self.assertEqual(result, 45)

    def test_get_number_digits_sum__5(self):
        result = get_number_digits_sum(-99_999)
        self.assertEqual(result, 45)

    def test_get_number_digits_sum__6(self):
        result = get_number_digits_sum(-500)
        self.assertEqual(result, 5)

    def test_get_number_digits_sum__7(self):
        result = get_number_digits_sum(-911)
        self.assertEqual(result, 11)


if __name__ == '__main__':
    unittest.main()
