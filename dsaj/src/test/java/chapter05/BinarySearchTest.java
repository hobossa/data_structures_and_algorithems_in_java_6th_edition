package chapter05;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinarySearchTest {

    @Test
    public void binarySearch() {
        int a[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(i, BinarySearch.binarySearch(a, i, 0, a.length - 1));
        }
    }
}