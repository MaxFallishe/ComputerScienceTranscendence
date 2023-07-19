from ..Task_8 import *
import unittest


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
