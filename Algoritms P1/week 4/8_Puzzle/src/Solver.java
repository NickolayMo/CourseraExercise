import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<Node> pq = new MinPQ<>();
    private MinPQ<Node> twpq = new MinPQ<>();
    private Stack<Board> path = new Stack<>();
    private Node solvedNode = null;
    private boolean isSolved = true;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("Null value is not allow");
        }
        Node inNode = new Node(initial, null);
        Node inTwNode = new Node(initial.twin(), null);
        pq.insert(inNode);
        twpq.insert(inTwNode);
        while (true) {
            inTwNode = twpq.delMin();
            for (Board n : inTwNode.board.neighbors()) {
                if (n.equals(inTwNode.board) || (inTwNode.prev != null && n.equals(inTwNode.prev.board))) {
                    continue;
                }
                twpq.insert(new Node(n, inTwNode));
            }
            if (inTwNode.board.isGoal()) {
                isSolved = false;
                moves = -1;
                return;
            }
            inNode = pq.delMin();

            for (Board n : inNode.board.neighbors()) {
                if (n.equals(inNode.board) || (inNode.prev != null && n.equals(inNode.prev.board))) {
                    continue;
                }
                pq.insert(new Node(n, inNode));
            }
            if (inNode.board.isGoal()) {
                isSolved = true;
                moves = inNode.move;
                solvedNode = inNode;
                return;
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solvedNode == null) {
            return null;
        }
        Node node = solvedNode;
        while (true) {
            path.push(node.board);
            if (node.prev == null) {
                break;
            }
            node = node.prev;
        }
        return path;

    }

    private class Node implements Comparable<Node> {
        private int manhattan;
        private Board board;
        private Node prev;
        private int priority;
        private int move;

        public Node(Board board, Node prev) {
            manhattan = board.manhattan();
            this.board = board;
            this.prev = prev;
            if (prev != null) {
                move = prev.move + 1;
            } else {
                move = 0;
            }
            priority = move + manhattan;
        }

        @Override
        public int compareTo(Node o) {
            if (priority < o.priority) return -1;
            if (priority > o.priority) return 1;
            return 0;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        int[][] test = {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        Board b = new Board(test);
        Solver s = new Solver(b);
        StdOut.println(s.moves() + " == 4");

    }
}
