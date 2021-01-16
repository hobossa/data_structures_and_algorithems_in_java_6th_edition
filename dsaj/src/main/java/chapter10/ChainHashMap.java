package chapter10;

import chapter07.ArrayList;
import chapter09.Entry;

public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {
    // a fixed capacity array of UnsortedTableMap that serve as buckets
    private UnsortedTableMap<K, V>[] table;  // initialized within createTable

    public ChainHashMap(int capacity, int p) {
        super(capacity, p);
    }

    public ChainHashMap(int cap) {
        super(cap);
    }

    public ChainHashMap() {
    }

    /**
     * Creates an empty table having length equal to current capacity
     */
    @Override
    protected void createTable() {
        table = (UnsortedTableMap<K, V>[]) new UnsortedTableMap[capacity];
    }

    @Override
    protected V bucketGet(int h, K k) {
        UnsortedTableMap<K,V> bucket = table[h];
        if (bucket == null) {
            return null;
        }
        return bucket.get(k);
    }

    @Override
    protected V bucketPut(int h, K k, V v) {
        UnsortedTableMap<K,V> bucket = table[n];
        if (bucket == null) {
            bucket = table[h] = new UnsortedTableMap<>();
        }
        int oldSize = bucket.size();
        V answer = bucket.put(k, v);
        n += (bucket.size() - oldSize);     // size may have increased
        return  answer;
    }

    @Override
    protected V bucketRemove(int h, K k) {
        UnsortedTableMap<K,V> bucket = table[h];
        if (bucket == null) {
            return null;
        }
        int oldSize = bucket.size();
        V answer = bucket.remove(k);
        n -= (oldSize- bucket.size());      // size may have decreased.
        // Personally I's like to use n += (bucket.size() - oldSize);
        return answer;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);
        for (int h = 0; h < capacity; h++) {
            if (table[h] == null) {
                continue;
            }
            for (Entry<K,V> entry : table[h].entrySet()){
                buffer.add(buffer.size(), entry);
            }
        }
        return buffer;
    }
}
