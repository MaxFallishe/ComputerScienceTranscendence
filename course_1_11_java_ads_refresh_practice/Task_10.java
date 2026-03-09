// Task number: 10.1
// Short description: Implement PowerSet class.
public class PowerSet {

    public static final int HASH_TABLE_SIZE = 131072;
    public static final String STATIC_SALT = "FdbjTr";
    public java.util.ArrayList<String>[] slots;
    private int size;

    public PowerSet() {
        slots = (java.util.ArrayList<String>[]) new java.util.ArrayList[HASH_TABLE_SIZE];
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
        int hashResult = (value + STATIC_SALT).hashCode();
        hashResult ^= (hashResult >>> 16);
        return hashResult & (HASH_TABLE_SIZE - 1);
    }

    // Task number: 10.1.2
    // Short description: Implement a method that putting value in powerset.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public void put(String value) {
        int index = customHash(value);

        if (slots[index] == null)
            slots[index] = new java.util.ArrayList<>();

        if (!slots[index].contains(value)) {
            slots[index].add(value);
            size += 1;
        }
    }

    // Task number: 10.1.3
    // Short description: Implement a method that return true if value is exist in powerset.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public boolean get(String value) {
        int indx = customHash(value);

        if (slots[indx] == null)
            return false;
        return slots[indx].contains(value);
    }

    // Task number: 10.2.1
    // Short description: Implement a method that remove value from powerset.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public boolean remove(String value) {
        int indx = customHash(value);
        if (this.slots[indx] == null)
            return false;
        boolean removed = slots[indx].remove(value);
        if (removed)
            size -= 1;
        if (slots[indx].isEmpty())
            slots[indx] = null;
        return removed;
    }

    // Task number: 10.2.2
    // Short description: Implement a method that return an intersection of two powersets.
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public PowerSet intersection(PowerSet set2) {
        PowerSet result = new PowerSet();

        for (java.util.ArrayList<String> bucket : this.slots) {
            if (bucket == null)
                continue;
            for (String value : bucket) {
                if (set2.get(value))
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
        for (java.util.ArrayList<String> bucket : this.slots) {
            if (bucket == null)
                continue;
            for (String value : bucket) {
                result.put(value);
            }
        }

        for (java.util.ArrayList<String> bucket : set2.slots) {
            if (bucket == null)
                continue;
            for (String value : bucket) {
                result.put(value);
            }
        }

        return result;
    }

    // Task number: 10.2.4
    // Short description: Implement a method that return a difference of two powersets.
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public PowerSet difference(PowerSet set2) {
        PowerSet result = new PowerSet();

        for (java.util.ArrayList<String> bucket : this.slots) {
            if (bucket == null)
                continue;
            for (String value : bucket) {
                if (!set2.get(value)) {
                    result.put(value);
                }
            }
        }

        return result;
    }

    // Task number: 10.2.5
    // Short description: Implement a method that powerset contain specific subset.
    // Time complexity: O(N)
    // Space Complexity: O(N)
    public boolean isSubset(PowerSet set2) {
        for (java.util.ArrayList<String> bucket : set2.slots) {
            if (bucket == null)
                continue;
            for (String value : bucket) {
                if (!this.get(value)) {
                    return false;
                }
            }

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

        for (java.util.ArrayList<String> bucket : this.slots) {
            if (bucket == null)
                continue;
            for (String value : bucket) {
                if (!set2.get(value)) {
                    return false;
                }
            }

        }
        return true;
    }
}

