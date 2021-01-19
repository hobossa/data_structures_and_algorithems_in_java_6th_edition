package chapter11;

import chapter07.Position;
import chapter09.Entry;

import javax.swing.border.EtchedBorder;
import java.util.Comparator;

public class AVLTreeMap<K, V> extends TreeMap<K, V> {
    public AVLTreeMap() {
        super();
    }

    public AVLTreeMap(Comparator<K> comp) {
        super(comp);
    }

    protected int height(Position<Entry<K, V>> p) {
        return tree.getAux(p);
    }

    protected void recomputeHeight(Position<Entry<K, V>> p) {
        tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
    }

    protected boolean isBalanced(Position<Entry<K, V>> p) {
        return Math.abs(height(left(p)) - height(right(p))) <= 1;
    }

    // return the taller child, or the same side child as p.
    protected Position<Entry<K, V>> tallerChild(Position<Entry<K, V>> p) {
        if (height(left(p)) > height(right(p))) {
            return left(p);
        }
        if (height(left(p)) < height(right(p))) {
            return right(p);
        }

        if (isRoot(p)) {
            return left(p);     // choice is irrelevant
        }
        if (p == left(parent(p))) {
            return left(p);
        } else {
            return right(p);
        }
    }

    /**
     * Utility used to rebalance after an insert or removal operation. This traverses
     * the path upward form p, performing a trinode restructuring when imbalance is found,
     * continuing util balance is restored.
     */
    protected void rebalance(Position<Entry<K,V>> p) {
        int oldHeight, newHeight;
        do {
            oldHeight = height(p);
            if (!isBalanced(p)) {
                p = restructure(tallerChild(tallerChild(p)));
                recomputeHeight(left(p));
                recomputeHeight(right(p));
            }
            recomputeHeight(p);
            newHeight = height(p);
            p = parent(p);
        }while (oldHeight != newHeight && p!= null);
    }

    @Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        rebalance(p);
    }

    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        if (!isRoot(p)) {
            rebalance(parent(p));
        }
    }
}