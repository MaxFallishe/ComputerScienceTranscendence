from ..Task_8 import *
import unittest


class TestTask7(unittest.TestCase):
    # TEST HASH_FUN() METHOD
    def test_hashfun__1(self):
        hash_t = HashTable(19, 3)
        hash1 = hash_t.hash_fun('a')
        hash2 = hash_t.hash_fun('a')
        self.assertEqual(hash1, hash2)

    def test_hashfun__2(self):
        hash_t = HashTable(19, 3)
        hash1 = hash_t.hash_fun('a')
        hash2 = hash_t.hash_fun('dshg')
        self.assertNotEqual(hash1, hash2)

    # TEST SEEK_SLOT() METHOD
    def test_seekslot__1(self):
        hash_t = HashTable(7, 3)
        hash_t.slots[0] = "BUSY"
        hash_t.slots[1] = "BUSY"
        hash_t.slots[2] = "BUSY"
        hash_t.slots[3] = "BUSY"
        hash_t.slots[4] = "BUSY"
        hash_t.slots[5] = "BUSY"
        hash_t.slots[6] = "BUSY"
        result = hash_t.seek_slot('teststr')
        self.assertEqual(result, None)

    def test_seekslot__2(self):
        hash_t = HashTable(7, 3)
        hash_t.slots[1] = "BUSY"
        hash_t.slots[2] = "BUSY"
        hash_t.slots[3] = "BUSY"
        hash_t.slots[4] = "BUSY"
        hash_t.slots[5] = "BUSY"
        hash_t.slots[6] = "BUSY"
        result = hash_t.seek_slot('teststr')
        self.assertEqual(result, 0)

    def test_seekslot__3(self):
        hash_t = HashTable(7, 3)
        hash_t.slots[0] = "BUSY"
        hash_t.slots[1] = "BUSY"
        hash_t.slots[2] = "BUSY"
        hash_t.slots[3] = "BUSY"
        hash_t.slots[5] = "BUSY"
        hash_t.slots[6] = "BUSY"
        result = hash_t.seek_slot('teststr')
        self.assertEqual(result, 4)


    def test_seekslot__4(self):
        hash_t = HashTable(7, 3)
        hash1 = hash_t.hash_fun('teststr')
        result = hash_t.seek_slot('teststr')
        self.assertEqual(result, hash1)

    # TEST PUT() METHOD
    def test_put__1(self):
        hash_t = HashTable(7, 3)
        hash1 = hash_t.hash_fun('teststr')
        result = hash_t.put('teststr')
        self.assertEqual(result, hash1)

    def test_put__2(self):
        hash_t = HashTable(7, 3)
        hash_t.slots[0] = "BUSY"
        hash_t.slots[1] = "BUSY"
        hash_t.slots[2] = "BUSY"
        hash_t.slots[3] = "BUSY"
        hash_t.slots[4] = "BUSY"
        hash_t.slots[5] = "BUSY"
        hash_t.slots[6] = "BUSY"
        result = (hash_t.put('teststr'), hash_t.slots)
        ref = (None, ["BUSY", "BUSY", "BUSY", "BUSY", "BUSY", "BUSY", "BUSY"])
        self.assertEqual(result, ref)

    def test_put__3(self):
        hash_t = HashTable(7, 3)
        hash_t.slots[0] = "BUSY"
        hash_t.slots[1] = "BUSY"
        hash_t.slots[3] = "BUSY"
        hash_t.slots[4] = "BUSY"
        hash_t.slots[5] = "BUSY"
        hash_t.slots[6] = "BUSY"
        result = (hash_t.put('teststr'), hash_t.slots)
        ref = (2, ["BUSY", "BUSY", "teststr", "BUSY", "BUSY", "BUSY", "BUSY"])
        self.assertEqual(result, ref)

    def test_put__4(self):
        hash_t = HashTable(7, 3)
        hash_t.slots[0] = "BUSY"
        hash_t.slots[1] = "BUSY"
        hash_t.slots[6] = "BUSY"
        result = (hash_t.put('teststr'), hash_t.slots)
        ref = (2, ["BUSY", "BUSY", "teststr", None, None, None, "BUSY"])
        self.assertEqual(result, ref)

    def test_put__5(self):
        hash_t = HashTable(7, 3)
        hash_t.slots[0] = "BUSY"
        hash_t.slots[1] = "BUSY"
        hash_t.slots[2] = "BUSY"
        hash_t.slots[6] = "BUSY"
        result = (hash_t.put('teststr'), hash_t.slots)
        ref = (5, ["BUSY", "BUSY", "BUSY", None, None, "teststr", "BUSY"])
        self.assertEqual(result, ref)

    # TEST PUT() METHOD
    def test_find__1(self):
        hash_t = HashTable(7, 3)
        result = hash_t.find('teststr')
        self.assertEqual(result, None)

    def test_find__2(self):
        hash_t = HashTable(7, 3)
        hash_t.slots[2] = "BUSY"
        hash_t.slots[6] = "teststr"
        result = hash_t.find('teststr')
        self.assertEqual(result, 6)

    def test_find__3(self):
        hash_t = HashTable(7, 3)
        hash_t.slots[2] = "BUSY"
        hash_t.slots[6] = "BUSY"
        hash_t.put('teststr')
        result = hash_t.find('teststr')
        self.assertEqual(result, 5)
