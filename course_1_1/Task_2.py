class Node:
    def __init__(self, v):
        self.value = v
        self.prev = None
        self.next = None

class LinkedList2:
    def __init__(self):
        self.head = None
        self.tail = None

    def add_in_tail(self, item):
        if self.head is None:
            self.head = item
            item.prev = None
            item.next = None
        else:
            self.tail.next = item
            item.prev = self.tail
        self.tail = item

    def find(self, val):
        node = self.head
        while node is not None:
            if node.value == val:
                return node
            node = node.next

    def find_all(self, val):
        suitable_nodes = []
        node = self.head
        while node is not None:
            if node.value == val:
                suitable_nodes.append(node)
            node = node.next

        return suitable_nodes

    def delete(self, val, all=False):
        node = self.head
        while node is not None:
            if node.value == val and node == self.head:
                self.head = node.next
                if self.head is None:
                    self.tail = None
                else:
                    self.head.prev = None
                if all is False:
                    break
            elif node.value == val and node != self.head:
                if self.tail == node:
                    self.tail = node.prev
                elif self.tail == node.next:
                    self.tail.prev = node.prev
                else:
                    node.next.prev = node.prev
                node.prev.next = node.next

                if all is False:
                    break
            node = node.next


    def clean(self):
        self.head = None
        self.tail = None

    def len(self):
        if self.head is None:
            return 0
        counter = 0
        node = self.head
        while node is not None:
            counter += 1
            node = node.next
        return counter

    def insert(self, afterNode, newNode):
        if afterNode is None and self.head is None:
            self.head = newNode
            self.tail = newNode
        elif afterNode is None and self.head is not None:
            newNode.prev, self.tail.next, self.tail = self.tail, newNode, newNode
        else:
            if afterNode == self.tail:
                newNode.next, newNode.prev, afterNode.next = None, afterNode, newNode
                self.tail = newNode
            else:
                newNode.next, newNode.prev, afterNode.next.prev, afterNode.next = afterNode.next, afterNode, newNode, newNode


    def add_in_head(self, newNode):
        if self.head is None:
            self.head, self.tail = newNode, newNode
        else:
            newNode.next, self.head.prev, self.head = self.head, newNode, newNode
