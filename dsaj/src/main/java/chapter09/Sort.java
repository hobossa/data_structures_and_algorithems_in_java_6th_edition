package chapter09;

import chapter07.ArrayList;
import chapter07.PositionalList;

public class Sort {
    public static <E> void pqSort(PositionalList<E> S, PriorityQueue<E, ?> P) {
        int n = S.size();
        // step 1
        // we can use the similar way to the bottom-up construction of the heap,
        // so we can get a O(n) step1. check HeapPriorityQueue.
        for (int i = 0; i < n; i++) {
            E element = S.remove(S.first());
            P.insert(element, null);        // element is the key; null value
        }
        // step 2
        for (int i = 0; i < n; i++) {
            E element = P.removeMin().getKey();
            S.addLast(element);             // the smallest key in P is next placed in S
        }
    }
}
