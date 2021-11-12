package hw2;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestPercolation {
    @Test
    public void testXYTo1D() {
        Percolation percolation = new Percolation(5);
        int x1 = 3;
        int y1 = 4;
        int expected1 = 19;
        int actual1 = percolation.xyTo1D(x1, y1);
        assertEquals(expected1, actual1);
        int x2 = 2;
        int y2 = 4;
        int expected2 = 14;
        int actual2 = percolation.xyTo1D(x2, y2);
        assertEquals(expected2, actual2);
        int x3 = 2;
        int y3 = 2;
        int expected3 = 12;
        int actual3 = percolation.xyTo1D(x3, y3);
        assertEquals(expected3, actual3);

    }
}
