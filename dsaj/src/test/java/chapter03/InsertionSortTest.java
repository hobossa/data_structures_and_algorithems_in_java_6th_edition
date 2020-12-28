package chapter03;

import org.junit.Assert;
import org.junit.Test;

public class InsertionSortTest {

    @Test
    public void sortChar() {
        char[] arr = {'e','d','c','b','a'};
        InsertionSort.insertionSort(arr);
        Assert.assertArrayEquals(new char[]{'a', 'b', 'c', 'd', 'e'}, arr);
    }

    @Test
    public void sortComparableObjects() {
        Character[] arr = {'e','d','c','b','a'};
        InsertionSort.insertionSort(arr);
        Assert.assertArrayEquals(new Character[]{'a', 'b', 'c', 'd', 'e'}, arr);
    }

    @Test
    public void sortWithComparator() {
        Character[] arr = {'a', 'b', 'c', 'd', 'e'};
        InsertionSort.insertionSort(arr, (a, b)-> {
            return -a.compareTo(b);
        });
        Assert.assertArrayEquals(new Character[]{'e','d','c','b','a'}, arr);
    }
}