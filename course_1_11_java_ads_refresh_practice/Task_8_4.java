// Reflection (Task 6 - Deque)
//
// *4 ---Checking the string for a palindrome.---
//    When planning the implementation of the minimum value search method in deque, it was clear that it would not
//    be possible to simply reuse the method of searching for the minimum value in the stack. Since in deque values
//    are added and deleted from both ends, which means that some kind of more advanced system is needed.
//    In the process of thinking about potential solutions, unfortunately, it was not possible to create a universally
//    effective way to track the minimum, so I decided to implement a search for the minimum through two stacks,
//    for each half of the deque. In this case, it was necessary to additionally provide a rebalancing mechanism
//    for these two stacks in the case when the user resets to start deleting values mainly from only one side of the deque.
//    The proposed algorithm in the task, in my opinion, does not allow us to fully correctly track the minimum value
//    in the deque data structure, below I will present a cornercase that shows its imperfection.
//
//    The proposed algorithm for tracking the minimum value in deque:
//    """
//    To track the minimum in a deck, you can use a second deck that stores the elements in ascending order.
//    When adding an element x to the main deck at the desired end:
//    -- remove elements from the end of the additional deck until its last element is greater than or equal to x.
//    -- add an x to the end of the additional deck.
//    When removing an element from the main deck from the desired end:
//    -- if the element to be deleted is equal to the first element of the additional deck, then we delete it from the additional deck.
//    The minimum is always at the beginning of the additional deck.
//    """
//
//    CORNER CASE WITH FAIL LOGIC EXAMPLE:
//    STEP 1 - ADD_FRONT(4)
//    MAIN DEQUE: 4
//    HELP DEQUE: 4
//
//    STEP 2 - ADD_FRONT(3)
//    MAIN DEQUE: 3 4
//    HELP DEQUE: 3
//
//    STEP 3 - ADD_TAIL(4)
//    MAIN DEQUE: 3 4 4
//    HELP DEQUE: 3 4
//
//    STEP 4 - ADD_FRONT(1)
//    MAIN DEQUE: 1 3 4 4
//    HELP DEQUE: 1
//
//    STEP 5 - ADD_TAIL(7)
//    MAIN DEQUE: 1 3 4 4 7
//    HELP DEQUE: 1 7
//
//    STEP 6 - POP_TAIL()
//    MAIN DEQUE: 1 3 4 4
//    HELP DEQUE: 1 7
//
//    STEP 7 - POP_FRONT()
//    MAIN DEQUE: 3 4 4
//    HELP DEQUE: 7 <- Wrong: min value of MAIN DEQUE is 3 now, not 7
//
// *5 ---The minimum element of the deque is O(1).---
//    In implementing the algorithm for checking the palindrome, I was able to immediately come up with the method
//    conceived in the task. First, convert the string to a deque, and then, using the built-in capabilities
//    of the data structure, break off one element from each side of the deque, checking them for compliance.
//    If there is no match, we immediately return False. But it is important to remember that you need to take into
//    account the case with an odd number of characters in a similar string, so with the condition "deque.size() > 1"
//    in the loop, we guarantee that we will correctly return False in the step with one last character in the string.
//
// *6 ---A two-way queue based on a dynamic array.---
//
//    In implementing deque through a dynamic array, I used the option of writing values to arrays by index.
//    This makes it possible to write values to a dynamic array in a format that can easily be identified with the queue format.
//    By storing two pointers to the beginning and end of the queue (at the beginning, the value of both pointers is 0)
//    and correctly recalculating the indexes (avoiding negative values), it is possible to write values inside
//    a dynamic value like a carousel. When the indexes intersect, it is enough to automatically expand the internal
//    buffer (the dynamic array itself) and the queue will continue working. I tried to work exclusively with a dynamic
//    array as much as possible in the implementation, and implement the interface under deque.

