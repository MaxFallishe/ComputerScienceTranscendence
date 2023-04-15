from ..Task_4_addon3 import *
import unittest


class TestTask4(unittest.TestCase):
    def test_postfix_calculation__1(self):
        stack = Stack()
        stack.push('=')
        stack.push('+')
        stack.push(9)
        stack.push('*')
        stack.push(5)
        stack.push('+')
        stack.push(2)
        stack.push(8)
        self.assertEqual(calculate_postfix_expression(stack), 59)

    def test_postfix_calculation__2(self):
        stack = Stack()
        stack.push('=')
        stack.push('*')
        stack.push(3)
        stack.push('+')
        stack.push(2)
        stack.push(1)
        self.assertEqual(calculate_postfix_expression(stack), 9)

    def test_postfix_calculation__3(self):
        stack = Stack()
        stack.push('=')
        stack.push('+')
        stack.push('+')
        stack.push(3)
        stack.push('+')
        stack.push(0)
        stack.push(3)
        stack.push(3)
        self.assertEqual(calculate_postfix_expression(stack), 9)

    def test_postfix_calculation__4(self):
        stack = Stack()
        stack.push('=')
        stack.push('+')
        stack.push(3)
        stack.push('/')
        stack.push(3)
        stack.push(3)
        self.assertEqual(calculate_postfix_expression(stack), 4)

    def test_postfix_calculation__5(self):
        stack = Stack()
        stack.push('=')
        stack.push('+')
        stack.push(3)
        stack.push('-')
        stack.push(3)
        stack.push(3)
        self.assertEqual(calculate_postfix_expression(stack), 3)

    def test_postfix_calculation__6(self):
        stack = Stack()
        stack.push('=')
        stack.push('/')
        stack.push(5)
        stack.push('-')
        stack.push(3)
        stack.push(10)
        self.assertEqual(calculate_postfix_expression(stack), 1.4)

