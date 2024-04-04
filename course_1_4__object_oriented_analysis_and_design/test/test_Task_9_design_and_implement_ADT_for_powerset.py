import unittest
from course_1_4__object_oriented_analysis_and_design.Task_9_design_and_implement_ADT_for_powerset import HashTable, PowerSet


class TestHashTablePutOperation(unittest.TestCase):
    def test_put_operation__1(self):
        a = HashTable(10)
        a.put('2')
        result = a.find('2')
        self.assertEqual(result, True)

    def test_put_operation__2(self):
        a = HashTable(10)
        a.put('2')
        a.put('3')
        result = a.find('9')
        self.assertEqual(result, False)

    def test_put_operation__3(self):
        a = HashTable(1)
        a.put('2')
        result = a.find('2')
        self.assertEqual(result, True)

    def test_put_operation__4(self):
        a = HashTable(1)
        a.put('2')
        a.put('3')
        result = a.find('2')
        self.assertEqual(result, True)

    def test_put_operation__5(self):
        a = HashTable(5)
        a.put('1')
        a.put('2')
        a.put('3')
        a.put('4')
        a.put('5')
        result = a.find('5')
        self.assertEqual(result, True)

    def test_put_operation__6(self):
        a = HashTable(5)
        result = a.get_put_status()
        self.assertEqual(result, a.PUT_NIL)

    def test_put_operation__7(self):
        a = HashTable(5)
        a.put(1)
        a.put(2)
        a.put(3)
        result = a.get_put_status()
        self.assertEqual(result, a.PUT_OK)

    def test_put_operation__8(self):
        a = HashTable(5)
        a.put(1)
        a.put(2)
        a.put(3)
        a.put(4)
        a.put(5)
        a.put(6)
        result = a.get_put_status()
        self.assertEqual(result, a.PUT_ERR)


class TestHashTableDeleteOperation(unittest.TestCase):
    def test_delete_operation__1(self):
        a = HashTable(10)
        a.put('1')
        a.put('2')
        a.delete('1')
        result = a.find('1')
        self.assertEqual(result, False)

    def test_delete_operation__2(self):
        a = HashTable(5)
        a.put('1')
        a.put('2')
        a.put('3')
        a.delete('2')
        result = a.find('1')
        self.assertEqual(result, True)

    def test_delete_operation__3(self):
        a = HashTable(10)
        a.put('1')
        a.put('2')
        a.put('3')
        a.put('4')
        a.put('5')
        a.delete('1')
        a.delete('2')
        a.delete('3')
        a.delete('4')
        a.delete('5')
        result = a.find('1')
        self.assertEqual(result, False)

    def test_delete_operation__4(self):
        a = HashTable(10)
        a.put('1')
        a.put('2')
        a.put('3')
        a.put('4')
        a.put('5')
        a.delete('1')
        a.delete('2')
        a.delete('3')
        a.delete('4')
        a.delete('5')
        result = a.find('3')
        self.assertEqual(result, False)

    def test_delete_operation__5(self):
        a = HashTable(10)
        a.put('1')
        a.put('2')
        a.put('3')
        a.put('4')
        a.put('5')
        a.delete('1')
        a.delete('2')
        a.delete('3')
        a.delete('4')
        a.delete('5')
        result = a.get_delete_status()
        self.assertEqual(result, a.DELETE_OK)

    def test_delete_operation__6(self):
        a = HashTable(10)
        # a.delete('5')
        result = a.get_delete_status()
        self.assertEqual(result, a.DELETE_NIL)

    def test_delete_operation__7(self):
        a = HashTable(10)
        a.delete('5')
        result = a.get_delete_status()
        self.assertEqual(result, a.DELETE_ERR)


class TestHashTableFindOperation(unittest.TestCase):
    def test_find_operation__1(self):
        a = HashTable(10)
        for i in range(10):
            a.put(i)
        result = a.find(11)
        self.assertEqual(result, False)

    def test_find_operation__2(self):
        a = HashTable(10)
        for i in range(10):
            a.put(i)
        result = a.find(8)
        self.assertEqual(result, True)

    def test_find_operation__3(self):
        a = HashTable(100)
        for i in range(100):
            a.put(i)
        result = a.find(99)
        self.assertEqual(result, True)

    def test_find_operation__4(self):
        a = HashTable(100)
        for i in range(100):
            a.put(i)
        result = True
        for i in range(100):
            result &= a.find(i)
        result = a.find(99)
        self.assertEqual(result, True)


