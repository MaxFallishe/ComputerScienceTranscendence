from ..Task_10 import *
import unittest


class TestTask10(unittest.TestCase):
    # TEST SIZE() METHOD
    def test_size__1(self):
        pset = PowerSet(19, 3)
        self.assertEqual(pset.size(), 0)

    def test_size__2(self):
        pset = PowerSet(19, 3)
        pset.put('Goal')
        self.assertEqual(pset.size(), 1)

    def test_size__3(self):
        pset = PowerSet(19, 3)
        pset.put('Goal')
        pset.put('Go')
        pset.put('Gong')
        pset.put('Goal')
        self.assertEqual(pset.size(), 3)

    # TEST PUT() METHOD
    def test_put__1(self):
        pset = PowerSet(19, 3)
        pset.put('Goal')  # hash = 17
        self.assertEqual(pset.slots[17], 'Goal')

    def test_put__2(self):
        pset = PowerSet(19, 3)
        pset.put('Goal')  # hash = 17
        pset.put('Ball')  # hash = 13
        res = (pset.slots[17], pset.slots[13])
        ref = ('Goal', 'Ball')
        self.assertEqual(res, ref)

    def test_put__3(self):
        pset = PowerSet(19, 3)
        pset.put('Goal')  # hash = 17
        pset.put('Ball')  # hash = 13
        pset.put('Gtyf')  # hash = 17
        res = (pset.slots[17], pset.slots[13], pset.slots[1])
        ref = ('Goal', 'Ball', 'Gtyf')
        self.assertEqual(res, ref)

    # TEST GET() METHOD
    def test_get__1(self):
        pset = PowerSet(19, 3)
        pset.put('Goal')  # hash = 17
        pset.put('Ball')  # hash = 13
        pset.put('Gtyf')  # hash = 17
        self.assertTrue(pset.get('Goal'))

    def test_get__2(self):
        pset = PowerSet(19, 3)
        pset.put('Goal')  # hash = 17
        pset.put('Ball')  # hash = 13
        pset.put('Gtyf')  # hash = 17
        self.assertFalse(pset.get('Ogo'))

    def test_get__3(self):
        pset = PowerSet(19, 3)
        self.assertFalse(pset.get('Ogo'))

    # TEST REMOVE() METHOD
    def test_remove__1(self):
        pset = PowerSet(19, 3)
        self.assertFalse(pset.remove('Ogo'))

    def test_remove__2(self):
        pset = PowerSet(19, 3)
        pset.put('Goal')  # hash = 17
        pset.put('Ball')  # hash = 13
        pset.put('Gtyf')  # hash = 17
        res = (pset.remove('Goal'), pset.get('Goal'))
        ref = (True, False)
        self.assertEqual(res, ref)

    def test_remove__3(self):
        pset = PowerSet(19, 3)
        pset.put('Goal')  # hash = 17
        pset.put('Ball')  # hash = 13
        pset.put('Gtyf')  # hash = 17
        res = (pset.remove('Goal'), pset.remove('Goal'))
        ref = (True, False)
        self.assertEqual(res, ref)

    def test_remove__4(self):
        pset = PowerSet(19, 3)
        pset.put('Goal')  # hash = 17
        pset.put('Ball')  # hash = 13
        pset.put('Gtyf')  # hash = 17
        res = (pset.remove('Gtyf'), pset.remove('Gtyf'))
        ref = (True, False)
        self.assertEqual(res, ref)

    # TEST INTERSECTION() METHOD
    def test_intersection__1(self):
        pset1 = PowerSet(19, 3)
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k11')
        pset1.put('k8')
        pset2 = PowerSet(19, 3)
        pset2.put('k2')
        pset2.put('k1')
        pset2.put('k8')
        pset2.put('k6')

        powset3 = pset1.difference(pset2)
        result = set(i for i in powset3.slots if i is not None)
        self.assertEqual(result, {'k11'})

    def test_intersection__2(self):
        pset1 = PowerSet(19, 3)
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k6')
        pset1.put('k8')
        pset2 = PowerSet(19, 3)
        pset2.put('k2')
        pset2.put('k1')
        pset2.put('k8')
        pset2.put('k6')

        powset3 = pset1.difference(pset2)
        result = set([i for i in powset3.slots if i is not None])
        self.assertEqual(result, set())

    def test_intersection__3(self):
        pset1 = PowerSet(19, 3)
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k99')
        pset1.put('k88')
        pset2 = PowerSet(19, 3)
        pset2.put('k2')
        pset2.put('k1')
        pset2.put('k8')
        pset2.put('k6')

        powset3 = pset1.difference(pset2)
        result = set(i for i in powset3.slots if i is not None)
        self.assertEqual(result, {'k88', 'k99'})

    # TEST UNION() METHOD
    def test_union__1(self):
        pset1 = PowerSet(19, 3)
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k99')
        pset1.put('k88')
        pset2 = PowerSet(19, 3)
        pset2.put('k2')
        pset2.put('k1')
        pset2.put('k8')
        pset2.put('k6')

        powset3 = pset1.union(pset2)
        result = set(i for i in powset3.slots if i is not None)
        self.assertEqual(result, {'k1', 'k2', 'k99', 'k88', 'k8', 'k6'})

    def test_union__2(self):
        pset1 = PowerSet(19, 3)
        pset1.put('k1')
        pset1.put('k2')
        pset2 = PowerSet(19, 3)
        pset2.put('k2')
        pset2.put('k1')

        powset3 = pset1.union(pset2)
        result = set(i for i in powset3.slots if i is not None)
        self.assertEqual(result, {'k1', 'k2'})

    def test_union__3(self):
        pset1 = PowerSet(19, 3)
        pset2 = PowerSet(19, 3)

        powset3 = pset1.union(pset2)
        result = set(i for i in powset3.slots if i is not None)
        self.assertEqual(result, set())

    # TEST DIFFERENCE() METHOD
    def test_difference__1(self):
        pset1 = PowerSet(19, 3)
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k3')
        pset2 = PowerSet(19, 3)
        pset2.put('k2')
        pset2.put('k1')

        powset3 = pset1.difference(pset2)
        result = set(i for i in powset3.slots if i is not None)
        self.assertEqual(result, {'k3'})

    def test_difference__2(self):
        pset1 = PowerSet(19, 3)
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k3')
        pset2 = PowerSet(19, 3)
        pset2.put('k5')
        pset2.put('k6')

        powset3 = pset1.difference(pset2)
        result = set(i for i in powset3.slots if i is not None)
        self.assertEqual(result, {'k1', 'k2', 'k3'})

    def test_difference__3(self):
        pset1 = PowerSet(19, 3)
        pset1.put('k6')
        pset1.put('k5')
        pset2 = PowerSet(19, 3)
        pset2.put('k5')
        pset2.put('k6')

        powset3 = pset1.difference(pset2)
        result = set(i for i in powset3.slots if i is not None)
        self.assertEqual(result, set())

    # TEST ISSUBSET() METHOD
    def test_issubset__1(self):
        pset1 = PowerSet(19, 3)
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k3')
        pset1.put('k4')
        pset2 = PowerSet(19, 3)
        pset2.put('k1')
        pset2.put('k2')

        result = pset1.issubset(pset2)
        self.assertTrue(result)

    def test_issubset__2(self):
        pset1 = PowerSet(19, 3)
        pset2 = PowerSet(19, 3)
        pset2.put('k1')
        pset2.put('k2')

        result = pset1.issubset(pset2)
        self.assertFalse(result)

    def test_issubset__3(self):
        pset1 = PowerSet(19, 3)
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k3')
        pset1.put('k4')
        pset2 = PowerSet(19, 3)
        pset2.put('k1')

        result = pset1.difference(pset2)
        self.assertTrue(result)


    def test_issubset__4(self):
        pset1 = PowerSet(100, 3)
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k3')
        pset1.put('k4')
        pset2 = PowerSet(19, 3)
        pset2.put('k1')

        result = pset1.difference(pset2)
        self.assertTrue(result)


    def test_issubset__5(self):
        pset1 = PowerSet(19, 3)
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k3')
        pset1.put('k4')
        pset2 = PowerSet(19, 3)
        pset2.put('k5')
        pset2.put('k1')

        result = pset1.issubset(pset2)
        self.assertFalse(result)
