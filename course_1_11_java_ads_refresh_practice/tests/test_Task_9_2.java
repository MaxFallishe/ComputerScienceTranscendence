import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;


public class NativeDictionarySecondAdditionTest {

    @Test
    public void testPutWithSoloValue() {
        NativeDictionarySecondAddition<Number> nativeDict = new NativeDictionarySecondAddition<>(17, Number.class, 8);

        nativeDict.put("01000001", 333);

        assertEquals(333, nativeDict.get("01000001"));
    }

    @Test
    public void testPutWithZeroValues() {
        NativeDictionarySecondAddition<Number> nativeDict = new NativeDictionarySecondAddition<>(17, Number.class, 8);

        assertNull(nativeDict.get("01000001"));
    }


    @Test
    public void testPutWithFewValues() {
        NativeDictionarySecondAddition<Number> nativeDict = new NativeDictionarySecondAddition<>(17, Number.class, 8);

        nativeDict.put("01000001", 333);
        nativeDict.put("01001001", 111);
        nativeDict.put("01001101", 999);

        assertEquals(333, nativeDict.get("01000001"));
        assertEquals(111, nativeDict.get("01001001"));
        assertEquals(999, nativeDict.get("01001101"));
    }

    @Test
    public void testPutWithMaxValues() {
        NativeDictionarySecondAddition<Number> nativeDict = new NativeDictionarySecondAddition<>(17, Number.class, 8);

        nativeDict.put("01000001", 1);
        nativeDict.put("01001001", 2);
        nativeDict.put("01001101", 3);
        nativeDict.put("00000110", 4);
        nativeDict.put("00001100", 5);
        nativeDict.put("00011000", 6);
        nativeDict.put("00110000", 7);
        nativeDict.put("01100000", 8);
        nativeDict.put("11000000", 9);
        nativeDict.put("10000000", 10);
        nativeDict.put("01000000", 11);
        nativeDict.put("00100000", 12);
        nativeDict.put("00010000", 13);
        nativeDict.put("00001000", 14);
        nativeDict.put("00000100", 15);
        nativeDict.put("00000010", 16);
        nativeDict.put("00000001", 17);

        assertEquals(1, nativeDict.get("01000001"));
        assertEquals(2, nativeDict.get("01001001"));
        assertEquals(3, nativeDict.get("01001101"));
        assertEquals(4, nativeDict.get("00000110"));
        assertEquals(5, nativeDict.get("00001100"));
        assertEquals(6, nativeDict.get("00011000"));
        assertEquals(7, nativeDict.get("00110000"));
        assertEquals(8, nativeDict.get("01100000"));
        assertEquals(9, nativeDict.get("11000000"));
        assertEquals(10, nativeDict.get("10000000"));
        assertEquals(11, nativeDict.get("01000000"));
        assertEquals(12, nativeDict.get("00100000"));
        assertEquals(13, nativeDict.get("00010000"));
        assertEquals(14, nativeDict.get("00001000"));
        assertEquals(15, nativeDict.get("00000100"));
        assertEquals(16, nativeDict.get("00000010"));
        assertEquals(17, nativeDict.get("00000001"));
    }

    @Test
    public void testGetWithSoloValue() {
        NativeDictionarySecondAddition<Number> nativeDict = new NativeDictionarySecondAddition<>(17, Number.class, 8);

        nativeDict.put("01110110", 333);

        assertEquals(333, nativeDict.get("01110110"));
    }

    @Test
    public void testGetWithZeroValues() {
        NativeDictionarySecondAddition<Number> nativeDict = new NativeDictionarySecondAddition<>(17, Number.class, 8);

        assertNull(nativeDict.get("01000001"));
        assertNull(nativeDict.get("11011001"));
        assertNull(nativeDict.get("01011001"));
    }

