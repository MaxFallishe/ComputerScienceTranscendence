import unittest
from ..Task_7 import *


class TestTask7(unittest.TestCase):
    # ### TEST GET_LARGEST_NUMBERS_SUBLIST() FUNCTION
    def test_get_largest_numbers_sublist__1(self):
        result = get_largest_numbers_sublist([3, 4, 5], [1, 2])
        self.assertEqual(result, [4, 5])

    def test_get_largest_numbers_sublist__2(self):
        result = get_largest_numbers_sublist([1, 1, 1], [1, 2])
        self.assertEqual(result, [1, 2])

    def test_get_largest_numbers_sublist__3(self):
        result = get_largest_numbers_sublist([0, 1, 0], [0, 0])
        self.assertEqual(result, [0, 1])

    def test_get_largest_numbers_sublist__4(self):
        result = get_largest_numbers_sublist([1, 2, 3], [4, 5])
        self.assertEqual(result, [4, 5])

    def test_get_largest_numbers_sublist__5(self):
        result = get_largest_numbers_sublist([], [1, 2])
        self.assertEqual(result, [1, 2])

    def test_get_largest_numbers_sublist__6(self):
        result = get_largest_numbers_sublist([5], [1, 2])
        self.assertEqual(result, [2, 5])

    def test_get_largest_numbers_sublist__7(self):
        result = get_largest_numbers_sublist([1, 2, 3], [1])
        self.assertEqual(result, [3])

    def test_get_largest_numbers_sublist__8(self):
        result = get_largest_numbers_sublist([4, 5, 1, 2], [1, 2, 3])
        self.assertEqual(result, [3, 4, 5])

    def test_get_largest_numbers_sublist__9(self):
        result = get_largest_numbers_sublist([1, 2, 3], [])
        self.assertEqual(result, [])

    def test_get_largest_numbers_sublist__10(self):
        result = get_largest_numbers_sublist([], [])
        self.assertEqual(result, [])

    def test_get_largest_numbers_sublist__11(self):
        result = get_largest_numbers_sublist([], [1, 2, 3])
        self.assertEqual(result, [1, 2, 3])

    # ### TEST GET_SECOND_LARGEST_NUMBER() FUNCTION
    def test_get_second_largest_number__1(self):
        result = get_second_largest_number([1, 2, 3, 4])
        self.assertEqual(result, 3)

    def test_get_second_largest_number__2(self):
        result = get_second_largest_number([4, 1, 2, 3])
        self.assertEqual(result, 3)

    def test_get_second_largest_number__3(self):
        result = get_second_largest_number([1, 2, 4, 4])
        self.assertEqual(result, 4)

    def test_get_second_largest_number__4(self):
        result = get_second_largest_number([4, 4])
        self.assertEqual(result, 4)

    def test_get_second_largest_number__5(self):
        result = get_second_largest_number([])
        self.assertEqual(result, None)

    def test_get_second_largest_number__6(self):
        result = get_second_largest_number([4])
        self.assertEqual(result, None)



if __name__ == '__main__':
    unittest.main()
