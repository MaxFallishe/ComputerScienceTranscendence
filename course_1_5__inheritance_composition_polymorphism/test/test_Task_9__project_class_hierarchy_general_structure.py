import unittest
from course_1_5__inheritance_composition_polymorphism.Task_9__project_class_hierarchy_general_structure import General


class TestGeneral(unittest.TestCase):
    def test_clone(self):
        obj = General()
        obj.attribute = "value"
        cloned_obj = obj.clone()
        self.assertIsNot(obj, cloned_obj)
        self.assertEqual(obj.attribute, cloned_obj.attribute)

    def test_compare(self):
        obj1 = General()
        obj2 = General()
        obj1.attribute = "value"
        obj2.attribute = "value"
        self.assertTrue(obj1.compare(obj2))
        obj2.attribute = "another value"
        self.assertFalse(obj1.compare(obj2))

    def test_serialize_deserialize(self):
        obj = General()
        obj.attribute = "value"
        serialized_obj = obj.serialize()
        new_obj = General()
        new_obj.deserialize(serialized_obj)
        self.assertEqual(obj.attribute, new_obj.attribute)

    def test_print(self):
        obj = General()
        obj.attribute = "value"
        self.assertEqual(str(obj), "General({'attribute': 'value'})")

    def test_is_instance_of(self):
        obj = General()
        self.assertTrue(obj.is_instance_of(General))
        self.assertFalse(obj.is_instance_of(dict))

    def test_get_type(self):
        obj = General()
        self.assertEqual(obj.get_type(), General)


if __name__ == '__main__':
    unittest.main()
