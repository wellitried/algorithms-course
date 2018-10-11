package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int gridSize;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final WeightedQuickUnionUF virtualWeightedQuickUnionUF;
    private final int bottomVirtualSite;
    private int numberOfOpenSites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        gridSize = n;
        bottomVirtualSite = gridSize * gridSize + 1;
        grid = new boolean[gridSize + 1][gridSize + 1];

        weightedQuickUnionUF = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        virtualWeightedQuickUnionUF = new WeightedQuickUnionUF(bottomVirtualSite);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (isOpen(row, col)) {
            return;
        }

        grid[row][col] = true;
        numberOfOpenSites++;

        int site = getSite(row - 1, col);

        if (row == 1) {
            weightedQuickUnionUF.union(0, col);
            virtualWeightedQuickUnionUF.union(0, col);
        }
        if (row == gridSize) {
            weightedQuickUnionUF.union(bottomVirtualSite, site);
        }

        // left neighbor
        if (col > 1 && isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(site, site - 1);
            virtualWeightedQuickUnionUF.union(site, site - 1);
        }
        // right neighbor
        if (col < gridSize && grid[row][col + 1]) {
            weightedQuickUnionUF.union(site, site + 1);
            virtualWeightedQuickUnionUF.union(site, site + 1);
        }
        // up neighbor
        if (row > 1 && isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(site, getSite(row - 2, col));
            virtualWeightedQuickUnionUF.union(site, getSite(row - 2, col));
        }
        // down neighbor
        if (row < gridSize && isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(site, getSite(row, col));
            virtualWeightedQuickUnionUF.union(site, getSite(row, col));
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        return grid[row][col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        if (isOpen(row, col)) {
            return virtualWeightedQuickUnionUF.connected(0, getSite(row - 1, col));
        }

        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, bottomVirtualSite);
    }

    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > gridSize || col > gridSize) {
            throw new IllegalArgumentException(row + " " + col);
        }
    }

    private int getSite(int row, int col) {
        return row * gridSize + col;
    }
}
