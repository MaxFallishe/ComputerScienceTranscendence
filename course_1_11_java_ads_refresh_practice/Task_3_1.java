import java.lang.reflect.Array;
import java.util.Arrays;

// Task number: 3.6
// Short description: Implement Dynamic Array based on "Bank method"
// Reflection:
// In my implementation of the dynamic array based on the banking method, the main goal for me was to test in practice
// how the depreciation assessment method works and that it really guarantees that the bank will not go into an infinite
// disadvantage and heavy operations are really compensated by lighter and more frequent ones. Unlike the proposed
// solution, where it is proposed to start increasing the buffer at the time of reaching the required number of coins
// and actually spend them, my banking method rather accompanies the program and rather performs the role of an
// invariant of correctness that heavy operations are compensated by light ones, at least that was the idea. For me,
// the mechanism itself has become much more understandable, and how the banking method should be applied so that you
// can make sure that complexity is also predictably amortized over a long horizon.
public class DynArrayFirstAddition<T> {
    public T[] array;
    public int count;
    public int capacity;
    public static final int MIN_AVAILABLE_CAPACITY = 16;
    public static final int BUFFER_INCREASE_COEFFICIENT = 2;
    public static final double BUFFER_DECREASE_COEFFICIENT = 1.5;
    public int bankAccountPoints = 0;
    public static final int APPEND_ELEMENT_OPERATION_REAL_PRICE = 1;
    public static final int APPEND_ELEMENT_OPERATION_NOMINAL_PRICE = 3;
    public static final int COPY_ELEMENT_OPERATION_REAL_PRICE = 1;
    public static final int COPY_ELEMENT_OPERATION_NOMINAL_PRICE = 3;
    public static final int REMOVE_ELEMENT_OPERATION_REAL_PRICE = 1;
    public static final int REMOVE_ELEMENT_OPERATION_NOMINAL_PRICE = 3;

    private final Class<T> clazz;

    public DynArrayFirstAddition(Class<T> clz) {
        clazz = clz; // needed for safe type conversion
        // new DynArray<Integer>(Integer.class);

        count = 0;
        makeArray(MIN_AVAILABLE_CAPACITY);
    }

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

    public T getItem(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ". Index cannot be negative.");

        if (index >= count)
            throw new IndexOutOfBoundsException("Index: " + index + ", Current Max Count: " + (count - 1));

        return array[index];
    }

    public void append(T itm) {
        bankAccountPoints += APPEND_ELEMENT_OPERATION_NOMINAL_PRICE;

        if (capacity == count) {
            int copyCost = capacity;
            bankAccountPoints -= copyCost;

            int newCapacity = capacity * BUFFER_INCREASE_COEFFICIENT;
            makeArray(newCapacity);
        }
        array[count] = itm;
        count++;

        bankAccountPoints -= APPEND_ELEMENT_OPERATION_REAL_PRICE;
    }

    public void insert(T itm, int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ". Index cannot be negative.");

        if (index > count)
            throw new IndexOutOfBoundsException("Index: " + index + ", Currently " + count + " elements in array, so you can use index from 0 to " + count);

        bankAccountPoints += COPY_ELEMENT_OPERATION_NOMINAL_PRICE;
        int shifts = count - index;
        bankAccountPoints += shifts * COPY_ELEMENT_OPERATION_NOMINAL_PRICE;
        bankAccountPoints -= shifts * COPY_ELEMENT_OPERATION_REAL_PRICE;

        if (capacity == count) {
            int copyCost = capacity;
            bankAccountPoints -= copyCost;

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

        bankAccountPoints -= COPY_ELEMENT_OPERATION_REAL_PRICE;
    }

    public void remove(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ". Index cannot be negative.");

        if (index >= count)
            throw new IndexOutOfBoundsException("Index: " + index + ", Currently " + count + " elements in array, so you can use index from 0 to " + (count - 1));

        bankAccountPoints += REMOVE_ELEMENT_OPERATION_NOMINAL_PRICE;
        int shifts = count - index - 1;
        bankAccountPoints += shifts * COPY_ELEMENT_OPERATION_NOMINAL_PRICE;
        bankAccountPoints -= shifts * COPY_ELEMENT_OPERATION_REAL_PRICE;

        if ((double) capacity / count < 2.0) {
            int newCapacity = (int) (capacity / BUFFER_DECREASE_COEFFICIENT);
            if (newCapacity < MIN_AVAILABLE_CAPACITY)
                newCapacity = MIN_AVAILABLE_CAPACITY;

            int copyCost = capacity;
            bankAccountPoints -= copyCost;
            makeArray(newCapacity);
        }

        array[index] = null;
        for (int i = index; i < count - 1; i++) {
            T tmp = (T) array[i];
            array[i] = array[i + 1];
            array[i + 1] = tmp;
        }
        count--;

        bankAccountPoints -= REMOVE_ELEMENT_OPERATION_REAL_PRICE;
    }

}

