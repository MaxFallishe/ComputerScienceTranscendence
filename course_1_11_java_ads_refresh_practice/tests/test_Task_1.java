import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

public class LinkedListTest {

    @Test
    public void testAddInTailWithEmptyList() {
        LinkedList list = new LinkedList();

        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testAddInTailWithListOfSingleNode() {
        LinkedList list = new LinkedList();
        list.addInTail(new Node(1));

        assertEquals(1, list.count());
        assertEquals(1, list.head.value);
        assertEquals(1, list.tail.value);
    }

    @Test
    public void testAddInTailWithListOfSeveralSNode() {
        LinkedList list = new LinkedList();
        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));
        list.addInTail(new Node(99));

        assertEquals(4, list.count());
        assertEquals(1, list.head.value);
        assertEquals(99, list.tail.value);
    }

    @Test
    public void testFindlWithEmptyList() {
        LinkedList list = new LinkedList();
        Node foundedNode = list.find(1);

        assertEquals(0, list.count());
        assertNull(foundedNode);
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testFindlWithListOfSingleNodeAndExistingNode() {
        LinkedList list = new LinkedList();
        list.addInTail(new Node(1));
        int valueToFound = 1;
        Node foundedNode = list.find(valueToFound);

        assertEquals(1, list.count());
        assertEquals(valueToFound, foundedNode.value);
        assertEquals(1, list.head.value);
        assertEquals(1, list.tail.value);
    }

    @Test
    public void testFindlWithListOfSingleNodeAndNotExistingNode() {
        LinkedList list = new LinkedList();
        list.addInTail(new Node(1));
        int valueToFound = 99;
        Node foundedNode = list.find(valueToFound);

        assertEquals(1, list.count());
        assertNull(foundedNode);
        assertEquals(1, list.head.value);
        assertEquals(1, list.tail.value);
    }

    @Test
    public void testFindlWithListOfSeveralSNodeAndExistingValueWhenLookupNodeIsTailNode() {
        LinkedList list = new LinkedList();
        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));
        list.addInTail(new Node(4));
        int valueToFound = 4;
        Node foundedNode = list.find(valueToFound);

        assertEquals(4, list.count());
        assertEquals(4, foundedNode.value);
        assertEquals(1, list.head.value);
        assertEquals(4, list.tail.value);
    }

    @Test
    public void testFindlWithListOfSeveralSNodeAndExistingValueWhenLookupNodeIsHeadNode() {
        LinkedList list = new LinkedList();
        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));
        list.addInTail(new Node(4));
        int valueToFound = 1;
        Node foundedNode = list.find(valueToFound);

        assertEquals(4, list.count());
        assertEquals(1, foundedNode.value);
        assertEquals(1, list.head.value);
        assertEquals(4, list.tail.value);
    }

    @Test
    public void testFindlWithListOfSeveralSNodeAndExistingValueWhenLookupNodeIsNotHeadOrTailNode() {
        LinkedList list = new LinkedList();
        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));
        list.addInTail(new Node(4));
        int valueToFound = 2;
        Node foundedNode = list.find(valueToFound);

        assertEquals(4, list.count());
        assertEquals(2, foundedNode.value);
        assertEquals(1, list.head.value);
        assertEquals(4, list.tail.value);
    }

    @Test
    public void testFindlAllWithEmptyList() {
        LinkedList list = new LinkedList();
        int valueToFound = 1;
        ArrayList<Node> foundedNodes = list.findAll(valueToFound);

        assertEquals(0, list.count());
        assertTrue(foundedNodes.isEmpty());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testFindAllWithListOfSingleNodeAndExistingNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        list.addInTail(node1);
        int valueToFound = 1;
        ArrayList<Node> foundedNodes = list.findAll(valueToFound);
        ArrayList<Node> expected = new ArrayList<>(List.of(node1));

        assertEquals(1, list.count());
        assertEquals(expected, foundedNodes);
        assertEquals(1, list.head.value);
        assertEquals(1, list.tail.value);
    }

    @Test
    public void testFindAllWithListOfSingleNodeAndNotExistingNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        list.addInTail(node1);
        int valueToFound = 99;
        ArrayList<Node> foundedNodes = list.findAll(valueToFound);
        ArrayList<Node> expected = new ArrayList<>(List.of());

        assertEquals(1, list.count());
        assertEquals(expected, foundedNodes);
        assertEquals(1, list.head.value);
        assertEquals(1, list.tail.value);
    }

    @Test
    public void testFindAllWithListOfSeveralNodesAndExistingNodes() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(99);
        Node node2 = new Node(99);
        Node node3 = new Node(3);
        Node node4 = new Node(99);
        Node node5 = new Node(5);
        Node node6 = new Node(99);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        list.addInTail(node6);
        int valueToFound = 99;
        ArrayList<Node> foundedNodes = list.findAll(valueToFound);
        ArrayList<Node> expected = new ArrayList<>(List.of(node1, node2, node4, node6));

        assertEquals(6, list.count());
        assertEquals(expected, foundedNodes);
        assertEquals(99, list.head.value);
        assertEquals(99, list.tail.value);
    }


    @Test
    public void testFindAllWithListOfSeveralNodesAndNotExistingNodes() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        list.addInTail(node6);
        int valueToFound = 99;
        ArrayList<Node> foundedNodes = list.findAll(valueToFound);
        ArrayList<Node> expected = new ArrayList<>(List.of());

        assertEquals(6, list.count());
        assertEquals(expected, foundedNodes);
        assertEquals(1, list.head.value);
        assertEquals(6, list.tail.value);
    }

    @Test
    public void testRemoveWithEmptyList() {
        LinkedList list = new LinkedList();
        int valueToRemove = 99;
        boolean isNodeRemoved = list.remove(valueToRemove);

        assertEquals(0, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertFalse(isNodeRemoved);
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveWithListOfSingleNodeAndExistingNodes() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        list.addInTail(node1);
        int valueToRemove = 1;
        boolean isNodeRemoved = list.remove(valueToRemove);

        assertEquals(0, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertTrue(isNodeRemoved);
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveWithListOfSingleNodeAndNotExistingNodes() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        list.addInTail(node1);
        int valueToRemove = 99;
        boolean isNodeRemoved = list.remove(valueToRemove);

        assertEquals(1, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertFalse(isNodeRemoved);
        assertEquals(node1, list.head);
        assertEquals(node1, list.tail);
    }

    @Test
    public void testRemoveWithListOfSeveralNodesAndOneExistingValueAsHeadNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(99);
        Node node2 = new Node(0);
        Node node3 = new Node(1);
        Node node4 = new Node(2);
        Node node5 = new Node(3);
        Node node6 = new Node(4);
        Node node7 = new Node(5);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        list.addInTail(node6);
        list.addInTail(node7);
        int valueToRemove = 99;
        boolean isNodeRemoved = list.remove(valueToRemove);

        assertEquals(6, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertTrue(isNodeRemoved);
        assertEquals(node2, list.head);
        assertEquals(node7, list.tail);
    }


    @Test
    public void testRemoveWithListOfSeveralNodesAndOneExistingValueAsTailNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(99);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        list.addInTail(node6);
        list.addInTail(node7);
        int valueToRemove = 99;
        boolean isNodeRemoved = list.remove(valueToRemove);

        assertEquals(6, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertTrue(isNodeRemoved);
        assertEquals(node1, list.head);
        assertEquals(node6, list.tail);
    }

    @Test
    public void testRemoveWithListOfSeveralNodesAndOneExistingValueAsMiddleNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(99);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        list.addInTail(node6);
        list.addInTail(node7);
        int valueToRemove = 99;
        boolean isNodeRemoved = list.remove(valueToRemove);

        assertEquals(6, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertTrue(isNodeRemoved);
        assertEquals(node1, list.head);
        assertEquals(node7, list.tail);
    }


    @Test
    public void testRemoveWithListOfSeveralNodesAndSeveralExistingNodes() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(99);
        Node node2 = new Node(0);
        Node node3 = new Node(1);
        Node node4 = new Node(2);
        Node node5 = new Node(3);
        Node node6 = new Node(99);
        Node node7 = new Node(99);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        list.addInTail(node6);
        list.addInTail(node7);
        int valueToRemove = 99;
        boolean isNodeRemoved = list.remove(valueToRemove);

        assertEquals(6, list.count());
        assertEquals(2, list.findAll(valueToRemove).size());
        assertTrue(isNodeRemoved);
        assertEquals(node2, list.head);
        assertEquals(node7, list.tail);
    }

    @Test
    public void testRemoveAllWithListOfEmptyList() {
        LinkedList list = new LinkedList();
        int valueToRemove = 99;
        list.removeAll(valueToRemove);

        assertEquals(0, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveAllWithListOfSingleNodeAndExistingNodes() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        list.addInTail(node1);
        int valueToRemove = 1;
        list.removeAll(valueToRemove);

        assertEquals(0, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveAllWithListOfSingleNodeAndNotExistingNodes() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        list.addInTail(node1);
        int valueToRemove = 99;
        list.removeAll(valueToRemove);

        assertEquals(1, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertEquals(node1, list.head);
        assertEquals(node1, list.tail);
    }

    @Test
    public void testRemoveAllWithListOfSeveralNodesAndOneExistingValueAsHeadNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(99);
        Node node2 = new Node(0);
        Node node3 = new Node(1);
        Node node4 = new Node(2);
        Node node5 = new Node(3);
        Node node6 = new Node(4);
        Node node7 = new Node(5);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        list.addInTail(node6);
        list.addInTail(node7);
        int valueToRemove = 99;
        list.removeAll(valueToRemove);

        assertEquals(6, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertEquals(node2, list.head);
        assertEquals(node7, list.tail);
    }

    @Test
    public void testRemoveAllWithListOfSeveralNodesAndOneExistingValueAsTailNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(99);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        list.addInTail(node6);
        list.addInTail(node7);
        int valueToRemove = 99;
        list.removeAll(valueToRemove);

        assertEquals(6, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertEquals(node1, list.head);
        assertEquals(node6, list.tail);
    }

    @Test
    public void testRemoveAllWithListOfSeveralNodesAndOneExistingValueAsMiddleNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(99);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        list.addInTail(node6);
        list.addInTail(node7);
        int valueToRemove = 99;
        list.removeAll(valueToRemove);

        assertEquals(6, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertEquals(node1, list.head);
        assertEquals(node7, list.tail);
    }

    @Test
    public void testRemoveAllWithListOfSeveralNodesAndSeveralExistingNodes() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(99);
        Node node2 = new Node(99);
        Node node3 = new Node(1);
        Node node4 = new Node(99);
        Node node5 = new Node(3);
        Node node6 = new Node(99);
        Node node7 = new Node(99);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        list.addInTail(node6);
        list.addInTail(node7);
        int valueToRemove = 99;
        list.removeAll(valueToRemove);

        assertEquals(2, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertEquals(node3, list.head);
        assertEquals(node5, list.tail);
    }

    @Test
    public void testClearWithEmptyList() {
        LinkedList list = new LinkedList();
        list.clear();

        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearWithListOfSingleNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(99);
        list.addInTail(node1);
        list.clear();

        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearWithListOfSeveralNodes() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.clear();

        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testCountWithEmptyList() {
        LinkedList list = new LinkedList();
        int nodeCount = list.count();

        assertEquals(0, nodeCount);
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testCountWithListOfSingleNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(99);
        list.addInTail(node1);
        int nodeCount = list.count();

        assertEquals(1, nodeCount);
        assertEquals(node1, list.head);
        assertEquals(node1, list.tail);
    }

    @Test
    public void testCountWithListOfSeveralNodes() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(99);
        Node node2 = new Node(99);
        Node node3 = new Node(99);
        Node node4 = new Node(99);
        Node node5 = new Node(99);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        int nodeCount = list.count();

        assertEquals(5, nodeCount);
        assertEquals(node1, list.head);
        assertEquals(node5, list.tail);
    }

    @Test
    public void testInsertAfterWithEmptyList() {
        LinkedList list = new LinkedList();
        Node nodeToInsert = new Node(99);
        list.insertAfter(null, nodeToInsert);

        assertEquals(1, list.count());
        assertEquals(nodeToInsert, list.head);
        assertEquals(nodeToInsert, list.tail);
    }

    @Test
    public void testInsertAfterWithListOfSingleNodeAsHeadNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        list.addInTail(node1);
        Node nodeToInsert = new Node(99);
        list.insertAfter(null, nodeToInsert);

        assertEquals(2, list.count());
        assertEquals(nodeToInsert, list.head);
        assertEquals(node1, list.tail);
    }

    @Test
    public void testInsertAfterWithListOfSingleNodeAsTailNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        list.addInTail(node1);
        Node nodeToInsert = new Node(99);
        list.insertAfter(node1, nodeToInsert);

        assertEquals(2, list.count());
        assertEquals(node1, list.head);
        assertEquals(nodeToInsert, list.tail);
    }

    @Test
    public void testInsertAfterWithListOfSeveralNodesAsHeadNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        Node nodeToInsert = new Node(99);
        list.insertAfter(null, nodeToInsert);

        assertEquals(5, list.count());
        assertEquals(nodeToInsert, list.head);
        assertEquals(node4, list.tail);
    }

    @Test
    public void testInsertAfterWithListOfSeveralNodesAsTailNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        Node nodeToInsert = new Node(99);
        list.insertAfter(node4, nodeToInsert);

        assertEquals(5, list.count());
        assertEquals(node1, list.head);
        assertEquals(nodeToInsert, list.tail);
    }

    @Test
    public void testInsertAfterWithListOfSeveralNodesAsMiddleNode() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        Node nodeToInsert = new Node(99);
        list.insertAfter(node2, nodeToInsert);

        assertEquals(5, list.count());
        assertEquals(node1, list.head);
        assertEquals(node4, list.tail);
    }

}

