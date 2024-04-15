import random
import unittest
from course_1_5__inheritance_composition_polymorphism.Task_1__basic_idea import WizardWand


class TestWizardWand(unittest.TestCase):
    def test_cast_some_stuff_operation__1(self):
        random.seed(13)
        my_wand = WizardWand()
        thing = 'sheep'
        my_wand.cast_some_stuff(thing)

    def test_cast_some_stuff_operation__2(self):
        random.seed(123)
        my_wand = WizardWand()
        thing = 'sheep'
        my_wand.cast_some_stuff(thing)

    def test_cast_some_stuff_operation__3(self):
        random.seed(94)
        my_wand = WizardWand()
        thing = 'sheep'
        my_wand.cast_some_stuff(thing)

