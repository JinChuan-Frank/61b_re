public class ArrayDeque <Item> {

    private Item [] items;
    private int size;
    private int posNextFirst;
    private int posNextLast;

    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
        posNextFirst = items.length - 1;
        posNextLast = 0;
    }

    /** Resize the array if the Alist is getting too large to hold. */
    private void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(Item x) {
        if (size == items.length) {
            resize(size * 2);
            posNextFirst = items.length - 1;
        }
        items [posNextFirst] = x;
        posNextFirst = posNextFirst -1;
        size += 1;
    }

    /** Adds an item to the end of the list. */
    public void addLast(Item x) {
        if (size == items.length) {
            resize(size * 2);
            posNextLast = items.length - 1;
        }
        items[posNextLast] = x;
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
        for (int i = posNextFirst + 1; i <= items.length - 1; i += 1) {
            System.out.print(items[i] + " ");
        }
        for (int i = 0; i < posNextLast - 1; i += 1) {
            System.out.print(items[i] + " ");
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.*/
    public Item removeFirst() {
        if (posNextFirst == items.length - 1) {
            return null;
        } else {
            Item a = items[posNextFirst + 1];
            posNextFirst = posNextFirst + 1;
            return a;
        }
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null.*/
    public Item removeLast() {
        if (posNextLast == 0) {
            return null;
        } else {
            Item a = items[posNextLast - 1];
            posNextFirst = posNextFirst - 1;
            return a;
        }
    }


    /** Gets the ith item in the list (0 is the front). */
    public Item get(int i) {
        getPos(i);
        return items[i];

    }

    /** Convert position in list into position in array. */
    private int getPos(int i) {
        int startPos = posNextFirst + 1;
        int arrPos = startPos + i;
        if (arrPos > items.length - 1) {
            arrPos = arrPos - items.length;
        }
        return arrPos;
    }

}
