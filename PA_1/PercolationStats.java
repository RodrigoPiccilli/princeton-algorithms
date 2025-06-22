import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private double[] results;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        results = new double[trials];

        this.trials = trials;

        for (int i = 0; i < trials; i++) {

            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {

                percolation.open(StdRandom.uniformInt(n) + 1, StdRandom.uniformInt(n) + 1);
            }

            results[i] = (percolation.numberOfOpenSites() / (double) (n * n));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return (mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials)));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        return (mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials)));
    }

    // test client (see below)
    public static void main(String[] args) {

        int gridLength = Integer.parseInt(args[0]);

        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(gridLength, trials);

        // PercolationStats stats = new PercolationStats(3, trials);


        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out
                .println("95% confidence interval = " + "[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");

    }

}
