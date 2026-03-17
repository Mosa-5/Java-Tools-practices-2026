package com.sorting;

public class QuickSort {

    // Pivot strategy: median-of-three
    // We pick the median of arr[low], arr[mid], arr[high] as pivot.
    // This avoids O(n²) worst case on sorted/reverse-sorted input
    // that would happen with a naive first-element or last-element pivot.
    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            sort(arr, low, pivotIndex - 1);
            sort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        // sort three elements so median is at mid
        if (arr[low] > arr[mid])  { int t = arr[low]; arr[low] = arr[mid]; arr[mid] = t; }
        if (arr[low] > arr[high]) { int t = arr[low]; arr[low] = arr[high]; arr[high] = t; }
        if (arr[mid] > arr[high]) { int t = arr[mid]; arr[mid] = arr[high]; arr[high] = t; }

        // move pivot to end
        int pivot = arr[mid];
        arr[mid] = arr[high];
        arr[high] = pivot;

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int t = arr[i]; arr[i] = arr[j]; arr[j] = t;
            }
        }
        int t = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = t;
        return i + 1;
    }
}
