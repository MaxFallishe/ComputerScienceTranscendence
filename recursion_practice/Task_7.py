from typing import Optional, Union


def get_second_largest_number(lst: list) -> Optional[Union[int, float]]:
    if len(lst) < 2:
        return None
    return get_second_largest_number_recursively(lst, float('-inf'), float('-inf'))


def get_second_largest_number_recursively(lst: list, max_number, second_max_number) -> Union[int, float]:
    if len(lst) == 0:
        return second_max_number
    last_lst_elm = lst.pop()
    if last_lst_elm > max_number:
        max_number, second_max_number = last_lst_elm, max_number
    elif last_lst_elm > second_max_number:
        second_max_number = last_lst_elm
    return get_second_largest_number_recursively(lst, max_number, second_max_number)
