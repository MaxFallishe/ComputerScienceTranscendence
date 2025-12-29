import java.util.*;

// Task number: 2.10
// Short description: Check is there any loops inside bidirectional linked list
// Time complexity: O(N)
// Space Complexity: O(N)
public class LinkedList2SecondAddition extends LinkedList2 {

    public LinkedList2SecondAddition() {
        super();
    }

    public boolean isLooped() {
        if (this.head == null)
            return false;
        if (this.head.prev != null || this.tail.next != null)
            return true;

        int headToTailIntervalCounter = 0;
        int tailToHeadIntervalCounter = 0;

        Set<Node> setOfNodes = new HashSet<>();
        Node node = this.head;
        while (!setOfNodes.contains(node)) {
            headToTailIntervalCounter += 1;
            setOfNodes.add(node);
            if (node.next == null) {
                break;
            }
            node = node.next;
        }

        if (node != this.tail)
            return true;

        Set<Node> setOfNodes2 = new HashSet<>();
        Node node2 = this.tail;
        while (!setOfNodes2.contains(node2)) {
            tailToHeadIntervalCounter += 1;
            setOfNodes2.add(node2);
            if (node2.prev == null)
                break;
            node2 = node2.prev;
        }
        if (node2 != this.head)
            return true;

        if (headToTailIntervalCounter != tailToHeadIntervalCounter)
            return true;

        return false;
    }
}

