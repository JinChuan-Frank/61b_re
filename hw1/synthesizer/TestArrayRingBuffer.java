package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        for (int i = 1; i <= 10; i++) {
            arb.enqueue(i);
        }
        int expectedOne = (int) arb.dequeue();
        assertEquals(1, expectedOne);
        int expectedTwo = (int) arb.dequeue();
        assertEquals(2, expectedTwo);
        int expectedThree = (int) arb.dequeue();
        assertEquals(3, expectedThree);
        for (int j = 4; j <= 8; j++) {
            arb.dequeue();
        }
        int expectedNine = (int) arb.peek();
        assertEquals(9, expectedNine);
        for (int k = 11; k < 14; k++) {
            arb.enqueue(k);
        }
        arb.dequeue();
        arb.dequeue();
        arb.dequeue();
        int expectedTwelve = (int) arb.dequeue();
        assertEquals(12, expectedTwelve);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {

        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
