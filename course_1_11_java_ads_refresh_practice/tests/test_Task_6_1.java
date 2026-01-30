import org.junit.Test;

import static org.junit.Assert.*;


public class DequeFirstAdditionTest {

    @Test
    public void testIsPalindromeWithEmptyString() {
        String line = "";

        Boolean result = DequeFirstAddition.isPalindrome(line);
        assertTrue(result);
    }

    @Test
    public void testIsPalindromeWithSingleCharacter() {
        String line = "r";

        Boolean result = DequeFirstAddition.isPalindrome(line);
        assertTrue(result);
    }

    @Test
    public void testIsPalindromeWithShortMirroredOddWord() {
        String line = "errre";

        Boolean result = DequeFirstAddition.isPalindrome(line);
        assertTrue(result);
    }

    @Test
    public void testIsPalindromeWithShortMirroredEvenWord() {
        String line = "ohho";

        Boolean result = DequeFirstAddition.isPalindrome(line);
        assertTrue(result);
    }

    @Test
    public void testIsPalindromeWithShortNonMirroredOddWord() {
        String line = "ohroo";

        Boolean result = DequeFirstAddition.isPalindrome(line);
        assertFalse(result);
    }


    @Test
    public void testIsPalindromeWithShortNonMirroredEvenWord() {
        String line = "oohroo";

        Boolean result = DequeFirstAddition.isPalindrome(line);
        assertFalse(result);
    }

    @Test
    public void testIsPalindromeWithLongNonMirroredEvenWord() {
        String line = "rainnior";

        Boolean result = DequeFirstAddition.isPalindrome(line);
        assertFalse(result);
    }


    @Test
    public void testIsPalindromeWithLongNonMirroredOddWord() {
        String line = "rainnkior";

        Boolean result = DequeFirstAddition.isPalindrome(line);
        assertFalse(result);
    }
}

