import os
import unittest
from ..Task_9 import *


class TestTask9(unittest.TestCase):
    # ### TEST GENERATE_PARENTHESIS() FUNCTION
    def test_generate_parenthesis__1(self):
        result = generate_parenthesis(0)
        reference = [""]
        result = set(result)
        reference = set(reference)
        self.assertEqual(result, reference)

    def test_generate_parenthesis__2(self):
        result = len(generate_parenthesis(0))
        reference = 1
        self.assertEqual(result, reference)

    def test_generate_parenthesis__3(self):
        result = generate_parenthesis(1)
        reference = ["()"]
        self.assertEqual(result, reference)

    def test_generate_parenthesis__4(self):
        result = len(generate_parenthesis(1))
        reference = 1
        self.assertEqual(result, reference)

    def test_generate_parenthesis__5(self):
        result = generate_parenthesis(2)
        reference = ["(())", "()()"]
        result = set(result)
        reference = set(reference)
        self.assertEqual(result, reference)

    def test_generate_parenthesis__6(self):
        result = len(generate_parenthesis(2))
        reference = 2
        self.assertEqual(result, reference)

    def test_generate_parenthesis__7(self):
        result = generate_parenthesis(3)
        reference = ["()()()", "()(())", "(())()", "(()())", "((()))"]
        result = set(result)
        reference = set(reference)
        self.assertEqual(result, reference)

    def test_generate_parenthesis__8(self):
        result = len(generate_parenthesis(3))
        reference = 5
        self.assertEqual(result, reference)

    def test_generate_parenthesis__9(self):
        result = generate_parenthesis(4)
        reference = ["()()()()", "()()(())", "()(())()", "()(()())", "()((()))", "(())()()", "(())(())", "(()())()", "((()))()", "(()()())", "(()(()))", "((())())", "((()()))", "(((())))"]
        result = set(result)
        reference = set(reference)
        self.assertEqual(result, reference)

    def test_generate_parenthesis__10(self):
        result = len(generate_parenthesis(4))
        reference = 14
        self.assertEqual(result, reference)


if __name__ == '__main__':
    unittest.main()
