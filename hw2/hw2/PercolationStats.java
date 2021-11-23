package hw2;

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private int testingTimes;
    private int sideLength;
    PercolationFactory percolationFactory;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        testingTimes = T;
        percolationFactory = pf;
        sideLength = N;
    }

    // sample mean of percolation threshold
    public double mean() {
        return getMean();
    }

    private double getMean() {
        int times = 0;
        double total = 0;
        while (times < testingTimes) {
            Percolation percolation = percolationFactory.make(sideLength);
            total += calThreshold(percolation);
            times ++;
        }
        return total / times;
    }

    private double calThreshold(Percolation percolation) {
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(0, sideLength);
            int col = StdRandom.uniform(0, sideLength);
            if (percolation.isOpen(row, col)) {
                continue;
            }
            percolation.open(row, col);
        }
        int numberOfOpenSites = percolation.numberOfOpenSites();
        return numberOfOpenSites / sideLength * sideLength;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0;
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
