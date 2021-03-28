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

    private IntNode first;
    private IntNode last;
    private int size;

    public SLList(int x) {
        first = new IntNode(x, null, null);
        last =  new IntNode(x, null, null);
        size = 1;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        first = new IntNode(x, null, first);
        size += 1;
    }

    /** Retrieves the front item from the list. */
    public int getFirst() {
        return first.item;
    }

    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        last = new IntNode(x, last, null);
        size += 1;
    }

    /** Returns the number of items in the list using recursion. */
    public int size() {
        return size;
    }

}
