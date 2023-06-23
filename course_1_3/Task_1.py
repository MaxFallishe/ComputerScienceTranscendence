class SimpleTreeNode:
    def __init__(self, val, parent):
        self.NodeValue = val
        self.Parent = parent
        self.Children = []


class SimpleTree:
    def __init__(self, root):
        self.Root = root

    def AddChild(self, ParentNode, NewChild):
        ParentNode.Children += [NewChild]
        NewChild.Parent = ParentNode

    def DeleteNode(self, NodeToDelete):
        NodeToDelete.Parent.Children.remove(NodeToDelete)
        NodeToDelete.Parent = None

    def GetAllNodes(self):
        if not self.Root:
            return []
        return self.__get_nested_nodes(self.Root)

    def __get_nested_nodes(self, node) -> list[SimpleTreeNode]:
        if not node.Children:
            return [node]
        answer = []
        for i in node.Children:
            answer += self.__get_nested_nodes(i)
        return answer + [node]

    def FindNodesByValue(self, val):
        if not self.Root:
            return []
        return self.__find_nested_nodes_by_value(self.Root, val)

    def __find_nested_nodes_by_value(self, node, val):
        answer = []
        if node.Children:
            for i in node.Children:
                answer += self.__find_nested_nodes_by_value(i, val)
        if node.NodeValue == val:
            answer += [node]
        return answer

    def MoveNode(self, OriginalNode, NewParent):
        if OriginalNode is not self.Root:
            self.DeleteNode(OriginalNode)
            self.AddChild(NewParent, OriginalNode)

    def Count(self):
        return len(self.GetAllNodes())

    def LeafCount(self):
        if not self.Root:
            return 0
        return self.get_leaf_count(self.Root)

    def get_leaf_count(self, node):
        if not node.Children:
            return 1
        counter = 0
        for i in node.Children:
            counter += self.get_leaf_count(i)
        return counter

    def nodes_with_levels(self):
        return self.__nodes_with_levels_rec(self.Root, 0)

    def __nodes_with_levels_rec(self, node, level):
        if not node.Children:
            return [(node.NodeValue, level)]
        answer = []
        for i in node.Children:
            answer += self.__nodes_with_levels_rec(i, level+1)
        return answer + [(node.NodeValue, level)]


# node_0 = SimpleTreeNode(0, None)
# node_1 = SimpleTreeNode(1, None)
# node_2 = SimpleTreeNode(2, None)
# node_3 = SimpleTreeNode(3, None)
# node_4 = SimpleTreeNode(4, None)
# node_5 = SimpleTreeNode(5, None)
# node_6 = SimpleTreeNode(6, None)
#
#
# nodes = (node_0, node_1, node_2, node_3, node_4, node_5, node_6)
#
# simple_tree = SimpleTree(node_0)
# simple_tree.AddChild(node_0, node_1)
# simple_tree.AddChild(node_0, node_2)
# simple_tree.AddChild(node_1, node_3)
# simple_tree.AddChild(node_1, node_4)
# simple_tree.AddChild(node_4, node_5)
# simple_tree.AddChild(node_2, node_6)
# simple_tree.DeleteNode(node_1)
# print([i.NodeValue for i in node_1.Children])

# print([i.NodeValue for i in simple_tree.GetAllNodes()])
# print(simple_tree.Count())
# print(simple_tree.LeafCount())
# print(simple_tree.get_nodes_with_levels())
# print(simple_tree.FindNodesByValue(3))
