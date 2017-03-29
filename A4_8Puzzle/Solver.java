import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private LinkedList<Board> solutionList;
    private int numMoves;
    private boolean solvable;

    // helper classes for storing boards in PQ
    private class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private Node prev; // record the previous board

        Node(Board b, int m, Node p) {
            this.board = b;
            this.moves = m;
            this.prev = p;
        }

        private int priority() {
            return this.moves + this.board.manhattan();
        }

        @Override
        public int compareTo(Node other) {
            return this.priority() - other.priority();
        }

        public String toString() {
            String str = "";
            str += this.board.toString();
            str += "Moves: " + this.moves + "\n";
            return str;
        }

    }

    public Solver(Board initial) {
        MinPQ<Node> minPQ;
        MinPQ<Node> twinPQ;
        Node twinSearchNode = null;
        Node curSearchNode = null;
        
        // find a solution to the initial board (using the A* algorithm)
        this.solutionList = new LinkedList<Board>();
        minPQ = new MinPQ<Node>();
        twinPQ = new MinPQ<Node>();

        // inseart initial board
        minPQ.insert(new Node(initial, 0, null));
        twinPQ.insert(new Node(initial.twin(), 0, null));

        while (!minPQ.isEmpty()) {
            // remove the node with the min priority
            curSearchNode = minPQ.delMin();
            twinSearchNode = twinPQ.delMin();

            // solutionList.add(curSearchNode.board);
            if (curSearchNode.board.isGoal() || twinSearchNode.board.isGoal())
                break;

            // // add neighbors to the minPQ
            // System.out.println("************************");
            // System.out.println(curSearchNode);
            // System.out.println("========================");

            // add to minPQ
            for (Board b : curSearchNode.board.neighbors()) {
                if (!visited(b, curSearchNode)) {
                    minPQ.insert(new Node(b, curSearchNode.moves + 1, curSearchNode));
                }
            }

            // add to twinPQ
            for (Board b : twinSearchNode.board.neighbors()) {
                if (!visited(b, twinSearchNode)) {
                    twinPQ.insert(new Node(b, twinSearchNode.moves + 1, twinSearchNode));
                }
            }
        }
        if (curSearchNode.board.isGoal()) {
            this.solvable = true;
            this.numMoves = curSearchNode.moves;
            while (curSearchNode != null) {
                solutionList.addFirst(curSearchNode.board);
                curSearchNode = curSearchNode.prev;
            }
        }
        if (twinSearchNode.board.isGoal()) {
            this.solvable = false;
            this.numMoves = -1;
        }
    }

    private boolean visited(Board b, Node cur) {
        while (cur != null) {
            if (b.equals(cur.board))
                return true;
            cur = cur.prev;
        }
        return false;
    }

    public boolean isSolvable() {
        // is the initial board solvable?
        return this.solvable;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        return this.numMoves;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        if (isSolvable())
            return this.solutionList;
        return null;
    }

    public static void main(String[] args) {
        // create initial board from file
        String infile = "8puzzle/puzzle08.txt";
        In in = new In(infile);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readByte();
        Board initial = new Board(blocks);

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