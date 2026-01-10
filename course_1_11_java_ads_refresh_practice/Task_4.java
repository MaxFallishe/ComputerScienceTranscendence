import java.util.*;

// Task number: 4.2
// Short description: Design the stack implementation so that it does work with the head of LinkedList.
public class Stack<T> {
      private final LinkedList<T> deque;

      public Stack() {
            deque = new LinkedList<>();
      }

      // Task number: 4.1.1
      // Short description: Implement method of current stack size calculation
      // Time complexity: O(1)
      // Space Complexity: O(1)
      public int size() {
            return deque.size();  // LinkedList from java.util support O(1) complexity via internal size counter
      }

      // Task number: 4.1.2
      // Short description: Implement method that return and remove from stack top item.
      // Time complexity: O(1)
      // Space Complexity: O(1)
      public T pop() {
            return deque.pollFirst();  // return null if stack is empty;
      }

      // Task number: 4.1.3
      // Short description: Implement method that put new item on top of stack.
      // Time complexity: O(1)
      // Space Complexity: O(1)
      public void push(T val) {
            deque.addFirst(val);
      }

      // Task number: 4.1.4
      // Short description: Implement method that return stack top item.
      // Time complexity: O(1)
      // Space Complexity: O(1)
      public T peek() {
            return deque.peekFirst();  // return null if stack is empty;
      }
}

// Task number: 4.3
// Short description: Without starting the program, describe, how will such a cycle work?
// while (stack.size() > 0)
//     stack.pop()
//     stack.pop()
//
// Answer: If there are an odd number of items on the stack, the program will return the last item from the stack
//         and null, since there are no more items on the stack.

