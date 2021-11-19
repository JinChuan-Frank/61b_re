package hw2;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPercolation {
    Percolation percolation = new Percolation(2);

    @Test
    public void testXYTo1D() {

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
        System.out.println("grid[0] is open: " + "" + percolation.grid[0].isOpen);
        System.out.println("grid[1] is open: " + "" + percolation.grid[1].isOpen);
        System.out.println("grid[3] is open: " + "" + percolation.grid[3].isOpen);
        System.out.println("neighbors:" + percolation.findNeighbors(1, 1));


        //assertTrue(percolation.weightedQuickUnionUF.connected(0,1));
        //assertTrue(percolation.isFull(1, 1));
    }
}
