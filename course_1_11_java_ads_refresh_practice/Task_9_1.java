import java.lang.reflect.Array;

// Task number: 9.5
// Short description: 5.* Implement a dictionary using an ordered list by key to optimize search performance.
// Estimate the time complexity of insertion, deletion, and search operations in such a dictionary.
// ---Reflection.---
// The main difference from the reference implementation is that in my value store, the values are stored in cells
// with the same indexes as in the key store, which makes it necessary to move the values when re-sorting in both stores.
// The time complexity in get() when using an ordered list is O(logN), compared to the implementation using a hash
// table where the speed is o(1), the ordered list will be more stable, but with a good hash table mechanism,
// o(1) is always better.
class NativeDictionaryFirstAddition<T> {
    public int size;
    public String[] slots;
    public T[] values;
    public int numElements = 0;

    public NativeDictionaryFirstAddition(int sz, Class clazz) {
        size = sz;
        slots = new String[size];
        values = (T[]) Array.newInstance(clazz, size);
    }

    // Task number: 9.5.1
    // Short description: Implement a method that finding key in ordered list.
    // Time complexity: O(logN)
    // Space Complexity: O(1)
    public int find(String key) {
        int lowPointer = 0;
        int highPointer = numElements - 1;
        while (lowPointer <= highPointer) {
            int middleIndx = (lowPointer + highPointer) / 2;
            int cmp = slots[middleIndx].compareTo(key);
            if (cmp == 0)
                return middleIndx;
            if (cmp < 0)
                lowPointer = middleIndx + 1;
            else
                highPointer = middleIndx - 1;
        }
        return -1;
    }

    // Task number: 9.5.2
    // Short description: Implement a method that seeking slot in ordered list.
    // Time complexity: O(logN)
    // Space Complexity: O(1)
    public int seekSlot(String key) {
        if (numElements == size)
            return -1;
        int lowPointer = 0;
        int highPointer = numElements;
        while (lowPointer < highPointer) {
            int middleIndx = (lowPointer + highPointer) / 2;
            if (slots[middleIndx].compareTo(key) < 0)
                lowPointer = middleIndx + 1;
            else
                highPointer = middleIndx;
        }
        return lowPointer;
    }

    // Task number: 9.5.3
    // Short description: Implement a method that return information about existing some key in dictionary.
    // Time complexity: O(logN)
    // Space Complexity: O(1)
    public boolean isKey(String key) {
        return find(key) >= 0;
    }


    // Task number: 9.5.4
    // Short description: Implement a method that getting a value from dictionary by key.
    // Time complexity: O(logN)
    // Space Complexity: O(1)
    public T get(String key) {
        int indx = find(key);
        if (indx >= 0)
            return values[indx];
        return null;
    }

    // Task number: 9.5.5
    // Short description: Implement a method that putting value in dictionary by key.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public void put(String key, T value) {
        int indx = find(key);
        if (indx >= 0) {
            values[indx] = value;
            return;
        }

        int slotIndx = seekSlot(key);
        if (slotIndx == -1)
            return;
        for (int i = numElements; i > slotIndx; i--) {
            slots[i] = slots[i - 1];
            values[i] = values[i - 1];
        }

        slots[slotIndx] = key;
        values[slotIndx] = value;
        numElements += 1;

    }

    // Task number: 9.5.6
    // Short description: Implement a method that delete value in dictionary by key.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public T remove(String key) {
        int indx = find(key);
        if (indx == -1)
            return null;
        T toRemove = values[indx];

        for (int i = indx; i < numElements; i++) {
            slots[i] = slots[i + 1];
            values[i] = values[i + 1];
        }

        slots[numElements-1] = null;
        values[numElements-1] = null;
        numElements -= 1;
        return toRemove;
    }
}

