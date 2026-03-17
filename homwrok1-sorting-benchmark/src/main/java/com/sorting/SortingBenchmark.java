package com.sorting;

import org.openjdk.jmh.annotations.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@State(Scope.Benchmark)
public class SortingBenchmark {

    @Param({"RANDOM", "SORTED", "REVERSE", "NEARLY_SORTED"})
    public String distribution;

    private int[] data;

    @Setup(Level.Trial)
    public void setup() {
        data = DataGenerator.generate(distribution, 1_000_000);
    }

    @Benchmark
    public int[] quickSort() {
        int[] copy = data.clone();
        QuickSort.sort(copy);
        return copy;
    }

    @Benchmark
    public int[] radixSort() {
        int[] copy = data.clone();
        RadixSort.sort(copy);
        return copy;
    }

    @Benchmark
    public int[] arraysSort() {
        int[] copy = data.clone();
        Arrays.sort(copy);
        return copy;
    }
}
