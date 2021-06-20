package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        rb = (T[]) new Object[this.capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (fillCount == capacity) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        rb[last] = x;
        fillCount += 1;
        last += 1;
        checkIndex();
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        T temp = rb[first];
        first += 1;
        fillCount -= 1;
        checkIndex();
        return temp;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        return rb[first];
    }

    private void checkIndex() {
        if (last == capacity) {
            last = 0;
        }
        if (first == capacity) {
            first = 0;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new RingBufferIterator();

    }

    private class RingBufferIterator implements Iterator {
        private int position;
        RingBufferIterator() {
            position = 0;
        }
        @Override
        public boolean hasNext() {
            return position < capacity;
        }
        @Override
        public T next() {
            T returnVal = rb[position];
            position += 1;
            return returnVal;
        }
    }
}
