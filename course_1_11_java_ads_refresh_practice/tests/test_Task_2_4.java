import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LinkedList2FourthAdditionTest {

    @Test
    public void testMergeListsToWithListsOfSeveralNodes() {
        LinkedList2FourthAddition list1 = new LinkedList2FourthAddition();
        LinkedList2FourthAddition list2 = new LinkedList2FourthAddition();

        Node node8 = new Node(8);
        Node node7 = new Node(7);
        Node node6 = new Node(6);
        Node node5 = new Node(5);
        Node node4 = new Node(4);
        Node node3 = new Node(3);
        Node node2 = new Node(2);
        Node node1 = new Node(1);

        list1.addInTail(node4);
        list1.addInTail(node3);
        list1.addInTail(node2);
        list1.addInTail(node1);

        list2.addInTail(node8);
        list2.addInTail(node6);
        list2.addInTail(node7);
        list2.addInTail(node5);

        LinkedList2FourthAddition mergedList = LinkedList2FourthAddition.mergeListsTo(list1, list2);

        assertEquals(mergedList.toArr(), List.of(node1, node2, node3, node4, node5, node6, node7, node8));
        assertEquals(mergedList.head, node1);
        assertNull(mergedList.head.prev);
        assertEquals(mergedList.head.next, node2);
        assertEquals(mergedList.tail, node8);
        assertEquals(mergedList.tail.prev, node7);
        assertNull(mergedList.tail.next);

    }

    @Test
    public void testMergeListsToWithListOfSeveralNodesAndEmptyList() {
        LinkedList2FourthAddition list1 = new LinkedList2FourthAddition();
        LinkedList2FourthAddition list2 = new LinkedList2FourthAddition();

        Node node8 = new Node(8);
        Node node7 = new Node(7);
        Node node6 = new Node(6);
        Node node5 = new Node(5);
        Node node4 = new Node(4);
        Node node3 = new Node(3);
        Node node2 = new Node(2);
        Node node1 = new Node(1);

        list1.addInTail(node4);
        list1.addInTail(node3);
        list1.addInTail(node2);
        list1.addInTail(node1);
        list1.addInTail(node8);
        list1.addInTail(node6);
        list1.addInTail(node7);
        list1.addInTail(node5);

        LinkedList2FourthAddition mergedList = LinkedList2FourthAddition.mergeListsTo(list1, list2);

        assertEquals(mergedList.toArr(), List.of(node1, node2, node3, node4, node5, node6, node7, node8));
        assertEquals(mergedList.head, node1);
        assertNull(mergedList.head.prev);
        assertEquals(mergedList.head.next, node2);
        assertEquals(mergedList.tail, node8);
        assertEquals(mergedList.tail.prev, node7);
        assertNull(mergedList.tail.next);

    }

    @Test
    public void testMergeListsToWithEmptyListAndListOfSeveralNodes() {
        LinkedList2FourthAddition list1 = new LinkedList2FourthAddition();
        LinkedList2FourthAddition list2 = new LinkedList2FourthAddition();

        Node node8 = new Node(8);
        Node node7 = new Node(7);
        Node node6 = new Node(6);
        Node node5 = new Node(5);
        Node node4 = new Node(4);
        Node node3 = new Node(3);
        Node node2 = new Node(2);
        Node node1 = new Node(1);

        list2.addInTail(node4);
        list2.addInTail(node3);
        list2.addInTail(node2);
        list2.addInTail(node1);
        list2.addInTail(node8);
        list2.addInTail(node6);
        list2.addInTail(node7);
        list2.addInTail(node5);

        LinkedList2FourthAddition mergedList = LinkedList2FourthAddition.mergeListsTo(list1, list2);

        assertEquals(mergedList.toArr(), List.of(node1, node2, node3, node4, node5, node6, node7, node8));
        assertEquals(mergedList.head, node1);
        assertNull(mergedList.head.prev);
        assertEquals(mergedList.head.next, node2);
        assertEquals(mergedList.tail, node8);
        assertEquals(mergedList.tail.prev, node7);
        assertNull(mergedList.tail.next);

    }

    @Test
    public void testMergeListsToWithListsOfSeveralNodesWithDiffSize() {
        LinkedList2FourthAddition list1 = new LinkedList2FourthAddition();
        LinkedList2FourthAddition list2 = new LinkedList2FourthAddition();

        Node node8 = new Node(8);
        Node node7 = new Node(7);
        Node node6 = new Node(6);
        Node node5 = new Node(5);
        Node node4 = new Node(4);
        Node node3 = new Node(3);
        Node node2 = new Node(2);
        Node node1 = new Node(1);

        list1.addInTail(node4);
        list1.addInTail(node3);
        list1.addInTail(node2);

        list2.addInTail(node1);
        list2.addInTail(node8);
        list2.addInTail(node6);
        list2.addInTail(node7);
        list2.addInTail(node5);

        LinkedList2FourthAddition mergedList = LinkedList2FourthAddition.mergeListsTo(list1, list2);

        assertEquals(mergedList.toArr(), List.of(node1, node2, node3, node4, node5, node6, node7, node8));
        assertEquals(mergedList.head, node1);
        assertNull(mergedList.head.prev);
        assertEquals(mergedList.head.next, node2);
        assertEquals(mergedList.tail, node8);
        assertEquals(mergedList.tail.prev, node7);
        assertNull(mergedList.tail.next);

    }
}

