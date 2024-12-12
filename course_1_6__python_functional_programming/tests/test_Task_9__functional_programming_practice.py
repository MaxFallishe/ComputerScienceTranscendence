import unittest
from course_1_6__python_functional_programming.Task_9__functional_programming_practice import odometer


class TestOdometer(unittest.TestCase):
    def test_odometer_1(self):
        data = [20, 1, 10, 2]
        result = odometer(data)
        assert result == 30

    def test_odometer_2(self):
        data = [20, 1, 10, 1]
        result = odometer(data)
        assert result == 20

    def test_odometer_3(self):
        data = [20, 2, 10, 5]
        result = odometer(data)
        assert result == 70

    def test_odometer_4(self):
        data = [20, 2, 10, 5, 3, 5]
        result = odometer(data)
        assert result == 70
