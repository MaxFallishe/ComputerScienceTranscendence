// Reflection (Task 8 - Hashing)
//
// *3 ---Dynamic hash table.---
//    Implementing a dynamic hash table turned out to be a fairly simple task from the point of view of the required
//    algorithm. It is necessary to set the required limit at which the hash table will expand (in my solution 100%,
//    in the reference 80% is proposed) and then recreate the hash table in a larger array. The most important thing
//    is that for each element, you need to recalculate its order in a new array, because if we try to move elements
//    from their current positions, then there will be a situation with duplicate values in completely different
//    locations of the array + problems with deleting existing values since the position of the hash function will
//    not match.
//
// *5 ---DDOS hash tables and salt.---
//   In my case the "salt" method does not work and does not reduce the number of collisions if the original hash function does not
//   imply dependence on the order of characters, as it turned out in these solutions.
//   Unfortunately, the data set I selected for the attack could not use a static salt to resolve the problem with collisions.
//   According to the reference version, making a static salt was not the best solution. With a static salt,
//   the same values will be hashed to the same result. It is better to add a short dynamic salt to the values,
//   add small random values to the hash, etc. And store in the table, respectively, an entity with two fields: value and salt.

