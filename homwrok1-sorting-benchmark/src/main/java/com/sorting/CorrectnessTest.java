package com.sorting;

public class CorrectnessTest {

    public static void main(String[] args) {
        String[] distributions = {"RANDOM", "SORTED", "REVERSE", "NEARLY_SORTED"};
        int n = 100_000;

        for (String dist : distributions) {
            System.out.println("Testing distribution: " + dist);

            int[] original = DataGenerator.generate(dist, n);

            int[] copy = original.clone();
            BubbleSort.sort(copy);
            SortVerifier.assertCorrect(original, copy);
            System.out.println("  BubbleSort:  PASS");

            copy = original.clone();
            QuickSort.sort(copy);
            SortVerifier.assertCorrect(original, copy);
            System.out.println("  QuickSort:   PASS");

            copy = original.clone();
            RadixSort.sort(copy);
            SortVerifier.assertCorrect(original, copy);
            System.out.println("  RadixSort:   PASS");

            copy = original.clone();
            java.util.Arrays.sort(copy);
            SortVerifier.assertCorrect(original, copy);
            System.out.println("  Arrays.sort: PASS");
        }

        System.out.println("\nAll correctness tests passed!");
    }
}
