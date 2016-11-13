import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
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
        int n = points.length;
        Point[] sortedPoints = new Point[n];
        Point[] sortedPointsByPoint = new Point[n];
        System.arraycopy(points, 0, sortedPoints, 0, n);
        Arrays.sort(sortedPoints);
        for (int i = 0; i < sortedPoints.length - 3; i++) {
            System.arraycopy(sortedPoints, 0, sortedPointsByPoint, 0, n);
            Arrays.sort(sortedPointsByPoint, sortedPointsByPoint[i].slopeOrder());
            int start = 1;
            int end = 2;
            for (int j = 2; j < n; j++) {
                while (end < n) {
                    double slopeStart = sortedPointsByPoint[0].slopeTo(sortedPointsByPoint[start]);
                    double slopeEnd = sortedPointsByPoint[0].slopeTo(sortedPointsByPoint[end]);
                    if (Double.compare(slopeStart, slopeEnd) != 0) {
                        break;
                    }
                    end++;
                }
                if (end >= start + 3 && sortedPointsByPoint[0].compareTo(sortedPointsByPoint[start]) < 0) {
                    segments.add(new LineSegment(sortedPointsByPoint[0], sortedPointsByPoint[end - 1]));
                }
                start = end;
                end++;
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
                new Point(3, 4),
                new Point(3, 3),
                new Point(5, 5),
        };
        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
        StdOut.println(fastCollinearPoints.numberOfSegments());
    }
}
