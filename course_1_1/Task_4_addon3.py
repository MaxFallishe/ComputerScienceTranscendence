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


def calculate_postfix_expression(stack_1: Stack) -> int:
    stack_2 = Stack()
    while stack_1.size():  # can be implemented using eval
        if type(i := stack_1.pop()) is int:
            stack_2.push(i)
        if i == '+':
            stack_2.push(stack_2.pop() + stack_2.pop())
        elif i == '*':
            stack_2.push(stack_2.pop() * stack_2.pop())
        elif i == '=':
            return stack_2.peek()
