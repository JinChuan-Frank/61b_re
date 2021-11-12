package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private site[] grid;
    private int virtualTopSite;
    private int virtualBottomSite;
    WeightedQuickUnionUF weightedQuickUnionUF;

    private class site {
        private boolean isOpen;
        public site() {
            isOpen = false;
        }
    }

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int sideLength) {
        N = sideLength;
        grid = new site[N * N - 1 + 2];
        for (int i = 0; i < N * N - 1; i++) {
            grid[i] = new site();
        }
        weightedQuickUnionUF = new WeightedQuickUnionUF(N * N - 1 + 2);
        virtualBottomSite = N;
        virtualTopSite = N + 1;
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
