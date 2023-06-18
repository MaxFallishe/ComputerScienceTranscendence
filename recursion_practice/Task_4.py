def is_string_palindrome(text: str) -> bool:
    return is_string_palindrome_recurcively(text, 0)


def is_string_palindrome_recurcively(text: str, start_indx: int) -> bool:
    if start_indx >= len(text) / 2:
        return True
    if text[start_indx] == text[(start_indx*-1)-1]:
        return is_string_palindrome_recurcively(text, start_indx+1)
    return False

