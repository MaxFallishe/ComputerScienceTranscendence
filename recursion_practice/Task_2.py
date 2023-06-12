def get_number_digits_sum(number: int) -> int:
    number = abs(number)
    if -10 < number < 10:
        return number
    return number % 10 + get_number_digits_sum(number // 10)
