from typing import Callable
from pymonad.tools import curry


# A curried function with two string parameters that calculates their concatenated value. And a function based on
# it is a function that gets one parameter and returns.
# "Hello, parameter value"
# This is required so that we can output a standard greeting by specifying only the name.
@curry(2)
def curr_str_add(x: str, y: str) -> str | Callable:
    return x + y


def advanced_hello_world(tail: str) -> str:
    hellower = curr_str_add('Hello, ')
    return hellower(tail)


print(advanced_hello_world('Mr.BDFL'))  # Output: Hello, Mr.BDFL


# A function that receives four arguments as input: the greeting word, the punctuation mark behind it,
# the name of the person being greeted, and the final sign.
# The implementation so that the variant of its partial application would receive only the name as the only parameter,
# and all other parameters would be configured by another call.
@curry(4)
def greetings_expression_composer(greetings: str,
                                  punctuation_mark: str,
                                  final_sign: str,
                                  person_name: str) -> str | Callable:
    return f"{greetings}{punctuation_mark} {person_name}{final_sign}"


final = greetings_expression_composer("Hello")(",")("!")
print(final('Mr.BDFL'))  # Output: Hello, Mr.BDFL!
