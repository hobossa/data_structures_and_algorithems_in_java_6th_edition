package chapter05;

public class Fibonacci {
    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static long fibonacciOpt(int n) {
        long[] temp = _fibonacciOpt(n);
        return temp[0];
    }

    // return {f(n), f(n-1)}, so we do not need to calculate f(n-2) repetitively
    private static long[] _fibonacciOpt(int n) {
        if (n <= 1) {
            return new long[]{n, 0};
        }
        long[] temp = _fibonacciOpt(n - 1);
        return new long[]{temp[0] + temp[1], temp[0]};
    }

    private static int _fibonacciTailRecursion(int n, int acc1, int acc2) {
        if (n == 0)
            return acc1;
        else
            return _fibonacciTailRecursion(n - 1, acc1 + acc2, acc1);
    }

    public static int fibonacciTailRecursion(int n) {
        return _fibonacciTailRecursion(n, 0, 1);
    }
}
