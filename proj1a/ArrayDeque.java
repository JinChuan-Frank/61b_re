public class ArrayDeque {

    private int [] items;
    private size;

    public ArrayDeque() {
        items = new int[8];
        size = 0;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        items[size] = x;
        size += 1;
    }

    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        items[0] = x;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty(){
        if (size == 0) {
            return true;
        } else{
            return false;
        }
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last,
     * separated by a space. */
    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            System.out.print(item[i] + " ");
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.*/
    public int removeFirst() {

    }

    /** Returns the item from the back of the list. */
    public int getLast() {
        int item = items[size - 1];
        return item;
    }

    /** Gets the ith item in the list (0 is the front). */
    public int get(int i) {
        return items[i];
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public int removeLast() {
        return 0;
    }
}
