package chapter05;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FactorialTest {

    @Test
    public void factorial() {
        Assert.assertEquals(120, Factorial.factorial(5));
    }
}