import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class BloomFilterThirdAdditionTest {

    @Test
    public void testAddWithZeroValuesAndSmallFilterSize() {
        BloomFilterThirdAddition bloomFilter = new BloomFilterThirdAddition(10);

        assertFalse(bloomFilter.isValue("chain"));
        assertFalse(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithFewValuesAndSmallFilterSize() {
        BloomFilterThirdAddition bloomFilter = new BloomFilterThirdAddition(10);

        bloomFilter.add("chain");
        bloomFilter.add("rezo");

        assertTrue(bloomFilter.isValue("chain"));
        assertTrue(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithZeroValuesAndMediumFilterSize() {
        BloomFilterThirdAddition bloomFilter = new BloomFilterThirdAddition(32);

        assertFalse(bloomFilter.isValue("chain"));
        assertFalse(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithFewValuesAndMediumFilterSize() {
        BloomFilterThirdAddition bloomFilter = new BloomFilterThirdAddition(32);

        bloomFilter.add("chain");
        bloomFilter.add("rezo");

        assertTrue(bloomFilter.isValue("chain"));
        assertTrue(bloomFilter.isValue("rezo"));
        assertFalse(bloomFilter.isValue("rizo"));
        assertFalse(bloomFilter.isValue("incha"));
    }

    @Test
    public void testAddWithShiftedArrayAndMediumFilterSize() {
        BloomFilterThirdAddition bloomFilter = new BloomFilterThirdAddition(32);

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
        BloomFilterThirdAddition bloomFilter = new BloomFilterThirdAddition(10);

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
        BloomFilterThirdAddition bloomFilter1 = new BloomFilterThirdAddition(32);
        BloomFilterThirdAddition bloomFilter2 = new BloomFilterThirdAddition(32);
        BloomFilterThirdAddition bloomFilter3 = new BloomFilterThirdAddition(32);

        bloomFilter1.merge(new BloomFilterThirdAddition[]{bloomFilter2, bloomFilter3});

        assertFalse(bloomFilter1.isValue("chain"));
        assertFalse(bloomFilter1.isValue("rezo"));
        assertFalse(bloomFilter1.isValue("zero"));
        assertFalse(bloomFilter1.isValue("goal"));
        assertFalse(bloomFilter1.isValue("rizo"));
        assertFalse(bloomFilter1.isValue("incha"));
    }

    @Test
    public void testMergeWithFewValuesAndMediumFilterSize() {
        BloomFilterThirdAddition bloomFilter1 = new BloomFilterThirdAddition(32);
        BloomFilterThirdAddition bloomFilter2 = new BloomFilterThirdAddition(32);
        BloomFilterThirdAddition bloomFilter3 = new BloomFilterThirdAddition(32);

        bloomFilter1.add("chain");
        bloomFilter1.add("rezo");

        bloomFilter2.add("rezo");
        bloomFilter2.add("rezo");

        bloomFilter3.add("zero");
        bloomFilter3.add("goal");

        bloomFilter1.merge(new BloomFilterThirdAddition[]{bloomFilter2, bloomFilter3});

        assertTrue(bloomFilter1.isValue("chain"));
        assertTrue(bloomFilter1.isValue("rezo"));
        assertTrue(bloomFilter1.isValue("zero"));
        assertTrue(bloomFilter1.isValue("goal"));
        assertFalse(bloomFilter1.isValue("rizo"));
        assertFalse(bloomFilter1.isValue("incha"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMergeWithFewValuesAndMediumFilterSizeException() {
        BloomFilterThirdAddition bloomFilter1 = new BloomFilterThirdAddition(32);
        BloomFilterThirdAddition bloomFilter2 = new BloomFilterThirdAddition(32);
        BloomFilterThirdAddition bloomFilter3 = new BloomFilterThirdAddition(31);

        bloomFilter1.add("chain");
        bloomFilter1.add("rezo");

        bloomFilter2.add("rezo");
        bloomFilter2.add("rezo");

        bloomFilter3.add("zero");
        bloomFilter3.add("goal");

        bloomFilter1.merge(new BloomFilterThirdAddition[]{bloomFilter2, bloomFilter3});

        assertTrue(bloomFilter1.isValue("chain"));
        assertTrue(bloomFilter1.isValue("rezo"));
        assertTrue(bloomFilter1.isValue("zero"));
        assertTrue(bloomFilter1.isValue("goal"));
        assertFalse(bloomFilter1.isValue("rizo"));
        assertFalse(bloomFilter1.isValue("incha"));
    }

    @Test
    public void testmergeWithShiftedArrayAndSmallFilterSize() {
        BloomFilterThirdAddition bloomFilter1 = new BloomFilterThirdAddition(32);
        BloomFilterThirdAddition bloomFilter2 = new BloomFilterThirdAddition(32);
        BloomFilterThirdAddition bloomFilter3 = new BloomFilterThirdAddition(32);

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

        bloomFilter1.merge(new BloomFilterThirdAddition[]{bloomFilter2, bloomFilter3});

        assertFalse(bloomFilter1.isValue("rezo"));
    }

    @Test
    public void testRecBuildCandidatesWithFewShortValuesAndMediumFilterSize() {
        BloomFilterThirdAddition bloomFilter = new BloomFilterThirdAddition(32);

        bloomFilter.add("ch");
        bloomFilter.add("re");

        List<String> result = bloomFilter.extractOriginalElements(2);
        assertEquals(88, result.size());  // 88 elements from 2 letters that fit, not really good
        assertTrue(bloomFilter.isValue("ch"));
        assertTrue(result.contains("re"));
        assertTrue(result.contains("ch"));

        assertFalse(result.contains("Zz"));
        assertFalse(result.contains("AA"));
    }

    @Test
    public void testRecBuildCandidatesWithFewMediumValuesAndMediumFilterSize() {
        BloomFilterThirdAddition bloomFilter = new BloomFilterThirdAddition(32);

        bloomFilter.add("chat");
        bloomFilter.add("recc");
        bloomFilter.add("NO");

        List<String> result = bloomFilter.extractOriginalElements(4);
        assertEquals(280976, result.size());  // 280976 elements from 4 letters that fit, not really good

        assertTrue(result.contains("chat"));
        assertTrue(result.contains("recc"));

        assertFalse(result.contains("zore"));
        assertFalse(result.contains("OooO"));
    }

}

