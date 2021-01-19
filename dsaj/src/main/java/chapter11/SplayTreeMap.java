package chapter11;

import chapter07.Position;
import chapter09.Entry;

import java.util.Comparator;

public class SplayTreeMap<K,V> extends TreeMap<K,V> {
    public SplayTreeMap() {
        super();
    }

    public SplayTreeMap(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Utility used to rebalance after a map operation.
     */
    private void splay(Position<Entry<K,V>> p) {
        while (!isRoot(p)) {
            Position<Entry<K,V>> parent = parent(p);
            Position<Entry<K,V>> grand = parent(parent);
            if (grand == null) {    // zig case
                rotate(p);
            } else if ((parent == left(grand)) == (p == left(parent))){ // zip-zig case
                rotate(parent);
                rotate(p);
            } else {            // zig-zag case
                rotate(p);
                rotate(p);
            }
        }
    }

    @Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        splay(p);
    }

    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        if (!isRoot(p)) {
            splay(parent(p));
        }
    }

    @Override
    protected void rebalanceAccess(Position<Entry<K, V>> p) {
        if (isExternal(p)){
            p = parent(p);
        }
        if (p != null) {
            splay(p);
        }
    }
}
