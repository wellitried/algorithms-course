package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double confidenceHi;
    private final double confidenceLo;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int gridSize, int trials) {
        if (gridSize <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        double[] thresholds = new double[trials];

        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(gridSize);
            int openedSites = 0;

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(gridSize) + 1;
                int col = StdRandom.uniform(gridSize) + 1;

                while (percolation.isOpen(row, col)) {
                    row = StdRandom.uniform(gridSize) + 1;
                    col = StdRandom.uniform(gridSize) + 1;
                }
                percolation.open(row, col);
                openedSites++;
            }

            thresholds[i] = (double) openedSites / (gridSize * gridSize);
        }

        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);

        double value = 1.96;
        confidenceLo = mean - value * stddev / Math.sqrt(trials);
        confidenceHi = mean + value * stddev / Math.sqrt(trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (described below)
    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(gridSize, trials);

        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
