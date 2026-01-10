import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StackFirstAdditionTest {

    @Test
    public void testSimpleCorrectBracketsCase() {
        StackFirstAddition bracketsChecker = new StackFirstAddition();

        String brackets = "()";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertTrue(result);

    }

    @Test
    public void testThreeBracketsPairsInBracketsCorrectBracketsCase() {
        StackFirstAddition bracketsChecker = new StackFirstAddition();

        String brackets = "(()()())";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertTrue(result);

    }

    @Test
    public void testComplexCorrectBracketsCase() {
        StackFirstAddition bracketsChecker = new StackFirstAddition();

        String brackets = "(()((())()))";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertTrue(result);

    }

    @Test
    public void testSimpleIncorrectBracketsCase() {
        StackFirstAddition bracketsChecker = new StackFirstAddition();

        String brackets = "(";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }

    @Test
    public void testThreeBracketsWithoutPairsIncorrectBracketsCase() {
        StackFirstAddition bracketsChecker = new StackFirstAddition();

        String brackets = "(((";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }

    @Test
    public void testThreeBracketsPairsWithWrongSequenceIncorrectBracketsCase() {
        StackFirstAddition bracketsChecker = new StackFirstAddition();

        String brackets = "())(()";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }

    @Test
    public void testInvertedBracketsPairsIncorrectBracketsCase() {
        StackFirstAddition bracketsChecker = new StackFirstAddition();

        String brackets = "))((";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }
}

