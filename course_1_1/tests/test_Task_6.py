from ..Task_6 import *
import unittest


class TestTask6(unittest.TestCase):
    def test_size__1(self):
        deque = Deque()
        self.assertEqual(deque.size(), 0)

    def test_size__2(self):
        deque = Deque()
        deque.addTail(2)
        self.assertEqual(deque.size(), 1)

    def test_size__3(self):
        deque = Deque()
        deque.addFront(2)
        self.assertEqual(deque.size(), 1)

    def test_size__4(self):
        deque = Deque()
        deque.addTail(2)
        deque.addFront(2)
        self.assertEqual(deque.size(), 2)

    def test_size__5(self):
        deque = Deque()
        deque.addTail(1)
        deque.addTail(2)
        deque.addTail(3)
        self.assertEqual(deque.size(), 3)

    def test_size__6(self):
        deque = Deque()
        deque.addFront(1)
        deque.addFront(2)
        deque.addFront(3)
        self.assertEqual(deque.size(), 3)

    def test_size__7(self):
        deque = Deque()
        deque.addFront(1)
        deque.addFront(2)
        deque.addFront(3)
        deque.removeFront()
        self.assertEqual(deque.size(), 2)

    def test_size__8(self):
        deque = Deque()
        deque.addFront(1)
        deque.removeFront()
        self.assertEqual(deque.size(), 0)

    def test_size__9(self):
        deque = Deque()
        deque.removeFront()
        self.assertEqual(deque.size(), 0)

    def test_size__10(self):
        deque = Deque()
        deque.removeTail()
        self.assertEqual(deque.size(), 0)

    def test_addtail__1(self):
        deque = Deque()
        deque.addTail(1)
        self.assertEqual(deque.deque, [1])

    def test_addtail__2(self):
        deque = Deque()
        deque.addTail(1)
        deque.addTail(2)
        deque.addTail(3)
        deque.removeTail()
        self.assertEqual(deque.deque, [1, 2])

    def test_addfront__1(self):
        deque = Deque()
        deque.addFront(1)
        deque.addFront(2)
        deque.addFront(3)
        self.assertEqual(deque.deque, [3, 2, 1])

    def test_addfront__2(self):
        deque = Deque()
        deque.addFront(1)
        self.assertEqual(deque.deque, [1])

    def test_removeteil__1(self):
        deque = Deque()
        deque.addTail(1)
        deque.addTail(2)
        deque.addTail(3)
        deque.removeTail()
        deque.removeTail()
        self.assertEqual(deque.deque, [1])

    def test_removeteil__2(self):
        deque = Deque()
        deque.addTail(1)
        deque.addTail(2)
        deque.addTail(3)
        result = deque.removeTail()
        self.assertEqual(result, 3)

    def test_removeteil__3(self):
        deque = Deque()
        result = deque.removeTail()
        self.assertEqual(result, None)

    def test_removefront__1(self):
        deque = Deque()
        deque.addFront(1)
        deque.addFront(2)
        deque.addFront(3)
        deque.removeFront()
        self.assertEqual(deque.deque, [2, 1])

    def test_removefront__2(self):
        deque = Deque()
        deque.addFront(1)
        result = deque.removeFront()
        self.assertEqual(result, 1)

    def test_removefront__3(self):
        deque = Deque()
        result = deque.removeFront()
        self.assertEqual(result, None)

    def test_complex__1(self):
        deque = Deque()
        deque.addTail(1)
        deque.addFront(11)
        deque.addTail(2)
        deque.addTail(3)
        deque.addFront(22)
        deque.addFront(33)
        self.assertEqual(deque.deque, [33, 22, 11, 1, 2, 3])

    def test_complex__2(self):
        deque = Deque()
        deque.addTail(1)
        deque.addFront(11)
        deque.addTail(2)
        deque.addTail(3)
        deque.addFront(22)
        deque.addFront(33)
        deque.removeFront()
        deque.removeFront()
        self.assertEqual(deque.deque, [11, 1, 2, 3])

    def test_complex__3(self):
        deque = Deque()
        deque.addTail(1)
        deque.addFront(11)
        deque.addTail(2)
        deque.addTail(3)
        deque.addFront(22)
        deque.addFront(33)
        deque.removeTail()
        deque.removeFront()
        self.assertEqual(deque.deque, [22, 11, 1, 2])

