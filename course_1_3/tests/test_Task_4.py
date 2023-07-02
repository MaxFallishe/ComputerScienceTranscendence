from ..Task_4 import *
import unittest


class aBSTMocksGenerator:
    def __init__(self):
        pass

    def generate_abst(self) -> aBST:
        pass


class aBSTMocksGeneratorType0(aBSTMocksGenerator):
    """
    Binary Strictly Tree object with following structure:
                1
             /    \
           None   None
    """

    def generate_abst(self):
        a_bst = aBST(1)
        a_bst.Tree[0] = 1
        a_bst.Tree[1] = None
        a_bst.Tree[2] = None
        return a_bst


class aBSTMocksGeneratorType1(aBSTMocksGenerator):
    """
    Binary Strictly Tree object (only one node, without children) with following structure:
                1
    """

    def generate_abst(self):
        a_bst = aBST(0)
        a_bst.Tree[0] = 1
        return a_bst


class aBSTMocksGeneratorType2(aBSTMocksGenerator):
    """
    Binary Strictly Tree object with following structure:
                 4
               /  \
              2    6
             / \  / \
            1  3 5  7
    """

    def generate_abst(self):
        a_bst = aBST(2)
        a_bst.Tree[0] = 4
        a_bst.Tree[1] = 2
        a_bst.Tree[2] = 6
        a_bst.Tree[3] = 1
        a_bst.Tree[4] = 3
        a_bst.Tree[5] = 5
        a_bst.Tree[6] = 7
        return a_bst


class aBSTMocksGeneratorType3(aBSTMocksGenerator):
    """
    Binary Strictly Tree object with following structure:
                   8
                 /    \
               4       12
                \     /  \
                 6    10   14
                / \  / \   / \
               N  7 9 11  13  N
    """

    def generate_abst(self):
        a_bst = aBST(3)
        a_bst.Tree[0] = 8
        a_bst.Tree[1] = 4
        a_bst.Tree[2] = 12
        a_bst.Tree[3] = None
        a_bst.Tree[4] = 6
        a_bst.Tree[5] = 10
        a_bst.Tree[6] = 14
        a_bst.Tree[7] = None
        a_bst.Tree[8] = None
        a_bst.Tree[9] = None
        a_bst.Tree[10] = 7
        a_bst.Tree[11] = 9
        a_bst.Tree[12] = 11
        a_bst.Tree[13] = 13
        a_bst.Tree[14] = None

        return a_bst


class TestFindKeyIndex(unittest.TestCase):
    def test_find_key_index__1(self):
        abst_mocks_generator = aBSTMocksGeneratorType0()
        a_bst = abst_mocks_generator.generate_abst()
        result = a_bst.FindKeyIndex(1)
        self.assertEqual(result, 0)

    def test_find_key_index__2(self):
        abst_mocks_generator = aBSTMocksGeneratorType0()
        a_bst = abst_mocks_generator.generate_abst()
        result = a_bst.FindKeyIndex(-5)
        self.assertEqual(result, -1)

    def test_find_key_index__3(self):
        abst_mocks_generator = aBSTMocksGeneratorType0()
        a_bst = abst_mocks_generator.generate_abst()
        result = a_bst.FindKeyIndex(5)
        self.assertEqual(result, -2)

    def test_find_key_index__4(self):
        abst_mocks_generator = aBSTMocksGeneratorType1()
        a_bst = abst_mocks_generator.generate_abst()
        result = a_bst.FindKeyIndex(1)
        self.assertEqual(result, 0)

    def test_find_key_index__5(self):
        abst_mocks_generator = aBSTMocksGeneratorType1()
        a_bst = abst_mocks_generator.generate_abst()
        result = a_bst.FindKeyIndex(3)
        self.assertEqual(result, None)

    def test_find_key_index__6(self):
        abst_mocks_generator = aBSTMocksGeneratorType1()
        a_bst = abst_mocks_generator.generate_abst()
        a_bst.Tree[0] = None
        result = a_bst.FindKeyIndex(99)
        self.assertEqual(result, 0)

    def test_find_key_index__7(self):
        abst_mocks_generator = aBSTMocksGeneratorType2()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.FindKeyIndex(5)
        self.assertEqual(result, 5)

    def test_find_key_index__8(self):
        abst_mocks_generator = aBSTMocksGeneratorType2()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.FindKeyIndex(4)
        self.assertEqual(result, 0)

    def test_find_key_index__9(self):
        abst_mocks_generator = aBSTMocksGeneratorType2()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.FindKeyIndex(11)
        self.assertEqual(result, None)

    def test_find_key_index__10(self):
        abst_mocks_generator = aBSTMocksGeneratorType3()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.FindKeyIndex(3)
        self.assertEqual(result, -3)

    def test_find_key_index__11(self):
        abst_mocks_generator = aBSTMocksGeneratorType3()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.FindKeyIndex(11.5)
        self.assertEqual(result, None)

    def test_find_key_index__12(self):
        abst_mocks_generator = aBSTMocksGeneratorType3()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.FindKeyIndex(10)
        self.assertEqual(result, 5)


class TestAddKey(unittest.TestCase):
    def test_add_key__1(self):
        abst_mocks_generator = aBSTMocksGeneratorType0()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.AddKey(5)
        self.assertEqual(result, 2)

    def test_add_key__2(self):
        abst_mocks_generator = aBSTMocksGeneratorType1()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.AddKey(1)
        self.assertEqual(result, 0)

    def test_add_key__3(self):
        abst_mocks_generator = aBSTMocksGeneratorType1()
        a_bst = abst_mocks_generator.generate_abst()
        a_bst.Tree[0] = None

        result = a_bst.AddKey(1)
        self.assertEqual(result, 0)

    def test_add_key__4(self):
        abst_mocks_generator = aBSTMocksGeneratorType1()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.AddKey(3)
        self.assertEqual(result, -1)

    def test_add_key__5(self):
        abst_mocks_generator = aBSTMocksGeneratorType2()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.AddKey(2)
        self.assertEqual(result, 1)

    def test_add_key__6(self):
        abst_mocks_generator = aBSTMocksGeneratorType2()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.AddKey(99)
        self.assertEqual(result, -1)

    def test_add_key__7(self):
        abst_mocks_generator = aBSTMocksGeneratorType2()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.AddKey(-99)
        self.assertEqual(result, -1)

    def test_add_key__8(self):
        abst_mocks_generator = aBSTMocksGeneratorType3()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.AddKey(3)
        self.assertEqual(result, 3)

    def test_add_key__9(self):
        abst_mocks_generator = aBSTMocksGeneratorType3()
        a_bst = abst_mocks_generator.generate_abst()

        a_bst.AddKey(3)
        result = a_bst.AddKey(2)
        self.assertEqual(result, 7)

    def test_add_key__10(self):
        abst_mocks_generator = aBSTMocksGeneratorType3()
        a_bst = abst_mocks_generator.generate_abst()

        result = a_bst.AddKey(13)
        self.assertEqual(result, 13)
