import java.util.LinkedList;


public class StackThirdAddition<T extends Comparable<? super T>> {
      private final LinkedList<T> deque;
      private final LinkedList<T> minValuesDeque;

      public StackThirdAddition() {
            deque = new LinkedList<T>();
            minValuesDeque = new LinkedList<T>();
      }

      public int size() {
            return deque.size();  // LinkedList from java.util support O(1) complexity via internal size counter
      }

      public T pop() {
            T itemToRemove = deque.pollFirst();
            if (itemToRemove == null)
                  return null;
            if (minValuesDeque.peekFirst() == itemToRemove)
                  minValuesDeque.removeFirst();
            return itemToRemove;  // return null if stack is empty;
      }

      public void push(T val) {
            T minStackValue = minValuesDeque.peekFirst();
            if (minStackValue == null || val.compareTo(minStackValue) <= 0)
                  minValuesDeque.addFirst(val);
            deque.addFirst(val);
      }

      public T peek() {
            return deque.peekFirst();  // return null if stack is empty;
      }

      // Task number: 4.6
      // Short description: Implement function that return a min value from whole stack
      // Time complexity: O(1)
      // Space Complexity: O(1)
      public T getMinValue() {
            return minValuesDeque.peekFirst();
      }
}

