def print_even_nums(lst: list[int]) -> None:
    if not lst:
        return
    if not (popped_elm := lst.pop(0)) % 2:
        print(popped_elm, end=' ')
    print_even_nums(lst)
