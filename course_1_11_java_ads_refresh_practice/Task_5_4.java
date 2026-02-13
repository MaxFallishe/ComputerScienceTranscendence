import java.lang.reflect.Array;

// Task number: 5.6
// Short description: Implement a circular (cyclic buffer) queue with a fixed-size static array.
// ---Refleсtion.---
// On the one hand, it was not so difficult to implement a cyclic queue inside a fairly rigid data structure, but still,
// the mechanism of pointer control itself, especially in edge cases, will make you think carefully about implementations.
// One of the main points that I would definitely remember is that you should select one pointer (front or back) and
// already adjust the index to it, which will allow you not to overwrite the values.
public class QueueFourthAddition<T> {

    private final T[] storage;
    private final int capacity;
    public int size_counter;
    public int pointer_head = 0;
    public int pointer_tail = 0;


    public QueueFourthAddition(Class<T> clazz, int capacity) {
        this.capacity = capacity;
        this.storage = (T[]) Array.newInstance(clazz, capacity);
    }


    public void enqueue(T item) {
        if (isFull())
            throw new IllegalStateException("Queue is full");
        storage[pointer_head] = item;
        pointer_head = calculateIndex(pointer_head+1);
        size_counter += 1;

    }

    public T dequeue() {
        if (size() == 0) {
            return null;
        }
        T value = storage[pointer_tail];
        storage[pointer_tail] = null;

        pointer_tail = calculateIndex(pointer_tail+1);
        size_counter -= 1;
        return value;
    }

    public int calculateIndex(int queueIndex) {
        if (queueIndex >= capacity)
            return (queueIndex % capacity);
        if (queueIndex < 0) {
            if (-queueIndex >= capacity)
                queueIndex = (queueIndex) % (-capacity);
            return (capacity - (-queueIndex));
        }
        return queueIndex;
    }

    public int size() {
        return size_counter;
    }

    public boolean isFull() {
        return size() == capacity;
    }

}

