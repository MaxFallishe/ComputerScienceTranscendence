from ..Task_10 import *
import unittest


class GraphMocksGenerator:
    def __init__(self):
        pass

    def generate_graph(self) -> SimpleGraph:
        pass


class GraphMocksGeneratorType0(GraphMocksGenerator):
    """
    Сonnected undirected graph object with following structure:
        0 - 2
           /
          1
    """

    def generate_graph(self) -> SimpleGraph:
        graph = SimpleGraph(5)
        graph.AddVertex(0)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddEdge(0, 2)
        graph.AddEdge(2, 1)
        return graph


class GraphMocksGeneratorType1(GraphMocksGenerator):
    """
    Сonnected undirected graph object with following structure:
        0 - 2
           /
          1 - 3
          |
          4
    """

    def generate_graph(self) -> SimpleGraph:
        graph = SimpleGraph(10)
        graph.AddVertex(0)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddVertex(4)

        graph.AddEdge(0, 2)
        graph.AddEdge(2, 1)
        graph.AddEdge(1, 3)
        graph.AddEdge(1, 4)
        return graph


class GraphMocksGeneratorType2(GraphMocksGenerator):
    """
    Сonnected undirected graph object with following structure:
                0
               / \
              4   2
              |   |
              1 - 3
    """

    def generate_graph(self) -> SimpleGraph:
        graph = SimpleGraph(10)
        graph.AddVertex(0)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddVertex(4)

        graph.AddEdge(0, 2)
        graph.AddEdge(2, 3)
        graph.AddEdge(3, 1)
        graph.AddEdge(1, 4)
        graph.AddEdge(4, 0)
        return graph

class TestAddVertex(unittest.TestCase):
    def test_add_vertex__1(self):
        graph = SimpleGraph(0)
        graph.AddVertex(99)
        self.assertEqual(graph.vertex, [])

    def test_add_vertex__2(self):
        graph = SimpleGraph(1)
        self.assertEqual(graph.vertex, [None])

    def test_add_vertex__3(self):
        graph = SimpleGraph(1)
        graph.AddVertex(99)
        result = [i.Value for i in graph.vertex]
        reference = [99]
        self.assertEqual(reference, result)

    def test_add_vertex__4(self):
        graph = SimpleGraph(2)
        graph.AddVertex(99)
        result = [i.Value if i else i for i in graph.vertex]
        reference = [99, None]
        self.assertEqual(reference, result)

    def test_add_vertex__5(self):
        graph = SimpleGraph(5)
        graph.AddVertex(4)
        graph.AddVertex(1)
        graph.AddVertex(5)
        graph.AddVertex(2)
        graph.AddVertex(3)
        result = [i.Value if i else i for i in graph.vertex]
        reference = [4, 1, 5, 2, 3]
        self.assertEqual(reference, result)

    def test_add_vertex__6(self):
        graph = SimpleGraph(5)
        graph.AddVertex(4)
        graph.AddVertex(1)
        graph.AddVertex(5)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddVertex(99)
        result = [i.Value if i else i for i in graph.vertex]
        reference = [4, 1, 5, 2, 3]
        self.assertEqual(reference, result)

    def test_add_vertex__7(self):
        graph = SimpleGraph(5)
        graph.AddVertex(4)
        graph.AddVertex(1)
        graph.AddVertex(5)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.RemoveVertex(0)
        graph.AddVertex(99)
        result = [i.Value if i else i for i in graph.vertex]
        reference = [99, 1, 5, 2, 3]
        self.assertEqual(reference, result)

    def test_add_vertex__8(self):
        graph = SimpleGraph(5)
        graph.AddVertex(4)
        graph.AddVertex(1)
        graph.AddVertex(5)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.RemoveVertex(4)
        graph.RemoveVertex(1)
        graph.AddVertex(99)
        graph.AddVertex(66)
        result = [i.Value if i else i for i in graph.vertex]
        reference = [4, 99, 5, 2, 66]
        self.assertEqual(reference, result)




