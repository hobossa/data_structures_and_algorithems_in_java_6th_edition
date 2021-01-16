package chapter10;

import chapter07.ArrayList;
import chapter09.Entry;

import java.util.Random;

public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {
    // the subclass has the responsibility to properly maintain n.
    protected int n = 0;            // number of entries in the dictionary,
    protected int capacity;         // length of the table
    private int prime;              // prime factor
    private long scale, shift;      // the shift and scaling factors.

    public AbstractHashMap(int capacity, int p) {
        this.prime = p;
        this.capacity = capacity;
        Random rand = new Random();
        scale = rand.nextInt(prime - 1) + 1;
        shift = rand.nextInt(prime);
        createTable();
    }

    public AbstractHashMap(int cap) {
        this(cap, 109345121);           // default prime
    }

    public AbstractHashMap() {
        this(17);                      // default capacity
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public V get(K key) {
        return bucketGet(hashValue(key), key);
    }

    @Override
    public V remove(K key) {
        return bucketRemove(hashValue(key), key);
    }

    @Override
    public V put(K key, V value) {
        V answer = bucketPut(hashValue(key), key, value);
        if (n > capacity / 2) {                 // keep load factor <= 0.5
            resize(2*capacity -1);      // (or find a nearby prime)
        }
        return answer;
    }

    private int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    private void resize(int newCap) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);
        for (Entry<K,V> e: entrySet()) {
            buffer.add(buffer.size(), e);
        }
        capacity = newCap;
        createTable();
        n = 0;
        for (Entry<K,V> e: buffer) {
            put(e.getKey(), e.getValue());
        }
    }

    protected abstract void createTable();

    protected abstract V bucketGet(int h, K k);

    protected abstract V bucketPut(int h, K k, V v);

    protected abstract V bucketRemove(int h, K k);
}
