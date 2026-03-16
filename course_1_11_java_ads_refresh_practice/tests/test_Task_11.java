import org.junit.Test;

import static org.junit.Assert.*;


// Task number: 11.1.5
// Short description: Implement tests for BloomFilter class.
public class BloomFilterTest {

    @Test
    public void testAddWithZeroValuesAndSmallFilterSize() {
        BloomFilter bloomFilter = new BloomFilter(10);

        assertFalse(bloomFilter.isValue("chain"));
        assertFalse(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithFewValuesAndSmallFilterSize() {
        BloomFilter bloomFilter = new BloomFilter(10);

        bloomFilter.add("chain");
        bloomFilter.add("rezo");

        assertTrue(bloomFilter.isValue("chain"));
        assertTrue(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithZeroValuesAndMediumFilterSize() {
        BloomFilter bloomFilter = new BloomFilter(32);

        assertFalse(bloomFilter.isValue("chain"));
        assertFalse(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithFewValuesAndMediumFilterSize() {
        BloomFilter bloomFilter = new BloomFilter(32);

        bloomFilter.add("chain");
        bloomFilter.add("rezo");

        assertTrue(bloomFilter.isValue("chain"));
        assertTrue(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithShiftedArrayAndMediumFilterSize() {
        BloomFilter bloomFilter = new BloomFilter(32);

        String[] elements = {
                "0123456789",
                "1234567890",
                "2345678901",
                "3456789012",
                "4567890123",
                "5678901234",
                "6789012345",
                "7890123456",
                "8901234567",
                "9012345678",
        };

        for (String element : elements) {
            bloomFilter.add(element);
        }

        for (String element : elements) {
            assertTrue(bloomFilter.isValue(element));
        }

    }


    @Test
    public void testAddWithShiftedArrayAndSmallFilterSize() {
        BloomFilter bloomFilter = new BloomFilter(10);

        String[] elements = {
                "0123456789",
                "1234567890",
                "2345678901",
                "3456789012",
                "4567890123",
                "5678901234",
                "6789012345",
                "7890123456",
                "8901234567",
                "9012345678",
        };

        for (String element : elements) {
            bloomFilter.add(element);
        }

        for (String element : elements) {
            assertTrue(bloomFilter.isValue(element));
        }

        assertFalse(bloomFilter.isValue("rezo"));
    }
}

