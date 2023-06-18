import io
import unittest
import unittest.mock
from ..Task_6 import *


class TestTask6(unittest.TestCase):
    @unittest.mock.patch('sys.stdout', new_callable=io.StringIO)
    def assert_print_even_indexed_items(self, lst: list, expected_output, mock_stdout):
        print_even_indexed_items(lst)
        self.assertEqual(mock_stdout.getvalue(), expected_output)

    # ### TEST PRINT_EVEN_INDEXED_ITEMS() FUNCTION
    def test_print_even_indexed_items__1(self):
        self.assert_print_even_indexed_items([1, 2, 3], '1 3 ')

    def test_print_even_indexed_items__2(self):
        self.assert_print_even_indexed_items([1, 2], '1 ')

    def test_print_even_indexed_items__3(self):
        self.assert_print_even_indexed_items([1], '1 ')

    def test_print_even_indexed_items__4(self):
        self.assert_print_even_indexed_items([], '')

    def test_print_even_indexed_items__5(self):
        self.assert_print_even_indexed_items(['cat', 2, 3], 'cat 3 ')

    def test_print_even_indexed_items__6(self):
        self.assert_print_even_indexed_items(['cat', 2, 3, 4, 5.44], 'cat 3 5.44 ')

    def test_print_even_indexed_items__7(self):
        self.assert_print_even_indexed_items(['cat', 2, None, 4, None], 'cat None None ')


if __name__ == '__main__':
    unittest.main()
