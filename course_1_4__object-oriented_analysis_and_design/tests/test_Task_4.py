import unittest
from ..Task_4__design_and_impement_ADT_for_dynarray import DynArray


class TestDynArrayAppendOperation(unittest.TestCase):
    def test_append_operation__1(self):
        d_array = DynArray()
        d_array.append(0)
        result = d_array.__str__()
        self.assertEqual(result, "DynArray [0]")

    def test_append_operation__2(self):
        d_array = DynArray()
        result = d_array.__str__()
        self.assertEqual(result, "DynArray []")

    def test_append_operation__3(self):
        d_array = DynArray()
        d_array.append(0)
        d_array.append(1)
        d_array.append(2)
        result = d_array.__str__()
        self.assertEqual(result, "DynArray [0, 1, 2]")

    def test_append_operation__4(self):
        d_array = DynArray()
        [d_array.append(i) for i in range(16+1)]
        result = d_array.__str__()
        self.assertEqual(result, "DynArray [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]")


class TestDynArrayInsertOperation(unittest.TestCase):
    def test_insert_operation__1(self):
        d_array = DynArray()
        d_array.insert("itm1", 0)
        result = d_array.__str__()
        self.assertEqual(result, "DynArray [itm1]")

    def test_insert_operation__2(self):
        d_array = DynArray()
        d_array.insert("itm1", 1)
        result = d_array.__str__()
        self.assertEqual(result, "DynArray []")


    def test_insert_operation__3(self):
        d_array = DynArray()
        d_array.insert("itm1", -1)
        result = d_array.__str__()
        self.assertEqual(result, "DynArray []")

    def test_insert_operation__4(self):
        d_array = DynArray()
        d_array.append("itm0")
        d_array.append("itm1")
        d_array.append("itm2")
        d_array.append("itm3")
        d_array.insert("itm1.5", 2)
        result = d_array.__str__()
        self.assertEqual(result, "DynArray [itm0, itm1, itm1.5, itm2, itm3]")

    def test_insert_operation__5(self):
        d_array = DynArray()
        d_array.append("itm0")
        d_array.append("itm1")
        d_array.append("itm2")
        d_array.append("itm3")
        d_array.insert("itm-1", 0)
        result = d_array.__str__()
        self.assertEqual(result, "DynArray [itm-1, itm0, itm1, itm2, itm3]")


class TestDynArrayRemoveOperation(unittest.TestCase):
    def test_remove_operation__1(self):
        d_array = DynArray()
        d_array.append("itm0")
        d_array.append("itm1")
        d_array.append("itm2")
        d_array.append("itm3")
        d_array.remove(-2)
        result = d_array.__str__()
        self.assertEqual(result, "DynArray [itm0, itm1, itm2, itm3]")

    def test_remove_operation__2(self):
        d_array = DynArray()
        d_array.append("itm0")
        d_array.append("itm1")
        d_array.append("itm2")
        d_array.append("itm3")
        d_array.remove(0)
        result = d_array.__str__()
        self.assertEqual(result, "DynArray [itm1, itm2, itm3]")

    def test_remove_operation__3(self):
        d_array = DynArray()
        d_array.append("itm0")
        d_array.append("itm1")
        d_array.append("itm2")
        d_array.append("itm3")
        d_array.remove(3)
        result = d_array.__str__()
        self.assertEqual(result, "DynArray [itm0, itm1, itm2]")


class TestDynArrayGetitemOperation(unittest.TestCase):
    def test_remove_operation__1(self):
        d_array = DynArray()
        d_array.append("itm0")
        d_array.append("itm1")
        d_array.append("itm2")
        d_array.append("itm3")
        result = d_array.get_item(0)
        self.assertEqual(result, "itm0")

    def test_remove_operation__2(self):
        d_array = DynArray()
        d_array.append("itm0")
        d_array.append("itm1")
        d_array.append("itm2")
        d_array.append("itm3")
        result = d_array.get_item(2)
        self.assertEqual(result, "itm2")

    def test_remove_operation__3(self):
        d_array = DynArray()
        d_array.append("itm0")
        d_array.append("itm1")
        d_array.append("itm2")
        d_array.append("itm3")
        result = d_array.get_item(-4)
        self.assertEqual(result, None)
