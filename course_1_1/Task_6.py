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
