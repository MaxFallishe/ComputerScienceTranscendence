class aBST:
    def __init__(self, depth):
        tree_size = 2 ** (depth + 1) - 1
        self.Tree = [None] * tree_size

    def FindKeyIndex(self, key):
        root_ind = 0
        return self.__find_key_index(key, root_ind)

    def __find_key_index(self, key, node_ind):
        if node_ind >= len(self.Tree):
            return None
        if self.Tree[node_ind] == key:
            return node_ind
        if self.Tree[node_ind] is None:
            return -node_ind
        if self.Tree[node_ind] > key:
            return self.__find_key_index(key, node_ind * 2 + 1)
        return self.__find_key_index(key, node_ind * 2 + 2)


    def AddKey(self, key):
        key_ind = self.FindKeyIndex(key)
        if key_ind and key_ind < 0:
            key_ind = - key_ind
        if key_ind is not None:
            self.Tree[key_ind] = key
            return key_ind
        return -1
