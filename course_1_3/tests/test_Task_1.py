from ..Task_1 import *
import unittest


class TestTask1(unittest.TestCase):
    # ### TEST ADD_CHILD() METHOD
    def test_add_child__1(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        self.assertEqual(node_0.Children, [node_1])

    def test_add_child__2(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        self.assertEqual(node_1.Parent, node_0)

    def test_add_child__3(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        self.assertEqual(node_0.Children, [node_1, node_2])

    def test_add_child__4(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        self.assertEqual(node_1.Parent, node_0)

    def test_add_child__5(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        self.assertEqual(node_2.Parent, node_0)

    def test_add_child__6(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_1, node_2)
        self.assertEqual(node_0.Children, [node_1])

    def test_add_child__7(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_1, node_2)
        self.assertEqual(node_1.Children, [node_2])

    def test_add_child__8(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_1, node_2)
        self.assertEqual(node_2.Parent, node_1)

    def test_add_child__9(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_0, node_3)
        self.assertEqual(node_0.Children, [node_1, node_2, node_3])

    # ### TEST DELETE_NODE() METHOD
    def test_delete_node__1(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.DeleteNode(node_1)
        self.assertEqual(node_0.Children, [])

    def test_delete_node__2(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.DeleteNode(node_1)
        self.assertEqual(node_0.Parent, None)

    def test_delete_node__3(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.DeleteNode(node_1)
        self.assertEqual(node_0.Children, [node_2])

    def test_delete_node__4(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.DeleteNode(node_1)
        self.assertEqual(node_1.Parent, None)

    def test_delete_node__5(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.DeleteNode(node_1)
        self.assertEqual(node_2.Parent, node_0)

    def test_delete_node__6(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_1, node_2)
        simple_tree.DeleteNode(node_1)
        self.assertEqual(node_0.Children, [])

    def test_delete_node__7(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_1, node_2)
        simple_tree.AddChild(node_0, node_3)
        simple_tree.DeleteNode(node_1)
        self.assertEqual(node_0.Children, [node_3])

    # ### TEST GET_ALL_NODES() METHOD
    def test_get_all_nodes__1(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        result = set(simple_tree.GetAllNodes())
        reference = {node_0, node_1, node_2}
        self.assertEqual(result, reference)

    def test_get_all_nodes__2(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        result = simple_tree.GetAllNodes()
        self.assertTrue(isinstance(result, list))


    def test_get_all_nodes__3(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_1, node_2)
        simple_tree.AddChild(node_2, node_3)
        result = set(simple_tree.GetAllNodes())
        reference = {node_0, node_1, node_2, node_3}
        self.assertEqual(result, reference)

    def test_get_all_nodes__4(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_0, node_3)
        result = set(simple_tree.GetAllNodes())
        reference = {node_0, node_1, node_2, node_3}
        self.assertEqual(result, reference)

    def test_get_all_nodes__5(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(5, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_2, node_5)
        result = set(simple_tree.GetAllNodes())
        reference = {node_0, node_1, node_2, node_3, node_4, node_5}
        self.assertEqual(result, reference)

    def test_get_all_nodes__6(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(5, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_4, node_5)
        result = set(simple_tree.GetAllNodes())
        reference = {node_0, node_1, node_2, node_3, node_4, node_5}
        self.assertEqual(result, reference)

    def test_get_all_nodes__7(self):
        node_0 = SimpleTreeNode(0, None)

        simple_tree = SimpleTree(node_0)
        result = set(simple_tree.GetAllNodes())
        reference = {node_0}
        self.assertEqual(result, reference)

    def test_get_all_nodes__8(self):
        simple_tree = SimpleTree(None)
        result = simple_tree.GetAllNodes()
        reference = []
        self.assertEqual(result, reference)

    # ### TEST FIND_NODES_BY_VALUE() METHOD
    def test_find_nodes_by_value__1(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(5, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_4, node_5)
        result = set(simple_tree.FindNodesByValue(3))
        reference = {node_3}
        self.assertEqual(result, reference)

    def test_find_nodes_by_value__2(self):
        node_0 = SimpleTreeNode(3, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(3, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_4, node_5)
        result = set(simple_tree.FindNodesByValue(3))
        reference = {node_0, node_3, node_5}
        self.assertEqual(result, reference)

    def test_find_nodes_by_value__3(self):
        node_0 = SimpleTreeNode(3, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(3, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_4, node_5)
        result = simple_tree.FindNodesByValue(99)
        self.assertEqual(result, [])

    def test_find_nodes_by_value__4(self):
        node_0 = SimpleTreeNode(3, None)

        simple_tree = SimpleTree(node_0)
        result = simple_tree.FindNodesByValue(99)
        self.assertEqual(result, [])

    def test_find_nodes_by_value__5(self):
        simple_tree = SimpleTree(None)
        result = simple_tree.FindNodesByValue(99)
        self.assertEqual(result, [])

    # ### TEST MOVE_NODE() METHOD
    def test_move_node__1(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(5, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_4, node_5)
        simple_tree.MoveNode(node_2, node_3)
        result = node_3.Children
        self.assertEqual(result, [node_2])

    def test_move_node__2(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(5, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_4, node_5)

        simple_tree.MoveNode(node_2, node_3)
        result = node_2.Children
        self.assertEqual(result, [node_4])

    def test_move_node__3(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(5, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_4, node_5)

        simple_tree.MoveNode(node_2, node_3)
        result = node_0.Children
        self.assertEqual(result, [node_1])

    def test_move_node__4(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(5, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_4, node_5)

        simple_tree.MoveNode(node_2, node_3)
        result = node_2.Parent
        self.assertEqual(result, node_3)

    # ### TEST COUNT() METHOD
    def test_count__1(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        result = simple_tree.Count()
        self.assertEqual(result, 2)

    def test_count__2(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        result = simple_tree.Count()
        self.assertEqual(result, 3)

    def test_count__3(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_0, node_3)

        result = simple_tree.Count()
        self.assertEqual(result, 4)

    def test_count__4(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(5, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_4, node_5)

        result = simple_tree.Count()
        self.assertEqual(result, 6)
    
    def test_count__5(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(5, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_2, node_5)

        result = simple_tree.Count()
        self.assertEqual(result, 6)

    def test_count__6(self):
        node_0 = SimpleTreeNode(0, None)
        simple_tree = SimpleTree(node_0)
        result = simple_tree.Count()
        self.assertEqual(result, 1)

    def test_count__7(self):
        simple_tree = SimpleTree(None)
        result = simple_tree.Count()
        self.assertEqual(result, 0)

    # ### TEST LEAF_COUNT() METHOD
    def test_leaf_count__1(self):
        simple_tree = SimpleTree(None)
        result = simple_tree.LeafCount()
        self.assertEqual(result, 0)

    def test_leaf_count__2(self):
        node_0 = SimpleTreeNode(0, None)
        simple_tree = SimpleTree(node_0)
        result = simple_tree.LeafCount()
        self.assertEqual(result, 1)

    def test_leaf_count__3(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(5, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_2, node_5)

        result = simple_tree.LeafCount()
        self.assertEqual(result, 3)

    def test_leaf_count__4(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(5, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_4, node_5)

        result = simple_tree.LeafCount()
        self.assertEqual(result, 2)

    def test_leaf_count__5(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_0, node_3)

        result = simple_tree.LeafCount()
        self.assertEqual(result, 3)
    
    
    def test_leaf_count__6(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_1, node_2)
        simple_tree.AddChild(node_0, node_3)

        result = simple_tree.LeafCount()
        self.assertEqual(result, 2)

    def test_leaf_count__7(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_1, node_2)

        result = simple_tree.LeafCount()
        self.assertEqual(result, 1)

    # ### TEST NODES_WITH_LEVELS() METHOD
    def test_nodes_with_levels__1(self):
        simple_tree = SimpleTree(None)
        result = simple_tree.nodes_with_levels()
        reference = []
        self.assertEqual(result, reference)

    def test_nodes_with_levels__2(self):
        node_0 = SimpleTreeNode(0, None)
        simple_tree = SimpleTree(node_0)
        result = set(simple_tree.nodes_with_levels())
        reference = {(node_0, 0)}
        self.assertEqual(result, reference)

    def test_nodes_with_levels__3(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_1, node_2)

        result = set(simple_tree.nodes_with_levels())
        reference = {(node_0, 0), (node_1, 1), (node_2, 2)}
        self.assertEqual(result, reference)

    def test_nodes_with_levels__4(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_1, node_2)
        simple_tree.AddChild(node_0, node_3)

        result = set(simple_tree.nodes_with_levels())
        reference = {(node_0, 0), (node_1, 1), (node_2, 2), (node_3, 1)}
        self.assertEqual(result, reference)

    def test_nodes_with_levels__5(self):
        node_0 = SimpleTreeNode(0, None)
        node_1 = SimpleTreeNode(1, None)
        node_2 = SimpleTreeNode(2, None)
        node_3 = SimpleTreeNode(3, None)
        node_4 = SimpleTreeNode(4, None)
        node_5 = SimpleTreeNode(5, None)

        simple_tree = SimpleTree(node_0)
        simple_tree.AddChild(node_0, node_1)
        simple_tree.AddChild(node_0, node_2)
        simple_tree.AddChild(node_1, node_3)
        simple_tree.AddChild(node_2, node_4)
        simple_tree.AddChild(node_4, node_5)

        result = set(simple_tree.nodes_with_levels())
        reference = {(node_0, 0), (node_1, 1), (node_2, 1), (node_3, 2), (node_4, 2), (node_5, 3)}
        self.assertEqual(result, reference)


if __name__ == '__main__':
    unittest.main()
