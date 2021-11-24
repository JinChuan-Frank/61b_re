package hw2;

public class TestPercolationStats {


    public static void main(String[] args) {
        PercolationFactory percolationFactory = new PercolationFactory();
        PercolationStats percolationStats = new PercolationStats(2, 5, percolationFactory);
        double mean = percolationStats.mean();
        System.out.println(mean);
    }
}
