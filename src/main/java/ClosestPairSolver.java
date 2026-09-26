import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

public class ClosestPairSolver {

    private long distanceComparisons;
    private int maxRecursionDepth;

    public Result findClosestPair(Point[] points) {

        if (points == null || points.length < 2) {
            throw new IllegalArgumentException(
                    "At least two points are required"
            );
        }

        distanceComparisons = 0;
        maxRecursionDepth = 0;

        Point[] byX = points.clone();
        Point[] byY = points.clone();

        Arrays.sort(
                byX,
                Comparator.comparingDouble(Point::getX)
                        .thenComparingDouble(Point::getY)
        );

        Arrays.sort(
                byY,
                Comparator.comparingDouble(Point::getY)
                        .thenComparingDouble(Point::getX)
        );

        return solve(byX, byY, 1);
    }

    private Result solve(
            Point[] byX,
            Point[] byY,
            int depth
    ) {
        maxRecursionDepth =
                Math.max(maxRecursionDepth, depth);

        int n = byX.length;

        if (n <= 3) {
            return bruteForce(byX);
        }

        int middle = n / 2;

        Point[] leftX =
                Arrays.copyOfRange(byX, 0, middle);

        Point[] rightX =
                Arrays.copyOfRange(byX, middle, n);

        Set<Point> leftSet =
                Collections.newSetFromMap(
                        new IdentityHashMap<>()
                );

        Collections.addAll(leftSet, leftX);

        Point[] leftY = new Point[leftX.length];
        Point[] rightY = new Point[rightX.length];

        int li = 0;
        int ri = 0;

        for (Point point : byY) {
            if (leftSet.contains(point)) {
                leftY[li++] = point;
            } else {
                rightY[ri++] = point;
            }
        }

        Result leftResult =
                solve(leftX, leftY, depth + 1);

        Result rightResult =
                solve(rightX, rightY, depth + 1);

        Result best =
                leftResult.distanceSquared
                        <= rightResult.distanceSquared
                        ? leftResult
                        : rightResult;

        double middleX = byX[middle].getX();

        List<Point> strip = new ArrayList<>();

        for (Point point : byY) {
            double dx = point.getX() - middleX;

            if (dx * dx < best.distanceSquared) {
                strip.add(point);
            }
        }

        for (int i = 0; i < strip.size(); i++) {

            for (int j = i + 1;
                 j < strip.size();
                 j++) {

                double dy =
                        strip.get(j).getY()
                                - strip.get(i).getY();

                if (dy * dy >= best.distanceSquared) {
                    break;
                }

                double distance =
                        distanceSquared(
                                strip.get(i),
                                strip.get(j)
                        );

                if (distance < best.distanceSquared) {
                    best = new Result(
                            strip.get(i),
                            strip.get(j),
                            distance
                    );
                }
            }
        }

        return best;
    }

    private Result bruteForce(Point[] points) {

        Result best =
                new Result(
                        null,
                        null,
                        Double.POSITIVE_INFINITY
                );

        for (int i = 0; i < points.length; i++) {

            for (int j = i + 1;
                 j < points.length;
                 j++) {

                double distance =
                        distanceSquared(
                                points[i],
                                points[j]
                        );

                if (distance < best.distanceSquared) {
                    best = new Result(
                            points[i],
                            points[j],
                            distance
                    );
                }
            }
        }

        return best;
    }

    private double distanceSquared(
            Point a,
            Point b
    ) {
        distanceComparisons++;

        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();

        return dx * dx + dy * dy;
    }

    public long getDistanceComparisons() {
        return distanceComparisons;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public static class Result {

        private final Point first;
        private final Point second;
        private final double distanceSquared;

        public Result(
                Point first,
                Point second,
                double distanceSquared
        ) {
            this.first = first;
            this.second = second;
            this.distanceSquared =
                    distanceSquared;
        }

        public Point getFirst() {
            return first;
        }

        public Point getSecond() {
            return second;
        }

        public double getDistance() {
            return Math.sqrt(distanceSquared);
        }
    }
}
