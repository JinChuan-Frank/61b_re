package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.ArrayList;

public class PercolationStats {

    private int testingTimes;
    private int sideLength;
    private int[] numOfOpenSitesAtPercolation;
    PercolationFactory percolationFactory;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        testingTimes = T;
        sideLength = N;
        numOfOpenSitesAtPercolation = new int[testingTimes];
        percolationFactory = pf;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(numOfOpenSitesAtPercolation);
    }

    private void setTestingTimes() {
        int times = 0;
        while (times < testingTimes) {
            Percolation percolation = percolationFactory.make(sideLength);
            calThreshold(percolation, times);
            times ++;
        }
    }

    private void calThreshold(Percolation percolation, int times) {
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(0, sideLength);
            int col = StdRandom.uniform(0, sideLength);
            if (percolation.isOpen(row, col)) {
                continue;
            }
            percolation.open(row, col);
        }
        int numberOfOpenSites = percolation.numberOfOpenSites();
        numOfOpenSitesAtPercolation[times] = numberOfOpenSites;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(numOfOpenSitesAtPercolation);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return 0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return 0;
    }

}
