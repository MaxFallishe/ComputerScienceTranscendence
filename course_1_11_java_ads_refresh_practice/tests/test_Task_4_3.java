import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StackThirdAdditionTest {

    @Test
    public void testPushSingleItem() {
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

        stack.push(1);

        assertEquals(Integer.valueOf(1), stack.pop());

    }

    @Test
    public void testPushFewItems() {
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(Integer.valueOf(3), stack.pop());
        assertEquals(Integer.valueOf(2), stack.pop());
        assertEquals(Integer.valueOf(1), stack.pop());

    }


    @Test
    public void testPopSingleItem() {
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

        stack.push(1);

        assertEquals(Integer.valueOf(1), stack.pop());
    }

    @Test
    public void testPopFewItems() {
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

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
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

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
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

        stack.push(1);

        assertEquals(Integer.valueOf(1), stack.peek());
    }

    @Test
    public void testPeekFewItems() {
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

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
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

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
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

        assertEquals(0, stack.size());
    }

    @Test
    public void testSizeWithSingleItem() {
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

        stack.push(1);

        assertEquals(1, stack.size());
    }

    @Test
    public void testSizeWithFewItems() {
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.size());
    }

    @Test
    public void testSizeAfterSequenceOfPushAndPopOperations() {
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

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
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

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
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

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
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

        assertNull(stack.getMinValue());
    }

    @Test
    public void testGetMinValueWithStackOfSingleValue() {
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

        stack.push(99);
        assertEquals(Integer.valueOf(99), stack.getMinValue());
    }

    @Test
    public void testGetMinValueWithStackOfFewValuesDesc() {
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(Integer.valueOf(1), stack.getMinValue());
    }

    @Test
    public void testGetMinValueWithStackOfFewValuesAsc() {
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

        stack.push(3);
        stack.push(2);
        stack.push(1);
        assertEquals(Integer.valueOf(1), stack.getMinValue());
    }

    @Test
    public void testGetMinValueWithDynamicStackFromFewValuesToEmpty() {
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

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
        StackThirdAddition<Integer> stack = new StackThirdAddition<>();

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

}

