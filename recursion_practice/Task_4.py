def is_string_palindrome(text: str, start_indx=0) -> bool:
    if start_indx >= len(text) / 2:
        return True
    if text[start_indx] == text[(start_indx*-1)-1]:
        return is_string_palindrome(text, start_indx+1)
    return False

