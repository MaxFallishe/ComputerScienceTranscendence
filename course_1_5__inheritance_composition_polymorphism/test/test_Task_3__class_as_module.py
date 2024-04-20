import unittest
from course_1_5__inheritance_composition_polymorphism import Task_3__class_as_module


class TestWholeScript(unittest.TestCase):
    def test_whole_script__1(self):
        result = bool(Task_3__class_as_module.soup)
        self.assertTrue(result)

    def test_whole_script__2(self):
        result = bool(Task_3__class_as_module.first_div)
        self.assertTrue(result)

    def test_whole_script__3(self):
        result = 'https://' in Task_3__class_as_module.imghp_link
        self.assertTrue(result)
