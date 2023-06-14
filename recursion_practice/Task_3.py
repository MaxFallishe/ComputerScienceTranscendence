def get_list_len(lst: list) -> int:
    try:
        lst.pop(0)
    except IndexError as e:
        return 0
    return 1 + get_list_len(lst)
