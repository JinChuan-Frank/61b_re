public class LinkedListDeque {
    public static class IntNode {
        public int item;
        public IntNode previous;
        public IntNode next;
        public IntNode(int i, IntNode m, IntNode n) {
            item = i;
            previous = m;
            next = n;
        }
    }

    private IntNode sentFront;
    private IntNode sentBack;
    private int size;

    public LinkedListDeque(int x) {
        sentFront = new IntNode (10, null, null);
        sentBack = new IntNode (-10, null, null);
        sentFront.next = new IntNode (x, sentFront, sentBack);
        sentBack.previous = sentFront.next;
        size = 1;
    }

    public LinkedListDeque(){
        sentFront = new IntNode (10, null, null);
        sentBack = new IntNode (-10, null, null);
        sentFront.next = sentBack;
        sentBack.previous = sentFront;
        size = 0;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        sentFront.next = new IntNode (x, sentFront, sentFront.next);
        sentFront.next.next.previous = sentFront.next;
        size += 1;
    }


    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        sentBack.previous = new IntNode (x, sentBack.previous, sentBack);
        sentBack.previous.previous.next =  sentBack.previous;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty(){
        if (size = 0) {
            return true;
        } else{
            return false;
        }
    }

    /** Returns the number of items in the list using recursion. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last,
     * separated by a space. */
    public void printDeque() {
        for (IntNode p = sentFront.next; p != sentBack; p = p.next) {
            System.out.print(p.item + " ");
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.*/
    public int removeFirst() {
        if (sentFront.next != sentBack) {
            sentFront.next = sentFront.next.next;
            sentFront.next.previous = sentFront;
            return sentFront.next.item;
        }  else {
            return null;
        }
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null.*/
    public T removeLast(){
        if (sentBack.previous != sentFront) {
            sentBack.previous = sentBack.previous.previous;
            sentBack.previous.next = sentBack;
            return sentBack.previous.item;
        } else {
            return null;
        }
    }

    /** Gets the item at the given index.
     * where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index) {
        int i = 0;
        IntNode p = sentFront;
        while (i < index) {
        i += 1;
        p = p.next;
        }
        return p.next.item;
    }

    /** Same as get, but uses recursion.*/
    public T getRecursive(int index){

    }





}
