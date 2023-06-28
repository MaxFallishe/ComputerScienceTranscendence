from ..Task_2 import *
import unittest


class BSTMocksGenerator:
    def __init__(self):
        pass

    def generate_bst(self) -> BST:
        pass


class BSTMocksGeneratorType0(BSTMocksGenerator):
    """
    Binary Strictly Tree object with following structure:
                1
             /    \
           None   None
    """

    def __init__(self):
        super().__init__()
        self.node_1 = BSTNode(1, 'node1', None)

    def generate_bst(self):

        bst = BST(self.node_1)
        return bst


class BSTMocksGeneratorType1(BSTMocksGenerator):
    """
    Binary Strictly Tree object with following structure:
                2
               / \
              1   3
    """

    def __init__(self):
        super().__init__()
        self.node_2 = BSTNode(2, 'node2', None)
        self.node_1 = BSTNode(1, 'node1', self.node_2)
        self.node_3 = BSTNode(3, 'node3', self.node_2)

    def generate_bst(self):
        self.node_2.LeftChild = self.node_1
        self.node_2.RightChild = self.node_3

        bst = BST(self.node_2)
        return bst


class BSTMocksGeneratorType2(BSTMocksGenerator):
    """
    Binary Strictly Tree object with following structure:
                 4
               /  \
              2    6
             / \  / \
            1  3 5  7
    """

    def __init__(self):
        super().__init__()
        self.node_4 = BSTNode(4, 'node4', None)
        self.node_2 = BSTNode(2, 'node2', self.node_4)
        self.node_6 = BSTNode(6, 'node6', self.node_4)
        self.node_1 = BSTNode(1, 'node1', self.node_2)
        self.node_3 = BSTNode(3, 'node3', self.node_2)
        self.node_5 = BSTNode(5, 'node5', self.node_6)
        self.node_7 = BSTNode(7, 'node7', self.node_6)

    def generate_bst(self):
        self.node_4.LeftChild = self.node_2
        self.node_4.RightChild = self.node_6
        self.node_2.LeftChild = self.node_1
        self.node_2.RightChild = self.node_3
        self.node_6.LeftChild = self.node_5
        self.node_6.RightChild = self.node_7

        bst = BST(self.node_4)
        return bst


class BSTMocksGeneratorType3(BSTMocksGenerator):
    """
    Binary Strictly Tree object with following structure:
                   8
                 /    \
               4       12
             /  \     /  \
            2    6    10   14
           / \  / \  / \   / \
          1  3  5 7  9 11 13 15
    """

    def __init__(self):
        super().__init__()
        self.node_8 = BSTNode(8, 'node8', None)
        self.node_4 = BSTNode(4, 'node4', self.node_8)
        self.node_12 = BSTNode(12, 'node12', self.node_8)
        self.node_2 = BSTNode(2, 'node2', self.node_4)
        self.node_6 = BSTNode(6, 'node6', self.node_4)
        self.node_1 = BSTNode(1, 'node1', self.node_2)
        self.node_3 = BSTNode(3, 'node3', self.node_2)
        self.node_5 = BSTNode(5, 'node5', self.node_6)
        self.node_7 = BSTNode(7, 'node7', self.node_6)
        self.node_10 = BSTNode(10, 'node10', self.node_12)
        self.node_14 = BSTNode(14, 'node14', self.node_12)
        self.node_9 = BSTNode(9, 'node9', self.node_10)
        self.node_11 = BSTNode(11, 'node11', self.node_10)
        self.node_13 = BSTNode(13, 'node13', self.node_14)
        self.node_15 = BSTNode(15, 'node15', self.node_14)


    def generate_bst(self):
        self.node_8.LeftChild = self.node_4
        self.node_8.RightChild = self.node_12
        self.node_4.LeftChild = self.node_2
        self.node_4.RightChild = self.node_6
        self.node_2.LeftChild = self.node_1
        self.node_2.RightChild = self.node_3
        self.node_6.LeftChild = self.node_5
        self.node_6.RightChild = self.node_7
        self.node_12.LeftChild = self.node_10
        self.node_12.RightChild = self.node_14
        self.node_10.LeftChild = self.node_9
        self.node_10.RightChild = self.node_11
        self.node_14.LeftChild = self.node_13
        self.node_14.RightChild = self.node_15

        bst = BST(self.node_8)
        return bst


