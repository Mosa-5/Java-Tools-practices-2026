package com.sorting;

import java.util.Random;

public class DataGenerator {

    public static int[] generate(String distribution, int n) {
        switch (distribution) {
            case "RANDOM":        return random(n);
            case "SORTED":        return sorted(n);
            case "REVERSE":       return reverse(n);
            case "NEARLY_SORTED": return nearlySorted(n);
            default: throw new IllegalArgumentException("Unknown distribution: " + distribution);
        }
    }

    private static int[] random(int n) {
        int[] arr = new int[n];
        Random rnd = new Random(42);
        for (int i = 0; i < n; i++) {
            arr[i] = rnd.nextInt();
        }
        return arr;
    }

    private static int[] sorted(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        return arr;
    }

    private static int[] reverse(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = n - 1 - i;
        }
        return arr;
    }

    private static int[] nearlySorted(int n) {
        int[] arr = sorted(n);
        Random rnd = new Random(42);
        int swaps = n / 100;
        for (int i = 0; i < swaps; i++) {
            int x = rnd.nextInt(n);
            int y = rnd.nextInt(n);
            int tmp = arr[x]; arr[x] = arr[y]; arr[y] = tmp;
        }
        return arr;
    }
}
