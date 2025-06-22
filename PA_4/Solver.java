import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

//TODO: Optimize for not calling isSolvable many times!

public class Solver {

    private SearchNode startMain;

    private SearchNode startTwin;

    private int numberOfMoves;

    private SearchNode endNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null)
            throw new IllegalArgumentException();

        startMain = new SearchNode(initial);

        startTwin = new SearchNode(initial.twin());

        endNode = null;

    }

    public boolean isSolvable() {

        SearchNode previousMain = null;

        SearchNode previousTwin = null;

        MinPQ<SearchNode> mainBoardSolver = new MinPQ<SearchNode>(new ByPriority());

        MinPQ<SearchNode> twinBoardSolver = new MinPQ<SearchNode>(new ByPriority());

        mainBoardSolver.insert(startMain);

        twinBoardSolver.insert(startTwin);

        while (previousMain == null || !previousMain.board.isGoal()) {

            SearchNode currentMain = mainBoardSolver.delMin();
            SearchNode currentTwin = twinBoardSolver.delMin();

            for (Board neighboringBoard : currentMain.board.neighbors()) {

                if (previousMain == null) {
                    mainBoardSolver.insert(new SearchNode(neighboringBoard, 1, currentMain));
                } else if (!neighboringBoard.equals(previousMain.board)) {
                    mainBoardSolver.insert(new SearchNode(neighboringBoard, currentMain.totalMoves + 1, currentMain));
                }

            }

            for (Board neighboringBoard : currentTwin.board.neighbors()) {

                if (previousTwin == null) {
                    twinBoardSolver.insert(new SearchNode(neighboringBoard, 1, currentTwin));
                } else if (!neighboringBoard.equals(previousTwin.board)) {
                    twinBoardSolver.insert(new SearchNode(neighboringBoard, currentTwin.totalMoves + 1, currentTwin));
                }

            }

            previousMain = currentMain;
            previousTwin = currentTwin;

            if (previousMain.board.isGoal()) {
                numberOfMoves = previousMain.totalMoves;
                endNode = previousMain;
                return true;
            }

            if (previousTwin.board.isGoal()) {
                break;
            }

        }

        return false;

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {

        if (!isSolvable())
            return -1;

        return numberOfMoves;

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        if (!isSolvable())
            return null;

        SearchNode traverse = endNode;

        ArrayList<Board> solution = new ArrayList<>();

        while (traverse != null) {
            solution.add(0, traverse.board);
            traverse = traverse.prev;
        }

        return solution;

    }

    private class SearchNode {

        private Board board;
        private int totalMoves;
        private SearchNode prev;
        private int manhattan;
        private int priority;

        public SearchNode(Board board) {
            this.board = board;
            totalMoves = 0;
            prev = null;
            this.manhattan = board.manhattan();
            priority = manhattan;
        }

        public SearchNode(Board board, int totalMoves, SearchNode prev) {
            this.board = board;
            this.totalMoves = totalMoves;
            this.prev = prev;
            this.manhattan = board.manhattan();
            priority = manhattan + totalMoves;
        }

    }

    private class ByPriority implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {

            if (o1.priority < o2.priority) {
                return -1;
            }

            if (o2.priority < o1.priority) {
                return 1;
            }

            return 0;
        }

    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

}