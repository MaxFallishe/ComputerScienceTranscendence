import java.util.*;

public class LinkedList {
    public Node head;
    public Node tail;

    public LinkedList() {
        head = null;
        tail = null;
    }

    public void addInTail(Node item) {
        if (this.head == null)
            this.head = item;
        else
            this.tail.next = item;
        this.tail = item;
    }

    public Node find(int value) {
        Node node = this.head;
        while (node != null) {
            if (node.value == value)
                return node;
            node = node.next;
        }
        return null;
    }

    // Task number: 1.4
    // Short description: Implement method of removing one node by value
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public ArrayList<Node> findAll(int _value) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        Node node = this.head;
        while (node != null) {
            if (node.value == _value)
                nodes.add(node);
            node = node.next;
        }

        return nodes;
    }

    // Task number: 1.1
    // Short description: Implement method of removing one node by value
    // Time complexity: O(N^2)
    // Space Complexity: O(N)
    public boolean remove(int _value) {
        Node node = this.head;
        if (node == null)
            return false;

        if (this.head.value == _value) {
            if (this.head == this.tail)
                this.tail = this.tail.next;
            this.head = this.head.next;
            return true;
        }

        while (node.next != null) {
            if (node.next.value == _value) {
                if (node.next == this.tail)
                    this.tail = node;
                node.next = node.next.next;
                return true;
            }
            node = node.next;
        }

        return false;
    }

    // Task number: 1.2
    // Short description: Implement method of removing all nodes with specific value
    // Time complexity: O(N^2)
    // Space Complexity: O(1)
    public void removeAll(int _value) {
        boolean isNodeRemoved = this.remove(_value);
        while (isNodeRemoved)
            isNodeRemoved = this.remove(_value);
    }

    // Task number: 1.3
    // Short description: Implement method for delete all nodes from linked list
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public void clear() {
        this.head = null;
        this.tail = null;
    }

    // Task number: 1.5
    // Short description: Implement method for counting nodes in linked list
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public int count() {
        int nodeCounter = 0;
        Node node = this.head;
        while (node != null) {
            nodeCounter += 1;
            node = node.next;
        }
        return nodeCounter;
    }

    // Task number: 1.6
    // Short description: Implement method that insert new node after existing in linked list
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public void insertAfter(Node _nodeAfter, Node _nodeToInsert) {
        if (_nodeAfter == null) {
            if (this.head == null) {
                this.addInTail(_nodeToInsert);
                return;
            }

            _nodeToInsert.next = this.head;
            this.head = _nodeToInsert;
            return;
        }

        _nodeToInsert.next = _nodeAfter.next;
        _nodeAfter.next = _nodeToInsert;

        if (_nodeToInsert.next == null)
            this.tail = _nodeToInsert;
    }


    public void printLinkedList() {
        Node node = this.head;

        while (node != null) {
            System.out.println(node);
            node = node.next;
        }

    }

}


class Node {
    public int value;
    public Node next;

    public Node(int _value) {
        value = _value;
        next = null;
    }

    @Override
    public String toString() {
        return "Node(value=" + value + ", id=" + System.identityHashCode(this) + ")";
    }
}

