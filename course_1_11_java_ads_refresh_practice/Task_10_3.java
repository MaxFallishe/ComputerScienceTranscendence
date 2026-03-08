import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


// Task number: 10.6
public class PowerSetThirdAddition {

    public static final int HASH_TABLE_SIZE = 131072;
    public String[] slots;
    public Integer[] counters;
    private int size;

    public PowerSetThirdAddition() {
        this.slots = new String[HASH_TABLE_SIZE];
        this.counters = new Integer[HASH_TABLE_SIZE];
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public int customHash(String value) {
        int hashResult = value.hashCode();
        hashResult ^= (hashResult >>> 16);
        return hashResult & (HASH_TABLE_SIZE - 1);
    }

    public int seekSlot(String value) {
        int start = this.customHash(value);
        int indx = start;

        do {
            if (slots[indx] == null || slots[indx].equals(value)) {
                return indx;
            }
            indx = (indx + 1) & (HASH_TABLE_SIZE - 1);
        } while (indx != start);

        return -1;
    }

    public void put(String value) {
        int indx = this.seekSlot(value);
        if (slots[indx] != null) {
            counters[indx] += 1;
            return;
        }
        slots[indx] = value;
        counters[indx] = 1;
        size += 1;
    }

    public boolean get(String value) {
        int start = this.customHash(value);
        int indx = start;
        while (slots[indx] != null) {
            if (slots[indx].equals(value)) {
                return true;
            }
            indx = (indx + 1) & (HASH_TABLE_SIZE - 1);
            if (indx == start) {
                break;
            }
        }
        return false;
    }

    public boolean remove(String value) {
        int indx = seekSlot(value);
        if (indx == -1)
            return false;
        if (slots[indx] == null) {
            return false;
        }
        if (counters[indx] > 1) {
            counters[indx] -= 1;
            return true;
        }
        counters[indx] = null;
        slots[indx] = null;
        size -= 1;
        return true;
    }

    public PowerSetThirdAddition intersection(PowerSetThirdAddition set2) {
        PowerSetThirdAddition result = new PowerSetThirdAddition();

        for (String value : this.slots) {
            if (value != null && set2.get(value)) {
                result.put(value);
            }
        }
        return result;
    }

    public PowerSetThirdAddition union(PowerSetThirdAddition set2) {
        PowerSetThirdAddition result = new PowerSetThirdAddition();
        for (String value : this.slots) {
            if (value != null)
                result.put(value);
        }
        for (String value : set2.slots) {
            if (value != null)
                result.put(value);
        }

        return result;
    }

    public PowerSetThirdAddition difference(PowerSetThirdAddition set2) {
        PowerSetThirdAddition result = new PowerSetThirdAddition();
        for (String value : this.slots) {
            if (value != null && !(set2.get(value)))
                result.put(value);
        }
        return result;
    }

    public boolean isSubset(PowerSetThirdAddition set2) {
        for (String value : set2.slots) {
            if (value != null && !(this.get(value)))
                return false;
        }
        return true;
    }

    public boolean equals(PowerSetThirdAddition set2) {
        if (this.size != set2.size)
            return false;

        for (String value : this.slots) {
            if (value != null && !(set2.get(value)))
                return false;
        }
        return true;
    }

    // Task number: 10.6
    // Short description: Implement a method that return pairs (value + num of entries) in set (Bag).
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public Map<String, Integer> getFrequencies() {
        return IntStream.range(0, HASH_TABLE_SIZE)
                .filter(i -> slots[i] != null)
                .boxed()
                .collect(Collectors.toMap(
                        i -> slots[i],
                        i -> counters[i]
                ));
    }


}

