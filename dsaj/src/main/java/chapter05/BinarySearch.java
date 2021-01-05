package chapter05;

public class BinarySearch {
    public static <T extends Comparable<? super T>> int binarySearch(T[] a, T key, int low, int high) {
        if (low > high) {
            return -1;
        }
        int m = (low + high) / 2;
        int c = a[m].compareTo(key);
        if (0 == c) {
            return m;
        } else if (c < 0) {
            return binarySearch(a, key, m + 1, high);
        } else {
            return binarySearch(a, key, low, m - 1);
        }
    }

    public static int binarySearch(int[] a, int key, int low, int high) {
        if (low > high) {
            return -1;
        }
        int m = (low + high) / 2;
        if (a[m] == key) {
            return m;
        } else if (a[m] < key) {
            return binarySearch(a, key, m + 1, high);
        } else {
            return binarySearch(a, key, low, m - 1);
        }
    }
}
