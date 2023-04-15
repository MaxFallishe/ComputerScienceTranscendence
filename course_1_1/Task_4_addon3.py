from typing import *


class Stack:
    def __init__(self):
        self.stack = []

    def size(self):
        return len(self.stack)

    def pop(self):
        if not self.size():
            return None
        ret_element = self.stack[0]
        del self.stack[0]
        return ret_element

    def push(self, value):
        self.stack.append('-')
        i = 0
        while i < self.size():
            self.stack[i], value = value, self.stack[i]
            i += 1

    def peek(self):
        if not self.size():
            return None
        return self.stack[0]


def calculate_postfix_expression(stack_1: Stack) -> Union[int, float]:
    stack_2 = Stack()
    while stack_1.size():  # Сan be implemented using eval
        if type(i := stack_1.pop()) is int:
            stack_2.push(i)
            continue
        first_term = stack_2.pop()
        second_term = stack_2.pop()
        if i == '+':
            stack_2.push(first_term + second_term)
        elif i == '*':
            stack_2.push(first_term * second_term)
        elif i == '/':
            stack_2.push(second_term / first_term)
        elif i == '-':
            stack_2.push(second_term - first_term)
        elif i == '=':
            return first_term
