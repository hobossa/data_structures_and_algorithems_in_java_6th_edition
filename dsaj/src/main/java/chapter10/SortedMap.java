package chapter10;

import chapter09.Entry;

public interface SortedMap<K, V> extends Map<K, V> {
    /**
     * Returns the entry with smallest key Value
     * (or null, if the map is empty).
     */
    Entry<K, V> firstEntry();

    /**
     * Returns the entry with largest key value
     * (or null, if the map is empty)l
     */
    Entry<K, V> lastEntry();

    /**
     * Returns the entry with the least key value greater than or equal to k
     * (or null, if no such entry exists).
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException;

    /**
     * Returns the entry with the greatest key value less than or equal to k
     * (or null, if no such entry exists).
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    Entry<K, V> floorEntry(K key) throws IllegalArgumentException;

    /**
     * Returns the entry with the greatest key value strictly less than k
     * (or null, if no such entry exists).
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    Entry<K, V> lowerEntry(K key) throws IllegalArgumentException;

    /**
     * Returns the entry with the least key value strictly greater than k
     * (or null, if no such entry exists).
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    Entry<K, V> higherEntry(K key) throws IllegalArgumentException;

    /**
     * Returns an iteration of all entries with key greater than or equal to k1,
     * and strictly less than k2.
     * @throws IllegalArgumentException if the key is not compatible with the map
     */
    Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException;
}
