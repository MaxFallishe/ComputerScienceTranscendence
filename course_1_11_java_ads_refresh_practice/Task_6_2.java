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

