import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Deque;
import java.util.LinkedList;

public class Solver {
    private Solving original;
    private Node solutionNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        original = new Solving(initial);
        Solving twin = new Solving(initial.twin());

        while (!original.isGoal() && !twin.isGoal()) {
            twin.step();
            original.step();
        }

        if (original.isGoal()) {
            solutionNode = original.min;
        }
    }

    private class Solving {
        private Node min;
        private MinPQ<Node> queue = new MinPQ<Node>();
        private Board initial;

        public Solving(Board initial) {
            this.initial = initial;
            queue.insert(new Node(initial, 0, null));
        }

        public void step() {
            min = queue.delMin();
            int nodemoves = min.moves;
            Board prevBoard = nodemoves > 0 ? min.prev.board : null;

            for (Board neighbor : min.board.neighbors()) {
                if (prevBoard != null && neighbor.equals(prevBoard)) {
                    continue;
                }
                queue.insert(new Node(neighbor, nodemoves+1, min));
            }
        }

        public boolean isGoal() {
            if (min == null)
                return initial.isGoal();
            return min.board.isGoal();
        }
    }

    private class Node implements Comparable<Node> {
        private Board board;
        private Node prev;
        private int manhattan;
        private int moves;

        Node(Board board, int moves, Node prev) {
            this.board = board;
            this.prev = prev;
            this.manhattan = board.manhattan();
            this.moves = moves;
        }

        @Override
        public int compareTo(Node o) {
            return this.priority() - o.priority();
        }

        public int priority() {
            return manhattan + moves;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return original.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return solutionNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        Deque<Board> solution = new LinkedList<>();
        Node node = solutionNode;
        if (node == null)
            solution.addFirst(original.initial);
        while (node != null) {
            solution.addFirst(node.board);
            node = node.prev;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        int n = StdIn.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = StdIn.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}