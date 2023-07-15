class Heap:
    def __init__(self):
        self.HeapArray = []
        self.first_none_ind = 0

    def MakeHeap(self, a, depth) -> None:
        heap_len = 2 ** (depth + 1) - 1
        self.HeapArray = [None] * heap_len
        for i in a:
            self.Add(i)

    def GetMax(self) -> int:
        if self.HeapArray[0] is None:
            return -1
        heap_root = self.HeapArray[0]
        self.__sift_node_from_top()
        self.first_none_ind -= 1
        return heap_root

    def Add(self, key) -> bool:
        if None not in self.HeapArray:
            return False
        self.HeapArray[self.first_none_ind] = key
        self.first_none_ind += 1
        self.__sift_node_from_bottom(self.first_none_ind - 1)
        return True

    def __sift_node_from_bottom(self, node_ind) -> None:
        node_parent_ind = self.__get_node_parent_ind(node_ind)
        while node_parent_ind != -1:
            if self.HeapArray[node_ind] < self.HeapArray[node_parent_ind]:
                break
            self.HeapArray[node_ind], self.HeapArray[node_parent_ind] = self.HeapArray[node_parent_ind], self.HeapArray[node_ind]
            node_ind = node_parent_ind
            node_parent_ind = self.__get_node_parent_ind(node_ind)

    def __sift_node_from_top(self) -> None:
        node_ind = 0
        self.HeapArray[0], self.HeapArray[self.first_none_ind - 1] = self.HeapArray[self.first_none_ind - 1], None
        left_child_ind, right_child_ind = self.__get_node_children_ind(node_ind)
        while True:
            if left_child_ind >= len(self.HeapArray):
                break
            if self.HeapArray[left_child_ind] is None:
                break
            if self.HeapArray[right_child_ind] is None:
                max_child_ind = left_child_ind
            if self.HeapArray[right_child_ind] is not None and self.HeapArray[right_child_ind] >= self.HeapArray[left_child_ind]:
                max_child_ind = right_child_ind
            if self.HeapArray[right_child_ind] is not None and self.HeapArray[right_child_ind] < self.HeapArray[left_child_ind]:
                max_child_ind = left_child_ind
            if self.HeapArray[max_child_ind] < self.HeapArray[node_ind]:
                break
            self.HeapArray[max_child_ind], self.HeapArray[node_ind] = self.HeapArray[node_ind], self.HeapArray[max_child_ind]
            node_ind = max_child_ind
            left_child_ind, right_child_ind = self.__get_node_children_ind(node_ind)


    @staticmethod
    def __get_node_parent_ind(node_ind) -> int:
        return (node_ind + 1) // 2 - 1

    @staticmethod
    def __get_node_children_ind(node_ind) -> tuple:
        left_child_ind = (node_ind + 1) * 2 - 1
        right_child_ind = (node_ind + 1) * 2
        return left_child_ind, right_child_ind
