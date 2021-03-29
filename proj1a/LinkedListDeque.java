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

    /**Returns true if deque is empty, false otherwise.*/
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

    /**Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        for (IntNode p = sentFront.next; p != sentBack; p = p.next) {
            System.out.print(p.item + " ");
        }
    }


    public int removeFirst() {
        sentFront.next = sentFront.next.next;
        sentFront.next.previous = sentFront;
        return sentFront.next.item;
    }





}