class TestRemoveVertex(unittest.TestCase):
    def test_remove_vertex__1(self):
        graph = SimpleGraph(5)
        graph.RemoveVertex(4)
        result = [i.Value if i else i for i in graph.vertex]
        reference = [None, None, None, None, None]
        self.assertEqual(reference, result)

    def test_remove_vertex__2(self):
        graph = SimpleGraph(5)
        graph.AddVertex(99)
        graph.RemoveVertex(0)
        result = [i.Value if i else i for i in graph.vertex]
        reference = [None, None, None, None, None]
        self.assertEqual(reference, result)

    def test_remove_vertex__3(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.RemoveVertex(0)
        result = [i.Value if i else i for i in graph.vertex]
        reference = [None, 2, None, None, None]
        self.assertEqual(reference, result)

    def test_remove_vertex__4(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddEdge(0, 1)
        graph.RemoveVertex(1)
        result = graph.m_adjacency[0][1]
        reference = 0
        self.assertEqual(reference, result)

    def test_remove_vertex__5(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddEdge(0, 1)
        graph.RemoveVertex(1)
        result = graph.m_adjacency[1][0]
        reference = 0
        self.assertEqual(reference, result)


class TestIsEdge(unittest.TestCase):
    def test_is_edge__1(self):
        graph = SimpleGraph(5)
        result = graph.IsEdge(0, 3)
        self.assertFalse(result)

    def test_is_edge__2(self):
        graph = SimpleGraph(5)
        result = graph.IsEdge(0, 0)
        self.assertFalse(result)

    def test_is_edge__3(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddEdge(0, 0)
        result = graph.IsEdge(0, 0)
        self.assertTrue(result)

    def test_is_edge__4(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddEdge(0, 2)
        result = graph.IsEdge(2, 0)
        self.assertTrue(result)

    def test_is_edge__5(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddEdge(1, 2)
        result = graph.IsEdge(1, 2)
        self.assertTrue(result)

    def test_is_edge__6(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddEdge(1, 2)
        result = graph.IsEdge(2, 1)
        self.assertTrue(result)


class TestAddEdge(unittest.TestCase):
    def test_add_edge__1(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddEdge(0, 1)
        result = graph.m_adjacency[0][1]
        self.assertEqual(1, result)

    def test_add_edge__2(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddEdge(0, 1)
        result = graph.m_adjacency[1][0]
        self.assertEqual(1, result)


    def test_add_edge__3(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddEdge(0, 2)
        graph.AddEdge(2, 1)
        result = graph.m_adjacency[1][2]
        self.assertEqual(1, result)

    def test_add_edge__4(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddEdge(0, 2)
        graph.AddEdge(2, 1)
        result = graph.m_adjacency[2][1]
        self.assertEqual(1, result)


class TestRemoveEdge(unittest.TestCase):
    def test_remove_edge__1(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddEdge(0, 2)
        graph.AddEdge(2, 1)
        graph.RemoveEdge(0, 2)
        result = graph.m_adjacency[0][2]
        self.assertEqual(0, result)

    def test_remove_edge__2(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddEdge(0, 2)
        graph.AddEdge(2, 1)
        graph.RemoveEdge(0, 2)
        result = graph.m_adjacency[2][0]
        self.assertEqual(0, result)

    def test_remove_edge__3(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddEdge(0, 2)
        graph.AddEdge(2, 1)
        graph.RemoveEdge(0, 2)
        graph.RemoveEdge(1, 2)
        result = graph.m_adjacency[1][2]
        self.assertEqual(0, result)

    def test_remove_edge__4(self):
        graph = SimpleGraph(5)
        graph.AddVertex(1)
        graph.AddVertex(2)
        graph.AddVertex(3)
        graph.AddEdge(0, 2)
        graph.AddEdge(2, 1)
        graph.RemoveEdge(0, 2)
        graph.RemoveEdge(1, 2)
        result = graph.m_adjacency[2][1]
        self.assertEqual(0, result)


class TestDepthFirstSearch(unittest.TestCase):
    def test_depth_first_search__1(self):
        graph_mocks_generator = GraphMocksGeneratorType0()
        graph = graph_mocks_generator.generate_graph()
        result = [i.Value for i in graph.DepthFirstSearch(1, 2)]
        self.assertEqual(result, [1, 2])

    def test_depth_first_search__2(self):
        graph_mocks_generator = GraphMocksGeneratorType0()
        graph = graph_mocks_generator.generate_graph()
        result = [i.Value for i in graph.DepthFirstSearch(0, 1)]
        self.assertEqual(result, [0, 2, 1])

    def test_depth_first_search__3(self):
        graph_mocks_generator = GraphMocksGeneratorType0()
        graph = graph_mocks_generator.generate_graph()
        result = [i.Value for i in graph.DepthFirstSearch(1, 0)]
        self.assertEqual(result, [1, 2, 0])

    def test_depth_first_search__4(self):
        graph_mocks_generator = GraphMocksGeneratorType0()
        graph = graph_mocks_generator.generate_graph()
        result = [i.Value for i in graph.DepthFirstSearch(1, 1)]
        self.assertEqual(result, [1])

    def test_depth_first_search__5(self):
        graph_mocks_generator = GraphMocksGeneratorType0()
        graph = graph_mocks_generator.generate_graph()
        result = [i.Value for i in graph.DepthFirstSearch(0, 0)]
        self.assertEqual(result, [0])

    def test_depth_first_search__6(self):
        graph_mocks_generator = GraphMocksGeneratorType1()
        graph = graph_mocks_generator.generate_graph()
        result = [i.Value for i in graph.DepthFirstSearch(0, 4)]
        self.assertEqual(result, [0, 2, 1, 4])

    def test_depth_first_search__7(self):
        graph_mocks_generator = GraphMocksGeneratorType1()
        graph = graph_mocks_generator.generate_graph()
        result = [i.Value for i in graph.DepthFirstSearch(4, 3)]
        self.assertEqual(result, [4, 1, 3])

    def test_depth_first_search__8(self):
        graph_mocks_generator = GraphMocksGeneratorType1()
        graph = graph_mocks_generator.generate_graph()
        result = [i.Value for i in graph.DepthFirstSearch(3, 0)]
        self.assertEqual(result, [3, 1, 2, 0])

    def test_depth_first_search__9(self):
        graph_mocks_generator = GraphMocksGeneratorType2()
        graph = graph_mocks_generator.generate_graph()
        result = [i.Value for i in graph.DepthFirstSearch(0, 1)]
        self.assertEqual(result, [0, 2, 3, 1])

    def test_depth_first_search__10(self):
        graph_mocks_generator = GraphMocksGeneratorType2()
        graph = graph_mocks_generator.generate_graph()
        result = [i.Value for i in graph.DepthFirstSearch(0, 4)]
        self.assertEqual(result, [0, 2, 3, 1, 4])

    def test_depth_first_search__11(self):
        graph_mocks_generator = GraphMocksGeneratorType2()
        graph = graph_mocks_generator.generate_graph()
        result = [i.Value for i in graph.DepthFirstSearch(7, 8)]
        self.assertEqual(result, [])

    def test_depth_first_search__12(self):
        graph_mocks_generator = GraphMocksGeneratorType2()
        graph = graph_mocks_generator.generate_graph()
        result = [i.Value for i in graph.DepthFirstSearch(1, 0)]
        self.assertEqual(result, [1, 3, 2, 0])
