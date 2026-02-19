import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;


public class HashTableFirstAdditionTest {

    @Test
    public void testHashFunWithOneCharStringAsLetter() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        assertEquals(12, hashTable.hashFun("a"));
        assertEquals(12, hashTable.hashFun("a"));
        assertTrue(hashTable.hashFun("a") < 17);
    }

    @Test
    public void testHashFunWithOneCharThatIndxZero() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        assertEquals(0, hashTable.hashFun("f"));
        assertEquals(0, hashTable.hashFun("f"));
        assertTrue(hashTable.hashFun("f") < 17);
    }

    @Test
    public void testHashFunWithOneCharStringAsSign() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        assertEquals(13, hashTable.hashFun("@"));
        assertEquals(13, hashTable.hashFun("@"));
        assertTrue(hashTable.hashFun("@") < 17);
    }

    @Test
    public void testHashFunWithFewCharsStringAsLetters() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        assertEquals(3, hashTable.hashFun("apple"));
        assertEquals(3, hashTable.hashFun("apple"));
        assertTrue(hashTable.hashFun("apple") < 17);
    }

    @Test
    public void testHashFunWithFewCharsStringAsLettersAndSigns() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        assertEquals(15, hashTable.hashFun("@apple!"));
        assertEquals(15, hashTable.hashFun("@apple!"));
        assertTrue(hashTable.hashFun("@apple!") < 17);
    }

    @Test
    public void testSeekSlotWithEmptyHashTableAndOneCharString() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        assertEquals(12, hashTable.seekSlot("a"));
        assertEquals(12, hashTable.seekSlot("a"));
    }

    @Test
    public void testSeekSlotWithNonEmptyHashTableAndOneCharString() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        assertEquals(12, hashTable.seekSlot("a"));
        assertEquals(12, hashTable.put("a"));

        assertEquals(15, hashTable.seekSlot("a"));
        assertEquals(15, hashTable.put("a"));

        assertEquals(1, hashTable.seekSlot("a"));
        assertEquals(1, hashTable.put("a"));
    }

    @Test
    public void testSeekSlotWithNonEmptyHashTableAndFewCharString() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        assertEquals(13, hashTable.seekSlot("condor!!!"));
        assertEquals(13, hashTable.put("condor!!!"));

        assertEquals(16, hashTable.seekSlot("condor!!!"));
        assertEquals(16, hashTable.put("condor!!!"));

        assertEquals(2, hashTable.seekSlot("condor!!!"));
        assertEquals(2, hashTable.put("condor!!!"));
    }

    @Test
    public void testSeekSlotWithFullHashTableAndFewCharString() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        for (int i = 0; i < 17; i++) {
            hashTable.put("condor!!!");
        }

        assertEquals(-1, hashTable.seekSlot("condor!!!"));
        assertEquals(-1, hashTable.seekSlot("a"));
        assertEquals(17, hashTable.numElements);
    }

    @Test
    public void testPutWithNonEmptyHashTableAndFewCharString() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        assertEquals(13, hashTable.put("condor!!!"));

        assertEquals(16, hashTable.put("condor!!!"));

        assertEquals(2, hashTable.put("condor!!!"));
    }

    @Test
    public void testPutWithFullHashTableAndFewCharString() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        for (int i = 0; i < 9; i++) {
            hashTable.put("condor!!!");
            hashTable.put("@@$");
        }

        assertEquals(21, hashTable.seekSlot("condor!!!"));
        assertEquals(21, hashTable.seekSlot("@@$"));
        assertEquals(23, hashTable.seekSlot("a"));
        assertEquals(18, hashTable.numElements);
    }

    @Test
    public void testFindWithEmptyHashTableAndFewCharString() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        assertEquals(-1, hashTable.find("condor!!!"));

        assertEquals(-1, hashTable.find("condor!!!"));

        assertEquals(-1, hashTable.find("condor!!!"));
    }

    @Test
    public void testFindWithNonEmptyHashTableAndFewCharString() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        assertEquals(-1, hashTable.find("condor!!!"));

        assertEquals(13, hashTable.put("condor!!!"));
        assertEquals(13, hashTable.find("condor!!!"));

        assertEquals(13, hashTable.find("condor!!!"));
    }

    @Test
    public void testFindWithFullHashTableAndFewCharString() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        for (int i = 0; i < 17; i++) {
            hashTable.put("condor!!!");
        }

        assertEquals(13, hashTable.find("condor!!!"));
        assertEquals(13, hashTable.find("condor!!!"));
        assertEquals(-1, hashTable.find("gryphon"));
        assertEquals(17, hashTable.numElements);

        assertEquals(35, hashTable.put("gryphon"));
        assertEquals(35, hashTable.find("gryphon"));

    }

    @Test
    public void testExtendSlotsWithFullHashTableAndFewCharString() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        for (int i = 0; i < 17; i++) {
            hashTable.put("condor!!!");
        }
        assertEquals(17, hashTable.numElements);
        assertEquals(17, hashTable.size);
        assertEquals(17, hashTable.slots.length);
    }

    @Test
    public void testExtendSlotsWithExtraFullHashTableAndFewCharString() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        for (int i = 0; i < 18; i++) {
            hashTable.put("condor!!!");
        }
        assertEquals(18, hashTable.numElements);
        assertEquals(37, hashTable.size);
        assertEquals(37, hashTable.slots.length);
    }

    @Test
    public void testExtendSlotsWithExtendedHashTableAndFewCharString() {
        HashTableFirstAddition hashTable = new HashTableFirstAddition(17, 3);

        for (int i = 0; i < 18; i++) {
            hashTable.put("condor!!!");
        }
        assertEquals(18, hashTable.numElements);
        assertEquals(37, hashTable.size);
        assertEquals(37, hashTable.slots.length);

        assertEquals(4, hashTable.find("condor!!!"));
        assertEquals(4, hashTable.find("condor!!!"));

        assertEquals(35, hashTable.put("gryphon"));
        assertEquals(35, hashTable.find("gryphon"));
        assertEquals(19, hashTable.numElements);
    }

}

