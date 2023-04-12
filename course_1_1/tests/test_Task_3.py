import unittest
from ..Task_3 import *


class TestTask3(unittest.TestCase):
    # ### TEST INSERT() METHOD
    def test_insert__1(self):
        count_of_elem = 16
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        da.insert(4, 99)
        ref_array = [i for i in range(count_of_elem)]
        ref_array.insert(4, 99)
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 17, 32))

    def test_insert__2(self):
        count_of_elem = 15
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        da.insert(4, 99)
        ref_array = [i for i in range(count_of_elem)]
        ref_array.insert(4, 99)
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 16, 16))

    def test_insert__3(self):
        count_of_elem = 8
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        da.insert(4, 99)
        ref_array = [i for i in range(count_of_elem)]
        ref_array.insert(4, 99)
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 9, 16))

    def test_insert__4(self):
        count_of_elem = 1
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        da.insert(1, 99)
        ref_array = [i for i in range(count_of_elem)]
        ref_array.insert(1, 99)
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 2, 16))

    def test_insert__5(self):
        count_of_elem = 0
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        da.insert(0, 99)
        ref_array = [i for i in range(count_of_elem)]
        ref_array.insert(0, 99)
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 1, 16))

    def test_insert__6(self):
        count_of_elem = 16
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        da.insert(16, 99)
        ref_array = [i for i in range(count_of_elem)]
        ref_array.insert(16, 99)
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 17, 32))

    def test_insert__7(self):
        count_of_elem = 32
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        da.insert(32, 99)
        ref_array = [i for i in range(count_of_elem)]
        ref_array.insert(32, 99)
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 33, 64))

    def test_insert__8(self):
        count_of_elem = 32
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)

        self.assertRaises(IndexError, da.insert, 99, 99)

    def test_insert__9(self):
        count_of_elem = 0
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)

        self.assertRaises(IndexError, da.insert, 1, 99)

    def test_insert__10(self):
        count_of_elem = 0
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)

        self.assertRaises(IndexError, da.insert, -3, 99)

    # ### TEST DELETE() METHOD
    def test_delete__1(self):
        count_of_elem = 32
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        da.delete(0)
        ref_array = [i for i in range(count_of_elem)]
        del ref_array[0]
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 31, 32))

    def test_delete__2(self):
        count_of_elem = 17
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        da.delete(0)
        ref_array = [i for i in range(count_of_elem)]
        del ref_array[0]
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 16, 32))

    def test_delete__3(self):
        count_of_elem = 65
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        da.delete(16)
        ref_array = [i for i in range(count_of_elem)]
        del ref_array[16]
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 64, 128))

    def test_delete__4(self):
        count_of_elem = 65
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        da.delete(16)
        da.delete(31)
        ref_array = [i for i in range(count_of_elem)]
        del ref_array[16]
        del ref_array[31]
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 63, 85))

    def test_delete__5(self):
        count_of_elem = 65
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        ref_array = [i for i in range(count_of_elem)]
        for i in range(5, 28+1):

            da.delete(i)
            del ref_array[i]
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 41, 56))

    def test_delete__6(self):
        count_of_elem = 65
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        ref_array = [i for i in range(count_of_elem)]
        for i in range(2, 43+1):
            da.delete(0)
            del ref_array[0]
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 23, 37))

    def test_delete__7(self):
        count_of_elem = 21
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        ref_array = [i for i in range(count_of_elem)]
        for i in range(0, 15+1):
            da.delete(0)
            del ref_array[0]
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 5, 16))

    def test_delete__8(self):
        count_of_elem = 65
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)

        self.assertRaises(IndexError, da.delete, -3)

    def test_delete__9(self):
        count_of_elem = 65
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)
        for i in range(2, 43+1):
            da.delete(0)

        self.assertRaises(IndexError, da.delete, 65)

    def test_delete__10(self):
        count_of_elem = 65
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)

        self.assertRaises(IndexError, da.delete, 65)

    def test_delete__11(self):
        count_of_elem = 0
        da = DynArray()
        for i in range(count_of_elem):
            da.append(i)

        self.assertRaises(IndexError, da.delete, 0)

    def test_appen_after_delete__1(self):
        count_of_elem = 21
        da = DynArray()
        # step 1
        for i in range(count_of_elem):
            da.append(i)
        # step 2
        ref_array = [i for i in range(count_of_elem)]
        for i in range(0, 15+1):
            da.delete(0)
            del ref_array[0]
        # step 3
        for i in range(100, 150+1):
            da.append(i)
            ref_array.append(i)
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 56, 64))

    def test_appen_after_delete__2(self):
        count_of_elem = 32
        da = DynArray()
        # step 1
        for i in range(count_of_elem):
            da.append(i)
        # step 2
        ref_array = [i for i in range(count_of_elem)]
        for i in range(0, 20):
            da.delete(0)
            del ref_array[0]
        # step 3
        for i in range(100, 120):
            da.append(i)
            ref_array.append(i)
        res_array = [da[i] for i in range(da.count)]
        self.assertEqual((res_array, da.count, da.capacity), (ref_array, 32, 42))
