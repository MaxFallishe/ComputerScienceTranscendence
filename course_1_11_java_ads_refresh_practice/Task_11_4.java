// Reflection (Task 9 - Native Dictionary)
//
// *5 ---Native dictionary with ordered list by key.---
// The main difference from the reference implementation is that in my value store, the values are stored in cells
// with the same indexes as in the key store, which makes it necessary to move the values when re-sorting in both stores.
// The time complexity in get() when using an ordered list is O(logN), compared to the implementation using a hash
// table where the speed is o(1), the ordered list will be more stable, but with a good hash table mechanism,
// o(1) is always better.

