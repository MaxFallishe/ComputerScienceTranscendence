class Node:
    def __init__(self, v):
        self.value = v
        self.prev = None
        self.next = None


class OrderedList:
    def __init__(self, asc):
        self.head = None
        self.tail = None
        self.__ascending = asc

    def compare(self, v1, v2):
        if v1.value > v2.value:
            return 1
        elif v1.value == v2.value:
            return 0
        elif v1.value < v2.value:
            return -1

    def add(self, value):
        new_node = Node(value)
        if not self.head:
            self.head, self.tail = new_node, new_node
            return
        asc_filt_lst = [0, -1] if self.__ascending else [0, 1]
        if self.compare(new_node, self.head) in asc_filt_lst:
            self.head.prev, new_node.next = new_node, self.head
            self.head = new_node
            return
        if self.compare(self.tail, new_node) in asc_filt_lst:
            self.tail.next, new_node.prev = new_node, self.tail
            self.tail = new_node
            return
        node = self.head.next
        while self.compare(new_node, node) not in asc_filt_lst:
            node = node.next
        new_node.prev, new_node.next = node.prev, node
        node.prev.next, node.prev = new_node, new_node


    def find(self, val):
        asc_filt = 1 if self.__ascending else -1
        node = self.head
        while node is not None:
            if node.value * asc_filt > val * asc_filt:
                break
            if node.value == val:
                return node
            node = node.next

    def delete(self, val):
        node_to_del = self.find(val)
        if not node_to_del:
            return
        if self.len() <= 1:
            self.clean(asc=self.__ascending)
        elif self.head is node_to_del:
            self.head = self.head.next
            self.head.prev = None
        elif self.tail is node_to_del:
            self.tail = self.tail.prev
            self.tail.next = None
        else:
            node_to_del.prev.next, node_to_del.next.prev = node_to_del.next, node_to_del.prev


    def clean(self, asc):
        self.head = None
        self.tail = None
        self.__ascending = asc


    def len(self):
        if self.head is None:
            return 0
        counter = 0
        node = self.head
        while node is not None:
            counter += 1
            node = node.next
        return counter

    def get_all(self):
        r = []
        node = self.head
        while node != None:
            r.append(node)
            node = node.next
        return r


class OrderedStringList(OrderedList):
    def __init__(self, asc):
        super(OrderedStringList, self).__init__(asc)

    def compare(self, v1, v2):
        v1.value = v1.value.strip()
        v2.value = v2.value.strip()
        if v1.value > v2.value:
            return 1
        elif v1.value == v2.value:
            return 0
        elif v1.value < v2.value:
            return -1
