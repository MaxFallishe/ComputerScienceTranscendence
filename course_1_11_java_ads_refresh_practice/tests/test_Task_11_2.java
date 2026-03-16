import org.junit.Test;

import static org.junit.Assert.*;


public class BloomFilterSecondAdditionTest {

    @Test
    public void testAddWithZeroValuesAndSmallFilterSize() {
        BloomFilterSecondAddition bloomFilter = new BloomFilterSecondAddition(10);

        assertFalse(bloomFilter.isValue("chain"));
        assertFalse(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithFewValuesAndSmallFilterSize() {
        BloomFilterSecondAddition bloomFilter = new BloomFilterSecondAddition(10);

        bloomFilter.add("chain");
        bloomFilter.add("rezo");

        assertTrue(bloomFilter.isValue("chain"));
        assertTrue(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithZeroValuesAndMediumFilterSize() {
        BloomFilterSecondAddition bloomFilter = new BloomFilterSecondAddition(32);

        assertFalse(bloomFilter.isValue("chain"));
        assertFalse(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithFewValuesAndMediumFilterSize() {
        BloomFilterSecondAddition bloomFilter = new BloomFilterSecondAddition(32);

        bloomFilter.add("chain");
        bloomFilter.add("rezo");

        assertTrue(bloomFilter.isValue("chain"));
        assertTrue(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithShiftedArrayAndMediumFilterSize() {
        BloomFilterSecondAddition bloomFilter = new BloomFilterSecondAddition(32);

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
        BloomFilterSecondAddition bloomFilter = new BloomFilterSecondAddition(10);

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
        BloomFilterSecondAddition bloomFilter1 = new BloomFilterSecondAddition(32);
        BloomFilterSecondAddition bloomFilter2 = new BloomFilterSecondAddition(32);
        BloomFilterSecondAddition bloomFilter3 = new BloomFilterSecondAddition(32);

        bloomFilter1.merge(new BloomFilterSecondAddition[]{bloomFilter2, bloomFilter3});

        assertFalse(bloomFilter1.isValue("chain"));
        assertFalse(bloomFilter1.isValue("rezo"));
        assertFalse(bloomFilter1.isValue("zero"));
        assertFalse(bloomFilter1.isValue("goal"));
        assertFalse(bloomFilter1.isValue("rizo"));
        assertFalse(bloomFilter1.isValue("incha"));
    }

    @Test
    public void testMergeWithFewValuesAndMediumFilterSize() {
        BloomFilterSecondAddition bloomFilter1 = new BloomFilterSecondAddition(32);
        BloomFilterSecondAddition bloomFilter2 = new BloomFilterSecondAddition(32);
        BloomFilterSecondAddition bloomFilter3 = new BloomFilterSecondAddition(32);

        bloomFilter1.add("chain");
        bloomFilter1.add("rezo");

        bloomFilter2.add("rezo");
        bloomFilter2.add("rezo");

        bloomFilter3.add("zero");
        bloomFilter3.add("goal");

        bloomFilter1.merge(new BloomFilterSecondAddition[]{bloomFilter2, bloomFilter3});

        assertTrue(bloomFilter1.isValue("chain"));
        assertTrue(bloomFilter1.isValue("rezo"));
        assertTrue(bloomFilter1.isValue("zero"));
        assertTrue(bloomFilter1.isValue("goal"));
        assertFalse(bloomFilter1.isValue("rizo"));
        assertFalse(bloomFilter1.isValue("incha"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMergeWithFewValuesAndMediumFilterSizeException() {
        BloomFilterSecondAddition bloomFilter1 = new BloomFilterSecondAddition(32);
        BloomFilterSecondAddition bloomFilter2 = new BloomFilterSecondAddition(32);
        BloomFilterSecondAddition bloomFilter3 = new BloomFilterSecondAddition(31);

        bloomFilter1.add("chain");
        bloomFilter1.add("rezo");

        bloomFilter2.add("rezo");
        bloomFilter2.add("rezo");

        bloomFilter3.add("zero");
        bloomFilter3.add("goal");

        bloomFilter1.merge(new BloomFilterSecondAddition[]{bloomFilter2, bloomFilter3});

        assertTrue(bloomFilter1.isValue("chain"));
        assertTrue(bloomFilter1.isValue("rezo"));
        assertTrue(bloomFilter1.isValue("zero"));
        assertTrue(bloomFilter1.isValue("goal"));
        assertFalse(bloomFilter1.isValue("rizo"));
        assertFalse(bloomFilter1.isValue("incha"));
    }

    @Test
    public void testmergeWithShiftedArrayAndSmallFilterSize() {
        BloomFilterSecondAddition bloomFilter1 = new BloomFilterSecondAddition(32);
        BloomFilterSecondAddition bloomFilter2 = new BloomFilterSecondAddition(32);
        BloomFilterSecondAddition bloomFilter3 = new BloomFilterSecondAddition(32);

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

        bloomFilter1.merge(new BloomFilterSecondAddition[]{bloomFilter2, bloomFilter3});

        assertFalse(bloomFilter1.isValue("rezo"));
    }

    @Test
    public void testRemoveWithFewValuesAndMediumFilterSize() {
        BloomFilterSecondAddition bloomFilter1 = new BloomFilterSecondAddition(32);

        bloomFilter1.add("chain");
        bloomFilter1.add("rezo");

        assertTrue(bloomFilter1.isValue("chain"));
        assertTrue(bloomFilter1.isValue("rezo"));

        bloomFilter1.remove("chain");
        bloomFilter1.remove("rezo");

        assertFalse(bloomFilter1.isValue("chain"));
        assertFalse(bloomFilter1.isValue("rezo"));
    }


    @Test
    public void testRemoveWithNonExistedValuesAndMediumFilterSize() {
        BloomFilterSecondAddition bloomFilter1 = new BloomFilterSecondAddition(32);

        bloomFilter1.add("chain");
        bloomFilter1.add("rezo");

        assertTrue(bloomFilter1.isValue("chain"));
        assertTrue(bloomFilter1.isValue("rezo"));

        bloomFilter1.remove("wall");
        bloomFilter1.remove("lazzy");

        assertTrue(bloomFilter1.isValue("chain"));
        assertTrue(bloomFilter1.isValue("rezo"));
    }

    @Test
    public void testRemoveWithDoubleActionAndMediumFilterSize() {
        BloomFilterSecondAddition bloomFilter1 = new BloomFilterSecondAddition(32);

        bloomFilter1.add("chain");
        bloomFilter1.add("rezo");

        assertTrue(bloomFilter1.isValue("chain"));
        assertTrue(bloomFilter1.isValue("rezo"));

        bloomFilter1.remove("chain");
        bloomFilter1.remove("chain");
        bloomFilter1.remove("chain");

        assertFalse(bloomFilter1.isValue("chain"));
        assertTrue(bloomFilter1.isValue("rezo"));
    }

    @Test
    public void testRemoveWithZeroElementsAndMediumFilterSize() {
        BloomFilterSecondAddition bloomFilter1 = new BloomFilterSecondAddition(32);

        bloomFilter1.remove("chain");
        bloomFilter1.remove("wall");
        bloomFilter1.remove("lazzy");

        assertFalse(bloomFilter1.isValue("chain"));
        assertFalse(bloomFilter1.isValue("lazzy"));
        assertFalse(bloomFilter1.isValue("wall"));
        assertFalse(bloomFilter1.isValue("rezo"));
        assertFalse(bloomFilter1.isValue("zero"));

    }

}

