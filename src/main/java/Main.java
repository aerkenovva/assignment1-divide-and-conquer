import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        int[] array = {
                12, 3, 5, 7, 4,
                19, 26, 1, 8, 15,
                10, 6, 13, 2, 9
        };

        int k = 5; // 0-based

        int[] check = array.clone();
        Arrays.sort(check);

        DeterministicSelector selector =
                new DeterministicSelector();

        int result = selector.select(array, k);

        System.out.println("Selected: " + result);
        System.out.println("Expected: " + check[k]);
        System.out.println(
                "Comparisons: " + selector.getComparisons()
        );
        System.out.println(
                "Max recursion depth: "
                        + selector.getMaxRecursionDepth()
        );
    }
}