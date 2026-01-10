public class StackSecondAddition {

    // Task number: 4.5
    // Short description: Implement function that checking is brackets ")", "(", "]", "[", "{" and "}" in string balanced.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public boolean isBracketsStringBalanced(String bracketsString) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < bracketsString.length(); i++) {
            switch (bracketsString.charAt(i)) {
                case '(' ->
                    stack.push(')');
                case '[' ->
                    stack.push(']');
                case '{' ->
                    stack.push('}');
                case ')', ']', '}' -> {
                    Character item = stack.pop();
                    if (item == null || item != bracketsString.charAt(i))
                        return false;
                }
            }
        }

        return stack.size() == 0;
    }
}

