package chapter05;

public class LinearSum {
    public static int linearSum(int[] data, int n) {
        if (n == 0) {
            return 0;
        } else {
            return linearSum(data, n-1) + data[n-1];
        }
    }
}
