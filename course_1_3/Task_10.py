class Vertex:
    def __init__(self, val):
        self.Value = val
        self.Hit = False


class SimpleGraph:
    def __init__(self, size):
        self.max_vertex = size
        self.m_adjacency = [[0] * size for _ in range(size)]
        self.vertex = [None] * size

    def AddVertex(self, v) -> None:
        vert_obj = Vertex(v)
        for i, el in enumerate(self.vertex):
            if el is None:
                self.vertex[i] = vert_obj
                return

    def RemoveVertex(self, v) -> None:
        self.vertex[v] = None
        self.m_adjacency[v] = [0] * len(self.vertex)
        for i in self.m_adjacency:
            i[v] = 0

    def IsEdge(self, v1, v2) -> bool:
        return bool(self.m_adjacency[v1][v2])

    def AddEdge(self, v1, v2) -> None:
        self.m_adjacency[v1][v2] = 1
        self.m_adjacency[v2][v1] = 1

    def RemoveEdge(self, v1, v2) -> None:
        self.m_adjacency[v1][v2] = 0
        self.m_adjacency[v2][v1] = 0

    def DepthFirstSearch(self, VFrom, VTo):
        v_from_vertex = self.vertex[VFrom]
        init_stack = [v_from_vertex, ]
        if v_from_vertex is None:
            return []
        if VFrom == VTo:
            return init_stack
        v_from_vertex.Hit = True
        return self.__depth_first_search(init_stack, VFrom, VTo)

    def __depth_first_search(self, stack: list, v_from: int, v_to: int) -> list:
        vertex_edges = self.m_adjacency[v_from]
        adjacent_vertexes = [i for i in range(self.max_vertex) if vertex_edges[i]]
        for i in adjacent_vertexes:
            vertex = self.vertex[i]
            v_to_vertex = self.vertex[v_to]
            if vertex is v_to_vertex:
                stack.append(vertex)
                return stack
            if vertex.Hit:
                continue
            stack.append(vertex)
            vertex.Hit = True
            f_stack = self.__depth_first_search(stack, i, v_to)
            if f_stack[-1] is v_to_vertex and v_to_vertex is not None:
                return f_stack
        stack.pop()
        return stack
