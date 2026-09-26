# Assignment 1: Divide-and-Conquer Algorithm Analysis

## A. Project Overview

This project implements and analyzes four divide-and-conquer algorithms in Java:

- Merge Sort
- Randomized Quick Sort
- Deterministic Select (Median of Medians)
- Closest Pair of Points

The project measures execution time, maximum recursion depth, and algorithm-specific operation counts.

Experimental results are stored in CSV format and compared with theoretical complexity.

---

## B. Algorithm Analysis

### 1. Merge Sort

Merge Sort recursively divides an array into two halves, sorts both halves, and combines them using a linear merge.

Implementation features:

- Linear merge
- Reusable auxiliary buffer
- Insertion Sort cutoff for small subarrays
- Comparison counting
- Maximum recursion-depth measurement

Recurrence:

`T(n) = 2T(n/2) + Θ(n)`

Using the Master Theorem:

`T(n) = Θ(n log n)`

Time complexity:

- Best: `Θ(n log n)`
- Average: `Θ(n log n)`
- Worst: `Θ(n log n)`

Space complexity:

`Θ(n)` auxiliary memory.

---

### 2. Randomized Quick Sort

Quick Sort selects a randomized pivot and partitions the array in place.

The implementation uses three-way partitioning, which is especially useful for duplicate-heavy inputs.

The algorithm recursively processes only the smaller partition and handles the larger partition iteratively.

General recurrence:

`T(n) = T(k) + T(n-k-1) + Θ(n)`

For reasonably balanced partitions:

`T(n) = Θ(n log n)`

Worst case:

`T(n) = T(n-1) + Θ(n) = Θ(n²)`

Time complexity:

- Expected: `Θ(n log n)`
- Worst: `O(n²)`

Typical recursion stack depth:

`O(log n)`

The smaller-first recursion strategy limits the recursive stack depth even when partitions are unbalanced.

---

### 3. Deterministic Select (Median of Medians)

Deterministic Select finds the k-th smallest element without sorting the entire array.

The implementation:

- Divides elements into groups of 5
- Sorts each group
- Finds the median of each group
- Recursively selects the median of medians
- Uses it as a pivot
- Partitions the array in place
- Recurses only into the partition containing the required element

Approximate recurrence:

`T(n) ≤ T(n/5) + T(7n/10) + Θ(n)`

Using divide-and-conquer and Akra-Bazzi intuition, the recursive subproblems shrink sufficiently while the partitioning work remains linear.

Therefore:

`T(n) = Θ(n)`

Worst-case time complexity:

`Θ(n)`

---

### 4. Closest Pair of Points

The Closest Pair algorithm finds the two points with the minimum Euclidean distance.

Steps:

1. Sort points by x-coordinate and y-coordinate.
2. Divide the points into left and right halves.
3. Recursively find the closest pair in both halves.
4. Choose the better recursive result.
5. Construct a strip around the dividing line.
6. Check possible closer pairs using y-order.

Recurrence:

`T(n) = 2T(n/2) + Θ(n)`

Using the Master Theorem:

`T(n) = Θ(n log n)`

This is asymptotically faster than the brute-force approach:

`Θ(n²)`

---

## C. Experimental Results

Experiments were performed using Java's `System.nanoTime()`.

### Input Sizes

Array algorithms were tested with:

- `n = 100`
- `n = 1,000`
- `n = 10,000`

Closest Pair was tested with:

- `n = 100`
- `n = 1,000`
- `n = 5,000`

### Sorting Input Types

Merge Sort and Quick Sort were tested on:

- Random input
- Sorted input
- Reverse-sorted input
- Duplicate-heavy input

### Measured Metrics

The experiments recorded:

- Execution time
- Maximum recursion depth
- Number of comparisons or distance comparisons

The complete experimental dataset is available in:

`results/results.csv`

### Selected Random-Input Results

| Algorithm | n | Time (ns) | Max Recursion Depth | Metric |
|---|---:|---:|---:|---:|
| MergeSort | 100 | 32,500 | 4 | 658 comparisons |
| MergeSort | 1,000 | 389,042 | 7 | 10,253 comparisons |
| MergeSort | 10,000 | 1,052,916 | 11 | 126,847 comparisons |
| QuickSort | 100 | 176,333 | 4 | 1,113 comparisons |
| QuickSort | 1,000 | 796,708 | 7 | 16,729 comparisons |
| QuickSort | 10,000 | 1,336,750 | 8 | 239,064 comparisons |
| Deterministic Select | 100 | 49,833 | 7 | 789 comparisons |
| Deterministic Select | 1,000 | 277,334 | 10 | 9,477 comparisons |
| Deterministic Select | 10,000 | 904,375 | 12 | 96,699 comparisons |
| Closest Pair | 100 | 3,485,209 | 7 | 136 distance comparisons |
| Closest Pair | 1,000 | 5,956,250 | 10 | 1,146 distance comparisons |
| Closest Pair | 5,000 | 14,903,166 | 12 | 6,419 distance comparisons |

### Execution Time vs Input Size

