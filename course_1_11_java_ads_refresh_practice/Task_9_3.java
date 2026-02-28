// Reflection (Task 7 - OrderedList)
//
// *9 ---Merge two ordered lists into one.---
//    Globally, connecting two or more sorted lists is not a complicated operation at the algorithm level, the lists are
//    already sorted, and it is enough for us to check gradually by iterating from the end (with smaller values) of the list
//    which of the values of the lists is smaller than all the presented ones, after which we remove this value from the original
//    list (in fact, we simply shift the pointer of a specific list by one value. However, with a specific implementation
//    in the code, I had to use a large number of conditional statements inside the main loop to support the logic with the
//    _ascending flag, which determines the order of elements in the array (asc or desc). I'm sure there is a more elegant
//    and shorter solution with java where you can avoid constantly checking the status of the _ascending parameter.
//
// *10 ---Checking the presence of a given ordered sub-list in the current list.---
//    I can't say that I was satisfied with my java code for finding the presence of a sublist in the list, in particular because
//    of the large number of conditional statements inside the while loop. Yes, all conditional statements end the loop
//    one way or another, but still I would like to make it easier, as well as the difficulty of understanding
//    the code (especially when I look at it now) prevents the customization of actions depending on the _ascending flag.
//    The implemented algorithm itself is quite simple and includes several nice optimizations mentioned in the reference solution,
//    namely, stopping the search for a sublist if we have already found the beginning of the occurrence of the sublist,
//    but the list does not match completely to the end - therefore, we can say for sure on this component that if the last
//    matching value from the substring is greater than the new (next) a value from the main list means you can interrupt
//    the search because both lists are deferred.
//
//    However, despite the basic correctness of the algorithm, upon reflection I managed to find an edge case that breaks
//    the current implementation, namely (SUBLIST: [1, 2, 3], MAINLIST: [1, 1, 2, 3]) and for example
//    this (SUBLIST: [1, 1, 1, 2, 3], MAINLIST: [1, 1, 1, 1, 1, 2, 3]). In these cases, we are substituted
//    by the string "if (counter < l1.count())", which does not take into account the case that when the value of the element
//    under the index of the sublist (pointer1) is greater than the value of the element under the pointer of the main list (pointer2),
//    it is possible that the sublist may potentially be contained in the main list, but the list does not just start [1, ...],
//    and with [1, 1, 1, ...]. Accordingly, the if (counter < l1.count() condition is not always met. it captures this moment correctly.
//    Just one match of the elements is enough for the condition to return true and result in false. Therefore, I replaced this
//    condition with a milder one that makes it possible to search for matches further, as long as the value from the previous match
//    is the first (we check through pointer1.prev.value != l1.head.value). For matches of more than one unique element, this will no
//    longer work, because if a sublist suddenly has a value greater than the next one in the main list, then we will not be able to
//    wait for a new correct sequence to appear, because we need at least one number less, and it cannot be further due to the nature
//    of the ordered list. Added two new tests and a modified code.
//
// *11 ---We are looking for the most frequently occurring value in the list.---
//    In the implementation of the algorithm for finding the most common value, I went through a fairly simple,
//    understandable implementation, we take the first value and wind up the counter of identical elements until each
//    next value is equal to the current one, as soon as a new value begins, we fix the new maximum number of elements
//    and the element itself, then continue in a similar sequence. In fact, all the logic goes around
//    4 values (counter, maxCounter, val, maxVal). The solution practically repeats the reference one. Also, in general,
//    you can finish off the algorithm by optimizing the verification of the number of remaining elements, if they are less
//    than the current maximum value, then there is no point in checking further, but not to say that it would give
//    a significant gain.
//
//
// *12 ---The index of the specified item in the list is O(log N).---
//     // In my case, the very first thought was about binary search (which is used in the reference version)
//     when I saw that the complexity of logN was needed. However, I wanted to try to implement this complexity without
//     changing the type of main storage from a linked list to a conditional dynamic array, unfortunately,
//     I could not discover any special interesting options, I liked the idea of conditionally storing links to a
//     limited number of nodes, which helped me jump through them faster in search of the right one, but in attempts
//     to implement them they required complex logic and in general lots of extra space.
//