class BSTMocksGeneratorType4(BSTMocksGenerator):
    """
    Binary Strictly Tree object with following structure:
                   8
                 /    \
               4       12
                \     /  \
                 6    10   14
                / \  / \   / \
                N 7  9 11 13  N
    """

    def __init__(self):
        super().__init__()
        self.node_8 = BSTNode(8, 'node8', None)
        self.node_4 = BSTNode(4, 'node4', self.node_8)
        self.node_12 = BSTNode(12, 'node12', self.node_8)
        self.node_6 = BSTNode(6, 'node6', self.node_4)
        self.node_7 = BSTNode(7, 'node7', self.node_6)
        self.node_10 = BSTNode(10, 'node10', self.node_12)
        self.node_14 = BSTNode(14, 'node14', self.node_12)
        self.node_9 = BSTNode(9, 'node9', self.node_10)
        self.node_11 = BSTNode(11, 'node11', self.node_10)
        self.node_13 = BSTNode(13, 'node13', self.node_14)


    def generate_bst(self):
        self.node_8.LeftChild = self.node_4
        self.node_8.RightChild = self.node_12
        self.node_4.RightChild = self.node_6
        self.node_6.RightChild = self.node_7
        self.node_12.LeftChild = self.node_10
        self.node_12.RightChild = self.node_14
        self.node_10.LeftChild = self.node_9
        self.node_10.RightChild = self.node_11
        self.node_14.LeftChild = self.node_13

        bst = BST(self.node_8)
        return bst


