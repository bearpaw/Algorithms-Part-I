import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private double eps = 0.00000001;
    private ArrayList<LineSegment> lineSeg = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        checkpoints(points);

        // Copy points for judge system
        Point[] pointsCopy = Arrays.copyOf(points, points.length);

        // sort points
        Arrays.sort(pointsCopy);

        double slope1, slope2, slope3;
        for (int p = 0; p < pointsCopy.length - 3; ++p)
            for (int q = p + 1; q < pointsCopy.length - 2; ++q)
                for (int r = q + 1; r < pointsCopy.length - 1; ++r)
                    for (int s = r + 1; s < pointsCopy.length; ++s) {
                        slope1 = pointsCopy[p].slopeTo(pointsCopy[q]);
                        slope2 = pointsCopy[p].slopeTo(pointsCopy[r]);
                        slope3 = pointsCopy[p].slopeTo(pointsCopy[s]);
                        if (Math.abs(slope1 - slope2) < eps && Math.abs(slope1 - slope3) < eps) {
                            lineSeg.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        } else if (slope1 == Double.POSITIVE_INFINITY && slope2 == Double.POSITIVE_INFINITY
                                && slope3 == Double.POSITIVE_INFINITY) { // Vertical
                                                                         // lines
                            lineSeg.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
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
}
