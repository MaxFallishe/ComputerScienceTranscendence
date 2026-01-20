import java.util.ArrayDeque;


// Task number: 5.4
// Short description: Implement a queue using two stacks.
public class QueueSecondAddition<T> {
    ArrayDeque<T> stackAlpha;
    ArrayDeque<T> stackBeta;

    public QueueSecondAddition() {
        // initializing the queue's internal storage
        stackAlpha = new ArrayDeque<>();
        stackBeta = new ArrayDeque<>();
    }

    // Task number: 5.4.1
    // Short description: Implement method of getting size of queue based on 2 stacks.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public void enqueue(T item) {
        stackAlpha.add(item);
    }

    // Task number: 5.4.2
    // Short description: Implement method of putting value in queue based on 2 stacks.
    // Time complexity: O(1) Amortized complexity
    // Space Complexity: O(1)
    public T dequeue() {
        if (size() == 0) {
            return null; // null if queue is already empty
        }

        if (!stackBeta.isEmpty())
            return stackBeta.removeLast();  // +4 coins (3 coins for future)

        while (!stackAlpha.isEmpty()) {
            T value = stackAlpha.removeLast();   // -1 coin for action
            stackBeta.add(value);  // -1 coin for action
        }
        return stackBeta.removeLast(); // -1 coin for action
    }

    // Task number: 5.4.4
    // Short description: Implement method of getting value from queue
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public int size() {
        return stackAlpha.size() + stackBeta.size();
    }

}

