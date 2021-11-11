package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    private site[] grid;
    private site virtualTopSite;
    private site virtualBottomSite;
    WeightedQuickUnionUF weightedQuickUnionUF;

    private class site {
        private boolean isOpen;
        public site() {
            isOpen = false;
        }
    }

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        size = N * N - 1;
        grid = new site[size];
        for (int i = 0; i < size; i++) {
            grid[i] = new site();
        }
        weightedQuickUnionUF = new WeightedQuickUnionUF(size);
        virtualTopSite = new site();
        virtualBottomSite = new site();
    }

    public int xyTo1D(int row, int column) {
        int oneD = row * size + column;
        return oneD;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        int pos = xyTo1D(row, col);
        grid[pos].isOpen = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int pos = xyTo1D(row, col);
        return grid[pos].isOpen;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int pos = xyTo1D(row, col);
        return weightedQuickUnionUF.connected(pos, );
    }

    // number of open sites
    public int numberOfOpenSites() {
        return 0;
    }
    // does the system percolate?
    public boolean percolates() {
        return false;
    }
    // use for unit testing (not required)
    public static void main(String[] args) {

    }

}
