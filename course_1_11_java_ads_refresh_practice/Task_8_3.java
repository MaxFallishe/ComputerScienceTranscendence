import java.math.BigInteger;
import java.util.Objects;

// Task number: 8.3
// Short description: Implement a hash table with protection against collision attacks.
// ---Reflection.---
// In my case the "salt" method does not work and does not reduce the number of collisions if the original hash function does not
// imply dependence on the order of characters, as it turned out in these solutions.
// Unfortunately, the data set I selected for the attack could not use a static salt to resolve the problem with collisions.
// According to the reference version, making a static salt was not the best solution. With a static salt,
// the same values will be hashed to the same result. It is better to add a short dynamic salt to the values,
// add small random values to the hash, etc. And store in the table, respectively, an entity with two fields: value and salt.
public class HashTableThirdAddition {
    public int size;
    public int step;
    public int numElements = 0;
    public int collisionCounter = 0;
    public String[] slots;
    public static final int MAX_LOOPS_COUNT = 1000000;
    public static final String STATIC_SALT = "iWnW2qbc";

    public HashTableThirdAddition(int sz, int stp) {
        size = sz;
        step = stp;
        slots = new String[size];
        for (int i = 0; i < size; i++) slots[i] = null;
    }

    public int hashFun(String value) {
        int result = 0;
//        value = value + STATIC_SALT;
        int minusFlag = 0;
        for (int i = 0; i < value.length(); i++) {
            if (minusFlag == 0) {
                result += value.charAt(i);
                minusFlag = 1;
            } else {
                result -= value.charAt(i);
                minusFlag = 0;
            }
        }
        int firstChar = value.charAt(0);
        return normalizeIndx(result + firstChar);
    }

    public int seekSlot(String value) {
        if (this.size == this.numElements)
            return -1;

        int slotIndx = this.hashFun(value);
        for (int i = 0; i < MAX_LOOPS_COUNT; i++) {
            if (slots[slotIndx] == null)
                return slotIndx;
            collisionCounter += 1;
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
        return Math.floorMod(indx, this.size);
    }

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