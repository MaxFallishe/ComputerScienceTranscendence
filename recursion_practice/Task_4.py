def is_string_palindrome(text: str) -> bool:
    if len(text) < 1:
        return True
    if text[0] != text[-1]:
        return False
    return is_string_palindrome(text[1:-1])
