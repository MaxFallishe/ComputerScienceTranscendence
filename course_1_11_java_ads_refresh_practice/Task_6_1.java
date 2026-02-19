
public final class DequeFirstAddition {

    private DequeFirstAddition() {}

    // Task number: 6.4
    // Short description: Reverse bidirectional linked list
    // Time complexity: O(N)
    // Space Complexity: O(1)
    // ---Refleсtion.---
    // In implementing the algorithm for checking the palindrome, I was able to immediately come up with the method
    // conceived in the task. First, convert the string to a deque, and then, using the built-in capabilities
    // of the data structure, break off one element from each side of the deque, checking them for compliance.
    // If there is no match, we immediately return False. But it is important to remember that you need to take into
    // account the case with an odd number of characters in a similar string, so with the condition "deque.size() > 1"
    // in the loop, we guarantee that we will correctly return False in the step with one last character in the string.
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

