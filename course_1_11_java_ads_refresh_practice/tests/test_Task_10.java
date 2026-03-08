import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

// Task number: 10.3
// Short description: Implement tests for NativeDictionary class.
public class PowerSetTest {

    @Test
    public void testSizeWithZeroElements() {
        PowerSet powerSet = new PowerSet();

        assertEquals(0, powerSet.size());
    }

    @Test
    public void testSizeWithOneElements() {
        PowerSet powerSet = new PowerSet();
        powerSet.put("app");

        assertEquals(1, powerSet.size());
    }

    @Test
    public void testSizeWithFewElements() {
        PowerSet powerSet = new PowerSet();
        powerSet.put("app");
        powerSet.put("arg");
        powerSet.put("all");

        assertEquals(3, powerSet.size());
    }

    @Test
    public void testSizeWithFewNotUniqueElements() {
        PowerSet powerSet = new PowerSet();
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
        PowerSet powerSet = new PowerSet();

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
        PowerSet powerSet = new PowerSet();
        powerSet.put("ankh");
        powerSet.put("ankh");
        powerSet.put("ankh");

        assertEquals(1, powerSet.size());
        assertTrue(powerSet.get("ankh"));
    }

    @Test
    public void testPutWithFewUniqueElements() {
        PowerSet powerSet = new PowerSet();
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
        PowerSet powerSet = new PowerSet();

        assertFalse(powerSet.remove("ankh"));
        assertFalse(powerSet.remove("ankh"));

        assertEquals(0, powerSet.size());
        assertFalse(powerSet.get("ankh"));
        assertFalse(powerSet.get("arr"));
    }

    @Test
    public void testRemoveWithFewUniqueElements() {
        PowerSet powerSet = new PowerSet();
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
        PowerSet powerSet = new PowerSet();
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
        PowerSet powerSet = new PowerSet();

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
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();

        PowerSet result = powerSet1.intersection(powerSet2);
        assertFalse(result.get("boil"));
        assertFalse(result.get("frozen"));
        assertEquals(0, result.size());
    }

    @Test
    public void testIntersectionWithFewElements() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSet result = powerSet1.intersection(powerSet2);
        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testIntersectionOfSomeElements() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSet result = powerSet1.intersection(powerSet2);
        assertTrue(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testUnionWithEmptySet() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();

        PowerSet result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testUnionOfSameElements() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSet result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testUnionOfSomeElements() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSet result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testUnionOfDifferentElements() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSet result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertEquals(4, result.size());
    }

    @Test
    public void testDifferenceWithEmptySet() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();

        PowerSet result = powerSet1.difference(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testDifferenceOfSameElements() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSet result = powerSet1.difference(powerSet2);

        assertFalse(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertFalse(result.get("boil"));
        assertEquals(0, result.size());
    }

    @Test
    public void testDifferenceOfSomeElements() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSet result = powerSet1.difference(powerSet2);

        assertTrue(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testDifferenceOfDifferentElements() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSet result = powerSet1.difference(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertEquals(2, result.size());
    }

    @Test
    public void testIsSubsetWithEmptySet() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();

        assertTrue(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testIsSubsetOfSameElements() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        assertTrue(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testIsSubsetOfSomeElements() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("hot");
        powerSet2.put("heat");

        assertTrue(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testIsSubsetFalseCase() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("frozen");
        powerSet2.put("heat");

        assertFalse(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testEqualsOfEmptySets() {
        PowerSet powerSet1 = new PowerSet();
        PowerSet powerSet2 = new PowerSet();

        assertTrue(powerSet1.equals(powerSet2));
    }

    @Test
    public void testEqualsOfSameElements() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        assertTrue(powerSet1.equals(powerSet2));
    }

    @Test
    public void testEqualsWithDifferentSizes() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("frozen");

        assertFalse(powerSet1.equals(powerSet2));
    }

    @Test
    public void testEqualsWithDifferentElements() {
        PowerSet powerSet1 = new PowerSet();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSet powerSet2 = new PowerSet();
        powerSet2.put("frozen");
        powerSet2.put("heat");

        assertFalse(powerSet1.equals(powerSet2));
    }
}