class TestPowerSetPutOperation(unittest.TestCase):
    def test_put_operation__1(self):
        a = PowerSet(10)
        a.put('2')
        result = a.find('2')
        self.assertEqual(result, True)

    def test_put_operation__2(self):
        a = PowerSet(10)
        a.put('2')
        a.put('3')
        result = a.find('9')
        self.assertEqual(result, False)

    def test_put_operation__3(self):
        a = PowerSet(1)
        a.put('2')
        result = a.find('2')
        self.assertEqual(result, True)

    def test_put_operation__4(self):
        a = PowerSet(1)
        a.put('2')
        a.put('3')
        result = a.find('2')
        self.assertEqual(result, True)

    def test_put_operation__5(self):
        a = PowerSet(5)
        a.put('1')
        a.put('2')
        a.put('3')
        a.put('4')
        a.put('5')
        result = a.find('5')
        self.assertEqual(result, True)

    def test_put_operation__6(self):
        a = PowerSet(5)
        result = a.get_put_status()
        self.assertEqual(result, a.PUT_NIL)

    def test_put_operation__7(self):
        a = PowerSet(5)
        a.put(1)
        a.put(2)
        a.put(3)
        result = a.get_put_status()
        self.assertEqual(result, a.PUT_OK)

    def test_put_operation__8(self):
        a = PowerSet(5)
        a.put(1)
        a.put(2)
        a.put(3)
        a.put(4)
        a.put(5)
        a.put(6)
        result = a.get_put_status()
        self.assertEqual(result, a.PUT_ERR)


class TestPowerSetIntersectionOperation(unittest.TestCase):
    def test_intersection_operation__1(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        b.put('1')
        b.put('2')
        c = a.intersection(b)
        result = True
        for i in ['1', '2']:
            result &= c.find(i)
        self.assertTrue(result)

    def test_intersection_operation__2(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        b.put('1')
        b.put('2')
        c = a.intersection(b)
        result = True
        for i in ['1', '2', '3']:
            result &= c.find(i)
        self.assertFalse(result)

    def test_intersection_operation__3(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        a.put('3')
        b.put('1')
        b.put('2')
        b.put('4')
        c = a.intersection(b)
        result = True
        for i in ['1', '2']:
            result &= c.find(i)
        self.assertTrue(result)


class TestPowerSetUnionOperation(unittest.TestCase):
    def test_union_operation__1(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        b.put('1')
        b.put('2')
        c = a.union(b)
        result = True
        for i in ['1', '2']:
            result &= c.find(i)
        self.assertTrue(result)

    def test_union_operation__2(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        b.put('1')
        b.put('2')
        c = a.union(b)
        result = True
        for i in ['1', '2', '3']:
            result &= c.find(i)
        self.assertFalse(result)

    def test_union_operation__3(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        a.put('3')
        b.put('1')
        b.put('2')
        b.put('4')
        c = a.union(b)
        result = True
        for i in ['1', '2', '3', '4']:
            result &= c.find(i)
        self.assertTrue(result)


class TestPowerSetDifferenceOperation(unittest.TestCase):
    def test_union_operation__1(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        b.put('1')
        b.put('2')
        c = a.difference(b)
        result = True
        for i in ['1', '2']:
            result &= not c.find(i)
        self.assertTrue(result)

    def test_difference_operation__2(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        b.put('1')
        b.put('2')
        c = a.difference(b)
        result = True
        for i in ['1', '2', '3']:
            result &= c.find(i)
        self.assertFalse(result)

    def test_difference_operation__3(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        a.put('3')
        b.put('1')
        b.put('2')
        b.put('4')
        c = a.difference(b)
        result = True
        for i in ['3']:
            result &= c.find(i)
        self.assertTrue(result)

    def test_difference_operation__4(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        a.put('3')
        b.put('1')
        b.put('2')
        b.put('4')
        c = a.difference(b)
        result = True
        for i in ['3', '4']:
            result &= c.find(i)
        self.assertFalse(result)


class TestPowerSetIsSubsetOperation(unittest.TestCase):
    def test_is_subset_operation__1(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        b.put('1')
        b.put('2')
        result = a.issubset(b)
        self.assertTrue(result)

    def test_is_subset_operation__2(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        a.put('3')
        b.put('1')
        b.put('2')
        result = a.issubset(b)
        self.assertTrue(result)

    def test_is_subset_operation__3(self):
        a = PowerSet(10)
        b = PowerSet(10)
        a.put('1')
        a.put('2')
        b.put('1')
        b.put('2')
        b.put('3')
        result = a.issubset(b)
        self.assertFalse(result)

