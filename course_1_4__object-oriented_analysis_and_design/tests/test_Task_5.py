import unittest
from ..Task_5__design_and_impement_ADT_for_queue import Queue


class TestQueueAppendOperation(unittest.TestCase):
    def test_append_operation__1(self):
        queue = Queue()
        queue.append(1)
        result = queue.pop()
        self.assertEqual(result, 1)

    def test_append_operation__2(self):
        queue = Queue()
        queue.append("22")
        result = queue.pop()
        self.assertEqual(result, "22")

    def test_append_operation__3(self):
        queue = Queue()
        queue.append("22")
        queue.append("11")
        queue.append("00")
        result = queue.pop()
        self.assertEqual(result, "22")

    def test_append_operation__4(self):
        queue = Queue()
        queue.append("22")
        queue.append("11")
        queue.append("00")
        queue.pop()
        queue.pop()
        result = queue.pop()
        self.assertEqual(result, "00")

    def test_append_operation__5(self):
        queue = Queue()
        queue.append("22")
        queue.append("11")
        queue.append("00")
        queue.pop()
        queue.pop()
        queue.pop()
        result = queue.pop()
        self.assertEqual(result, None)

    def test_append_operation__6(self):
        queue = Queue()
        queue.append("22")
        queue.append("11")
        result = queue.get_append_status()
        self.assertEqual(result, queue.APPEND_OK)


    def test_append_operation__7(self):
        queue = Queue()
        result = queue.get_append_status()
        self.assertEqual(result, queue.APPEND_NIL)



class TestQueuePopOperation(unittest.TestCase):
    def test_pop_operation__1(self):
        queue = Queue()
        queue.append(1)
        result = queue.pop()
        self.assertEqual(result, 1)

    def test_pop_operation__2(self):
        queue = Queue()
        queue.append(1)
        queue.append(2)
        queue.append(3)
        result = [queue.pop(), queue.pop(), queue.pop()]
        self.assertEqual(result, [1, 2, 3])

    def test_pop_operation__3(self):
        queue = Queue()
        result = queue.get_pop_status()
        self.assertEqual(result, queue.POP_NIL)

    def test_pop_operation__4(self):
        queue = Queue()
        queue.append(0)
        queue.pop()
        result = queue.get_pop_status()
        self.assertEqual(result, queue.POP_OK)

    def test_pop_operation__5(self):
        queue = Queue()
        queue.pop()
        result = queue.get_pop_status()
        self.assertEqual(result, queue.POP_ERR)


class TestQueueSizeOperation(unittest.TestCase):
    def test_size_operation__1(self):
        queue = Queue()
        queue.append(1)
        result = queue.size()
        self.assertEqual(result, 1)

    def test_size_operation__2(self):
        queue = Queue()
        queue.append(1)
        queue.append(2)
        queue.append(3)
        result = queue.size()
        self.assertEqual(result, 3)

    def test_size_operation__3(self):
        queue = Queue()
        queue.append(1)
        queue.append(2)
        queue.append(3)
        queue.pop()
        queue.pop()
        queue.pop()
        queue.pop()
        result = queue.size()
        self.assertEqual(result, 0)

    def test_size_operation__4(self):
        queue = Queue()
        queue.append(1)
        queue.append(2)
        queue.append(3)
        queue.pop()
        queue.size()
        result = queue.get_size_status()
        self.assertEqual(result, queue.SIZE_OK)

    def test_size_operation__5(self):
        queue = Queue()
        result = queue.get_size_status()
        self.assertEqual(result, queue.SIZE_NIL)

    def test_size_operation__6(self):
        queue = Queue()
        result = queue.get_size_status()
        self.assertEqual(result, queue.SIZE_NIL)
