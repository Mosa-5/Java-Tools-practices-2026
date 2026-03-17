package com.sorting;

public class RadixSort {

    public static void sort(int[] arr) {
        int n = arr.length;
        int[] buf = new int[n];
        int[] src = arr;
        int[] dst = buf;

        for (int pass = 0; pass < 4; pass++) {
            int[] count = new int[256];

            // count occurrences for this byte
            for (int i = 0; i < n; i++) {
                int b = (src[i] >>> (8 * pass)) & 0xFF;
                count[b]++;
            }

            // prefix sum — on the last pass, put negatives (128-255) before positives (0-127)
            int total = 0;
            if (pass == 3) {
                for (int i = 128; i < 256; i++) { int c = count[i]; count[i] = total; total += c; }
                for (int i = 0;   i < 128; i++) { int c = count[i]; count[i] = total; total += c; }
            } else {
                for (int i = 0; i < 256; i++) { int c = count[i]; count[i] = total; total += c; }
            }

            // scatter
            for (int i = 0; i < n; i++) {
                int b = (src[i] >>> (8 * pass)) & 0xFF;
                dst[count[b]++] = src[i];
            }

            // swap buffers
            int[] tmp = src; src = dst; dst = tmp;
        }

        if (src != arr) {
            System.arraycopy(src, 0, arr, 0, n);
        }
    }
}
