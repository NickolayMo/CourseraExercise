import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class TestClient {
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("E:\\MYPROJECTS\\hosting\\Coursera\\Algoritms P1\\week 3\\CollenearPoints\\test\\input40.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(.002);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        StdDraw.setPenRadius(.0002);

        // print and draw the line segments
        FastCollinearPoints collinear1 = new FastCollinearPoints(points);
        for (LineSegment segment : collinear1.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

//        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
        StdDraw.show();
        //FastCollinearPoints fast = new FastCollinearPoints(points);
    }
}
