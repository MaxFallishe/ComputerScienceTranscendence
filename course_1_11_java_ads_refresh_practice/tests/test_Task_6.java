import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

// Task number: 6.3
// Short description: Implement tests for deque class.
public class DequeTest {

    @Test
    public void testAddFrontWithEmptyDeque() {
        Deque<Object> deque = new Deque<>();
        deque.addFront(1);

        assertEquals(1, deque.size());
        assertEquals(1, deque.removeFront());
        assertNull(deque.removeFront());
        assertNull(deque.removeTail());
    }

    @Test
    public void testMultipleAddFront() {
        Deque<Object> deque = new Deque<>();
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
        Deque<Object> deque = new Deque<>();
        deque.addTail(1);

        assertEquals(1, deque.size());
        assertEquals(1, deque.removeTail());
        assertNull(deque.removeFront());
        assertNull(deque.removeTail());
    }

    @Test
    public void testMultipleAddTail() {
        Deque<Object> deque = new Deque<>();
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
        Deque<Object> deque = new Deque<>();
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
        Deque<Object> deque = new Deque<>();
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
        Deque<Object> deque = new Deque<>();
        Object result = deque.removeFront();

        assertNull(result);

        assertEquals(0, deque.size());
    }

    @Test
    public void testMultipleRemoveFront() {
        Deque<Object> deque = new Deque<>();

        assertNull(deque.removeFront());
        assertNull(deque.removeFront());
        assertNull(deque.removeFront());
        assertEquals(0, deque.size());
    }

    @Test
    public void testRemoveTailWithEmptyDeque() {
        Deque<Object> deque = new Deque<>();
        Object result = deque.removeTail();

        assertNull(result);

        assertEquals(0, deque.size());
    }

    @Test
    public void testMultipleRemoveTail() {
        Deque<Object> deque = new Deque<>();

        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertEquals(0, deque.size());
    }

    @Test
    public void testMixedMultipleRemoveTailAndRemoveFrontWithEmptyDeque() {
        Deque<Object> deque = new Deque<>();

        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertNull(deque.removeTail());
        assertEquals(0, deque.size());
    }

    @Test
    public void testMixedMultipleRemoveTailAndRemoveFrontWithNonEmptyDeque() {
        Deque<Object> deque = new Deque<>();

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
        Deque<Object> deque = new Deque<>();

        assertEquals(0, deque.size());
    }

    @Test
    public void testSizeWithExtraRemoving() {
        Deque<Object> deque = new Deque<>();

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

}

