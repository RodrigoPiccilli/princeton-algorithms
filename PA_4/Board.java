import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private int[][] gameBoard;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        gameBoard = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            gameBoard[i] = tiles[i].clone();
        }

        n = tiles[0].length;
    }

    // string representation of this board
    public String toString() {

        StringBuilder stringRepresentation = new StringBuilder();

        stringRepresentation.append(gameBoard[0].length);

        // String boardRepresentation = "";

        for (int i = 0; i < gameBoard.length; i++) {

            stringRepresentation.append("\n");

            for (int j = 0; j < gameBoard.length; j++) {
                stringRepresentation.append(" " + gameBoard[i][j] + " ");
            }

        }

        return stringRepresentation.toString();

    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {

        int[][] goalArray = getGoalArray();

        int hammingCount = 0;

        for (int i = 0; i < goalArray.length; i++) {
            for (int j = 0; j < goalArray.length; j++) {
                if (goalArray[i][j] != 0 && goalArray[i][j] != gameBoard[i][j])
                    hammingCount++;
            }
        }

        return hammingCount;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        int manhattanSum = 0;

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (gameBoard[i][j] != 0)
                    manhattanSum += manhattanHelper(gameBoard[i][j], i, j);
            }
        }

        return manhattanSum;
    }

    private int manhattanHelper(int value, int row, int col) {

        int[][] goalArray = getGoalArray();

        int idealRow = 0;

        int idealCol = 0;

        int manhattanDistance = 0;

        for (int i = 0; i < goalArray.length; i++) {
            for (int j = 0; j < goalArray.length; j++) {
                if (goalArray[i][j] == value) {
                    idealRow = i;
                    idealCol = j;
                }
            }
        }

        manhattanDistance = Math.abs(idealRow - row) + Math.abs(idealCol - col);

        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return Arrays.deepEquals(gameBoard, getGoalArray());
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)
            return true;

        if (y == null)
            return false;

        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;

        if (that.dimension() != this.dimension())
            return false;

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (this.gameBoard[i][j] != that.gameBoard[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        ArrayList<Board> neighboringBoards = new ArrayList<>();

        int zeroRow = 0;

        int zeroCol = 0;

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {

                if (gameBoard[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }

        if (!(zeroRow - 1 < 0)) {

            int[][] copy = new int[gameBoard.length][gameBoard.length];

            for (int i = 0; i < gameBoard.length; i++) {
                copy[i] = gameBoard[i].clone();
            }

            copy[zeroRow][zeroCol] = copy[zeroRow - 1][zeroCol];
            copy[zeroRow - 1][zeroCol] = 0;

            neighboringBoards.add(new Board(copy));

        }

        if (!(zeroRow + 1 >= n)) {

            int[][] copy = new int[gameBoard.length][gameBoard.length];

            for (int i = 0; i < gameBoard.length; i++) {
                copy[i] = gameBoard[i].clone();
            }

            copy[zeroRow][zeroCol] = copy[zeroRow + 1][zeroCol];
            copy[zeroRow + 1][zeroCol] = 0;

            neighboringBoards.add(new Board(copy));

        }

        if (!(zeroCol - 1 < 0)) {

            int[][] copy = new int[gameBoard.length][gameBoard.length];

            for (int i = 0; i < gameBoard.length; i++) {
                copy[i] = gameBoard[i].clone();
            }
            copy[zeroRow][zeroCol] = copy[zeroRow][zeroCol - 1];
            copy[zeroRow][zeroCol - 1] = 0;

            neighboringBoards.add(new Board(copy));

        }

        if (!(zeroCol + 1 >= n)) {

            int[][] copy = new int[gameBoard.length][gameBoard.length];

            for (int i = 0; i < gameBoard.length; i++) {
                copy[i] = gameBoard[i].clone();
            }

            copy[zeroRow][zeroCol] = copy[zeroRow][zeroCol + 1];
            copy[zeroRow][zeroCol + 1] = 0;

            neighboringBoards.add(new Board(copy));
        }

        return neighboringBoards;

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int gameBoardLength = gameBoard.length;

        int[][] copy = new int[gameBoardLength][];
        for (int i = 0; i < gameBoardLength; i++) {
            copy[i] = gameBoard[i].clone();
        }

        // Find first non-zero tile in the first row
        int row1Col = -1;
        for (int j = 0; j < gameBoardLength; j++) {
            if (copy[0][j] != 0) {
                row1Col = j;
                break;
            }
        }

        // Find first non-zero tile in the second row
        int row2Col = -1;
        for (int j = 0; j < gameBoardLength; j++) {
            if (copy[1][j] != 0) {
                row2Col = j;
                break;
            }
        }

        // Swap them if found
        if (row1Col != -1 && row2Col != -1) {
            int temp = copy[0][row1Col];
            copy[0][row1Col] = copy[1][row2Col];
            copy[1][row2Col] = temp;
        } else {
            throw new IllegalStateException("Cannot find two non-zero tiles to swap in first two rows");
        }

        return new Board(copy);
    }

    private int[][] getGoalArray() {

        int[][] goalArray = new int[gameBoard.length][gameBoard.length];

        int count = 1;

        for (int i = 0; i < goalArray.length; i++) {
            for (int j = 0; j < goalArray.length; j++) {
                goalArray[i][j] = count++;
            }
        }

        goalArray[gameBoard.length - 1][gameBoard.length - 1] = 0;

        return goalArray;

    }

    // unit testing (not graded)
    public static void main(String[] args) {

        int[][] testGame = {
                { 4, 2, 6 },
                { 1, 5, 8 },
                { 7, 0, 3 }
        };

        Board testBoard = new Board(testGame);

        System.out.println(testBoard.toString());
        System.out.println(testBoard.twin().toString());

    }

}