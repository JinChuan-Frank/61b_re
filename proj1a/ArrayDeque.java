public class ArrayDeque<T> {

    private T [] items;
    private int size;
    private int posNextFirst;
    private int posNextLast;
    private double usageFactor;

    public ArrayDeque() {
        items = (T[])  new Object[8];
        size = 0;
        posNextFirst = items.length - 1;
        posNextLast = 0;
        usageFactor = (double) size / items.length;
    }

    /** Resize the array if the Alist is getting too large to hold. */
    private void resize(int capacity) {
        T [] a = (T []) new Object[capacity];
        T [] itemsSorted = sortArray(items);
        System.arraycopy(itemsSorted, 0, a, 0, size);
        items = a;
    }

    private T [] sortArray(T [] x) {
        T [] a = (T []) new Object[x.length];
        for (int i = 0; i < x.length; i += 1) {
            System.arraycopy(x, getPos(i), a, i, 1);
        }
        return a;
    }


    /** Adds an T to the front of the list. */
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
            posNextFirst = items.length - 1;
            posNextLast = size;
        }
        items [posNextFirst] = x;
        posNextFirst = firstForward(posNextFirst);
        size += 1;
    }

    /** Adds an T to the end of the list. */
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
            posNextFirst = items.length - 1;
            posNextLast = size;
        }
        items[posNextLast] = x;
        posNextLast = lastForward(posNextLast);
        size += 1;
    }

    /** Helper methods to relocate the first and last pointers. */
    private int firstForward(int x) {
        if (x == 0) {
            x = items.length - 1;
        } else {
            x = x - 1;
        }
        return x;
    }

    private int lastForward(int x) {
        if (x == items.length - 1) {
            x = 0;
        } else {
            x = x + 1;
        }
        return x;
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
        if (size == 0) {
            return null;
        }
        posNextFirst = firstBackward(posNextFirst);
        T a = items[posNextFirst];
        items[posNextFirst] = null;
        size -= 1;
        if (usageFactor < 0.25) {
            resize(size * 2);
            posNextFirst = items.length - 1;
            posNextLast = size;
        }
        return a;
    }

    /** Removes and returns the T at the back of the deque.
     *  If no such T exists, returns null.*/
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        posNextLast = lastBackward(posNextLast);
        T a = items[posNextLast];
        items[posNextLast] = null;
        size -= 1;
        if (usageFactor < 0.25) {
            resize(size * 2);
            posNextFirst = items.length - 1;
            posNextLast = size;
        }
        return a;
    }

    /** Helper methods to relocate the first and last pointers. */
    private int firstBackward(int x) {
        if (x == items.length - 1) {
            x = 0;
        } else {
            x = x + 1;
        }
        return x;
    }

    private int lastBackward(int x) {
        if (x == 0) {
            x = items.length - 1;
        } else {
            x = x - 1;
        }
        return x;
    }

    /** Gets the ith T in the list (0 is the front). */
    public T get(int i) {
        return items[getPos(i)];
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
