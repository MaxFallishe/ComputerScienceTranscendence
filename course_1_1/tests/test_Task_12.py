from ..Task_12 import *
import unittest
import random


class TestTask9(unittest.TestCase):
    # TEST HASH_FUN() METHOD
    def test_hashfun__1(self):
        cache_t = NativeCache(7)
        hash1 = cache_t.hash_fun('a')
        hash2 = cache_t.hash_fun('a')
        self.assertEqual(hash1, hash2)

    def test_hashfun__2(self):
        cache_t = NativeCache(7)
        hash1 = cache_t.hash_fun('a')
        hash2 = cache_t.hash_fun('dshg')
        self.assertNotEqual(hash1, hash2)

    def test_hashfun__3(self):
        cache_t = NativeCache(7)
        hash1 = cache_t.hash_fun('')
        hash2 = cache_t.hash_fun('')
        self.assertEqual(hash1, hash2)

    # TEST IS_KEY() METHOD
    def test_iskey__1(self):
        cache_t = NativeCache(7)
        cache_t.slots[0], cache_t.values[0] = 'k1', 'k1v'
        self.assertTrue('k1' in cache_t)

    def test_iskey__2(self):
        cache_t = NativeCache(7)
        cache_t.slots[0], cache_t.values[0] = 'k1', 'k1v'
        cache_t.slots[5], cache_t.values[5] = 'k2', 'k2v'
        self.assertTrue('k2' in cache_t)

    def test_iskey__3(self):
        cache_t = NativeCache(7)
        cache_t.slots[0], cache_t.values[0] = 'k1', 'k1v'
        cache_t.slots[5], cache_t.values[5] = 'k2', 'k2v'
        self.assertFalse('k3' in cache_t)

    def test_iskey__4(self):
        cache_t = NativeCache(7)
        self.assertFalse('k4' in cache_t)

    # TEST PUT() METHOD
    def test_put__1(self):
        cache_t = NativeCache(7)
        cache_t.put('k3', "k3v")  # hash is 2
        res_keys = cache_t.slots
        res_values = cache_t.values
        ref_keys = [None, None, 'k3', None, None, None, None]
        ref_values = [None, None, 'k3v', None, None, None, None]
        res = (res_keys, res_values)
        ref = (ref_keys, ref_values)
        self.assertEqual(res, ref)

    def test_put__2(self):
        cache_t = NativeCache(7)
        cache_t.put('k3', "k3v")  # hash is 2
        cache_t.put('k3', "k3v_v2")  # hash is 2
        res_keys = cache_t.slots
        res_values = cache_t.values
        ref_keys = [None, None, 'k3', None, None, None, None]
        ref_values = [None, None, 'k3v_v2', None, None, None, None]
        res = (res_keys, res_values)
        ref = (ref_keys, ref_values)
        self.assertEqual(res, ref)

    def test_put__3(self):
        cache_t = NativeCache(7)
        cache_t.put('k1', "k1v")  # hash is 1
        cache_t.put('k3', "k3v")  # hash is 2
        res_keys = cache_t.slots
        res_values = cache_t.values
        ref_keys = [None, 'k1', 'k3', None, None, None, None]
        ref_values = [None, 'k1v', 'k3v', None, None, None, None]
        res = (res_keys, res_values)
        ref = (ref_keys, ref_values)
        self.assertEqual(res, ref)

    def test_put__4(self):
        cache_t = NativeCache(7)
        cache_t.put('k1', "k1v")  # hash is 1
        cache_t.put('k2', "k2v")  # hash is 1
        cache_t.put('k3', "k3v")  # hash is 2
        res_keys = cache_t.slots
        res_values = cache_t.values
        ref_keys = [None, 'k1', 'k3', 'k2', None, None, None]
        ref_values = [None, 'k1v', 'k3v', 'k2v', None, None, None]
        res = (res_keys, res_values)
        ref = (ref_keys, ref_values)
        self.assertEqual(res, ref)

    def test_put__5(self):
        cache_t = NativeCache(5)
        cache_t.put('k1', "k1v")  # hash is 3
        cache_t.put('k2', "k2v")  # hash is 3
        cache_t.put('k3', "k3v")  # hash is 4
        cache_t.put('k7', "k7v")  # hash is 0
        cache_t.put('k8', "k8v")  # hash is 3
        cache_t.put('k99', "k99v")  # hash is 3

        res_keys = cache_t.slots
        res_values = cache_t.values
        ref_keys = ['k99', 'k8', 'k7', 'k1', 'k3']
        ref_values = ['k99v', 'k8v', 'k7v', 'k1v', 'k3v']
        res = (res_keys, res_values)
        ref = (ref_keys, ref_values)
        self.assertEqual(res, ref)

    def test_put__6(self):
        cache_t = NativeCache(5)
        cache_t.put('k1', "k1v")  # hash is 3
        cache_t.put('k2', "k2v")  # hash is 3
        cache_t.put('k3', "k3v")  # hash is 4
        cache_t.put('k7', "k7v")  # hash is 0
        cache_t.put('k8', "k8v")  # hash is 3
        cache_t.put('k99', "k99v")  # hash is 3
        cache_t.get('k99')
        cache_t.put('k222', "k222v")  # hash is 4

        res_keys = cache_t.slots
        res_values = cache_t.values
        ref_keys = ['k99', 'k222', 'k7', 'k1', 'k3']
        ref_values = ['k99v', 'k222v', 'k7v', 'k1v', 'k3v']
        res = (res_keys, res_values)
        ref = (ref_keys, ref_values)
        self.assertEqual(res, ref)

    # We check that the step calculated to bypass collisions is calculated correctly and does not cause the function to hang
    def test_put__7(self):
        for i in range(1, 150):
            cache_t = NativeCache(i)
            for elem in range(i*3):
                cache_t.put(str(random.randint(1, 10_000_000)), 'test')

        self.assertTrue(True)

    # TEST GET() METHOD
    def test_get__1(self):
        cache_t = NativeCache(7)
        cache_t.put('k1', "k1v")  # hash is 1
        cache_t.put('k2', "k2v")  # hash is 1
        cache_t.put('k3', "k3v")  # hash is 2
        result = cache_t.get('k1')
        self.assertEqual(result, 'k1v')

    def test_get__2(self):
        cache_t = NativeCache(7)
        cache_t.put('k1', "k1v")  # hash is 1
        cache_t.put('k2', "k2v")  # hash is 1
        cache_t.put('k3', "k3v")  # hash is 2
        result = cache_t.get('k2')
        self.assertEqual(result, 'k2v')

    def test_get__3(self):
        cache_t = NativeCache(7)
        cache_t.put('k1', "k1v")  # hash is 1
        cache_t.put('k2', "k2v")  # hash is 1
        cache_t.put('k3', "k3v")  # hash is 2
        result = cache_t.get('k3')
        self.assertEqual(result, 'k3v')

    def test_get__4(self):
        cache_t = NativeCache(7)
        cache_t.put('k1', "k1v")  # hash is 1
        cache_t.put('k2', "k2v")  # hash is 1
        cache_t.put('k3', "k3v")  # hash is 2
        result = cache_t.get('k5')
        self.assertEqual(result, None)

    def test_get__5(self):
        cache_t = NativeCache(7)
        cache_t.put('k1', "k1v")  # hash is 1
        cache_t.put('k2', "k2v")  # hash is 1
        cache_t.put('k3', "k3v")  # hash is 2
        cache_t.get('k1')
        cache_t.get('k1')
        cache_t.get('k1')
        cache_t.get('k2')
        cache_t.get('k3')
        self.assertEqual(cache_t.hits, [0, 3, 1, 1, 0, 0, 0])

    def test_get__6(self):
        cache_t = NativeCache(7)
        cache_t.put('k1', "k1v")
        cache_t.put('k2', "k2v")
        cache_t.put('k3', "k3v")
        cache_t.get('k1')
        cache_t.get('k1')
        cache_t.get('k1')
        self.assertEqual(cache_t.hits, [0, 3, 0, 0, 0, 0, 0])

    def test_get__7(self):
        cache_t = NativeCache(7)
        cache_t.put('k1', "k1v")
        cache_t.put('k2', "k2v")
        cache_t.put('k3', "k3v")
        cache_t.put('k4', "k4v")
        cache_t.put('k5', "k5v")
        cache_t.put('k6', "k6v")
        cache_t.put('k7', "k7v")
        for i in range(2):
            [cache_t.get(j) for j in ['k1', 'k2', 'k3', 'k4', 'k5', 'k6', 'k7']]
        cache_t.put('k8', "k8v")
        cache_t.get('k8')
        self.assertEqual(cache_t.hits, [1, 2, 2, 2, 2, 2, 2])
