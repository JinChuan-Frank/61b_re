public class ArrayDeque {

    int [] items;
    int size;

    public ArrayDeque() {
        items = new int[8];
        size = 0;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        items [size] = x;
        size += 1;
    }

    /** Inserts X into the back of the list. */
    public void addLast(int x) {
    }

    /** Returns the item from the back of the list. */
    public int getLast() {
        return items [size - 1];
    }
    /** Gets the ith item in the list (0 is the front). */
    public int get(int i) {
        return 0;
    }

    /** Returns the number of items in the list. */
    public int size() {
        return 0;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public int removeLast() {
        return 0;
    }
}
