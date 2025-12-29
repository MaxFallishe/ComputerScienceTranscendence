import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

public class DynArrayFirstAdditionTest {

    @Test
    public void testDynArrayConstructor() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);

        assertEquals(16, arr.array.length);
        assertEquals(0, arr.count);
        assertEquals(16, arr.capacity);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testMakeArrayWithSameCapacityAndEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        arr.makeArray(16);

        assertEquals(16, arr.array.length);
        assertEquals(0, arr.count);
        assertEquals(16, arr.capacity);
    }


    @Test
    public void testMakeArrayWithSameCapacityAndNonEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);

        arr.append(0);
        arr.append(1);
        arr.append(2);
        arr.append(3);
        arr.append(4);
        arr.append(5);

        arr.makeArray(16);
        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(6, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(6, notNullsCount);
        assertEquals(10, nullsCount);

    }


    @Test
    public void testMakeArrayWithBiggerCapacityAndNonEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);

        for (int i = 0; i <= 15; i++) {
            arr.append(i);
        }

        assertEquals(16, arr.array.length);
        assertEquals(16, arr.count);
        assertEquals(16, arr.capacity);

        arr.makeArray(20);
        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(20, arr.array.length);
        assertEquals(16, arr.count);
        assertEquals(20, arr.capacity);
        assertEquals(16, notNullsCount);
        assertEquals(4, nullsCount);
    }

    @Test
    public void testMakeArrayWithSmallerCapacityAndEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        arr.makeArray(5);
        assertEquals(5, arr.array.length);
        assertEquals(0, arr.count);
        assertEquals(5, arr.capacity);
    }

    @Test
    public void testMakeArrayWithSmallerCapacityAndNonEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);

        for (int i = 0; i <= 2; i++) {
            arr.append(i);
        }

        arr.makeArray(5);
        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(5, arr.array.length);
        assertEquals(3, arr.count);
        assertEquals(5, arr.capacity);
        assertEquals(3, notNullsCount);
        assertEquals(2, nullsCount);
    }

    @Test
    public void testMakeArrayWithSmallerCapacityAndFullArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);

        for (int i = 0; i <= 15; i++) {
            arr.append(i);
        }

        arr.makeArray(5);
        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(5, arr.array.length);
        assertEquals(5, arr.count);
        assertEquals(5, arr.capacity);
        assertEquals(5, notNullsCount);
        assertEquals(0, nullsCount);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetItemInCapacityRangeWithEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);

        arr.getItem(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetItemByNegativeIndexWithEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);

        arr.getItem(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetItemOutOfCapacityWithEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);

        arr.getItem(17);
    }

    @Test
    public void testAppendWithEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        arr.append(99);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(1, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(99, (int) arr.getItem(0));
        assertEquals(1, notNullsCount);
        assertEquals(15, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }


    @Test
    public void testAppendWithNonEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 9; i++) {
            arr.append(i);
        }

        arr.append(99);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(11, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(99, (int) arr.getItem(10));
        assertEquals(11, notNullsCount);
        assertEquals(5, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testAppendWithFullArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 15; i++) {
            arr.append(i);
        }

        arr.append(99);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(32, arr.array.length);
        assertEquals(17, arr.count);
        assertEquals(32, arr.capacity);
        assertEquals(99, (int) arr.getItem(16));
        assertEquals(17, notNullsCount);
        assertEquals(15, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testAppendWithFullArrayFewIterations() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 15; i++) {
            arr.append(i);
        }
        arr.append(99);

        for (int i = 0; i <= 14; i++) {
            arr.append(i);
        }
        arr.append(999);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(64, arr.array.length);
        assertEquals(33, arr.count);
        assertEquals(64, arr.capacity);
        assertEquals(99, (int) arr.getItem(16));
        assertEquals(999, (int) arr.getItem(32));
        assertEquals(33, notNullsCount);
        assertEquals(31, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }


    @Test
    public void testInsertWithEmptyArrayAndCorrectIndex() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        arr.insert(99, 0);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(1, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(99, (int) arr.getItem(0));
        assertEquals(1, notNullsCount);
        assertEquals(15, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testInsertWithEmptyArrayAndIncorrectIndexInCapacityRange() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        arr.insert(99, 1);
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testInsertWithEmptyArrayAndIncorrectIndexOutOfCapacityRange() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        arr.insert(99, 18);
    }


    @Test
    public void testInsertWithEmptyArrayFewIterations() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        arr.insert(99, 0);
        arr.insert(999, 1);
        arr.insert(9999, 2);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(3, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(99, (int) arr.getItem(0));
        assertEquals(999, (int) arr.getItem(1));
        assertEquals(9999, (int) arr.getItem(2));
        assertEquals(3, notNullsCount);
        assertEquals(13, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }


    @Test
    public void testInsertWithNonEmptyArrayAndTailIndex() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 4; i++) {
            arr.append(i);
        }

        arr.insert(99, 5);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(6, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(99, (int) arr.getItem(5));
        assertEquals(3, (int) arr.getItem(3));
        assertEquals(6, notNullsCount);
        assertEquals(10, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testInsertWithNonEmptyArrayAndHeadIndex() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 4; i++) {
            arr.append(i);
        }

        arr.insert(99, 0);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(6, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(99, (int) arr.getItem(0));
        assertEquals(4, (int) arr.getItem(5));
        assertEquals(6, notNullsCount);
        assertEquals(10, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testInsertWithNonEmptyArrayAndMiddleIndex() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 4; i++) {
            arr.append(i);
        }

        arr.insert(99, 2);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(6, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(99, (int) arr.getItem(2));
        assertEquals(4, (int) arr.getItem(5));
        assertEquals(1, (int) arr.getItem(1));
        assertEquals(6, notNullsCount);
        assertEquals(10, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testInsertWithFullArrayAndTailIndex() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 15; i++) {
            arr.append(i);
        }

        arr.insert(99, 16);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(32, arr.array.length);
        assertEquals(17, arr.count);
        assertEquals(32, arr.capacity);
        assertEquals(99, (int) arr.getItem(16));
        assertEquals(5, (int) arr.getItem(5));
        assertEquals(17, notNullsCount);
        assertEquals(15, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testInsertWithFullArrayAndMiddleIndex() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 15; i++) {
            arr.append(i);
        }

        arr.insert(99, 10);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(32, arr.array.length);
        assertEquals(17, arr.count);
        assertEquals(32, arr.capacity);
        assertEquals(99, (int) arr.getItem(10));
        assertEquals(5, (int) arr.getItem(5));
        assertEquals(10, (int) arr.getItem(11));
        assertEquals(17, notNullsCount);
        assertEquals(15, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testInsertWithFullArrayAndHeadIndex() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 15; i++) {
            arr.append(i);
        }

        arr.insert(99, 0);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(32, arr.array.length);
        assertEquals(17, arr.count);
        assertEquals(32, arr.capacity);
        assertEquals(99, (int) arr.getItem(0));
        assertEquals(4, (int) arr.getItem(5));
        assertEquals(17, notNullsCount);
        assertEquals(15, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveWithEmptyArrayInCapacityRange() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);

        arr.remove(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveWithEmptyArrayOutOfCapacityRange() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);

        arr.remove(18);
    }


    @Test
    public void testRemoveHeadElementWithNonEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 15; i++) {
            arr.append(i);
        }

        arr.remove(0);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(15, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(1, (int) arr.getItem(0));
        assertEquals(15, (int) arr.getItem(14));
        assertEquals(15, notNullsCount);
        assertEquals(1, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testRemoveTailElementWithNonEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 15; i++) {
            arr.append(i);
        }

        arr.remove(15);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(15, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(0, (int) arr.getItem(0));
        assertEquals(14, (int) arr.getItem(14));
        assertEquals(15, notNullsCount);
        assertEquals(1, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testRemoveMiddleElementWithNonEmptyArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 15; i++) {
            arr.append(i);
        }

        arr.remove(10);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(15, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(0, (int) arr.getItem(0));
        assertEquals(11, (int) arr.getItem(10));
        assertEquals(15, (int) arr.getItem(14));
        assertEquals(15, notNullsCount);
        assertEquals(1, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testRemoveWithNonEmptyArrayFewIterations() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 15; i++) {
            arr.append(i);
        }

        arr.remove(15);
        arr.remove(0);
        arr.remove(10);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(13, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(1, (int) arr.getItem(0));
        assertEquals(12, (int) arr.getItem(10));
        assertEquals(14, (int) arr.getItem(12));
        assertEquals(13, notNullsCount);
        assertEquals(3, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testRemoveWithAlmostEmptyArrayFewIterations() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 2; i++) {
            arr.append(i);
        }

        arr.remove(0);
        arr.remove(0);
        arr.remove(0);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(0, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(0, notNullsCount);
        assertEquals(16, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

    @Test
    public void testRemoveWithIncreasedArray() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 16; i++) {
            arr.append(i);
        }
        assertEquals(32, arr.capacity);

        arr.remove(0);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(21, arr.array.length);
        assertEquals(16, arr.count);
        assertEquals(21, arr.capacity);
        assertEquals(16, notNullsCount);
        assertEquals(5, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }


    @Test
    public void testRemoveWithIncreasedArrayFewIterations() {
        DynArrayFirstAddition<Integer> arr = new DynArrayFirstAddition<>(Integer.class);
        for (int i = 0; i <= 16; i++) {
            arr.append(i);
        }
        assertEquals(32, arr.capacity);

        arr.remove(0);
        arr.remove(0);
        arr.remove(0);
        arr.remove(0);
        arr.remove(0);
        arr.remove(0);
        arr.remove(0);

        int nullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::isNull)
                .count();
        int notNullsCount = (int) Arrays.stream(arr.array)
                .filter(Objects::nonNull)
                .count();

        assertEquals(16, arr.array.length);
        assertEquals(10, arr.count);
        assertEquals(16, arr.capacity);
        assertEquals(10, notNullsCount);
        assertEquals(6, nullsCount);
        assertTrue(arr.bankAccountPoints >= 0);
    }

}

