package collinear.points;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null || Arrays.stream(points).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            Point p1 = points[i];
            for (int j = 0; j < points.length; j++) {
                Point p2 = points[j];
                if (i != j && p1.compareTo(p2) == 0) throw new IllegalArgumentException();
            }
        }
        this.segments = findSegments(points);
    }

    private LineSegment[] findSegments(Point[] points) {
        LineSegment[] empty = new LineSegment[0];
        if (points.length < 4) {
            return empty;
        }
        ArrayList<LineSegment> lines = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            Point p1 = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point p2 = points[j];
                for (int m = j + 1; m < points.length; m++) {
                    Point p3 = points[m];
                    for (int n = m + 1; n < points.length; n++) {
                        Point p4 = points[n];

                        double s1 = p1.slopeTo(p2);
                        double s2 = p2.slopeTo(p3);
                        double s3 = p3.slopeTo(p4);
                        boolean areCollinear = s1 != Double.NEGATIVE_INFINITY && s1 == s2 && s2 == s3;
                        if (areCollinear) {
                            Point[] p = {p1, p2, p3, p4};
                            Point min = Arrays.stream(p).min(Point::compareTo).orElse(null);
                            Point max = Arrays.stream(p).max(Point::compareTo).orElse(null);
                            lines.add(new LineSegment(min, max));
                        }
                    }
                }
            }
        }

        return lines.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }
}