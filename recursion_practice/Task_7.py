from typing import Optional


def get_second_largest_number(lst: list[int]) -> Optional[int]:
    if len(lst) < 2:
        return None
    last_lst_elm = lst.pop()
    penultimate_lst_elm = lst.pop()
    two_sorted_elements = [last_lst_elm, penultimate_lst_elm]
    if penultimate_lst_elm < last_lst_elm:
        two_sorted_elements = [penultimate_lst_elm, last_lst_elm]
    two_largest_numbers = get_largest_numbers_sublist(lst, two_sorted_elements)
    return two_largest_numbers[0]


def get_largest_numbers_sublist(lst: list[int], sublst: list[int]) -> list[int]:
    if len(lst) == 0:
        return sublst
    last_lst_elm = lst.pop()
    for i in range(-1, -len(sublst)-1, -1):
        if last_lst_elm > sublst[i]:
            sublst.insert(i+len(sublst)+1, last_lst_elm)
            sublst.pop(0)
            break
    return get_largest_numbers_sublist(lst, sublst)
