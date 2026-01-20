import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;


public class QueueTest {

    @Test
    public void testEnqueueOneItemInEmptyQueue() {
        Queue<Object> queue = new Queue<>();
        queue.enqueue(1);
        assertEquals(1, queue.dequeue());
    }

    @Test
    public void testEnqueueFewItemsInEmptyQueue() {
        Queue<Object> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }

    @Test
    public void testDequeueOneItemInEmptyQueue() {
        Queue<Object> queue = new Queue<>();

        assertNull(queue.dequeue());
    }

    @Test
    public void testDequeueFewItemsInEmptyQueue() {
        Queue<Object> queue = new Queue<>();

        assertNull(queue.dequeue());
        assertNull(queue.dequeue());
        assertNull(queue.dequeue());
    }

    @Test
    public void testDequeueOneItemInNonEmptyQueue() {
        Queue<Object> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        assertEquals(2, queue.dequeue());

    }

    @Test
    public void testDequeueFewItemsInNonEmptyQueue() {
        Queue<Object> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        queue.dequeue();
        assertNull(queue.dequeue());
    }

    @Test
    public void testSizeInEmptyQueue() {
        Queue<Object> queue = new Queue<>();

        assertEquals(0, queue.size());
    }

    @Test
    public void testSizeInNonEmptyQueue() {
        Queue<Object> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.size());
    }

    @Test
    public void testSizeWithComplexOperations() {
        Queue<Object> queue = new Queue<>();
        assertEquals(0, queue.size());
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.size());
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        assertEquals(0, queue.size());
    }

    @Test
    public void testSizeWithComplexDequeueOperations() {
        Queue<Object> queue = new Queue<>();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();

        assertEquals(0, queue.size());

        queue.enqueue(99);
        queue.enqueue(99);
        queue.enqueue(99);

        assertEquals(3, queue.size());
    }
}

