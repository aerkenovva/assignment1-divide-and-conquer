public class MergeSorter {

    private static final int INSERTION_SORT_THRESHOLD = 16;

    private long comparisons;
    private int maxRecursionDepth;

    public void sort(int[] array) {
        comparisons = 0;
        maxRecursionDepth = 0;

        if (array == null || array.length < 2) {
            return;
        }

        // One auxiliary array is created once
        // and reused during the whole MergeSort.
        int[] buffer = new int[array.length];

        mergeSort(array, buffer, 0, array.length - 1, 1);
    }

    private void mergeSort(
            int[] array,
            int[] buffer,
            int left,
            int right,
            int depth
    ) {
        maxRecursionDepth = Math.max(maxRecursionDepth, depth);

        int size = right - left + 1;

        // Small-input cutoff
        if (size <= INSERTION_SORT_THRESHOLD) {
            insertionSort(array, left, right);
            return;
        }

        int middle = left + (right - left) / 2;

        mergeSort(array, buffer, left, middle, depth + 1);
        mergeSort(array, buffer, middle + 1, right, depth + 1);

        merge(array, buffer, left, middle, right);
    }

    private void merge(
            int[] array,
            int[] buffer,
            int left,
            int middle,
            int right
    ) {
        for (int i = left; i <= right; i++) {
            buffer[i] = array[i];
        }

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            comparisons++;

            if (buffer[i] <= buffer[j]) {
                array[k++] = buffer[i++];
            } else {
                array[k++] = buffer[j++];
            }
        }

        while (i <= middle) {
            array[k++] = buffer[i++];
        }

        while (j <= right) {
            array[k++] = buffer[j++];
        }
    }

    private void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= left) {
                comparisons++;

                if (array[j] <= key) {
                    break;
                }

                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }
}
