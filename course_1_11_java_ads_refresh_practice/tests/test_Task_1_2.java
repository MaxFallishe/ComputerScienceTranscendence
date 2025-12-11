// Task number: 1.7
// Short description: Unittests for LinkedList class functionality
// Time complexity: -
// Space Complexity: -

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class LinkedListFirstAdditionTest {

    @Test
    public void testOverlapWithSummationWithEmptyLists() {
        LinkedList listX = new LinkedList();
        listX.addInTail(new Node(1));

        LinkedList listY = new LinkedList();
        listY.addInTail(new Node(10));

        LinkedList overlappedList = LinkedListFirstAddition.overlapWithSummation(listX, listY);

        assertEquals(11, overlappedList.head.value);
        assertEquals(11, overlappedList.tail.value);
    }

    @Test
    public void testOverlapWithSummationWithListsOfSingleValue() {
        LinkedList listX = new LinkedList();
        LinkedList listY = new LinkedList();
        LinkedList overlappedList = LinkedListFirstAddition.overlapWithSummation(listX, listY);

        assertNull(overlappedList.head);
        assertNull(overlappedList.tail);
    }

    @Test
    public void testOverlapWithSummationWithListsOfSeveralNodes() {
        LinkedList listX = new LinkedList();
        listX.addInTail(new Node(1));
        listX.addInTail(new Node(2));
        listX.addInTail(new Node(3));

        LinkedList listY = new LinkedList();
        listY.addInTail(new Node(10));
        listY.addInTail(new Node(20));
        listY.addInTail(new Node(30));

        LinkedList overlappedList = LinkedListFirstAddition.overlapWithSummation(listX, listY);
        Node overlappedListNode1 = overlappedList.head;
        Node overlappedListNode2 = overlappedListNode1.next;
        Node overlappedListNode3 = overlappedListNode2.next;

        assertEquals(3, overlappedList.count());
        assertEquals(11, overlappedListNode1.value);
        assertEquals(22, overlappedListNode2.value);
        assertEquals(33, overlappedListNode3.value);

        assertEquals(11, overlappedList.head.value);
        assertEquals(33, overlappedList.tail.value);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testOverlapWithSummationWithListsOfSeveralNodesButListsHasDifferentSizes() {
        LinkedList listX = new LinkedList();
        listX.addInTail(new Node(1));
        listX.addInTail(new Node(2));
        listX.addInTail(new Node(3));
        listX.addInTail(new Node(4));

        LinkedList listY = new LinkedList();
        listY.addInTail(new Node(10));
        listY.addInTail(new Node(20));
        listY.addInTail(new Node(30));

        LinkedList overlappedList = LinkedListFirstAddition.overlapWithSummation(listX, listY);

    }

}

