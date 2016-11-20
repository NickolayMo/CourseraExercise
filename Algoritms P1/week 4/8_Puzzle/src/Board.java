import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private int dimension;
    private int[][] boardsBlocks;
    private int xEmpty;
    private int yEmpty;

    // construct a board from an n-by-n array of blocks
    public Board(int[][] blocks) {
        dimension = blocks.length;
        boardsBlocks = copArr(blocks);
        findEmpty();
    }

    // (where blocks[i][j] = block in row i, column j)
    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        int n = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (boardsBlocks[i][j] != 0 && !inRightPlace(i, j)) {
                    n++;
                }
            }
        }
        return n;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int n = 0;
        int mx;
        int my;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (boardsBlocks[i][j] != 0 && !inRightPlace(i, j)) {
                    my = Math.abs((boardsBlocks[i][j] - 1) / dimension - i);
                    mx = Math.abs(j - ((boardsBlocks[i][j] - 1) % dimension));
                    n += my + mx;
                }
            }
        }

        return n;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twinBlocks = copArr(boardsBlocks);
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension - 1; col++) {
                if ((twinBlocks[row][col] != 0) && (twinBlocks[row][col + 1] != 0)) {
                    exch(twinBlocks, row, col, row, col + 1);
                    return new Board(twinBlocks);
                }
            }
        }
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension != this.dimension) return false;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (that.boardsBlocks[i][j] != this.boardsBlocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean inRightPlace(int i, int j) {
        int y = Math.abs((boardsBlocks[i][j] - 1) / dimension - i);
        int x = Math.abs(j - ((boardsBlocks[i][j] - 1) % dimension));
        return y == 0 && x == 0;
    }

    private void exch(int[][] board, int i, int j, int i2, int j2) {
        int tmp = board[i][j];
        board[i][j] = board[i2][j2];
        board[i2][j2] = tmp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> nq = new Queue<>();
        int[][] boards;
        if (yEmpty != 0) {
            boards = copArr(boardsBlocks);
            exch(boards, yEmpty, xEmpty, yEmpty - 1, xEmpty);
            addNeighbors(nq, boards);
        }
        if (xEmpty != 0) {
            boards = copArr(boardsBlocks);
            exch(boards, yEmpty, xEmpty, yEmpty, xEmpty - 1);
            addNeighbors(nq, boards);
        }
        if (yEmpty < dimension - 1) {
            boards = copArr(boardsBlocks);
            exch(boards, yEmpty, xEmpty, yEmpty + 1, xEmpty);
            addNeighbors(nq, boards);
        }
        if (xEmpty < dimension - 1) {
            boards = copArr(boardsBlocks);
            exch(boards, yEmpty, xEmpty, yEmpty, xEmpty + 1);
            addNeighbors(nq, boards);
        }
        return nq;
    }

    private void addNeighbors(Queue<Board> nq, int[][] blocks) {
        Board n = new Board(blocks);
        nq.enqueue(n);
    }

    private int[][] copArr(int[][] arr) {
        int[][] newBoardsBlocks = new int[dimension][dimension];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                newBoardsBlocks[i][j] = arr[i][j];
            }
        }
        return newBoardsBlocks;
    }

    private void findEmpty() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (boardsBlocks[i][j] == 0) {
                    xEmpty = j;
                    yEmpty = i;
                }
            }
        }
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", boardsBlocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] test = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        int[][] test2 = {
                {1, 8, 3},
                {4, 0, 5},
                {7, 2, 6}
        };
        int[][] test3 = {
                {3, 0},
                {1, 2}
        };
        Board b = new Board(test);
        Board b1 = new Board(test);
        Board c = new Board(test2);
        Board d = new Board(test3);
        StdOut.println(b.dimension);
        StdOut.println(b.manhattan());
        StdOut.println(b.hamming());
        StdOut.println(b.toString());
        StdOut.println(b.equals(b1) + " true");
        StdOut.println(b.equals(c) + " false");
        StdOut.println(b.isGoal() + " true");
        for (Board v : b.neighbors()) {
            StdOut.println(v.toString());
            StdOut.println(v.manhattan());
            StdOut.println(v.hamming());
        }
        Board tw = b.twin();
        StdOut.println(tw.toString());

        Board dtw = d.twin();
        StdOut.println(dtw.toString());
    }
}

