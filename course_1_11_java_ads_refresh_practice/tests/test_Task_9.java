import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

// Task number: 9.4
// Short description: Implement tests for NativeDictionary class.
public class NativeDictionaryTest {

    @Test
    public void testHashFunWithOneCharStringAsLetter() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        assertEquals(12, nativeDict.hashFun("a"));
        assertEquals(12, nativeDict.hashFun("a"));
    }

    @Test
    public void testHashFunWithAnotherOneCharStringAsLetter() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        assertEquals(13, nativeDict.hashFun("b"));
        assertEquals(13, nativeDict.hashFun("b"));
    }

    @Test
    public void testHashFunWithCornerOneCharStringAsLetter() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        assertEquals(0, nativeDict.hashFun("f"));
        assertEquals(0, nativeDict.hashFun("f"));
    }

    @Test
    public void testHashFunWithDifferentOrderOfSameChars() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        assertEquals(5, nativeDict.hashFun("flow"));
        assertEquals(2, nativeDict.hashFun("wolf"));

    }

    @Test
    public void testPutWithSoloValue() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        nativeDict.put("ac", 333);

        assertEquals(333, nativeDict.get("ac"));
    }

    @Test
    public void testPutWithZeroValues() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        assertNull(nativeDict.get("ac"));
    }


    @Test
    public void testPutWithFewValues() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        nativeDict.put("ac", 333);
        nativeDict.put("ca", 111);
        nativeDict.put("bc", 999);

        assertEquals(333, nativeDict.get("ac"));
        assertEquals(111, nativeDict.get("ca"));
        assertEquals(999, nativeDict.get("bc"));
    }

    @Test
    public void testPutWithMaxValues() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        nativeDict.put("ac", 1);
        nativeDict.put("ca", 2);
        nativeDict.put("bc", 3);
        nativeDict.put("cb", 4);
        nativeDict.put("ab", 5);
        nativeDict.put("ba", 6);
        nativeDict.put("aa", 7);
        nativeDict.put("bb", 8);
        nativeDict.put("cc", 9);
        nativeDict.put("abc", 10);
        nativeDict.put("cba", 11);
        nativeDict.put("bac", 12);
        nativeDict.put("cab", 13);
        nativeDict.put("bca", 14);
        nativeDict.put("aaa", 15);
        nativeDict.put("bbb", 16);
        nativeDict.put("ccc", 17);

        assertEquals(1, nativeDict.get("ac"));
        assertEquals(2, nativeDict.get("ca"));
        assertEquals(3, nativeDict.get("bc"));
        assertEquals(4, nativeDict.get("cb"));
        assertEquals(5, nativeDict.get("ab"));
        assertEquals(6, nativeDict.get("ba"));
        assertEquals(7, nativeDict.get("aa"));
        assertEquals(8, nativeDict.get("bb"));
        assertEquals(9, nativeDict.get("cc"));
        assertEquals(10, nativeDict.get("abc"));
        assertEquals(11, nativeDict.get("cba"));
        assertEquals(12, nativeDict.get("bac"));
        assertEquals(13, nativeDict.get("cab"));
        assertEquals(14, nativeDict.get("bca"));
        assertEquals(15, nativeDict.get("aaa"));
        assertEquals(16, nativeDict.get("bbb"));
        assertEquals(17, nativeDict.get("ccc"));
    }

    @Test
    public void testGetWithSoloValue() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        nativeDict.put("tryst", 333);

        assertEquals(333, nativeDict.get("tryst"));
    }

    @Test
    public void testGetWithZeroValues() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        assertNull(nativeDict.get("ac"));
        assertNull(nativeDict.get("!!"));
        assertNull(nativeDict.get("23@"));
    }

    @Test
    public void testGetWithFewValuesAndOverride() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        nativeDict.put("tryst", 333);
        nativeDict.put("mod", 222);
        nativeDict.put("boom", 111);

        assertEquals(333, nativeDict.get("tryst"));
        assertEquals(222, nativeDict.get("mod"));
        assertEquals(111, nativeDict.get("boom"));

        nativeDict.put("tryst", 3);
        nativeDict.put("mod", 2);
        nativeDict.put("boom", 1);

        assertEquals(3, nativeDict.get("tryst"));
        assertEquals(2, nativeDict.get("mod"));
        assertEquals(1, nativeDict.get("boom"));

    }

    @Test
    public void testIsKeytWithZeroValues() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        assertFalse(nativeDict.isKey("ac"));
        assertFalse(nativeDict.isKey("!!"));
        assertFalse(nativeDict.isKey("23@"));
    }

    @Test
    public void testIsKeytWithFewValues() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        nativeDict.put("ac", 1);
        nativeDict.put("23@", 1);

        assertTrue(nativeDict.isKey("ac"));
        assertFalse(nativeDict.isKey("!!"));
        assertTrue(nativeDict.isKey("23@"));
    }

    @Test
    public void testIsKeytWithFewValuesAfterOverride() {
        NativeDictionary<Number> nativeDict = new NativeDictionary<>(17, Number.class);

        nativeDict.put("ac", 1);
        nativeDict.put("23@", 1);

        assertTrue(nativeDict.isKey("ac"));
        assertFalse(nativeDict.isKey("!!"));
        assertTrue(nativeDict.isKey("23@"));

        nativeDict.put("ac", 128);
        nativeDict.put("23@", 256);

        assertTrue(nativeDict.isKey("ac"));
        assertFalse(nativeDict.isKey("!!"));
        assertTrue(nativeDict.isKey("23@"));
    }
}

