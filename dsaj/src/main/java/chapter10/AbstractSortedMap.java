package chapter10;

import chapter09.Entry;

public abstract class AbstractSortedMap<K, V> extends AbstractMap<K, V> {

    /**
     * Returns the entry with smallest key Value
     * (or null, if the map is empty).
     */
    public abstract Entry<K, V> firstEntry();

    /**
     * Returns the entry with largest key value
     * (or null, if the map is empty)l
     */
    public abstract Entry<K, V> lastEntry();

    /**
     * Returns the entry with the least key value greater than or equal to k
     * (or null, if no such entry exists).
     */
    public abstract Entry<K, V> ceilingEntry(K key);

    /**
     * Returns the entry with the greatest key value less than or equal to k
     * (or null, if no such entry exists).
     */
    public abstract Entry<K, V> floorEntry(K key);

    /**
     * Returns the entry with the greatest key value strictly less than k
     * (or null, if no such entry exists).
     */
    public abstract Entry<K, V> lowerEntry(K key);

    /**
     * Returns the entry with the least key value strictly greater than k
     * (or null, if no such entry exists).
     */
    public abstract Entry<K, V> higherEntry(K key);

    /**
     * Returns an iteration of all entries with key greater than or equal to k1,
     * and strictly less than k2.
     */
    public abstract Iterable<Entry<K, V>> subMap(K fromKey, K toKey);
}