# for duture me, I ran these tests with ```python3 -m unittest course_1_1/tests/test_Task_1.py```
import unittest
from ..Task_1_addon1 import *


def create_mock_llist(mock_llist_nodes: list):
    mock_llist = LinkedList()
    for mock_node in mock_llist_nodes:
        mock_llist.add_in_tail(mock_node)
    return mock_llist


def get_list_of_nodes_values(mock_llist: LinkedList):
    llist_values = []
    node = mock_llist.head
    while node is not None:
        llist_values.append(node.value)
        node = node.next

    return llist_values


class TestTask1(unittest.TestCase):
    # ### TEST DELETE() METHOD WITH FLAG all=False
    def test_delete_single_node__1(self):
        mock_llist = create_mock_llist([Node(2), Node(3), Node(4)])
        mock_llist.delete(2, all=False)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        # Here we assert that all nodes in our list are correct and the tail of Linkedlist object is correct too
        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([3, 4], (4, None)))

    def test_delete_single_node__2(self):
        mock_llist = create_mock_llist([Node(2)])
        mock_llist.delete(2, all=False)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        # Here we assert that values are empty, head and tail is None
        self.assertEqual((processed_llist_values, (mock_llist.head, mock_llist.tail)), ([], (None, None)))

    def test_delete_single_node__3(self):
        mock_llist = create_mock_llist([])
        mock_llist.delete(2, all=False)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        # Here we assert that values are empty, head and tail is None
        self.assertEqual((processed_llist_values, (mock_llist.head, mock_llist.tail)), ([], (None, None)))

    def test_delete_single_node__4(self):
        mock_llist = create_mock_llist([Node(1), Node(2), Node(3), Node(4)])
        mock_llist.delete(2, all=False)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 3, 4], (4, None)))

    def test_delete_single_node__5(self):
        mock_llist = create_mock_llist([Node(1), Node(2), Node(2), Node(3)])
        mock_llist.delete(2, all=False)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 2, 3], (3, None)))

    def test_delete_single_node__6(self):
        mock_llist = create_mock_llist([Node(0), Node(1), Node(2)])
        mock_llist.delete(2, all=False)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([0, 1], (1, None)))

    def test_delete_single_node__7(self):
        mock_llist = create_mock_llist([Node(0), Node(1), Node(2), Node(2)])
        mock_llist.delete(2, all=False)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([0, 1, 2], (2, None)))

    def test_delete_single_node__8(self):
        mock_llist = create_mock_llist([Node(2), Node(0), Node(1), Node(1)])
        mock_llist.delete(2, all=False)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([0, 1, 1], (1, None)))

    def test_delete_single_node__9(self):
        mock_llist = create_mock_llist([Node(2), Node(2), Node(1), Node(0)])
        mock_llist.delete(2, all=False)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([2, 1, 0], (0, None)))

    def test_delete_single_node__10(self):
        mock_llist = create_mock_llist([Node(2), Node(1)])
        mock_llist.delete(2, all=False)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        # Here we assert that values are empty, head and tail is None
        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1], (1, None)))

    def test_delete_single_node__11(self):
        mock_llist = create_mock_llist([Node(2), Node(1), Node(2), Node(2), Node(3), Node(2), Node(4), Node(2)])
        mock_llist.delete(2, all=False)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 2, 2, 3, 2, 4, 2], (2, None)))

    # ### TEST DELETE() METHOD WITH FLAG all=True
    def test_delete_multiple_node__1(self):
        mock_llist = create_mock_llist([Node(2), Node(3), Node(4)])
        mock_llist.delete(2, all=True)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        # Here we assert that all nodes in our list are correct and the tail of Linkedlist object is correct too
        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([3, 4], (4, None)))

    def test_delete_multiple_node__2(self):
        mock_llist = create_mock_llist([Node(2), Node(2), Node(3), Node(4)])
        mock_llist.delete(2, all=True)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        # Here we assert that all nodes in our list are correct and the tail of Linkedlist object is correct too
        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([3, 4], (4, None)))

    def test_delete_multiple_node__3(self):
        mock_llist = create_mock_llist([Node(2)])
        mock_llist.delete(2, all=True)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        # Here we assert that values are empty, head and tail is None
        self.assertEqual((processed_llist_values, (mock_llist.head, mock_llist.tail)), ([], (None, None)))

    def test_delete_multiple_node__4(self):
        mock_llist = create_mock_llist([Node(2), Node(2), Node(2)])
        mock_llist.delete(2, all=True)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        # Here we assert that values are empty, head and tail is None
        self.assertEqual((processed_llist_values, (mock_llist.head, mock_llist.tail)), ([], (None, None)))

    def test_delete_multiple_node__5(self):
        mock_llist = create_mock_llist([Node(1), Node(2), Node(3), Node(4)])
        mock_llist.delete(2, all=True)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 3, 4], (4, None)))

    def test_delete_multiple_node__6(self):
        mock_llist = create_mock_llist([Node(1), Node(2), Node(2), Node(3), Node(4)])
        mock_llist.delete(2, all=True)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 3, 4], (4, None)))

    def test_delete_multiple_node__7(self):
        mock_llist = create_mock_llist([Node(1), Node(2), Node(2), Node(3), Node(2), Node(4)])
        mock_llist.delete(2, all=True)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 3, 4], (4, None)))


    def test_delete_multiple_node__8(self):
        mock_llist = create_mock_llist([Node(1), Node(2), Node(2), Node(3), Node(2), Node(4), Node(2)])
        mock_llist.delete(2, all=True)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 3, 4], (4, None)))

    def test_delete_multiple_node__9(self):
        mock_llist = create_mock_llist([Node(1), Node(2), Node(2), Node(3), Node(2), Node(4), Node(2), Node(2)])
        mock_llist.delete(2, all=True)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 3, 4], (4, None)))

    def test_delete_multiple_node__10(self):
        mock_llist = create_mock_llist([Node(2), Node(1), Node(2), Node(2), Node(3), Node(2), Node(4), Node(2), Node(2)])
        mock_llist.delete(2, all=True)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 3, 4], (4, None)))

    def test_delete_multiple_node__11(self):
        mock_llist = create_mock_llist([Node(2), Node(2), Node(3), Node(2), Node(2)])
        mock_llist.delete(2, all=True)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([3], (3, None)))

    # ### TEST CLEAN() METHOD
    def test_clean_llist__1(self):
        mock_llist = create_mock_llist([Node(2), Node(2), Node(3), Node(2), Node(2)])
        mock_llist.clean()
        self.assertEqual((mock_llist.head, mock_llist.tail), (None, None))

    def test_clean_llist__2(self):
        mock_llist = create_mock_llist([])
        mock_llist.clean()
        self.assertEqual((mock_llist.head, mock_llist.tail), (None, None))

    def test_clean_llist__3(self):
        mock_llist = create_mock_llist([Node(1)])
        mock_llist.clean()
        self.assertEqual((mock_llist.head, mock_llist.tail), (None, None))

    # ### TEST FIND_ALL() METHOD
    def test_findall__1(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        mock_llist = create_mock_llist([node_1, node_2, node_3])
        filterd_nodes_list = mock_llist.find_all(2)
        self.assertEqual(filterd_nodes_list, [node_2])

    def test_findall__2(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(2)
        mock_llist = create_mock_llist([node_1, node_2, node_3])
        filterd_nodes_list = mock_llist.find_all(2)
        self.assertEqual(filterd_nodes_list, [node_2, node_3])

    def test_findall__3(self):
        node_1 = Node(2)
        node_2 = Node(2)
        node_3 = Node(2)
        mock_llist = create_mock_llist([node_1, node_2, node_3])
        filterd_nodes_list = mock_llist.find_all(2)
        self.assertEqual(filterd_nodes_list, [node_1, node_2, node_3])

    def test_findall__4(self):
        node_1 = Node(2)
        node_2 = Node(2)
        node_3 = Node(1)
        mock_llist = create_mock_llist([node_1, node_2, node_3])
        filterd_nodes_list = mock_llist.find_all(2)
        self.assertEqual(filterd_nodes_list, [node_1, node_2])

    def test_findall__5(self):
        mock_llist = create_mock_llist([])
        filterd_nodes_list = mock_llist.find_all(2)
        self.assertEqual(filterd_nodes_list, [])

    def test_findall__6(self):
        node_1 = Node(1)
        node_2 = Node(3)
        node_3 = Node(4)
        mock_llist = create_mock_llist([node_1, node_2, node_3])
        filterd_nodes_list = mock_llist.find_all(2)
        self.assertEqual(filterd_nodes_list, [])

    # ### TEST LEN() METHOD
    def test_len__1(self):
        mock_llist = create_mock_llist([Node(2), Node(2), Node(3)])
        mock_llist_len = mock_llist.len()
        self.assertEqual(mock_llist_len, 3)

    def test_len__2(self):
        mock_llist = create_mock_llist([Node(2)])
        mock_llist_len = mock_llist.len()
        self.assertEqual(mock_llist_len, 1)

    def test_len__3(self):
        mock_llist = create_mock_llist([])
        mock_llist_len = mock_llist.len()
        self.assertEqual(mock_llist_len, 0)

    def test_len__4(self):
        mock_llist = create_mock_llist([Node(2), Node(3), Node(3), Node(3)])
        mock_llist_len = mock_llist.len()
        self.assertEqual(mock_llist_len, 4)

    # ### TEST INSERT() METHOD
    def test_insert__1(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(55)
        mock_llist = create_mock_llist([node_1, node_2, node_3])
        mock_llist.insert(node_1, node_4)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 55, 2, 3], (3, None)))

    def test_insert__2(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(55)
        mock_llist = create_mock_llist([node_1, node_2, node_3])
        mock_llist.insert(node_3, node_4)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 2, 3, 55], (55, None)))

    def test_insert__3(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(3)
        node_new = Node(55)
        mock_llist = create_mock_llist([node_1, node_2, node_3, node_4])
        mock_llist.insert(node_3, node_new)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 2, 3, 55, 3], (3, None)))

    def test_insert__4(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(3)
        node_new = Node(55)
        mock_llist = create_mock_llist([node_1, node_2, node_3, node_4])
        mock_llist.insert(node_4, node_new)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([1, 2, 3, 3, 55], (55, None)))

    def test_insert__5(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(3)
        node_new = Node(55)
        mock_llist = create_mock_llist([node_1, node_2, node_3, node_4])
        mock_llist.insert(None, node_new)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([55, 1, 2, 3, 3], (3, None)))

    def test_insert__6(self):
        node_new = Node(55)
        mock_llist = create_mock_llist([])
        mock_llist.insert(None, node_new)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([55], (55, None)))

    def test_insert__7(self):
        node_1 = Node(1)
        node_new = Node(55)
        mock_llist = create_mock_llist([node_1])
        mock_llist.insert(None, node_new)
        processed_llist_values = get_list_of_nodes_values(mock_llist)

        self.assertEqual((processed_llist_values, (mock_llist.tail.value, mock_llist.tail.next)), ([55, 1], (1, None)))


if __name__ == '__main__':
    unittest.main()
