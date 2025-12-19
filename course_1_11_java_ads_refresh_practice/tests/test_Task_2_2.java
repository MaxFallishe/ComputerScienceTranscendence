import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedList2SecondAdditionTest {

    @Test
    public void testIsLoopedWithEmptyList() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        boolean result = list.isLooped();
        assertFalse(result);
    }

    @Test
    public void testIsLoopedWithSingleNodeList() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        Node node1 = new Node(1);

        list.addInTail(node1);

        boolean result = list.isLooped();
        assertFalse(result);
    }

    @Test
    public void testIsLoopedWithSingleNodeListWithPrevLoop() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        Node node1 = new Node(1);

        list.addInTail(node1);
        node1.prev = node1;

        boolean result = list.isLooped();
        assertTrue(result);
    }

    @Test
    public void testIsLoopedWithSingleNodeListWithNextLoop() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        Node node1 = new Node(1);

        list.addInTail(node1);
        node1.next = node1;

        boolean result = list.isLooped();
        assertTrue(result);
    }

    @Test
    public void testIsLoopedWithTwoNodesList() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);

        boolean result = list.isLooped();
        assertFalse(result);
    }

    @Test
    public void testIsLoopedWithTwoNodesListWithPreLoop() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);
        node1.prev = node2;

        boolean result = list.isLooped();
        assertTrue(result);
    }


    @Test
    public void testIsLoopedWithTwoNodesListWithNextLoop() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);
        node2.next = node1;

        boolean result = list.isLooped();
        assertTrue(result);
    }

    @Test
    public void testIsLoopedWithThreeNodesList() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        boolean result = list.isLooped();
        assertFalse(result);
    }

    @Test
    public void testIsLoopedWithThreeNodesListWithMiddleNextLoop() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        node1.next = node3;

        boolean result = list.isLooped();
        assertTrue(result);
    }


    @Test
    public void testIsLoopedWithThreeNodesListWithMiddlePreLoop() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        node3.prev = node3;

        boolean result = list.isLooped();
        assertTrue(result);
    }

    @Test
    public void testIsLoopedWithListOfSeveralNodes() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        boolean result = list.isLooped();
        assertFalse(result);
    }


    @Test
    public void testIsLoopedWithListOfSeveralNodesWithMiddleLoop() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        node2.prev = node3;
        node3.next = node2;

        boolean result = list.isLooped();
        assertTrue(result);
    }

    @Test
    public void testIsLoopedWithListOfSeveralNodesAndLoopInMiddleNodes() {
        LinkedList2SecondAddition list = new LinkedList2SecondAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        node3.next = node2;

        boolean result = list.isLooped();
        assertTrue(result);
    }
}

