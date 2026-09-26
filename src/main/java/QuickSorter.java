import java.util.concurrent.ThreadLocalRandom;

public class QuickSorter {

    private long comparisons;
    private int maxRecursionDepth;

    public void sort(int[] array) {
        comparisons = 0;
        maxRecursionDepth = 0;

        if (array == null || array.length < 2) {
            return;
        }

        quickSort(array, 0, array.length - 1, 1);
    }

    private void quickSort(int[] array, int left, int right, int depth) {
        maxRecursionDepth = Math.max(maxRecursionDepth, depth);

        while (left < right) {

            int pivotIndex =
                    ThreadLocalRandom.current().nextInt(left, right + 1);

            int pivot = array[pivotIndex];

            int lt = left;
            int i = left;
            int gt = right;

            // In-place 3-way partition
            while (i <= gt) {
                comparisons++;

                if (array[i] < pivot) {
                    swap(array, lt, i);
                    lt++;
                    i++;
                } else {
                    comparisons++;

                    if (array[i] > pivot) {
                        swap(array, i, gt);
                        gt--;
                    } else {
                        i++;
                    }
                }
            }

            int leftSize = lt - left;
            int rightSize = right - gt;

            // Recurse on smaller partition
            if (leftSize < rightSize) {

                if (left < lt - 1) {
                    quickSort(array, left, lt - 1, depth + 1);
                }

                left = gt + 1;

            } else {

                if (gt + 1 < right) {
                    quickSort(array, gt + 1, right, depth + 1);
                }

                right = lt - 1;
            }
        }
    }

    private void swap(int[] array, int i, int j) {
        if (i != j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }
}