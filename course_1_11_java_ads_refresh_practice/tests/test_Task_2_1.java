import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedList2FirstAdditionTest {

    @Test
    public void testReverseWithListOfEmptyNodes() {
        LinkedList2FirstAddition list = new LinkedList2FirstAddition();

        list.reverse();

        assertNull(list.head);
        assertNull(list.tail);

    }

    @Test
    public void testReverseWithListOfSingleNode() {
        LinkedList2FirstAddition list = new LinkedList2FirstAddition();

        Node node1 = new Node(1);

        list.addInTail(node1);

        list.reverse();

        assertEquals(list.head, node1);
        assertEquals(list.tail, node1);
        assertNull(list.head.next);
        assertNull(list.head.prev);
        assertNull(list.tail.next);
        assertNull(list.tail.prev);

    }

    @Test
    public void testReverseWithListOfTwoNodes() {
        LinkedList2FirstAddition list = new LinkedList2FirstAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);

        list.reverse();

        assertEquals(list.head, node2);
        assertEquals(list.tail, node1);
        assertEquals(list.head.next, node1);
        assertNull(list.head.prev);
        assertNull(list.tail.next);
        assertEquals(list.tail.prev, node2);

    }

    @Test
    public void testReverseWithListOfSeveralNodes() {
        LinkedList2FirstAddition list = new LinkedList2FirstAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        list.reverse();

        assertEquals(list.head, node4);
        assertEquals(list.tail, node1);
        assertEquals(list.head.next, node3);
        assertNull(list.head.prev);
        assertNull(list.tail.next);
        assertEquals(list.tail.prev, node2);

    }


}

