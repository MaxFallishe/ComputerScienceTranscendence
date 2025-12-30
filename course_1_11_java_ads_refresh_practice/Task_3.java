import java.lang.reflect.Array;
import java.util.Arrays;

public class DynArray<T> {
    public T[] array;
    public int count;
    public int capacity;
    public static final int MIN_AVAILABLE_CAPACITY = 16;
    public static final int BUFFER_INCREASE_COEFFICIENT = 2;
    public static final double BUFFER_DECREASE_COEFFICIENT = 1.5;
    Class clazz;

    public DynArray(Class clz) {
        clazz = clz; // needed for safe type conversion
        // new DynArray<Integer>(Integer.class);

        count = 0;
        makeArray(MIN_AVAILABLE_CAPACITY);
    }

    // Task number: 3.1.1
    // Short description: Implement method of array creation/recreation
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public void makeArray(int new_capacity) {

        if (array == null) {
            array = (T[]) Array.newInstance(clazz, new_capacity);
            capacity = new_capacity;
            return;
        }

        array = Arrays.copyOf(array, new_capacity);
        capacity = new_capacity;
        if (capacity <= count)
            count = new_capacity;

    }

    // Task number: 3.1.2
    // Short description: Implement method of getting value by index
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public T getItem(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ". Index cannot be negative.");

        if (index >= count)
            throw new IndexOutOfBoundsException("Index: " + index + ", Current Max Count: " + (count - 1));

        return array[index];
    }

    // Task number: 3.1.3
    // Short description: Implement method of adding new value in tail of dynArray
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public void append(T itm) {
        if (capacity == count) {
            int newCapacity = capacity * BUFFER_INCREASE_COEFFICIENT;
            makeArray(newCapacity);
        }
        array[count] = itm;
        count++;

    }

    // Task number: 3.2
    // Short description: Implement method of insert operation for dynArray
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public void insert(T itm, int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ". Index cannot be negative.");

        if (index > count)
            throw new IndexOutOfBoundsException("Index: " + index + ", Currently " + count + " elements in array, so you can use index from 0 to " + count);

        if (capacity == count) {
            int newCapacity = capacity * BUFFER_INCREASE_COEFFICIENT;
            makeArray(newCapacity);
        }

        T oldValue;
        for (int i = index; i <= count; i++) {
            oldValue = (T) array[i];
            array[i] = itm;
            itm = oldValue;
        }
        count++;
    }

    // Task number: 3.3
    // Short description: Implement method of element removing from dynArray
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public void remove(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ". Index cannot be negative.");

        if (index >= count)
            throw new IndexOutOfBoundsException("Index: " + index + ", Currently " + count + " elements in array, so you can use index from 0 to " + (count - 1));


        array[index] = null;
        for (int i = index; i < count - 1; i++) {
            T tmp = (T) array[i];
            array[i] = array[i + 1];
            array[i + 1] = tmp;
        }
        count--;

        if ((double) count / capacity < 0.5) {
            int newCapacity = (int) (capacity / BUFFER_DECREASE_COEFFICIENT);
            if (newCapacity < MIN_AVAILABLE_CAPACITY)
                newCapacity = MIN_AVAILABLE_CAPACITY;
            makeArray(newCapacity);
        }

    }

}

