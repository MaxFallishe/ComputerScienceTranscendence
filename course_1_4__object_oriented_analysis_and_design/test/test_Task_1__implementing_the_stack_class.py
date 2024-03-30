import unittest
from course_1_4__object_oriented_analysis_and_design.Task_1__implementing_the_stack_class import BoundedStack


class TestPushOperation(unittest.TestCase):
    def test_push_operation__1(self):
        bounded_stack = BoundedStack()
        bounded_stack.push(1)
        result = bounded_stack.pop()
        self.assertEqual(result, 1)

    def test_push_operation__2(self):
        bounded_stack = BoundedStack()
        bounded_stack.push('text')
        result = bounded_stack.pop()
        self.assertEqual(result, 'text')

    def test_push_operation__3(self):
        bounded_stack = BoundedStack()
        bounded_stack.push('text')
        bounded_stack.push(1)
        result = bounded_stack.pop()
        self.assertEqual(result, 1)

    def test_push_operation__4(self):
        bounded_stack = BoundedStack()
        for i in range(31+1):
            bounded_stack.push(i)
        bounded_stack.push(111)
        result = bounded_stack.pop()
        self.assertEqual(result, 31)

    def test_push_operation__5(self):
        bounded_stack = BoundedStack()
        for i in range(31+1):
            bounded_stack.push(i)
        bounded_stack.push(111)
        result = bounded_stack.pop()
        self.assertEqual(result, 31)


class TestPopOperation(unittest.TestCase):
    def test_pop_operation__1(self):
        bounded_stack = BoundedStack()
        result = bounded_stack.pop()
        self.assertEqual(result, None)

    def test_pop_operation__2(self):
        bounded_stack = BoundedStack()
        bounded_stack.push(2)
        result = bounded_stack.pop()
        self.assertEqual(result, 2)


class TestPeekOperation(unittest.TestCase):
    def test_peek_operation__1(self):
        bounded_stack = BoundedStack()
        result = bounded_stack.peek()
        self.assertEqual(result, None)

    def test_peek_operation__2(self):
        bounded_stack = BoundedStack()
        bounded_stack.push(1)
        result = bounded_stack.peek()
        self.assertEqual(result, 1)

    def test_peek_operation__3(self):
        bounded_stack = BoundedStack()
        bounded_stack.push(1)
        bounded_stack.peek()
        bounded_stack.peek()
        result = bounded_stack.peek()
        self.assertEqual(result, 1)


class TestClearOperation(unittest.TestCase):
    def test_clear_operation__1(self):
        bounded_stack = BoundedStack()
        bounded_stack.clear()
        result = bounded_stack.peek()
        self.assertEqual(result, None)

    def test_clear_operation__2(self):
        bounded_stack = BoundedStack()
        bounded_stack.push(1)
        bounded_stack.clear()
        result = bounded_stack.peek()
        self.assertEqual(result, None)

    def test_clear_operation__3(self):
        bounded_stack = BoundedStack()
        bounded_stack.push(1)
        bounded_stack.push('1')
        bounded_stack.push({1, 2, 3})
        bounded_stack.clear()
        result = bounded_stack.peek()
        self.assertEqual(result, None)


class TestSizeOperation(unittest.TestCase):
    def test_size_operation__1(self):
        bounded_stack = BoundedStack()
        result = bounded_stack.size()
        self.assertEqual(result, 0)

    def test_size_operation__2(self):
        bounded_stack = BoundedStack()
        bounded_stack.push(1)
        result = bounded_stack.size()
        self.assertEqual(result, 1)

    def test_size_operation__3(self):
        bounded_stack = BoundedStack()
        bounded_stack.push(1)
        bounded_stack.push(1)
        bounded_stack.push(1)
        result = bounded_stack.size()
        self.assertEqual(result, 3)

    def test_size_operation__4(self):
        bounded_stack = BoundedStack()
        [bounded_stack.push(i) for i in range(100)]
        result = bounded_stack.size()
        self.assertEqual(result, 32)

    def test_size_operation__5(self):
        bounded_stack = BoundedStack(77)
        [bounded_stack.push(i) for i in range(100)]
        result = bounded_stack.size()
        self.assertEqual(result, 77)





