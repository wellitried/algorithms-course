package collinear.points;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class FastCollinearPoints {

    private static final int MIN_POINTS_IN_LINE_SEGMENT = 4;

    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null || pointsContainNull(points) || pointsAreNotDistinct(points)) {
            throw new IllegalArgumentException();
        }
        this.segments = findSegments(points);
    }

    private boolean pointsContainNull(Point[] points) {
        return Arrays.stream(points).anyMatch(Objects::isNull);
    }

    private boolean pointsAreNotDistinct(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            Point p1 = points[i];
            if (pointIsDuplicated(points, i, p1)) {
                return true;
            }
        }
        return false;
    }

    private boolean pointIsDuplicated(Point[] points, int i, Point p1) {
        for (int j = i + 1; j < points.length; j++) {
            Point p2 = points[j];
            if (p1.compareTo(p2) == 0) {
                return true;
            }
        }
        return false;
    }

    private LineSegment[] findSegments(Point[] points) {
        LineSegment[] emptyArray = new LineSegment[0];
        if (points.length < MIN_POINTS_IN_LINE_SEGMENT) {
            return emptyArray;
        }
        ArrayList<LineSegment> lines = new ArrayList<>();
        for (int i = 0; i < points.length - 1; i++) {
            Point point = points[i];
            Point[] sorted = getSortedPoints(points, i, point);
            ArrayList<Point> collinearPoints = new ArrayList<>();
            double previousSlope = point.slopeTo(sorted[0]);
            for (Point s : sorted) {
                double slope = point.slopeTo(s);
                if (slope == previousSlope) {
                    collinearPoints.add(s);
                } else {
                    addSegmentIfItExists(lines, point, collinearPoints);
                    collinearPoints.clear();
                }
                previousSlope = slope;
            }
            addSegmentIfItExists(lines, point, collinearPoints);
        }
        return lines.toArray(emptyArray);
    }

    private Point[] getSortedPoints(
            Point[] points,
            int currentIndex,
            Point point
    ) {
        Point[] qs = Arrays.copyOfRange(points, currentIndex + 1, points.length);
        return sortBySlope(qs, point);
    }

    private Point[] sortBySlope(
            Point[] points,
            Point point
    ) {
        return Arrays.stream(points)
                .sorted(Comparator.comparingDouble(point::slopeTo))
                .toArray(Point[]::new);
    }

    private void addSegmentIfItExists(
            ArrayList<LineSegment> lines,
            Point point,
            ArrayList<Point> collinearPoints
    ) {
        if (collinearPoints.size() >= MIN_POINTS_IN_LINE_SEGMENT - 1) {
            collinearPoints.add(point);
            LineSegment segment = findLongestSegment(collinearPoints);
            lines.add(segment);
        }
    }

    private LineSegment findLongestSegment(ArrayList<Point> collinearPoints) {
        Point min = collinearPoints.stream().min(Point::compareTo).orElse(null);
        Point max = collinearPoints.stream().max(Point::compareTo).orElse(null);
        return new LineSegment(min, max);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }
}