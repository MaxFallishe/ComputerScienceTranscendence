import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

// Task number: 8.0
// Short description: Implement tests for HashTable class.
public class HashTableTest {

    @Test
    public void testHashFunWithOneCharStringAsLetter() {
        HashTable hashTable = new HashTable(17, 3);

        assertEquals(12, hashTable.hashFun("a"));
        assertEquals(12, hashTable.hashFun("a"));
        assertTrue(hashTable.hashFun("a") < 17);
    }

    @Test
    public void testHashFunWithOneCharThatIndxZero() {
        HashTable hashTable = new HashTable(17, 3);

        assertEquals(0, hashTable.hashFun("f"));
        assertEquals(0, hashTable.hashFun("f"));
        assertTrue(hashTable.hashFun("f") < 17);
    }

    @Test
    public void testHashFunWithOneCharStringAsSign() {
        HashTable hashTable = new HashTable(17, 3);

        assertEquals(13, hashTable.hashFun("@"));
        assertEquals(13, hashTable.hashFun("@"));
        assertTrue(hashTable.hashFun("@") < 17);
    }

    @Test
    public void testHashFunWithFewCharsStringAsLetters() {
        HashTable hashTable = new HashTable(17, 3);

        assertEquals(3, hashTable.hashFun("apple"));
        assertEquals(3, hashTable.hashFun("apple"));
        assertTrue(hashTable.hashFun("apple") < 17);
    }

    @Test
    public void testHashFunWithFewCharsStringAsLettersAndSigns() {
        HashTable hashTable = new HashTable(17, 3);

        assertEquals(15, hashTable.hashFun("@apple!"));
        assertEquals(15, hashTable.hashFun("@apple!"));
        assertTrue(hashTable.hashFun("@apple!") < 17);
    }

    @Test
    public void testSeekSlotWithEmptyHashTableAndOneCharString() {
        HashTable hashTable = new HashTable(17, 3);

        assertEquals(12, hashTable.seekSlot("a"));
        assertEquals(12, hashTable.seekSlot("a"));
    }

    @Test
    public void testSeekSlotWithNonEmptyHashTableAndOneCharString() {
        HashTable hashTable = new HashTable(17, 3);

        assertEquals(12, hashTable.seekSlot("a"));
        assertEquals(12, hashTable.put("a"));

        assertEquals(15, hashTable.seekSlot("a"));
        assertEquals(15, hashTable.put("a"));

        assertEquals(1, hashTable.seekSlot("a"));
        assertEquals(1, hashTable.put("a"));
    }

    @Test
    public void testSeekSlotWithNonEmptyHashTableAndFewCharString() {
        HashTable hashTable = new HashTable(17, 3);

        assertEquals(13, hashTable.seekSlot("condor!!!"));
        assertEquals(13, hashTable.put("condor!!!"));

        assertEquals(16, hashTable.seekSlot("condor!!!"));
        assertEquals(16, hashTable.put("condor!!!"));

        assertEquals(2, hashTable.seekSlot("condor!!!"));
        assertEquals(2, hashTable.put("condor!!!"));
    }

    @Test
    public void testSeekSlotWithFullHashTableAndFewCharString() {
        HashTable hashTable = new HashTable(17, 3);

        for (int i = 0; i < 17; i++) {
            hashTable.put("condor!!!");
        }

        assertEquals(-1, hashTable.seekSlot("condor!!!"));
        assertEquals(-1, hashTable.seekSlot("a"));
        assertEquals(17, hashTable.numElements);
    }

    @Test
    public void testPutWithNonEmptyHashTableAndFewCharString() {
        HashTable hashTable = new HashTable(17, 3);

        assertEquals(13, hashTable.put("condor!!!"));

        assertEquals(16, hashTable.put("condor!!!"));

        assertEquals(2, hashTable.put("condor!!!"));
    }

    @Test
    public void testPutWithFullHashTableAndFewCharString() {
        HashTable hashTable = new HashTable(17, 3);

        for (int i = 0; i < 9; i++) {
            hashTable.put("condor!!!");
            hashTable.put("@@$");
        }

        assertEquals(-1, hashTable.seekSlot("condor!!!"));
        assertEquals(-1, hashTable.seekSlot("@@$"));
        assertEquals(-1, hashTable.seekSlot("a"));
        assertEquals(17, hashTable.numElements);
    }

    @Test
    public void testFindWithEmptyHashTableAndFewCharString() {
        HashTable hashTable = new HashTable(17, 3);

        assertEquals(-1, hashTable.find("condor!!!"));

        assertEquals(-1, hashTable.find("condor!!!"));

        assertEquals(-1, hashTable.find("condor!!!"));
    }

    @Test
    public void testFindWithNonEmptyHashTableAndFewCharString() {
        HashTable hashTable = new HashTable(17, 3);

        assertEquals(-1, hashTable.find("condor!!!"));

        assertEquals(13, hashTable.put("condor!!!"));
        assertEquals(13, hashTable.find("condor!!!"));

        assertEquals(13, hashTable.find("condor!!!"));
    }

    @Test
    public void testFindWithFullHashTableAndFewCharString() {
        HashTable hashTable = new HashTable(17, 3);

        for (int i = 0; i < 17; i++) {
            hashTable.put("condor!!!");
        }

        assertEquals(13, hashTable.find("condor!!!"));
        assertEquals(13, hashTable.find("condor!!!"));
        assertEquals(-1, hashTable.find("gryphon"));
        assertEquals(17, hashTable.numElements);

        assertEquals(-1, hashTable.put("gryphon"));
        assertEquals(-1, hashTable.find("gryphon"));

    }

}

