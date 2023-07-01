from ..Task_3 import *
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
        self.node_1 = BSTNode(1, "node1", None)

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
        self.node_2 = BSTNode(2, "node2", None)
        self.node_1 = BSTNode(1, "node1", self.node_2)
        self.node_3 = BSTNode(3, "node3", self.node_2)

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
        self.node_4 = BSTNode(4, "node4", None)
        self.node_2 = BSTNode(2, "node2", self.node_4)
        self.node_6 = BSTNode(6, "node6", self.node_4)
        self.node_1 = BSTNode(1, "node1", self.node_2)
        self.node_3 = BSTNode(3, "node3", self.node_2)
        self.node_5 = BSTNode(5, "node5", self.node_6)
        self.node_7 = BSTNode(7, "node7", self.node_6)

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
        self.node_8 = BSTNode(8, "node8", None)
        self.node_4 = BSTNode(4, "node4", self.node_8)
        self.node_12 = BSTNode(12, "node12", self.node_8)
        self.node_2 = BSTNode(2, "node2", self.node_4)
        self.node_6 = BSTNode(6, "node6", self.node_4)
        self.node_1 = BSTNode(1, "node1", self.node_2)
        self.node_3 = BSTNode(3, "node3", self.node_2)
        self.node_5 = BSTNode(5, "node5", self.node_6)
        self.node_7 = BSTNode(7, "node7", self.node_6)
        self.node_10 = BSTNode(10, "node10", self.node_12)
        self.node_14 = BSTNode(14, "node14", self.node_12)
        self.node_9 = BSTNode(9, "node9", self.node_10)
        self.node_11 = BSTNode(11, "node11", self.node_10)
        self.node_13 = BSTNode(13, "node13", self.node_14)
        self.node_15 = BSTNode(15, "node15", self.node_14)

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
        self.node_8 = BSTNode(8, "node8", None)
        self.node_4 = BSTNode(4, "node4", self.node_8)
        self.node_12 = BSTNode(12, "node12", self.node_8)
        self.node_6 = BSTNode(6, "node6", self.node_4)
        self.node_7 = BSTNode(7, "node7", self.node_6)
        self.node_10 = BSTNode(10, "node10", self.node_12)
        self.node_14 = BSTNode(14, "node14", self.node_12)
        self.node_9 = BSTNode(9, "node9", self.node_10)
        self.node_11 = BSTNode(11, "node11", self.node_10)
        self.node_13 = BSTNode(13, "node13", self.node_14)

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


class BSTMocksGeneratorType5(BSTMocksGenerator):
    """
    Binary Strictly Tree object with following structure:
                    8
                 /     \
               4        12
                \         \
                 6         14
                           / \
                          13  15
    """

    def __init__(self):
        super().__init__()
        self.node_8 = BSTNode(8, "node8", None)
        self.node_4 = BSTNode(4, "node4", self.node_8)
        self.node_12 = BSTNode(12, "node12", self.node_8)
        self.node_6 = BSTNode(6, "node6", self.node_4)
        self.node_14 = BSTNode(14, "node14", self.node_12)
        self.node_13 = BSTNode(13, "node13", self.node_14)
        self.node_15 = BSTNode(15, "node15", self.node_14)

    def generate_bst(self):
        self.node_8.LeftChild = self.node_4
        self.node_8.RightChild = self.node_12
        self.node_4.RightChild = self.node_6
        self.node_12.RightChild = self.node_14
        self.node_14.LeftChild = self.node_13
        self.node_14.RightChild = self.node_15

        bst = BST(self.node_8)
        return bst


class BSTMocksGeneratorType6(BSTMocksGenerator):
    """
    Binary Strictly Tree object with following structure:
                    6
                      \
                       12
                      /  \
                    10    14
                   / \    / \
                  9  11  13  15
                /     \  /    \
               8      N N      20
    """

    def __init__(self):
        super().__init__()
        self.node_6 = BSTNode(6, "node6", None)
        self.node_12 = BSTNode(12, "node12", self.node_6)
        self.node_10 = BSTNode(10, "node10", self.node_12)
        self.node_14 = BSTNode(14, "node14", self.node_12)
        self.node_13 = BSTNode(13, "node13", self.node_14)
        self.node_15 = BSTNode(15, "node15", self.node_14)
        self.node_9 = BSTNode(9, "node9", self.node_10)
        self.node_11 = BSTNode(11, "node11", self.node_10)
        self.node_8 = BSTNode(8, "node8", self.node_9)
        self.node_20 = BSTNode(20, "node20", self.node_15)

    def generate_bst(self):
        self.node_6.RightChild = self.node_12
        self.node_12.LeftChild = self.node_10
        self.node_12.RightChild = self.node_14
        self.node_10.LeftChild = self.node_9
        self.node_10.RightChild = self.node_11
        self.node_14.LeftChild = self.node_13
        self.node_14.RightChild = self.node_15
        self.node_9.LeftChild = self.node_8
        self.node_15.RightChild = self.node_20

        bst = BST(self.node_6)
        return bst


