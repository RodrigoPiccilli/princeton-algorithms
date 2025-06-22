import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Input is null");

        Point[] copy = points.clone();
        for (Point p : copy)
            if (p == null)
                throw new IllegalArgumentException("Null point found");

        Arrays.sort(copy);
        for (int i = 1; i < copy.length; i++)
            if (copy[i].compareTo(copy[i - 1]) == 0)
                throw new IllegalArgumentException("Duplicate points");

        int n = copy.length;

        for (int i = 0; i < n; i++) {
            Point origin = copy[i];
            Point[] sorted = copy.clone();
            Arrays.sort(sorted, origin.slopeOrder());

            int j = 1;
            while (j < n) {
                ArrayList<Point> collinear = new ArrayList<>();
                double slopeRef = origin.slopeTo(sorted[j]);

                do {
                    collinear.add(sorted[j++]);
                } while (j < n && origin.slopeTo(sorted[j]) == slopeRef);

                if (collinear.size() >= 3) {
                    collinear.add(origin);
                    Point[] line = collinear.toArray(new Point[0]);
                    Arrays.sort(line);

                    // Only add if origin is the smallest point to avoid duplicates
                    if (origin.compareTo(line[0]) == 0) {
                        LineSegment seg = new LineSegment(line[0], line[line.length - 1]);

                        // Explicitly check for duplicates in the list
                        if (!segments.contains(seg)) {
                            segments.add(seg);
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

        LineSegment[] segmentArray = new LineSegment[segments.size()];

        for (int i = 0; i < segments.size(); i++) {
            segmentArray[i] = segments.get(i);
        }

        return segmentArray;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // // draw the points
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        //     segment.draw();
        }
        // StdDraw.show();
    }
}