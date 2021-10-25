import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class RandomWord {
    public static void shuffle(Object[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            // choose index uniformly in [0, i]
            int r = (int) (Math.random() * (i + 1));
            Object swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
    }
    public static void main(String[] arg) {
        // read in the data
        String[] a = StdIn.readAllStrings();

        // shuffle the array
        RandomWord.shuffle(a);
        
        // print results.
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
    }
}