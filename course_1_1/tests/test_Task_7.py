from ..Task_7 import *
import unittest


def get_list_of_nodes_values(mock_llist: OrderedList):
    llist_values = []
    node = mock_llist.head
    while node is not None:
        llist_values.append(node.value)
        node = node.next

    return llist_values


def get_reversed_list_of_nodes(mock_llist: OrderedList):
    llist_values = []
    node = mock_llist.tail
    while node is not None:
        llist_values.append(node.value)
        node = node.prev

    return llist_values


class TestTask7(unittest.TestCase):
    # TEST COMPARE() METHOD
    def test_compare__1(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        mock_ord_lst = OrderedList(asc=True)
        mock_ord_lst.head, mock_ord_lst.tail = node_1, node_3
        node_1.next, node_3.prev = node_2, node_2
        node_2.next, node_2.prev = node_3, node_1
        result = mock_ord_lst.compare(node_1, node_2)
        self.assertEqual(result, -1)

    def test_compare__2(self):
        node_1 = Node(1)
        node_2 = Node(2)
        node_3 = Node(3)
        mock_ord_lst = OrderedList(asc=True)
        mock_ord_lst.head, mock_ord_lst.tail = node_1, node_3
        node_1.next, node_3.prev = node_2, node_2
        node_2.next, node_2.prev = node_3, node_1
        result = mock_ord_lst.compare(node_2, node_1)
        self.assertEqual(result, 1)

    def test_compare__3(self):
        node_1 = Node(1)
        node_2 = Node(1)
        node_3 = Node(3)
        mock_ord_lst = OrderedList(asc=True)
        mock_ord_lst.head, mock_ord_lst.tail = node_1, node_3
        node_1.next, node_3.prev = node_2, node_2
        node_2.next, node_2.prev = node_3, node_1
        result = mock_ord_lst.compare(node_1, node_2)
        self.assertEqual(result, 0)

    def test_compare__4(self):
        node_1 = Node(3)
        node_2 = Node(2)
        node_3 = Node(3)
        mock_ord_lst = OrderedList(asc=True)
        mock_ord_lst.head, mock_ord_lst.tail = node_1, node_3
        node_1.next, node_3.prev = node_2, node_2
        node_2.next, node_2.prev = node_3, node_1
        result = mock_ord_lst.compare(node_1, node_3)
        self.assertEqual(result, 0)

    # TEST ADD() METHOD, asc=True
    def test_add__1(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [1]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__2(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [1, 2, 3]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__3(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(2)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [1, 2, 2]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__4(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [1, 1, 2]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__5(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(2)
        ord_lst.add(1)
        ord_lst.add(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [1, 1, 2]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__6(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.add(1)
        ord_lst.add(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [1, 1, 1]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__7(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(2)
        ord_lst.add(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [1, 2]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__8(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.add(2)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [1, 2]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    # TEST ADD() METHOD, asc=False
    def test_add__9(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [1]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__10(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [3, 2, 1]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__11(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(2)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [2, 2, 1]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__12(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [2, 1, 1]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__13(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(2)
        ord_lst.add(1)
        ord_lst.add(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [2, 1, 1]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__14(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        ord_lst.add(1)
        ord_lst.add(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [1, 1, 1]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__15(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(2)
        ord_lst.add(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [2, 1]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__16(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        ord_lst.add(2)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [2, 1]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_add__17(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(222)
        ord_lst.add(33)
        ord_lst.add(22)
        ord_lst.add(99)
        ord_lst.add(44)
        ord_lst.add(111)
        ord_lst.add(555)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [555, 222, 111, 99, 44, 33, 22]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    # TEST FIND() METHOD
    def test_find__1(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        self.assertEqual(ord_lst.find(2).value, 2)

    def test_find__2(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        self.assertEqual(ord_lst.find(1).value, 1)

    def test_find__3(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        self.assertEqual(ord_lst.find(3).value, 3)

    def test_find__4(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        self.assertEqual(ord_lst.find(99), None)

    def test_find__5(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(-1)
        ord_lst.add(-2)
        ord_lst.add(-3)
        self.assertEqual(ord_lst.find(-3).value, -3)

    def test_find__6(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(-1)
        ord_lst.add(-2)
        ord_lst.add(-3)
        self.assertEqual(ord_lst.find(-1).value, -1)

    def test_find__7(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(-1)
        ord_lst.add(-2)
        ord_lst.add(-3)
        self.assertEqual(ord_lst.find(-1).value, -1)

    def test_find__8(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(-1)
        ord_lst.add(-2)
        ord_lst.add(-3)
        self.assertEqual(ord_lst.find(-3).value, -3)

    # TEST LEN() METHOD
    def test_len__1(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        self.assertEqual(ord_lst.len(), 1)

    def test_len__2(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.add(1)
        ord_lst.add(1)
        self.assertEqual(ord_lst.len(), 3)

    def test_len__3(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        self.assertEqual(ord_lst.len(), 3)

    def test_len__4(self):
        ord_lst = OrderedList(asc=False)
        self.assertEqual(ord_lst.len(), 0)

    # TEST CLEAR() METHOD
    def test_clean__1(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        ord_lst.clean(asc=True)
        res = (ord_lst.head, ord_lst.tail, ord_lst._OrderedList__ascending)
        ref = (None, None, True)
        self.assertEqual(res, ref)

    def test_clean__2(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.clean(asc=True)
        res = (ord_lst.head, ord_lst.tail, ord_lst._OrderedList__ascending)
        ref = (None, None, True)
        self.assertEqual(res, ref)

    def test_clean__3(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.clean(asc=True)
        res = (ord_lst.head, ord_lst.tail, ord_lst._OrderedList__ascending)
        ref = (None, None, True)
        self.assertEqual(res, ref)

    def test_clean__4(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.clean(asc=False)
        res = (ord_lst.head, ord_lst.tail, ord_lst._OrderedList__ascending)
        ref = (None, None, False)
        self.assertEqual(res, ref)

    # TEST DELETE() METHOD
    def test_delete__1(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        ord_lst.delete(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [2, 3]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_delete__2(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        ord_lst.delete(2)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [1, 3]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_delete__3(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        ord_lst.delete(3)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [1, 2]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_delete__4(self):
        ord_lst = OrderedList(asc=True)
        ord_lst.add(1)
        ord_lst.delete(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = []
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_delete__5(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        ord_lst.delete(2)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [3, 1]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_delete__6(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        ord_lst.delete(1)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [3, 2]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)

    def test_delete__7(self):
        ord_lst = OrderedList(asc=False)
        ord_lst.add(1)
        ord_lst.add(2)
        ord_lst.add(3)
        ord_lst.add(99)
        ord_lst.add(4)
        ord_lst.delete(4)
        res_nodes_values = get_list_of_nodes_values(ord_lst)
        res_reversed_nodes_values = get_reversed_list_of_nodes(ord_lst)
        ref_nodes_values = [99, 3, 2, 1]
        ref_reversed_nodes_values = ref_nodes_values[::-1]
        res = (res_nodes_values, res_reversed_nodes_values)
        ref = (ref_nodes_values, ref_reversed_nodes_values)
        self.assertEqual(res, ref)
