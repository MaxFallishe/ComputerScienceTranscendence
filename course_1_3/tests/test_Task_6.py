from ..Task_6 import *
import unittest


class TestGenerateTree(unittest.TestCase):
    def test_generate_tree__1(self):
        test_a = [1]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        self.assertEqual(bbst.Root.NodeKey, 1)

    def test_generate_tree__2(self):
        test_a = [1, 2, 3]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        self.assertEqual(bbst.Root.NodeKey, 2)

    def test_generate_tree__3(self):
        test_a = [1, 2, 3, 4, 5, 6, 7]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        self.assertEqual(bbst.Root.NodeKey, 4)

    def test_generate_tree__4(self):
        test_a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        self.assertEqual(bbst.Root.NodeKey, 8)

    def test_generate_tree__5(self):
        test_a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        self.assertEqual(bbst.Root.Level, 0)

    def test_generate_tree__6(self):
        test_a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        self.assertEqual(bbst.Root.RightChild.NodeKey, 12)

    def test_generate_tree__7(self):
        test_a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        self.assertEqual(bbst.Root.RightChild.Level, 1)

    def test_generate_tree__8(self):
        test_a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        self.assertEqual(bbst.Root.RightChild.RightChild.RightChild.NodeKey, 15)

    def test_generate_tree__9(self):
        test_a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        self.assertEqual(bbst.Root.RightChild.RightChild.RightChild.Level, 3)

    def test_generate_tree__10(self):
        test_a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        self.assertEqual(bbst.Root.LeftChild.LeftChild.LeftChild.Level, 3)


class TestIsBalanced(unittest.TestCase):
    def test_is_balanced__1(self):
        test_a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        self.assertEqual(bbst.IsBalanced(bbst.Root), True)

    def test_is_balanced__2(self):
        test_a = [1, 2, 3]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        self.assertEqual(bbst.IsBalanced(bbst.Root), True)

    def test_is_balanced__3(self):
        test_a = [1, 2, 3]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)
        bbst.Root.LeftChild.NodeKey = 99

        self.assertEqual(bbst.IsBalanced(bbst.Root), False)

    def test_is_balanced__4(self):
        test_a = [1, 2, 3]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)
        bbst.Root.RightChild.NodeKey = -99

        self.assertEqual(bbst.IsBalanced(bbst.Root), False)

    def test_is_balanced__5(self):
        test_a = [1, 2, 3]
        bbst = BalancedBST()
        bbst.GenerateTree(test_a)

        bbst.Root.LeftChild = None
        node_new = BSTNode(99, bbst.Root.RightChild)
        node_new.Level = 2
        bbst.Root.RightChild.RightChild = node_new

        self.assertEqual(bbst.IsBalanced(bbst.Root), False)

