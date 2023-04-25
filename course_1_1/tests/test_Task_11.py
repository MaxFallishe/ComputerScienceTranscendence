from ..Task_11 import *
import unittest



class TestTask11(unittest.TestCase):
    def test_hash1__1(self):
        bloomf = BloomFilter(32)
        result = bloomf.hash1('0123456789')
        self.assertEqual(result, 13)

    def test_hash2__1(self):
        bloomf = BloomFilter(32)
        result = bloomf.hash2('0123456789')
        self.assertEqual(result, 5)

    def test_add__1(self):
        bloomf = BloomFilter(32)
        bloomf.add('0123456789')
        result = bin(bloomf.filter)
        self.assertEqual(result, '0b10000000100000')

    def test_add__2(self):
        bloomf = BloomFilter(32)
        result = bin(bloomf.filter)
        self.assertEqual(result, '0b0')

    def test_add__3(self):
        bloomf = BloomFilter(32)
        bloomf.add('0123456789')
        bloomf.add('1234567890')
        result = bin(bloomf.filter)
        self.assertEqual(result, '0b101000000000000010000000100000')

    def test_add__4(self):
        bloomf = BloomFilter(32)
        bloomf.add('0123456789')
        bloomf.add('1234567890')
        bloomf.add('3456789012')
        result = bin(bloomf.filter)
        self.assertEqual(result, '0b101000000000000010000000100000')

    def test_isvalue__1(self):
        bloomf = BloomFilter(32)
        bloomf.add('0123456789')
        result = bloomf.is_value('0123456789')
        self.assertEqual(result, True)

    def test_isvalue__2(self):
        bloomf = BloomFilter(32)
        bloomf.add('0123456789')
        result = bloomf.is_value('99999999')
        self.assertEqual(result, False)

    def test_isvalue__3(self):
        bloomf = BloomFilter(32)
        bloomf.add('0123456789')
        bloomf.add('1234567890')
        result = (bloomf.is_value('0123456789'), bloomf.is_value('1234567890'))
        ref = (True, True)
        self.assertEqual(result, ref)

    def test_isvalue__4(self):
        bloomf = BloomFilter(32)
        f = 0
        result = True
        for i in range(10):
            j = "".join(str(j % 10) for j in range(f, 10+f))
            f += 1
            bloomf.add(j)
        for i in range(10):
            j = "".join(str(j % 10) for j in range(f, 10+f))
            f += 1
            result *= bloomf.is_value(j)
        self.assertEqual(result, True)
