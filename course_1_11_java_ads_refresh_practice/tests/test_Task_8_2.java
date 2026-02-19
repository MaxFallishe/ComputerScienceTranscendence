import org.junit.Test;

import static org.junit.Assert.*;

public class HashTableSecondAdditionTest {

    @Test
    public void testHashFunWithOneCharStringAsLetter() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        assertEquals(12, hashTable.hashFun("a"));
        assertEquals(12, hashTable.hashFun("a"));
        assertTrue(hashTable.hashFun("a") < 17);
    }

    @Test
    public void testHashFunWithOneCharThatIndxZero() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        assertEquals(0, hashTable.hashFun("f"));
        assertEquals(0, hashTable.hashFun("f"));
        assertTrue(hashTable.hashFun("f") < 17);
    }

    @Test
    public void testHashFunWithOneCharStringAsSign() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        assertEquals(13, hashTable.hashFun("@"));
        assertEquals(13, hashTable.hashFun("@"));
        assertTrue(hashTable.hashFun("@") < 17);
    }

    @Test
    public void testHashFunWithFewCharsStringAsLetters() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        assertEquals(3, hashTable.hashFun("apple"));
        assertEquals(3, hashTable.hashFun("apple"));
        assertTrue(hashTable.hashFun("apple") < 17);
    }

    @Test
    public void testHashFunWithFewCharsStringAsLettersAndSigns() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        assertEquals(15, hashTable.hashFun("@apple!"));
        assertEquals(15, hashTable.hashFun("@apple!"));
        assertTrue(hashTable.hashFun("@apple!") < 17);
    }

    @Test
    public void testSeekSlotWithEmptyHashTableAndOneCharString() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        assertEquals(12, hashTable.seekSlot("a"));
        assertEquals(12, hashTable.seekSlot("a"));
    }

    @Test
    public void testSeekSlotWithNonEmptyHashTableAndOneCharString() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        assertEquals(12, hashTable.seekSlot("a"));
        assertEquals(12, hashTable.put("a"));

        assertEquals(15, hashTable.seekSlot("a"));
        assertEquals(15, hashTable.put("a"));

        assertEquals(1, hashTable.seekSlot("a"));
        assertEquals(1, hashTable.put("a"));
    }

    @Test
    public void testSeekSlotWithNonEmptyHashTableAndFewCharString() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        assertEquals(13, hashTable.seekSlot("condor!!!"));
        assertEquals(13, hashTable.put("condor!!!"));

        assertEquals(16, hashTable.seekSlot("condor!!!"));
        assertEquals(16, hashTable.put("condor!!!"));

        assertEquals(2, hashTable.seekSlot("condor!!!"));
        assertEquals(2, hashTable.put("condor!!!"));
    }

    @Test
    public void testSeekSlotWithFullHashTableAndFewCharString() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        for (int i = 0; i < 17; i++) {
            hashTable.put("condor!!!");
        }

        assertEquals(-1, hashTable.seekSlot("condor!!!"));
        assertEquals(-1, hashTable.seekSlot("a"));
        assertEquals(17, hashTable.numElements);
    }

    @Test
    public void testPutWithNonEmptyHashTableAndFewCharString() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        assertEquals(13, hashTable.put("condor!!!"));

        assertEquals(16, hashTable.put("condor!!!"));

        assertEquals(2, hashTable.put("condor!!!"));
    }

    @Test
    public void testPutWithFullHashTableAndFewCharString() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

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
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        assertEquals(-1, hashTable.find("condor!!!"));

        assertEquals(-1, hashTable.find("condor!!!"));

        assertEquals(-1, hashTable.find("condor!!!"));
    }

    @Test
    public void testFindWithNonEmptyHashTableAndFewCharString() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        assertEquals(-1, hashTable.find("condor!!!"));

        assertEquals(13, hashTable.put("condor!!!"));
        assertEquals(13, hashTable.find("condor!!!"));

        assertEquals(13, hashTable.find("condor!!!"));
    }

    @Test
    public void testFindWithFullHashTableAndFewCharString() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

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
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        for (int i = 0; i < 17; i++) {
            hashTable.put("condor!!!");
        }
        assertEquals(17, hashTable.numElements);
        assertEquals(17, hashTable.size);
        assertEquals(17, hashTable.slots.length);
    }

    @Test
    public void testExtendSlotsWithExtraFullHashTableAndFewCharString() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        for (int i = 0; i < 18; i++) {
            hashTable.put("condor!!!");
        }
        assertEquals(18, hashTable.numElements);
        assertEquals(37, hashTable.size);
        assertEquals(37, hashTable.slots.length);
    }

    @Test
    public void testExtendSlotsWithExtendedHashTableAndFewCharString() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

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

    @Test
    public void testCollisionsCountWithDifferentNumOfHashFuncsAndShortStrings() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        hashTable.put("a9F");
        hashTable.put("Qx7");
        hashTable.put("mK2");
        hashTable.put("7Zp");
        hashTable.put("B4e");
        hashTable.put("rT8");
        hashTable.put("9cW");
        hashTable.put("L2A");
        hashTable.put("x6M");
        hashTable.put("Pq3");
        hashTable.put("8Hn");
        hashTable.put("V5R");
        hashTable.put("s1Y");
        hashTable.put("D7J");
        hashTable.put("wE4");
        hashTable.put("C9u");
        hashTable.put("F2Z");


        assertEquals(17, hashTable.numElements);
         assertEquals(30, hashTable.collisionCounter); // with one custom hash function
