import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class QueueThirdAdditionTest {

    @Test
    public void testEnqueueOneItemInEmptyQueueThirdAddition() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();
        queue.enqueue(1);
        assertEquals(1, queue.dequeue());
    }

    @Test
    public void testEnqueueFewItemsInEmptyQueueThirdAddition() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }

    @Test
    public void testDequeueOneItemInEmptyQueueThirdAddition() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();

        assertNull(queue.dequeue());
    }

    @Test
    public void testDequeueFewItemsInEmptyQueueThirdAddition() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();

        assertNull(queue.dequeue());
        assertNull(queue.dequeue());
        assertNull(queue.dequeue());
    }

    @Test
    public void testDequeueOneItemInNonEmptyQueueThirdAddition() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        assertEquals(2, queue.dequeue());

    }

    @Test
    public void testDequeueFewItemsInNonEmptyQueueThirdAddition() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        queue.dequeue();
        assertNull(queue.dequeue());
    }

    @Test
    public void testSizeInEmptyQueueThirdAddition() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();

        assertEquals(0, queue.size());
    }

    @Test
    public void testSizeInNonEmptyQueueThirdAddition() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.size());
    }

    @Test
    public void testSizeWithComplexOperations() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();
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
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();

        assertEquals(0, queue.size());

        queue.enqueue(99);
        queue.enqueue(99);
        queue.enqueue(99);

        assertEquals(3, queue.size());
    }

    @Test
    public void testReverseWithEmptyStack() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();

        queue.reverse();
        assertEquals(0, queue.size());
        assertNull(queue.dequeue());
    }

    @Test
    public void testReverseWithNonEmptyStack() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        queue.reverse();

        assertEquals(3, queue.size());
        assertEquals(3, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(1, queue.dequeue());
        assertNull(queue.dequeue());
    }

    @Test
    public void testReverseDoubledWithNonEmptyStack() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        queue.reverse();
        queue.reverse();

        assertEquals(3, queue.size());
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
    }

    @Test
    public void testReverseWithNonEmptyStackInMiddleProcess() {
        QueueThirdAddition<Object> queue = new QueueThirdAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.dequeue();
        queue.enqueue(4);
        queue.enqueue(5);
        queue.reverse();

        assertEquals(4, queue.size());
        assertEquals(5, queue.dequeue());
        assertEquals(4, queue.dequeue());
        assertEquals(3, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertNull(queue.dequeue());
    }

}

