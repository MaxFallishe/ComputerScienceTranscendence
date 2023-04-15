class Stack:
    def __init__(self):
        self.stack = []

    def size(self):
        return len(self.stack)

    def pop(self):
        if not self.size():
            return None
        ret_element = self.stack[self.size()-1]
        del self.stack[self.size()-1]
        return ret_element

    def push(self, value):
        self.stack.append(value)

    def peek(self):
        if not self.size():
            return None
        return self.stack[self.size()-1]


class Queue:
    def __init__(self):
        self.main_stack = Stack()
        self.assist_stack = Stack()

    def enqueue(self, item):
        if self.assist_stack.size() > 0:
            while self.assist_stack.size() > 0:
                self.main_stack.push(self.assist_stack.pop())
        self.main_stack.push(item)

    def dequeue(self):
        if not self.size():
            return None
        while self.main_stack.size() > 0:
            self.assist_stack.push(self.main_stack.pop())
        return self.assist_stack.pop()

    def size(self):
        return self.main_stack.size() + self.assist_stack.size()
