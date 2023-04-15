class Queue:
    def __init__(self):
        self.queue = []

    def enqueue(self, item):  # O(1)
        self.queue.append(item)

    def dequeue(self):  # O(n)
        if not self.size():
            return None
        queue_start = self.queue[0]
        del self.queue[0]
        return queue_start

    def size(self):
        return len(self.queue)
