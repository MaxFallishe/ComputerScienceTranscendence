import java.lang.reflect.Array;

class NativeDictionarySecondAddition<T> {

    public String[] slots;
    public T[] values;
    public int numElements = 0;
    public int size = 0;
    public final int step = 3;
    public static final int MAX_LOOPS_COUNT = 1000000;
    private final int bitLen;
    private final long[] keyBits;
    private final byte[] stateArr;

    public NativeDictionarySecondAddition(int size, Class clazz, int bitLen) {
        if (bitLen <= 0 || bitLen > 63) throw new IllegalArgumentException("Only values: 1..63");
        this.size = size;
        this.bitLen = bitLen;

        slots = new String[size];
        values = (T[]) Array.newInstance(clazz, this.size);

        keyBits = new long[size];
        stateArr = new byte[size];
    }

    private long parse(String key) {
        long x = 0;
        for (int i = 0; i < bitLen; i++) {
            char c = key.charAt(i);
            x = (x << 1) | (c == '1' ? 1L : 0L);
            if (c != '0' && c != '1')
                throw new IllegalArgumentException();
        }
        return x;
    }

    public int hashBits(long x) {
        x ^= (x >>> 16);
        return normalizeIndx((int) x);
    }

    public int normalizeIndx(int indx) {
        if ((size & (size - 1)) == 0)
            return indx & (size - 1);
        return indx % size;
    }

    public int find(String key) {
        long kb = parse(key);
        int slotIndx = hashBits(kb);

        for (int i = 0; i < MAX_LOOPS_COUNT; i++) {
            byte st = stateArr[slotIndx];
            if (st == 0)
                return -1;
            if (st == 1 && keyBits[slotIndx] == kb)
                return slotIndx;
            slotIndx = normalizeIndx(slotIndx + step);
        }
        return -1;
    }

    public int seekSlot(String key) {
        if (this.size == this.numElements) return -1;

        long kb = parse(key);
        int slotIndx = hashBits(kb);
        int firstDeleted = -1;

        for (int i = 0; i < MAX_LOOPS_COUNT; i++) {
            byte st = stateArr[slotIndx];

            if (st == 0) {
                return (firstDeleted != -1) ? firstDeleted : slotIndx;
            }
            if (st == 2 && firstDeleted == -1) {
                firstDeleted = slotIndx;
            }
            if (st == 1 && keyBits[slotIndx] == kb) {
                return slotIndx;
            }

            slotIndx = normalizeIndx(slotIndx + step);
        }
        return -1;
    }

    public boolean isKey(String key) {
        return find(key) != -1;
    }

    public void put(String key, T value) {
        int slotIndx = seekSlot(key);
        if (slotIndx == -1)
            return;

        long kb = parse(key);
        if (stateArr[slotIndx] != 1) {
            numElements += 1;
        }

        stateArr[slotIndx] = 1;
        keyBits[slotIndx] = kb;
        slots[slotIndx] = key;
        values[slotIndx] = value;
    }

    public T get(String key) {
        int indx = find(key);
        if (indx == -1)
            return null;
        return values[indx];
    }

    public boolean remove(String key) {
        int indx = find(key);
        if (indx == -1)
            return false;

        stateArr[indx] = 2;
        slots[indx] = null;
        values[indx] = null;
        numElements -= 1;
        return true;
    }
}