    @Test
    public void testGetWithFewValuesAndOverride() {
        NativeDictionarySecondAddition<Number> nativeDict = new NativeDictionarySecondAddition<>(17, Number.class, 8);

        nativeDict.put("01110110", 333);
        nativeDict.put("01111110", 222);
        nativeDict.put("01111000", 111);

        assertEquals(333, nativeDict.get("01110110"));
        assertEquals(222, nativeDict.get("01111110"));
        assertEquals(111, nativeDict.get("01111000"));

        nativeDict.put("01110110", 3);
        nativeDict.put("01111110", 2);
        nativeDict.put("01111000", 1);

        assertEquals(3, nativeDict.get("01110110"));
        assertEquals(2, nativeDict.get("01111110"));
        assertEquals(1, nativeDict.get("01111000"));

    }

    @Test
    public void testIsKeytWithZeroValues() {
        NativeDictionarySecondAddition<Number> nativeDict = new NativeDictionarySecondAddition<>(17, Number.class, 8);

        assertFalse(nativeDict.isKey("01000001"));
        assertFalse(nativeDict.isKey("11011001"));
        assertFalse(nativeDict.isKey("01011001"));
    }

    @Test
    public void testIsKeytWithFewValues() {
        NativeDictionarySecondAddition<Number> nativeDict = new NativeDictionarySecondAddition<>(17, Number.class, 8);

        nativeDict.put("01000001", 1);
        nativeDict.put("01011001", 1);

        assertTrue(nativeDict.isKey("01000001"));
        assertFalse(nativeDict.isKey("11011001"));
        assertTrue(nativeDict.isKey("01011001"));
    }

    @Test
    public void testIsKeytWithFewValuesAfterOverride() {
        NativeDictionarySecondAddition<Number> nativeDict = new NativeDictionarySecondAddition<>(17, Number.class, 8);

        nativeDict.put("01000001", 1);
        nativeDict.put("01011001", 1);

        assertTrue(nativeDict.isKey("01000001"));
        assertFalse(nativeDict.isKey("11011001"));
        assertTrue(nativeDict.isKey("01011001"));

        nativeDict.put("01000001", 128);
        nativeDict.put("01011001", 256);

        assertTrue(nativeDict.isKey("01000001"));
        assertFalse(nativeDict.isKey("11011001"));
        assertTrue(nativeDict.isKey("01011001"));
    }

    @Test
    public void testRemoveAllValues() {
        NativeDictionarySecondAddition<Number> nativeDict = new NativeDictionarySecondAddition<>(17, Number.class, 8);

        nativeDict.put("01000001", 1);
        nativeDict.put("11011111", 2);
        nativeDict.put("11000111", 3);

        assertTrue(nativeDict.isKey("01000001"));
        assertTrue(nativeDict.isKey("11011111"));
        assertTrue(nativeDict.isKey("11000111"));
        assertEquals(3, nativeDict.numElements);

        assertTrue(nativeDict.remove("01000001"));
        assertTrue(nativeDict.remove("11011111"));
        assertTrue(nativeDict.remove("11000111"));

        assertEquals(0, nativeDict.numElements);
    }


    @Test
    public void testRemoveAllValuesWithEctraEttempts() {
        NativeDictionarySecondAddition<Number> nativeDict = new NativeDictionarySecondAddition<>(17, Number.class, 8);

        nativeDict.put("01000001", 1);
        nativeDict.put("11011111", 2);
        nativeDict.put("11000111", 3);

        assertTrue(nativeDict.isKey("01000001"));
        assertTrue(nativeDict.isKey("11011111"));
        assertTrue(nativeDict.isKey("11000111"));
        assertEquals(3, nativeDict.numElements);

        assertTrue(nativeDict.remove("01000001"));
        assertTrue(nativeDict.remove("11011111"));
        assertTrue(nativeDict.remove("11000111"));

        assertEquals(0, nativeDict.numElements);

        assertFalse(nativeDict.remove("01000001"));
        assertFalse(nativeDict.remove("11011111"));
        assertFalse(nativeDict.remove("11000111"));

        assertEquals(0, nativeDict.numElements);
    }
}

