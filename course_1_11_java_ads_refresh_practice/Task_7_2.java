import java.util.*;


public class OrderedListSecondAddition<T> {
    public Node<T> head, tail;


    private boolean _ascending;

    public OrderedListSecondAddition(boolean asc) {
        head = null;
        tail = null;
        _ascending = asc;
    }

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

    public void clear(boolean asc) {
        _ascending = asc;
        head = null;
        tail = null;
    }

    public int count() {
        return getAll().size();
    }

    ArrayList<Node<T>> getAll() {
        ArrayList<Node<T>> r = new ArrayList<Node<T>>();
        Node<T> node = head;
        while (node != null) {
            r.add(node);
            node = node.next;
        }
        return r;
    }

    public void removeDublicates() {
        if (head == null || tail == null) {
            return;
        }
        Node<T> startNode = head;
        T valueForDelete = head.value;
        while (valueForDelete != null) {
            while (find(valueForDelete) != null) {
                delete(valueForDelete);
            }
            add(valueForDelete);
            startNode = find(valueForDelete).next;
            valueForDelete = (startNode == null) ? null : startNode.value;
        }
    }

    // Task number: 7.9
    // Short description: A method for merging two ordered lists.
    // Time complexity: O(N)
    // Space Complexity: O(N)
    // ---Refleсtion.---
    // Globally, connecting two or more sorted lists is not a complicated operation at the algorithm level, the lists are
    // already sorted, and it is enough for us to check gradually by iterating from the end (with smaller values) of the list
    // which of the values of the lists is smaller than all the presented ones, after which we remove this value from the original
    // list (in fact, we simply shift the pointer of a specific list by one value. However, with a specific implementation
    // in the code, I had to use a large number of conditional statements inside the main loop to support the logic with the
    // _ascending flag, which determines the order of elements in the array (asc or desc). I'm sure there is a more elegant
    // and shorter solution with java where you can avoid constantly checking the status of the _ascending parameter.
    public OrderedListSecondAddition<T> mergeList(OrderedListSecondAddition<T> l1, OrderedListSecondAddition<T> l2) {
        OrderedListSecondAddition<T> resultList = new OrderedListSecondAddition<T>(_ascending);
        if (l1.head == null) {
            return l2;
        }
        if (l2.head == null) {
            return l1;
        }
        Node<T> pointer1 = (l1._ascending) ? l1.head : l1.tail;
        Node<T> pointer2 = (l2._ascending) ? l2.head : l2.tail;

        while (pointer1 != null || pointer2 != null) {
            if (pointer1 == null) {
                resultList.add(pointer2.value);
                l2.delete(pointer2.value);
                pointer2 = (l2._ascending) ? l2.head : l2.tail;
                continue;
            }
            if (pointer2 == null) {
                resultList.add(pointer1.value);
                l1.delete(pointer1.value);
                pointer1 = (l1._ascending) ? l1.head : l1.tail;
                continue;
            }

            if (compare(pointer1.value, pointer2.value) <= 0) {
                resultList.add(pointer1.value);
                l1.delete(pointer1.value);
                pointer1 = (l1._ascending) ? l1.head : l1.tail;
            } else {
                resultList.add(pointer2.value);
                l2.delete(pointer2.value);
                pointer2 = (l2._ascending) ? l2.head : l2.tail;
            }
        }

        return resultList;
    }

}

