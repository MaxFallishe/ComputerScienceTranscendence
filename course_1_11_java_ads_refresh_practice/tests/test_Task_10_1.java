import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;


public class PowerSetFirstAdditionTest {

    @Test
    public void testSizeWithZeroElements() {
        PowerSetFirstAddition powerSet = new PowerSetFirstAddition();

        assertEquals(0, powerSet.size());
    }

    @Test
    public void testSizeWithOneElements() {
        PowerSetFirstAddition powerSet = new PowerSetFirstAddition();
        powerSet.put("app");

        assertEquals(1, powerSet.size());
    }

    @Test
    public void testSizeWithFewElements() {
        PowerSetFirstAddition powerSet = new PowerSetFirstAddition();
        powerSet.put("app");
        powerSet.put("arg");
        powerSet.put("all");

        assertEquals(3, powerSet.size());
    }

    @Test
    public void testSizeWithFewNotUniqueElements() {
        PowerSetFirstAddition powerSet = new PowerSetFirstAddition();
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
        PowerSetFirstAddition powerSet = new PowerSetFirstAddition();

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
        PowerSetFirstAddition powerSet = new PowerSetFirstAddition();
        powerSet.put("ankh");
        powerSet.put("ankh");
        powerSet.put("ankh");

        assertEquals(1, powerSet.size());
        assertTrue(powerSet.get("ankh"));
    }

    @Test
    public void testPutWithFewUniqueElements() {
        PowerSetFirstAddition powerSet = new PowerSetFirstAddition();
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
        PowerSetFirstAddition powerSet = new PowerSetFirstAddition();

        assertFalse(powerSet.remove("ankh"));
        assertFalse(powerSet.remove("ankh"));

        assertEquals(0, powerSet.size());
        assertFalse(powerSet.get("ankh"));
        assertFalse(powerSet.get("arr"));
    }

    @Test
    public void testRemoveWithFewUniqueElements() {
        PowerSetFirstAddition powerSet = new PowerSetFirstAddition();
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
        PowerSetFirstAddition powerSet = new PowerSetFirstAddition();
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
        PowerSetFirstAddition powerSet = new PowerSetFirstAddition();

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
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();

        PowerSetFirstAddition result = powerSet1.intersection(powerSet2);
        assertFalse(result.get("boil"));
        assertFalse(result.get("frozen"));
        assertEquals(0, result.size());
    }

    @Test
    public void testIntersectionWithFewElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetFirstAddition result = powerSet1.intersection(powerSet2);
        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testIntersectionOfSomeElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetFirstAddition result = powerSet1.intersection(powerSet2);
        assertTrue(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testUnionWithEmptySet() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();

        PowerSetFirstAddition result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testUnionOfSameElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetFirstAddition result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testUnionOfSomeElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetFirstAddition result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(4, result.size());
    }

    @Test
    public void testUnionOfDifferentElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSetFirstAddition result = powerSet1.union(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertTrue(result.get("heat"));
        assertTrue(result.get("hot"));
        assertEquals(4, result.size());
    }

    @Test
    public void testDifferenceWithEmptySet() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();

        PowerSetFirstAddition result = powerSet1.difference(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testDifferenceOfSameElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetFirstAddition result = powerSet1.difference(powerSet2);

        assertFalse(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertFalse(result.get("boil"));
        assertEquals(0, result.size());
    }

    @Test
    public void testDifferenceOfSomeElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSetFirstAddition result = powerSet1.difference(powerSet2);

        assertTrue(result.get("frozen"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertTrue(result.get("boil"));
        assertEquals(2, result.size());
    }

    @Test
    public void testDifferenceOfDifferentElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSetFirstAddition result = powerSet1.difference(powerSet2);

        assertTrue(result.get("frozen"));
        assertTrue(result.get("boil"));
        assertFalse(result.get("heat"));
        assertFalse(result.get("hot"));
        assertEquals(2, result.size());
    }

    @Test
    public void testIsSubsetWithEmptySet() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();

        assertTrue(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testIsSubsetOfSameElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        assertTrue(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testIsSubsetOfSomeElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("hot");
        powerSet2.put("heat");

        assertTrue(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testIsSubsetFalseCase() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");

        assertFalse(powerSet1.isSubset(powerSet2));
    }

    @Test
    public void testEqualsOfEmptySets() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();

        assertTrue(powerSet1.equals(powerSet2));
    }

    @Test
    public void testEqualsOfSameElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("heat");
        powerSet1.put("hot");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        assertTrue(powerSet1.equals(powerSet2));
    }

    @Test
    public void testEqualsWithDifferentSizes() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("frozen");

        assertFalse(powerSet1.equals(powerSet2));
    }

    @Test
    public void testEqualsWithDifferentElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("frozen");
        powerSet2.put("heat");

        assertFalse(powerSet1.equals(powerSet2));
    }

    @Test
    public void testCartesianProductWithEmptySet() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();

        PowerSetFirstAddition result = powerSet1.cartesianProduct(powerSet2);
        assertFalse(result.get("(frozen,frozen)"));
        assertFalse(result.get("(frozen,boil)"));
        assertFalse(result.get("(boil,frozen)"));
        assertFalse(result.get("(boil,boil)"));
        assertEquals(0, result.size());
    }

    @Test
    public void testCartesianProductWithFewElements() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");

        PowerSetFirstAddition result = powerSet1.cartesianProduct(powerSet2);
        assertTrue(result.get("(frozen,heat)"));
        assertTrue(result.get("(frozen,hot)"));
        assertTrue(result.get("(boil,heat)"));
        assertTrue(result.get("(boil,hot)"));
        assertEquals(4, result.size());
    }

    @Test
    public void testCartesianProductWithOneElement() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("heat");
        powerSet2.put("hot");
        powerSet2.put("boil");

        PowerSetFirstAddition result = powerSet1.cartesianProduct(powerSet2);
        assertTrue(result.get("(frozen,heat)"));
        assertTrue(result.get("(frozen,hot)"));
        assertTrue(result.get("(frozen,boil)"));
        assertEquals(3, result.size());
    }

    @Test
    public void testCartesianProductOrderMatters() {
        PowerSetFirstAddition powerSet1 = new PowerSetFirstAddition();
        powerSet1.put("frozen");
        powerSet1.put("boil");

        PowerSetFirstAddition powerSet2 = new PowerSetFirstAddition();
        powerSet2.put("heat");

        PowerSetFirstAddition result = powerSet1.cartesianProduct(powerSet2);
        assertTrue(result.get("(frozen,heat)"));
        assertTrue(result.get("(boil,heat)"));
        assertFalse(result.get("(heat,frozen)"));
        assertFalse(result.get("(heat,boil)"));
        assertEquals(2, result.size());
    }
}

