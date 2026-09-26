import java.util.Arrays;
import java.util.Random;

public class AlgorithmTests {

    private static final Random RANDOM = new Random(42);

    public static void main(String[] args) {
        testSorting();
        testDeterministicSelect();
        testClosestPair();

        System.out.println("ALL TESTS PASSED");
    }

    private static void testSorting() {
        int[][] cases = {
                {},
                {5},
                {1, 2, 3, 4, 5},
                {5, 4, 3, 2, 1},
                {4, 2, 4, 1, 2, 4, 1},
                randomArray(1000)
        };

        for (int[] original : cases) {

            int[] expected = original.clone();
            Arrays.sort(expected);

            int[] merge = original.clone();
            new MergeSorter().sort(merge);

            if (!Arrays.equals(expected, merge)) {
                throw new AssertionError("MergeSort failed");
            }

            int[] quick = original.clone();
            new QuickSorter().sort(quick);

            if (!Arrays.equals(expected, quick)) {
                throw new AssertionError("QuickSort failed");
            }
        }

        System.out.println("Sorting tests passed");
    }

    private static void testDeterministicSelect() {

        for (int test = 0; test < 100; test++) {

            int[] array = randomArray(
                    RANDOM.nextInt(200) + 1
            );

            int[] sorted = array.clone();
            Arrays.sort(sorted);

            int k = RANDOM.nextInt(array.length);

            DeterministicSelector selector =
                    new DeterministicSelector();

            int actual =
                    selector.select(array.clone(), k);

            if (actual != sorted[k]) {
                throw new AssertionError(
                        "Deterministic Select failed"
                );
            }
        }

        System.out.println(
                "Deterministic Select: 100 tests passed"
        );
    }

    private static void testClosestPair() {

        for (int test = 0; test < 50; test++) {

            int n = RANDOM.nextInt(100) + 2;

            Point[] points = new Point[n];

            for (int i = 0; i < n; i++) {
                points[i] = new Point(
                        RANDOM.nextDouble() * 1000,
                        RANDOM.nextDouble() * 1000
                );
            }

            ClosestPairSolver solver =
                    new ClosestPairSolver();

            double fast =
                    solver.findClosestPair(points)
                            .getDistance();

            double brute =
                    bruteForce(points);

            if (Math.abs(fast - brute) > 1e-9) {
                throw new AssertionError(
                        "Closest Pair failed"
                );
            }
        }

        System.out.println("Closest Pair tests passed");
    }

    private static double bruteForce(Point[] points) {

        double best = Double.POSITIVE_INFINITY;

        for (int i = 0; i < points.length; i++) {

            for (int j = i + 1;
                 j < points.length;
                 j++) {

                double dx =
                        points[i].getX()
                                - points[j].getX();

                double dy =
                        points[i].getY()
                                - points[j].getY();

                double distance =
                        Math.sqrt(dx * dx + dy * dy);

                best = Math.min(best, distance);
            }
        }

        return best;
    }

    private static int[] randomArray(int n) {

        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = RANDOM.nextInt(1000);
        }

        return array;
    }
}