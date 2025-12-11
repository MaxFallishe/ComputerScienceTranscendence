// Task number: 1.8
// Short description: Sum two linked lists element-wise
// Time complexity: O(N)
// Space Complexity: O(N)


public final class LinkedListFirstAddition {

    private LinkedListFirstAddition() {}

    public static LinkedList overlapWithSummation(LinkedList _firstList, LinkedList _secondList) {
        if (_firstList.count() != _secondList.count())
            throw new IllegalArgumentException("Both _firstList and _secondList must have equal size.");

        LinkedList overlappedList = new LinkedList();
        Node nodeX = _firstList.head;
        Node nodeY = _secondList.head;

        while (nodeX != null) {
            int summedValue = nodeX.value + nodeY.value;
            overlappedList.addInTail(new Node(summedValue));
            nodeX = nodeX.next;
            nodeY = nodeY.next;
        }

        return overlappedList;
    }
}

