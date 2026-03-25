import java.lang.reflect.Array;

class NativeCache<T> {
    public int size;
    public int capacity;
    public String[] slots;
    public T[] values;
    public Integer[] hits;

    public NativeCache(Class clazz) {
        this(10, clazz);
    }

    public NativeCache(int capacity, Class clazz) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity cannot be less than 1");
        }
        this.size = 0;
        this.capacity = capacity;
        this.values = (T[]) Array.newInstance(clazz, this.capacity);
        this.hits = new Integer[this.capacity];
        this.slots = new String[this.capacity];

    }


    public int customHash(String value) {
        int hashResult = value.hashCode();
        hashResult ^= (hashResult >>> 16);
        return Math.abs(hashResult) % capacity;
    }

    public int seekSlot(String value) {
        int start = this.customHash(value);
        int indx = start;

        do {
            if (slots[indx] == null || slots[indx].equals(value)) {
                return indx;
            }
            indx = (indx + 1) % capacity;
            ;
        } while (indx != start);

        return -1;
    }

    private void put(String value) {
        if (this.size == this.capacity) {
            this.removeLeastHittedElement();
        }
        int indx = this.seekSlot(value);
        if (slots[indx] != null) {
            hits[indx] += 1;
            return;
        }
        slots[indx] = value;
        values[indx] = loadValue(value);
        hits[indx] = 1;
        size += 1;
    }

    public T get(String value) {
        int start = this.customHash(value);
        int indx = start;
        while (slots[indx] != null) {
            if (slots[indx].equals(value)) {
                return values[indx];
            }
            indx = (indx + 1) % capacity;

            if (indx == start) {
                break;
            }
        }
        return null;
    }

    public void removeLeastHittedElement() {
        int minValue = 0;
        int minValueIndex = 0;
        for (int i = 0; i < capacity; i++) {
            if (hits[i] < minValue) {
                minValueIndex = i;
                minValue = hits[i];
            }
        }

        slots[minValueIndex] = null;
        values[minValueIndex] = null;
        hits[minValueIndex] = null;
        size--;
    }

    public T loadValue(String key) {
        return (T) ("mock_for___" + key);
    }

    public int size() {
        return this.size;
    }

    public T getOrLoad(String key) {
        T cachedValue = this.get(key);
        if (cachedValue != null) {
            int indx = this.seekSlot(key);
            hits[indx] += 1;
            return cachedValue;
        }

        put(key);
        return this.get(key);
    }

}

