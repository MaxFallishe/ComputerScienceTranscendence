import unittest
from course_1_5__inheritance_composition_polymorphism.Task_10__openclosed_class_hierarchy import General, SomeObject


class TestGeneral(unittest.TestCase):
    def test_clone_1(self):
        obj = General()
        obj.attribute = "value"
        cloned_obj = obj.clone()
        self.assertIsNot(obj, cloned_obj)
        self.assertEqual(obj.attribute, cloned_obj.attribute)

    def test_clone_2(self):
        obj = SomeObject()
        cloned_obj = obj.clone()


if __name__ == '__main__':
    unittest.main()
