import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class QueueFourthAdditionTest {

    @Test
    public void testEnqueueOneItemInEmptyQueueFourthAddition() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
        queue.enqueue(1);
        assertEquals(1, queue.dequeue());
    }

    @Test
    public void testEnqueueFewItemsInEmptyQueueFourthAddition() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }

    @Test
    public void testDequeueOneItemInEmptyQueueFourthAddition() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);

        assertNull(queue.dequeue());
    }

    @Test
    public void testDequeueFewItemsInEmptyQueueFourthAddition() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);

        assertNull(queue.dequeue());
        assertNull(queue.dequeue());
        assertNull(queue.dequeue());
    }

    @Test
    public void testDequeueOneItemInNonEmptyQueueFourthAddition() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        assertEquals(2, queue.dequeue());

    }

    @Test
    public void testDequeueFewItemsInNonEmptyQueueFourthAddition() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        queue.dequeue();
        assertNull(queue.dequeue());
    }

    @Test
    public void testSizeInEmptyQueueFourthAddition() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);

        assertEquals(0, queue.size());
    }

    @Test
    public void testSizeInNonEmptyQueueFourthAddition() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.size());
    }

    @Test
    public void testSizeWithComplexOperations() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
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
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
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
    public void testCalculateIndexExtraIndex() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
        int result = queue.calculateIndex(12);

        assertEquals(2, result);
    }

    @Test
    public void testCalculateIndexExactlyCapacity() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
        int result = queue.calculateIndex(10);

        assertEquals(0, result);
    }

    @Test
    public void testCalculateIndexLowIndex() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
        int result = queue.calculateIndex(0);

        assertEquals(0, result);
    }

    @Test
    public void testCalculateIndexMiddleIndex() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
        int result = queue.calculateIndex(1);

        assertEquals(1, result);
    }

    @Test
    public void testCalculateIndexNegativeIndex() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
        int result = queue.calculateIndex(-2);

        assertEquals(8, result);
    }

    @Test
    public void testCalculateIndexExtraNegativeIndex() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
        int result = queue.calculateIndex(-22);

        assertEquals(8, result);
    }

    @Test(expected = IllegalStateException.class)
    public void testQueueEnqueueException() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 3);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(3);

    }

    @Test(expected = IllegalStateException.class)
    public void testQueueEnqueueExceptionManyValues() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 15);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        queue.enqueue(7);
        queue.enqueue(8);
        queue.enqueue(9);
        queue.enqueue(10);
        queue.enqueue(11);
        queue.enqueue(12);
        queue.enqueue(13);
        queue.enqueue(14);
        queue.enqueue(15);

        assertEquals(15, queue.size());
        queue.enqueue(16);
    }

    @Test
    public void testQueueDequeueComplexAlgorythm() {
        QueueFourthAddition<Object> queue = new QueueFourthAddition<>(Object.class, 10);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        assertEquals(6, queue.size());

        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        assertEquals(2, queue.size());

        queue.enqueue(7);
        queue.enqueue(8);
        queue.enqueue(9);
        queue.enqueue(10);
        queue.enqueue(11);
        queue.enqueue(12);
        queue.enqueue(13);
        queue.enqueue(14);
        assertEquals(10, queue.size());
    }

}

