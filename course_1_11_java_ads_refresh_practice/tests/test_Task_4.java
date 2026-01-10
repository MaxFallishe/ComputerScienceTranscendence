import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

public class StackTest {

    @Test
    public void testPushSingleItem() {
        Stack<Object> stack = new Stack<>();

        stack.push(1);

        assertEquals(1, stack.pop());

    }

    @Test
    public void testPushFewItems() {
        Stack<Object> stack = new Stack<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());

    }

    @Test
    public void testPushFewItemsWithDifferentTypes() {
        Stack<Object> stack = new Stack<>();

        stack.push(1);
        stack.push(2.2);
        stack.push("3");

        assertEquals("3", stack.pop());
        assertEquals(2.2, stack.pop());
        assertEquals(1, stack.pop());

    }

    @Test
    public void testPopSingleItem() {
        Stack<Object> stack = new Stack<>();

        stack.push(1);

        assertEquals(1, stack.pop());
    }

    @Test
    public void testPopFewItems() {
        Stack<Object> stack = new Stack<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());

        stack.push(4);
        stack.push(5);

        assertEquals(5, stack.pop());
        assertEquals(4, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    public void testPopExtraItems() {
        Stack<Object> stack = new Stack<>();

        stack.push(1);
        stack.push(2);

        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertNull(stack.pop());
        assertNull(stack.pop());
        assertNull(stack.pop());
    }

    @Test
    public void testPeekSingleItem() {
        Stack<Object> stack = new Stack<>();

        stack.push(1);

        assertEquals(1, stack.peek());
    }

    @Test
    public void testPeekFewItems() {
        Stack<Object> stack = new Stack<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.peek());
        assertEquals(3, stack.peek());

        stack.push(4);
        stack.push(5);

        assertEquals(5, stack.peek());
        assertEquals(5, stack.peek());
        assertEquals(5, stack.peek());
    }

    @Test
    public void testPeekExtraItems() {
        Stack<Object> stack = new Stack<>();

        stack.push(1);
        stack.push(2);

        assertEquals(2, stack.peek());
        assertEquals(2, stack.peek());

        stack.pop();
        stack.pop();

        assertNull(stack.peek());
        assertNull(stack.peek());
        assertNull(stack.peek());
    }

    @Test
    public void testSizeWithNoItems() {
        Stack<Object> stack = new Stack<>();

        assertEquals(0, stack.size());
    }

    @Test
    public void testSizeWithSingleItem() {
        Stack<Object> stack = new Stack<>();

        stack.push(1);

        assertEquals(1, stack.size());
    }

    @Test
    public void testSizeWithFewItems() {
        Stack<Object> stack = new Stack<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.size());
    }

    @Test
    public void testSizeAfterSequenceOfPushAndPopOperations() {
        Stack<Object> stack = new Stack<>();

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
        Stack<Object> stack = new Stack<>();

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
        Stack<Object> stack = new Stack<>();

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

}

