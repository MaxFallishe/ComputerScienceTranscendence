// Task number: 10.4
public class PowerSetFirstAddition {

    public static final int HASH_TABLE_SIZE = 131072;
    public String[] slots;
    private int size;

    public PowerSetFirstAddition() {
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

    public PowerSetFirstAddition intersection(PowerSetFirstAddition set2) {
        PowerSetFirstAddition result = new PowerSetFirstAddition();

        for (String value : this.slots) {
            if (value != null && set2.get(value)) {
                result.put(value);
            }
        }
        return result;
    }

    public PowerSetFirstAddition union(PowerSetFirstAddition set2) {
        PowerSetFirstAddition result = new PowerSetFirstAddition();
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

    public PowerSetFirstAddition difference(PowerSetFirstAddition set2) {
        PowerSetFirstAddition result = new PowerSetFirstAddition();
        for (String value : this.slots) {
            if (value != null && !(set2.get(value)))
                result.put(value);
        }
        return result;
    }

    public boolean isSubset(PowerSetFirstAddition set2) {
        for (String value : set2.slots) {
            if (value != null && !(this.get(value)))
                return false;
        }
        return true;
    }

    public boolean equals(PowerSetFirstAddition set2) {
        if (this.size != set2.size)
            return false;

        for (String value : this.slots) {
            if (value != null && !(set2.get(value)))
                return false;
        }
        return true;
    }

    // Task number: 10.4
    // Short description: Implement a method that return cartesian product of two sets.
    // Time complexity: O(N*M)
    // Space Complexity: O(N*M)
    // ---Reflection.---
    // As part of the implementation of the method, it turned out to use the same algorithm as suggested
    // by the reference solution - two nested loops in which sets are multiplied through the creation
    // of tuples (although the current implementation uses just a string simulating the desired structure).
    // Nevertheless, the best solution would be to implement it through recursion, which supports multiplication
    // of several sets at once.
    public PowerSetFirstAddition cartesianProduct(PowerSetFirstAddition set2) {
        PowerSetFirstAddition result = new PowerSetFirstAddition();
        for (String value1 : this.slots) {
            if (value1 == null)
                continue;
            for (String value2 : set2.slots) {
                if (value2 == null)
                    continue;
                result.put("(" + value1 + "," + value2 + ")");
            }
        }
        return result;
    }
}

