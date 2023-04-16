class Deque:
    def __init__(self):
        self.deque = []

    def addFront(self, item):
        self.deque.append(item)
        for i in range(self.size()):
            self.deque[i], self.deque[-1] = self.deque[-1], self.deque[i]


    def addTail(self, item):
        self.deque.append(item)

    def removeFront(self):
        if not self.size():
            return None
        deque_tail = self.deque[0]
        del self.deque[0]
        return deque_tail

    def removeTail(self):
        if not self.size():
            return None
        return self.deque.pop()


    def size(self):
        return len(self.deque)


def is_string_palindrome(string: str) -> bool:
    deque = Deque()
    [deque.addTail(i) for i in string]
    while deque.deque:
        term_1 = deque.removeTail()
        term_2 = deque.removeFront()
        if term_1 != term_2 and term_2 is not None:
            return False
    return True



