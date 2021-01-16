package chapter10;

import chapter07.ArrayList;
import chapter09.Entry;

public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
    private MapEntry<K, V>[] table;      // a fixed array of entries (all initially null)
    private MapEntry<K, V> DEFUNCT = new MapEntry<>(null, null);     // sentinel

    public ProbeHashMap() {
        super();
    }

    public ProbeHashMap(int cap) {
        super(cap);
    }

    public ProbeHashMap(int capacity, int p) {
        super(capacity, p);
    }

    /**
     * Returns true if location is either empty or the "defunct" sentinel
     */
    private boolean isAvailable(int i) {
        return (table[i] == null || table[i] == DEFUNCT);
    }

    /**
     * Returns index with key k, or -(a+1) such that k could be added at index a.
     */
    private int findSlot(int h, K k) {
        int avail = -1;                     // no slot available (thus far)
        int i = h;                          // index while scanning table
        do {
            if (isAvailable(i)) {           // may be either empty or defunct
                if (avail == -1) {
                    avail = i;              // this is the first available slot
                }
                if (table[i] == null) {
                    break;                  // if empty, search fails immediately
                }
            } else if (table[i].getKey().equals(k)) {
                return i;                   // successful match
            }
            i = (i + 1) % capacity;         // keep looking (cyclically)
        } while (i != h);                   // stop if we return to the start.
        return -(avail+1);                  // search has failed.
    }

    @Override
    protected void createTable() {
        table = (MapEntry<K, V>[]) new MapEntry[capacity];      // safe case
    }

    @Override
    protected V bucketGet(int h, K k) {
        int i = findSlot(h, k);
        if (i < 0) {
            return null;
        }
        return table[i].getValue();
    }

    @Override
    protected V bucketPut(int h, K k, V v) {
        int i = findSlot(h, k);
        if (i >=0) {                            // this key has an existing entry
            return table[i].setValue(v);
        }
        table[-(i+1)] = new MapEntry<>(k, v);   // convert to proper index. check the
                                                // last return statement of findSlot.
        n++;
        return null;
    }

    @Override
    protected V bucketRemove(int h, K k) {
        int i = findSlot(h, k);
        if (i < 0) {
            return null;
        }
        V answer = table[i].getValue();
        table[i] = DEFUNCT;                     // make this slot as deactivated
        n--;
        return answer;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (!isAvailable(i)) {
                buffer.add(buffer.size(), table[i]);
            }
        }
        return buffer;
    }
}
