import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class DequeSecondAdditionTest {

    @Test
    public void testAddFrontWithEmptyDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();
        deque.addFront(1);

        assertEquals((Integer) 1, deque.size());
        assertEquals((Integer) 1, deque.removeFront());
        assertNull(deque.removeFront());
        assertNull(deque.removeTail());
    }

    @Test
    public void testMultipleAddFront() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();
        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);

        assertEquals((Integer) 3, deque.size());
        assertEquals((Integer) 3, deque.removeFront());
        assertEquals((Integer) 2, deque.removeFront());
        assertEquals((Integer) 1, deque.removeFront());
        assertNull(deque.removeTail());
    }

    @Test
    public void testAddTailWithEmptyDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();
        deque.addTail(1);

        assertEquals((Integer) 1, deque.size());
        assertEquals((Integer) 1, deque.removeTail());
        assertNull(deque.removeFront());
        assertNull(deque.removeTail());
    }

    @Test
    public void testMultipleAddTail() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();
        deque.addTail(1);
        deque.addTail(2);
        deque.addTail(3);

        assertEquals((Integer) 3, deque.size());
        assertEquals((Integer) 3, deque.removeTail());
        assertEquals((Integer) 2, deque.removeTail());
        assertEquals((Integer) 1, deque.removeTail());
        assertNull(deque.removeTail());
    }

    @Test
    public void testMixedMultipleAddTailAndAddFrontDescCheck() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();
        deque.addTail(1);
        deque.addFront(-1);
        deque.addTail(2);
        deque.addFront(-2);
        deque.addTail(3);
        deque.addFront(-3);

        assertEquals((Integer) 6, deque.size());
        assertEquals((Integer) 3, deque.removeTail());
        assertEquals((Integer) 2, deque.removeTail());
        assertEquals((Integer) 1, deque.removeTail());
        assertEquals(Integer.valueOf(-1), deque.removeTail());
        assertEquals(Integer.valueOf(-2), deque.removeTail());
        assertEquals(Integer.valueOf(-3), deque.removeTail());
        assertNull(deque.removeTail());
    }

    @Test
    public void testMixedMultipleAddTailAndAddFrontAscCheck() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();
        deque.addTail(1);
        deque.addFront(-1);
        deque.addTail(2);
        deque.addFront(-2);
        deque.addTail(3);
        deque.addFront(-3);

        assertEquals((Integer) 6, deque.size());
        assertEquals(Integer.valueOf(-3), deque.removeFront());
        assertEquals(Integer.valueOf(-2), deque.removeFront());
        assertEquals(Integer.valueOf(-1), deque.removeFront());
        assertEquals((Integer) 1, deque.removeFront());
        assertEquals((Integer) 2, deque.removeFront());
        assertEquals((Integer) 3, deque.removeFront());
        assertNull(deque.removeFront());
    }


    @Test
    public void testRemoveFrontWithEmptyDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();
        Object result = deque.removeFront();

        assertNull(result);

        assertEquals((Integer) 0, deque.size());
    }

    @Test
    public void testMultipleRemoveFront() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        assertNull(deque.removeFront());
        assertNull(deque.removeFront());
        assertNull(deque.removeFront());
        assertEquals((Integer) 0, deque.size());
    }

    @Test
    public void testRemoveTailWithEmptyDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();
        Object result = deque.removeTail();

        assertNull(result);

        assertEquals((Integer) 0, deque.size());
    }

    @Test
    public void testMultipleRemoveTail() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertEquals((Integer) 0, deque.size());
    }

    @Test
    public void testMixedMultipleRemoveTailAndRemoveFrontWithEmptyDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertEquals((Integer) 0, deque.size());
    }

    @Test
    public void testMixedMultipleRemoveTailAndRemoveFrontWithNonEmptyDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        deque.addTail(1);
        deque.addFront(-1);
        deque.addTail(2);
        deque.addFront(-2);
        deque.addTail(3);
        deque.addFront(-3);

        assertEquals((Integer) 6, deque.size());
        assertEquals(Integer.valueOf(-3), deque.removeFront());
        assertEquals((Integer) 3, deque.removeTail());
        assertEquals(Integer.valueOf(-2), deque.removeFront());
        assertEquals((Integer) 2, deque.removeTail());
        assertEquals(Integer.valueOf(-1), deque.removeFront());
        assertEquals((Integer) 1, deque.removeTail());

        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertEquals((Integer) 0, deque.size());
    }


    @Test
    public void testSizeWithEmptyDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        assertEquals((Integer) 0, deque.size());
    }

    @Test
    public void testSizeWithExtraRemoving() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        deque.addTail(1);
        deque.addFront(-1);
        assertEquals((Integer) 2, deque.size());

        deque.removeTail();
        deque.removeFront();
        assertEquals((Integer) 0, deque.size());

        deque.removeTail();
        deque.removeFront();
        assertEquals((Integer) 0, deque.size());
    }


    @Test
    public void testGetMinElementWithEmptyDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        assertNull(deque.getMinElement());
    }

    @Test
    public void testGetMinElementAfterOnlyAscAddTail() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        deque.addTail(1);
        deque.addTail(2);
        deque.addTail(3);
        assertEquals((Integer) 1, deque.getMinElement());

        deque.removeTail();
        assertEquals((Integer) 1, deque.getMinElement());

        deque.removeTail();
        assertEquals((Integer) 1, deque.getMinElement());

        deque.removeTail();
        assertNull(deque.getMinElement());

    }

    @Test
    public void testGetMinElementAfterOnlyDescAddTail() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        deque.addFront(3);
        deque.addFront(2);
        deque.addFront(2);
        deque.addFront(1);

        assertEquals((Integer) 1, deque.getMinElement());

        deque.removeFront();
        assertEquals((Integer) 2, deque.getMinElement());

        deque.removeFront();
        assertEquals((Integer) 2, deque.getMinElement());

        deque.removeFront();
        assertEquals((Integer) 3, deque.getMinElement());

        deque.removeFront();
        assertNull(deque.getMinElement());
    }

    @Test
    public void testReassembleFrontStorageAndTailStorageWithZeroElementsDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        assertEquals(List.of(), deque.storage);

        assertEquals(List.of(), deque.frontStorage);
        assertEquals(List.of(), deque.tailStorage);

        deque.reassembleFrontStorageAndTailStorage();

        assertEquals(List.of(), deque.frontStorage);
        assertEquals(List.of(), deque.tailStorage);
    }

    @Test
    public void testReassembleFrontStorageAndTailStorageWithSingleElementDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        deque.addFront(1);

        assertEquals(List.of(1), deque.storage);

        assertEquals(List.of(1), deque.frontStorage);
        assertEquals(List.of(), deque.tailStorage);

        deque.reassembleFrontStorageAndTailStorage();

        assertEquals(List.of(), deque.frontStorage);
        assertEquals(List.of(1), deque.tailStorage);
    }

    @Test
    public void testReassembleFrontStorageAndTailStorageWithTwoElementsDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        deque.addFront(1);
        deque.addFront(2);

        assertEquals(List.of(2, 1), deque.storage);

        assertEquals(List.of(1), deque.frontStorage);
        assertEquals(List.of(), deque.tailStorage);

        deque.reassembleFrontStorageAndTailStorage();

        assertEquals(List.of(2), deque.frontStorage);
        assertEquals(List.of(1), deque.tailStorage);

    }

    @Test
    public void testReassembleFrontStorageAndTailStorageWithThreeElementsDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);

        assertEquals(List.of(3, 2, 1), deque.storage);

        assertEquals(List.of(1), deque.frontStorage);
        assertEquals(List.of(), deque.tailStorage);

        deque.reassembleFrontStorageAndTailStorage();

        assertEquals(List.of(3), deque.frontStorage);
        assertEquals(List.of(2, 1), deque.tailStorage);

    }

    @Test
    public void testGetMinElementAfterExtraFrontRemoves() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);
        deque.addTail(4);
        deque.addTail(5);
        deque.addTail(6);

        assertEquals(List.of(3, 2, 1, 4, 5, 6), deque.storage);

        assertEquals(List.of(1), deque.frontStorage);
        assertEquals(List.of(4), deque.tailStorage);
        assertEquals((Integer) 1, deque.getMinElement());

        deque.removeFront();
        deque.removeFront();
        deque.removeFront();
        deque.removeFront();

        assertEquals((Integer) 5, deque.getMinElement());

        deque.removeFront();
        assertEquals((Integer) 6, deque.getMinElement());

        deque.removeFront();
        assertNull(deque.getMinElement());
    }

    @Test
    public void testGetMinElementAfterExtraTailRemoves() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        deque.addFront(1);
        deque.addFront(2);
        deque.addFront(3);
        deque.addTail(4);
        deque.addTail(5);
        deque.addTail(6);

        assertEquals(List.of(3, 2, 1, 4, 5, 6), deque.storage);

        assertEquals(List.of(1), deque.frontStorage);
        assertEquals(List.of(4), deque.tailStorage);
        assertEquals((Integer) 1, deque.getMinElement());

        deque.removeTail();
        deque.removeTail();
        deque.removeTail();
        deque.removeTail();

        assertEquals((Integer) 2, deque.getMinElement());

        deque.removeTail();
        assertEquals((Integer) 3, deque.getMinElement());

        deque.removeTail();
        assertNull(deque.getMinElement());
    }

    @Test
    public void testGetMinElementAfterIteratingDequeClearing() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        deque.addFront(11);
        deque.addFront(2);
        deque.addFront(9);
        deque.addTail(4);
        deque.addTail(4);
        deque.addTail(0);
        deque.addTail(1);

        assertEquals(List.of(9, 2, 11, 4, 4, 0, 1), deque.storage);
        assertEquals((Integer) 0, deque.getMinElement());

        deque.removeTail();   // 9, 2, 11, 4, 4, 0
        assertEquals((Integer) 0, deque.getMinElement());

        deque.removeTail();   // 9, 2, 11, 4, 4
        assertEquals((Integer) 2, deque.getMinElement());

        deque.removeFront();  // 2, 11, 4, 4
        assertEquals((Integer) 2, deque.getMinElement());

        deque.removeFront();  // 11, 4, 4
        assertEquals((Integer) 4, deque.getMinElement());

        deque.removeFront();  // 4, 4
        assertEquals((Integer) 4, deque.getMinElement());

        deque.removeFront();  // 4
        assertEquals((Integer) 4, deque.getMinElement());

        deque.removeTail();  // deque is empty
        assertNull(deque.getMinElement());
    }


    @Test
    public void testGetMinElementWithDequeOfSameValues() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        deque.addFront(42);
        deque.addFront(42);
        deque.addFront(42);
        deque.addTail(42);
        deque.addTail(42);
        deque.addTail(42);
        deque.addTail(42);

        assertEquals(List.of(42, 42, 42, 42, 42, 42, 42), deque.storage);
        assertEquals((Integer) 42, deque.getMinElement());

        deque.removeTail();
        assertEquals((Integer) 42, deque.getMinElement());

        deque.removeTail();
        deque.removeTail();
        deque.removeTail();
        deque.removeTail();
        assertEquals((Integer) 42, deque.getMinElement());

        deque.removeFront();
        assertEquals((Integer) 1, deque.size());
        assertEquals((Integer) 42, deque.getMinElement());

        deque.removeFront();
        assertNull(deque.getMinElement());
    }

    @Test
    public void testGetMinElementInComplexModifiedDeque() {
        DequeSecondAddition<Integer> deque = new DequeSecondAddition<>();

        deque.addTail(1);
        deque.addFront(2);
        deque.addTail(3);
        deque.addFront(4);
        deque.addTail(5);
        deque.addFront(6);
        deque.addTail(7);
        deque.addFront(8);
        deque.addFront(8);
        deque.addFront(8);
        deque.addTail(8);

        assertEquals(List.of(8, 8, 8, 6, 4, 2, 1, 3, 5, 7, 8), deque.storage);

        deque.removeTail();
        deque.removeTail();
        deque.removeTail();
        deque.removeTail();
        deque.removeTail();
        deque.removeTail();
        assertEquals((Integer) 4, deque.getMinElement());

        deque.removeFront();
        deque.removeFront();
        deque.removeFront();
        deque.removeFront();
        assertEquals((Integer) 4, deque.getMinElement());

        deque.removeFront();
        assertNull(deque.getMinElement());
    }

}

