// Reflection (Task 3 - Dynamic Array)
//
// *5 ---Implement Dynamic Array based on "Bank method".---
//    In my implementation of the dynamic array based on the banking method, the main goal for me was to test in practice
//    how the depreciation assessment method works and that it really guarantees that the bank will not go into an infinite
//    disadvantage and heavy operations are really compensated by lighter and more frequent ones. Unlike the proposed
//    solution, where it is proposed to start increasing the buffer at the time of reaching the required number of coins
//    and actually spend them, my banking method rather accompanies the program and rather performs the role of an
//    invariant of correctness that heavy operations are compensated by light ones, at least that was the idea. For me,
//    the mechanism itself has become much more understandable, and how the banking method should be applied so that you
//    can make sure that complexity is also predictably amortized over a long horizon.
//
// *6 ---Implement Multidimensional Dynamic Array.---
//    For me, the practice with a multidimensional dynamic array turned out to be more like a study of how this can be done,
//    unfortunately, I might like to focus a little more at the moment on the implementation itself with already a lot of
//    limitations, perhaps even a boiler plate of the code. The idea of deploying multiple nested dimensions in one straight
//    line looks very strong, it's not in my solution, but I can well admit that it is stronger and more effective than
//    just nested arrays (they have much more problems with expansion), and there is a lot of code, looking at the code
//    again, I can say what exactly can be done better, shorter and flatter.

