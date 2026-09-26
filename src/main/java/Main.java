import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        int[] array = {
                20, 5, 17, 3, 12,
                8, 1, 19, 6, 14,
                2, 10, 18, 7, 13,
                4, 16, 9, 15, 11
        };

        MergeSorter sorter = new MergeSorter();

        System.out.println("Before:");
        System.out.println(Arrays.toString(array));

        sorter.sort(array);

        System.out.println("After:");
        System.out.println(Arrays.toString(array));

        System.out.println(
                "Comparisons: " + sorter.getComparisons()
        );

        System.out.println(
                "Max recursion depth: "
                        + sorter.getMaxRecursionDepth()
        );
    }
}