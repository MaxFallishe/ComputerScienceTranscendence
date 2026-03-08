import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;


public class PowerSetSecondAdditionTest {

    @Test
    public void testSizeWithZeroElements() {
        PowerSetSecondAddition powerSet = new PowerSetSecondAddition();

        assertEquals(0, powerSet.size());
    }

    @Test
    public void testSizeWithOneElements() {
        PowerSetSecondAddition powerSet = new PowerSetSecondAddition();
        powerSet.put("app");

        assertEquals(1, powerSet.size());
    }

    @Test
    public void testSizeWithFewElements() {
        PowerSetSecondAddition powerSet = new PowerSetSecondAddition();
        powerSet.put("app");
        powerSet.put("arg");
        powerSet.put("all");

        assertEquals(3, powerSet.size());
    }

    @Test
    public void testSizeWithFewNotUniqueElements() {
        PowerSetSecondAddition powerSet = new PowerSetSecondAddition();
        powerSet.put("app");
        powerSet.put("app");
        powerSet.put("arg");

        powerSet.put("arg");
        powerSet.put("arg");
        powerSet.put("app");


        assertEquals(2, powerSet.size());
    }

    @Test
    public void testGetWithManyElements() {
        PowerSetSecondAddition powerSet = new PowerSetSecondAddition();

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
        PowerSetSecondAddition powerSet = new PowerSetSecondAddition();
        powerSet.put("ankh");
        powerSet.put("ankh");
        powerSet.put("ankh");

        assertEquals(1, powerSet.size());
        assertTrue(powerSet.get("ankh"));
    }

    @Test
    public void testPutWithFewUniqueElements() {
        PowerSetSecondAddition powerSet = new PowerSetSecondAddition();
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
        PowerSetSecondAddition powerSet = new PowerSetSecondAddition();

        assertFalse(powerSet.remove("ankh"));
        assertFalse(powerSet.remove("ankh"));

        assertEquals(0, powerSet.size());
        assertFalse(powerSet.get("ankh"));
        assertFalse(powerSet.get("arr"));
    }

    @Test
    public void testRemoveWithFewUniqueElements() {
        PowerSetSecondAddition powerSet = new PowerSetSecondAddition();
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
        PowerSetSecondAddition powerSet = new PowerSetSecondAddition();
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
        PowerSetSecondAddition powerSet = new PowerSetSecondAddition();

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
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();

        PowerSetSecondAddition result = powerSet1.intersection(powerSet2);
        assertFalse(result.get("boil"));
        assertFalse(result.get("frozen"));
        assertEquals(0, result.size());
    }

    @Test
    public void testIntersectionWithFewElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetSecondAddition result = powerSet1.intersection(powerSet2);
        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testIntersectionOfSomeElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetSecondAddition result = powerSet1.intersection(powerSet2);
        assertTrue(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testUnionWithEmptySet() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();

        PowerSetSecondAddition result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testUnionOfSameElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetSecondAddition result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testUnionOfSomeElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetSecondAddition result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testUnionOfDifferentElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSetSecondAddition result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertEquals(4, result.size());
    }

    @Test
    public void testDifferenceWithEmptySet() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();

        PowerSetSecondAddition result = powerSet1.difference(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testDifferenceOfSameElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetSecondAddition result = powerSet1.difference(powerSet2);

        assertFalse(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertFalse(result.get("boil"));
        assertEquals(0, result.size());
    }

    @Test
    public void testDifferenceOfSomeElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSetSecondAddition result = powerSet1.difference(powerSet2);

        assertTrue(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testDifferenceOfDifferentElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSetSecondAddition result = powerSet1.difference(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertEquals(2, result.size());
    }

    @Test
    public void testIsSubsetWithEmptySet() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();

        assertTrue(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testIsSubsetOfSameElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        assertTrue(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testIsSubsetOfSomeElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("hot");
        powerSet2.put("heat");

        assertTrue(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testIsSubsetFalseCase() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");

        assertFalse(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testEqualsOfEmptySets() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();

        assertTrue(powerSet1.equals(powerSet2));
    }

    @Test
    public void testEqualsOfSameElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        assertTrue(powerSet1.equals(powerSet2));
    }

    @Test
    public void testEqualsWithDifferentSizes() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("frozen");

        assertFalse(powerSet1.equals(powerSet2));
    }

    @Test
    public void testEqualsWithDifferentElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");

        assertFalse(powerSet1.equals(powerSet2));
    }

    @Test
    public void testWideIntersectionOfSameElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetSecondAddition powerSet3 = new PowerSetSecondAddition();
        powerSet3.put("frozen");
        powerSet3.put("heat");
        powerSet3.put("hot");
        powerSet3.put("boil");

        PowerSetSecondAddition result = PowerSetSecondAddition.wideIntersection(
                List.of(powerSet1, powerSet2, powerSet3)
        );
        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testWideIntersectionOfSomeElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("boil");

        PowerSetSecondAddition powerSet3 = new PowerSetSecondAddition();
        powerSet3.put("frozen");
        powerSet3.put("boil");
        powerSet3.put("steam");

        PowerSetSecondAddition result = PowerSetSecondAddition.wideIntersection(
                List.of(powerSet1, powerSet2, powerSet3)
        );
        assertTrue(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertTrue(result.get("boil"));
        assertFalse(result.get("steam"));
        assertEquals(2, result.size());
    }

    @Test
    public void testWideIntersectionOfZeroElements() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("boil");
        powerSet2.put("hot");

        PowerSetSecondAddition powerSet3 = new PowerSetSecondAddition();
        powerSet3.put("steam");
        powerSet3.put("warm");

        PowerSetSecondAddition result = PowerSetSecondAddition.wideIntersection(
                List.of(powerSet1, powerSet2, powerSet3)
        );
        assertFalse(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("boil"));
        assertFalse(result.get("hot"));
        assertFalse(result.get("steam"));
        assertFalse(result.get("warm"));
        assertEquals(0, result.size());
    }

    @Test
    public void testWideIntersectionOfFourSets() {
        PowerSetSecondAddition powerSet1 = new PowerSetSecondAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("boil");
        powerSet1.put("hot");

        PowerSetSecondAddition powerSet2 = new PowerSetSecondAddition();
        powerSet2.put("frozen");
        powerSet2.put("boil");
        powerSet2.put("hot");

        PowerSetSecondAddition powerSet3 = new PowerSetSecondAddition();
        powerSet3.put("frozen");
        powerSet3.put("boil");
        powerSet3.put("steam");

        PowerSetSecondAddition powerSet4 = new PowerSetSecondAddition();
        powerSet4.put("frozen");
        powerSet4.put("boil");
        powerSet4.put("water");

        PowerSetSecondAddition result = PowerSetSecondAddition.wideIntersection(
                List.of(powerSet1, powerSet2, powerSet3, powerSet4)
        );
        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertFalse(result.get("steam"));
        assertFalse(result.get("water"));
        assertEquals(2, result.size());
    }

}

