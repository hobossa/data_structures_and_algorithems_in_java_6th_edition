package chapter11;

import chapter07.Position;
import chapter09.Entry;

import java.util.Comparator;

public class RBTreeMap<K, V> extends TreeMap<K, V> {
    public RBTreeMap() {
        super();
    }

    public RBTreeMap(Comparator<K> comp) {
        super(comp);
    }

    // we use the inherited aux field with convention that 0=black and 1=red
    private boolean isBlack(Position<Entry<K, V>> p) {
        return tree.getAux(p) == 0;
    }

    private boolean isRed(Position<Entry<K, V>> p) {
        return tree.getAux(p) == 1;
    }

    private void makeBlack(Position<Entry<K, V>> p) {
        tree.setAux(p, 0);
    }

    private void makeRed(Position<Entry<K, V>> p) {
        tree.setAux(p, 1);
    }

    private void setColor(Position<Entry<K, V>> p, boolean toRed) {
        tree.setAux(p, toRed ? 1 : 0);
    }

    @Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        if (!isRoot(p)) {
            makeRed(p);         // the new internal node is initially colored red
            resolveRed(p);      // but this may cause a double-red violation.
        }
    }

    // Remedies potential double-red violation above red position p.
    private void resolveRed(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> parent, uncle, middle, grand;  // used in case analysis
        parent = parent(p);
        if (!isRed(parent)) {
            return;     // no double-red problem
        }
        // double-red problem exists
        uncle = sibling(parent);
        if (isBlack(uncle)) {   // case 1: misshapen 4-node do trinode restructuring.
            middle = restructure(p);
            makeBlack(middle);
            makeRed(left(middle));
            makeRed(right(middle));
        } else {                // case 2: overfull 5-node perform recoloring
            makeBlack(parent);
            makeBlack(uncle);
            grand = parent(parent);
            if (!isRoot(grand)) {
                makeRed(grand);         // grandparent becomes red
                resolveRed(grand);      // recur at red grandparent
            }
        }
    }

    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        if (isRed(p)) {
            makeBlack(p);   // deleted parent was black so this restores black depth
        } else if (!isRoot(p)) {
            Position<Entry<K, V>> sib = sibling(p);
            if (isInternal(sib)
                    && (isBlack(sib) || isInternal(left(sib)))) {
                remedyDoubleBlack(p);       // sib's subtree has nonzero black height
            }
        }
    }

    // Remedies a presumed double-black violation at the given (nonroot) position.
    private void remedyDoubleBlack(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> z = parent(p);
        Position<Entry<K, V>> y = sibling(p);
        if (isBlack(y)) {
            if (isRed(left(y)) || isRed(right(y))) { // case 1: trinode restructuring
                Position<Entry<K, V>> x = (isRed(left(y))? left(y) : right(y));
                Position<Entry<K, V>> middle = restructure(x);
                setColor(middle, isRed(z)); // root of restructured subtree gets z's old color
                makeBlack(left(middle));
                makeBlack((right(middle)));
            } else {                        // case 2: recoloring
                makeRed(y);
                if (isRed(z)) {
                    makeBlack(z);           // problem is solved
                } else if (!isRoot(z)) {
                    remedyDoubleBlack(z);   // propagate the problem
                }
            }
        } else {                            // case 3: reorient 3-node
            rotate(y);
            makeBlack(y);
            makeRed(z);
            remedyDoubleBlack(p);           // restart the process at p
        }
    }
}
