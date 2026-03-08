import java.util.Objects;
import java.math.BigInteger;

// Task number: 8.1
// Short description: Implement a dynamic hash table.
// ---Reflection.---
// Implementing a dynamic hash table turned out to be a fairly simple task from the point of view of the required
// algorithm. It is necessary to set the required limit at which the hash table will expand (in my solution 100%,
// in the reference 80% is proposed) and then recreate the hash table in a larger array. The most important thing
// is that for each element, you need to recalculate its order in a new array, because if we try to move elements
// from their current positions, then there will be a situation with duplicate values in completely different
// locations of the array + problems with deleting existing values since the position of the hash function will
// not match.
public class HashTableFirstAddition {
    public int size;
    public int step;
    public int numElements = 0;
    public String[] slots;
    public static final int MAX_LOOPS_COUNT = 1000000;

    public HashTableFirstAddition(int sz, int stp) {
        size = sz;
        step = stp;
        slots = new String[size];
        for (int i = 0; i < size; i++) slots[i] = null;
    }

    public int hashFun(String value) {
        int result = 0;
        for (int i = 0; i < value.length(); i++) {
            result += value.charAt(i);
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

    public int put(String value) {
        int slotIndx = this.seekSlot(value);
        if (slotIndx == -1) {
            extendSlots();
            slotIndx = this.seekSlot(value);
        }

        slots[slotIndx] = value;
        this.numElements += 1;
        return slotIndx;
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

    public int normalizeIndx(int indx) {
        return indx % this.size;
    }

    // Task number: 8.1.1
    // Short description: Implement a dynamic hash table.
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public void extendSlots() {
        long increasedSize = size * 2L;
        BigInteger n = BigInteger.valueOf(increasedSize);
        BigInteger nextPrime = n.nextProbablePrime();
        int newSize = Integer.parseInt(String.valueOf(nextPrime));
        String[] newSlots = new String[newSize];
        int oldSize = size;
        String[] oldSlots = slots;

        this.size = newSize;
        this.slots = newSlots;
        this.numElements = 0;

        for (int i = 0; i < oldSize; i++) {
            this.put(oldSlots[i]);
        }
    }
}

