from ..Task_9 import *
import unittest


class TestTask9(unittest.TestCase):
    # TEST HASH_FUN() METHOD
    def test_hashfun__1(self):
        hash_t = NativeDictionary(7)
        hash1 = hash_t.hash_fun('a')
        hash2 = hash_t.hash_fun('a')
        self.assertEqual(hash1, hash2)

    def test_hashfun__2(self):
        hash_t = NativeDictionary(7)
        hash1 = hash_t.hash_fun('a')
        hash2 = hash_t.hash_fun('dshg')
        self.assertNotEqual(hash1, hash2)

    def test_hashfun__3(self):
        hash_t = NativeDictionary(7)
        hash1 = hash_t.hash_fun('')
        hash2 = hash_t.hash_fun('')
        self.assertEqual(hash1, hash2)

    # TEST IS_KEY() METHOD
    def test_iskey__1(self):
        hash_t = NativeDictionary(7)
        hash_t.slots[0], hash_t.values[0] = 'k1', 'k1v'
        self.assertTrue(hash_t.is_key('k1'))

    def test_iskey__2(self):
        hash_t = NativeDictionary(7)
        hash_t.slots[0], hash_t.values[0] = 'k1', 'k1v'
        hash_t.slots[5], hash_t.values[5] = 'k2', 'k2v'
        self.assertTrue(hash_t.is_key('k2'))

    def test_iskey__3(self):
        hash_t = NativeDictionary(7)
        hash_t.slots[0], hash_t.values[0] = 'k1', 'k1v'
        hash_t.slots[5], hash_t.values[5] = 'k2', 'k2v'
        self.assertFalse(hash_t.is_key('k3'))

    def test_iskey__4(self):
        hash_t = NativeDictionary(7)
        self.assertFalse(hash_t.is_key('k1'))

    # TEST PUT() METHOD
    def test_put__1(self):
        hash_t = NativeDictionary(7)
        hash_t.put('k3', "k3v")  # hash is 2
        res_keys = hash_t.slots
        res_values = hash_t.values
        ref_keys = [None, None, 'k3', None, None, None, None]
        ref_values = [None, None, 'k3v', None, None, None, None]
        res = (res_keys, res_values)
        ref = (ref_keys, ref_values)
        self.assertEqual(res, ref)

    def test_put__2(self):
        hash_t = NativeDictionary(7)
        hash_t.put('k3', "k3v")  # hash is 2
        hash_t.put('k3', "k3v_v2")  # hash is 2
        res_keys = hash_t.slots
        res_values = hash_t.values
        ref_keys = [None, None, 'k3', None, None, None, None]
        ref_values = [None, None, 'k3v_v2', None, None, None, None]
        res = (res_keys, res_values)
        ref = (ref_keys, ref_values)
        self.assertEqual(res, ref)

    def test_put__3(self):
        hash_t = NativeDictionary(7)
        hash_t.put('k1', "k1v")  # hash is 1
        hash_t.put('k3', "k3v")  # hash is 2
        res_keys = hash_t.slots
        res_values = hash_t.values
        ref_keys = [None, 'k1', 'k3', None, None, None, None]
        ref_values = [None, 'k1v', 'k3v', None, None, None, None]
        res = (res_keys, res_values)
        ref = (ref_keys, ref_values)
        self.assertEqual(res, ref)

    def test_put__4(self):
        hash_t = NativeDictionary(7)
        hash_t.put('k1', "k1v")  # hash is 1
        hash_t.put('k2', "k2v")  # hash is 1
        hash_t.put('k3', "k3v")  # hash is 2
        res_keys = hash_t.slots
        res_values = hash_t.values
        ref_keys = [None, 'k1', 'k3', None, 'k2', None, None]
        ref_values = [None, 'k1v', 'k3v', None, 'k2v', None, None]
        res = (res_keys, res_values)
        ref = (ref_keys, ref_values)
        self.assertEqual(res, ref)

    def test_put__5(self):
        hash_t = NativeDictionary(5)
        hash_t.put('k1', "k1v")  # hash is 3
        hash_t.put('k2', "k2v")  # hash is 3
        hash_t.put('k3', "k3v")  # hash is 4
        hash_t.put('k7', "k7v")  # hash is 0
        hash_t.put('k8', "k8v")  # hash is 3
        hash_t.put('k99', "k99v")  # hash is 3

        res_keys = hash_t.slots
        res_values = hash_t.values
        ref_keys = ['k7', 'k2', 'k8', 'k1', 'k3']
        ref_values = ['k7v', 'k2v', 'k8v', 'k1v', 'k3v']
        res = (res_keys, res_values)
        ref = (ref_keys, ref_values)
        self.assertEqual(res, ref)

    # TEST GET() METHOD
    def test_get__1(self):
        hash_t = NativeDictionary(7)
        hash_t.put('k1', "k1v")  # hash is 1
        hash_t.put('k2', "k2v")  # hash is 1
        hash_t.put('k3', "k3v")  # hash is 2
        result = hash_t.get('k1')
        self.assertEqual(result, 'k1v')

    def test_get__2(self):
        hash_t = NativeDictionary(7)
        hash_t.put('k1', "k1v")  # hash is 1
        hash_t.put('k2', "k2v")  # hash is 1
        hash_t.put('k3', "k3v")  # hash is 2
        result = hash_t.get('k2')
        self.assertEqual(result, 'k2v')

    def test_get__3(self):
        hash_t = NativeDictionary(7)
        hash_t.put('k1', "k1v")  # hash is 1
        hash_t.put('k2', "k2v")  # hash is 1
        hash_t.put('k3', "k3v")  # hash is 2
        result = hash_t.get('k3')
        self.assertEqual(result, 'k3v')

    def test_get__4(self):
        hash_t = NativeDictionary(7)
        hash_t.put('k1', "k1v")  # hash is 1
        hash_t.put('k2', "k2v")  # hash is 1
        hash_t.put('k3', "k3v")  # hash is 2
        result = hash_t.get('k5')
        self.assertEqual(result, None)
