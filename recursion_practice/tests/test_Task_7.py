import unittest
from ..Task_7 import *


class TestTask7(unittest.TestCase):
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
