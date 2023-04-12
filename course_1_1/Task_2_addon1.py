class Node:
    def __init__(self, v):
        self.value = v
        self.prev = None
        self.next = None


class DummyNode:
    def __init__(self, v=None):
        self.prev = None
        self.next = None
        self.value = v


class LinkedList2:
    def __init__(self):
        self.dummy_head = DummyNode(v='d-head')  # to fix
        self.dummy_tail = DummyNode(v='d-tail')  # to fix
        self.dummy_tail.prev = self.dummy_head
        self.dummy_head.next = self.dummy_tail

    def add_in_tail(self, item: Node):
        self.dummy_tail.prev.next, item.next = item, self.dummy_tail
        item.prev, self.dummy_tail.prev = self.dummy_tail.prev, item

    def find(self, val):
        node = self.dummy_head.next
        while node.next is not None:
            if node.value == val:
                return node
            node = node.next

    def find_all(self, val):
        suitable_nodes = []
        node = self.dummy_head.next
        while node.next is not None:
            if node.value == val:
                suitable_nodes.append(node)
            node = node.next
        return suitable_nodes

    def delete(self, val, all=False):
        node = self.dummy_head.next
        while node.next is not None:
            if node.value == val:
                node.prev.next, node.next.prev = node.next, node.prev
                if all is False:
                    break
            node = node.next

    def clean(self):
        self.dummy_head = DummyNode()
        self.dummy_tail = DummyNode()
        self.dummy_tail.prev = self.dummy_head
        self.dummy_head.next = self.dummy_tail

    def len(self):
        counter = 0
        node = self.dummy_head.next
        while node.next is not None:
            counter += 1
            node = node.next
        return counter

    def insert(self, afterNode, newNode):
        if afterNode is None and self.len() == 0:
            self.add_in_head(newNode)
        elif afterNode is None and self.len() > 0:
            self.add_in_tail(newNode)
        else:
            newNode.prev, newNode.next = afterNode, afterNode.next
            afterNode.next.prev, afterNode.next = newNode, newNode

    def add_in_head(self, newNode):
        newNode.prev, newNode.next = self.dummy_head, self.dummy_head.next
        self.dummy_head.next.prev, self.dummy_head.next = newNode, newNode