![Execution Time vs Input Size](docs/plots/time-vs-n.png)

### Recursion Depth vs Input Size

![Recursion Depth vs Input Size](docs/plots/recursion-depth-vs-n.png)

### Experimental Results Preview

![Experimental Results](docs/screenshots/results-preview.png)

---

## D. Discussion

### Do the results match theoretical complexity?

Overall, the experimental results are consistent with the expected theoretical behavior.

Merge Sort shows logarithmic growth in recursion depth while its operation count grows approximately according to `n log n`.

Quick Sort also maintains relatively low recursion depth because only the smaller partition is processed recursively.

Deterministic Select shows approximately linear growth in its number of comparisons as the input size increases.

Closest Pair performs significantly fewer distance comparisons than a brute-force `O(n²)` algorithm would require for large datasets.

Execution time does not perfectly follow theoretical complexity because practical measurements are affected by JVM behavior and the execution environment.

---

### How does input structure affect performance?

Merge Sort retains `Θ(n log n)` asymptotic complexity regardless of input ordering, although the number of comparisons and execution time may vary.

Randomized Quick Sort reduces dependence on the original ordering because the pivot is selected randomly.

Duplicate-heavy inputs perform particularly well with three-way partitioning because values equal to the pivot are grouped together and do not need to be processed again in recursive partitions.

---

### Why does smaller-first recursion help QuickSort?

After partitioning, the implementation recursively processes the smaller partition and handles the larger partition iteratively.

The smaller recursive partition can contain at most about half of the current elements.

Therefore, the recursion stack remains bounded by approximately:

`O(log n)`

This reduces stack usage and the risk of `StackOverflowError` on the JVM.

---

### Why does Median of Medians guarantee O(n)?

Median of Medians chooses a pivot that guarantees that a significant fraction of elements can be discarded after each partition.

The recurrence is:

`T(n) ≤ T(n/5) + T(7n/10) + Θ(n)`

The recursive subproblems shrink sufficiently while the remaining work is linear.

Therefore:

`T(n) = Θ(n)`

in the worst case.

---

### Why is divide-and-conquer Closest Pair faster than O(n²)?

A brute-force algorithm compares every possible pair of points and therefore requires:

`Θ(n²)`

time.

The divide-and-conquer solution recursively solves smaller problems and only checks points inside a narrow strip near the dividing line.

This reduces the total running time to:

`Θ(n log n)`

which is much more efficient for large datasets.

---

### Practical Performance Factors

Real execution time can be influenced by:

- JVM warm-up
- JIT compilation
- Garbage collection
- CPU cache behavior
- Memory allocation
- Branch prediction
- Background operating-system activity
- Random pivot selection in Quick Sort

For this reason, individual execution-time measurements may vary between program runs even when the algorithm and input size remain the same.

---

## E. Reflection

This assignment helped me understand how divide-and-conquer algorithms behave both theoretically and practically. Implementing the algorithms made recurrence relations, recursive decomposition, and recursion depth easier to understand because I could observe how problem sizes decrease during execution.

One of the main challenges was implementing the algorithms while satisfying both correctness and performance requirements. In particular, smaller-first recursion in Quick Sort, Median-of-Medians pivot selection, and the strip step in Closest Pair required careful implementation. The experiments also demonstrated that practical JVM execution time can vary even when the theoretical complexity remains unchanged.

---

## F. Testing

### Merge Sort and Quick Sort

Both sorting algorithms were compared against Java's `Arrays.sort()`.

The test cases included:

- Random arrays
- Sorted arrays
- Reverse-sorted arrays
- Duplicate-heavy arrays
- Empty arrays
- Single-element arrays

### Deterministic Select

Deterministic Select was verified using 100 random tests.

For every test, the returned result was compared with the element at index `k` in a sorted copy of the same array.

### Closest Pair

The divide-and-conquer Closest Pair implementation was compared with an `O(n²)` brute-force solution on small random datasets.

All implemented correctness tests passed.

### Test Results

![Test Results](docs/screenshots/test-results.png)

### Program Output

![Program Output](docs/screenshots/program-output.png)

---

## Project Structure

```text
assignment1-divide-and-conquer/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── MergeSorter.java
│   │       ├── QuickSorter.java
│   │       ├── DeterministicSelector.java
│   │       ├── ClosestPairSolver.java
│   │       ├── Experiment.java
│   │       ├── Point.java
│   │       └── Main.java
│   └── test/
│       └── java/
│           └── AlgorithmTests.java
├── docs/
│   ├── screenshots/
│   │   ├── program-output.png
│   │   ├── results-preview.png
│   │   └── test-results.png
│   └── plots/
│       ├── time-vs-n.png
│       └── recursion-depth-vs-n.png
├── results/
│   └── results.csv
├── README.md
├── pom.xml
└── .gitignore
```

---

## Technologies

- Java 17
- Maven
- IntelliJ IDEA
- Git
- GitHub

---

## AI Usage Disclosure

AI assistance was used for explanations, debugging guidance, code review, and documentation support. All implementations were tested and reviewed as part of the assignment work.