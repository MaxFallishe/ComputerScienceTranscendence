import unittest
from course_1_6__python_functional_programming.Task_8__functional_programming_practice import get_second_biggest_number


class TestSecondBiggestNumber(unittest.TestCase):
    def test_empty_list(self):
        data = []
        result = get_second_biggest_number(data)
        assert result is None

    def test_one_size_list(self):
        data = [1]
        result = get_second_biggest_number(data)
        assert result is None

    def test_two_size_list(self):
        data = [1, 2]
        result = get_second_biggest_number(data)
        assert result == 1

    def test_two_size_uniform_list(self):
        data = [1, 1]
        result = get_second_biggest_number(data)
        assert result == 1

    def test_five_size_uniform_list(self):
        data = [1, 1, 1, 1, 1]
        result = get_second_biggest_number(data)
        assert result == 1

    def test_five_size_list(self):
        data = [-1, 2, 5, 2, -3]
        result = get_second_biggest_number(data)
        assert result == 2