//        assertEquals(38, hashTable.collisionCounter); // with three hash custom function

    }

    @Test
    public void testCollisionsCountWithDifferentNumOfHashFuncsAndLongStrings() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(17, 3);

        hashTable.put("FaFFaa");
        hashTable.put("7Q77QQ");
        hashTable.put("2m22mm");
        hashTable.put("p7pp77");
        hashTable.put("eBeeBB");
        hashTable.put("8r88rr");
        hashTable.put("W9WW99");
        hashTable.put("ALAALL");
        hashTable.put("MxMMxx");
        hashTable.put("3P33PP");
        hashTable.put("n8nn88");
        hashTable.put("RVRRVV");
        hashTable.put("YsYYss");
        hashTable.put("JDJJDD");
        hashTable.put("4w44ww");
        hashTable.put("uCuuCC");
        hashTable.put("ZFZZFF");


        assertEquals(17, hashTable.numElements);
        assertEquals(31, hashTable.collisionCounter); // with one custom hash function
//        assertEquals(40, hashTable.collisionCounter); // with three hash custom function
    }

    @Test
    public void testCollisionsCountWithDifferentNumOfHashFuncsAndShortStringsInNotFullHashTable() {
        HashTableSecondAddition hashTable = new HashTableSecondAddition(37, 3);

        hashTable.put("a9F");
        hashTable.put("Qx7");
        hashTable.put("mK2");
        hashTable.put("7Zp");
        hashTable.put("B4e");
        hashTable.put("rT8");
        hashTable.put("9cW");
        hashTable.put("L2A");
        hashTable.put("x6M");
        hashTable.put("Pq3");
        hashTable.put("8Hn");
        hashTable.put("V5R");
        hashTable.put("s1Y");
        hashTable.put("D7J");
        hashTable.put("wE4");
        hashTable.put("C9u");
        hashTable.put("F2Z");
        hashTable.put("K7m");
        hashTable.put("4Qx");
        hashTable.put("Z2E");
        hashTable.put("n9P");
        hashTable.put("H5c");
        hashTable.put("R8A");
        hashTable.put("w3S");
        hashTable.put("J6F");


        assertEquals(25, hashTable.numElements);
        assertEquals(15, hashTable.collisionCounter); // with one custom hash function
//        assertEquals(26, hashTable.collisionCounter); // with three hash custom function
    }

// Short result:
// In all cases when I tried to use three custom hash functions instead
// of one original one, I got more collisions than with one hash function.
// On average, the difference in the number of collisions was 28%.
}

