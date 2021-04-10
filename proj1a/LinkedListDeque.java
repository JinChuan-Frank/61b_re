public class LinkedListDeque<T> {
    private class StuffNode {
        private T item;
        private StuffNode previous;
        private StuffNode next;
        StuffNode(T i, StuffNode m, StuffNode n) {
            item = i;
            previous = m;
            next = n;
        }
    }

    private StuffNode sentFront;
    private StuffNode sentBack;
    private int size;

    private LinkedListDeque(T x) {
        sentFront = new StuffNode(null, null, null);
        sentBack = new StuffNode(null, null, null);
        sentFront.next = new StuffNode(x, sentFront, sentBack);
        sentBack.previous = sentFront.next;
        size = 1;
    }

    public LinkedListDeque() {
        sentFront = new StuffNode(null, null, null);
        sentBack = new StuffNode(null, null, null);
        sentFront.next = sentBack;
        sentBack.previous = sentFront;
        size = 0;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(T x) {
        sentFront.next = new StuffNode(x, sentFront, sentFront.next);
        sentFront.next.next.previous = sentFront.next;
        size += 1;
    }


    /** Adds an item to the end of the list. */
    public void addLast(T x) {
        sentBack.previous = new StuffNode(x, sentBack.previous, sentBack);
        sentBack.previous.previous.next =  sentBack.previous;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the list */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last,
     * separated by a space. */
    public void printDeque() {
        for (StuffNode p = sentFront.next; p != sentBack; p = p.next) {
            System.out.print(p.item + " ");
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.*/
    public T removeFirst() {
        if (sentFront.next != sentBack) {
            StuffNode a = sentFront.next;
            sentFront.next = sentFront.next.next;
            sentFront.next.previous = sentFront;
            size -= 1;
            return a.item;
        }  else {
            return null;
        }
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null.*/
    public T removeLast() {
        if (sentBack.previous != sentFront) {
            StuffNode a = sentBack.previous;
            sentBack.previous = sentBack.previous.previous;
            sentBack.previous.next = sentBack;
            size -= 1;
            return a.item;
        } else {
            return null;
        }
    }

    /** Gets the item at the given index.
     * where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!*/
    private T get(int index) {
        int i = 0;
        StuffNode p = sentFront;
        while (i < index) {
            i += 1;
            p = p.next;
        }
        return p.next.item;
    }

    /** Same as get, but uses recursion.*/
    public T getRecursive(int index) {
        return getNode(index).item;
    }

    private StuffNode getNode(int index) {
        if (index == 0) {
            return sentFront.next;
        } else {
            return getNode(index - 1).next;
        }
    }





}
