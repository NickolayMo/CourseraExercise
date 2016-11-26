import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node(Point2D p, RectHV rect, Node lb, Node rt) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
        }
    }

    private Node root = null;
    private int size = 0;


    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null value not allow");
        }
        root = insert(root, p, true, 0.0, 0.0, 1.0, 1.0);
    }

    private Node insert(Node node, Point2D p, boolean isVertical, double x1, double y1, double x2, double y2) {
        if (node == null) {
            Node n = new Node(p, new RectHV(x1, y1, x2, y2), null, null);
            size++;
            return n;
        }
        if (p.equals(node.p)) {
            return node;
        }
        if (isVertical) {
            if (p.x() < node.p.x()) {
                node.lb = insert(node.lb, p, false, x1, y1, node.p.x(), y2);
            } else {
                node.rt = insert(node.rt, p, false, node.p.x(), y1, x2, y2);
            }
        } else {
            if (p.y() < node.p.y()) {
                node.lb = insert(node.lb, p, true, x1, y1, x2, node.p.y());
            } else {
                node.rt = insert(node.rt, p, true, x1, node.p.y(), x2, y2);
            }
        }
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null value not allow");
        }
        return contains(p, root, true);
    }

    private boolean contains(Point2D p, Node node, boolean isVertical) {
        if (node == null) {
            return false;
        }
        if (p.equals(node.p)) {
            return true;
        }
        if (isVertical) {
            if (p.x() < node.p.x()) {
                return contains(p, node.lb, false);
            } else {
                return contains(p, node.rt, false);
            }
        } else {
            if (p.y() < node.p.y()) {
                return contains(p, node.lb, true);
            } else {
                return contains(p, node.rt, true);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean isVertical) {
        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        if (node.lb != null) {
            draw(node.lb, !isVertical);
        }
        if (node.rt != null) {
            draw(node.rt, !isVertical);
        }
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        node.p.draw();
        StdDraw.setPenRadius();
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException("Null value not allow");
        }
        Stack<Point2D> range = new Stack<>();
        range(rect, root, range);
        return range;
    }

    private void range(RectHV rectHV, Node node, Stack<Point2D> range) {
        if (rectHV == null || range == null || node == null) {
            return;
        }
        if (!rectHV.intersects(node.rect)) {
            return;
        }
        if (rectHV.contains(node.p)) {
            range.push(node.p);
        }
        range(rectHV, node.lb, range);
        range(rectHV, node.rt, range);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Null value not allow");
        }
        if (size() == 0) {
            return null;
        }
        return nearest(p, root.p, root);
    }

    private Point2D nearest(Point2D point, Point2D closest, Node node) {
        Point2D closestPoint = closest;
        if (node == null) {
            return closestPoint;
        }
        if (point.distanceSquaredTo(closestPoint) > point.distanceSquaredTo(node.p)) {
            closestPoint = node.p;
        }
        if (node.rect.distanceSquaredTo(point) >= closest.distanceSquaredTo(point)) {
            return closest;
        }
        Node min = node.lb;
        Node max = node.rt;
        if (min != null && max != null) {
            if (min.rect.distanceSquaredTo(point) > max.rect.distanceSquaredTo(point)) {
                min = node.rt;
                max = node.lb;
            }
        }
        Point2D closestMin = nearest(point, closestPoint, min);
        if (closestMin.distanceSquaredTo(point) < closestPoint.distanceSquaredTo(point)) {
            closestPoint = closestMin;
        }

        Point2D closestMax = nearest(point, closestPoint, max);
        if (closestMax.distanceSquaredTo(point) < closestPoint.distanceSquaredTo(point)) {
            closestPoint = closestMax;
        }
        return closestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
