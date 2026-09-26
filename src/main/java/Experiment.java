import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

public class Experiment {

    private static final Random RANDOM = new Random(42);

    public void runAll() throws IOException {

        Files.createDirectories(Path.of("results"));

        try (BufferedWriter writer =
                     Files.newBufferedWriter(
                             Path.of("results/results.csv")
                     )) {

            writer.write(
                    "algorithm,inputType,n,timeNs,recursionDepth,metricName,metricValue"
            );
            writer.newLine();

            int[] sizes = {100, 1000, 10000};

            for (int n : sizes) {

                runSortingExperiments(writer, n);
                runSelectionExperiment(writer, n);
            }

            int[] pointSizes = {100, 1000, 5000};

            for (int n : pointSizes) {
                runClosestPairExperiment(writer, n);
            }
        }

        System.out.println(
                "Experiments finished. Results saved to results/results.csv"
        );
    }

    private void runSortingExperiments(
            BufferedWriter writer,
            int n
    ) throws IOException {

        String[] types = {
                "random",
                "sorted",
                "reverse",
                "duplicates"
        };

        for (String type : types) {

            int[] original = generateArray(n, type);

            runMergeSort(writer, original, type);
            runQuickSort(writer, original, type);
        }
    }

    private void runMergeSort(
            BufferedWriter writer,
            int[] original,
            String type
    ) throws IOException {

        int[] array = original.clone();

        MergeSorter sorter = new MergeSorter();

        long start = System.nanoTime();

        sorter.sort(array);

        long end = System.nanoTime();

        writer.write(
                "MergeSort,"
                        + type + ","
                        + array.length + ","
                        + (end - start) + ","
                        + sorter.getMaxRecursionDepth() + ","
                        + "comparisons,"
                        + sorter.getComparisons()
        );

        writer.newLine();
    }

    private void runQuickSort(
            BufferedWriter writer,
            int[] original,
            String type
    ) throws IOException {

        int[] array = original.clone();

        QuickSorter sorter = new QuickSorter();

        long start = System.nanoTime();

        sorter.sort(array);

        long end = System.nanoTime();

        writer.write(
                "QuickSort,"
                        + type + ","
                        + array.length + ","
                        + (end - start) + ","
                        + sorter.getMaxRecursionDepth() + ","
                        + "comparisons,"
                        + sorter.getComparisons()
        );

        writer.newLine();
    }

    private void runSelectionExperiment(
            BufferedWriter writer,
            int n
    ) throws IOException {

        int[] array = generateArray(n, "random");

        DeterministicSelector selector =
                new DeterministicSelector();

        int k = n / 2;

        long start = System.nanoTime();

        selector.select(array, k);

        long end = System.nanoTime();

        writer.write(
                "DeterministicSelect,"
                        + "random,"
                        + n + ","
                        + (end - start) + ","
                        + selector.getMaxRecursionDepth() + ","
                        + "comparisons,"
                        + selector.getComparisons()
        );

        writer.newLine();
    }

    private void runClosestPairExperiment(
            BufferedWriter writer,
            int n
    ) throws IOException {

        Point[] points = generatePoints(n);

        ClosestPairSolver solver =
                new ClosestPairSolver();

        long start = System.nanoTime();

        solver.findClosestPair(points);

        long end = System.nanoTime();

        writer.write(
                "ClosestPair,"
                        + "random,"
                        + n + ","
                        + (end - start) + ","
                        + solver.getMaxRecursionDepth() + ","
                        + "distanceComparisons,"
                        + solver.getDistanceComparisons()
        );

        writer.newLine();
    }

    private int[] generateArray(
            int n,
            String type
    ) {

        int[] array = new int[n];

        switch (type) {

            case "random":
                for (int i = 0; i < n; i++) {
                    array[i] =
                            RANDOM.nextInt(n * 10 + 1);
                }
                break;

            case "sorted":
                for (int i = 0; i < n; i++) {
                    array[i] = i;
                }
                break;

            case "reverse":
                for (int i = 0; i < n; i++) {
                    array[i] = n - i;
                }
                break;

            case "duplicates":
                for (int i = 0; i < n; i++) {
                    array[i] =
                            RANDOM.nextInt(10);
                }
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown input type: " + type
                );
        }

        return array;
    }

    private Point[] generatePoints(int n) {

        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {

            points[i] = new Point(
                    RANDOM.nextDouble() * 10000,
                    RANDOM.nextDouble() * 10000
            );
        }

        return points;
    }
}