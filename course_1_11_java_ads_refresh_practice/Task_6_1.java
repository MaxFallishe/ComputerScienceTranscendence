
public final class DequeFirstAddition {

    private DequeFirstAddition() {}

    // Task number: 6.4
    // Short description: Reverse bidirectional linked list
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public static Boolean isPalindrome(String line) {
        Deque<Character> deque = new Deque<>();

        for (int i = 0; i < line.length(); i++) {
            deque.addTail(line.charAt(i));
        }

        for (int i = 0; deque.size() > 1 ; i++) {
            if (deque.removeTail() != deque.removeFront()) {
                return false;
            }
        }

        return true;
    }
}

