class Node:
    def __init__(self, v):
        self.value = v
        self.prev = None
        self.next = None


class DummyNode:
    def __init__(self, prev=None, next=None):
        self.prev = prev
        self.next = next
        self.value = None


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
        if self.head is None:
            return
        dummy_node_1, dummy_node_2 = DummyNode(next=self.head), DummyNode(prev=self.tail)
        self.head.prev, self.tail.next = dummy_node_1, dummy_node_2
        node = dummy_node_1
        while node.next is not None:
            if node.value == val:
                node.prev.next, node.next.prev = node.next, node.prev
                if all is False:
                    break
            node = node.next
        self.head, self.tail = dummy_node_1.next, dummy_node_2.prev
        self.head.prev, self.tail.next = None, None
        if dummy_node_1.next is None:
            self.head, self.tail = None, None

    def clean(self):
        self.head = None
        self.tail = None

    def len(self):
        counter = 0
        node = self.head
        while node is not None:
            counter += 1
            node = node.next
        return counter

    def insert(self, afterNode, newNode):
        if afterNode is None and self.len() == 0:
            self.add_in_head(newNode)
        elif afterNode is None and self.len() > 0:
            self.add_in_tail(newNode)
        else:
            dummy_node_1, dummy_node_2 = DummyNode(next=self.head), DummyNode(prev=self.tail)
            self.head.prev, self.tail.next = dummy_node_1, dummy_node_2
            newNode.next, newNode.prev = afterNode.next, afterNode
            afterNode.next.prev, afterNode.next = newNode, newNode

            self.head, self.tail = dummy_node_1.next, dummy_node_2.prev
            self.head.prev, self.tail.next = None, None
            if dummy_node_1.next is None:
                self.head, self.tail = None, None

    def add_in_head(self, newNode):
        if self.head is None:
            self.head, self.tail = newNode, newNode
        else:
            newNode.next, self.head.prev, self.head = self.head, newNode, newNode
