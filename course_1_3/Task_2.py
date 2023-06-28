class BSTNode:
    def __init__(self, key, val, parent):
        self.NodeKey = key
        self.NodeValue = val
        self.Parent = parent
        self.LeftChild = None
        self.RightChild = None


class BSTFind:
    def __init__(self):
        self.Node = None

        self.NodeHasKey = False
        self.ToLeft = False


class BST:
    def __init__(self, node):
        self.Root = node

    def FindNodeByKey(self, key):
        if not self.Root:
            bst_find = BSTFind()
            return bst_find
        return self.__find_node_by_key(key, self.Root)

    def __find_node_by_key(self, key, node: BSTNode) -> BSTFind:
        if node.NodeKey == key:
            bst_find = BSTFind()
            bst_find.Node = node
            bst_find.NodeHasKey = True
            return bst_find
        elif node.NodeKey < key and node.RightChild is not None:
            return self.__find_node_by_key(key, node.RightChild)
        elif node.NodeKey < key and node.RightChild is None:
            bst_find = BSTFind()
            bst_find.Node = node
            return bst_find
        elif node.NodeKey > key and node.LeftChild is not None:
            return self.__find_node_by_key(key, node.LeftChild)
        elif node.NodeKey > key and node.LeftChild is None:
            bst_find = BSTFind()
            bst_find.Node = node
            bst_find.ToLeft = True
            return bst_find


    def AddKeyValue(self, key, val):
        bst_find = self.FindNodeByKey(key)
        if bst_find.NodeHasKey:
            return False
        bst_node = BSTNode(key, val, bst_find.Node)
        if bst_find.ToLeft:
            bst_find.Node.LeftChild = bst_node
        if not bst_find.ToLeft:
            bst_find.Node.RightChild = bst_node

    def FinMinMax(self, FromNode, FindMax):
        if FromNode.LeftChild is None and FindMax is not True:
            return FromNode
        if FromNode.RightChild is None and FindMax is True:
            return FromNode
        if FromNode.LeftChild is not None and FindMax is not True:
            return self.FinMinMax(FromNode.LeftChild, FindMax)
        if FromNode.RightChild is not None and FindMax is True:
            return self.FinMinMax(FromNode.RightChild, FindMax)


    def DeleteNodeByKey(self, key):
        bst_find = self.FindNodeByKey(key)
        if not bst_find.NodeHasKey:
            return False
        # 1. Scenario - if leftChild = None or rightChild = None or both = None
        if None in (bst_find.Node.LeftChild, bst_find.Node.RightChild):
            new_node = bst_find.Node.LeftChild
            if new_node is None:
                new_node = bst_find.Node.RightChild
            if bst_find.Node.Parent is None:
                self.Root = new_node
                if new_node is not None:
                    new_node.Parent = None
                return
            if new_node is not None:
                new_node.Parent = bst_find.Node.Parent
            if bst_find.Node.Parent.LeftChild is bst_find.Node:
                bst_find.Node.Parent.LeftChild = new_node
            elif bst_find.Node.Parent.RightChild is bst_find.Node:
                bst_find.Node.Parent.RightChild = new_node
            return

        # 2. Scenario if leftChild and rightChild != None And bst_find.Node.RightChild.LeftChild is None
        if bst_find.Node.RightChild.LeftChild is None:
            new_node = bst_find.Node.RightChild
            bst_find.Node.LeftChild.Parent = new_node
            new_node.LeftChild = bst_find.Node.LeftChild
            if bst_find.Node.Parent is None:
                self.Root = new_node
                new_node.Parent = None
                return
            new_node.Parent = bst_find.Node.Parent
            if bst_find.Node.Parent.LeftChild is bst_find.Node:
                bst_find.Node.Parent.LeftChild = new_node
            elif bst_find.Node.Parent.RightChild is bst_find.Node:
                bst_find.Node.Parent.RightChild = new_node
            return

        # 3. Scenario if leftChild and rightChild != None And bst_find.Node.RightChild.LeftChild is None
        new_node = self.FinMinMax(bst_find.Node.RightChild, False)
        if new_node.RightChild is not None:
            new_node.Parent.LeftChild = new_node.RightChild
            new_node.RightChild.Parent = new_node.Parent
        else:
            if new_node.Parent.LeftChild is new_node:
                new_node.Parent.LeftChild = None
            elif new_node.Parent.RightChild is new_node:
                new_node.Parent.RightChild = None

        new_node.LeftChild = bst_find.Node.LeftChild
        new_node.RightChild = bst_find.Node.RightChild
        bst_find.Node.LeftChild.Parent = new_node
        bst_find.Node.RightChild.Parent = new_node

        if bst_find.Node is self.Root:
            self.Root = new_node
            new_node.Parent = None
            return

        new_node.Parent = bst_find.Node.Parent

        if bst_find.Node.Parent.LeftChild is bst_find.Node:
            bst_find.Node.Parent.LeftChild = new_node
        elif bst_find.Node.Parent.RightChild is bst_find.Node:
            bst_find.Node.Parent.RightChild = new_node
        return


    def Count(self):
        return self.__count(self.Root)

    def __count(self, node) -> int:
        if node is None:
            return 0
        return 1 + self.__count(node.LeftChild) + self.__count(node.RightChild)


def print_BST(node: BSTNode, count: int, node_type: str) -> None:
    if node is None:
        print(count * '     ' + f'|{node_type} ' + "-", None)
        return
    print(count * '     ' + f'|{node_type} ' + "-", node.NodeKey)
    print_BST(node.LeftChild, count+1, 'l')
    print_BST(node.RightChild, count+1, 'r')
