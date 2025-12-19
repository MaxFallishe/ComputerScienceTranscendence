import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LinkedList2ThirdAdditionTest {

    @Test
    public void testFilterWithListOfSeveralNodesDescOrder() {
        LinkedList2ThirdAddition list = new LinkedList2ThirdAddition();

        Node node4 = new Node(4);
        Node node3 = new Node(3);
        Node node2 = new Node(2);
        Node node1 = new Node(1);

        list.addInTail(node4);
        list.addInTail(node3);
        list.addInTail(node2);
        list.addInTail(node1);

        list.filter();
        assertEquals(list.toArr(), List.of(node1, node2, node3, node4));
        assertEquals(list.head, node1);
        assertNull(list.head.prev);
        assertEquals(list.head.next, node2);
        assertEquals(list.tail, node4);
        assertEquals(list.tail.prev, node3);
        assertNull(list.tail.next);

    }

    @Test
    public void testFilterWithListOfSeveralNodesAsceOrder() {
        LinkedList2ThirdAddition list = new LinkedList2ThirdAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        list.filter();
        assertEquals(list.toArr(), List.of(node1, node2, node3, node4));
        assertEquals(list.head, node1);
        assertNull(list.head.prev);
        assertEquals(list.head.next, node2);
        assertEquals(list.tail, node4);
        assertEquals(list.tail.prev, node3);
        assertNull(list.tail.next);

    }

    @Test
    public void testFilterWithListOfSeveralNodesRandomOrder() {
        LinkedList2ThirdAddition list = new LinkedList2ThirdAddition();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);

        list.addInTail(node7);
        list.addInTail(node6);
        list.addInTail(node4);
        list.addInTail(node3);
        list.addInTail(node2);
        list.addInTail(node5);
        list.addInTail(node1);

        list.filter();
        assertEquals(list.toArr(), List.of(node1, node2, node3, node4, node5, node6, node7));
        assertEquals(list.head, node1);
        assertNull(list.head.prev);
        assertEquals(list.head.next, node2);
        assertEquals(list.tail, node7);
        assertEquals(list.tail.prev, node6);
        assertNull(list.tail.next);

    }

}

