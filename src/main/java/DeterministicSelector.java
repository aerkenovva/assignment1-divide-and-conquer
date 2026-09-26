public class DeterministicSelector {

    private long comparisons;
    private int maxRecursionDepth;

    public int select(int[] array, int k) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }

        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("Invalid k");
        }

        comparisons = 0;
        maxRecursionDepth = 0;

        return select(array, 0, array.length - 1, k, 1);
    }

    private int select(
            int[] array,
            int left,
            int right,
            int k,
            int depth
    ) {
        maxRecursionDepth = Math.max(maxRecursionDepth, depth);

        if (left == right) {
            return array[left];
        }

        int pivot = choosePivot(array, left, right, depth);

        int[] bounds = partition(array, left, right, pivot);

        int lt = bounds[0];
        int gt = bounds[1];

        if (k < lt) {
            return select(array, left, lt - 1, k, depth + 1);
        } else if (k > gt) {
            return select(array, gt + 1, right, k, depth + 1);
        } else {
            return pivot;
        }
    }

    private int choosePivot(
            int[] array,
            int left,
            int right,
            int depth
    ) {
        int n = right - left + 1;

        if (n <= 5) {
            insertionSort(array, left, right);
            return array[left + n / 2];
        }

        int medianCount = 0;

        for (int i = left; i <= right; i += 5) {
            int groupRight = Math.min(i + 4, right);

            insertionSort(array, i, groupRight);

            int medianIndex = i + (groupRight - i) / 2;

            swap(array, left + medianCount, medianIndex);
            medianCount++;
        }

        int medianK = left + medianCount / 2;

        return select(
                array,
                left,
                left + medianCount - 1,
                medianK,
                depth + 1
        );
    }

    private int[] partition(
            int[] array,
            int left,
            int right,
            int pivot
    ) {
        int lt = left;
        int i = left;
        int gt = right;

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

        return new int[]{lt, gt};
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