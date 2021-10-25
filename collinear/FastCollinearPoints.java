import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment [] lines = new LineSegment[1];
    private int count = 0;

    public FastCollinearPoints(Point[] opoints) {    // finds all line segments containing 4 or more points
        if (opoints == null) throw new IllegalArgumentException();
        checkDuplicatesAndNulls(opoints);
        Point[] points = opoints.clone();
        for (int origin = 0; origin < points.length; origin++) {
            Arrays.sort(points);
            Arrays.sort(points, points[origin].slopeOrder());
            for (int i = 0, j = 1, k = 2; k < points.length; k++) {
                while (k < points.length && points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {
                    k++;
                }
                if (k - j >= 3 && points[i].compareTo(points[j]) < 0) {
                    lines[count++] = new LineSegment(points[i], points[k-1]);
                    if (count >= lines.length) resize(2*lines.length);
                }
                j = k;
            }
        }
        size(count);

    }
    private void resize(int capacity) {
        LineSegment[] copy =  new LineSegment[capacity];
        for (int i = 0; i < lines.length; i++)
            copy[i] = lines[i];
        lines = copy;
    }
    
    private void size(int capacity) {
        LineSegment[] copy =  new LineSegment[capacity];
        for (int i = 0; i < capacity; i++)
            copy[i] = lines[i];
        lines = copy;
    }
   
    private void checkDuplicatesAndNulls(Point[] points) {
        if (points[0] == null) throw new IllegalArgumentException();
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0 || points[i] == null) throw new IllegalArgumentException();
        }
    }

    public           int numberOfSegments() {       // the number of line segments
        return count;
    }

    public LineSegment[] segments() {               // the line segments
        return lines;
    }
    
    public static void main(String[] args) {

        // read the n points from a file
        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }