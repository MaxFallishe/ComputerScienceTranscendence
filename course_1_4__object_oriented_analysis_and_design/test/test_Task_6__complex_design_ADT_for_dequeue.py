import unittest
from course_1_4__object_oriented_analysis_and_design.Task_6__complex_design_ADT_for_dequeue import Dequeue


class TestDequeueEnqueueOperation(unittest.TestCase):
    def test_enqueue_operation__1(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        result = dequeue.get()
        self.assertEqual(result, 1)

    def test_enqueue_operation__2(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        dequeue.enqueue(2)
        dequeue.enqueue(3)
        result = dequeue.get()
        self.assertEqual(result, 1)

    def test_enqueue_operation__3(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        dequeue.enqueue(2)
        dequeue.enqueue(3)
        result = dequeue.get()
        self.assertEqual(result, 1)


class TestDequeueDequeueOperation(unittest.TestCase):
    def test_dequeue_operation__1(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        dequeue.dequeue()
        result = dequeue.get()
        self.assertEqual(result, None)

    def test_dequeue_operation__2(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        dequeue.enqueue(2)
        dequeue.dequeue()
        result = dequeue.get()
        self.assertEqual(result, 2)

    def test_dequeue_operation__3(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        dequeue.enqueue(2)
        dequeue.enqueue(3)
        dequeue.dequeue()
        dequeue.dequeue()
        dequeue.dequeue()
        result = dequeue.get()
        self.assertEqual(result, None)

    def test_dequeue_operation__4(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        result = dequeue.get_dequeue_status()
        self.assertEqual(result, dequeue.DEQUEUE_NIL)

    def test_dequeue_operation__5(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        dequeue.dequeue()
        result = dequeue.get_dequeue_status()
        self.assertEqual(result, dequeue.DEQUEUE_OK)

    def test_dequeue_operation__6(self):
        dequeue = Dequeue()
        dequeue.dequeue()
        result = dequeue.get_dequeue_status()
        self.assertEqual(result, dequeue.DEQUEUE_ERR)


class TestDequeueGetOperation(unittest.TestCase):
    def test_get_operation__1(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        result = dequeue.get()
        self.assertEqual(result, 1)

    def test_get_operation__2(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        result = dequeue.get_get_status()
        self.assertEqual(result, dequeue.GET_NIL)

    def test_get_operation__3(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        dequeue.get()
        dequeue.get()
        dequeue.get()
        result = dequeue.get_get_status()
        self.assertEqual(result, dequeue.GET_OK)

    def test_get_operation__4(self):
        dequeue = Dequeue()
        dequeue.get()
        result = dequeue.get_get_status()
        self.assertEqual(result, dequeue.GET_ERR)


class TestDequeueEnqueueHeadOperation(unittest.TestCase):
    def test_enqueue_head_operation__1(self):
        dequeue = Dequeue()
        dequeue.enqueue_head(1)
        dequeue.enqueue_head(2)
        dequeue.enqueue_head(3)
        result = dequeue.get()
        self.assertEqual(result, 3)

    def test_enqueue_head_operation__2(self):
        dequeue = Dequeue()
        dequeue.enqueue_head(1)
        dequeue.enqueue_head(2)
        dequeue.enqueue_head(3)
        result = dequeue.get_tail()
        self.assertEqual(result, 1)


class TestDequeueDequeueTailOperation(unittest.TestCase):
    def test_deque_tail_operation__1(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        dequeue.enqueue(2)
        dequeue.enqueue(3)
        dequeue.dequeue_tail()
        result = dequeue.get_tail()
        self.assertEqual(result, 2)

    def test_deque_tail_operation__2(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        dequeue.enqueue(2)
        dequeue.enqueue(3)
        dequeue.dequeue_tail()
        dequeue.dequeue_tail()
        dequeue.dequeue_tail()
        result = dequeue.get()
        self.assertEqual(result, None)

    def test_deque_tail_operation__3(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        dequeue.enqueue(2)
        dequeue.enqueue(3)
        dequeue.dequeue_tail()
        dequeue.dequeue_tail()
        dequeue.dequeue_tail()
        result = dequeue.get_dequeue_tail_status()
        self.assertEqual(result, dequeue.DEQUEUE_TAIL_OK)

    def test_deque_tail_operation__4(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        dequeue.enqueue(2)
        dequeue.enqueue(3)
        dequeue.dequeue_tail()
        dequeue.dequeue_tail()
        dequeue.dequeue_tail()
        dequeue.dequeue_tail()
        result = dequeue.get_dequeue_tail_status()
        self.assertEqual(result, dequeue.DEQUEUE_TAIL_ERR)

    def test_deque_tail_operation__5(self):
        dequeue = Dequeue()
        dequeue.enqueue(1)
        dequeue.enqueue(2)
        dequeue.enqueue(3)
        result = dequeue.get_dequeue_tail_status()
        self.assertEqual(result, dequeue.DEQUEUE_TAIL_NIL)


if __name__ == '__main__':
    unittest.main()
