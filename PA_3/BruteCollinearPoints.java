import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int numberOfSegments;
    private ArrayList<LineSegment> segments;

    // Finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if (points == null)
            throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {

            if (points[i] == null)
                throw new IllegalArgumentException();

        }

        Point[] copy = points.clone();
        Arrays.sort(copy);

        for (int i = 1; i < copy.length; i++) {
            if (copy[i].compareTo(copy[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        double slope1 = 0;
        double slope2 = 0;
        double slope3 = 0;

        segments = new ArrayList<>();
        numberOfSegments = 0;

        for (int i = 0; i < points.length; i++) {

            for (int j = i + 1; j < points.length; j++) {

                slope1 = points[i].slopeTo(points[j]);

                for (int k = j + 1; k < points.length; k++) {

                    slope2 = points[i].slopeTo(points[k]);

                    for (int l = k + 1; l < points.length; l++) {

                        slope3 = points[i].slopeTo(points[l]);

                        Point a = points[i];

                        Point b = points[j];

                        Point c = points[k];

                        Point d = points[l];

                        Point[] sortedArray = { a, b, c, d };

                        if (slope1 == slope2 && slope2 == slope3) {

                            Arrays.sort(sortedArray);
                            LineSegment newSegment = new LineSegment(sortedArray[0], sortedArray[3]);

                            if (!segments.contains(newSegment)) {
                                segments.add(newSegment);
                                numberOfSegments++;

                            }

                        }

                    }
                }

            }

        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {

        LineSegment[] segmentsArray = new LineSegment[segments.size()];

        for (int i = 0; i < segments.size(); i++) {
            segmentsArray[i] = segments.get(i);
        }

        return segmentsArray;
    }
}
