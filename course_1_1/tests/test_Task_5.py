from ..Task_5 import *
import unittest


class TestTask5(unittest.TestCase):
    def test_size__1(self):
        queue = Queue()
        self.assertEqual(queue.size(), 0)

    def test_size__2(self):
        queue = Queue()
        queue.enqueue(1)
        self.assertEqual(queue.size(), 1)

    def test_size__3(self):
        queue = Queue()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        self.assertEqual(queue.size(), 3)

    def test_size__4(self):
        queue = Queue()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.dequeue()
        queue.enqueue(3)
        self.assertEqual(queue.size(), 2)

    def test_size__5(self):
        queue = Queue()
        queue.dequeue()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        queue.dequeue()
        self.assertEqual(queue.size(), 2)

    def test_enqueue__1(self):
        queue = Queue()
        queue.enqueue(1)
        self.assertEqual(queue.queue, [1])

    def test_enqueue__2(self):
        queue = Queue()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        self.assertEqual(queue.queue, [1, 2, 3])

    def test_enqueue__3(self):
        queue = Queue()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        queue.dequeue()
        self.assertEqual(queue.queue, [2, 3])

    def test_enqueue__4(self):
        queue = Queue()
        queue.enqueue(1)
        queue.dequeue()
        queue.enqueue(2)
        queue.enqueue(3)
        queue.dequeue()
        self.assertEqual(queue.queue, [3])

    def test_dequeue__1(self):
        queue = Queue()
        queue.dequeue()

        self.assertEqual(queue.queue, [])

    def test_dequeue__2(self):
        queue = Queue()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.dequeue()
        queue.enqueue(1)
        queue.enqueue(2)

        self.assertEqual(queue.queue, [2, 1, 2])

    def test_dequeue__3(self):
        queue = Queue()
        queue.enqueue(1)
        queue.enqueue(2)
        result = queue.dequeue()

        self.assertEqual(result, 1)

    def test_dequeue__4(self):
        queue = Queue()
        result = queue.dequeue()

        self.assertEqual(result, None)
