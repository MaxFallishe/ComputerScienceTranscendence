import io
import unittest
import unittest.mock
from ..Task_5 import *


class TestTask5(unittest.TestCase):
    @unittest.mock.patch('sys.stdout', new_callable=io.StringIO)
    def assert_print_even_numse(self, lst: list, expected_output, mock_stdout):
        print_even_nums(lst)
        self.assertEqual(mock_stdout.getvalue(), expected_output)

    # ### TEST PRINT_EVEN_NUMS() FUNCTION
    def test_print_even_numse__1(self):
        self.assert_print_even_numse([1, 2, 3], '2 ')

    def test_print_even_numse__2(self):
        self.assert_print_even_numse([4, 2, 1], '4 2 ')

    def test_print_even_numse__3(self):
        self.assert_print_even_numse([1, 1, 3, 5], '')

    def test_print_even_numse__4(self):
        self.assert_print_even_numse([0, 4, 33, 22], '0 4 22 ')

    def test_print_even_numse__5(self):
        self.assert_print_even_numse([-1, -2, -3], '-2 ')

    def test_print_even_numse__6(self):
        self.assert_print_even_numse([-1, -5, -1], '')

    def test_print_even_numse__7(self):
        self.assert_print_even_numse([0, -2, 22], '0 -2 22 ')

    def test_print_even_numse__8(self):
        self.assert_print_even_numse([-22], '-22 ')


if __name__ == '__main__':
    unittest.main()
