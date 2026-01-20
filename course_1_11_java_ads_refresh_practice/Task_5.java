import java.util.*;

public class Queue<T> {

    ArrayList<T> storage;

    public Queue() {
        // initializing the queue's internal storage
        storage = new ArrayList<>();

    }

    // Task number: 5.1.1
    // Short description: Implement method of getting size of queue
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public void enqueue(T item) {
        storage.addLast(item);
    }

    // Task number: 5.1.2
    // Short description: Implement method of putting value in queue
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public T dequeue() {
        if (size() == 0) {
            return null; // null if queue is already empty
        }
        return storage.removeFirst();
    }

    // Task number: 5.1.3
    // Short description: Implement method of getting value from queue
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public int size() {
        return storage.size();
    }

}

