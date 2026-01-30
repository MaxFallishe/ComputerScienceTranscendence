import java.util.Arrays;

// Task number: 6.6
// Short description: Implement deque methods of adding and removing elements with o(1) efficiency.
public class DequeThirdAddition<T> {
    public int capacity;
    public int size;
    private int frontIndx;
    private int tailIndx;
    private T[] storage;

    @SuppressWarnings("unchecked")
    public DequeThirdAddition() {
        capacity = 10; // initial capacity
        size = 0;
        frontIndx = 0;
        tailIndx = 1;
        storage = (T[]) new Object[capacity];
    }

    // Task number: 6.6.1
    // Short description: Implement method of adding new element in deque from head
    // Time complexity: o(1)
    // Space Complexity: o(1)
    public void addFront(T item) {
        if (frontIndx != tailIndx) {
            storage[frontIndx] = item;
            size += 1;
            frontIndx = adjustIndex(frontIndx - 1);
        } else {
            storage[frontIndx] = item;
            size += 1;
            changeCapacity(capacity * 2);
        }
    }

    // Task number: 6.6.2
    // Short description: Implement method of adding new element in deque from end.
    // Time complexity: o(1)
    // Space Complexity: o(1)
    public void addTail(T item) {
        if (frontIndx != tailIndx) {
            storage[tailIndx] = item;
            size += 1;
            tailIndx = adjustIndex(tailIndx + 1);
        } else {
            storage[tailIndx] = item;
            size += 1;
            frontIndx = adjustIndex(frontIndx+1);
            tailIndx = adjustIndex(tailIndx+1);
            changeCapacity(capacity * 2);
        }
    }

    // Task number: 6.6.3
    // Short description: Implement method of removing element in deque from the head.
    // Time complexity: o(1)
    // Space Complexity: o(1)
    public T removeFront() {
        if (size == 0)
            return null;

        int adjustedFrontIndx = adjustIndex(frontIndx+1);
        T item = storage[adjustedFrontIndx];
        storage[adjustedFrontIndx] = null;

        if (size > 1)
            frontIndx = adjustedFrontIndx;
        size = size - 1;

        if (size <= (capacity / 4)) {
            int newCapacity = (int) (capacity / 1.5);
            changeCapacity(newCapacity);
        }
        return item;
    }

    // Task number: 6.6.4
    // Short description: Implement method of removing element in deque from the end.
    // Time complexity: o(1)
    // Space Complexity: o(1)
    public T removeTail() {
        if (size == 0)
            return null;

        int adjustedTailIndx = adjustIndex(tailIndx-1);
        T item = storage[adjustedTailIndx];
        storage[adjustedTailIndx] = null;

        if (size > 1)
            tailIndx = adjustedTailIndx;
        size = size - 1;

        if (size <= (capacity / 4)) {
            int newCapacity = (int) (capacity / 1.5);
            changeCapacity(newCapacity);
        }
        return item;
    }

    public int size() {
        return this.size;
    }

    public int adjustIndex(int indx) {
        if (indx >= capacity) {
            return indx % capacity;
        } else if (indx < 0) {
            indx = indx % (-capacity);
            if (indx == 0)
                return 0;
            return capacity + (indx % (-capacity));
        }

        return indx;
    }

    @SuppressWarnings("unchecked")
    private void changeCapacity(int newCapacitySize) {
        if (newCapacitySize <= 10) {
            return;
        }
        T[] newStorage = (T[]) new Object[newCapacitySize];
        if (frontIndx == tailIndx) {
            for (int i = frontIndx; i < frontIndx+capacity; i++) {
                newStorage[i-frontIndx] = storage[adjustIndex(i)];
            }
            frontIndx = newCapacitySize - 1;
            tailIndx = capacity;
            capacity = newCapacitySize;
        } else {
            int adjustFrontIndx = adjustIndex(frontIndx+1);
            int adjustTailIndx = adjustIndex(tailIndx);
            for (int i = adjustFrontIndx; adjustIndex(i) != adjustTailIndx; i++) {
                newStorage[i-adjustFrontIndx] = storage[adjustIndex(i)];
            }
            frontIndx = newCapacitySize - 1;
            tailIndx = size();
            capacity = newCapacitySize;
        }

        storage = newStorage;
    }

    public int getFrontIndx() {
        return frontIndx;
    }

    public int getTailIndx() {
        return tailIndx;
    }

    public T[] getStorage() {
        return storage;
    }
}

