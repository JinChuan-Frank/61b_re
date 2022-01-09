package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private int testingTimes;
    private int sideLength;
    private double[] thresholds;
    private PercolationFactory percolationFactory;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        testingTimes = T;
        sideLength = N;
        thresholds = new double[testingTimes];
        percolationFactory = pf;
        testTTimes();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    private void testTTimes() {
        int times = 0;
        while (times < testingTimes) {
            Percolation percolation = percolationFactory.make(sideLength);
            calThreshold(percolation, times);
            times++;
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
        double percolationThreshold = (double) percolation.numberOfOpenSites()
                / ((double) (sideLength * sideLength));
        thresholds[times] = percolationThreshold;
        /**System.out.println("number of open sites is " + percolation.numberOfOpenSites());
        System.out.println("number of total sites is " + sideLength * sideLength);
        System.out.println("times at " + times + "threshold is" + " " + percolationThreshold);*/
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double averageThreshold = mean();
        double lowEnd = averageThreshold - (1.96 * stddev() / Math.sqrt(testingTimes));
        return lowEnd;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double averageThreshold = mean();
        double highEnd = averageThreshold + (1.96 * stddev() / Math.sqrt(testingTimes));
        return highEnd;
    }

}
