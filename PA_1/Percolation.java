import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int length; // Length of grid length
    private WeightedQuickUnionUF openUnion;
    private boolean[][] openGrid; // Blocked = false, Open = true
    private int[][] idGrid;
    private int openCount;
    private int topSite;
    private int bottomSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        openGrid = new boolean[n][n];
        idGrid = new int[n][n];

        openCount = n * n;

        length = n;

        openUnion = new WeightedQuickUnionUF(n * n + 2);

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {
                openGrid[i][j] = false;
                idGrid[i][j] = openCount--;
            }

        }

        topSite = 0;

        bottomSite = (n * n) + 1;

    }

    private boolean validCell(int row, int col) {
        return !(row < 1 || row > length || col < 1 || col > length);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (!validCell(row, col))
            throw new IllegalArgumentException();

        if (isOpen(row, col))
            return;

        openGrid[row - 1][col - 1] = true;

        openCount++;

        // Neighbor Up
        if (row != 1 && isOpen(row - 1, col)) {
            openUnion.union(idGrid[row - 1][col - 1], idGrid[row - 2][col - 1]);
        }

        // Neighbor Down
        if (row != length && isOpen(row + 1, col)) {
            openUnion.union(idGrid[row - 1][col - 1], idGrid[row][col - 1]);
        }

        // Neighbor Left
        if (col != 1 && isOpen(row, col - 1)) {
            openUnion.union(idGrid[row - 1][col - 1], idGrid[row - 1][col - 2]);
        }

        // Neighbor Right
        if (col != length && isOpen(row, col + 1)) {
            openUnion.union(idGrid[row - 1][col - 1], idGrid[row - 1][col]);
        }

        if (row == 1) {
            openUnion.union(idGrid[row - 1][col - 1], topSite);
        }

        if (row == length) {
            openUnion.union(idGrid[row - 1][col - 1], bottomSite);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (!validCell(row, col))
            throw new IllegalArgumentException();

        return openGrid[row - 1][col - 1];

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        if (!validCell(row, col))
            throw new IllegalArgumentException();

        return openUnion.find(idGrid[row - 1][col - 1]) == openUnion.find(topSite);

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {

        if (openUnion.count() == 1)
            return true;

        return openUnion.find(topSite) == openUnion.find(bottomSite);

    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
