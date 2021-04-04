public class ArrayDeque<T> {

    private T [] items;
    private int size;
    private int posNextFirst;
    private int posNextLast;

    public ArrayDeque() {
        items = (T[])  new Object[8];
        size = 0;
        posNextFirst = items.length - 1;
        posNextLast = 0;
    }

    /** Resize the array if the Alist is getting too large to hold. */
    private void resize(int capacity) {
        T [] a = (T []) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    private int setPosNextFirst() {
        if (posNextFirst >= posNextLast) {
           posNextFirst = posNextFirst - 1;
        } else {
           posNextFirst = posNextFirst + 1;
        }


        }
    }

    /** Adds an T to the front of the list. */
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
            posNextFirst = items.length - 1;
        }
        items [posNextFirst] = x;
        posNextFirst = posNextFirst - 1;
        size += 1;
    }

    /** Adds an T to the end of the list. */
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
            posNextLast = items.length - 1;
        }
        items[posNextLast] = x;
        posNextLast = posNextLast + 1;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last,
     * separated by a space. */
    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            System.out.print(items[getPos(i)] + " ");
        }
    }

    /** Removes and returns the T at the front of the deque.
     * If no such T exists, returns null.*/
    public T removeFirst() {
        if (posNextFirst == items.length - 1) {
            return null;
        } else {
            T a = items[posNextFirst + 1];
            items[posNextFirst + 1] = null;
            posNextFirst = posNextFirst + 1;
            if (posNextFirst == items.length - 1 &&
            size -= 1;
            return a;

        }
    }

    /** Removes and returns the T at the back of the deque.
     *  If no such T exists, returns null.*/
    public T removeLast() {
        if (posNextLast == 0) {
            return null;
        } else {
            T a = items[posNextLast - 1];
            posNextFirst = posNextFirst - 1;
            return a;
        }
    }


    /** Gets the ith T in the list (0 is the front). */
    public T get(int i) {
        getPos(i);
        return items[i];
    }

    /** Convert position in list into position in array. */
    public int getPos(int i) {
        int startPos = posNextFirst + 1;
        int arrPos = startPos + i;
        if (arrPos > items.length - 1) {
            arrPos = arrPos - items.length;
        }
        return arrPos;
    }

}
