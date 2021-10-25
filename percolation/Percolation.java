// import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean [] state;
    private final int num;
    private final WeightedQuickUnionUF uf;
    private int count;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        uf = new WeightedQuickUnionUF(n*n+2);
        for(int j = 1; j <= n; j++) {
            uf.union(0, j);
            uf.union(n*n+1, n*n+1-j);
        }
        num = n;
        state = new boolean[n*n+2];
        state[0] = true;
        state[n*n+1] = true;
        for (int i = 1; i < n*n+1; i++)
            state[i] = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row<=0 || col<=0) throw new IllegalArgumentException();
        int p = (row-1)*num + col;
        if (!isOpen(row, col)) {
            state[p] = true;
            count++;
            if ((row-2) >= 0 && isOpen(row-1, col))  //up
                uf.union(p, (row-2)*num + col);
            if (row < num && isOpen(row+1, col))  //down
                uf.union(p, row*num + col);
            if (col+1 <= num && isOpen(row, col+1))  //right
                uf.union(p, (row-1)*num + col+1);
            if (col-1 > 0 && isOpen(row, col-1))  //left
                uf.union(p, (row-1)*num + col-1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row<=0 || col<=0) throw new IllegalArgumentException();
        return state[(row-1)*num + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) { 
        if (row<=0 || col<=0) throw new IllegalArgumentException();
        return (uf.find((row-1)*num + col) == uf.find(0)) &&  state[(row-1)*num + col];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(num*num+1) &&  state[num*num+1];
    }

    // public static void main(String[] args){
    //     Percolation percolation = new Percolation(5);
    //     percolation.open(1, 1);
    //     // perculation.isFull(1, 1);
    //     // perculation.isOpen(1, 1);
    //     StdOut.println(percolation.isFull(1, 1));
    //     StdOut.println(percolation.isOpen(1, 1));
        
    // }
}
