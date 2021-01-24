package chapter12;

import java.awt.image.renderable.RenderableImage;
import java.util.Comparator;
import java.util.Random;

public class QuickSelect {
    public static <K> K quickSelect(K[] S, int k, Comparator<K> comp) {
        return quickSelect(S, k, comp, 0, S.length-1);
    }

    // find the kth smallest element in the subarray S[a..b] inlusive.
    public static <K> K quickSelect(K[] S, int k, Comparator<K> comp, int a, int b) {
        if (a >= b) {
            return S[a];
        }
        int left = a;
        int right = b - 1;
        K pivot = S[b];       // pick the last element as the pivot
        while (left <= right){
            while (left <= right && comp.compare(S[left], pivot) < 0) {
                left++;
            }
            while (left <= right && comp.compare(S[right], pivot) > 0) {
                right--;
            }
            if (left <= right) {
                swap(S, left, right);
                left++;
                right--;
            }
        }
        // put pivot to its right place
        swap(S, left, b);

        if (k <= left) {
            // actually, left is the length of the subarray
            // that at the left of the pivot, and pivot the (left+1)th smallest element
            return quickSelect(S, k, comp, a, left-1);
        } else if (k == left+1) {
            return pivot;
        } else {
            return quickSelect(S, (k-left-1), comp,left +1, b);
        }
    }

    private static <K> void swap(K[] S, int a, int b) {
        K temp;
        temp = S[a];
        S[a] = S[b];
        S[b] = temp;
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
        for (int i = 1; i <= n; i++) {
            int k = quickSelect(S, i, Integer::compareTo);
            System.out.println(String.valueOf(i) + ": " + k);
        }
    }
}
