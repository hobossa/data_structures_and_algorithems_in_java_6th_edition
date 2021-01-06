package chapter05;

public class Factorial {
    public static int factorial(int n) throws IllegalArgumentException {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be non negative");
        } else if (n == 0) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    private static int _factorialTailRecursion(int n,int acc) // tail recursion call
    {
        if(n==0)
            return acc;
        else
            return _factorialTailRecursion(n-1,acc*n);
    }

    public static int factorialTailRecursion(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be non negative");
        }
        return _factorialTailRecursion(n, 1);
    }
}
