import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class QueueFirstAdditionTest {

    @Test
    public void testShiftQueueOneByOneStep() {
        QueueFirstAddition<Object> queue = new QueueFirstAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        queue.shift(1);
        assertEquals(queue.storage, List.of(5, 1, 2, 3, 4));

        queue.shift(1);
        assertEquals(queue.storage, List.of(4, 5, 1, 2, 3));

        queue.shift(1);
        assertEquals(queue.storage, List.of(3, 4, 5, 1, 2));

        queue.shift(1);
        assertEquals(queue.storage, List.of(2, 3, 4, 5, 1));

        queue.shift(1);
        assertEquals(queue.storage, List.of(1, 2, 3, 4, 5));

    }

    @Test
    public void testShiftQueueThreeStepsByThreeWithFiveElements() {
        QueueFirstAddition<Object> queue = new QueueFirstAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        queue.shift(3);
        assertEquals(queue.storage, List.of(3, 4, 5, 1, 2));

        queue.shift(3);
        assertEquals(queue.storage, List.of(5, 1, 2, 3, 4));

        queue.shift(3);
        assertEquals(queue.storage, List.of(2, 3, 4, 5, 1));

    }

    @Test
    public void testShiftQueueZeroSteps() {
        QueueFirstAddition<Object> queue = new QueueFirstAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        queue.shift(0);
        assertEquals(queue.storage, List.of(1, 2, 3, 4, 5));

        queue.shift(0);
        assertEquals(queue.storage, List.of(1, 2, 3, 4, 5));

        queue.shift(0);
        assertEquals(queue.storage, List.of(1, 2, 3, 4, 5));

    }

    @Test
    public void testShiftWithStepsAmountEqualToElementCount() {
        QueueFirstAddition<Object> queue = new QueueFirstAddition<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        queue.shift(5);
        assertEquals(queue.storage, List.of(1, 2, 3, 4, 5));

        queue.shift(5);
        assertEquals(queue.storage, List.of(1, 2, 3, 4, 5));

        queue.shift(5);
        assertEquals(queue.storage, List.of(1, 2, 3, 4, 5));

    }

}

