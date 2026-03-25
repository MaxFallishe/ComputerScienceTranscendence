// Reflection (Task 11 - BloomFilter)
//
// *2 ---Merging several Blum filters.---
//    Merging several Blum Filters is a fairly simple operation, including in the reference solution.
//    Using the "or" operator, you can overlay them on top of each other in just one operation. It is important
//    to take into account that the length of the Blum Filters and the hash functions used should be the same,
//    but it is quite logical that the addition has consequences in the form of adding the probability
//    of false positives.
//
// *3 ---A Blum filter with the ability to remove elements.---
//    In order to adapt the Blum Filter to the possibility of removing added elements in the current solution,
//    we had to change the class structure itself and add counters for each slot. This allows you to first check
//    the presence of an item in the value store, and then reduce the values of the desired cells. Unfortunately,
//    even in such an implementation, it is possible that after deleting an element, the element will somehow be
//    present in memory due to the fact that there are two other elements occupying the corresponding slots.
//
// *4 ---Blum filter with the ability to restore the original set.---
//    The reference solution highlights the fact that any other more efficient solution besides brute force is
//    unlikely to be possible, but it offers potential options such as studying the domain of input data,
//    using some kind of similarity metrics, Bayesian approximation, and iteration attempts to avoid false positives.
//    In the current solution, there was an attempt to restore the source data through brute force, however,
//    even with a small number of embedded values, the method produced too many potentially corresponding
//    values of the set.

