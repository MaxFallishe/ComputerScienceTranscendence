import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StackFourthAdditionTest {

    @Test
    public void testPushSingleItem() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);

        assertEquals(Integer.valueOf(1), stack.pop());

    }

    @Test
    public void testPushFewItems() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(Integer.valueOf(3), stack.pop());
        assertEquals(Integer.valueOf(2), stack.pop());
        assertEquals(Integer.valueOf(1), stack.pop());

    }


    @Test
    public void testPopSingleItem() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);

        assertEquals(Integer.valueOf(1), stack.pop());
    }

    @Test
    public void testPopFewItems() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(Integer.valueOf(3), stack.pop());
        assertEquals(Integer.valueOf(2), stack.pop());

        stack.push(4);
        stack.push(5);

        assertEquals(Integer.valueOf(5), stack.pop());
        assertEquals(Integer.valueOf(4), stack.pop());
        assertEquals(Integer.valueOf(1), stack.pop());
    }

    @Test
    public void testPopExtraItems() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.push(2);

        assertEquals(Integer.valueOf(2), stack.pop());
        assertEquals(Integer.valueOf(1), stack.pop());
        assertNull(stack.pop());
        assertNull(stack.pop());
        assertNull(stack.pop());
    }

    @Test
    public void testPeekSingleItem() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);

        assertEquals(Integer.valueOf(1), stack.peek());
    }

    @Test
    public void testPeekFewItems() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(Integer.valueOf(3), stack.peek());
        assertEquals(Integer.valueOf(3), stack.peek());

        stack.push(4);
        stack.push(5);

        assertEquals(Integer.valueOf(5), stack.peek());
        assertEquals(Integer.valueOf(5), stack.peek());
        assertEquals(Integer.valueOf(5), stack.peek());
    }

    @Test
    public void testPeekExtraItems() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.push(2);

        assertEquals(Integer.valueOf(2), stack.peek());
        assertEquals(Integer.valueOf(2), stack.peek());

        stack.pop();
        stack.pop();

        assertNull(stack.peek());
        assertNull(stack.peek());
        assertNull(stack.peek());
    }

    @Test
    public void testSizeWithNoItems() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        assertEquals(0, stack.size());
    }

    @Test
    public void testSizeWithSingleItem() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);

        assertEquals(1, stack.size());
    }

    @Test
    public void testSizeWithFewItems() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.size());
    }

    @Test
    public void testSizeAfterSequenceOfPushAndPopOperations() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.pop();
        stack.push(2);
        stack.pop();
        stack.push(3);

        assertEquals(1, stack.size());

        stack.push(4);
        stack.push(5);

        assertEquals(3, stack.size());

        stack.pop();
        stack.pop();
        stack.pop();

        assertEquals(0, stack.size());
    }

    @Test
    public void testSizeAfterExtraPopOperations() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.push(2);

        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();

        assertEquals(0, stack.size());
    }

    @Test
    public void testSizeAfterPushAndPeekAndPopOperations() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.push(2);
        stack.pop();
        stack.peek();
        stack.push(3);
        stack.push(4);
        stack.peek();
        stack.peek();
        stack.pop();

        assertEquals(2, stack.size());
    }

    @Test
    public void testGetMinValueWithEmptyStack() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        assertNull(stack.getMinValue());
    }

    @Test
    public void testGetMinValueWithStackOfSingleValue() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(99);
        assertEquals(Integer.valueOf(99), stack.getMinValue());
    }

    @Test
    public void testGetMinValueWithStackOfFewValuesDesc() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(Integer.valueOf(1), stack.getMinValue());
    }

    @Test
    public void testGetMinValueWithStackOfFewValuesAsc() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(3);
        stack.push(2);
        stack.push(1);
        assertEquals(Integer.valueOf(1), stack.getMinValue());
    }

    @Test
    public void testGetMinValueWithDynamicStackFromFewValuesToEmpty() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(3);
        stack.push(2);
        stack.push(1);
        assertEquals(Integer.valueOf(1), stack.getMinValue());

        stack.pop();
        stack.pop();
        stack.pop();
        assertNull(stack.getMinValue());
    }

    @Test
    public void testGetMinValueWithDynamicStackOfFewValues() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(3);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(1);
        assertEquals(Integer.valueOf(1), stack.getMinValue());

        stack.pop();
        assertEquals(Integer.valueOf(2), stack.getMinValue());

        stack.pop();
        stack.pop();
        stack.pop();
        assertEquals(Integer.valueOf(3), stack.getMinValue());
    }

    @Test
    public void testGetMediumValueWithEmptyStack() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        assertNull(stack.getMediumValue());
    }

    @Test
    public void testGetMediumValueWithStackOfSingleValue() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(99);
        assertEquals(Double.valueOf(99), stack.getMediumValue());
    }

    @Test
    public void testGetMediumValueWithStackOfSingleValueAndReducingToEmptyStack() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(99);
        assertEquals(Double.valueOf(99.0), stack.getMediumValue());
    }

    @Test
    public void testGetMediumValueWithStackOfFewValues() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(Double.valueOf(2.0), stack.getMediumValue());
    }

    @Test
    public void testGetMediumValueWithStackOfFewValuesAndReducing() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(Double.valueOf(2.0), stack.getMediumValue());

        stack.pop();
        assertEquals(Double.valueOf(1.5), stack.getMediumValue());

        stack.pop();
        assertEquals(Double.valueOf(1.0), stack.getMediumValue());
    }

    @Test
    public void testGetMediumValueWithStackOfDynamicFewValues() {
        StackFourthAddition<Integer> stack = new StackFourthAddition<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(Double.valueOf(2.0), stack.getMediumValue());

        stack.pop();
        assertEquals(Double.valueOf(1.5), stack.getMediumValue());

        stack.pop();
        assertEquals(Double.valueOf(1.0), stack.getMediumValue());

        stack.push(99);
        assertEquals(Double.valueOf(50.0), stack.getMediumValue());

        stack.push(50);
        assertEquals(Double.valueOf(50.0), stack.getMediumValue());

    }
}

