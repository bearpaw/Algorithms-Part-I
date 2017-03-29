import java.util.ArrayList;

// passed all the tests
public class Board {
    // class used to store coordinate pairs
    private class Point {
        private int i;
        private int j;
    }

    private short[][] blocks;
    private Point blankPos = new Point();

    public Board(int[][] blk) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        this.blocks = new short[blk.length][blk.length];

        for (int i = 0; i < blk.length; i++) {
            for (int j = 0; j < blk.length; j++) {
                this.blocks[i][j] = (short)blk[i][j];
                if (blk[i][j] == 0) {
                    blankPos.i = i;
                    blankPos.j = j;
                }
            }
        }
    }
    
    private int[][] integerBoard() {
        int[][] boardCopy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                boardCopy[i][j] = (int)blocks[i][j];
            }
        }
        return boardCopy;
    }

    public int dimension() {
        // board dimension n
        return blocks.length;
    }

    public int hamming() {
        int gt = 1;
        int dist = 0;
        // number of blocks out of place
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                if (blocks[i][j] != gt++)
                    ++dist;
            }
        }
        // the last block should be omitted
//        if (blocks[this.dimension() - 1][this.dimension() - 1] != 0)
            --dist;
        return dist;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int dist = 0;
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                int r = (blocks[i][j] - 1) / this.dimension(); // gt row
                int c = (blocks[i][j] - 1) % this.dimension(); // gt col
                if ((i != r || j != c) && blocks[i][j] != 0)
                    dist += Math.abs(r - i) + Math.abs(j - c);
            }
        }
        return dist;
    }

    public boolean isGoal() {
        // is this board the goal board?
        return hamming() == 0;
    }

    private void exch(int i1, int j1, int i2, int j2) {
        short tmp = this.blocks[i1][j1];
        this.blocks[i1][j1] = this.blocks[i2][j2];
        this.blocks[i2][j2] = tmp;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        // NOTE! Blank does not account
        Board twinBoard = new Board(integerBoard());

        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension() - 1; j++) {
                if (blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
                    twinBoard.exch(i, j, i, j + 1);
                    return twinBoard;
                }
            }
        }
        return twinBoard;
    }

    public boolean equals(Object y) {
        // does this board equal y?
        if (this == y)
            return true;
        if (y == null || this.getClass() != y.getClass())
            return false;

        // convert y to board
        Board other = (Board) y;
        if (this.dimension() != other.dimension())
            return false;
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                if (this.blocks[i][j] != other.blocks[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        ArrayList<Board> boards = new ArrayList<Board>(); 
        // Vertical
        if (blankPos.i > 0 && blankPos.i < this.dimension() - 1) { // up and
                                                                   // down
            Board up = new Board(integerBoard());
            Board down = new Board(integerBoard());
            up.exch(blankPos.i, blankPos.j, blankPos.i - 1, blankPos.j);
            down.exch(blankPos.i, blankPos.j, blankPos.i + 1, blankPos.j);
            up.blankPos.i -= 1; 
            down.blankPos.i += 1;
            boards.add(up);
            boards.add(down);
        } else if (blankPos.i > 0) { // up
            Board up = new Board(integerBoard());
            up.exch(blankPos.i, blankPos.j, blankPos.i - 1, blankPos.j);
            up.blankPos.i -= 1; 
            boards.add(up);
        } else { // down
            Board down = new Board(integerBoard());
            down.exch(blankPos.i, blankPos.j, blankPos.i + 1, blankPos.j);
            down.blankPos.i += 1;
            boards.add(down);
        }
        // Horizontal
        if (blankPos.j > 0 && blankPos.j < this.dimension() - 1) { // left and
                                                                   // right
            Board left = new Board(integerBoard());
            Board right = new Board(integerBoard());
            left.exch(blankPos.i, blankPos.j, blankPos.i, blankPos.j - 1);
            right.exch(blankPos.i, blankPos.j, blankPos.i, blankPos.j + 1);
            left.blankPos.j -= 1; 
            right.blankPos.j += 1;
            boards.add(left);
            boards.add(right);
        } else if (blankPos.j > 0) { // left
            Board left = new Board(integerBoard());
            left.exch(blankPos.i, blankPos.j, blankPos.i, blankPos.j - 1);
            left.blankPos.j -= 1;
            boards.add(left);
        } else { // right
            Board right = new Board(integerBoard());
            right.exch(blankPos.i, blankPos.j, blankPos.i, blankPos.j + 1);
            right.blankPos.j += 1;
            boards.add(right);
        }

        return boards;
    }

    public String toString() {
        // string representation of this board (in the output format specified
        // below)
        String str = "";
        str += this.dimension() + "\n";
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                str += this.blocks[i][j] + " ";
            }
            str += "\n";
        }
        return str;
    }

    public static void main(String[] args) {
        // unit tests (not graded)
    }
}