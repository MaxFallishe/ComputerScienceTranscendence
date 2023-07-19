class Vertex:
    def __init__(self, val):
        self.Value = val


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
