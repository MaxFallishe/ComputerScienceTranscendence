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
