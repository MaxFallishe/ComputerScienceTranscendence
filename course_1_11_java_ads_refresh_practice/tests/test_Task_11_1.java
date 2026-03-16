import org.junit.Test;

import static org.junit.Assert.*;


public class BloomFilterFirstAdditionTest {

    @Test
    public void testAddWithZeroValuesAndSmallFilterSize() {
        BloomFilterFirstAddition bloomFilter = new BloomFilterFirstAddition(10);

        assertFalse(bloomFilter.isValue("chain"));
        assertFalse(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithFewValuesAndSmallFilterSize() {
        BloomFilterFirstAddition bloomFilter = new BloomFilterFirstAddition(10);

        bloomFilter.add("chain");
        bloomFilter.add("rezo");

        assertTrue(bloomFilter.isValue("chain"));
        assertTrue(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithZeroValuesAndMediumFilterSize() {
        BloomFilterFirstAddition bloomFilter = new BloomFilterFirstAddition(32);

        assertFalse(bloomFilter.isValue("chain"));
        assertFalse(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithFewValuesAndMediumFilterSize() {
        BloomFilterFirstAddition bloomFilter = new BloomFilterFirstAddition(32);

        bloomFilter.add("chain");
        bloomFilter.add("rezo");

        assertTrue(bloomFilter.isValue("chain"));
        assertTrue(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithShiftedArrayAndMediumFilterSize() {
        BloomFilterFirstAddition bloomFilter = new BloomFilterFirstAddition(32);

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
        BloomFilterFirstAddition bloomFilter = new BloomFilterFirstAddition(10);

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

    @Test
    public void testMergeWithZeroValuesAndMediumFilterSize() {
        BloomFilterFirstAddition bloomFilter1 = new BloomFilterFirstAddition(32);
        BloomFilterFirstAddition bloomFilter2 = new BloomFilterFirstAddition(32);
        BloomFilterFirstAddition bloomFilter3 = new BloomFilterFirstAddition(32);

        bloomFilter1.merge(new BloomFilterFirstAddition[]{bloomFilter2, bloomFilter3});

        assertFalse(bloomFilter1.isValue("chain"));
        assertFalse(bloomFilter1.isValue("rezo"));
        assertFalse(bloomFilter1.isValue("zero"));
        assertFalse(bloomFilter1.isValue("goal"));
        assertFalse(bloomFilter1.isValue("rizo"));
        assertFalse(bloomFilter1.isValue("incha"));
    }

    @Test
    public void testMergeWithFewValuesAndMediumFilterSize() {
        BloomFilterFirstAddition bloomFilter1 = new BloomFilterFirstAddition(32);
        BloomFilterFirstAddition bloomFilter2 = new BloomFilterFirstAddition(32);
        BloomFilterFirstAddition bloomFilter3 = new BloomFilterFirstAddition(32);

        bloomFilter1.add("chain");
        bloomFilter1.add("rezo");

        bloomFilter2.add("rezo");
        bloomFilter2.add("rezo");

        bloomFilter3.add("zero");
        bloomFilter3.add("goal");

        bloomFilter1.merge(new BloomFilterFirstAddition[]{bloomFilter2, bloomFilter3});

        assertTrue(bloomFilter1.isValue("chain"));
        assertTrue(bloomFilter1.isValue("rezo"));
        assertTrue(bloomFilter1.isValue("zero"));
        assertTrue(bloomFilter1.isValue("goal"));
        assertFalse(bloomFilter1.isValue("rizo"));
        assertFalse(bloomFilter1.isValue("incha"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMergeWithFewValuesAndMediumFilterSizeException() {
        BloomFilterFirstAddition bloomFilter1 = new BloomFilterFirstAddition(32);
        BloomFilterFirstAddition bloomFilter2 = new BloomFilterFirstAddition(32);
        BloomFilterFirstAddition bloomFilter3 = new BloomFilterFirstAddition(31);

        bloomFilter1.add("chain");
        bloomFilter1.add("rezo");

        bloomFilter2.add("rezo");
        bloomFilter2.add("rezo");

        bloomFilter3.add("zero");
        bloomFilter3.add("goal");

        bloomFilter1.merge(new BloomFilterFirstAddition[]{bloomFilter2, bloomFilter3});

        assertTrue(bloomFilter1.isValue("chain"));
        assertTrue(bloomFilter1.isValue("rezo"));
        assertTrue(bloomFilter1.isValue("zero"));
        assertTrue(bloomFilter1.isValue("goal"));
        assertFalse(bloomFilter1.isValue("rizo"));
        assertFalse(bloomFilter1.isValue("incha"));
    }

    @Test
    public void testmergeWithShiftedArrayAndSmallFilterSize() {
        BloomFilterFirstAddition bloomFilter1 = new BloomFilterFirstAddition(32);
        BloomFilterFirstAddition bloomFilter2 = new BloomFilterFirstAddition(32);
        BloomFilterFirstAddition bloomFilter3 = new BloomFilterFirstAddition(32);

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
            bloomFilter1.add(element);
        }

        for (String element : elements) {
            assertTrue(bloomFilter1.isValue(element));
        }

        bloomFilter1.merge(new BloomFilterFirstAddition[]{bloomFilter2, bloomFilter3});

        assertFalse(bloomFilter1.isValue("rezo"));
    }

}

