import java.util.Arrays;
import java.util.List;

// Task number: 10.5
public class PowerSetSecondAddition {

    public static final int HASH_TABLE_SIZE = 131072;
    public String[] slots;
    private int size;

    public PowerSetSecondAddition() {
        this.slots = new String[HASH_TABLE_SIZE];
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
            return;
        }
        slots[indx] = value;
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
        slots[indx] = null;
        size -= 1;
        return true;
    }

    public PowerSetSecondAddition intersection(PowerSetSecondAddition set2) {
        PowerSetSecondAddition result = new PowerSetSecondAddition();

        for (String value : this.slots) {
            if (value != null && set2.get(value)) {
                result.put(value);
            }
        }
        return result;
    }

    public PowerSetSecondAddition union(PowerSetSecondAddition set2) {
        PowerSetSecondAddition result = new PowerSetSecondAddition();
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

    public PowerSetSecondAddition difference(PowerSetSecondAddition set2) {
        PowerSetSecondAddition result = new PowerSetSecondAddition();
        for (String value : this.slots) {
            if (value != null && !(set2.get(value)))
                result.put(value);
        }
        return result;
    }

    public boolean isSubset(PowerSetSecondAddition set2) {
        for (String value : set2.slots) {
            if (value != null && !(this.get(value)))
                return false;
        }
        return true;
    }

    public boolean equals(PowerSetSecondAddition set2) {
        if (this.size != set2.size)
            return false;

        for (String value : this.slots) {
            if (value != null && !(set2.get(value)))
                return false;
        }
        return true;
    }

    // Task number: 10.5
    // Short description: Implement a method that return intersection of few sets.
    // Time complexity: O(N*k)
    // Space Complexity: O(N*k)
    public static PowerSetSecondAddition wideIntersection(List<PowerSetSecondAddition> sets) {
        if (sets == null || sets.size() < 3) {
            throw new IllegalArgumentException("Should be at least 3 sets as input");
        }

        PowerSetSecondAddition result = new PowerSetSecondAddition();
        for (String value : sets.getFirst().slots) {
            if (value == null)
                continue;
            boolean existInAll = sets.stream()
                    .skip(1)
                    .allMatch(set -> set.get(value));
            if (existInAll) {
                result.put(value);
            }
        }
        return result;

    }

}

