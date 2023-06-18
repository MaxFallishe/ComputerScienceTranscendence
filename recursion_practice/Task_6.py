def print_even_indexed_items(lst: list, even_index_flag=None) -> None:
    if even_index_flag is None:
        even_index_flag = 1
    if not len(lst):
        return
    popped_item = lst.pop(0)
    if even_index_flag + 1:
        print(popped_item, end=' ')
    print_even_indexed_items(lst, even_index_flag=even_index_flag*-1)
