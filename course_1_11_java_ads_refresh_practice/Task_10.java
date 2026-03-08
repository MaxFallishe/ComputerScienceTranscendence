// Task number: 10.1
// Short description: Implement PowerSet class.
public class PowerSet {

    public static final int HASH_TABLE_SIZE = 131072;
    public String[] slots;
    private int size;

    public PowerSet() {
        this.slots = new String[HASH_TABLE_SIZE];
        this.size = 0;
    }

    // Task number: 10.1.1
    // Short description: Implement a method that return information about powerset size.
    // Time complexity: O(1)
    // Space Complexity: O(1)
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

    // Task number: 10.1.2
    // Short description: Implement a method that putting value in powerset.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public void put(String value) {
        int indx = this.seekSlot(value);
        if (slots[indx] != null) {
            return;
        }
        slots[indx] = value;
        size += 1;
    }

    // Task number: 10.1.3
    // Short description: Implement a method that return true if value is exist in powerset.
    // Time complexity: O(1)
    // Space Complexity: O(1)
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

    // Task number: 10.2.1
    // Short description: Implement a method that remove value from powerset.
    // Time complexity: O(1)
    // Space Complexity: O(1)
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

    // Task number: 10.2.2
    // Short description: Implement a method that return an intersection of two powersets.
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public PowerSet intersection(PowerSet set2) {
        PowerSet result = new PowerSet();

        for (String value : this.slots) {
            if (value != null && set2.get(value)) {
                result.put(value);
            }
        }
        return result;
    }

    // Task number: 10.2.3
    // Short description: Implement a method that return a union of two powersets.
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public PowerSet union(PowerSet set2) {
        PowerSet result = new PowerSet();
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

    // Task number: 10.2.4
    // Short description: Implement a method that return a difference of two powersets.
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public PowerSet difference(PowerSet set2) {
        PowerSet result = new PowerSet();
        for (String value : this.slots) {
            if (value != null && !(set2.get(value)))
                result.put(value);
        }
        return result;
    }

    // Task number: 10.2.5
    // Short description: Implement a method that powerset contain specific subset.
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public boolean isSubset(PowerSet set2) {
        for (String value : set2.slots) {
            if (value != null && !(this.get(value)))
                return false;
        }
        return true;
    }

    // Task number: 10.2.6
    // Short description: Implement a method that return are two powersets are equal.
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public boolean equals(PowerSet set2) {
        if (this.size != set2.size)
            return false;

        for (String value : this.slots) {
            if (value != null && !(set2.get(value)))
                return false;
        }
        return true;
    }
}

