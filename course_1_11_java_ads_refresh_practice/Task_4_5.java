import com.bluecatcode.junit.shaded.org.apache.commons.lang3.math.NumberUtils;

import java.util.*;

public class StackFivthAddition {

    // Task number: 4.8
    // Short description: Implement function that implement calculation with postfix type of expressions.
    // Time complexity: O(N)
    // Space Complexity: O(1)
    public double calculatePostfixExpression(String expression) {
        // expression string example "8 2 + 5 * 9 + ="
        Stack<String> stackWithExpression = new Stack<>();
        Stack<Double> calculationStack = new Stack<>();

        String[] parts = expression.split(" ");
        List<String> list = new ArrayList<>(Arrays.asList(parts));
        Collections.reverse(list);

        for (String p : list) {
            stackWithExpression.push(p);
        }

        for (int i = stackWithExpression.size(); i > 0; i--) {
            String item = stackWithExpression.pop();
            boolean isParsable = NumberUtils.isParsable(item);
            if (isParsable) {
                calculationStack.push(Double.parseDouble(item));
            } else {
                double arg1 = calculationStack.pop();
                double arg2 = calculationStack.size() == 0 ? 1.0 : calculationStack.pop();
                switch (item) {
                    case "+" -> calculationStack.push(arg2 + arg1);
                    case "-" -> calculationStack.push(arg2 - arg1);
                    case "*" -> calculationStack.push(arg2 * arg1);
                    case "/" -> calculationStack.push(arg2 / arg1);
                    case "=" -> {
                        calculationStack.push(arg1);
                        return arg1;
                    }
                }
            }
        }

        return calculationStack.peek();
    }
}

