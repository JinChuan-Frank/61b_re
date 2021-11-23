package hw2;

import java.lang.reflect.Array;
import java.util.Arrays;

public class TestPercolationStats {


    public static void main(String[] args) {
        PercolationFactory percolationFactory = new PercolationFactory();
        PercolationStats percolationStats = new PercolationStats(2, 5, percolationFactory);
        double mean = percolationStats.mean();
        System.out.println(mean);
        System.out.println(Arrays.toString(percolationStats.numOfOpenSitesAtPercolation));
    }
}
