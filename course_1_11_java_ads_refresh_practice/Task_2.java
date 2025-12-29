import java.util.*;


public class LinkedList2 {

    public Node head;
    public Node tail;

    public LinkedList2() {
        head = null;
        tail = null;
    }

    public void addInTail(Node _item) {
        if (head == null) {
            this.head = _item;
            this.head.next = null;
            this.head.prev = null;
        } else {
            this.tail.next = _item;
            _item.prev = tail;
        }
        this.tail = _item;
    }

    // Task number: 2.1
    // Short description: Implement method of finding node by value that nearest to head node
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public Node find(int _value) {
        Node node = this.head;
        while (node != null) {
            if (node.value == _value)
                return node;
            node = node.next;
        }

        return null;
    }

    // Task number: 2.2
    // Short description: Implement method of finding all nodes by value
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

    // Task number: 1.3
    // Short description: Implement method of removing one node by value
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public boolean remove(int _value) {
        Node node = this.head;
        if (this.head == null)
            return false;

        if (this.head.value == _value) {
            if (this.head == this.tail) {
                this.clear();
                return true;
            }

            this.head = this.head.next;
            this.head.prev = null;
            return true;
        }

        while (node != null) {
            if (node.value == _value && node == this.tail) {
                this.tail = this.tail.prev;
                this.tail.next = null;
                return true;
            }
            if (node.value == _value) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                return true;
            }

            node = node.next;
        }
        return false;
    }

    // Task number: 2.4
    // Short description: Implement method of removing all nodes with specific value
    // Time complexity: O(N^2)  # can be O(N) without using this.remove()
    // Space Complexity: O(1)
    public void removeAll(int _value) {
        boolean isNodeRemoved = this.remove(_value);
        while(isNodeRemoved)
            isNodeRemoved = this.remove(_value);
    }

    // Task number: 2.5, 2.6
    // Short description: Implement method that insert new node after existing in linked list, if _nodeAfter is null - insert as first element
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public void insertAfter(Node _nodeAfter, Node _nodeToInsert) {
        if (this.head == null) {
            this.addInTail(_nodeToInsert);
            return;
        }

        if (_nodeAfter == null) {
            _nodeToInsert.next = this.head;
            this.head.prev = _nodeToInsert;
            this.head = _nodeToInsert;
            return;
        }

        if (_nodeAfter == tail) {
            _nodeToInsert.prev = this.tail;
            this.tail.next = _nodeToInsert;
            this.tail = _nodeToInsert;
            return;
        }

        _nodeToInsert.next = _nodeAfter.next;
        _nodeToInsert.prev = _nodeAfter;
        _nodeAfter.next.prev = _nodeToInsert;
        _nodeAfter.next = _nodeToInsert;

    }

    // Task number: 2.7
    // Short description: Implement method for delete all nodes from linked list
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public void clear() {
        this.head = null;
        this.tail = null;
    }

    public int count() {
        int nodeCounter = 0;
        Node node = this.head;
        while (node != null) {
            nodeCounter += 1;
            node = node.next;
        }
        return nodeCounter;
    }

    public void printLinkedList2() {
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
    public Node prev;

    public Node(int _value) {
        value = _value;
        next = null;
        prev = null;
    }

    @Override
    public String toString() {
        return "Node(value=" + value + ", id=" + System.identityHashCode(this) + ")";
    }
}

