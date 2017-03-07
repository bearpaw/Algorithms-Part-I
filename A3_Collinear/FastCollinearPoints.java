import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private double epsilon = 0.00000001;
    private ArrayList<LineSegment> lineSeg = new ArrayList<>();
    private HashMap<Double, ArrayList<Point>> addedLineSeg = new HashMap<>();

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        Point[] pointsCopy = Arrays.copyOf(points, points.length);

        checkpoints(points);
        if (points.length >= 4) {
            for (int p = 0; p < points.length; ++p) {
                // compute and sort slopes
                Arrays.sort(pointsCopy, points[p].slopeOrder());

                double oldSlope = Double.NEGATIVE_INFINITY; // itself
                double curSlope = Double.NEGATIVE_INFINITY;
                ArrayList<Point> lineSegPoints = new ArrayList<>();
                int firstSameSlopePtr = 1;
                lineSegPoints.add(points[p]);
                for (int q = 1; q < pointsCopy.length; ++q) {
                    curSlope = points[p].slopeTo(pointsCopy[q]);
                    if (curSlope == oldSlope || Math.abs(curSlope - oldSlope) < epsilon) {
                        lineSegPoints.add(pointsCopy[q]);
                    } else {
                        // check if we can add a new segment
                        lineSegPoints.add(pointsCopy[firstSameSlopePtr]);
                        if (lineSegPoints.size() > 3) {
                            createLineSeg(oldSlope, lineSegPoints);
                        }
                        // clear state
                        lineSegPoints.clear();
                        lineSegPoints.add(points[p]);
                        firstSameSlopePtr = q;
                    }
                    // System.out.format("%f %d\n", curSlope,
                    // lineSegPoints.size());
                    oldSlope = curSlope;
                }

                // System.out.println("---------------");
                lineSegPoints.add(pointsCopy[firstSameSlopePtr]);
                if (lineSegPoints.size() > 3)
                    createLineSeg(curSlope, lineSegPoints);
            }
        }
    }

    private boolean createLineSeg(double slope, ArrayList<Point> lineSegPoints) {
        // get already added points for this slope
        ArrayList<Point> addedPoints = addedLineSeg.get(slope);

        // sort line segment points to eliminate sub-linesegs
        Collections.sort(lineSegPoints);
        Point a = lineSegPoints.get(0);
        Point b = lineSegPoints.get(lineSegPoints.size() - 1);

        // add if not exists
        if (addedPoints == null) {
            lineSeg.add(new LineSegment(a, b));
            // save to already added linesegs
            addedPoints = new ArrayList<>();
            addedPoints.add(b);
            addedLineSeg.put(slope, addedPoints);
        } else {
            for (Point p : addedPoints) {
                if (p.compareTo(b) == 0)
                    return false;
            }

            lineSeg.add(new LineSegment(a, b));
            // save to already added linesegs
            addedPoints.add(b);
            addedLineSeg.put(slope, addedPoints);
        }

        return true;
    }

    public int numberOfSegments() {
        // the number of line segments
        return lineSeg.size();
    }

    public LineSegment[] segments() {
        // the line segments
        return lineSeg.toArray(new LineSegment[lineSeg.size()]);
    }

    private void checkpoints(Point[] points) {
        for (int i = 0; i < points.length - 1; ++i)
            for (int j = i + 1; j < points.length; ++j) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Repeated points detected.");
            }
    }

    public static void main(String[] args) {
        System.out.println(Double.POSITIVE_INFINITY == Double.POSITIVE_INFINITY);

        // read the n points from a file
        In in = new In("collinear-testing/collinear/vertical25.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // Brute force
        StdOut.println("BruteCollinearPoints");
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }

        StdOut.println(collinear.numberOfSegments());

        // print and draw the line segments
        StdOut.println("\nFastCollinearPoints");
        FastCollinearPoints collinear2 = new FastCollinearPoints(points);
        for (LineSegment segment : collinear2.segments()) {
            StdOut.println(segment);
        }

        StdOut.println(collinear2.numberOfSegments());
    }
}