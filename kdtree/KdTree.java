import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final RectHV TILE = new RectHV(0, 0, 1, 1);
    private Node root = null;


    public KdTree() { // construct an empty set of points
    }

    private class Node {
        private double x, y;
        private Point2D key;
        private char orientation;
        private Node left, right;
        private int count;

        public Node(Point2D key, char orientation) {
            this.x = key.x();
            this.y = key.y();
            this.orientation = orientation;
            this.key = key;
        }
    }

    public boolean isEmpty() { // is the set empty?
        return root == null;
    }

    public int size() { // number of points in the set
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
        {
            return 0;
        }
        return x.count;
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        root = put(root, p, null);
    }

    private Node put(Node root, Point2D p, Node parent) {
        int cmp = 0;
        char orientation = 'f';

        if (root == null) {
            if (parent == null || parent.orientation == 'h') orientation = 'v';
            else if (parent.orientation == 'v') orientation = 'h';
            Node temp = new Node(p, orientation);
            temp.count = 1 + size(temp.left) + size(temp.right);
            return temp;
        }

        if (root.orientation == 'v') {
            cmp = Double.compare(p.x(), root.x); //this line  
            if (cmp == 0) {
                cmp = Double.compare(p.y(), root.y);
            }      
        }
        if (root.orientation == 'h') {
            cmp = Double.compare(p.y(), root.y); //this line  
            if (cmp == 0) {
                cmp = Double.compare(p.x(), root.x);
            }        
        }

        if (cmp < 0)
            root.left = put(root.left, p, root);
        else if (cmp > 0)
            root.right = put(root.right, p, root);

        root.count = 1 + size(root.left) + size(root.right);
        return root;
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null) throw new IllegalArgumentException();
        int cmp = 0;
        Node root = this.root;
        while (root != null) {
            if (root.orientation == 'v') {
                cmp = Double.compare(p.x(), root.x); //this line        
            }
            if (root.orientation == 'h') {
                cmp = Double.compare(p.y(), root.y); //this line        
            }

            if (cmp < 0) {
                root = root.left;
            }
            else if (cmp > 0) {
                root = root.right;
            }
            else if (Double.compare(p.x(), root.x) == 0 && Double.compare(p.y(), root.y) == 0) {
                return true;
            }else{
                break;
            }
        }
        return false;
    }

    public void draw() { // draw all points to standard draw
        drawNode(root, TILE);
    }

    private void drawNode(Node x, RectHV rect) {
        if (x != null) {
            if (x.orientation == 'v') {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                x.key.draw();
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(0.001);
                StdDraw.line(x.x, rect.ymax(), x.x, rect.ymin());
            }
            if (x.orientation == 'h') {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                x.key.draw();
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(0.001);
                StdDraw.line(rect.xmax(), x.y, rect.xmin(), x.y);
            }
            drawNode(x.left, getChildRect(x, rect, true));
            drawNode(x.right, getChildRect(x, rect, false));
        }
    }

    private RectHV getChildRect(Node x, RectHV rect, boolean left) {
        if (x.orientation == 'v') {
            if (left) {
                return new RectHV(rect.xmin(), rect.ymin(), x.x, rect.ymax());
            } else {
                return new RectHV(x.x, rect.ymin(), rect.xmax(), rect.ymax());
            }
        } else {
            if (left) {
                return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.y);
            } else {
                return new RectHV(rect.xmin(), x.y, rect.xmax(), rect.ymax());
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        return new Iterable<Point2D>() {
            @Override
            public Iterator<Point2D> iterator() {
                return new isInside(rect);
            }
        };
    }

    private class isInside implements Iterator<Point2D> {
        private int count = 0;
        private Queue<Point2D> q = new Queue<Point2D>();

        public isInside (RectHV rect) {
            RectHV nodeRect = new RectHV(0, 0, 1, 1);
            search(root, rect, nodeRect);
        }

        private void search (Node root, RectHV rect, RectHV nodeRect) {
            if (root == null) return;
            if (rect.intersects(nodeRect)) {
                if (rect.contains(root.key)) q.enqueue(root.key);
                if (root.left != null) {
                    search(root.left, rect, getChildRect(root, nodeRect, true));
                }
                if (root.right != null) {
                    search(root.right, rect, getChildRect(root, nodeRect, false));
                }
            }
        }

        @Override
        public boolean hasNext()
        {
            return count < q.size();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Point2D next()
        {
            return q.dequeue();
        }
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();
        return theNearest(p, root, null, TILE, Double.MAX_VALUE);
    }

    private Point2D theNearest(Point2D p, Node node, Point2D champion, RectHV rect, double minDistance){
        if (node != null && rect.distanceSquaredTo(p) < minDistance) {
            double tmpDist = node.key.distanceSquaredTo(p);
            if (tmpDist < minDistance) {
                champion = node.key;
                minDistance = tmpDist;
            }
            int cmp = node.key.compareTo(p);

            if (cmp < 0) {
                champion = theNearest(p, node.left, champion, getChildRect(node, rect, true), minDistance);
                champion = theNearest(p, node.right, champion, getChildRect(node, rect, false), champion.distanceSquaredTo(p));
            } else if (cmp > 0) {
                champion = theNearest(p, node.right, champion, getChildRect(node, rect, false), minDistance);
                champion = theNearest(p, node.left, champion, getChildRect(node, rect, true), champion.distanceSquaredTo(p));
            }
        }

        return champion;
    }
    public static void main(String[] args) { // unit testing of the methods (optional)
        // String filename = args[0];
        // In in = new In(filename);
        // PointSET brute = new PointSET();
        // KdTree kdtree = new KdTree();
        // Point2D p = new Point2D(0.5,0.5);
        // kdtree.insert(p);
        // StdOut.println(kdtree.size());
        // StdOut.println(kdtree.isEmpty());

        // StdOut.println(kdtree.contains(p));

        // Point2D q = new Point2D(0.5, 0.92);
        // StdOut.println(kdtree.contains(q));

        // In in = new In();
        // double x = 1;
        // while (x!=0) {
        //     x = in.readDouble();
        //     double y = in.readDouble();
        //     Point2D p = new Point2D(x, y);
        //     kdtree.insert(p);
        //     brute.insert(p);
        // }
        // StdOut.println(kdtree.size());

        // RectHV rect = new RectHV(0, 0, 1, 1);

        // for (Point2D p : kdtree.range(rect))
        //     p.draw();
    

    }
}