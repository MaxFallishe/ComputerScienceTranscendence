import java.util.ArrayList;
import java.util.List;

public class BloomFilterThirdAddition {
    public int filter_len;
    private long mainMask;

    public BloomFilterThirdAddition(int f_len) {
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

    public void merge(BloomFilterThirdAddition[] bloomFilters) {
        for (BloomFilterThirdAddition bloomFilter : bloomFilters) {
            if (this.filter_len != bloomFilter.filter_len) {
                throw new IllegalArgumentException("All bloom filters must have same lenght");
            }
            this.mainMask |= bloomFilter.mainMask;
        }
    }

    // Task number: 11.4
    // Short description: Try to implement method that reassemble original set of elements that was added in bloom's filter.
    // Time complexity: O(N^N)
    // Space Complexity: O(1)
    // Thoughts:
    // First of all, I want to start from hash functions and, depending on their implementation, try to make a solution
    // that could predict at least an approximate pool of values that might be inside. In the current implementation,
    // I tried to go through all possible combinations, but the moment with the resolution
    // of conditional collisions did not work out.
    // ---Reflection.---
    // The reference solution highlights the fact that any other more efficient solution besides brute force is
    // unlikely to be possible, but it offers potential options such as studying the domain of input data,
    // using some kind of similarity metrics, Bayesian approximation, and iteration attempts to avoid false positives.
    // In the current solution, there was an attempt to restore the source data through brute force, however,
    // even with a small number of embedded values, the method produced too many potentially corresponding
    // values of the set.
    public List<String> extractOriginalElements(int targetLen) {
        String bruteForceList = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<String> result = new ArrayList<>();
        if (targetLen <= 0)
            return result;

        recBuildCandidates("", targetLen, bruteForceList, result);
        return result;

    }

    public void recBuildCandidates(String prefix, int remaining, String bruteForceList, List<String> result) {
        if (remaining == 0) {
            if (isValue(prefix))
                result.add(prefix);
            return;
        }
        for (int i = 0; i < bruteForceList.length(); i++) {
            recBuildCandidates(prefix + bruteForceList.charAt(i), remaining - 1, bruteForceList, result);
        }
    }


}

