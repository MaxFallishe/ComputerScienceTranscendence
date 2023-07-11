class BSTNode:
    def __init__(self, key, parent):
        self.NodeKey = key
        self.Parent = parent
        self.LeftChild = None
        self.RightChild = None
        self.Level = 0


class BalancedBST:
    def __init__(self):
        self.Root = None

    def GenerateTree(self, a):
        if not a:
            return
        a.sort()
        root_node_key = a[len(a)//2]
        root_node = BSTNode(root_node_key, None)
        root_node.Level = 0
        self.Root = root_node
        self.Root.LeftChild = self.__generate_node_child(a[:len(a)//2], self.Root, 1)
        self.Root.RightChild = self.__generate_node_child(a[len(a)//2+1:], self.Root, 1)

    def __generate_node_child(self, a, parent, level):
        child = None
        if a:
            child_node_key = a[len(a)//2]
            child = BSTNode(child_node_key, parent)
            child.Level = level
            child.LeftChild = self.__generate_node_child(a[:len(a)//2], self.Root, level + 1)
            child.RightChild = self.__generate_node_child(a[len(a)//2+1:], self.Root, level + 1)
        return child

    def IsBalanced(self, root_node):
        return self.is_correct_nodes_keys_positioning(root_node) & self.is_correct_nodes_levels(root_node)

    def is_correct_nodes_keys_positioning(self, root_node):
        if not root_node:
            return True
        if root_node.LeftChild and root_node.LeftChild.NodeKey >= root_node.NodeKey:
            return False
        if root_node.RightChild and root_node.RightChild.NodeKey < root_node.NodeKey:
            return False
        return self.is_correct_nodes_keys_positioning(root_node.LeftChild) & self.is_correct_nodes_keys_positioning(root_node.RightChild)

    def is_correct_nodes_levels(self, root_node):
        left_subtree_max_level = root_node.Level
        right_subtree_max_level = root_node.Level
        if root_node.LeftChild:
            left_subtree_max_level = self.max_depth_level(root_node.LeftChild)
        if root_node.RightChild:
            right_subtree_max_level = self.max_depth_level(root_node.RightChild)

        return abs(left_subtree_max_level - right_subtree_max_level) <= 1

    def max_depth_level(self, node):
        if node.LeftChild and node.RightChild:
            return max(self.max_depth_level(node.LeftChild), self.max_depth_level(node.RightChild))
        if node.LeftChild:
            return self.max_depth_level(node.LeftChild)
        if node.RightChild:
            return self.max_depth_level(node.RightChild)
        return node.Level
