import java.lang.reflect.Array;
import java.util.Arrays;

// Task number: 3.6
// Short description: Implement Dynamic Array based on "Bank method"
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

