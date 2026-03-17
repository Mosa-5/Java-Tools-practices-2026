package com.sorting;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@State(Scope.Benchmark)
public class BubbleSortBenchmark {

    @Param({"RANDOM", "SORTED", "REVERSE", "NEARLY_SORTED"})
    public String distribution;

    private int[] data;

    @Setup(Level.Trial)
    public void setup() {
        data = DataGenerator.generate(distribution, 100_000);
    }

    @Benchmark
    public int[] bubbleSort() {
        int[] copy = data.clone();
        BubbleSort.sort(copy);
        return copy;
    }
}
