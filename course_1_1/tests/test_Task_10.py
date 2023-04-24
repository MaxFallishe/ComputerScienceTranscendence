from ..Task_10 import *
import unittest
import time
import random


class TestTask10(unittest.TestCase):
    # TEST SIZE() METHOD
    def test_size__1(self):
        pset = PowerSet()
        self.assertEqual(pset.size(), 0)

    def test_size__2(self):
        pset = PowerSet()
        pset.put('Goal')
        self.assertEqual(pset.size(), 1)

    def test_size__3(self):
        pset = PowerSet()
        pset.put('Goal')
        pset.put('Go')
        pset.put('Gong')
        pset.put('Goal')
        self.assertEqual(pset.size(), 3)

    # # TEST GET() METHOD
    def test_get__1(self):
        pset = PowerSet()
        pset.put('Goal')
        pset.put('Ball')
        pset.put('Gtyf')
        self.assertTrue(pset.get('Goal'))

    def test_get__2(self):
        pset = PowerSet()
        pset.put('Goal')
        pset.put('Ball')
        pset.put('Gtyf')
        self.assertFalse(pset.get('Ogo'))

    def test_get__3(self):
        pset = PowerSet()
        self.assertFalse(pset.get('Ogo'))

    # TEST PUT() METHOD
    def test_put__1(self):
        pset = PowerSet()
        pset.put('Goal')
        self.assertTrue(pset.get('Goal'))

    def test_put__2(self):
        pset = PowerSet()
        pset.put('Goal')  # hash = 17
        pset.put('Ball')  # hash = 13
        self.assertTrue(pset.get('Ball'))

    def test_put__3(self):
        pset = PowerSet()
        pset.put('Goal')
        pset.put('Ball')
        pset.put('Gtyf')
        self.assertTrue(pset.get('Goal'))

    def test_put__4(self):
        pset = PowerSet()
        pset.put('Goal')
        pset.put('Goal')
        pset.put('Ball')
        pset.put('Ball')
        self.assertEqual(pset.size(), 2)

    # # TEST REMOVE() METHOD
    def test_remove__1(self):
        pset = PowerSet()
        self.assertFalse(pset.remove('Ogo'))

    def test_remove__2(self):
        pset = PowerSet()
        pset.put('Goal')
        pset.put('Ball')
        pset.put('Gtyf')
        res = (pset.remove('Goal'), pset.get('Goal'))
        ref = (True, False)
        self.assertEqual(res, ref)

    def test_remove__3(self):
        pset = PowerSet()
        pset.put('Goal')
        pset.put('Ball')
        pset.put('Gtyf')
        res = (pset.remove('Goal'), pset.remove('Goal'))
        ref = (True, False)
        self.assertEqual(res, ref)

    def test_remove__4(self):
        pset = PowerSet()
        pset.put('Goal')
        pset.put('Ball')
        pset.put('Gtyf')
        res = (pset.remove('Gtyf'), pset.remove('Gtyf'))
        ref = (True, False)
        self.assertEqual(res, ref)

    # TEST INTERSECTION() METHOD
    def test_intersection__1(self):
        pset1 = PowerSet()
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k11')
        pset1.put('k8')
        pset2 = PowerSet()
        pset2.put('k2')
        pset2.put('k1')
        pset2.put('k8')
        pset2.put('k6')

        powset3 = pset1.difference(pset2)
        result = set(item for i in powset3.buckets for item in i if item is not None)
        self.assertEqual(result, {'k11'})

    def test_intersection__2(self):
        pset1 = PowerSet()
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k6')
        pset1.put('k8')
        pset2 = PowerSet()
        pset2.put('k2')
        pset2.put('k1')
        pset2.put('k8')
        pset2.put('k6')

        powset3 = pset1.difference(pset2)
        result = set(item for i in powset3.buckets for item in i if item is not None)
        self.assertEqual(result, set())

    def test_intersection__3(self):
        pset1 = PowerSet()
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k99')
        pset1.put('k88')
        pset2 = PowerSet()
        pset2.put('k2')
        pset2.put('k1')
        pset2.put('k8')
        pset2.put('k6')

        powset3 = pset1.difference(pset2)
        result = set(item for i in powset3.buckets for item in i if item is not None)
        self.assertEqual(result, {'k88', 'k99'})

    # TEST UNION() METHOD
    def test_union__1(self):
        pset1 = PowerSet()
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k99')
        pset1.put('k88')
        pset2 = PowerSet()
        pset2.put('k2')
        pset2.put('k1')
        pset2.put('k8')
        pset2.put('k6')

        powset3 = pset1.union(pset2)
        result = set(item for i in powset3.buckets for item in i if item is not None)
        self.assertEqual(result, {'k1', 'k2', 'k99', 'k88', 'k8', 'k6'})

    def test_union__2(self):
        pset1 = PowerSet()
        pset1.put('k1')
        pset1.put('k2')
        pset2 = PowerSet()
        pset2.put('k2')
        pset2.put('k1')

        powset3 = pset1.union(pset2)
        result = set(item for i in powset3.buckets for item in i if item is not None)
        self.assertEqual(result, {'k1', 'k2'})

    def test_union__3(self):
        pset1 = PowerSet()
        pset2 = PowerSet()

        powset3 = pset1.union(pset2)
        result = set(item for i in powset3.buckets for item in i if item is not None)
        self.assertEqual(result, set())

    # TEST DIFFERENCE() METHOD
    def test_difference__1(self):
        pset1 = PowerSet()
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k3')
        pset2 = PowerSet()
        pset2.put('k2')
        pset2.put('k1')

        powset3 = pset1.difference(pset2)
        result = set(item for i in powset3.buckets for item in i if item is not None)
        self.assertEqual(result, {'k3'})

    def test_difference__2(self):
        pset1 = PowerSet()
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k3')
        pset2 = PowerSet()
        pset2.put('k5')
        pset2.put('k6')

        powset3 = pset1.difference(pset2)
        result = set(item for i in powset3.buckets for item in i if item is not None)
        self.assertEqual(result, {'k1', 'k2', 'k3'})

    def test_difference__3(self):
        pset1 = PowerSet()
        pset1.put('k6')
        pset1.put('k5')
        pset2 = PowerSet()
        pset2.put('k5')
        pset2.put('k6')

        powset3 = pset1.difference(pset2)
        result = set(item for i in powset3.buckets for item in i if item is not None)
        self.assertEqual(result, set())

    # TEST ISSUBSET() METHOD
    def test_issubset__1(self):
        pset1 = PowerSet()
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k3')
        pset1.put('k4')
        pset2 = PowerSet()
        pset2.put('k1')
        pset2.put('k2')

        result = pset1.issubset(pset2)
        self.assertTrue(result)

    def test_issubset__2(self):
        pset1 = PowerSet()
        pset2 = PowerSet()
        pset2.put('k1')
        pset2.put('k2')

        result = pset1.issubset(pset2)
        self.assertFalse(result)

    def test_issubset__3(self):
        pset1 = PowerSet()
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k3')
        pset1.put('k4')
        pset2 = PowerSet()
        pset2.put('k1')

        result = pset1.difference(pset2)
        self.assertTrue(result)


    def test_issubset__4(self):
        pset1 = PowerSet()
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k3')
        pset1.put('k4')
        pset2 = PowerSet()
        pset2.put('k1')

        result = pset1.difference(pset2)
        self.assertTrue(result)


    def test_issubset__5(self):
        pset1 = PowerSet()
        pset1.put('k1')
        pset1.put('k2')
        pset1.put('k3')
        pset1.put('k4')
        pset2 = PowerSet()
        pset2.put('k5')
        pset2.put('k1')

        result = pset1.issubset(pset2)
        self.assertFalse(result)

    # Checking for the performance of functions
    # (operations on sets of tens of thousands of elements fit in a couple of seconds)
    def test_performance__init(self):
        t1 = time.time()
        pset1 = PowerSet()
        dur = time.time() - t1
        self.assertLess(dur, 2)

    def test_performance__hashfun(self):
        pset1 = PowerSet()
        t1 = time.time()
        pset1.hash_fun('test')
        dur = time.time() - t1
        self.assertLess(dur, 2)

    def test_performance__size(self):
        pset1 = PowerSet()
        for i in range(30_000):
            pset1.put(str(random.randint(0, 10_000_000)))
        t1 = time.time()
        sz = pset1.size()
        dur = time.time() - t1
        self.assertLess(dur, 2)

    def test_performance__put(self):
        pset1 = PowerSet()
        for i in range(30_000):
            pset1.put(str(random.randint(0, 10_000_000)))
        t1 = time.time()
        pset1.put('Goal')
        dur = time.time() - t1
        self.assertLess(dur, 2)

    def test_performance__get(self):
        pset1 = PowerSet()
        for i in range(30_000):
            pset1.put(str(random.randint(0, 10_000_000)))
        pset1.put('Goal')
        t1 = time.time()
        pset1.get('Goal')
        dur = time.time() - t1
        self.assertLess(dur, 2)

    def test_performance__remove(self):
        pset1 = PowerSet()
        for i in range(30_000):
            pset1.put(str(random.randint(0, 10_000_000)))
        pset1.put('Goal')
        t1 = time.time()
        pset1.remove('Goal')
        dur = time.time() - t1
        self.assertLess(dur, 2)

    def test_performance__intersection(self):
        pset1 = PowerSet()
        pset2 = PowerSet()
        for i in range(30_000):
            pset1.put(str(random.randint(0, 10_000_000)))
        for i in range(30_000):
            pset2.put(str(random.randint(0, 10_000_000)))
        t1 = time.time()
        pset1.intersection(pset2)
        dur = time.time() - t1
        self.assertLess(dur, 2)

    def test_performance__union(self):
        pset1 = PowerSet()
        pset2 = PowerSet()
        for i in range(30_000):
            pset1.put(str(random.randint(0, 10_000_000)))
        for i in range(30_000):
            pset2.put(str(random.randint(0, 10_000_000)))
        t1 = time.time()
        pset1.union(pset2)
        dur = time.time() - t1
        self.assertLess(dur, 2)

    def test_performance__difference(self):
        pset1 = PowerSet()
        pset2 = PowerSet()
        for i in range(30_000):
            pset1.put(str(random.randint(0, 10_000_000)))
        for i in range(30_000):
            pset2.put(str(random.randint(0, 10_000_000)))
        t1 = time.time()
        pset1.difference(pset2)
        dur = time.time() - t1
        self.assertLess(dur, 2)

    def test_performance__issubset(self):
        pset1 = PowerSet()
        pset2 = PowerSet()
        for i in range(30_000):
            pset1.put(str(random.randint(0, 10_000_000)))
        for i in range(30_000):
            pset2.put(str(random.randint(0, 10_000_000)))
        t1 = time.time()
        pset1.issubset(pset2)
        dur = time.time() - t1
        self.assertLess(dur, 2)
