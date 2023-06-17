def print_even_nums(lst: list[int]) -> None:
    if not lst:
        return
    popped_elm = lst.pop(0)
    if popped_elm % 2 == 0:
        print(popped_elm, end=' ')
    print_even_nums(lst)
