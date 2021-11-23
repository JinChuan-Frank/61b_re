package hw2;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPercolation {
    Percolation percolation = new Percolation(10);

    @Test
    public void testXYTo1D() {
        int expected = 2;
        int actual = percolation.xyTo1D(1, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructor() {

        assertTrue(percolation.weightedQuickUnionUF.connected(4, 26));
        assertTrue(percolation.weightedQuickUnionUF.connected(24, 25));
        assertFalse(percolation.isOpen(0, 0));
        assertFalse(percolation.isOpen(1, 1));
    }

    @Test
    public void testOpenAndIsOpen() {
        percolation.open(0, 0);
        assertTrue(percolation.isOpen(0, 0));
        percolation.open(1, 0);
        assertTrue(percolation.isOpen(1, 0));
        percolation.open(1, 1);
        assertFalse(percolation.isOpen(0, 1));
    }

    @Test
    public void testIsFull() {
        percolation.open(0, 0);
        percolation.open(1, 1);
        percolation.open(0, 1);
        //System.out.println(percolation.weightedQuickUnionUF.connected(1, 3));
        assertTrue(percolation.isOpen(0, 0));
        assertTrue(percolation.isOpen(1, 1));
        assertTrue(percolation.isOpen(0, 1));
        assertTrue(percolation.isFull(1, 1));
        assertTrue(percolation.weightedQuickUnionUF.connected(0, 1));

    }
}
