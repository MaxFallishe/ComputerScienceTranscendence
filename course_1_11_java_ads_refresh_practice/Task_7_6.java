// Reflection (Task 5 - Queue)
//
// *3 ---Rotation of the queue in a circle of N elements.---
//    During the execution, the main difficulty was the one that I set myself. I immediately realized that you can just
//    push items from one end of the queue and push them into the other, but I was worried about the suboptimality
//    that occurs with large amounts of "crunching", for example, when there are 10 items but you need to make 1000
//    shifts. Therefore, I decided to make a solution through sabers where it would be possible to immediately
//    calculate the desired shift and not move the elements too many times.
//
// *4 ---Queue using two stacks.---
//    The basic concept in the task of implementing a queue through two stacks was also quite clear, most of all it
//    took the calculation of the asymptotic complexity itself, which really managed to get closer to o(1).
//
// *5 ---Reverse order of all items in the queue.---
//    In the task of deploying the queue, I managed to fulfill my idea that when implementing a queue through two stacks,
//    it is possible to simply swap these stacks correctly and then the queue will be reversed.
//
// *6 ---A cyclic buffer queue based on a fixed-size static array.---
//    On the one hand, it was not so difficult to implement a cyclic queue inside a fairly rigid data structure, but still,
//    the mechanism of pointer control itself, especially in edge cases, will make you think carefully about implementations.
//    One of the main points that I would definitely remember is that you should select one pointer (front or back) and
//    already adjust the index to it, which will allow you not to overwrite the values.
