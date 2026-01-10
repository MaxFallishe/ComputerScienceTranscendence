import org.junit.Test;

import static org.junit.Assert.*;

public class StackFivthAdditionTest {

    private static final double DELTA = 1e-9;

    @Test
    public void testSimplePostfixExpression() {
        StackFivthAddition postfixCalculator = new StackFivthAddition();
        String expressions = "8 2 + =";

        double result = postfixCalculator.calculatePostfixExpression(expressions);

        assertEquals(10.0, result, DELTA);
    }

    @Test
    public void testComplexPostfixExpression() {
        StackFivthAddition postfixCalculator = new StackFivthAddition();
        String expressions = "8 2 + 5 * 9 + =";

        double result = postfixCalculator.calculatePostfixExpression(expressions);

        assertEquals(59.0, result, DELTA);
    }

    @Test
    public void testComplexPostfixExpressionWithDivide() {
        StackFivthAddition postfixCalculator = new StackFivthAddition();
        String expressions = "8 2 / 5 + =";

        double result = postfixCalculator.calculatePostfixExpression(expressions);

        assertEquals(9.0, result, DELTA);
    }

    @Test
    public void testComplexPostfixExpressionWithMultiplication() {
        StackFivthAddition postfixCalculator = new StackFivthAddition();
        String expressions = "8 2 * 5 * 2 + =";

        double result = postfixCalculator.calculatePostfixExpression(expressions);

        assertEquals(82.0, result, DELTA);
    }

    @Test
    public void testSimplePostfixExpressionWithoutEqualSign() {
        StackFivthAddition postfixCalculator = new StackFivthAddition();
        String expressions = "8 2 +";

        double result = postfixCalculator.calculatePostfixExpression(expressions);

        assertEquals(10.0, result, DELTA);
    }

    @Test
    public void testComplexPostfixExpressionWithoutEqualSign() {
        StackFivthAddition postfixCalculator = new StackFivthAddition();
        String expressions = "8 2 + 33 -";

        double result = postfixCalculator.calculatePostfixExpression(expressions);

        assertEquals(-23.0, result, DELTA);
    }

}

