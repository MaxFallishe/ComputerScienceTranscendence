from typing import Optional, Union


def get_second_largest_number(lst: list) -> Optional[Union[int, float]]:
    if len(lst) < 2:
        return None
    bigger_item = max(lst[0], lst[1])
    smaller_item = min(lst[0], lst[1])
    return get_second_largest_number_recursively(lst, 2, bigger_item, smaller_item)


def get_second_largest_number_recursively(lst: list, ind: int, max_number, second_max_number) -> Union[int, float]:
    if ind == len(lst):
        return second_max_number
    last_lst_elm = lst[ind]
    if last_lst_elm > max_number:
        max_number, second_max_number = last_lst_elm, max_number
    elif last_lst_elm > second_max_number:
        second_max_number = last_lst_elm
    return get_second_largest_number_recursively(lst, ind+1, max_number, second_max_number)

