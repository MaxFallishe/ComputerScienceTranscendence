public class StackFirstAddition {

    // Task number: 4.4
    // Short description: Implement function that checking is brackets ")" and "(" in string balanced.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public boolean isBracketsStringBalanced(String bracketsString) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < bracketsString.length(); i++) {
            if (bracketsString.charAt(i) == '(') {
                stack.push('(');
            } else if (bracketsString.charAt(i) == ')') {
                Character item = stack.pop();
                if (item == null)
                    return false;
            }
        }

        return stack.size() == 0;
    }
}

