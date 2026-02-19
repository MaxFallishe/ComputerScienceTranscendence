import java.util.*;


public class  DequeSecondAddition<T extends Comparable<T>>
{
    LinkedList<T> storage;
    LinkedList<T> frontStorage;
    LinkedList<T> tailStorage;


    public DequeSecondAddition() {
        storage = new LinkedList<>();
        frontStorage = new LinkedList<>();
        tailStorage = new LinkedList<>();
    }

    public void addFront(T item) {
        storage.addFirst(item);
        T frontMost = frontStorage.peekLast();
        if (frontMost == null || item.compareTo(frontMost) <= 0 )
            frontStorage.addLast(item);
    }

    public void addTail(T item) {
        storage.addLast(item);
        T tailMost = tailStorage.peekLast();
        if (tailMost == null || item.compareTo(tailMost) <= 0)
            tailStorage.addLast(item);
    }

    public T removeFront() {
        T removedItem = storage.pollFirst();
        T frontMost = frontStorage.peekLast();
        if (frontMost != null && removedItem == frontMost) {
            frontStorage.removeLast();
        } else {
            reassembleFrontStorageAndTailStorage();
        }
        return removedItem;
    }

    public T removeTail() {
        T removedItem = storage.pollLast();
        T tailMost = tailStorage.peekLast();
        if (tailMost != null && removedItem == tailMost) {
            tailStorage.removeLast();
        } else {
            reassembleFrontStorageAndTailStorage();
        }
        return removedItem;
    }

    public Integer size() {
        return storage.size();
    }

    // Task number: 6.5
    // Short description: Write a method that returns the minimum element of the deck in O(1).
    // Time complexity: O(1)
    // Space Complexity: O(1)
    // ---Refleсtion.---
    // When planning the implementation of the minimum value search method in deque, it was clear that it would not
    // be possible to simply reuse the method of searching for the minimum value in the stack. Since in deque values
    // are added and deleted from both ends, which means that some kind of more advanced system is needed.
    // In the process of thinking about potential solutions, unfortunately, it was not possible to create a universally
    // effective way to track the minimum, so I decided to implement a search for the minimum through two stacks,
    // for each half of the deque. In this case, it was necessary to additionally provide a rebalancing mechanism
    // for these two stacks in the case when the user resets to start deleting values mainly from only one side of the deque.
    // The proposed algorithm in the task, in my opinion, does not allow us to fully correctly track the minimum value
    // in the deque data structure, below I will present a cornercase that shows its imperfection.
    //
    // The proposed algorithm for tracking the minimum value in deque:
    // """
    // To track the minimum in a deck, you can use a second deck that stores the elements in ascending order.
    // When adding an element x to the main deck at the desired end:
    // -- remove elements from the end of the additional deck until its last element is greater than or equal to x.
    // -- add an x to the end of the additional deck.
    // When removing an element from the main deck from the desired end:
    // -- if the element to be deleted is equal to the first element of the additional deck, then we delete it from the additional deck.
    // The minimum is always at the beginning of the additional deck.
    // """
    //
    // CORNER CASE WITH FAIL LOGIC EXAMPLE:
    // STEP 1 - ADD_FRONT(4)
    // MAIN DEQUE: 4
    // HELP DEQUE: 4
    //
    // STEP 2 - ADD_FRONT(3)
    // MAIN DEQUE: 3 4
    // HELP DEQUE: 3
    //
    // STEP 3 - ADD_TAIL(4)
    // MAIN DEQUE: 3 4 4
    // HELP DEQUE: 3 4
    //
    // STEP 4 - ADD_FRONT(1)
    // MAIN DEQUE: 1 3 4 4
    // HELP DEQUE: 1
    //
    // STEP 5 - ADD_TAIL(7)
    // MAIN DEQUE: 1 3 4 4 7
    // HELP DEQUE: 1 7
    //
    // STEP 6 - POP_TAIL()
    // MAIN DEQUE: 1 3 4 4
    // HELP DEQUE: 1 7
    //
    // STEP 7 - POP_FRONT()
    // MAIN DEQUE: 3 4 4
    // HELP DEQUE: 7 <- Wrong: min value of MAIN DEQUE is 3 now, not 7
    public T getMinElement() {
        T minFrontMost = frontStorage.peekLast();
        T minTailMost = tailStorage.peekLast();

        if (minFrontMost == null && minTailMost== null)
            return null;

        if (minFrontMost == null)
            return minTailMost;

        if (minTailMost == null)
            return minFrontMost;

        return minFrontMost.compareTo(minTailMost) < 0 ? minFrontMost : minTailMost;
    }

    public void reassembleFrontStorageAndTailStorage() {
        frontStorage.clear();
        tailStorage.clear();

        // mean that tailStorage size will be == or +1 than frontStorage size
        int middle = this.size() / 2;

        // front logic
        ListIterator<T> frontIter = storage.listIterator(middle);
        T minFrontElement = null;
        while (frontIter.hasPrevious()) {
            T x = frontIter.previous();
            if (minFrontElement == null || x.compareTo(minFrontElement)  <= 0) {
                minFrontElement = x;
                frontStorage.addLast(x);
            }
        }

        // tail logic
        ListIterator<T> tailIter = storage.listIterator(middle);
        T minTailElement = null;
        while (tailIter.hasNext()) {
            T x = tailIter.next();
            if (minTailElement == null || x.compareTo(minTailElement) <= 0) {
                minTailElement = x;
                tailStorage.addLast(x);
            }
        }

    }
}

