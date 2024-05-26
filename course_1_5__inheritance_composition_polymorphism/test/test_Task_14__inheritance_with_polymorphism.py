import unittest
from course_1_5__inheritance_composition_polymorphism.Task_14__inheritance_with_polymorphism import Vector


class TestVector(unittest.TestCase):
    def setUp(self):
        self.a = Vector([1, 11, 30, 1])
        self.b = Vector([2, 11, 1, 1])
        self.c = Vector([3, 11, 30, 1])
        self.v = Vector([4, 11, 1, 1])
        self.f = Vector([self.a, self.b])
        self.g = Vector([self.c, self.v])

    def test_add_method(self):
        result = self.a + self.b
        self.assertEqual(result, Vector([3, 22, 31, 2]))

    def test_add_method_recursive_option(self):
        result = self.f + self.g
        self.assertEqual(result[0], Vector([4, 22, 60, 2]))

    def test_len_method(self):
        self.assertEqual(len(self.a), 4)

    def test_getitem_method(self):
        self.assertEqual(self.a[2], 30)
        self.assertEqual(self.b[0], 2)

    def test_str_method(self):
        self.assertEqual(str(self.a), "[1, 11, 30, 1]")


if __name__ == '__main__':
    unittest.main()
