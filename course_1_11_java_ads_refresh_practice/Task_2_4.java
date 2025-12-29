// Task number: 2.12
// Short description: Merge two bidirectional linked lists to a filtered bidirectional linked list
// Time complexity: O(N^2)
// Space Complexity: O(1)
public class LinkedList2FourthAddition extends LinkedList2ThirdAddition {

    public LinkedList2FourthAddition() {
        super();
    }

    public static LinkedList2FourthAddition mergeListsTo(LinkedList2FourthAddition _listOne, LinkedList2FourthAddition _listTwo) {
        LinkedList2FourthAddition list = new LinkedList2FourthAddition();

        _listOne.filter();
        _listTwo.filter();

        if (_listOne.count() == 0) {
            return _listTwo;
        }

        if (_listTwo.count() == 0) {
            return _listOne;
        }

        if (_listOne.count() + _listTwo.count() == 0) {
            return list;
        }

        Node node1 = _listOne.head;
        Node node2 = _listTwo.head;

        while (node1 != null || node2 != null) {
            if (node1 == null) {
                list.addInTail(node2);
                node2 = node2.next;
                continue;
            }

            if (node2 == null) {
                list.addInTail(node2);
                node1 = node1.next;
                continue;
            }

            if (node1.value <= node2.value) {
                Node nextNode = node1.next;
                list.addInTail(node1);
                node1 = nextNode;
                continue;
            }

            Node nextNode = node2.next;
            list.addInTail(node2);
            node2 = nextNode;

        }

        return list;
    }


}

