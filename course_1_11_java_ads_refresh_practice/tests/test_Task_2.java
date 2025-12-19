import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

public class LinkedList2Test {

    @Test
    public void testAddInTailWithEmptyList() {
        LinkedList2 list = new LinkedList2();

        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testAddInTailWithListOfSingleNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);

        list.addInTail(node1);

        assertEquals(1, list.count());
        assertEquals(1, list.head.value);
        assertEquals(1, list.tail.value);
        assertNull(list.tail.next);
        assertNull(list.tail.prev);
        assertNull(list.head.next);
        assertNull(list.head.prev);
    }

    @Test
    public void testAddInTailWithListOfSeveralSNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(99);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        assertEquals(4, list.count());
        assertEquals(1, list.head.value);
        assertEquals(99, list.tail.value);
        assertNull(list.tail.next);
        assertEquals(list.tail.prev, node3);
        assertEquals(list.head.next, node2);
        assertNull(list.head.prev);
        assertEquals(node2.prev, list.head);
        assertEquals(node2.next, node3);
        assertEquals(node3.next, list.tail);
        assertEquals(node3.prev, node2);
    }

    @Test
    public void testFindlWithEmptyList() {
        LinkedList2 list = new LinkedList2();

        Node foundedNode = list.find(1);

        assertEquals(0, list.count());
        assertNull(foundedNode);
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testFindlWithListOfSingleNodeAndExistingNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);

        list.addInTail(node1);

        int valueToFound = 1;
        Node foundedNode = list.find(valueToFound);

        assertEquals(1, list.count());
        assertEquals(1, list.head.value);
        assertEquals(1, list.tail.value);
        assertEquals(valueToFound, foundedNode.value);
        assertNull(list.tail.next);
        assertNull(list.tail.prev);
        assertNull(list.head.next);
        assertNull(list.head.prev);
    }

    @Test
    public void testFindlWithListOfSingleNodeAndNotExistingNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);

        list.addInTail(node1);

        int valueToFound = 99;
        Node foundedNode = list.find(valueToFound);

        assertEquals(1, list.count());
        assertEquals(1, list.head.value);
        assertEquals(1, list.tail.value);
        assertNull(foundedNode);
        assertNull(list.tail.next);
        assertNull(list.tail.prev);
        assertNull(list.head.next);
        assertNull(list.head.prev);
    }

    @Test
    public void testFindlWithListOfSeveralSNodeAndExistingValueWhenLookupNodeIsTailNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        int valueToFound = 4;
        Node foundedNode = list.find(valueToFound);

        assertEquals(4, list.count());
        assertEquals(4, foundedNode.value);
        assertEquals(1, list.head.value);
        assertEquals(4, list.tail.value);

        assertNull(list.tail.next);
        assertEquals(list.tail.prev, node3);
        assertEquals(list.head.next, node2);
        assertNull(list.head.prev);
    }

    @Test
    public void testFindlWithListOfSeveralSNodeAndExistingValueWhenLookupNodeIsHeadNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        int valueToFound = 1;
        Node foundedNode = list.find(valueToFound);

        assertEquals(4, list.count());
        assertEquals(1, foundedNode.value);
        assertEquals(1, list.head.value);
        assertEquals(4, list.tail.value);
        assertNull(list.tail.next);
        assertEquals(list.tail.prev, node3);
        assertEquals(list.head.next, node2);
        assertNull(list.head.prev);
    }

    @Test
    public void testFindlWithListOfSeveralSNodeAndExistingValueWhenLookupNodeIsNotHeadOrTailNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        int valueToFound = 2;
        Node foundedNode = list.find(valueToFound);

        assertEquals(4, list.count());
        assertEquals(2, foundedNode.value);
        assertEquals(1, list.head.value);
        assertEquals(4, list.tail.value);
        assertNull(list.tail.next);
        assertEquals(list.tail.prev, node3);
        assertEquals(list.head.next, node2);
        assertNull(list.head.prev);
    }

    @Test
    public void testFindlWithListOfSeveralSNodeAndExistingValueWhenLookupNodeIsMiddleNodeAndTailNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(4);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        int valueToFound = 4;
        Node foundedNode = list.find(valueToFound);

        assertEquals(4, list.count());
        assertEquals(node3, foundedNode);
        assertEquals(1, list.head.value);
        assertEquals(4, list.tail.value);
        assertNull(list.tail.next);
        assertEquals(list.tail.prev, node3);
        assertEquals(list.head.next, node2);
        assertNull(list.head.prev);
    }

    @Test
    public void testFindlAllWithEmptyList() {
        LinkedList2 list = new LinkedList2();
        int valueToFound = 1;
        ArrayList<Node> foundedNodes = list.findAll(valueToFound);

        assertEquals(0, list.count());
        assertTrue(foundedNodes.isEmpty());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testFindAllWithListOfSingleNodeAndExistingNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);

        list.addInTail(node1);

        int valueToFound = 1;
        ArrayList<Node> foundedNodes = list.findAll(valueToFound);
        ArrayList<Node> expected = new ArrayList<>(List.of(node1));

        assertEquals(1, list.count());
        assertEquals(expected, foundedNodes);
        assertEquals(1, list.head.value);
        assertEquals(1, list.tail.value);
        assertNull(list.tail.next);
        assertNull(list.tail.prev);
        assertNull(list.head.next);
        assertNull(list.head.prev);
    }

    @Test
    public void testFindAllWithListOfSingleNodeAndNotExistingNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);

        list.addInTail(node1);

        int valueToFound = 99;
        ArrayList<Node> foundedNodes = list.findAll(valueToFound);
        ArrayList<Node> expected = new ArrayList<>(List.of());

        assertEquals(1, list.count());
        assertEquals(expected, foundedNodes);
        assertEquals(1, list.head.value);
        assertEquals(1, list.tail.value);
        assertNull(list.tail.next);
        assertNull(list.tail.prev);
        assertNull(list.head.next);
        assertNull(list.head.prev);
    }

    @Test
    public void testFindAllWithListOfSeveralNodesAndExistingNodes() {
        LinkedList2 list = new LinkedList2();

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
        assertNull(list.tail.next);
        assertEquals(list.tail.prev, node5);
        assertEquals(list.head.next, node2);
        assertNull(list.head.prev);
    }

    @Test
    public void testFindAllWithListOfSeveralNodesAndNotExistingNodes() {
        LinkedList2 list = new LinkedList2();

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
        assertNull(list.tail.next);
        assertEquals(list.tail.prev, node5);
        assertEquals(list.head.next, node2);
        assertNull(list.head.prev);
    }

    @Test
    public void testRemoveWithEmptyList() {
        LinkedList2 list = new LinkedList2();

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
        LinkedList2 list = new LinkedList2();

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
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);

        list.addInTail(node1);

        int valueToRemove = 99;
        boolean isNodeRemoved = list.remove(valueToRemove);

        assertEquals(1, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertFalse(isNodeRemoved);
        assertEquals(node1, list.head);
        assertEquals(node1, list.tail);
        assertNull(list.tail.next);
        assertNull(list.tail.prev);
        assertNull(list.head.next);
        assertNull(list.head.prev);
    }

    @Test
    public void testRemoveWithListOfSeveralNodesAndOneExistingValueAsHeadNode() {
        LinkedList2 list = new LinkedList2();

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
        assertNull(list.head.prev);
        assertEquals(list.head.next, node3);
    }

    @Test
    public void testRemoveWithListOfSeveralNodesAndOneExistingValueAsTailNode() {
        LinkedList2 list = new LinkedList2();

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
        assertNull(list.tail.next);
        assertEquals(list.tail.prev, node5);
    }

    @Test
    public void testRemoveWithListOfSeveralNodesAndOneExistingValueAsPreTailNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(99);
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
        assertEquals(list.tail.prev, node5);
    }


    @Test
    public void testRemoveWithListOfSeveralNodesAndOneExistingValueAsPostHeadNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);
        Node node2 = new Node(99);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
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
        assertEquals(list.head.next, node3);
    }

    @Test
    public void testRemoveWithListOfSeveralNodesAndOneExistingValueAsMiddleNode() {
        LinkedList2 list = new LinkedList2();

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
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(-1);
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
        assertEquals(1, list.findAll(valueToRemove).size());
        assertTrue(isNodeRemoved);
        assertEquals(node1, list.head);
        assertEquals(node7, list.tail);
        assertEquals(list.tail.prev, node5);
    }

    @Test
    public void testRemoveAllWithEmptyList() {
        LinkedList2 list = new LinkedList2();

        int valueToRemove = 99;
        list.removeAll(valueToRemove);

        assertEquals(0, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveAllWithListOfSingleNodeAndExistingNodes() {
        LinkedList2 list = new LinkedList2();

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
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);

        list.addInTail(node1);

        int valueToRemove = 99;
        list.removeAll(valueToRemove);

        assertEquals(1, list.count());
        assertEquals(0, list.findAll(valueToRemove).size());
        assertEquals(node1, list.head);
        assertEquals(node1, list.tail);
        assertNull(list.tail.next);
        assertNull(list.tail.prev);
        assertNull(list.head.next);
        assertNull(list.head.prev);
    }

    @Test
    public void testRemoveAllWithListOfSeveralNodesAndOneExistingValueAsHeadNode() {
        LinkedList2 list = new LinkedList2();

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
        assertNull(list.head.prev);
        assertEquals(list.head.next, node3);
    }

    @Test
    public void testRemoveAllWithListOfSeveralNodesAndOneExistingValueAsTailNode() {
        LinkedList2 list = new LinkedList2();

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
        assertNull(list.tail.next);
        assertEquals(list.tail.prev, node5);
    }

    @Test
    public void testRemoveAllWithListOfSeveralNodesAndOneExistingValueAsPreTailNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(99);
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
        assertEquals(list.tail.prev, node5);
    }

    @Test
    public void testRemoveAllWithListOfSeveralNodesAndOneExistingValueAsPostHeadNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);
        Node node2 = new Node(99);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
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
        assertEquals(list.head.next, node3);
    }

    @Test
    public void testRemoveAllWithListOfSeveralNodesAndOneExistingValueAsMiddleNode() {
        LinkedList2 list = new LinkedList2();

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
        LinkedList2 list = new LinkedList2();

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
        assertEquals(list.head, node3);
        assertEquals(list.tail, node5);
        assertEquals(list.tail.prev, node3);
        assertNull(list.tail.next);
        assertEquals(list.head.next, node5);
        assertNull(list.head.prev);
    }

    @Test
    public void testClearWithEmptyList() {
        LinkedList2 list = new LinkedList2();
        list.clear();

        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearWithListOfSingleNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(99);

        list.addInTail(node1);

        list.clear();

        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearWithListOfSeveralNodes() {
        LinkedList2 list = new LinkedList2();

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
        LinkedList2 list = new LinkedList2();

        int nodeCount = list.count();

        assertEquals(0, nodeCount);
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testCountWithListOfSingleNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(99);

        list.addInTail(node1);

        int nodeCount = list.count();

        assertEquals(1, nodeCount);
        assertEquals(node1, list.head);
        assertEquals(node1, list.tail);
    }

    @Test
    public void testCountWithListOfSeveralNodes() {
        LinkedList2 list = new LinkedList2();

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
        LinkedList2 list = new LinkedList2();

        Node nodeToInsert = new Node(99);

        list.insertAfter(null, nodeToInsert);

        assertEquals(1, list.count());
        assertEquals(nodeToInsert, list.head);
        assertEquals(nodeToInsert, list.tail);
    }

    @Test
    public void testInsertAfterWithListOfSingleNodeAsHeadNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);

        list.addInTail(node1);

        Node nodeToInsert = new Node(99);
        list.insertAfter(null, nodeToInsert);

        assertEquals(2, list.count());
        assertEquals(list.head, nodeToInsert);
        assertEquals(list.tail, node1);
        assertEquals(list.head.next, node1);
        assertNull(list.head.prev);
        assertEquals(list.tail.prev, nodeToInsert);
        assertNull(list.tail.next);

    }

    @Test
    public void testInsertAfterWithListOfSingleNodeAsTailNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);

        list.addInTail(node1);

        Node nodeToInsert = new Node(99);
        list.insertAfter(node1, nodeToInsert);

        assertEquals(2, list.count());
        assertEquals(node1, list.head);
        assertEquals(nodeToInsert, list.tail);
        assertEquals(list.tail, nodeToInsert);
        assertEquals(list.head.next, nodeToInsert);
        assertNull(list.head.prev);
        assertEquals(list.tail.prev, node1);
        assertNull(list.tail.next);
    }

    @Test
    public void testInsertAfterWithListOfSeveralNodesAsHeadNode() {
        LinkedList2 list = new LinkedList2();

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
        assertEquals(nodeToInsert.next, node1);
        assertEquals(node4, list.tail);
        assertEquals(list.head.next, node1);
        assertNull(list.head.prev);
        assertEquals(list.tail.prev, node3);
        assertNull(list.tail.next);
    }

    @Test
    public void testInsertAfterWithListOfSeveralNodesAsTailNode() {
        LinkedList2 list = new LinkedList2();

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
        assertEquals(nodeToInsert.prev, node4);
        assertEquals(list.head.next, node2);
        assertNull(list.head.prev);
        assertEquals(list.tail.prev, node4);
        assertNull(list.tail.next);
    }

    @Test
    public void testInsertAfterWithListOfSeveralNodesAsMiddleNode() {
        LinkedList2 list = new LinkedList2();

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
        assertEquals(nodeToInsert.prev, node2);
        assertEquals(nodeToInsert.next, node3);
    }

    @Test
    public void testInsertAfterWithListOfSeveralNodesAsPreTailNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        Node nodeToInsert = new Node(99);
        list.insertAfter(node3, nodeToInsert);

        assertEquals(5, list.count());
        assertEquals(node1, list.head);
        assertEquals(node4, list.tail);
        assertEquals(nodeToInsert.prev, node3);
        assertEquals(nodeToInsert.next, node4);
        assertEquals(list.tail.prev, nodeToInsert);
        assertNull(list.tail.next);
    }

    @Test
    public void testInsertAfterWithListOfSeveralNodesAsPostHeadNode() {
        LinkedList2 list = new LinkedList2();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        Node nodeToInsert = new Node(99);
        list.insertAfter(node1, nodeToInsert);

        assertEquals(5, list.count());
        assertEquals(node1, list.head);
        assertEquals(node4, list.tail);
        assertEquals(nodeToInsert.prev, node1);
        assertEquals(nodeToInsert.next, node2);
        assertEquals(list.head.next, nodeToInsert);
        assertNull(list.head.prev);
    }
}

