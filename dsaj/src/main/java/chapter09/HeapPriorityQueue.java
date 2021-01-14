package chapter09;

import chapter07.ArrayList;

import java.util.Comparator;

public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

    protected ArrayList<Entry<K, V>> heap = new ArrayList<>();

    public HeapPriorityQueue() {
        super();
    }

    public HeapPriorityQueue(Comparator<K> comp) {
        super(comp);
    }

    protected int parent(int i) {
        return (i - 1) / 2;     // truncating division
    }

    protected int left(int i) {
        return i * 2 + 1;
    }

    protected int right(int i) {
        return i * 2 + 2;
    }

    protected boolean hasLeft(int i) {
        return left(i) < size();
    }

    protected boolean hasRight(int i) {
        return right(i) < size();
    }

    protected void swap(int i, int j) {
        Entry<K, V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public HeapPriorityQueue(K[] keys, V[] values) {
        super();
        for (int i = 0; i < Math.min(keys.length, values.length); i++) {
            heap.add(heap.size(), new PQEntry<>(keys[i], values[i]));
        }
        heapify();
    }

    /**
     * Performs a bottom-up construction of the heap in linear time
     */
    protected void heapify() {
        int startindex = parent(size() - 1); // start at the parent of the last entry.
        for (int i = startindex; i >= 0; i--) {
            downHeap(i);
        }
    }

    /**
     * Moves the entry at index i higher, if necessary, to restore the heap property.
     */
    protected void upHeap(int i) {
        while (i > 0) {
            int p = parent(i);
            if (compare(heap.get(i), heap.get(p)) >= 0) {
                break;
            }
            swap(i, p);
            i = p;
        }
    }

    /**
     * Moves the entry at index i lower, if necessary, to restore the heap property.
     */
    protected void downHeap(int i) {
        while (hasLeft(i)) {
            int leftIndex = left(i);
            int smallChildIndex = leftIndex;
            if (hasRight(i)) {
                int rightIndex = right(i);
                if (compare(heap.get(leftIndex), heap.get(rightIndex)) > 0) {
                    smallChildIndex = rightIndex;
                }
            }
            if (compare(heap.get(smallChildIndex), heap.get(i)) >= 0) {
                break;
            }
            swap(i, smallChildIndex);
            i = smallChildIndex;
        }
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> newest = new PQEntry<>(key, value);
        heap.add(heap.size(), newest);
        upHeap(size() - 1);
        return newest;
    }

    @Override
    public Entry<K, V> min() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    @Override
    public Entry<K, V> removeMin() {
        if (heap.isEmpty()) {
            return null;
        }
        Entry<K, V> answer = heap.get(0);
        swap(0, size() - 1);
        heap.remove(size() - 1);
        downHeap(0);
        return answer;
    }
}
