import java.util.ArrayList;

public class QueueFirstAddition<T> {

    ArrayList<T> storage;


    public QueueFirstAddition() {
        // initializing the queue's internal storage
        storage = new ArrayList<>();

    }

    public void enqueue(T item) {
        storage.addLast(item);
    }

    public T dequeue() {
        if (size() == 0) {
            return null; // null if queue is already empty
        }
        return storage.removeFirst();
    }

    public int size() {
        return storage.size();
    }

    // Task number: 5.3
    // Short description: Implement method that "rotate" queue
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public void shift(int steps) {
        if (steps < 0)
            throw new IllegalArgumentException("Amount of steps should be 0 or greater");
        if (size() == 0 || size() == 1 || steps == size())
            return;
        if (steps > size())
            steps = steps % size();

        ArrayList<T> newStorage = new ArrayList<>();
        newStorage.addAll(storage.subList(size() - steps, size()));
        newStorage.addAll(storage.subList(0, size() - steps));

        this.storage = newStorage;
    }

}

