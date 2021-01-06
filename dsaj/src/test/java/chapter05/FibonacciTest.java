package chapter05;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciTest {

    @Test
    public void fibonacci() {
        Assert.assertEquals(8, Fibonacci.fibonacci(6));
        Assert.assertEquals(8, Fibonacci.fibonacciOpt(6));
        Assert.assertEquals(8, Fibonacci.fibonacciTailRecursion(6));
    }
}