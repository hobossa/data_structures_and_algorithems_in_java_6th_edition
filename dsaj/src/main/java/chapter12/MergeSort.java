package chapter12;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class MergeSort {

    /**
     * Merge contents of Array S1 and S2 into properly sized array S.
     */
    private static <K> void merge(K[] S1, K[] S2, K[] S, Comparator<K> comp) {
        int i = 0, j = 0;
        while (i + j < S.length) {
            if (j == S2.length  // all items in the S2 have been added to S
                    || (i < S1.length && comp.compare(S1[i], S2[j]) < 0)) {
                S[i + j] = S1[i++];
            } else {
                S[i + j] = S2[j++];
            }
        }
    }

    /**
     * Merge-sort contents of array S.
     */
    public static <K> void mergeSort(K[] S, Comparator<K> comp) {
        int n = S.length;
        if (n < 2) {
            return;
        }
        // divide
        int mid = n / 2;
        K[] S1 = Arrays.copyOfRange(S, 0, mid);
        K[] S2 = Arrays.copyOfRange(S, mid, n);
        // conquer (with recursion)
        mergeSort(S1, comp);
        mergeSort(S2, comp);
        // merge results
        merge(S1, S2, S, comp);
    }

    /**
     * Merge contents of sorted queues S1 and S2 into empty queue S.
     */
    protected static <K> void merge(Queue<K> S1, Queue<K> S2, Queue<K> S, Comparator<K> comp) {
        while (!S1.isEmpty() && !S2.isEmpty()) {
            if (comp.compare(S1.peek(), S2.peek()) < 0) {
                S.add(S1.remove());
            } else {
                S.add(S2.remove());
            }
        }
        if (!S1.isEmpty()) {
            S.addAll(S1);
        }
        if (!S2.isEmpty()) {
            S.addAll(S2);
        }
    }

    public static <K> void mergeSort(Queue<K> S, Comparator<K> comp) {
        int n = S.size();
        if (n < 2) {
            return;
        }
        // divide
        Queue<K> S1 = new LinkedList<>();
        Queue<K> S2 = new LinkedList<>();
        while (S1.size() < n / 2) {
            S1.add(S.remove());
        }
        S2.addAll(S);
        // conquer
        mergeSort(S1, comp);
        mergeSort(S2, comp);
        // merge results
        merge(S1, S2, S, comp);
    }

    /**
     * A nonrecursive version of array-based merge-sort, which runs in O(nlogn) time.
     */
    private static <K> void merge(K[] in, K[] out, Comparator<K> comp, int start, int inc) {
        int end1 = Math.min(start + inc, in.length);        // boundary for run 1
        int end2 = Math.min(start + 2 * inc, in.length);    // boundary for run 2
        int x = start;                  // index into run 1
        int y = start + inc;            // index into run 2
        int z = start;                  // index into output
        while (x < end1 && y < end2) {
            if (comp.compare(in[x], in[y]) < 0) {
                out[z++] = in[x++];
            } else {
                out[z++] = in[y++];
            }
        }
        if (x < end1) {
            System.arraycopy(in, x, out, z, end1 - x);
        } else if (y < end2) {
            System.arraycopy(in, y, out, z, end2 - y);
        }
    }

    public static <K> void mergeSortBottomUp(K[] orig, Comparator<K> comp) {
        int n = orig.length;
        K[] src = orig;
        K[] dest = (K[]) new Object[n];
        K[] temp;
        for (int i = 1; i < n; i *= 2) {
            for (int j = 0; j < n; j += 2 * i) {
                merge(src, dest, comp, j, i);
            }
            temp = src;
            src = dest;
            dest = temp;
        }
        if (orig != src) {
            System.arraycopy(src, 0, orig, 0, n);
        }
    }
}
