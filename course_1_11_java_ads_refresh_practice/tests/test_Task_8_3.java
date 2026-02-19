import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class HashTableThirdAdditionTest {

    @Test
    public void testCollisionAttackSimulationSmallSetup() {
        HashTableThirdAddition hashTable = new HashTableThirdAddition(17, 3);

        hashTable.put("acd");
        hashTable.put("ade");
        hashTable.put("aef");
        hashTable.put("afg");
        hashTable.put("agh");
        hashTable.put("ahi");
        hashTable.put("ano");

        assertEquals(12, hashTable.numElements);
        assertEquals(66, hashTable.collisionCounter);
    }

    @Test
    public void testCollisionAttackSimulationBigSetup() {
        HashTableThirdAddition hashTable = new HashTableThirdAddition(17, 3);

        hashTable.put("acd");
        hashTable.put("ade");
        hashTable.put("aef");
        hashTable.put("afg");
        hashTable.put("agh");
        hashTable.put("ahi");
        hashTable.put("aij");
        hashTable.put("ajk");
        hashTable.put("akl");
        hashTable.put("alm");
        hashTable.put("amn");
        hashTable.put("ano");

        assertEquals(12, hashTable.numElements);
        assertEquals(66, hashTable.collisionCounter);
    }

// Short result:
// The "salt" method does not work and does not reduce the number of collisions if the original hash function does not
// imply dependence on the order of characters, as it turned out in these solutions.
// Unfortunately, the data set I selected for the attack could not use a static salt to resolve the problem with collisions.
// With or without salt, the number of collisions is the same in cases above.
}

