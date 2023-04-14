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
