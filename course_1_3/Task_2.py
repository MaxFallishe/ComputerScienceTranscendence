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
        if node.NodeKey < key and node.RightChild:
            return self.__find_node_by_key(key, node.RightChild)
        if node.NodeKey < key and not node.RightChild:
            bst_find = BSTFind()
            bst_find.Node = node
            return bst_find
        if node.NodeKey > key and node.LeftChild:
            return self.__find_node_by_key(key, node.LeftChild)
        if node.NodeKey > key and not node.LeftChild:
            bst_find = BSTFind()
            bst_find.Node = node
            bst_find.ToLeft = True
            return bst_find

    def AddKeyValue(self, key, val):
        bst_find = self.FindNodeByKey(key)
        if bst_find.NodeHasKey:
            return False

        bst_node = BSTNode(key, val, bst_find.Node)
        if bst_find.Node is None:
            self.Root = bst_node
            return True

        if bst_find.ToLeft:
            bst_find.Node.LeftChild = bst_node
        if not bst_find.ToLeft:
            bst_find.Node.RightChild = bst_node
        return True

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

        new_node = None
        if bst_find.Node.LeftChild and bst_find.Node.RightChild:
            new_node = self.FinMinMax(bst_find.Node.RightChild, False)
            bst_find.Node.NodeKey = new_node.NodeKey
            bst_find.Node.NodeValue = new_node.NodeValue

        if new_node and new_node is new_node.Parent.LeftChild:
            new_node.Parent.LeftChild = new_node.RightChild
        if new_node and new_node is new_node.Parent.RightChild:
            new_node.Parent.RightChild = new_node.RightChild
        if new_node and new_node.RightChild:
            new_node.RightChild.Parent = new_node.Parent

        if new_node:
            return True

        successor_node = None
        if bst_find.Node.LeftChild:
            successor_node = bst_find.Node.LeftChild
        if bst_find.Node.RightChild:
            successor_node = bst_find.Node.RightChild
        if successor_node and successor_node.LeftChild:
            successor_node.LeftChild.Parent = bst_find.Node
        if successor_node and successor_node.RightChild:
            successor_node.RightChild.Parent = bst_find.Node

        if successor_node:
            bst_find.Node.NodeKey = successor_node.NodeKey
            bst_find.Node.NodeValue = successor_node.NodeValue
            bst_find.Node.RightChild = successor_node.RightChild
            bst_find.Node.LeftChild = successor_node.LeftChild
            return True

        if bst_find.Node is self.Root:
            self.Root = None
            return True

        if bst_find.Node is bst_find.Node.Parent.LeftChild:
            bst_find.Node.Parent.LeftChild = None
        if bst_find.Node is bst_find.Node.Parent.RightChild:
            bst_find.Node.Parent.RightChild = None
        return True

    def Count(self):
        return self.__count(self.Root)

    def __count(self, node) -> int:
        if node is None:
            return 0
        return 1 + self.__count(node.LeftChild) + self.__count(node.RightChild)


def print_BST(node: BSTNode, count: int, node_type: str) -> None:
    if node is None:
        print(count * "     " + f"|{node_type} " + "-", None)
        return
    print(count * "     " + f"|{node_type} " + "-", node.NodeKey)
    print_BST(node.LeftChild, count + 1, "l")
    print_BST(node.RightChild, count + 1, "r")
