package chapter10;

import chapter07.ArrayList;
import chapter09.Entry;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Inefficient implementation of Map, because findIndex method takes O(n) time.
 */
public class UnsortedTableMap<K, V> extends AbstractMap<K, V> {
    private ArrayList<MapEntry<K, V>> table = new ArrayList<>();

    public UnsortedTableMap() {
    }

    /**
     * Returns the index of an entry with equal key, or -1 if none found.
     */
    private int findIndex(K key) {
        int n = table.size();
        for (int i = 0; i < n; i++) {
            if (table.get(i).getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public V get(K key) {
        int i = findIndex(key);
        if (i == -1) {
            return null;
        }
        return table.get(i).getValue();
    }

    @Override
    public V put(K key, V value) {
        int i = findIndex(key);
        if (i == -1) {
            table.add(table.size(), new MapEntry<>(key, value));
            return null;
        } else {
            return table.get(i).setValue(value);
        }
    }

    @Override
    public V remove(K key) {
        int i = findIndex(key);
        if (i == -1) {
            return null;
        }
        int n = size();
        V answer = table.get(i).getValue();
        if (i != n - 1) {
            table.set(i, table.get(n - 1));
        }
        table.remove(n - 1);
        return answer;
    }

    // -------- nested EntryIterator class --------
    private class EntryIterator implements Iterator<Entry<K, V>> {
        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < table.size();
        }

        @Override
        public Entry<K, V> next() {
            if (i == table.size()) {
                throw new NoSuchElementException();
            }
            return table.get(i++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    } // -------- end of nested EntryIterator class --------

    // -------- nested EntryIterable class --------
    private class EntryIterable implements Iterable<Entry<K,V>> {

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }
    } // -------- end of nested EntryIterable class --------
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        return new EntryIterable();
    }
}
