from ..Task_4_addon2 import *
import unittest


class TestTask4(unittest.TestCase):
    def test_is_brackets_balanced__1(self):
        result = is_brackets_balanced("(")
        self.assertEqual(result, False)

    def test_is_brackets_balanced__2(self):
        result = is_brackets_balanced(")")
        self.assertEqual(result, False)

    def test_is_brackets_balanced__3(self):
        result = is_brackets_balanced("()")
        self.assertEqual(result, True)

    def test_is_brackets_balanced__4(self):
        result = is_brackets_balanced("()()")
        self.assertEqual(result, True)

    def test_is_brackets_balanced__5(self):
        result = is_brackets_balanced("(()())")
        self.assertEqual(result, True)

    def test_is_brackets_balanced__6(self):
        result = is_brackets_balanced("(()()))")
        self.assertEqual(result, False)

    def test_is_brackets_balanced__7(self):
        result = is_brackets_balanced("(()(())())")
        self.assertEqual(result, True)

