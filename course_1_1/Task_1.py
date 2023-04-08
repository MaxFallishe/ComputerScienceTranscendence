class Node:

    def __init__(self, v):
        self.value = v
        self.next = None

class LinkedList:

    def __init__(self):
        self.head = None
        self.tail = None

    def add_in_tail(self, item):
        if self.head is None:
            self.head = item
        else:
            self.tail.next = item
        self.tail = item

    def print_all_nodes(self):
        node = self.head
        while node != None:
            print(node.value)
            node = node.next

    def find(self, val):
        node = self.head
        while node is not None:
            if node.value == val:
                return node
            node = node.next
        return None

    def find_all(self, val):
        suitable_nodes = []
        node = self.head
        while node is not None:
            if node.value == val:
                suitable_nodes.append(node)
            node = node.next
        return suitable_nodes

    def delete(self, val, all=False):
        dummy_start_node = Node('')
        dummy_start_node.next = self.head

        node = dummy_start_node
        while node.next is not None:
            if node.next.value == val and node.next == self.head:
                self.head = node.next.next
                node = node.next
                if self.head is None:
                    self.tail = None
                if all is False:
                    break
            elif node.next.value == val and node.next != self.head:
                if self.tail == node.next:
                    self.tail = node
                node.next = node.next.next
                if all is False:
                    break
            else:
                node = node.next

    def clean(self):
        self.head = None
        self.tail = None

    def len(self):
        if self.head is None:
            return 0
        else:
            counter = 0
            node = self.head
            while node is not None:
                counter += 1
                node = node.next
            return counter

    def insert(self, afterNode, newNode):
        if afterNode is None:
            newNode.next = self.head
            self.head = newNode
            if self.head.next is None:
                self.tail = self.head
        else:
            node = self.head
            while node is not None:
                if node == afterNode:
                    node.next, newNode.next = newNode, node.next
                    if node == self.tail:
                        self.tail = newNode
                    break
                node = node.next
