import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;


public class DequeThirdAdditionTest {

    @Test
    public void testAddFrontWithEmptyDeque() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();
        deque.addFront(1);

        assertEquals(1, deque.size());
        assertEquals(1, deque.removeFront());
        assertNull(deque.removeFront());
        assertNull(deque.removeTail());
    }

    @Test
    public void testMultipleAddFront() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();
        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);

        assertEquals(3, deque.size());
        assertEquals(3, deque.removeFront());
        assertEquals(2, deque.removeFront());
        assertEquals(1, deque.removeFront());
        assertNull(deque.removeTail());
    }

    @Test
    public void testAddTailWithEmptyDeque() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();
        deque.addTail(1);

        assertEquals(1, deque.size());
        assertEquals(1, deque.removeTail());
        assertNull(deque.removeFront());
        assertNull(deque.removeTail());
    }

    @Test
    public void testMultipleAddTail() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();
        deque.addTail(1);
        deque.addTail(2);
        deque.addTail(3);

        assertEquals(3, deque.size());
        assertEquals(3, deque.removeTail());
        assertEquals(2, deque.removeTail());
        assertEquals(1, deque.removeTail());
        assertNull(deque.removeTail());
    }

    @Test
    public void testMixedMultipleAddTailAndAddFrontDescCheck() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();
        deque.addTail(1);
        deque.addFront(-1);
        deque.addTail(2);
        deque.addFront(-2);
        deque.addTail(3);
        deque.addFront(-3);

        assertEquals(6, deque.size());
        assertEquals(3, deque.removeTail());
        assertEquals(2, deque.removeTail());
        assertEquals(1, deque.removeTail());
        assertEquals(-1, deque.removeTail());
        assertEquals(-2, deque.removeTail());
        assertEquals(-3, deque.removeTail());
        assertNull(deque.removeTail());
    }

    @Test
    public void testMixedMultipleAddTailAndAddFrontAscCheck() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();
        deque.addTail(1);
        deque.addFront(-1);
        deque.addTail(2);
        deque.addFront(-2);
        deque.addTail(3);
        deque.addFront(-3);

        assertEquals(6, deque.size());
        assertEquals(-3, deque.removeFront());
        assertEquals(-2, deque.removeFront());
        assertEquals(-1, deque.removeFront());
        assertEquals(1, deque.removeFront());
        assertEquals(2, deque.removeFront());
        assertEquals(3, deque.removeFront());
        assertNull(deque.removeFront());
    }

    @Test
    public void testRemoveFrontWithEmptyDeque() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();
        Object result = deque.removeFront();

        assertNull(result);

        assertEquals(0, deque.size());
    }

    @Test
    public void testMultipleRemoveFront() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        assertNull(deque.removeFront());
        assertNull(deque.removeFront());
        assertNull(deque.removeFront());
        assertEquals(0, deque.size());
    }

    @Test
    public void testRemoveTailWithEmptyDeque() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();
        Object result = deque.removeTail();

        assertNull(result);

        assertEquals(0, deque.size());
    }

    @Test
    public void testMultipleRemoveTail() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertEquals(0, deque.size());
    }

    @Test
    public void testMixedMultipleRemoveTailAndRemoveFrontWithEmptyDeque() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertEquals(0, deque.size());
    }

    @Test
    public void testMixedMultipleRemoveTailAndRemoveFrontWithNonEmptyDeque() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        deque.addTail(1);
        deque.addFront(-1);
        deque.addTail(2);
        deque.addFront(-2);
        deque.addTail(3);
        deque.addFront(-3);

        assertEquals(6, deque.size());
        assertEquals(-3, deque.removeFront());
        assertEquals(3, deque.removeTail());
        assertEquals(-2, deque.removeFront());
        assertEquals(2, deque.removeTail());
        assertEquals(-1, deque.removeFront());
        assertEquals(1, deque.removeTail());

        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertEquals(0, deque.size());
    }


    @Test
    public void testSizeWithEmptyDeque() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        assertEquals(0, deque.size());
    }

    @Test
    public void testSizeWithExtraRemoving() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        deque.addTail(1);
        deque.addFront(-1);
        assertEquals(2, deque.size());

        deque.removeTail();
        deque.removeFront();
        assertEquals(0, deque.size());

        deque.removeTail();
        deque.removeFront();
        assertEquals(0, deque.size());
    }

    @Test
    public void testAdjustIndexWithNormalIndex() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();
        deque.capacity = 10; // init capacity and min capacity

        assertEquals(5, deque.adjustIndex(5));

        assertEquals(0, deque.adjustIndex(0));

        assertEquals(1, deque.adjustIndex(1));

        assertEquals(9, deque.adjustIndex(9));
    }

    @Test
    public void testAdjustIndexWithExtraIndex() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();
        deque.capacity = 10; // init capacity and min capacity

        assertEquals(0, deque.adjustIndex(10));

        assertEquals(1, deque.adjustIndex(11));

        assertEquals(9, deque.adjustIndex(19));

        assertEquals(8, deque.adjustIndex(48));
    }

    @Test
    public void testAdjustIndexWithNegativeIndex() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();
        deque.capacity = 10; // init capacity and min capacity

        assertEquals(9, deque.adjustIndex(-1));

        assertEquals(9, deque.adjustIndex(-11));

        assertEquals(8, deque.adjustIndex(-22));

        assertEquals(2, deque.adjustIndex(-28));
    }

    @Test
    public void testFillDequeWithOneOffInitialCapacityChangeWithOnlyAddTail() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        deque.addTail(1);
        deque.addTail(2);
        deque.addTail(3);
        deque.addTail(4);
        deque.addTail(5);
        deque.addTail(6);
        deque.addTail(7);
        deque.addTail(8);
        deque.addTail(9);

        assertArrayEquals(new Object[]{null, 1, 2, 3, 4, 5, 6, 7, 8, 9}, deque.getStorage());
    }

    @Test
    public void testFillDequeWithOneOffInitialCapacityChangeWithOnlyAddFront() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);
        deque.addFront(4);
        deque.addFront(5);
        deque.addFront(6);
        deque.addFront(7);
        deque.addFront(8);
        deque.addFront(9);

        assertArrayEquals(new Object[]{1, null, 9, 8, 7, 6, 5, 4, 3, 2}, deque.getStorage());
    }

    @Test
    public void testFillDequeWithOneOffInitialCapacityChangeWithAddFrontAndAddTail() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        deque.addFront(1);
        deque.addTail(2);
        deque.addFront(3);
        deque.addTail(4);
        deque.addFront(5);
        deque.addTail(6);
        deque.addFront(7);
        deque.addTail(8);
        deque.addFront(9);

        assertArrayEquals(new Object[]{1, 2, 4, 6, 8, null, 9, 7, 5, 3}, deque.getStorage());
    }

    @Test
    public void testFillDequeWithInitailCapacityValueWithOnlyAddTail() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        deque.addTail(1);
        deque.addTail(2);
        deque.addTail(3);
        deque.addTail(4);
        deque.addTail(5);
        deque.addTail(6);
        deque.addTail(7);
        deque.addTail(8);
        deque.addTail(9);
        deque.addTail(10);


        assertEquals(20, deque.capacity);
        assertEquals(10, deque.getTailIndx());
        assertEquals(19, deque.getFrontIndx());
        assertArrayEquals(
                new Object[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, null, null, null, null, null, null, null, null, null, null},
                deque.getStorage()
        );
    }

    @Test
    public void testFillDequeWithInitailCapacityValueWithOnlyAddFront() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);
        deque.addFront(4);
        deque.addFront(5);
        deque.addFront(6);
        deque.addFront(7);
        deque.addFront(8);
        deque.addFront(9);
        deque.addFront(10);


        assertEquals(20, deque.capacity);
        assertEquals(10, deque.getTailIndx());
        assertEquals(19, deque.getFrontIndx());
        assertArrayEquals(
                new Object[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1, null, null, null, null, null, null, null, null, null, null},
                deque.getStorage()
        );
    }

    @Test
    public void testFillDequeWithInitailCapacityValueWithAddFrontAndAddTail() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        deque.addFront(1);
        deque.addTail(2);
        deque.addFront(3);
        deque.addTail(4);
        deque.addFront(5);
        deque.addTail(6);
        deque.addFront(7);
        deque.addTail(8);
        deque.addFront(9);
        deque.addTail(10);


        assertEquals(20, deque.capacity);
        assertEquals(10, deque.getTailIndx());
        assertEquals(19, deque.getFrontIndx());
        assertArrayEquals(
                new Object[]{9, 7, 5, 3, 1, 2, 4, 6, 8, 10, null, null, null, null, null, null, null, null, null, null},
                deque.getStorage()
        );
    }

    @Test
    public void testFillingDequeAfterRemovingFromOneSideWithAddFrontAndAddTailAndRemoveFront() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        deque.addFront(1);
        deque.addTail(2);
        deque.addFront(3);
        deque.addTail(4);
        deque.addFront(5);
        deque.addTail(6);

        assertArrayEquals(
                new Object[]{1, 2, 4, 6, null, null, null, null, 5, 3},
                deque.getStorage()
        );

        assertEquals(5, deque.removeFront());
        assertEquals(3, deque.removeFront());
        assertEquals(1, deque.removeFront());
        assertEquals(2, deque.removeFront());
        assertEquals(4, deque.removeFront());
        assertEquals(1, deque.size());

        assertArrayEquals(
                new Object[]{null, null, null, 6, null, null, null, null, null, null},
                deque.getStorage()
        );
        assertEquals(2, deque.getFrontIndx());
        assertEquals(4, deque.getTailIndx());

        deque.addFront(11);
        deque.addTail(22);
        deque.addFront(33);
        deque.addTail(44);
        deque.addFront(55);
        deque.addTail(66);
        deque.addFront(77);
        deque.addTail(88);
        deque.addFront(99);
        deque.addTail(1000);
        deque.addFront(1010);
        assertEquals(18, deque.getFrontIndx());
        assertEquals(11, deque.getTailIndx());

        assertArrayEquals(
                new Object[]{99, 77, 55, 33, 11, 6, 22, 44, 66, 88, 1000, null, null, null, null, null, null, null, null, 1010},
                deque.getStorage()
        );
    }

    @Test
    public void testFillingDequeAfterRemovingFromOneSideWithAddFrontAndAddTailAndRemoveTail() {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        deque.addFront(1);
        deque.addTail(2);
        deque.addFront(3);
        deque.addTail(4);
        deque.addFront(5);
        deque.addTail(6);

        assertArrayEquals(
                new Object[]{1, 2, 4, 6, null, null, null, null, 5, 3},
                deque.getStorage()
        );

        assertEquals(6, deque.removeTail());
        assertEquals(4, deque.removeTail());
        assertEquals(2, deque.removeTail());
        assertEquals(1, deque.removeTail());
        assertEquals(3, deque.removeTail());
        assertEquals(1, deque.size());

        assertArrayEquals(
                new Object[]{null, null, null, null, null, null, null, null, 5, null},
                deque.getStorage()
        );
        assertEquals(7, deque.getFrontIndx());
        assertEquals(9, deque.getTailIndx());

        deque.addFront(11);
        deque.addTail(22);
        deque.addFront(33);
        deque.addTail(44);
        deque.addFront(55);
        deque.addTail(66);
        deque.addFront(77);
        deque.addTail(88);
        deque.addFront(99);
        deque.addTail(1000);
        deque.addFront(1010);

        assertEquals(18, deque.getFrontIndx());
        assertEquals(11, deque.getTailIndx());
        assertArrayEquals(
                new Object[]{99, 77, 55, 33, 11, 5, 22, 44, 66, 88, 1000, null, null, null, null, null, null, null, null, 1010},
                deque.getStorage()
        );
    }

    @Test
    public void testDequeCapacityDecreasingWithRemoveTail () {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);
        deque.addFront(4);
        deque.addFront(5);
        deque.addFront(6);
        deque.addFront(7);
        deque.addFront(8);
        deque.addFront(9);
        deque.addFront(10);


        assertEquals(20, deque.capacity);
        assertEquals(10, deque.getTailIndx());
        assertEquals(19, deque.getFrontIndx());
        assertArrayEquals(
                new Object[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1, null, null, null, null, null, null, null, null, null, null},
                deque.getStorage()
        );

        assertEquals(1, deque.removeTail());
        assertEquals(2, deque.removeTail());
        assertEquals(3, deque.removeTail());
        assertEquals(4, deque.removeTail());
        assertEquals(5, deque.removeTail());
        assertEquals(13, deque.capacity);
        assertArrayEquals(
                new Object[]{10, 9, 8, 7, 6, null, null, null, null, null, null, null, null},
                deque.getStorage()
        );

        assertEquals(6, deque.removeTail());
        assertEquals(10, deque.removeFront());
        assertEquals(9, deque.removeFront());
        assertEquals(8, deque.removeFront());
        assertEquals(7, deque.removeFront());

        assertNull(deque.removeFront());
        assertNull(deque.removeFront());
    }

    @Test
    public void testDequeCapacityDecreasingWithRemoveFront () {
        DequeThirdAddition<Object> deque = new DequeThirdAddition<>();

        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);
        deque.addFront(4);
        deque.addFront(5);
        deque.addFront(6);
        deque.addFront(7);
        deque.addFront(8);
        deque.addFront(9);
        deque.addFront(10);


        assertEquals(20, deque.capacity);
        assertEquals(10, deque.getTailIndx());
        assertEquals(19, deque.getFrontIndx());
        assertArrayEquals(
                new Object[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1, null, null, null, null, null, null, null, null, null, null},
                deque.getStorage()
        );

        assertEquals(10, deque.removeFront());
        assertEquals(9, deque.removeFront());
        assertEquals(8, deque.removeFront());
        assertEquals(7, deque.removeFront());
        assertEquals(6, deque.removeFront());
        assertEquals(13, deque.capacity);

        assertArrayEquals(
                new Object[]{5, 4, 3, 2, 1, null, null, null, null, null, null, null, null},
                deque.getStorage()
        );

        assertEquals(5, deque.removeFront());

        assertEquals(1, deque.removeTail());
        assertEquals(2, deque.removeTail());
        assertEquals(3, deque.removeTail());
        assertEquals(4, deque.removeTail());

        assertNull(deque.removeFront());
        assertNull(deque.removeFront());
    }

}

