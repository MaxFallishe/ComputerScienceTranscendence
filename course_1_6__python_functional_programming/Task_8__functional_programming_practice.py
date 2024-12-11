# Implementation of the function of finding the second maximum number using "functools.reduce()"
# without using min() and max()
from functools import reduce


def get_second_biggest_number(seq: list[int]) -> int:
    if len(seq) <= 1:
        return None

    def gen_maxduo_lst(x, y):
        if not isinstance(x, list):
            x = [x]
        max_duo = x + [y]

        if len(max_duo) > 2:
            min_value = max_duo[0] if max_duo[0] < max_duo[1] else max_duo[1]
            min_value = max_duo[2] if max_duo[2] < min_value else min_value
            max_duo.remove(min_value)
        return max_duo

    res_duo = reduce(gen_maxduo_lst, seq)
    return res_duo[0] if res_duo[0] < res_duo[1] else res_duo[1]
