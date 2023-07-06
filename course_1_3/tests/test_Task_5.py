from ..Task_5 import *
import unittest


class TestGenerateBBSTArray(unittest.TestCase):
    def test_generate_bbst_array__1(self):
        mock_input_arr = [1]
        result = GenerateBBSTArray(mock_input_arr)
        reference = [1]
        self.assertEqual(result, reference)

    def test_generate_bbst_array__2(self):
        mock_input_arr = [3]
        result = GenerateBBSTArray(mock_input_arr)
        reference = [3]
        self.assertEqual(result, reference)

    def test_generate_bbst_array__3(self):
        mock_input_arr = [1, 2, 3]
        result = GenerateBBSTArray(mock_input_arr)
        reference = [2, 1, 3]
        self.assertEqual(result, reference)

    def test_generate_bbst_array__4(self):
        mock_input_arr = [2, 1, 3]
        result = GenerateBBSTArray(mock_input_arr)
        reference = [2, 1, 3]
        self.assertEqual(result, reference)

    def test_generate_bbst_array__5(self):
        mock_input_arr = [22, 38, 11]
        result = GenerateBBSTArray(mock_input_arr)
        reference = [22, 11, 38]
        self.assertEqual(result, reference)

    def test_generate_bbst_array__6(self):
        mock_input_arr = [1, 2, 3, 4, 5, 6, 7]
        result = GenerateBBSTArray(mock_input_arr)
        reference = [4, 2, 6, 1, 3, 5, 7]
        self.assertEqual(result, reference)

    def test_generate_bbst_array__7(self):
        mock_input_arr = [5, 2, 3, 7, 1, 6, 4]
        result = GenerateBBSTArray(mock_input_arr)
        reference = [4, 2, 6, 1, 3, 5, 7]
        self.assertEqual(result, reference)

    def test_generate_bbst_array__8(self):
        mock_input_arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
        result = GenerateBBSTArray(mock_input_arr)
        reference = [8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15]
        self.assertEqual(result, reference)

    def test_generate_bbst_array__9(self):
        mock_input_arr = [1, 2, 9, 4, 5, 6, 7, 8, 3, 10, 15, 12, 13, 14, 11]
        result = GenerateBBSTArray(mock_input_arr)
        reference = [8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15]
        self.assertEqual(result, reference)

    def test_generate_bbst_array__10(self):
        mock_input_arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31]
        result = GenerateBBSTArray(mock_input_arr)
        reference = [16, 8, 24, 4, 12, 20, 28, 2, 6, 10, 14, 18, 22, 26, 30, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31]
        self.assertEqual(result, reference)

    def test_generate_bbst_array__11(self):
        mock_input_arr = [27, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 12, 13, 14, 11, 16, 17, 18, 19, 20, 26, 22, 23, 24, 25, 21, 1, 28, 29, 30, 31]
        result = GenerateBBSTArray(mock_input_arr)
        reference = [16, 8, 24, 4, 12, 20, 28, 2, 6, 10, 14, 18, 22, 26, 30, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31]
        self.assertEqual(result, reference)
