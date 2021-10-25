import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.Iterator;

public class PointSET {
    private SET<Point2D> set;

    public         PointSET() {                              // construct an empty set of points 
        set = new SET<Point2D>();
    }

    public           boolean isEmpty() {                     // is the set empty? 
        return set.isEmpty();
    }

    public               int size() {                        // number of points in the set 
        return set.size();
    }

    public              void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        set.add(p);
    }

    public           boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    public              void draw() {                        // draw all points to standard draw 
        for (Point2D point : set) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle (or on the boundary) 
        SET<Point2D> finalSet = new SET<Point2D>();

        if (rect == null) throw new IllegalArgumentException();
        for (Point2D point : set)
            if (rect.contains(point)) finalSet.add(point);

        return new Iterable<Point2D>() {
            @Override
            public Iterator<Point2D> iterator() {
                return finalSet.iterator();
            }
        };
    }

    public           Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) throw new IllegalArgumentException();
        if (set.isEmpty()) return null;
        Point2D temp = set.max();

        for (Point2D point : set){
            if (point.distanceTo(p) < temp.distanceTo(p)) temp = point;
        }
        return temp;
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional) 
    
    }
}