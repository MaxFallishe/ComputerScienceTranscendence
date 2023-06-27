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
        if bst_find.Node is self.Root:
            self.__delete_root_node()
            return

        if not bst_find.Node.LeftChild and not bst_find.Node.RightChild:
            if bst_find.Node.Parent.LeftChild is bst_find.Node:
                bst_find.Node.Parent.LeftChild = None
            elif bst_find.Node.Parent.RightChild is bst_find.Node:
                bst_find.Node.Parent.RightChild = None
            bst_find.Node.Parent = None
        elif bst_find.Node.LeftChild and not bst_find.Node.RightChild:
            if bst_find.Node.Parent.LeftChild is bst_find.Node:
                bst_find.Node.Parent.LeftChild = bst_find.Node.LeftChild
            elif bst_find.Node.Parent.RightChild is bst_find.Node:
                bst_find.Node.Parent.RightChild = bst_find.Node.LeftChild
            bst_find.Node.Parent = None
        elif not bst_find.Node.LeftChild and bst_find.Node.RightChild:
            if bst_find.Node.Parent.LeftChild is bst_find.Node:
                bst_find.Node.Parent.LeftChild = bst_find.Node.RightChild
            elif bst_find.Node.Parent.RightChild is bst_find.Node:
                bst_find.Node.Parent.RightChild = bst_find.Node.RightChild
            bst_find.Node.Parent = None
        elif bst_find.Node.LeftChild and bst_find.Node.RightChild:
            new_root = self.FinMinMax(bst_find.Node.RightChild, False)
            if new_root.RightChild:
                if new_root.Parent.LeftChild is new_root:
                    new_root.Parent.LeftChild = new_root.RightChild
                    new_root.RightChild.Parent = new_root.Parent
                elif new_root.Parent.RightChild is new_root:
                    new_root.Parent.RightChild = new_root.RightChild
                    new_root.RightChild.Parent = new_root.Parent

            if new_root.Parent.LeftChild is new_root:
                new_root.Parent.LeftChild = None
            elif new_root.Parent.RightChild is new_root:
                new_root.Parent.RightChild = None

            if bst_find.Node.Parent.LeftChild is bst_find.Node:
                bst_find.Node.Parent.LeftChild = new_root
                new_root.Parent = bst_find.Node.Parent
            elif bst_find.Node.Parent.RightChild is bst_find.Node:
                bst_find.Node.Parent.RightChild = new_root
                new_root.Parent = bst_find.Node.Parent

            if new_root is not bst_find.Node.RightChild:
                new_root.RightChild = bst_find.Node.RightChild
            new_root.LeftChild = bst_find.Node.LeftChild
            bst_find.Node.RightChild = None
            bst_find.Node.LeftChild = None
            bst_find.Node.Parent = None


    def __delete_root_node(self) -> None:
        if not self.Root.LeftChild and not self.Root.RightChild:
            self.Root = None
        elif self.Root.LeftChild and not self.Root.RightChild:
            self.Root = self.Root.LeftChild
            self.Root.Parent = None
        elif not self.Root.LeftChild and self.Root.RightChild:
            self.Root = self.Root.RightChild
            self.Root.Parent = None
        elif self.Root.LeftChild and self.Root.RightChild:
            new_root = self.FinMinMax(self.Root.RightChild, False)
            if new_root.RightChild:
                if new_root.Parent.LeftChild is new_root:
                    new_root.Parent.LeftChild = new_root.RightChild
                    new_root.RightChild.Parent = new_root.Parent
                elif new_root.Parent.RightChild is new_root:
                    new_root.Parent.RightChild = new_root.RightChild
                    new_root.RightChild.Parent = new_root.Parent

            if new_root.Parent.LeftChild is new_root:
                new_root.Parent.LeftChild = None
            elif new_root.Parent.RightChild is new_root:
                new_root.Parent.RightChild = None

            if new_root is not self.Root.RightChild:
                new_root.RightChild = self.Root.RightChild

            new_root.LeftChild = self.Root.LeftChild

            if new_root.LeftChild:
                new_root.LeftChild.Parent = new_root

            self.Root.RightChild = None
            self.Root.LeftChild = None

            self.Root = new_root
            new_root.Parent = None


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
