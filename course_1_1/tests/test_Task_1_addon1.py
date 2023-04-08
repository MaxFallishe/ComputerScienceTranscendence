# for future me, i ran it with ```python3 -m unittest course_1_1.tests.test_Task_1_addon1```
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


class TestTask1Addon1(unittest.TestCase):
    # ### TEST DELETE() METHOD WITH FLAG all=False
    def test_sum_two_llists__1(self):
        mock_llist_1 = create_mock_llist([Node(1), Node(2), Node(3)])
        mock_llist_2 = create_mock_llist([Node(10), Node(20), Node(30)])
        result_llist = sum_llist(mock_llist_1, mock_llist_2)
        processed_result_llist_values = get_list_of_nodes_values(result_llist)

        self.assertEqual((processed_result_llist_values, (result_llist.tail.value, result_llist.tail.next)), ([11, 22, 33], (33, None)))

    def test_sum_two_llists__2(self):
        mock_llist_1 = create_mock_llist([Node(1)])
        mock_llist_2 = create_mock_llist([Node(10)])
        result_llist = sum_llist(mock_llist_1, mock_llist_2)
        processed_result_llist_values = get_list_of_nodes_values(result_llist)

        self.assertEqual((processed_result_llist_values, (result_llist.tail.value, result_llist.tail.next)), ([11], (11, None)))

    def test_sum_two_llists__3(self):
        mock_llist_1 = create_mock_llist([])
        mock_llist_2 = create_mock_llist([])
        result_llist = sum_llist(mock_llist_1, mock_llist_2)
        processed_result_llist_values = get_list_of_nodes_values(result_llist)

        self.assertEqual((processed_result_llist_values, (result_llist.head, result_llist.tail)), ([], (None, None)))

    def test_sum_two_llists__4(self):
        mock_llist_1 = create_mock_llist([Node(1), Node(2)])
        mock_llist_2 = create_mock_llist([Node(1)])
        result_llist = sum_llist(mock_llist_1, mock_llist_2)

        self.assertEqual(result_llist, None)

    def test_sum_two_llists__5(self):
        mock_llist_1 = create_mock_llist([Node(1), Node(2)])
        mock_llist_2 = create_mock_llist([])
        result_llist = sum_llist(mock_llist_1, mock_llist_2)

        self.assertEqual(result_llist, None)
