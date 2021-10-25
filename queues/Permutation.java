import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int num = Integer.valueOf(args[0]);
        
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        String x;
        while (!StdIn.isEmpty()){
            x = StdIn.readString();
            queue.enqueue(x);
        }
        for (int i = 0; i < num; i++)
            StdOut.println(queue.dequeue());
    }
}