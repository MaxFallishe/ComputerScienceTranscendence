import os
import unittest
from ..Task_8 import *


class TestTask7(unittest.TestCase):
    # ### TEST GET_ALL_FILES() FUNCTION
    def test_get_all_files__1(self):
        result = set(get_all_files('.'))
        reference = set([name for root, dirs, files in os.walk('.') for name in files])
        self.assertEqual(result, reference)

    def test_get_all_files__2(self):
        self.assertRaises(FileNotFoundError, get_all_files, '')



if __name__ == '__main__':
    unittest.main()
