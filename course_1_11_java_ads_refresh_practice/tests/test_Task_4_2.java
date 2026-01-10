import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StackSecondAdditionTest {

    @Test
    public void testSimpleCorrectRoundBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "()";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertTrue(result);
    }

    @Test
    public void testSimpleCorrectSquareBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "[]";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertTrue(result);
    }

    @Test
    public void testSimpleCorrectCurlyBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "{}";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertTrue(result);
    }

    @Test
    public void testSimpleCorrectMultiBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "[({})]";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertTrue(result);
    }


    @Test
    public void testThreeBracketsPairsInBracketsCorrectBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "(()()())";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertTrue(result);

    }

    @Test
    public void testComplexCorrectBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "(()((())()))";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertTrue(result);

    }

    @Test
    public void testComplexCorrectMultiBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "[()(([]){})]";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertTrue(result);

    }

    @Test
    public void testSimpleIncorrectBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "(";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }

    @Test
    public void testSimpleIncorrectMultiBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "{([";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }

    @Test
    public void testThreeRoundBracketsWithoutPairsIncorrectBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "(((";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }

    @Test
    public void testThreeSquareBracketsWithoutPairsIncorrectBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "[[[";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }

    @Test
    public void testThreeCurlyBracketsWithoutPairsIncorrectBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "{{{";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }

    @Test
    public void testThreeRoundBracketsPairsWithWrongSequenceIncorrectBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "())(()";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }

    @Test
    public void testThreeMultiBracketsPairsWithWrongSequenceIncorrectBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "{})([]";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }

    @Test
    public void testInvertedRoundBracketsPairsIncorrectBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "))((";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }

    @Test
    public void testInvertedMultiBracketsPairsIncorrectBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "]}{[";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }


    @Test
    public void testMultiBracketsPairsWithWrongPairsIncorrectBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "{()[]]";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }

    @Test
    public void testComplexMultiBracketsPairsWithWrongPairsIncorrectBracketsCase() {
        StackSecondAddition bracketsChecker = new StackSecondAddition();

        String brackets = "{{((){[]{}})})";
        boolean result = bracketsChecker.isBracketsStringBalanced(brackets);

        assertFalse(result);
    }
}

