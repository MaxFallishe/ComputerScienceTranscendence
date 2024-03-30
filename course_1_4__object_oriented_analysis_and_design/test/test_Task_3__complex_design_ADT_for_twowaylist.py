import unittest
from course_1_4__object_oriented_analysis_and_design.Task_3__complex_design_ADT_for_twowaylist import ParentList, LinkedList, TwoWayList


class TestLinkedListAddToEmptyOperation(unittest.TestCase):
    def test_addtoempty_operation__1(self):
        llist = LinkedList()
        llist.add_to_empty()
        result = llist.get()
        self.assertEqual(result, None)

    def test_addtoempty_operation__2(self):
        llist = LinkedList()
        llist.add_to_empty()
        result = llist.get_addtoempty_status()
        self.assertEqual(result, 1)

    def test_addtoempty_operation__3(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.add_to_empty()
        result = llist.get_addtoempty_status()
        self.assertEqual(result, 2)

    def test_addtoempty_operation__4(self):
        llist = LinkedList()
        result = llist.get_addtoempty_status()
        self.assertEqual(result, 0)


class TestLinkedListHeadOperation(unittest.TestCase):
    def test_head_operation__1(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.head()
        result = llist.get()
        self.assertEqual(result, None)

    def test_head_operation__2(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(1)
        llist.head()
        result = llist.get()
        self.assertEqual(result, 1)

    def test_head_operation__3(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(1)
        llist.put_left(2)
        llist.put_left(3)
        llist.head()
        result = llist.get()
        self.assertEqual(result, 1)

    def test_head_operation__4(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(1)
        llist.put_left(2)
        llist.put_left(3)
        llist.put_right(-1)
        llist.put_right(-2)
        llist.put_right(-3)
        llist.head()
        result = llist.get()
        self.assertEqual(result, 1)


class TestLinkedListTailOperation(unittest.TestCase):
    def test_tail_operation__1(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.tail()
        result = llist.get()
        self.assertEqual(result, None)

    def test_tail_operation__2(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(1)
        llist.tail()
        result = llist.get()
        self.assertEqual(result, None)

    def test_tail_operation__3(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(1)
        llist.put_left(2)
        llist.put_left(3)
        llist.tail()
        result = llist.get()
        self.assertEqual(result, None)

    def test_tail_operation__4(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(1)
        llist.put_left(2)
        llist.put_left(3)
        llist.put_right(-1)
        llist.put_right(-2)
        llist.put_right(-3)
        llist.tail()
        result = llist.get()
        self.assertEqual(result, -1)


class TestLinkedListRightOperation(unittest.TestCase):
    def test_right_operation__1(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.right()
        result = llist.get()
        self.assertEqual(result, None)

    def test_right_operation__2(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(1)
        llist.right()
        result = llist.get()
        self.assertEqual(result, 1)

    def test_right_operation__3(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(1)
        llist.put_right(2)
        llist.put_right(3)
        llist.right()
        result = llist.get()
        self.assertEqual(result, 3)

    def test_right_operation__4(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(1)
        llist.put_right(2)
        llist.put_right(3)
        llist.right()
        llist.right()
        llist.right()
        result = llist.get()
        self.assertEqual(result, 1)


class TestLinkedListGetOperation(unittest.TestCase):
    def test_right_operation__1(self):
        llist = LinkedList()
        llist.add_to_empty()
        result = llist.get()
        self.assertEqual(result, None)

    def test_get_operation__2(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(2)
        llist.right()
        result = llist.get()
        self.assertEqual(result, 2)

    def test_get_operation__3(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right("FOR")
        llist.right()
        result = llist.get()
        self.assertEqual(result, "FOR")

    def test_get_operation__4(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right({1, 2, 3, 4})
        llist.right()
        result = llist.get()
        self.assertEqual(result, {1, 2, 3, 4})


class TestLinkedListPutRightOperation(unittest.TestCase):
    def test_putright_operation__1(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(2)
        llist.put_right(3)
        llist.right()
        llist.right()
        result = llist.get()
        self.assertEqual(result, 2)

    def test_putright_operation__2(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(2)
        llist.put_right(3)
        llist.right()
        result = llist.get()
        self.assertEqual(result, 3)

    def test_putright_operation__3(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(2)
        llist.put_right(3)
        llist.right()
        llist.head()
        llist.right()
        result = llist.get()
        self.assertEqual(result, 3)


    def test_putright_operation__4(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(2)
        llist.put_right(3)
        llist.right()
        llist.right()
        llist.right()
        result = llist.get()
        self.assertEqual(result, 2)


class TestLinkedListPutLeftOperation(unittest.TestCase):
    def test_putleft_operation__1(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(2)
        llist.put_left(3)
        result = llist.get()
        self.assertEqual(result, None)

    def test_putleft_operation__2(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(2)
        llist.put_left(3)
        llist.head()
        result = llist.get()
        self.assertEqual(result, 2)

    def test_putleft_operation__3(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(2)
        llist.put_left(3)
        llist.put_left(-2)
        llist.head()
        llist.right()
        result = llist.get()
        self.assertEqual(result, 3)


class TestLinkedListRemoveOperation(unittest.TestCase):
    def test_remove_operation__1(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.replace('jell')
        llist.remove()
        result = llist.get()
        self.assertEqual(result, None)

    def test_remove_operation__2(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(2)
        llist.put_right(3)
        llist.right()
        llist.remove()
        result = llist.get()
        self.assertEqual(result, 2)

    def test_remove_operation__3(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(2)
        llist.put_left(3)
        llist.remove()
        result = llist.get()
        self.assertEqual(result, 3)

    def test_remove_operation__4(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(2)
        llist.put_right(3)
        llist.remove()
        result = llist.get()
        self.assertEqual(result, 3)


class TestLinkedListSizeOperation(unittest.TestCase):
    def test_size_operation__1(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(2)
        llist.put_right(3)
        result = llist.size()
        self.assertEqual(result, 3)

    def test_size_operation__2(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_left(2)
        llist.put_left(2)
        llist.put_right(3)
        llist.put_right(3)
        llist.put_right(3)
        result = llist.size()
        self.assertEqual(result, 6)

    def test_size_operation__3(self):
        llist = LinkedList()
        llist.add_to_empty()
        result = llist.size()
        self.assertEqual(result, 1)

    def test_size_operation__4(self):
        llist = LinkedList()
        result = llist.size()
        self.assertEqual(result, 0)


class TestLinkedListFindOperation(unittest.TestCase):
    def test_find_operation__1(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.find(5)
        result = llist.get()
        self.assertEqual(result, None)

    def test_find_operation__2(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(1)
        llist.put_right(2)
        llist.put_right(3)
        llist.put_right(4)
        llist.put_right(5)
        llist.find(5)
        result = llist.get()
        self.assertEqual(result, 5)

    def test_find_operation__3(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(1)
        llist.put_right(2)
        llist.put_right(3)
        llist.put_right(4)
        llist.put_right(5)
        llist.find(1)
        result = llist.is_tail()
        self.assertEqual(result, True)

    def test_find_operation__4(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(1)
        llist.put_right(2)
        llist.put_right(3)
        llist.put_right(4)
        llist.put_left(2)
        llist.put_left(44)
        llist.put_right(5)
        llist.find(4)
        result = llist.get()
        self.assertEqual(result, 4)

    def test_find_operation__5(self):
        llist = LinkedList()
        llist.add_to_empty()
        llist.put_right(1)
        llist.put_right(2)
        llist.put_right(3)
        llist.put_right(44)
        llist.put_left(2)
        llist.put_left(44)
        llist.put_right(5)
        llist.head()
        llist.find(44)
        llist.find(44)
        result = llist.get()
        self.assertEqual(result, 44)
