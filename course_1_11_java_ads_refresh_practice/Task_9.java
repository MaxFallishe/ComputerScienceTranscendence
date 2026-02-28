import java.lang.reflect.Array;
import java.util.Objects;


// Task number: 9.1
// Short description: Research about dictionary data strcuture implementation in java.
// Findings: In java, unlike python, the dictionary is implemented through an array of buckets, and not,
//           as in python, through a single dense hash table. The order of the elements is not guaranteed.

// Task number: 9.2
// Short description: Implement NativeDictionary class.
class NativeDictionary<T> {
    public int size;
    public String[] slots;
    public T[] values;
    public int numElements = 0;
    public final int step = 3;
    public static final int MAX_LOOPS_COUNT = 1000000;

    public NativeDictionary(int sz, Class clazz) {
        size = sz;
        slots = new String[size];
        values = (T[]) Array.newInstance(clazz, this.size);
    }

    public int hashFun(String key) {
        int result = 0;
        for (int i = 0; i < key.length(); i++) {
            result += key.charAt(i) * (i + 1);
        }
        return normalizeIndx(result);
    }

    public int seekSlot(String value) {
        if (this.size == this.numElements)
            return -1;

        int slotIndx = this.hashFun(value);
        for (int i = 0; i < MAX_LOOPS_COUNT; i++) {
            if (slots[slotIndx] == null)
                return slotIndx;
            slotIndx = normalizeIndx(slotIndx + step);
        }
        return -1;
    }

    public int find(String value) {
        int slotIndx = this.hashFun(value);
        for (int i = 0; i < MAX_LOOPS_COUNT; i++) {

            if (Objects.equals(slots[slotIndx], null))
                return -1;
            if (Objects.equals(slots[slotIndx], value))
                return slotIndx;

            slotIndx = normalizeIndx(slotIndx + step);
        }
        return -1;
    }

    // Task number: 9.3.2
    // Short description: Implement a method that return information about existing some key in dictionary.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public boolean isKey(String key) {
        T value = get(key);
        return value != null;
    }

    // Task number: 9.3.1
    // Short description: Implement a method that putting value in dictionary by key.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public void put(String key, T value) {
        int slotIndx = this.find(key);
        if (slotIndx == -1)
            slotIndx = this.seekSlot(key);

        slots[slotIndx] = key;
        values[slotIndx] = value;
        this.numElements += 1;
    }

    // Task number: 9.3.3
    // Short description: Implement a method that getting a value from dictionary by key.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public T get(String key) {
        int indx = find(key);
        if (indx == -1)
            return null;

        return values[indx];
    }

    public int normalizeIndx(int indx) {
        return indx % this.size;
    }
}

