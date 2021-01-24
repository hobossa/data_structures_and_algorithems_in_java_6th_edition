package chapter12;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class QuickSort {
    public static <K> void quickSort(List<K> S, Comparator<K> comp) {
        int n = S.size();
        if (n < 2) {
            return;
        }
        // divide
        K pivot = S.get(0);
        List<K> L = new LinkedList<>();
        List<K> E = new LinkedList<>();
        List<K> G = new LinkedList<>();
        while (!S.isEmpty()) {
            K element = S.remove(0);
            int c = comp.compare(element, pivot);
            if (c < 0) {
                L.add(element);
            } else if (c == 0) {
                E.add(element);
            } else {
                G.add(element);
            }
        }
        // conquer
        quickSort(L, comp);
        quickSort(G, comp);
        // concatenate results
        S.addAll(L);
        S.addAll(E);
        S.addAll(G);
    }

    // Sort the subarray S[a..b] inlusive.
    private static <K> void quickSortInPlace(K[] S, Comparator<K> comp, int a, int b) {
        if (a >= b) {
            return;
        }
        int left = a;
        int right = b - 1;
        K pivot = S[b];
        K temp;
        while (left <= right) {
            // scan util reaching value equal or larger than pivot ( or right marker)
            while (left <= right && comp.compare(S[left], pivot) < 0) {
                left++;
            }
            // scan util reaching value equal or smaller than pivot
            while (left <= right && comp.compare(S[right], pivot) > 0) {
                right--;
            }
            if (left <= right) {    // indices did not strictly cross
                // swap
                temp = S[left];
                S[left] = S[right];
                S[right] = temp;
                left++;
                right--;
            }
        }
        // put pivot into its final place (currently marked by left index)
        temp = S[left];
        S[left] = S[b];     // pivot = S[b]
        S[b] = temp;
        // make recursive calls
        quickSortInPlace(S, comp, a, left - 1);
        quickSortInPlace(S, comp, left + 1, b);

    }

    public static <K> void quickSortInPlace(K[] S, Comparator<K> comp) {
        quickSortInPlace(S, comp, 0, S.length - 1);
    }

    public static void main(String[] args) {
        Random random = new Random();
        int n = 10;
        Integer[] S = new Integer[n];
        for (int i = 0; i < n; i++) {
            S[i] = random.nextInt(1000);
            System.out.print(S[i] + " ");
        }
        System.out.print("\n");
        quickSortInPlace(S, Integer::compareTo);
        for (Integer i : S) {
            System.out.println(i);
        }
    }
}

