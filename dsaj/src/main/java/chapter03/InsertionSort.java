package chapter03;

import java.util.Arrays;
import java.util.Comparator;

public class InsertionSort {
    public static void insertionSort(char[] data) {
        int n = data.length;
        for (int i = 1; i < n; i++) {
            char cur = data[i];
            int j = i;
            while ( j>0 && data[j-1] > cur) {
                data[j] = data[j-1];
                j--;
            }
            data[j] = cur;
        }
    }


    public static <T extends Comparable<? super T>> void insertionSort(T[] data) {
        int n = data.length;
        for (int i = 1; i < n; i++) {
            T cur = data[i];
            int j = i;
            while ( j>0 && data[j-1].compareTo(cur) > 0) {
                data[j] = data[j-1];
                j--;
            }
            data[j] = cur;
        }
    }


    public static <T> void insertionSort(T[] data, Comparator<? super T> c) {
        int n = data.length;
        for (int i = 1; i < n; i++) {
            T cur = data[i];
            int j = i;
            while ( j>0 && c.compare(data[j-1], cur) > 0) {
                data[j] = data[j-1];
                j--;
            }
            data[j] = cur;
        }
    }
}
