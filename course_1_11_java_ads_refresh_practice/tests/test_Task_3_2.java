import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DynArraySecondAdditionTest {

    @Test
    public void test2DBasicSetGet() {
        DynArraySecondAddition<Integer> arr =
                new DynArraySecondAddition<>(Integer.class, 2, 3);

        arr.set(10, 0, 0);
        arr.set(20, 1, 2);

        assertEquals(Integer.valueOf(20), arr.get(1, 2));
        assertEquals(Integer.valueOf(10), arr.get(0, 0));

    }

    @Test
    public void test3DBasicSetGet() {
        DynArraySecondAddition<String> arr =
                new DynArraySecondAddition<>(String.class, 2, 2, 2);

        arr.set("hello", 1, 0, 1);

        assertEquals("hello", arr.get(1, 0, 1));
    }

    @Test
    public void testOverwriteValue() {
        DynArraySecondAddition<Integer> arr =
                new DynArraySecondAddition<>(Integer.class, 1, 1);

        arr.set(5, 0, 0);
        arr.set(7, 0, 0);

        assertEquals(Integer.valueOf(7), arr.get(0, 0));
    }

    @Test
    public void testExpandFirstDimension() {
        DynArraySecondAddition<Integer> arr =
                new DynArraySecondAddition<>(Integer.class, 1, 2);

        arr.set(99, 5, 1);

        assertEquals(Integer.valueOf(99), arr.get(5, 1));
    }

    @Test
    public void testExpandInnerDimension() {
        DynArraySecondAddition<Integer> arr =
                new DynArraySecondAddition<>(Integer.class, 2, 2);

        arr.set(44, 1, 5);

        assertEquals(Integer.valueOf(44), arr.get(1, 5));
    }

    @Test
    public void testIndependentDimensionExpansion() {
        DynArraySecondAddition<Integer> arr =
                new DynArraySecondAddition<>(Integer.class, 2, 2);

        arr.set(10, 0, 5);
        arr.set(20, 1, 1);

        assertEquals(Integer.valueOf(10), arr.get(0, 5));
        assertEquals(Integer.valueOf(20), arr.get(1, 1));
    }

    @Test
    public void testNullValueAllowed() {
        DynArraySecondAddition<Integer> arr =
                new DynArraySecondAddition<>(Integer.class, 2, 2);

        arr.set(null, 1, 1);

        assertNull(arr.get(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongNumberOfIndicesDuringGet() {
        DynArraySecondAddition<Integer> arr =
                new DynArraySecondAddition<>(Integer.class, 2, 2);

        arr.get(1);
        arr.set(10, 1, 1, 1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongNumberOfIndicesDuringSet() {
        DynArraySecondAddition<Integer> arr =
                new DynArraySecondAddition<>(Integer.class, 2, 2);

        arr.set(10, 1, 1, 1);

    }
}

