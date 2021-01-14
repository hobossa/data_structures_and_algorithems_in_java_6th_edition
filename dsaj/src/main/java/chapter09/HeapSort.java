package chapter09;

import chapter07.ArrayList;
import chapter07.List;

import java.util.Random;

public class HeapSort {
    public static <E extends Comparable<? super E>> List<E> sort(List<E> s) {
        List<E> result = new ArrayList<>(s.size());
        for (int i = 0; i < s.size(); i++) {
            result.add(i, s.get(i));
        }
        sortInPlace((ArrayList<E>) result);
        return result;
    }

    public static <E extends Comparable<? super E>> void sortInPlace(ArrayList<E> s) {
        // heapify s
        // start from the parent of the last element
        int startindex = (s.size() - 1) / 2;
        for (int i = startindex; i >= 0; i--) {
            downHeap(s, i, s.size());
        }

        // continuously move the biggest element to the right position at the end of array
        int heapSize = s.size();   // the last index of heap
        while (heapSize > 1) {
            E biggest = s.get(0);
            s.set(0, s.get(heapSize - 1));
            s.set(heapSize - 1, biggest);
            heapSize--;
            // restore the property of heap
            downHeap(s, 0, heapSize);
        }
    }

    private static <E extends Comparable<? super E>> void downHeap(ArrayList<E> s, int i, int heapSize) {
        // Because we want to sort s from small to big.
        // So we put the biggest element at the root of heap,
        // in this way we can continuously remove the max element and put it
        // at the right position in the s.

        int leftIndex = 2 * i + 1;
        while (leftIndex < heapSize) {  // has left child

            int bigChildIndex = leftIndex;
            int rightIndex = 2 * i + 2;
            if (rightIndex < heapSize) {
                if (s.get(rightIndex).compareTo(s.get(leftIndex)) > 0) {
                    bigChildIndex = rightIndex;
                }
            }

            if (s.get(bigChildIndex).compareTo(s.get(i)) <= 0) {
                break;
            }

            E temp = s.get(i);
            s.set(i, s.get(bigChildIndex));
            s.set(bigChildIndex, temp);

            i = bigChildIndex;
            leftIndex = 2 * i + 1;
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        final int size = 10;
        ArrayList<Integer> ls = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ls.add(i, random.nextInt(1000));
        }
        ls.forEach((i -> System.out.print(i + " ")));
        System.out.print('\n');
        sortInPlace(ls);
        ls.forEach((i -> System.out.print(i + " ")));
        System.out.print('\n');
    }
}
