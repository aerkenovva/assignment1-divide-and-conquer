public class Main {

    public static void main(String[] args) {

        Point[] points = {
                new Point(2, 3),
                new Point(12, 30),
                new Point(40, 50),
                new Point(5, 1),
                new Point(12, 10),
                new Point(3, 4)
        };

        ClosestPairSolver solver =
                new ClosestPairSolver();

        ClosestPairSolver.Result result =
                solver.findClosestPair(points);

        System.out.println(
                "Point 1: " + result.getFirst()
        );

        System.out.println(
                "Point 2: " + result.getSecond()
        );

        System.out.println(
                "Distance: " + result.getDistance()
        );

        System.out.println(
                "Distance comparisons: "
                        + solver.getDistanceComparisons()
        );

        System.out.println(
                "Max recursion depth: "
                        + solver.getMaxRecursionDepth()
        );
    }
}