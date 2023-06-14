import unittest
from ..Task_3 import *


class TestTask3(unittest.TestCase):
    # ### TEST GET_NUMBER_DIGITS_SUM() FUNCTION
    def test_get_list_len__1(self):
        result = get_list_len([1, 2, 3])
        self.assertEqual(result, 3)

    def test_get_list_len__2(self):
        result = get_list_len([1])
        self.assertEqual(result, 1)

    def test_get_list_len__3(self):
        result = get_list_len([])
        self.assertEqual(result, 0)

    def test_get_list_len__4(self):
        result = get_list_len([1, "2", None, {}])
        self.assertEqual(result, 4)

    def test_get_list_len__5(self):
        result = get_list_len([None, None])
        self.assertEqual(result, 2)



if __name__ == '__main__':
    unittest.main()
