from collections import deque


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

    def BreadthFirstSearch(self, VFrom, VTo):
        self.__mark_all_vertices_as_unvisited()
        if self.vertex[VFrom] is None or self.vertex[VTo] is None:
            return []
        path_to_vertex = []
        init_deq = deque(
            [[VFrom, path_to_vertex]]
        )  # create dataclass would be much better
        return self.__breadth_first_search(VTo, init_deq)

    def __mark_all_vertices_as_unvisited(self):
        for i in self.vertex:
            if i:
                i.Hit = False

    def __breadth_first_search(self, v_to: int, deq: deque) -> list:
        if not deq:
            return []
        vertex = deq.pop()
        vertex_ind = vertex[0]
        vertex_path = vertex[1]
        vertex_adj_ind = self.m_adjacency[vertex_ind]
        vertex_obj = self.vertex[vertex_ind]
        v_to_vertex = self.vertex[v_to]
        if vertex_obj is v_to_vertex:
            return vertex_path + [vertex_obj]
        vertex_obj.Hit = True
        adjacent_vertexes = [i for i in range(self.max_vertex) if vertex_adj_ind[i]]
        for i in adjacent_vertexes:
            if self.vertex[i].Hit is not True:
                vertex_path_i = vertex_path + [vertex_obj]
                deq.appendleft([i, vertex_path_i])
        return self.__breadth_first_search(v_to, deq)
