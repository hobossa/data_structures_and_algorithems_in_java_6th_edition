package chapter10;

import chapter09.DefaulterComparator;
import chapter09.Entry;

import java.util.Comparator;

public abstract class AbstractSortedMap<K, V>
        extends AbstractMap<K, V> implements SortedMap<K, V> {
    private Comparator<K> comp;

    protected AbstractSortedMap(Comparator<K> comp) {
        this.comp = comp;
    }

    protected AbstractSortedMap() {
        this(new DefaulterComparator<K>());
    }

    protected int compare(Entry<K, V> a, Entry<K, V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

    protected int compare(K a, Entry<K, V> b) {
        return comp.compare(a, b.getKey());
    }

    protected int compare(Entry<K, V> a, K b) {
        return comp.compare(a.getKey(), b);
    }

    protected int compare(K a, K b) {
        return comp.compare(a, b);
    }

    /**
     * Determines whether a key is valid.
     */
    protected boolean checkKey(K key) throws IllegalArgumentException {
        try {
            return (comp.compare(key, key) == 0);   // see if key can be compared to itself.
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Incompatible key");
        }
    }
}