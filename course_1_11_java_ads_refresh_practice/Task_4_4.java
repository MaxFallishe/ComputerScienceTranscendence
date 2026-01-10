import java.util.LinkedList;
import java.util.Objects;


public class StackFourthAddition<T extends Number & Comparable<? super T>> {
      private final LinkedList<T> deque;
      private final LinkedList<T> minValuesDeque;
      private final LinkedList<Double> mediumValuesDeque;

      public StackFourthAddition() {
            deque = new LinkedList<>();
            minValuesDeque = new LinkedList<>();
            mediumValuesDeque = new LinkedList<>();
      }

      public int size() {
            return deque.size();  // LinkedList from java.util support O(1) complexity via internal size counter
      }

      public T pop() {
            T itemToRemove = deque.pollFirst();
            if (itemToRemove == null)
                  return null;
            if (Objects.equals(minValuesDeque.peekFirst(), itemToRemove))
                  minValuesDeque.removeFirst();
            mediumValuesDeque.removeFirst();
            return itemToRemove;  // return null if stack is empty;
      }

      public void push(T val) {
            T minStackValue = minValuesDeque.peekFirst();
            Double mediumStackValue = mediumValuesDeque.peekFirst();
            if (minStackValue == null || val.compareTo(minStackValue) <= 0)
                  minValuesDeque.addFirst(val);
            if (mediumStackValue == null) {
                  mediumValuesDeque.addFirst(val.doubleValue());
            } else {
                  mediumValuesDeque.addFirst((mediumStackValue * this.size() + val.doubleValue()) / (this.size()+1));
            }

            deque.addFirst(val);
      }

      public T peek() {
            return deque.peekFirst();  // return null if stack is empty;
      }

      public T getMinValue() {
            return minValuesDeque.peekFirst();
      }

      // Task number: 4.7
      // Short description: Implement function that return a medium value from whole stack
      // Time complexity: O(1)
      // Space Complexity: O(1)
      public Double getMediumValue() {
            return mediumValuesDeque.peekFirst();
      }
}

