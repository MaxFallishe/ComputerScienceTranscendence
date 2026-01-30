// Reflection (Task 4 - Stack)
//
// *4 ---Implement function that checking is brackets ")" and "(" in string balanced.---
//    In my implementation of checking the correctness of an expression from parentheses, I quickly managed to reproduce
//    all the important aspects of the desired algorithm. We managed to immediately write a universal solution that would fit logic
//    with different types of brackets well. This task, to check the correctness of an expression from parentheses,
//    is for me one of the most memorable tasks that are solved very clearly and efficiently using a stack.
//
// *5 ---Implement function that checking is brackets ")", "(", "]", "[", "{" and "}" in string balanced.---
//    In the case where it was necessary to process a large number of types of brackets than one, I simply added a switch-case construction
//    that, depending on the type of bracket, checked that the encountered closed bracket was not only in the right place,
//    but also of the correct type. It was nice to reuse almost completely the solution from the previous problem.
//
// *6 ---Implement function that return a min value from whole stack.---
//    In the task of returning the minimum value from the stack, it was nice to "discover"/"remember" the method using the second
//    stack in which you can effectively store the minimum values as the stack increases. In this task, it was definitely important to
//    remember that the minimum values can be repeated, and you need to remember to save duplicates, despite the fact that the side stack
//    allows you to save only the minimum values, without reference to each element in the stack
//    (conditionally we can have 10 elements in the stack, but only 3 elements in the stack the auxiliary one that stores the minimum values).
//
// *7 ---Implement function that return a medium value from whole stack.---
//    While solving the problem with calculating the average value, I was close to making a mistake in which I would count the average
//    through the sum of the last element (already the average of the previous values in the stack) and the new number divided by two,
//    however, this concept seemed intuitively wrong to me. As a result, the algorithm takes into account the last number from
//    the auxiliary stack (it is the average for the previous values in the stack), but when calculating the new average,
//    we simply multiply the average by the number of elements-1, add the new value and divide the number of elements.
//    It is a good task to consolidate the use of the stack itself as an auxiliary storage.
//
// * 8 ---Implement function that implement calculation with postfix type of expressions.---
//   Despite the apparent complexity of implementing postfix notation, this task is quite simple and is implemented using two stacks.
//   The main key to a quick solution is to understand that postfix notation perfectly correlates with the stack model
//   and all you need to do is take the values of numbers and signs in turn, where to perform the operation after finding the sign.
//   It is also worth paying special attention to checking the order of the elements in the stack during division and subtraction.
//   I assume that the solution was given to me quite easily due to the high level of understanding of the very concept of postfix notation.

