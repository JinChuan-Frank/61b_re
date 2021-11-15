package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Percolation {

    private int N;
    private Site[] grid;
    private int virtualTopSite;
    private int virtualBottomSite;
    WeightedQuickUnionUF weightedQuickUnionUF;

    private class Site {
        private boolean isOpen;
        public Site() {
            isOpen = false;
        }
    }

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int sideLength) {
        if (sideLength < 0) {
            throw new IllegalArgumentException();
        }
        N = sideLength;
        grid = new Site[N * N];
        for (int i = 0; i <= N * N - 1; i++) {
            grid[i] = new Site();
        }
        weightedQuickUnionUF = new WeightedQuickUnionUF(N * N - 1 + 2);
        virtualBottomSite = N;
        virtualTopSite = N + 1;
        connectToTopAndBottom();
    }

    private void connectToTopAndBottom() {
        for (int i = 0; i <= xyTo1D(0, N - 1); i++) {
            weightedQuickUnionUF.union(i, virtualTopSite);
        }
        for (int j = xyTo1D(N - 1, 0) ; j <= xyTo1D(N - 1, N - 1); j++) {
            weightedQuickUnionUF.union(j, virtualBottomSite);
        }
    }

    public int xyTo1D(int row, int column) {
        int oneD = row * N + column;
        return oneD;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > N || col < 0 || col > N) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int pos = xyTo1D(row, col);
        grid[pos].isOpen = true;
        connectOpenSites(row, col);
    }

    private void connectOpenSites(int row, int col) {
        int pos = xyTo1D(row, col);
        List neighbors = findNeighbors(row, col);
        for (Object i : neighbors) {
            if (grid[(int) i].isOpen) {
                weightedQuickUnionUF.union(pos, (int) i);
            }
        }
    }

    private List findNeighbors(int row, int col) {
        List neighbors= new ArrayList();
        int[] positions = new int[4];

        int up = xyTo1D(row - 1, col);
        int down = xyTo1D(row + 1, col);
        int left = xyTo1D(row, col - 1);
        int right = xyTo1D(row, col + 1);
        positions[0] = up;
        positions[1] = down;
        positions[2] = left;
        positions[3] = right;
        for (int i : positions) {
            if (i >= 0 && i < N * N - 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int pos = xyTo1D(row, col);
        return grid[pos].isOpen;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int pos = xyTo1D(row, col);
        return weightedQuickUnionUF.connected(pos, virtualTopSite);
    }

    // number of open sites
    public int numberOfOpenSites() {
        int numberOfOpenSites = 0;
        for (Site site : grid) {
            if (site.isOpen) {
                numberOfOpenSites += 1;
            }
        }
        return numberOfOpenSites;
    }
    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(virtualBottomSite, virtualTopSite);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }

}
