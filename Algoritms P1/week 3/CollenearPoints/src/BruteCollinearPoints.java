import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("Array is null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException("Array has null element");
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new java.lang.IllegalArgumentException("Array has repeatable points");
                }
            }
        }
        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);
        Arrays.sort(pointsCopy);
        for (int i = 0; i < pointsCopy.length; i++) {
            for (int j = i + 1; j < pointsCopy.length; j++) {
                double slopej = pointsCopy[i].slopeTo(pointsCopy[j]);
                for (int k = j + 1; k < pointsCopy.length; k++) {
                    double slopek = pointsCopy[i].slopeTo(pointsCopy[k]);
                    if (slopej != slopek) {
                        continue;
                    }
                    for (int l = k + 1; l < pointsCopy.length; l++) {
                        double slopel = pointsCopy[i].slopeTo(pointsCopy[l]);
                        if (slopej == slopel) {
                            segments.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] arg) {
        Point[] points = new Point[]{
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(3, 3)
        };
        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        StdOut.println(bruteCollinearPoints.numberOfSegments());
    }

}
