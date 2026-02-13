import java.util.*;


class Node<T> {
    public T value;
    public Node<T> next, prev;

    public Node(T _value) {
        value = _value;
        next = null;
        prev = null;
    }
}


public class OrderedList<T> {
    public Node<T> head, tail;

    // Task number: 7.1
    // Short description: Implement an option that specifies whether the data should be stored in ascending order.
    private boolean _ascending;

    public OrderedList(boolean asc) {
        head = null;
        tail = null;
        _ascending = asc;
    }

    // Task number: 7.2 && 7.5
    // Short description: A method for comparing two values (only for numbers and strings).
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public int compare(T v1, T v2) {
        if (v1 instanceof Number number1 && v2 instanceof Number number2)
            return Double.compare(number1.doubleValue(), number2.doubleValue());
        else if (v1 instanceof String string1 && v2 instanceof String string2) {
            String trimmedString1 = string1.trim();
            String trimmedString2 = string2.trim();
            int result = trimmedString1.compareTo(trimmedString2);
            return Integer.compare(result, 0);
        } else {
            throw new IllegalArgumentException("Unproceble types");
        }
    }

    // Task number: 7.3
    // Short description: A method for adding value in right position.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public void add(T value) {
        Node<T> newNode = new Node(value);
        if (head == null && tail == null) {
            head = newNode;
            tail = newNode;
            return;
        }
        if (_ascending) {
            Node<T> startNode = head;
            while (startNode.next != null && this.compare(startNode.value, value) == -1) {
                startNode = startNode.next;
            }
            if (startNode == head && this.compare(startNode.value, value) >= 0) {
                head = newNode;
                newNode.next = startNode;
                startNode.prev = newNode;
                return;
            }
            if (startNode == head && this.compare(startNode.value, value) == -1) {
                tail = newNode;
                newNode.prev = startNode;
                startNode.next = newNode;
                return;
            }
            if (startNode == tail && this.compare(startNode.value, value) == -1) {
                tail = newNode;
                newNode.prev = startNode;
                startNode.next = newNode;
                return;
            }
            newNode.prev = startNode.prev;
            startNode.prev.next = newNode;
            startNode.prev = newNode;
            newNode.next = startNode;

        } else {
            Node<T> startNode = tail;
            while (startNode.prev != null && this.compare(startNode.value, value) == -1) {
                startNode = startNode.prev;
            }

            if (startNode == tail && this.compare(startNode.value, value) >= 0) {
                tail = newNode;
                newNode.prev = startNode;
                startNode.next = newNode;
                return;
            }
            if (startNode == head && this.compare(startNode.value, value) <= 0) {
                head = newNode;
                newNode.next = startNode;
                startNode.prev = newNode;
                return;
            }

            newNode.prev = startNode;
            newNode.next = startNode.next;
            startNode.next.prev = newNode;
            startNode.next = newNode;
        }


    }

    // Task number: 7.6
    // Short description: A method for deleting element by it value.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public Node<T> find(T val) {
        if (head == null || tail == null)
            return null;
        if (_ascending && (compare(val, this.head.value) == -1 || compare(val, this.tail.value) == 1))
            return null;
        if (!_ascending && (compare(val, this.head.value) == 1 || compare(val, this.tail.value) == -1))
            return null;

        if (_ascending) {
            Node<T> startNode = head;
            while (startNode != null) {
                if (startNode.value == val)
                    return startNode;
                startNode = startNode.next;
            }
            return null;
        } else {
            Node<T> startNode = tail;
            while (startNode != null) {
                if (startNode.value == val)
                    return startNode;
                startNode = startNode.prev;
            }
            return null;
        }


    }

    // Task number: 7.4
    // Short description: A method for deleting element by it value.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public void delete(T val) {
        Node<T> startNode = head;

        if (startNode == null)
            return;

        while (startNode != null) {
            if (startNode.value == val) {
                if (this.count() == 1) {
                    clear(_ascending);
                    return;
                }
                if (this.count() == 2 && startNode == head) {
                    head = tail;
                    head.prev = null;
                    head.next = null;
                    return;
                }
                if (this.count() == 2 && startNode == tail) {
                    tail = head;
                    tail.prev = null;
                    tail.next = null;
                    return;
                }
                if (startNode == head) {
                    head = head.next;
                    head.prev = null;
                    return;
                }
                if (startNode == tail) {
                    tail = tail.prev;
                    tail.next = null;
                    return;
                }
                startNode.prev.next = startNode.next;
                startNode.next.prev = startNode.prev;
                return;

            }
            startNode = startNode.next;
        }
    }

    // Time complexity: O(1)
    // Space Complexity: O(1)
    public void clear(boolean asc) {
        _ascending = asc;
        head = null;
        tail = null;
    }

    // Time complexity: O(N)
    // Space Complexity: O(N)
    public int count() {
        return getAll().size();
    }

    // Time complexity: O(N)
    // Space Complexity: O(N)
    ArrayList<Node<T>> getAll() {
        ArrayList<Node<T>> r = new ArrayList<Node<T>>();
        Node<T> node = head;
        while (node != null) {
            r.add(node);
            node = node.next;
        }
        return r;
    }
}

