package chapter10;

import chapter09.Entry;

import java.util.Iterator;

public abstract class AbstractMap<K, V> implements Map<K, V> {

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    // -------- nested MapEntry class --------
    protected static class MapEntry<K, V> implements Entry<K, V> {
        private K key;
        private V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        protected void setKey(K key) {
            this.key = key;
        }

        protected V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    } // -------- end of nested MapEntry class --------

    // Support for public keySet method...
    // -------- nested KeyIterator class --------
    private class KeyIterator implements Iterator<K> {
        private Iterator<Entry<K, V>> entries = entrySet().iterator();

        @Override
        public boolean hasNext() {
            return entries.hasNext();
        }

        @Override
        public K next() {
            return entries.next().getKey();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    } // -------- end of nested KeyIterator class --------

    // -------- nested KeyIterable class --------
    private class KeyIterable implements Iterable<K> {
        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }
    } // -------- end of nested KeyIterable class --------

    @Override
    public Iterable<K> keySet() {
        return new KeyIterable();
    }

    // Support for public values method
    // -------- nested ValueIterator class --------
    private class ValueIterator implements Iterator<V> {
        private Iterator<Entry<K,V>> entries = entrySet().iterator();
        @Override
        public boolean hasNext() {
            return entries.hasNext();
        }

        @Override
        public V next() {
            return entries.next().getValue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    } // -------- end of nested ValueIterator class --------

    // -------- nested ValueIterable class --------
    private class ValueIterable implements Iterable<V> {

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }
    } // -------- end of nested ValueIterable class --------

    @Override
    public Iterable<V> values() {
        return new ValueIterable();
    }
}
