# Summary

This repository meets the Assignment 1 requirements. It contains a Maven-based Java submission with real `JMH` benchmarks, all required sorting algorithms, all required input distributions, explicit correctness verification, and a PDF analytical report.

The benchmark scope is handled in a way that matches the Assignment 1 clarification: the scalable algorithms are benchmarked on `1_000_000` integers in [`homwrok1-sorting-benchmark/src/main/java/com/sorting/SortingBenchmark.java`](homwrok1-sorting-benchmark/src/main/java/com/sorting/SortingBenchmark.java), while Bubble Sort is benchmarked separately on `100_000` integers in [`homwrok1-sorting-benchmark/src/main/java/com/sorting/BubbleSortBenchmark.java`](homwrok1-sorting-benchmark/src/main/java/com/sorting/BubbleSortBenchmark.java), and that split is documented in [`homwrok1-sorting-benchmark/sorting-benchmark-report.pdf`](homwrok1-sorting-benchmark/sorting-benchmark-report.pdf).

# Strengths

- Proper `JMH` setup is present in [`homwrok1-sorting-benchmark/pom.xml`](homwrok1-sorting-benchmark/pom.xml).
- Separate benchmark methods are present for `QuickSort`, `RadixSort`, and `Arrays.sort(int[])` in [`homwrok1-sorting-benchmark/src/main/java/com/sorting/SortingBenchmark.java`](homwrok1-sorting-benchmark/src/main/java/com/sorting/SortingBenchmark.java).
- Bubble Sort is benchmarked separately, with the reduced size documented rather than hidden, in [`homwrok1-sorting-benchmark/src/main/java/com/sorting/BubbleSortBenchmark.java`](homwrok1-sorting-benchmark/src/main/java/com/sorting/BubbleSortBenchmark.java).
- Bubble Sort includes early exit in [`homwrok1-sorting-benchmark/src/main/java/com/sorting/BubbleSort.java`](homwrok1-sorting-benchmark/src/main/java/com/sorting/BubbleSort.java).
- Quick Sort is in-place and its median-of-three pivot strategy is documented directly in [`homwrok1-sorting-benchmark/src/main/java/com/sorting/QuickSort.java`](homwrok1-sorting-benchmark/src/main/java/com/sorting/QuickSort.java).
- Radix Sort matches the required byte-oriented `LSD` design with `4` passes, base `256`, and signed `int` handling on the final pass in [`homwrok1-sorting-benchmark/src/main/java/com/sorting/RadixSort.java`](homwrok1-sorting-benchmark/src/main/java/com/sorting/RadixSort.java).
- All required distributions are implemented, including a nearly-sorted generator based on about `1%` random swaps, in [`homwrok1-sorting-benchmark/src/main/java/com/sorting/DataGenerator.java`](homwrok1-sorting-benchmark/src/main/java/com/sorting/DataGenerator.java).
- Correctness verification compares against `Arrays.sort()` and checks sortedness in [`homwrok1-sorting-benchmark/src/main/java/com/sorting/SortVerifier.java`](homwrok1-sorting-benchmark/src/main/java/com/sorting/SortVerifier.java).
- Correctness verification is visibly exercisable across all required distributions in [`homwrok1-sorting-benchmark/src/main/java/com/sorting/CorrectnessTest.java`](homwrok1-sorting-benchmark/src/main/java/com/sorting/CorrectnessTest.java); a local execution completed successfully with all distributions passing.
- A relevant analytical report in PDF form is present in [`homwrok1-sorting-benchmark/sorting-benchmark-report.pdf`](homwrok1-sorting-benchmark/sorting-benchmark-report.pdf).

# Findings

No material assignment defects were identified.

# Requirement Checklist

- Java source code present: Pass
- JMH benchmark classes present: Pass
- PDF analytical report present: Pass
- Bubble Sort with early exit: Pass
- in-place Quick Sort: Pass
- Quick Sort pivot strategy documented: Pass
- LSD Radix Sort implemented: Pass
- Radix Sort uses 4 passes / base 256: Pass
- Radix Sort supports negative numbers: Pass
- `Arrays.sort(int[])` benchmark present: Pass
- `Arrays.sort(int[])` used as correctness reference: Pass
- uniform random dataset: Pass
- ascending sorted dataset: Pass
- descending sorted dataset: Pass
- nearly sorted dataset with about `1%` swaps: Pass
- arrays of `1,000,000` integers where required: Pass
- separate benchmark method per algorithm: Pass
- warmup iterations within required range: Pass
- measurement iterations within required range: Pass
- correctness check against `Arrays.sort()`: Pass
- sortedness verification: Pass

# Verdict

`Meets requirements`

Reasoning: the repository satisfies the required benchmark architecture, algorithm set, dataset coverage, correctness verification, and report deliverable. The Bubble Sort size exception is handled transparently and consistently with the Assignment 1 evaluation clarification rather than being used to hide missing benchmark scope.
