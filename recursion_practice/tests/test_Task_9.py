import os
import unittest
from ..Task_9 import *


class TestTask9(unittest.TestCase):
    # ### TEST generate_parenthesis_front() FUNCTION
    def test_generate_parenthesis_front__1(self):
        result = generate_parenthesis_front(0)
        self.assertEqual(result, None)

    def test_generate_parenthesis_front__2(self):
        self.assertRaises(TypeError, len, generate_parenthesis_front(0))

    def test_generate_parenthesis_front__3(self):
        result = generate_parenthesis_front(1)
        reference = ["()"]
        self.assertEqual(result, reference)

    def test_generate_parenthesis_front__4(self):
        result = len(generate_parenthesis_front(1))
        reference = 1
        self.assertEqual(result, reference)

    def test_generate_parenthesis_front__5(self):
        result = generate_parenthesis_front(2)
        reference = ["(())", "()()"]
        result = set(result)
        reference = set(reference)
        self.assertEqual(result, reference)

    def test_generate_parenthesis_front__6(self):
        result = len(generate_parenthesis_front(2))
        reference = 2
        self.assertEqual(result, reference)

    def test_generate_parenthesis_front__7(self):
        result = generate_parenthesis_front(3)
        reference = ["()()()", "()(())", "(())()", "(()())", "((()))"]
        result = set(result)
        reference = set(reference)
        self.assertEqual(result, reference)

    def test_generate_parenthesis_front__8(self):
        result = len(generate_parenthesis_front(3))
        reference = 5
        self.assertEqual(result, reference)

    def test_generate_parenthesis_front__9(self):
        result = generate_parenthesis_front(4)
        reference = ["()()()()", "()()(())", "()(())()", "()(()())", "()((()))", "(())()()", "(())(())", "(()())()", "((()))()", "(()()())", "(()(()))", "((())())", "((()()))", "(((())))"]
        result = set(result)
        reference = set(reference)
        self.assertEqual(result, reference)

    def test_generate_parenthesis_front__10(self):
        result = len(generate_parenthesis_front(4))
        reference = 14
        self.assertEqual(result, reference)


if __name__ == '__main__':
    unittest.main()
