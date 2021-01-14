package chapter09;

import java.util.Comparator;

public class HeapAdaptablePriorityQueue<K, V>
        extends HeapPriorityQueue<K, V> implements AdaptablePriorityQueue<K, V> {
    // -------- nested AdaptablePQEntry class --------
    protected static class AdaptablePQEntry<K, V> extends PQEntry<K, V> {
        private int index;      // entry's current index within the heap

        public AdaptablePQEntry(K k, V v, int index) {
            super(k, v);
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    } // -------- end of nested AdaptablePQEntry class --------

    public HeapAdaptablePriorityQueue() {
        super();
    }

    public HeapAdaptablePriorityQueue(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Validates an entry to ensure it location-aware.
     */
    protected AdaptablePQEntry<K, V> validate(Entry<K, V> entry) {
        if (!(entry instanceof AdaptablePQEntry)) {
            throw new IllegalArgumentException("Invalid entry");
        }
        AdaptablePQEntry<K, V> locator = (AdaptablePQEntry<K, V>) entry;
        int i = locator.getIndex();
        if (i >= size() || heap.get(i) != locator) {
            throw new IllegalArgumentException("Invalid entry");
        }
        return locator;
    }

    @Override
    protected void swap(int i, int j) {
        super.swap(i, j);
        // reset indices
        ((AdaptablePQEntry<K, V>) heap.get(i)).setIndex(j);
        ((AdaptablePQEntry<K, V>) heap.get(j)).setIndex(i);
    }

    /**
     * Restores the heap property by moving the enrty at index i upward/downward
     *
     * @param i
     */
    protected void bubble(int i) {
        if (i > 0 && compare(heap.get(i), heap.get(parent(i))) < 0) {
            upHeap(i);
        } else {
            downHeap(i);    // although it might not need to move when it equals to its parent.
        }
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> newest = new AdaptablePQEntry<>(key, value, heap.size());
        heap.add(heap.size(), newest);
        upHeap(size() - 1);   // unheap newly added entry to restore heap property.
        return newest;
    }

    @Override
    public void remove(Entry<K, V> entry) throws IllegalArgumentException {
        AdaptablePQEntry<K, V> locator = validate(entry);
        int i = locator.getIndex();
        if (i == heap.size() - 1) {
            heap.remove(heap.size() - 1);
        } else {
            swap(i, heap.size() - 1);
            heap.remove(heap.size() - 1);
            bubble(i);
        }
    }

    @Override
    public void replaceKey(Entry<K, V> entry, K key) throws IllegalArgumentException {
        AdaptablePQEntry<K,V> locator = validate(entry);
        checkKey(key);
        locator.setKey(key);
        bubble(locator.getIndex());
    }

    @Override
    public void replaceValue(Entry<K, V> entry, V value) throws IllegalArgumentException {
        AdaptablePQEntry<K,V> locator = validate(entry);
        locator.setValue(value);
    }
}
