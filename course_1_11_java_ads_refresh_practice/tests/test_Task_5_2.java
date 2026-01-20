import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class QueueSecondAdditionTest {

    @Test
    public void testEnqueueOneItemInEmptyQueueSecondAddition() {
        QueueSecondAddition<Object> queue = new QueueSecondAddition<>();
        queue.enqueue(1);
        assertEquals(1, queue.dequeue());
    }

    @Test
    public void testEnqueueFewItemsInEmptyQueueSecondAddition() {
        QueueSecondAddition<Object> queue = new QueueSecondAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }

    @Test
    public void testDequeueOneItemInEmptyQueueSecondAddition() {
        QueueSecondAddition<Object> queue = new QueueSecondAddition<>();

        assertNull(queue.dequeue());
    }

    @Test
    public void testDequeueFewItemsInEmptyQueueSecondAddition() {
        QueueSecondAddition<Object> queue = new QueueSecondAddition<>();

        assertNull(queue.dequeue());
        assertNull(queue.dequeue());
        assertNull(queue.dequeue());
    }

    @Test
    public void testDequeueOneItemInNonEmptyQueueSecondAddition() {
        QueueSecondAddition<Object> queue = new QueueSecondAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        assertEquals(2, queue.dequeue());

    }

    @Test
    public void testDequeueFewItemsInNonEmptyQueueSecondAddition() {
        QueueSecondAddition<Object> queue = new QueueSecondAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        queue.dequeue();
        assertNull(queue.dequeue());
    }

    @Test
    public void testSizeInEmptyQueueSecondAddition() {
        QueueSecondAddition<Object> queue = new QueueSecondAddition<>();

        assertEquals(0, queue.size());
    }

    @Test
    public void testSizeInNonEmptyQueueSecondAddition() {
        QueueSecondAddition<Object> queue = new QueueSecondAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.size());
    }

    @Test
    public void testSizeWithComplexOperations() {
        QueueSecondAddition<Object> queue = new QueueSecondAddition<>();
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
        QueueSecondAddition<Object> queue = new QueueSecondAddition<>();
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

