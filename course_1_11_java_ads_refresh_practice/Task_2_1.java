// Task number: 2.9
// Short description: Reverse bidirectional linked list
// Time complexity: O(N)
// Space Complexity: O(1)
public class LinkedList2FirstAddition extends LinkedList2 {

    public LinkedList2FirstAddition() {
        super();
    }

    public void reverse() {
        if (this.head == null || this.head == this.tail)
            return;

        Node node = this.tail;
        while (node != null) {
            if (node == this.tail) {
                node.next = node.prev;
                node.prev = null;
            }
            else if (node == this.head) {
                node.prev = node.next;
                node.next = null;
            }
            else {
                Node tempNode = node.prev;
                node.prev = node.next;
                node.next = tempNode;
            }

            node = node.next;
        }

        Node tempNode = this.head;
        this.head = this.tail;
        this.tail = tempNode;
    }
}

