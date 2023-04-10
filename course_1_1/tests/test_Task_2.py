# for duture me, I ran these tests with ```python3 -m unittest course_1_1/tests/test_Task_2.py```
import unittest
import logging
from ..Task_2 import *


def create_mock_llist2(mock_llist_nodes: list):
    mock_llist = LinkedList2()
    for mock_node in mock_llist_nodes:
        mock_llist.add_in_tail(mock_node)
    return mock_llist


def get_list_of_nodes(mock_llist: LinkedList2):
    llist_values = []
    node = mock_llist.head
    while node is not None:
        llist_values.append(node)
        # llist_values.append(id(node))  # for debug
        node = node.next

    return llist_values


def get_reversed_list_of_nodes(mock_llist: LinkedList2):
    llist_values = []
    node = mock_llist.tail
    while node is not None:
        llist_values.append(node)
        # llist_values.append(id(node))  # for debug
        node = node.prev

    return llist_values


class TestTask2(unittest.TestCase):
    # ### TEST FIND() METHOD
    def test_find_single_node__1(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find(3)
        self.assertEqual(finded_node, node_3)

    def test_find_single_node__2(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find(1)
        self.assertEqual(finded_node, node_1)

    def test_find_single_node__3(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find(4)
        self.assertEqual(finded_node, node_4)

    def test_find_single_node__4(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find(5)
        self.assertEqual(finded_node, None)

    def test_find_single_node__5(self):
        node_1 = Node(1)
        node_2 = Node(1)
        node_3 = Node(1)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find(1)
        self.assertEqual(finded_node, node_1)

    def test_find_single_node__6(self):
        node_1 = Node(1)
        node_2 = Node(1)
        node_3 = Node(1)
        node_4 = Node(1)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find(1)
        self.assertEqual(finded_node, node_1)

    def test_find_single_node__7(self):
        node_1 = Node(1)
        mock_llist = create_mock_llist2([node_1])
        finded_node = mock_llist.find(1)
        self.assertEqual(finded_node, node_1)

    def test_find_single_node__8(self):
        mock_llist = create_mock_llist2([])
        finded_node = mock_llist.find(1)
        self.assertEqual(finded_node, None)

    # ### TEST FINDALL() METHOD
    def test_findall_nodes__1(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_nodes = mock_llist.find_all(3)
        self.assertEqual(finded_nodes, [node_3])

    def test_findall_nodes__2(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find_all(1)
        self.assertEqual(finded_node, [node_1])

    def test_findall_nodes__3(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find_all(4)
        self.assertEqual(finded_node, [node_4])

    def test_findall_nodes__4(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find_all(5)
        self.assertEqual(finded_node, [])

    def test_findall_nodes__5(self):
        node_1 = Node(1)
        node_2 = Node(1)
        node_3 = Node(1)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find_all(1)
        self.assertEqual(finded_node, [node_1, node_2, node_3])

    def test_findall_nodes__6(self):
        node_1 = Node(1)
        node_2 = Node(1)
        node_3 = Node(1)
        node_4 = Node(1)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find_all(1)
        self.assertEqual(finded_node, [node_1, node_2, node_3, node_4])


    def test_findall_nodes__7(self):
        node_1 = Node(1)
        mock_llist = create_mock_llist2([node_1])
        finded_node = mock_llist.find_all(1)
        self.assertEqual(finded_node, [node_1])

    def test_findall_nodes__8(self):
        mock_llist = create_mock_llist2([])
        finded_node = mock_llist.find_all(1)
        self.assertEqual(finded_node, [])

    def test_findall_nodes__9(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(2)
        node_4 = Node(1)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find_all(1)
        self.assertEqual(finded_node, [node_1, node_4])

    def test_findall_nodes__10(self):
        node_1 = Node(2)
        node_2 = Node(1)
        node_3 = Node(2)
        node_4 = Node(1)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find_all(1)
        self.assertEqual(finded_node, [node_2, node_4])

    def test_findall_nodes__11(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(1)
        node_4 = Node(2)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])
        finded_node = mock_llist.find_all(1)
        self.assertEqual(finded_node, [node_1, node_3])

    # ### TEST DELETE() METHOD WITH FLAG all=False
    def test_delete_single_node__1(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(3)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])  # input field
        mock_llist.delete(3, all=False)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_2, node_4]   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (3, None)) # input field
        self.assertEqual(result, ref)

    def test_delete_single_node__2(self):
        node_1 = Node(1)
        node_2 = Node(1)
        node_3 = Node(2)
        node_4 = Node(3)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])  # input field
        mock_llist.delete(1, all=False)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_2, node_3, node_4]   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (3, None)) # input field
        self.assertEqual(result, ref)

    def test_delete_single_node__3(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(2)
        node_4 = Node(3)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])  # input field
        mock_llist.delete(2, all=False)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_3, node_4]   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (3, None))  # input field
        self.assertEqual(result, ref)

    def test_delete_single_node__4(self):
        node_1 = Node(1)
        node_2 = Node(4)
        node_3 = Node(2)
        node_4 = Node(2)
        node_5 = Node(7)
        node_6 = Node(3)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4, node_5, node_6])  # input field
        mock_llist.delete(2, all=False)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_2, node_4, node_5, node_6]   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (3, None)) # input field
        self.assertEqual(result, ref)

    def test_delete_single_node__5(self):
        node_1 = Node(2)
        node_2 = Node(2)
        node_3 = Node(2)
        node_4 = Node(2)
        node_5 = Node(2)
        node_6 = Node(2)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4, node_5, node_6])  # input field
        mock_llist.delete(2, all=False)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_2, node_3, node_4, node_5, node_6]   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (2, None), (2, None))
        self.assertEqual(result, ref)

    def test_delete_single_node__6(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(2)
        node_4 = Node(2)
        node_5 = Node(2)
        node_6 = Node(2)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4, node_5, node_6])  # input field
        mock_llist.delete(2, all=False)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_3, node_4, node_5, node_6]   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (2, None))  # input field
        self.assertEqual(result, ref)

    def test_delete_single_node__7(self):
        node_1 = Node(2)
        mock_llist = create_mock_llist2([node_1])  # input field
        mock_llist.delete(2, all=False)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = []   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = None
        ref_tail = None

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, None, None) # input field
        self.assertEqual(result, ref)

    def test_delete_single_node__8(self):
        mock_llist = create_mock_llist2([])  # input field
        mock_llist.delete(2, all=False)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = []   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = None
        ref_tail = None

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, None, None) # input field
        self.assertEqual(result, ref)

    # ### TEST DELETE() METHOD WITH FLAG all=True
    def test_delete_multiple_node__1(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(3)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])  # input field
        mock_llist.delete(3, all=True)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_2]   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (2, None))  # input field
        self.assertEqual(result, ref)

    def test_delete_multiple_node__2(self):
        node_1 = Node(1)
        node_2 = Node(1)
        node_3 = Node(2)
        node_4 = Node(3)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])  # input field
        mock_llist.delete(1, all=True)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_3, node_4]   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (2, None), (3, None))  # input field
        self.assertEqual(result, ref)

    def test_delete_multiple_node__3(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(2)
        node_4 = Node(3)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4])  # input field
        mock_llist.delete(2, all=True)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_4]   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (3, None))  # input field
        self.assertEqual(result, ref)

    def test_delete_multiple_node__4(self):
        node_1 = Node(1)
        node_2 = Node(4)
        node_3 = Node(2)
        node_4 = Node(2)
        node_5 = Node(7)
        node_6 = Node(3)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4, node_5, node_6])  # input field
        mock_llist.delete(2, all=True)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_2, node_5, node_6]   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (3, None))  # input field
        self.assertEqual(result, ref)

    def test_delete_multiple_node__5(self):
        node_1 = Node(2)
        node_2 = Node(2)
        node_3 = Node(2)
        node_4 = Node(2)
        node_5 = Node(2)
        node_6 = Node(2)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4, node_5, node_6])  # input field
        mock_llist.delete(2, all=True)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = []   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = None
        ref_tail = None

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, None, None)
        self.assertEqual(result, ref)

    def test_delete_multiple_node__6(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(2)
        node_4 = Node(2)
        node_5 = Node(2)
        node_6 = Node(2)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4, node_5, node_6])  # input field
        mock_llist.delete(2, all=True)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1]   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)
        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (1, None))  # input field
        self.assertEqual(result, ref)

    def test_delete_multiple_node__7(self):
        node_1 = Node(2)
        mock_llist = create_mock_llist2([node_1])  # input field
        mock_llist.delete(2, all=True)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = []   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = None
        ref_tail = None

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, None, None)  # input field
        self.assertEqual(result, ref)

    def test_delete_multiple_node__8(self):
        mock_llist = create_mock_llist2([])  # input field
        mock_llist.delete(2, all=True)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = []   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = None
        ref_tail = None

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, None, None)  # input field
        self.assertEqual(result, ref)

    def test_delete_multiple_node__9(self):
        node_1 = Node(2)
        node_2 = Node(1)
        node_3 = Node(2)
        node_4 = Node(5)
        node_5 = Node(2)
        node_6 = Node(2)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4, node_5, node_6])  # input field
        mock_llist.delete(2, all=True)   # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_2, node_4]   # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (5, None))  # input field
        self.assertEqual(result, ref)

    def test_delete_multiple_node__10(self):
        node_1 = Node(2)
        node_2 = Node(2)
        node_3 = Node(5)
        node_4 = Node(6)
        node_5 = Node(2)
        node_6 = Node(2)
        mock_llist = create_mock_llist2([node_1, node_2, node_3, node_4, node_5, node_6])  # input field
        mock_llist.delete(2, all=True)  # input field
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_3, node_4]  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (5, None), (6, None))  # input field
        self.assertEqual(result, ref)

    # ### TEST INSERT() METHOD
    def test_insert_node__1(self):
        node_1 = Node(2)
        mock_llist = create_mock_llist2([])  # input field
        mock_llist.insert(None, node_1)
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1]  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (2, None), (2, None))  # input field
        self.assertEqual(result, ref)

    def test_insert_node__2(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        mock_llist = create_mock_llist2([node_1, node_2])  # input field
        mock_llist.insert(None, node_3)
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_2, node_3]  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)
        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (3, None))  # input field
        self.assertEqual(result, ref)

    def test_insert_node__3(self):
        node_1 = Node(1)
        node_2 = Node(2)
        mock_llist = create_mock_llist2([node_1])  # input field
        mock_llist.insert(None, node_2)
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_2]  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)
        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (2, None))  # input field
        self.assertEqual(result, ref)

    def test_insert_node__4(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_2, node_4])  # input field
        mock_llist.insert(node_2, node_3)
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_2, node_3, node_4]  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (4, None))  # input field
        self.assertEqual(result, ref)

    def test_insert_node__5(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_2, node_3])  # input field
        mock_llist.insert(node_3, node_4)
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_2, node_3, node_4]  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (4, None))  # input field
        self.assertEqual(result, ref)

    def test_insert_node__6(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_1, node_3, node_4])  # input field
        mock_llist.insert(node_1, node_2)
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_2, node_3, node_4]  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (4, None))  # input field
        self.assertEqual(result, ref)

    # ### TEST ADD_IN_HEAD() METHOD
    def test_addinhead_node__1(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        node_4 = Node(4)
        mock_llist = create_mock_llist2([node_2, node_3, node_4])  # input field
        mock_llist.add_in_head(node_1)
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_2, node_3, node_4]  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (4, None))  # input field
        self.assertEqual(result, ref)

    def test_addinhead_node__2(self):
        node_1 = Node(1)
        mock_llist = create_mock_llist2([])  # input field
        mock_llist.add_in_head(node_1)
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1]  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (1, None))  # input field
        self.assertEqual(result, ref)

    def test_addinhead_node__3(self):
        node_1 = Node(1)
        node_2 = Node(2)
        mock_llist = create_mock_llist2([node_2])  # input field
        mock_llist.add_in_head(node_1)
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = [node_1, node_2]  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = (mock_llist.head.value, mock_llist.head.prev)
        ref_tail = (mock_llist.tail.value, mock_llist.tail.next)

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, (1, None), (2, None))  # input field
        self.assertEqual(result, ref)

    # ### TEST CLEAN() METHOD
    def test_clean_node__1(self):
        node_1 = Node(1)
        node_2 = Node(2)
        mock_llist = create_mock_llist2([node_1, node_2])  # input field
        mock_llist.clean()
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = []  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = None
        ref_tail = None

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, None, None)  # input field
        self.assertEqual(result, ref)

    def test_clean_node__2(self):
        node_1 = Node(1)
        mock_llist = create_mock_llist2([node_1])  # input field
        mock_llist.clean()
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = []  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = None
        ref_tail = None

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, None, None)  # input field
        self.assertEqual(result, ref)

    def test_clean_node__3(self):
        mock_llist = create_mock_llist2([])  # input field
        mock_llist.clean()
        processed_llist_values = get_list_of_nodes(mock_llist)
        processed_rev_llist_values = get_reversed_list_of_nodes(mock_llist)
        ref_llist_values = []  # input field
        ref_rev_llist_values = ref_llist_values[::-1]
        ref_head = None
        ref_tail = None

        ref = (ref_llist_values, ref_rev_llist_values, ref_head, ref_tail)
        result = (processed_llist_values, processed_rev_llist_values, None, None)  # input field
        self.assertEqual(result, ref)

    # ### TEST LEN() METHOD
    def test_len_llist__1(self):
        node_1 = Node(1)
        node_2 = Node(2)
        mock_llist = create_mock_llist2([node_1, node_2])  # input field
        llist_len = mock_llist.len()

        self.assertEqual(llist_len, 2)

    def test_len_llist__2(self):
        node_1 = Node(1)
        mock_llist = create_mock_llist2([node_1])  # input field
        llist_len = mock_llist.len()

        self.assertEqual(llist_len, 1)

    def test_len_llist__3(self):
        mock_llist = create_mock_llist2([])  # input field
        llist_len = mock_llist.len()

        self.assertEqual(llist_len, 0)
