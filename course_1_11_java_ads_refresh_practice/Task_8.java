import java.util.Objects;

public class HashTable
  {
    public int size;
    public int step;
    public int numElements = 0;
    public String [] slots;
    public static final int MAX_LOOPS_COUNT = 1000000;

    public HashTable(int sz, int stp)
    {
      size = sz;
      step = stp;
      slots = new String[size];
      for(int i=0; i<size; i++) slots[i] = null;
    }

    // Task number: 8.0.1
    // Short description: Implement a method thath calculates the slot index based on the input value.
    // Time complexity: O(1)
    // Space Complexity: O(1)
    public int hashFun(String value)
    {
        int result = 0;
        for (int i = 0; i < value.length(); i++) {
            result += value.charAt(i);
        }
        return normalizeIndx(result);
    }

    // Task number: 8.0.2
    // Short description: Implement a method that searches for a suitable slot, taking into account collisions, or returns a failure if it failed.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public int seekSlot(String value)
    {
        if (this.size == this.numElements)
            return -1;

        int slotIndx = this.hashFun(value);
        for (int i = 0; i < MAX_LOOPS_COUNT; i++) {
            if (slots[slotIndx] == null)
                return slotIndx;
            slotIndx = normalizeIndx(slotIndx + step);
        }
        return -1;
    }

    // Task number: 8.0.3
    // Short description: Implement a method that puts the value in the slot calculated using the search function
    // Time complexity: O(N)
    // Space Complexity: O(1)
     public int put(String value)
     {
         int slotIndx = this.seekSlot(value);
         if (slotIndx == -1)
             return -1;

         slots[slotIndx] = value;
         this.numElements += 1;
         return slotIndx;
     }

    // Task number: 8.0.4
    // Short description: Implement a method that checks whether the specified value is available in the slots.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public int find(String value) {
        int slotIndx = this.hashFun(value);
        for (int i = 0; i < MAX_LOOPS_COUNT; i++) {

            if (Objects.equals(slots[slotIndx], null))
                return -1;
            if (Objects.equals(slots[slotIndx], value))
                return slotIndx;

            slotIndx = normalizeIndx(slotIndx + step);
        }
        return -1;
    }

     public int normalizeIndx(int indx) {
        return indx % this.size;
     }
  }

