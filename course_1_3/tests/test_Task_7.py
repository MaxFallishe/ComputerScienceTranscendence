from ..Task_7 import *
import unittest


class TestMakeHeap(unittest.TestCase):
    def test_make_heap__1(self):
        heap = Heap()
        heap.MakeHeap([], 0)
        self.assertEqual(heap.HeapArray, [None])

    def test_make_heap__2(self):
        heap = Heap()
        heap.MakeHeap([], 1)
        self.assertEqual(heap.HeapArray, [None, None, None])

    def test_make_heap__3(self):
        heap = Heap()
        heap.MakeHeap([], 2)
        self.assertEqual(heap.HeapArray, [None, None, None, None, None, None, None])

    def test_make_heap__4(self):
        heap = Heap()
        heap.MakeHeap([1], 0)
        self.assertEqual(heap.HeapArray, [1])

    def test_make_heap__5(self):
        heap = Heap()
        heap.MakeHeap([1, 2], 1)
        self.assertEqual(heap.HeapArray, [2, 1, None])

    def test_make_heap__6(self):
        heap = Heap()
        heap.MakeHeap([1, 3, 5], 1)
        self.assertEqual(heap.HeapArray, [5, 1, 3])

    def test_make_heap__7(self):
        heap = Heap()
        heap.MakeHeap([7, 6, 1, 2, 3, 5, 4], 2)
        self.assertEqual(heap.HeapArray, [7, 6, 5, 2, 3, 1, 4])

    def test_make_heap__8(self):
        heap = Heap()
        heap.MakeHeap([7, 6, 1, 2, 3, 5], 2)
        self.assertEqual(heap.HeapArray, [7, 6, 5, 2, 3, 1, None])

    def test_make_heap__9(self):
        heap = Heap()
        heap.MakeHeap([1, 3, 4], 0)
        self.assertEqual(heap.HeapArray, [1])

    def test_make_heap__10(self):
        heap = Heap()
        heap.MakeHeap([1, 3, 4, 5, 6], 1)
        self.assertEqual(heap.HeapArray, [4, 1, 3])


class TestGetMax(unittest.TestCase):
    def test_get_max__1(self):
        heap = Heap()
        heap.MakeHeap([], 0)
        result = heap.GetMax()
        self.assertEqual(result, -1)

    def test_get_max__2(self):
        heap = Heap()
        heap.MakeHeap([1], 0)
        result = heap.GetMax()
        self.assertEqual(result, 1)

    def test_get_max__3(self):
        heap = Heap()
        heap.MakeHeap([1, 2, 3], 1)
        result = heap.GetMax()
        self.assertEqual(result, 3)

    def test_get_max__4(self):
        heap = Heap()
        heap.MakeHeap([1, 2], 1)
        result = heap.GetMax()
        self.assertEqual(result, 2)

    def test_get_max__5(self):
        heap = Heap()
        heap.MakeHeap([7, 4, 3, 6, 1, 5, 2], 2)
        result = heap.GetMax()
        self.assertEqual(result, 7)

    def test_get_max__6(self):
        heap = Heap()
        heap.MakeHeap([7, 4, 3, 6, 1, 5, 2], 2)
        heap.GetMax()
        result = heap.GetMax()
        self.assertEqual(result, 6)

    def test_get_max__7(self):
        heap = Heap()
        heap.MakeHeap([7, 4, 3, 6, 1, 5, 2], 2)
        heap.GetMax()
        heap.GetMax()
        result = heap.GetMax()
        self.assertEqual(result, 5)

    def test_get_max__8(self):
        heap = Heap()
        heap.MakeHeap([7, 4, 3, 6, 1, 5, 2], 2)
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        result = heap.GetMax()
        self.assertEqual(result, 1)

    def test_get_max__9(self):
        heap = Heap()
        heap.MakeHeap([7, 4, 3, 6, 1, 5, 2], 2)
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        result = heap.GetMax()
        self.assertEqual(result, -1)

    def test_get_max__10(self):
        heap = Heap()
        heap.MakeHeap([7, 4, 3, 6, 1, 5, 2], 2)
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        self.assertEqual(heap.HeapArray, [4, 2, 3, 1, None, None, None])

    def test_get_max__11(self):
        heap = Heap()
        heap.MakeHeap([7, 4, 3, 6, 1, 5, 2], 2)
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        self.assertEqual(heap.HeapArray, [3, 2, 1, None, None, None, None])

    def test_get_max__12(self):
        heap = Heap()
        heap.MakeHeap([1], 0)
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        self.assertEqual(heap.HeapArray, [None])

    def test_get_max__13(self):
        heap = Heap()
        heap.MakeHeap([1, 2, 3], 1)
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        self.assertEqual(heap.HeapArray, [None, None, None])

    def test_get_max__14(self):
        heap = Heap()
        heap.MakeHeap([1, 2], 1)
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        heap.GetMax()
        self.assertEqual(heap.HeapArray, [None, None, None])

    def test_get_max__15(self):
        heap = Heap()
        heap.MakeHeap([1, 2], 1)
        heap.GetMax()
        self.assertEqual(heap.HeapArray, [1, None, None])


class TestAdd(unittest.TestCase):
    def test_add__1(self):
        heap = Heap()
        heap.MakeHeap([7, 4, 3, 6, 1, 5, 2], 2)
        result = heap.Add(5)
        self.assertEqual(result, False)

    def test_add__2(self):
        heap = Heap()
        heap.MakeHeap([7, 4, 3, 6, 1, 5], 2)
        result = heap.Add(5)
        self.assertEqual(result, True)

    def test_add__3(self):
        heap = Heap()
        heap.MakeHeap([7, 4, 3, 6, 1, 5], 2)
        heap.Add(99)
        self.assertEqual(heap.HeapArray, [99, 6, 7, 4, 1, 3, 5])

    def test_add__4(self):
        heap = Heap()
        heap.MakeHeap([7, 4, 3, 6, 1, 5], 2)
        heap.Add(0)
        self.assertEqual(heap.HeapArray, [7, 6, 5, 4, 1, 3, 0])

    def test_add__5(self):
        heap = Heap()
        heap.MakeHeap([7, 4, 3, 6, 1], 2)
        heap.Add(0)
        self.assertEqual(heap.HeapArray, [7, 6, 3, 4, 1, 0, None])

    def test_add__6(self):
        heap = Heap()
        heap.MakeHeap([7, 4], 2)
        heap.Add(6)
        self.assertEqual(heap.HeapArray, [7, 4, 6, None, None, None, None])

