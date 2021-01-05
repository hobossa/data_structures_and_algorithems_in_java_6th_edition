package chapter05;

public class Power {
    public static double power(double x, int n) {
        if (n == 0) {
            return 1;
        } else {
            return x * power(x, n - 1);
        }
    }

    public static double powerEx(double x, int n) {
//        if (n == 0) {
//            return 1;
//        } else if (n % 2 == 0) {
//            double t = power(x, n / 2);
//            return t * t;
//        } else {
//            // n % 2 == 1
//            double t = power(x, n / 2);
//            return x * t * t;
//        }
        if (n == 0) {
            return 1;
        } else {
            double partial = power(x, n / 2);
            double result = partial * partial;
            if (n % 2 == 1) {
                result *= x;
            }
            return result;
        }
    }
}
