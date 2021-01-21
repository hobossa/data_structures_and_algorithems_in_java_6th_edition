package chapter12;

import java.util.Arrays;
import java.util.Comparator;

public class MergeSort {

    /**
     * Merge contents of Array S1 and S2 into properly sized array S.
     */
    public static <K> void merge(K[] S1, K[] S2, K[] S, Comparator<K> comp) {
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
    public]

    static <K> void mergeSort(K[] S, Comparator<K> comp) {
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
}
