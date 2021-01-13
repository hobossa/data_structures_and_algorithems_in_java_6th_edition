package chapter09;

import java.util.Comparator;

public abstract class AbstractPriorityQueue<K, V> implements PriorityQueue<K, V> {

    // -------- nested PQEntry class --------
    protected static class PQEntry<K, V> implements Entry<K, V> {
        private K k;
        private V v;

        public PQEntry(K k, V v) {
            this.k = k;
            this.v = v;
        }

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        protected void setK(K k) {
            this.k = k;
        }

        protected void setV(V v) {
            this.v = v;
        }
    } // -------- end of nested PQEntry class --------

    private Comparator<K> comp;

    public AbstractPriorityQueue(Comparator<K> comp) {
        this.comp = comp;
    }

    public AbstractPriorityQueue() {
        this(new DefaulterComparator<>());
    }

    protected int compare(Entry<K,V> o1, Entry<K, V> o2) {
        return comp.compare(o1.getKey(), o2.getKey());
    }

    protected boolean checkKey(K k) throws IllegalArgumentException {
        try {
            return (comp.compare(k, k) == 0);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Incompatible key");
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
