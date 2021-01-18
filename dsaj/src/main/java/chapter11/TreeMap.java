package chapter11;

import chapter07.ArrayList;
import chapter07.Position;
import chapter08.LinkedBinaryTree;
import chapter09.Entry;
import chapter10.AbstractSortedMap;

import java.util.Comparator;

public class TreeMap<K, V> extends AbstractSortedMap<K, V> {

    // -------- nested BalanceableBinaryTree class --------
    protected static class BalanceableBinaryTree<K, V>
            extends LinkedBinaryTree<Entry<K, V>> {
        // -------- nested BSTNode class -------
        protected static class BSTNode<E> extends Node<E> {
            int aux = 0;

            public BSTNode(E element, Node<E> parent, Node<E> left, Node<E> right) {
                super(element, parent, left, right);
            }

            public int getAux() {
                return aux;
            }

            public void setAux(int aux) {
                this.aux = aux;
            }
        } // -------- end of nested BSTNode class -------

        @Override
        protected Node<Entry<K, V>> createNode(Entry<K, V> kvEntry, Node<Entry<K, V>> parent,
                                               Node<Entry<K, V>> left, Node<Entry<K, V>> right) {
            return new BSTNode<>(kvEntry, parent, left, right);
        }

        /**
         * Relinks a parent node with its oriented child node.
         */
        private void relink(Node<Entry<K, V>> parent, Node<Entry<K, V>> child, boolean makeLeftChild) {
            child.setParent(parent);
            if (makeLeftChild) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }

        /**
         * Rotates Position p above its parent.  Switches between these
         * configurations, depending on whether p is a or p is b.
         * <pre>
         *          y                  y
         *         / \                / \
         *        x  t2             t0   x
         *       / \                    / \
         *      t0  t1                 t1  t2
         * </pre>
         * Caller should ensure that p is not the root.
         */
        public void rotate(Position<Entry<K, V>> p) {
            Node<Entry<K, V>> x = validate(p);
            Node<Entry<K, V>> y = x.getParent();                 // we assume this exists
            Node<Entry<K, V>> z = y.getParent();                 // grandparent (possibly null)
            if (z == null) {
                root = x;
                x.setParent(null);                              // x becomes root fo the tree
            } else {
                relink(z, x, y == z.getLeft());    // x becomes direct child of z
            }
            // now rotate x and y, including transfer of middle subtree
            if (x == y.getLeft()) {
                relink(y, x.getRight(), true);      // x's right child becomes y's left
                relink(x, y, false);                // y becomes x's right child
            } else {
                relink(y, x.getLeft(), false);      // x's left child becomes y's right
                relink(x, y, true);                 // y becomes x's left child
            }
        }

        /**
         * Returns the Position that becomes the root of the restructured subtree.
         * <p>
         * Assumes the nodes are in one of the following configurations:
         * <pre>
         *     z=a                 z=c           z=a               z=c
         *    /  \                /  \          /  \              /  \
         *   t0  y=b             y=b  t3       t0   y=c          y=a  t3
         *      /  \            /  \               /  \         /  \
         *     t1  x=c         x=a  t2            x=b  t3      t0   x=b
         *        /  \        /  \               /  \              /  \
         *       t2  t3      t0  t1             t1  t2            t1  t2
         * </pre>
         * The subtree will be restructured so that the node with key b becomes its root.
         * <pre>
         *           b
         *         /   \
         *       a       c
         *      / \     / \
         *     t0  t1  t2  t3
         * </pre>
         * Caller should ensure that x has a grandparent.
         */
        public Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
            Position<Entry<K, V>> y = parent(x);
            Position<Entry<K, V>> z = parent(y);
            if ((x == right(y)) == (y == right(z))) {       // matching alignments
                rotate(y);                                  // single rotation (of y)
                return y;                                   // y is new subtree root
            } else {                                        // opposite alignment
                rotate(x);                                  // double rotation (of x)
                rotate(x);
                return x;                                   // x is new subtree root
            }
        }

    } // -------- end of nested BalanceableBinaryTree class --------

    protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

    public TreeMap() {
        super();                        // the AbstractSortedMap constructor
        tree.addRoot(null);          // create a sentinel leaf as root
    }

    public TreeMap(Comparator<K> comp) {
        super(comp);
        tree.addRoot(null);
    }

    @Override
    public int size() {
        return (tree.size() - 1) / 2;   // only internal nodes have entries
    }

    @Override
    public V get(K key) throws IllegalArgumentException {
        checkKey(key);              // may throw IllegalArgumentException
        Position<Entry<K, V>> p = treeSearch(root(), key);
        rebalanceAccess(p);         // hook for balanced tree subclass
        if (isExternal(p)) {
            return null;            // unsuccessful search
        }
        return p.getElement().getValue();
    }

    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        checkKey(key);              // may throw IllegalArgumentException
        Entry<K, V> newEntry = new MapEntry<>(key, value);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (isExternal(p)) {        // key is new
            expandExternal(p, newEntry);
            rebalanceInsert(p);     // hook for balanced tree subclasses
            return null;
        } else {
            V old = p.getElement().getValue();
            set(p, newEntry);
            rebalanceAccess(p);     // hook for balanced tree subclasses
            return old;
        }
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
        checkKey(key);              // may throw IllegalArgumentException
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (isExternal(p)) {        // key not found
            rebalanceAccess(p);     // hook for balanced tree subclasses;
            return null;
        } else {
            V old = p.getElement().getValue();
            if (isInternal(left(p)) && isInternal(right(p))) {  // both children are internal
                Position<Entry<K, V>> replacement = treeMax(left(p));
                set(p, replacement.getElement());
                p = replacement;
            }   // now p has at most one child that is an internal node
            Position<Entry<K, V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
            Position<Entry<K, V>> sib = sibling(leaf);
            remove(leaf);
            remove(p);                  // sib is promoted in p's place
            rebalanceDelete(sib);       // hook for balanced tree subclasses
            return old;
        }
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        for (Position<Entry<K,V>> p : tree.inorder()) {
            if (isInternal(p)){
                buffer.add(buffer.size(), p.getElement());
            }
        }
        return buffer;
    }

    @Override
    public Entry<K, V> firstEntry() {
        if (isEmpty()) {
            return null;
        }
        return treeMin(root()).getElement();
    }

    @Override
    public Entry<K, V> lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return treeMax(root()).getElement();
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
        checkKey(key);                  // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p)) {
            return p.getElement();      // exact match
        }
        while (!isRoot(p)) {
            if (p == left(parent(p))){
                return parent(p).getElement();  // parent has next greater key;
            }
            else {
                p = parent(p);
            }
        }
        return  null;               // no such ceiling exists;
    }

    @Override
    public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
        checkKey(key);                  // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p)) {
            return p.getElement();      // exact match
        }
        while (!isRoot(p)){
            if (p == right((parent(p)))) {
                return parent(p).getElement();  // parent has nex lesser key
            } else {
                p = parent(p);
            }
        }
        return null;                    // no such floor exists;
    }

    @Override
    public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p) && isInternal(left(p))) {
            return treeMax(left(p)).getElement();   // this is the predecessor to p
        }
        // otherwise, we had failed search, or match with no left child
        while (!isRoot(p)) {
            if (p == right(parent(p))) {
                return parent(p).getElement();
            } else {
                p = parent(p);
            }
        }
        return null;
    }

    @Override
    public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p) && isInternal(right(p))){
            return treeMax(right(p)).getElement();
        }
        while (!isRoot(p)) {
            if (p == left(parent(p))) {
                return parent(p).getElement();
            } else {
                p = parent(p);
            }
        }
        return null;
    }

    /**
     * Returns an iterable containing all entries with keys in the range from
     * <code>fromKey</code> inclusive to <code>toKey</code> exclusive.
     * @return iterable with keys in desired range
     * @throws IllegalArgumentException if <code>fromKey</code> or <code>toKey</code> is not compatible with the map
     */
    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
        checkKey(fromKey);
        checkKey(toKey);
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        if (compare(fromKey, toKey) < 0) {
            subMapRecurse(fromKey, toKey, root(), buffer);
        }
        return buffer;
    }

    /**
     * utility to fill subMap buffer recursively (while maintaining order)
     */
    private void subMapRecurse(K fromKey, K toKey,
                               Position<Entry<K,V>> p, ArrayList<Entry<K,V>> buffer) {
        if (isInternal(p)){
            if (compare(p.getElement(), fromKey) < 0) {
                subMapRecurse(fromKey, toKey, right(p), buffer);
            } else {
                subMapRecurse(fromKey, toKey, left(p), buffer);         // first consider left subtree
                if (compare(p.getElement(), toKey) < 0) {               // p is within range
                    buffer.add(buffer.size(), p.getElement());          // so add it to buffer,
                    subMapRecurse(fromKey, toKey, right(p), buffer);    // and consider right subtree as well
                }
            }
        }
    }

    /**
     * Utility used when inserting a new entry at a leaf of the tree.
     */
    private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
        tree.set(p, entry);             // store new entry at p
        tree.addLeft(p, null);       // add new sentinel leaves as children
        tree.addRight(p, null);
    }

    protected Position<Entry<K, V>> root() {
        return tree.root();
    }

    protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) {
        return tree.parent(p);
    }

    protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) {
        return tree.left(p);
    }

    protected Position<Entry<K, V>> right(Position<Entry<K, V>> p) {
        return tree.right(p);
    }

    protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) {
        return tree.sibling(p);
    }

    protected boolean isRoot(Position<Entry<K, V>> p) {
        return tree.isRoot(p);
    }

    protected boolean isExternal(Position<Entry<K, V>> p) {
        return tree.isExternal(p);
    }

    protected boolean isInternal(Position<Entry<K, V>> p) {
        return tree.isInternal(p);
    }

    protected void set(Position<Entry<K, V>> p, Entry<K, V> e) {
        tree.set(p, e);
    }

    protected Entry<K, V> remove(Position<Entry<K, V>> p) {
        return tree.remove(p);
    }

    protected void rotate(Position<Entry<K, V>> p) {
        tree.rotate(p);
    }

    protected Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
        return tree.restructure(x);
    }

    /**
     * Returns the position in p's subtree having given key (or else the terminal leaf).
     */
    private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
        if (isExternal(p)) {
            return p;               // key not found; return the final leaf
        }

        int comp = compare(key, p.getElement());
        if (comp == 0) {
            return p;               // key found; return its position
        } else if (comp < 0) {
            return treeSearch(left(p), key);
        } else {
            return treeSearch(right(p), key);
        }
    }

    protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> walk = p;
        while (isInternal(walk)) {
            walk = left(walk);
        }
        return parent(walk);       // we want the parent of the leaf.
        // (leaf is a sentinel with null children, check expandExternal)
    }

    protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> walk = p;
        while (isInternal(walk)) {
            walk = right(walk);
        }
        return parent(walk);       // we want the parent of the leaf.
        // (leaf is a sentinel with null children, check expandExternal)
    }


    // Stubs for balanced search tree operations (subclasses can override)

    /**
     * Rebalances the tree after an insertion of specified position.  This
     * version of the method does not do anything, but it can be
     * overridden by subclasses.
     *
     * @param p the position which was recently inserted
     */
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
    }

    /**
     * Rebalances the tree after a child of specified position has been
     * removed.  This version of the method does not do anything, but it
     * can be overridden by subclasses.
     *
     * @param p the position of the sibling of the removed leaf
     */
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
    }

    /**
     * Rebalances the tree after an access of specified position.  This
     * version of the method does not do anything, but it can be
     * overridden by a subclasses.
     *
     * @param p the Position which was recently accessed (possibly a leaf)
     */
    protected void rebalanceAccess(Position<Entry<K, V>> p) {
    }

    // remainder of class is for debug purposes only

    /**
     * Prints textual representation of tree structure (for debug purpose only).
     */
    protected void dump() {
        dumpRecurse(root(), 0);
    }

    /**
     * This exists for debugging only
     */
    private void dumpRecurse(Position<Entry<K, V>> p, int depth) {
        String indent = (depth == 0 ? "" : String.format("%" + (2 * depth) + "s", ""));
        if (isExternal(p))
            System.out.println(indent + "leaf");
        else {
            System.out.println(indent + p.getElement());
            dumpRecurse(left(p), depth + 1);
            dumpRecurse(right(p), depth + 1);
        }
    }
}