class TestFindNodeByKey(unittest.TestCase):
    def test_find_node_by_key__1(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        bst_find = bst.FindNodeByKey(4)
        result = (bst_find.Node, bst_find.NodeHasKey, bst_find.ToLeft)
        reference = (bst_mocks_generator.node_3, False, False)
        self.assertEqual(result, reference)

    def test_find_node_by_key__2(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        bst_find = bst.FindNodeByKey(2.5)
        result = (bst_find.Node, bst_find.NodeHasKey, bst_find.ToLeft)
        reference = (bst_mocks_generator.node_3, False, True)
        self.assertEqual(result, reference)

    def test_find_node_by_key__3(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        bst_find = bst.FindNodeByKey(0.5)
        result = (bst_find.Node, bst_find.NodeHasKey, bst_find.ToLeft)
        reference = (bst_mocks_generator.node_1, False, True)
        self.assertEqual(result, reference)

    def test_find_node_by_key__4(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        bst_find = bst.FindNodeByKey(1.5)
        result = (bst_find.Node, bst_find.NodeHasKey, bst_find.ToLeft)
        reference = (bst_mocks_generator.node_1, False, False)
        self.assertEqual(result, reference)

    def test_find_node_by_key__5(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        bst_find = bst.FindNodeByKey(2)
        result = (bst_find.Node, bst_find.NodeHasKey, bst_find.ToLeft)
        reference = (bst_mocks_generator.node_2, True, False)
        self.assertEqual(result, reference)

    def test_find_node_by_key__6(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        bst_find = bst.FindNodeByKey(1)
        result = (bst_find.Node, bst_find.NodeHasKey, bst_find.ToLeft)
        reference = (bst_mocks_generator.node_1, True, False)
        self.assertEqual(result, reference)

    def test_find_node_by_key__7(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst_find = bst.FindNodeByKey(7)
        result = (bst_find.Node, bst_find.NodeHasKey, bst_find.ToLeft)
        reference = (bst_mocks_generator.node_7, True, False)
        self.assertEqual(result, reference)

    def test_find_node_by_key__8(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst_find = bst.FindNodeByKey(1)
        result = (bst_find.Node, bst_find.NodeHasKey, bst_find.ToLeft)
        reference = (bst_mocks_generator.node_1, True, False)
        self.assertEqual(result, reference)

    def test_find_node_by_key__9(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst_find = bst.FindNodeByKey(99)
        result = (bst_find.Node, bst_find.NodeHasKey, bst_find.ToLeft)
        reference = (bst_mocks_generator.node_7, False, False)
        self.assertEqual(result, reference)

    def test_find_node_by_key__10(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst_find = bst.FindNodeByKey(2.5)
        result = (bst_find.Node, bst_find.NodeHasKey, bst_find.ToLeft)
        reference = (bst_mocks_generator.node_3, False, True)
        self.assertEqual(result, reference)


class TestAddKeyValue(unittest.TestCase):
    def test_find_node_by_key__1(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst.AddKeyValue(0.5, 'new_node')
        result = bst_mocks_generator.node_1.LeftChild.NodeKey
        reference = 0.5
        self.assertEqual(result, reference)

    def test_find_node_by_key__2(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst.AddKeyValue(99, 'new_node')
        result = bst_mocks_generator.node_7.RightChild.NodeKey
        reference = 99
        self.assertEqual(result, reference)

    def test_find_node_by_key__3(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        result = bst.AddKeyValue(5, 'new_node')
        self.assertFalse(result)


class TestFinMinMax(unittest.TestCase):
    def test_fin_min_max__1(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        result = bst.FinMinMax(bst_mocks_generator.node_2, False)
        reference = bst_mocks_generator.node_1
        self.assertEqual(result, reference)

    def test_fin_min_max__2(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        result = bst.FinMinMax(bst_mocks_generator.node_2, True)
        reference = bst_mocks_generator.node_3
        self.assertEqual(result, reference)

    def test_fin_min_max__3(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        result = bst.FinMinMax(bst_mocks_generator.node_4, False)
        reference = bst_mocks_generator.node_1
        self.assertEqual(result, reference)

    def test_fin_min_max__4(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        result = bst.FinMinMax(bst_mocks_generator.node_4, True)
        reference = bst_mocks_generator.node_7
        self.assertEqual(result, reference)

    def test_fin_min_max__5(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        result = bst.FinMinMax(bst_mocks_generator.node_6, False)
        reference = bst_mocks_generator.node_5
        self.assertEqual(result, reference)

    def test_fin_min_max__6(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        result = bst.FinMinMax(bst_mocks_generator.node_2, True)
        reference = bst_mocks_generator.node_3
        self.assertEqual(result, reference)


class TestCount(unittest.TestCase):
    def test_count__1(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        result = bst.Count()
        self.assertEqual(result, 3)

    def test_count__2(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        result = bst.Count()
        self.assertEqual(result, 7)


class TestDeleteNodeToKey(unittest.TestCase):
    def test_delete_node_to_key__1(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeleteNodeByKey(99)
        self.assertEqual(result, False)

    def test_delete_node_to_key__2(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeleteNodeByKey(99)
        self.assertEqual(result, False)

    def test_delete_node_to_key__3(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(1)
        self.assertEqual(bst_mocks_generator.node_2.LeftChild, None)

    def test_delete_node_to_key__4(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(1)
        self.assertEqual(bst_mocks_generator.node_2.RightChild, bst_mocks_generator.node_3)

    def test_delete_node_to_key__5(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(2)
        self.assertEqual(bst_mocks_generator.node_3.LeftChild, bst_mocks_generator.node_1)

    def test_delete_node_to_key__6(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(2)
        self.assertEqual(bst_mocks_generator.node_3.RightChild, None)

    def test_delete_node_to_key__7(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(2)
        self.assertEqual(bst_mocks_generator.node_3.Parent, None)

    def test_delete_node_to_key__8(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(2)
        self.assertEqual(bst_mocks_generator.node_1.Parent, bst_mocks_generator.node_3)

    def test_delete_node_to_key__10(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(7)
        self.assertEqual(bst_mocks_generator.node_5.RightChild, None)

    def test_delete_node_to_key__11(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(6)
        self.assertEqual(bst_mocks_generator.node_4.RightChild, bst_mocks_generator.node_7)


    def test_delete_node_to_key__13(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(4)
        self.assertEqual(bst.Root, bst_mocks_generator.node_5)

    def test_delete_node_to_key__14(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(4)
        self.assertEqual(bst_mocks_generator.node_5.LeftChild, bst_mocks_generator.node_2)

    def test_delete_node_to_key__15(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(4)
        self.assertEqual(bst_mocks_generator.node_2.Parent, bst_mocks_generator.node_5)

    def test_delete_node_to_key__16(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(4)
        self.assertEqual(bst_mocks_generator.node_5.Parent, None)

    def test_delete_node_to_key__17(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(4)
        self.assertEqual(bst_mocks_generator.node_5.Parent, None)

    def test_delete_node_to_key__18(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(4)
        self.assertEqual(bst_mocks_generator.node_8.LeftChild, bst_mocks_generator.node_5)

    def test_delete_node_to_key__19(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(4)
        self.assertEqual(bst_mocks_generator.node_5.Parent, bst_mocks_generator.node_8)

    def test_delete_node_to_key__20(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(4)
        self.assertEqual(bst_mocks_generator.node_5.LeftChild, bst_mocks_generator.node_2)

    def test_delete_node_to_key__21(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(4)
        self.assertEqual(bst_mocks_generator.node_5.RightChild, bst_mocks_generator.node_6)

    def test_delete_node_to_key__22(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()


        bst.DeleteNodeByKey(4)
        self.assertEqual(bst_mocks_generator.node_6.LeftChild, None)

    def test_delete_node_to_key__23(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(12)
        self.assertEqual(bst_mocks_generator.node_8.RightChild, bst_mocks_generator.node_13)

    def test_delete_node_to_key__24(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(12)
        self.assertEqual(bst_mocks_generator.node_13.Parent, bst_mocks_generator.node_8)

    def test_delete_node_to_key__25(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(12)
        self.assertEqual(bst_mocks_generator.node_13.LeftChild, bst_mocks_generator.node_10)

    def test_delete_node_to_key__26(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(12)
        self.assertEqual(bst_mocks_generator.node_13.RightChild, bst_mocks_generator.node_14)

    def test_delete_node_to_key__27(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(8)
        self.assertEqual(bst.Root, bst_mocks_generator.node_9)

    def test_delete_node_to_key__28(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(8)
        self.assertEqual(bst_mocks_generator.node_9.Parent, None)

    def test_delete_node_to_key__29(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(8)
        self.assertEqual(bst_mocks_generator.node_9.LeftChild, bst_mocks_generator.node_4)

    def test_delete_node_to_key__30(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(8)
        self.assertEqual(bst_mocks_generator.node_4.Parent, bst_mocks_generator.node_9)

    def test_delete_node_to_key__31(self):
        bst_mocks_generator = BSTMocksGeneratorType0()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeleteNodeByKey(99)
        self.assertEqual(result, False)

    def test_delete_node_to_key__32(self):
        bst_mocks_generator = BSTMocksGeneratorType0()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(1)
        self.assertEqual(bst.Root, None)

    def test_delete_node_to_key__34(self):
        bst_mocks_generator = BSTMocksGeneratorType4()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(4)
        self.assertEqual(bst_mocks_generator.node_8.LeftChild, bst_mocks_generator.node_6)

    def test_delete_node_to_key__35(self):
        bst_mocks_generator = BSTMocksGeneratorType4()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(4)
        self.assertEqual(bst_mocks_generator.node_6.RightChild, bst_mocks_generator.node_7)

    def test_delete_node_to_key__36(self):
        bst_mocks_generator = BSTMocksGeneratorType4()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(10)
        self.assertEqual(bst_mocks_generator.node_12.LeftChild, bst_mocks_generator.node_11)

    def test_delete_node_to_key__37(self):
        bst_mocks_generator = BSTMocksGeneratorType4()
        bst = bst_mocks_generator.generate_bst()

        bst.DeleteNodeByKey(10)
        self.assertEqual(bst_mocks_generator.node_11.Parent, bst_mocks_generator.node_12)

    def test_delete_node_to_key__38(self):
        from itertools import permutations

        nodes_keys = [i for i in range(1, 7+1)]

        for i in list(permutations(nodes_keys)):
            bst_mocks_generator = BSTMocksGeneratorType2()
            bst = bst_mocks_generator.generate_bst()
            for j in i:
                bst.DeleteNodeByKey(j)
            self.assertEqual(bst.Root, None)


if __name__ == '__main__':
    unittest.main()
