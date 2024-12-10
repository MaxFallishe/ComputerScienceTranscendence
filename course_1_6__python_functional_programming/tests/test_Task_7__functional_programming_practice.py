import unittest

from pymonad.operators.maybe import Just

from course_1_6__python_functional_programming.Task_7__functional_programming_practice import ConquestCampaign


class TestConquestCampign(unittest.TestCase):
    """
    Stage №1 - Field setup
    Stage №2..N - Field state turn by turn

    ⚪ - empty cell
    ⚫ - conquered cell
    """

    def test_2x2_field_with_single_start_coordinate(self):
        """
        Stage №1  -> Stage №2  -> Stage №3
        ⚫ ⚪         ⚫ ⚫          ⚫ ⚫
        ⚪ ⚪         ⚫ ⚪          ⚫ ⚫
        """
        result = ConquestCampaign(N=2, M=2, L=1, batallion=Just([1, 1]))
        assert result == 3

    def test_3x4_field_with_two_start_coordinate(self):
        """
        Stage №1   ->     Stage №2    ->    Stage №3
        ⚪ ⚪ ⚪ ⚪         ⚪ ⚫ ⚪ ⚪          ⚫ ⚫ ⚫ ⚫
        ⚪ ⚫ ⚪ ⚪         ⚫ ⚫ ⚫ ⚫          ⚫ ⚫ ⚫ ⚫
        ⚪ ⚪ ⚪ ⚫         ⚪ ⚫ ⚫ ⚫          ⚫ ⚫ ⚫ ⚫
        """
        result = ConquestCampaign(N=3, M=4, L=2, batallion=Just([2, 2, 3, 4]))
        assert result == 3
