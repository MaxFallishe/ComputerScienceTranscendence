from typing import Union


def get_number_in_power(number: Union[int, float], power: int) -> Union[int, float]:
    if number == power == 0:
        return 1
    if power == 1:
        return number
    if power < 1:
        return (1 / number) * get_number_in_power(number, power+1)
    return number * get_number_in_power(number, power-1)
