package chapter10;

import chapter07.ArrayList;
import chapter09.Entry;

import java.util.Comparator;

public class SortedTableMap<K, V> extends AbstractSortedMap<K, V> {
    private ArrayList<MapEntry<K, V>> table = new ArrayList<>();

    public SortedTableMap() {
        super();
    }

    public SortedTableMap(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Returns the smallest index for range table[low..high] inclusive an entry
     * with a key greater than or equal to k (or else index high+1, by convention).
     */
    private int findIndex(K key, int low, int high) {
        if (high < low) {       // no entry qualifies
            return high + 1;
        }
        int mid = (low + high) / 2;
        int comp = compare(key, table.get(mid));
        if (comp == 0) {
            return mid;
        } else if (comp < 0) {
            return findIndex(key, low, mid - 1);
        } else {
            return findIndex(key, mid + 1, high);
        }
    }

    private int findIndex(K key) {
        return findIndex(key, 0, size() - 1);
    }

    /**
     * Utility returns the entry at index i, or else null if i is out of bounds.
     */
    private Entry<K, V> safeEntry(int i) {
        if (i < 0 || i >= table.size()) {
            return null;
        }
        return table.get(i);
    }

    /**
     * support for snapshot iterators for entrySet() and subMap() follow
     */
    private Iterable<Entry<K,V>> snapshot(int startIndex, K stop) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        int i = startIndex;
        while (i < table.size() && (stop == null || compare(stop, table.get(i))> 0)) {
            buffer.add(buffer.size(), table.get(i++));
        }
        return buffer;
    }

    @Override
    public Entry<K, V> firstEntry() {
        return safeEntry(0);
    }

    @Override
    public Entry<K, V> lastEntry() {
        return safeEntry(table.size() - 1);
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) {
        return safeEntry(findIndex(key));
    }

    @Override
    public Entry<K, V> floorEntry(K key) {
        int i = findIndex(key);
        if (i == size() || !key.equals(table.get(i).getKey())) {
            i--;    // look one earlier (unless we had found a perfect match)
        }
        return safeEntry(i);
    }

    @Override
    public Entry<K, V> lowerEntry(K key) {
        return safeEntry(findIndex(key) - 1);       // go strictly before the ceiling entry
    }

    @Override
    public Entry<K, V> higherEntry(K key) {
        int i = findIndex(key);
        if (i < size() && key.equals(table.get(i).getKey())){
            i++;
        }
        return safeEntry(i);
    }

    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) {
        return snapshot(findIndex(fromKey), toKey);
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public V get(K key) {
        int i = findIndex(key);
        if (i == size() || compare(key, table.get(i)) != 0) {
            return null;        // no match
        }
        return table.get(i).getValue();
    }

    @Override
    public V put(K key, V value) {
        int i = findIndex(key);
        if (i < size() && compare(table.get(i), key) == 0) {
            return table.get(i).setValue(value);
        }
        table.add(i, new MapEntry<>(key, value));       // new
        return null;
    }

    @Override
    public V remove(K key) {
        int i = findIndex(key);
        if (i == size() || compare(key, table.get(i)) != 0) { // no match
            return null;
        }
        return table.remove(i).getValue();
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        return snapshot(0, null);
    }
}
