def print_even_indexed_items(lst: list) -> None:
    print_recursively_even_indexed_items(lst, 1)


def print_recursively_even_indexed_items(lst: list, even_index_flag: int) -> None:
    if not len(lst):
        return
    popped_item = lst.pop(0)
    if even_index_flag + 1:
        print(popped_item, end=' ')
    print_recursively_even_indexed_items(lst, even_index_flag * -1)
