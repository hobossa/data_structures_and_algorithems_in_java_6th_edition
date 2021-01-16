package chapter10;

import chapter07.ArrayList;
import chapter09.Entry;

import java.util.Comparator;

public class SortedTableMap <K,V> extends AbstractSortedMap<K,V>{
    private ArrayList<MapEntry<K,V>> table = new ArrayList<>();

    public SortedTableMap() {
        super();
    }

    public SortedTableMap(Comparator<K> comp) {
        super(comp);
    }

    @Override
    public Entry<K, V> firstEntry() {
        return null;
    }

    @Override
    public Entry<K, V> lastEntry() {
        return null;
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) {
        return null;
    }

    @Override
    public Entry<K, V> floorEntry(K key) {
        return null;
    }

    @Override
    public Entry<K, V> lowerEntry(K key) {
        return null;
    }

    @Override
    public Entry<K, V> higherEntry(K key) {
        return null;
    }

    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        return null;
    }
}