class TestWideAllNodes(unittest.TestCase):
    def test_wide_all_nodes__1(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        result = bst.WideAllNodes()
        result = tuple(i.NodeKey for i in result)
        reference = (4, 2, 6, 1, 3, 5, 7)
        self.assertEqual(result, reference)

    def test_wide_all_nodes__2(self):
        bst_mocks_generator = BSTMocksGeneratorType4()
        bst = bst_mocks_generator.generate_bst()

        result = bst.WideAllNodes()
        result = tuple(i.NodeKey for i in result)
        reference = (8, 4, 12, 6, 10, 14, 7, 9, 11, 13)
        self.assertEqual(result, reference)

    def test_wide_all_nodes__3(self):
        bst_mocks_generator = BSTMocksGeneratorType0()
        bst = bst_mocks_generator.generate_bst()

        result = bst.WideAllNodes()
        result = tuple(i.NodeKey for i in result)
        reference = (1,)
        self.assertEqual(result, reference)

    def test_wide_all_nodes__4(self):
        bst_mocks_generator = BSTMocksGeneratorType0()
        bst = bst_mocks_generator.generate_bst()
        bst.DeleteNodeByKey(1)

        result = bst.WideAllNodes()
        result = tuple(i.NodeKey for i in result)
        reference = ()
        self.assertEqual(result, reference)


class TestDeepAllNodes(unittest.TestCase):
    def test_deep_all_nodes__1(self):
        bst_mocks_generator = BSTMocksGeneratorType0()
        bst = bst_mocks_generator.generate_bst()
        bst.DeleteNodeByKey(1)

        result = bst.DeepAllNodes(0)
        result = tuple(i.NodeKey for i in result)

        reference = ()
        self.assertEqual(result, reference)

    def test_deep_all_nodes__2(self):
        bst_mocks_generator = BSTMocksGeneratorType0()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(0)
        result = tuple(i.NodeKey for i in result)

        reference = (1,)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__3(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(0)
        result = tuple(i.NodeKey for i in result)

        reference = (1, 2, 3,)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__4(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(0)
        result = tuple(i.NodeKey for i in result)

        reference = (1, 2, 3, 4, 5, 6, 7,)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__5(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(0)
        result = tuple(i.NodeKey for i in result)

        reference = (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__6(self):
        bst_mocks_generator = BSTMocksGeneratorType4()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(0)
        result = tuple(i.NodeKey for i in result)

        reference = (4, 6, 7, 8, 9, 10, 11, 12, 13, 14,)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__7(self):
        bst_mocks_generator = BSTMocksGeneratorType5()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(0)
        result = tuple(i.NodeKey for i in result)

        reference = (4, 6, 8, 12, 13, 14, 15)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__8(self):
        bst_mocks_generator = BSTMocksGeneratorType0()
        bst = bst_mocks_generator.generate_bst()
        bst.DeleteNodeByKey(1)

        result = bst.DeepAllNodes(1)
        result = tuple(i.NodeKey for i in result)

        reference = ()
        self.assertEqual(result, reference)

    def test_deep_all_nodes__9(self):
        bst_mocks_generator = BSTMocksGeneratorType0()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(1)
        result = tuple(i.NodeKey for i in result)

        reference = (1,)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__10(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(1)
        result = tuple(i.NodeKey for i in result)

        reference = (1, 3, 2, )
        self.assertEqual(result, reference)

    def test_deep_all_nodes__11(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(1)
        result = tuple(i.NodeKey for i in result)

        reference = (1, 3, 2, 5, 7, 6, 4)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__12(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(1)
        result = tuple(i.NodeKey for i in result)

        reference = (1, 3, 2, 5, 7, 6, 4, 9, 11, 10, 13, 15, 14, 12, 8)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__13(self):
        bst_mocks_generator = BSTMocksGeneratorType4()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(1)
        result = tuple(i.NodeKey for i in result)

        reference = (7, 6, 4, 9, 11, 10, 13, 14, 12, 8)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__14(self):
        bst_mocks_generator = BSTMocksGeneratorType5()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(1)
        result = tuple(i.NodeKey for i in result)

        reference = (6, 4, 13, 15, 14, 12, 8)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__15(self):
        bst_mocks_generator = BSTMocksGeneratorType0()
        bst = bst_mocks_generator.generate_bst()
        bst.DeleteNodeByKey(1)

        result = bst.DeepAllNodes(2)
        result = tuple(i.NodeKey for i in result)

        reference = ()
        self.assertEqual(result, reference)

    def test_deep_all_nodes__16(self):
        bst_mocks_generator = BSTMocksGeneratorType0()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(2)
        result = tuple(i.NodeKey for i in result)

        reference = (1,)
        self.assertEqual(result, reference)


    def test_deep_all_nodes__17(self):
        bst_mocks_generator = BSTMocksGeneratorType1()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(2)
        result = tuple(i.NodeKey for i in result)

        reference = (2, 1, 3)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__18(self):
        bst_mocks_generator = BSTMocksGeneratorType2()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(2)
        result = tuple(i.NodeKey for i in result)

        reference = (4, 2, 1, 3, 6, 5, 7)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__19(self):
        bst_mocks_generator = BSTMocksGeneratorType3()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(2)
        result = tuple(i.NodeKey for i in result)

        reference = (8, 4, 2, 1, 3, 6, 5, 7, 12, 10, 9, 11, 14, 13, 15)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__20(self):
        bst_mocks_generator = BSTMocksGeneratorType4()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(2)
        result = tuple(i.NodeKey for i in result)

        reference = (8, 4, 6, 7, 12, 10, 9, 11, 14, 13)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__21(self):
        bst_mocks_generator = BSTMocksGeneratorType5()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(2)
        result = tuple(i.NodeKey for i in result)

        reference = (8, 4, 6, 12, 14, 13, 15)
        self.assertEqual(result, reference)

    def test_deep_all_nodes__22(self):
        bst_mocks_generator = BSTMocksGeneratorType6()
        bst = bst_mocks_generator.generate_bst()

        result = bst.DeepAllNodes(2)
        result = tuple(i.NodeKey for i in result)

        reference = (6, 12, 10, 9, 8, 11, 14, 13, 15, 20)
        self.assertEqual(result, reference)
