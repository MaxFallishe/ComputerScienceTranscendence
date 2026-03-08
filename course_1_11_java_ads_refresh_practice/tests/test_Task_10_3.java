import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;


public class PowerSetThirdAdditionTest {

    @Test
    public void testSizeWithZeroElements() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();

        assertEquals(0, powerSet.size());
    }

    @Test
    public void testSizeWithOneElements() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();
        powerSet.put("app");

        assertEquals(1, powerSet.size());
    }

    @Test
    public void testSizeWithFewElements() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();
        powerSet.put("app");
        powerSet.put("arg");
        powerSet.put("all");

        assertEquals(3, powerSet.size());
    }

    @Test
    public void testSizeWithFewNotUniqueElements() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();
        powerSet.put("app");
        powerSet.put("app");
        powerSet.put("arg");

        powerSet.put("arg");
        powerSet.put("arg");
        powerSet.put("app");


        assertEquals(2, powerSet.size());
        assertEquals(2, powerSet.size());
    }

    @Test
    public void testGetWithManyElements() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();

        int count = 20000;
        for (int i = 0; i < count; i++) {
            powerSet.put("value_" + i);
        }

        assertEquals(count, powerSet.size());

        for (int i = 0; i < count; i++) {
            assertTrue(powerSet.get("value_" + i));
        }

        assertFalse(powerSet.get("value_25001"));
        assertFalse(powerSet.get("not_existing"));
    }

    @Test
    public void testPutWithSoloUniqueElement() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();
        powerSet.put("ankh");
        powerSet.put("ankh");
        powerSet.put("ankh");

        assertEquals(1, powerSet.size());
        assertTrue(powerSet.get("ankh"));
    }

    @Test
    public void testPutWithFewUniqueElements() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();
        powerSet.put("ankh");
        powerSet.put("arr");
        powerSet.put("azz");
        powerSet.put("avv");
        powerSet.put("avd");

        assertEquals(5, powerSet.size());
        assertTrue(powerSet.get("ankh"));
        assertTrue(powerSet.get("arr"));
        assertTrue(powerSet.get("azz"));
        assertTrue(powerSet.get("avv"));
        assertTrue(powerSet.get("avd"));
    }


    @Test
    public void testRemoveNonExistElement() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();

        assertFalse(powerSet.remove("ankh"));
        assertFalse(powerSet.remove("ankh"));

        assertEquals(0, powerSet.size());
        assertFalse(powerSet.get("ankh"));
        assertFalse(powerSet.get("arr"));
    }

    @Test
    public void testRemoveWithFewUniqueElements() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();
        powerSet.put("ankh");
        powerSet.put("arr");
        powerSet.put("azz");
        powerSet.put("avv");
        powerSet.put("avd");

        assertTrue(powerSet.remove("ankh"));
        assertFalse(powerSet.remove("ankh"));

        assertEquals(4, powerSet.size());
        assertFalse(powerSet.get("ankh"));
        assertTrue(powerSet.get("arr"));
        assertTrue(powerSet.get("azz"));
        assertTrue(powerSet.get("avv"));
        assertTrue(powerSet.get("avd"));
    }

    @Test
    public void testRemoveWithFewUniqueElementsAndFullWipe() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();
        powerSet.put("ankh");
        powerSet.put("arr");
        powerSet.put("azz");
        powerSet.put("avv");
        powerSet.put("avd");

        assertTrue(powerSet.remove("ankh"));
        assertTrue(powerSet.remove("arr"));
        assertTrue(powerSet.remove("azz"));
        assertTrue(powerSet.remove("avv"));
        assertTrue(powerSet.remove("avd"));

        assertEquals(0, powerSet.size());
        assertFalse(powerSet.get("ankh"));
        assertFalse(powerSet.get("arr"));
        assertFalse(powerSet.get("azz"));
        assertFalse(powerSet.get("avv"));
        assertFalse(powerSet.get("avd"));
    }

    @Test
    public void testRemoveWithManyElements() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();

        int count = 20000;
        for (int i = 0; i < count; i++) {
            powerSet.put("value_" + i);
        }

        assertEquals(count, powerSet.size());

        for (int i = 0; i < count; i++) {
            assertTrue(powerSet.get("value_" + i));
        }

        assertTrue(powerSet.get("value_5000"));

        powerSet.put("nitro");
        assertTrue(powerSet.get("nitro"));

        assertTrue(powerSet.remove("value_5000"));
        assertFalse(powerSet.remove("value_5000"));
        assertFalse(powerSet.get("value_5000"));

    }

    @Test
    public void testIntersectionOfZeroElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();

        PowerSetThirdAddition result = powerSet1.intersection(powerSet2);
        assertFalse(result.get("boil"));
        assertFalse(result.get("frozen"));
        assertEquals(0, result.size());
    }

    @Test
    public void testIntersectionWithFewElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetThirdAddition result = powerSet1.intersection(powerSet2);
        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testIntersectionOfSomeElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetThirdAddition result = powerSet1.intersection(powerSet2);
        assertTrue(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testUnionWithEmptySet() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();

        PowerSetThirdAddition result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testUnionOfSameElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetThirdAddition result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testUnionOfSomeElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetThirdAddition result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testUnionOfDifferentElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSetThirdAddition result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertEquals(4, result.size());
    }

    @Test
    public void testDifferenceWithEmptySet() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();

        PowerSetThirdAddition result = powerSet1.difference(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testDifferenceOfSameElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetThirdAddition result = powerSet1.difference(powerSet2);

        assertFalse(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertFalse(result.get("boil"));
        assertEquals(0, result.size());
    }

    @Test
    public void testDifferenceOfSomeElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSetThirdAddition result = powerSet1.difference(powerSet2);

        assertTrue(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testDifferenceOfDifferentElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSetThirdAddition result = powerSet1.difference(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertEquals(2, result.size());
    }

    @Test
    public void testIsSubsetWithEmptySet() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();

        assertTrue(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testIsSubsetOfSameElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        assertTrue(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testIsSubsetOfSomeElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("hot");
        powerSet2.put("heat");

        assertTrue(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testIsSubsetFalseCase() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");

        assertFalse(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testEqualsOfEmptySets() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();

        assertTrue(powerSet1.equals(powerSet2));
    }

    @Test
    public void testEqualsOfSameElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        assertTrue(powerSet1.equals(powerSet2));
    }

    @Test
    public void testEqualsWithDifferentSizes() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("frozen");

        assertFalse(powerSet1.equals(powerSet2));
    }

    @Test
    public void testEqualsWithDifferentElements() {
        PowerSetThirdAddition powerSet1 = new PowerSetThirdAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetThirdAddition powerSet2 = new PowerSetThirdAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");

        assertFalse(powerSet1.equals(powerSet2));
    }

    @Test
    public void testFrequenciesWithZeroElements() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();

        Map<String, Integer> frequencies = powerSet.getFrequencies();
        assertTrue(frequencies.isEmpty());
        assertEquals(0, powerSet.size());
    }

    @Test
    public void testFrequenciesWithSoloElement() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();
        powerSet.put("ankh");
        powerSet.put("ankh");
        powerSet.put("ankh");

        Map<String, Integer> frequencies = powerSet.getFrequencies();
        assertEquals(1, frequencies.size());
        assertEquals(Integer.valueOf(3), frequencies.get("ankh"));
        assertEquals(1, powerSet.size());
    }

    @Test
    public void testFrequenciesWithFewElements() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();
        powerSet.put("ankh");
        powerSet.put("ankh");
        powerSet.put("arr");
        powerSet.put("arr");
        powerSet.put("arr");
        powerSet.put("azz");

        Map<String, Integer> frequencies = powerSet.getFrequencies();
        assertEquals(3, frequencies.size());
        assertEquals(Integer.valueOf(2), frequencies.get("ankh"));
        assertEquals(Integer.valueOf(3), frequencies.get("arr"));
        assertEquals(Integer.valueOf(1), frequencies.get("azz"));
        assertEquals(3, powerSet.size());
    }

    @Test
    public void testFrequenciesAfterRemoveOneElement() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();
        powerSet.put("ankh");
        powerSet.put("ankh");
        powerSet.put("ankh");

        assertTrue(powerSet.remove("ankh"));

        Map<String, Integer> frequencies = powerSet.getFrequencies();
        assertEquals(1, frequencies.size());
        assertEquals(Integer.valueOf(2), frequencies.get("ankh"));
        assertEquals(1, powerSet.size());
    }

    @Test
    public void testFrequenciesAfterFullRemoveElement() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();
        powerSet.put("ankh");
        powerSet.put("ankh");

        assertTrue(powerSet.remove("ankh"));
        assertTrue(powerSet.remove("ankh"));

        Map<String, Integer> frequencies = powerSet.getFrequencies();
        assertFalse(frequencies.containsKey("ankh"));
        assertTrue(frequencies.isEmpty());
        assertEquals(0, powerSet.size());
    }

    @Test
    public void testFrequenciesWithManyRepeatedElements() {
        PowerSetThirdAddition powerSet = new PowerSetThirdAddition();

        for (int i = 0; i < 5000; i++) {
            powerSet.put("ankh");
            powerSet.put("arr");
        }
        powerSet.put("azz");

        Map<String, Integer> frequencies = powerSet.getFrequencies();
        assertEquals(3, frequencies.size());
        assertEquals(Integer.valueOf(5000), frequencies.get("ankh"));
        assertEquals(Integer.valueOf(5000), frequencies.get("arr"));
        assertEquals(Integer.valueOf(1), frequencies.get("azz"));
        assertEquals(3, powerSet.size());
    }

}

