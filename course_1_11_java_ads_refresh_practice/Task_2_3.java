import java.util.*;

// Task number: 1.11
// Short description: Filter bidirectional linked list
// Time complexity: O(N^2)
// Space Complexity: O(1)
public class LinkedList2ThirdAddition extends LinkedList2 {

    public LinkedList2ThirdAddition() {
        super();
    }

    public void filter() {
        if (this.head == null)
            return;

        while (true) {
            int flag = 0;
            Node node = this.head;
            while (node.next != null) {

                if (node.value <= node.next.value) {
                    node = node.next;
                    continue;
                }

                if (node.prev != null)
                    node.prev.next = node.next;
                if (node.next.next != null)
                    node.next.next.prev = node;

                Node tempNode = node.prev;
                node.prev = node.next;

                Node tempNode2 = node.next.next;
                node.next.next = node;

                node.next.prev = tempNode;
                node.next = tempNode2;


                if (node == this.head)
                    this.head = node.prev;
                if (node.prev == this.tail)
                    this.tail = node;

                flag += 1;

//                this.printLinkedList2();
//                System.out.println("------------------------------------");
            }
            if (flag == 0)
                break;
        }

    }

    public ArrayList<Node> toArr() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        Node node = this.head;
        while (node != null) {
            nodes.add(node);
            node = node.next;
        }

        return nodes;
    }

}

