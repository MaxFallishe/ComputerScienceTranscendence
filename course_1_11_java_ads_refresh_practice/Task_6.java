import java.util.*;


// Task number: 6.1
// Short description: Implement deque class.
public class Deque<T> {
    LinkedList<T> storage;

    public Deque() {
        storage = new LinkedList<>();
    }

    // Task number: 6.1.1
    // Short description: Implement method of adding new element in deque from head
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public void addFront(T item) {
        storage.addFirst(item);
    }

    // Task number: 6.1.2
    // Short description: Implement method of adding new element in deque from end.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public void addTail(T item) {
        storage.addLast(item);
    }

    // Task number: 6.1.3
    // Short description: Implement method of removing element in deque from the head.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public T removeFront() {
        return storage.pollFirst();
    }

    // Task number: 6.1.4
    // Short description: Implement method of removing element in deque from the end.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public T removeTail() {
        return storage.pollLast();
    }

    // Task number: 6.1.5
    // Short description: Implement method that return amount of elements in deque.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public int size() {
        return storage.size();
    }
}


// Task number: 6.2
// Short description: How can the complexity of addHead/removeHead and addTail/removeTail be lowered (leveled), using which previously studied data type?
// Answer: The best way to balance the efficiency of adding and removing items from the beginning and end is to use a bidirectional linked list.
//         In this case, both operations will take only O(1) in time and memory, the only disadvantage with this implementation is that
//         it is impossible in O(1) to access an item in the queue by index (for example, the 3rd elemnt from start) or to find out
//         if there is a specific item in the deque.

