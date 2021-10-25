import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] otiles) {
        tiles = copyOf(otiles);
    }

    // string representation of this board
    public String toString() {
        String output = tiles.length + "\n";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                output += tiles[i][j] + " ";
            }
            output += "\n";
        }
        return output;
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int val;
        int count = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                val = tiles[i][j];
                if (val != (i * tiles.length) + j + 1 && val != 0)
                    count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int values = 0;
        int val;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                val = tiles[i][j];
                if (val != ((i) * tiles.length) + j + 1 && val != 0) {
                    values += Math.abs(i - ((val - 1) / tiles.length));
                    values += Math.abs(j - (val - 1) % tiles.length);
                }
            }
        }
        return values;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int count = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == ((i) * tiles.length) + j + 1 && tiles[i][j] != 0) {
                    count++;
                }
            }
        }
        return count == tiles.length * tiles.length - 1;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (this.getClass() != y.getClass())
            return false;

        Board copy = (Board) y;
        if (tiles.length != copy.dimension())
            return false;

        if (tiles.length == copy.dimension()) {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (tiles[i][j] != copy.tiles[i][j])
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new neighbouring();
            }
        };
    }

    // the most stupid code I have ever ever done :) :)
    private class neighbouring implements Iterator<Board> {
        private int count = 0;
        private int row = -2, col = -2;
        private Board[] neighbours;

        private void swap(int rowOld, int colOld, int rowNew, int colNew, int[][] temp) {
            temp[rowOld][colOld] = tiles[rowOld][colOld]; // initialization
            temp[row][col] = tiles[rowNew][colNew];
            temp[rowNew][colNew] = 0;
        }

        public neighbouring() {
            int[][] temp = copyOf(tiles);

            for (int i = 0; i < tiles.length && row < 0; i++) {
                for (int j = 0; j < tiles.length && col < 0; j++) {
                    if (tiles[i][j] == 0) {
                        row = i;
                        col = j;
                    }
                }
            }

            if (row == 0) {
                if (col == 0) {
                    neighbours = new Board[2];
                    swap(0, 0, row, col + 1, temp);
                    neighbours[0] = new Board(copyOf(temp));
                    swap(row, col + 1, row + 1, col, temp);
                    neighbours[1] = new Board(copyOf(temp));
                } else if (col == temp.length - 1) {
                    neighbours = new Board[2];
                    swap(0, 0, row, col - 1, temp);
                    neighbours[0] = new Board(copyOf(temp));
                    swap(row, col - 1, row + 1, col, temp);
                    neighbours[1] = new Board(copyOf(temp));
                } else {
                    neighbours = new Board[3];
                    swap(0, 0, row, col + 1, temp);
                    neighbours[0] = new Board(copyOf(temp));
                    swap(row, col + 1, row + 1, col, temp);
                    neighbours[1] = new Board(copyOf(temp));
                    swap(row + 1, col, row, col - 1, temp);
                    neighbours[2] = new Board(copyOf(temp));
                }
            } else if (row == temp.length - 1) {
                if (col == 0) {
                    neighbours = new Board[2];
                    swap(0, 0, row, col + 1, temp);
                    neighbours[0] = new Board(copyOf(temp));
                    swap(row, col + 1, row - 1, col, temp);
                    neighbours[1] = new Board(copyOf(temp));
                } else if (col == temp.length - 1) {
                    neighbours = new Board[2];
                    swap(0, 0, row, col - 1, temp);
                    neighbours[0] = new Board(copyOf(temp));
                    swap(row, col - 1, row - 1, col, temp);
                    neighbours[1] = new Board(copyOf(temp));
                } else {
                    neighbours = new Board[3];
                    swap(0, 0, row, col + 1, temp);
                    neighbours[0] = new Board(copyOf(temp));
                    swap(row, col + 1, row - 1, col, temp);
                    neighbours[1] = new Board(copyOf(temp));
                    swap(row - 1, col, row, col - 1, temp);
                    neighbours[2] = new Board(copyOf(temp));
                }

            } else if (col == 0) {
                neighbours = new Board[3];
                swap(0, 0, row, col + 1, temp);
                neighbours[0] = new Board(copyOf(temp));
                swap(row, col + 1, row - 1, col, temp);
                neighbours[1] = new Board(copyOf(temp));
                swap(row - 1, col, row + 1, col, temp);
                neighbours[2] = new Board(copyOf(temp));
            } else if (col == temp.length - 1) {
                neighbours = new Board[3];
                swap(0, 0, row + 1, col, temp);
                neighbours[0] = new Board(copyOf(temp));
                swap(row + 1, col, row - 1, col, temp);
                neighbours[1] = new Board(copyOf(temp));
                swap(row - 1, col, row, col - 1, temp);
                neighbours[2] = new Board(copyOf(temp));
            } else {
                neighbours = new Board[4];
                swap(0, 0, row, col + 1, temp);
                neighbours[0] = new Board(copyOf(temp));
                swap(row, col + 1, row - 1, col, temp);
                neighbours[1] = new Board(copyOf(temp));
                swap(row - 1, col, row, col - 1, temp);
                neighbours[2] = new Board(copyOf(temp));
                swap(row, col - 1, row + 1, col, temp);
                neighbours[3] = new Board(copyOf(temp));
            }
        }

        @Override
        public boolean hasNext() {
            return count < neighbours.length;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Board next() {
            return neighbours[count++];
        }
    };

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] tile = copyOf(tiles);
        if (tile[0][0] == 0 || tile[0][1] == 0) {
            swap(tile, 1, 0);
        } else {
            swap(tile, 0, 0);
        }

        return new Board(tile);
    }

    private int[][] copyOf(int[][] matrix) {
        int[][] clone = new int[matrix.length][];
        for (int row = 0; row < matrix.length; row++) {
            clone[row] = matrix[row].clone();
        }
        return clone;
    }

    private void swap(int[][] tile, int i, int j) {
        int swap;
        swap = tile[i][j];
        tile[i][j] = tile[i][j + 1];
        tile[i][j + 1] = swap;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = StdIn.readInt();
        Board initial1 = new Board(tiles);

        int m = StdIn.readInt();
        int[][] tiles2 = new int[m][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < m; j++)
                tiles2[i][j] = StdIn.readInt();
        Board initial2 = new Board(tiles2);
        StdOut.println(initial1.equals(initial2));

        // for(Board neighbor : initial.neighbors()){
        // StdOut.println(neighbor.toString());
        // }
    }
}