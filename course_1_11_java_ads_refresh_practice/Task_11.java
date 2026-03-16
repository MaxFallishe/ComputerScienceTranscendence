public class BloomFilter {
    public int filter_len;
    private long mainMask;

    public BloomFilter(int f_len) {
        filter_len = f_len;
        mainMask = 0L;
    }

    // Task number: 11.1.1
    // Short description: Implement a method that compute hash from a string.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public int hash1(String str1) {
        int result = 0;
        int salt = 17;
        for (int i = 0; i < str1.length(); i++) {
            int code = (int) str1.charAt(i);
            result = (result * salt + code) % filter_len;
        }
        return result;
    }

    // Task number: 11.1.2
    // Short description: Implement method that compute different hash from a string.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public int hash2(String str1) {
        int result = 0;
        int salt = 223;
        for (int i = 0; i < str1.length(); i++) {
            int code = (int) str1.charAt(i);
            result = (result * salt + code) % filter_len;
        }
        return result;
    }

    // Task number: 11.1.3
    // Short description: Implement method that add value into bloomfilter.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public void add(String str1) {
        mainMask |= (1L << hash1(str1));
        mainMask |= (1L << hash2(str1));
    }

    // Task number: 11.1.4
    // Short description: Implement method check value existing in bloomFilter.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public boolean isValue(String str1) {
        long verificationMask = 0L;
        verificationMask |= (1L << hash1(str1));
        verificationMask |= (1L << hash2(str1));
        return (mainMask & verificationMask) == verificationMask;
    }
}

