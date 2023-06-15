import unittest
from ..Task_4 import *


class TestTask4(unittest.TestCase):
    # ### TEST IS_STRING_PALINDROME() FUNCTION
    def test_is_string_palindrome__1(self):
        result = is_string_palindrome('ABA')
        self.assertTrue(result)

    def test_is_string_palindrome__2(self):
        result = is_string_palindrome('ABBA')
        self.assertTrue(result)

    def test_is_string_palindrome__3(self):
        result = is_string_palindrome('')
        self.assertTrue(result)

    def test_is_string_palindrome__4(self):
        result = is_string_palindrome('ABABBABA')
        self.assertTrue(result)

    def test_is_string_palindrome__5(self):
        result = is_string_palindrome('ABC')
        self.assertFalse(result)

    def test_is_string_palindrome__6(self):
        result = is_string_palindrome('AABCAA')
        self.assertFalse(result)


if __name__ == '__main__':
    unittest.main()
