public class BloomFilterSecondAddition {
    public int filter_len;
    private int[] mainMask;

    public BloomFilterSecondAddition(int f_len) {
        filter_len = f_len;
        mainMask = new int[filter_len];
    }

    public int hash1(String str1) {
        int result = 0;
        int salt = 17;
        for (int i = 0; i < str1.length(); i++) {
            int code = (int) str1.charAt(i);
            result = (result * salt + code) % filter_len;
        }
        return result;
    }

    public int hash2(String str1) {
        int result = 0;
        int salt = 223;
        for (int i = 0; i < str1.length(); i++) {
            int code = (int) str1.charAt(i);
            result = (result * salt + code) % filter_len;
        }
        return result;
    }

    public void add(String str1) {
        mainMask[this.hash1(str1)] += 1;
        mainMask[this.hash2(str1)] += 1;
    }

    public boolean isValue(String str1) {
        return mainMask[hash1(str1)] > 0 && mainMask[this.hash2(str1)] > 0;
    }

    public void merge(BloomFilterSecondAddition[] bloomFilters) {
        for (BloomFilterSecondAddition bloomFilter : bloomFilters) {
            if (this.filter_len != bloomFilter.filter_len) {
                throw new IllegalArgumentException("All bloom filters must have same lenght");
            }
            for (int i = 0; i< filter_len; i++) {
                this.mainMask[i] += bloomFilter.mainMask[i];
            }
        }
    }

    // Task number: 11.3
    // Short description: Implement method for remove element from bloom filter.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    // ---Reflection.---
    // In order to adapt the Blum Filter to the possibility of removing added elements in the current solution,
    // we had to change the class structure itself and add counters for each slot. This allows you to first check
    // the presence of an item in the value store, and then reduce the values of the desired cells. Unfortunately,
    // even in such an implementation, it is possible that after deleting an element, the element will somehow be
    // present in memory due to the fact that there are two other elements occupying the corresponding slots.
    public void remove(String str1) {
        int firstIndx = this.hash1(str1);
        int secondIndx = this.hash2(str1);

        if (!this.isValue(str1))
            return;

        if (mainMask[firstIndx] > 0)
            mainMask[firstIndx] -= 1;

        if (mainMask[secondIndx] > 0)
            mainMask[secondIndx] -= 1;
    }

}

