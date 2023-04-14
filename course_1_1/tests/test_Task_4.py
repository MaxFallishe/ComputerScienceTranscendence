from ..Task_4 import *
import unittest


class TestTask4(unittest.TestCase):
    # ### TEST SIZE() METHOD
    def test_size__1(self):
        stack = Stack()
        self.assertEqual(stack.size(), 0)

    def test_size__2(self):
        stack = Stack()
        stack.push(0)
        self.assertEqual(stack.size(), 1)

    def test_size__3(self):
        stack = Stack()
        stack.push(0)
        stack.push(0)
        stack.push(0)
        self.assertEqual(stack.size(), 3)

    def test_size__4(self):
        stack = Stack()
        stack.push(0)
        stack.push(0)
        stack.push(0)
        stack.pop()
        self.assertEqual(stack.size(), 2)

    def test_size__5(self):
        stack = Stack()
        stack.push(0)
        stack.push(0)
        stack.push(0)
        stack.pop()
        stack.pop()
        stack.pop()
        self.assertEqual(stack.size(), 0)

    def test_size__6(self):
        stack = Stack()
        stack.push(0)
        stack.pop()
        stack.pop()
        self.assertEqual(stack.size(), 0)

    def test_size__7(self):
        stack = Stack()
        stack.pop()
        stack.pop()
        self.assertEqual(stack.size(), 0)

    def test_push__1(self):
        stack = Stack()
        stack.push(0)
        self.assertEqual(stack.stack, [0])

    def test_push__2(self):
        stack = Stack()
        stack.push(0)
        stack.push(1)
        stack.push(2)
        self.assertEqual(stack.stack, [0, 1, 2])

    def test_push__3(self):
        stack = Stack()
        self.assertEqual(stack.stack, [])

    def test_push__4(self):
        stack = Stack()
        stack.push(0)
        stack.push(1)
        stack.push(2)
        stack.pop()
        self.assertEqual(stack.stack, [0, 1])


    def test_peak__1(self):
        stack = Stack()
        stack.push(0)
        stack.push(1)
        stack.push(2)
        self.assertEqual((stack.peek(), stack.stack), (2, [0, 1, 2]))


    def test_peak__2(self):
        stack = Stack()

        self.assertEqual((stack.peek(), stack.stack), (None, []))


    def test_peak__3(self):
        stack = Stack()
        stack.push(0)
        self.assertEqual((stack.peek(), stack.stack), (0, [0]))


    def test_pop__1(self):
        stack = Stack()
        stack.push(0)
        stack.push(1)
        stack.push(2)
        self.assertEqual((stack.pop(), stack.stack), (2, [0, 1]))

    def test_pop__2(self):
        stack = Stack()
        stack.push(0)
        self.assertEqual((stack.pop(), stack.stack), (0, []))

    def test_pop__3(self):
        stack = Stack()
        self.assertEqual((stack.pop(), stack.stack), (None, []))

