package hw2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Percolation {

    private int N;
    public Site[] grid;
    private int virtualTopSite;
    private int virtualBottomSite;
    WeightedQuickUnionUF weightedQuickUnionUF;

    public class Site {
        public boolean isOpen;
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
        weightedQuickUnionUF = new WeightedQuickUnionUF(N * N + 2);
        virtualBottomSite = N * N;
        virtualTopSite = N * N + 1;
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
        if (!grid[pos].isOpen) {
            grid[pos].isOpen = true;
        }
        connectOpenSites(row, col);
    }

    private void connectOpenSites(int row, int col) {
        int pos = xyTo1D(row, col);
        ArrayList<Integer> neighbors = findNeighbors(row, col);
        for (int i : neighbors) {
            if (grid[i].isOpen && !weightedQuickUnionUF.connected(pos, i)) {
                weightedQuickUnionUF.union(pos, i);
            }
        }
    }

    public ArrayList<Integer> findNeighbors(int row, int col) {
        ArrayList<Integer> neighbors = new ArrayList();
        int[][] potentialNeighbors = new int[4][2];
        potentialNeighbors[0] = new int[]{row - 1, col};
        potentialNeighbors[1] = new int[]{row + 1, col};
        potentialNeighbors[2] = new int[]{row, col - 1};
        potentialNeighbors[3] = new int[]{row, col + 1};
        for (int[]i : potentialNeighbors) {
            if (isValidSite(i[0], i[1])) {
                neighbors.add(xyTo1D(i[0], i[1]));
            }
        }
        return neighbors;
    }

    private boolean isValidSite(int row, int col) {
        return (row >= 0 && row < N && col >= 0 && col < N);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int pos = xyTo1D(row, col);
        return grid[pos].isOpen;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int pos = xyTo1D(row, col);
        return isOpen(row, col) && weightedQuickUnionUF.connected(pos, virtualTopSite);
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
