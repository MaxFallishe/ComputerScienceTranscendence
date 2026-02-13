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
    // ---Refleсtion.---
    // During the execution, the main difficulty was the one that I set myself. I immediately realized that you can just
    // push items from one end of the queue and push them into the other, but I was worried about the suboptimality
    // that occurs with large amounts of "crunching", for example, when there are 10 items but you need to make 1000
    // shifts. Therefore, I decided to make a solution through sabers where it would be possible to immediately
    // calculate the desired shift and not move the elements too many times.
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

