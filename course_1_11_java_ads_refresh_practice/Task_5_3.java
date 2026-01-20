import java.util.ArrayDeque;


public class QueueThirdAddition<T> {
    ArrayDeque<T> stackAlpha;
    ArrayDeque<T> stackBeta;

    public QueueThirdAddition() {
        // initializing the queue's internal storage
        stackAlpha = new ArrayDeque<>();
        stackBeta = new ArrayDeque<>();
    }

    public void enqueue(T item) {
        stackAlpha.add(item);
    }

    public T dequeue() {
        if (size() == 0) {
            return null; // null if queue is already empty
        }

        if (!stackBeta.isEmpty())
            return stackBeta.removeLast();

        while (!stackAlpha.isEmpty()) {
            T value = stackAlpha.removeLast();
            stackBeta.add(value);
        }
        return stackBeta.removeLast();
    }

    public int size() {
        return stackAlpha.size() + stackBeta.size();
    }

    // Task number: 5.5
    // Short description: Implement a function that reverses all the items in the queue.
    public void reverse() {
        ArrayDeque<T> stackGamma = stackAlpha;
        stackAlpha = stackBeta;
        stackBeta = stackGamma;
    }

}

