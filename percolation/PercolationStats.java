import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final int T;
    private final double [] total;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n<=0 || trials<=0) throw new IllegalArgumentException();
        T = trials;
        total = new double[T];
        Percolation perculation;
        int row, col;
        for (int i = 0; i < T; i++) {
            perculation = new Percolation(n);
            while (!perculation.percolates()) {
                row = StdRandom.uniform(1, n+1);
                col = StdRandom.uniform(1, n+1);
                perculation.open(row, col);
            }
            total[i] = (double) perculation.numberOfOpenSites()/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(total);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(total);
        return stddev; 
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        mean = StdStats.mean(total);
        stddev = StdStats.stddev(total);
        return (mean - (1.96*stddev) / Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        mean = StdStats.mean(total);
        stddev = StdStats.stddev(total);
        return (mean + (1.96*stddev) / Math.sqrt(T));
    }

   // test client (see below)
   public static void main(String[] args) {
    int n = StdIn.readInt();
    int t = StdIn.readInt();
    PercolationStats percolationStats = new PercolationStats(n, t);
    StdOut.println("mean                    = "+percolationStats.mean());
    StdOut.println("stddev                  = "+percolationStats.stddev());
    StdOut.println("95% confidence interval = ["+percolationStats.confidenceLo()+", "+percolationStats.confidenceHi()+"]");
   }
}
