public class BloomFilterFirstAddition {
    public int filter_len;
    private long mainMask;

    public BloomFilterFirstAddition(int f_len) {
        filter_len = f_len;
        mainMask = 0L;
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
        mainMask |= (1L << hash1(str1));
        mainMask |= (1L << hash2(str1));
    }


    public boolean isValue(String str1) {
        long verificationMask = 0L;
        verificationMask |= (1L << hash1(str1));
        verificationMask |= (1L << hash2(str1));
        return (mainMask & verificationMask) == verificationMask;
    }

    // Task number: 11.2
    // Short description: Implement method for merging few bloom filter in one;
    // Time complexity: O(K), k - bloomFilters length
    // Space Complexity: O(1)
    public void merge(BloomFilterFirstAddition[] bloomFilters) {
        for (BloomFilterFirstAddition bloomFilter : bloomFilters) {
            if (this.filter_len != bloomFilter.filter_len) {
                throw new IllegalArgumentException("All bloom filters must have same lenght");
            }
            this.mainMask |= bloomFilter.mainMask;
        }
    }

}


