package chapter07;

import chapter06.LinkedQueue;
import chapter06.Queue;

/**
 * An abstract base class providing some functionality of the Tree interface.
 */
public abstract class AbstractTree<E> implements Tree<E> {
    @Override
    public boolean isInternal(Position<E> p) throws IllegalArgumentException {
        return numChildren(p) > 0;
    }

    @Override
    public boolean isExternal(Position<E> p) throws IllegalArgumentException {
        return numChildren(p) == 0;
    }

    @Override
    public boolean isRoot(Position<E> p) throws IllegalArgumentException {
        return p == root();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    public int depth(Position<E> p) {
        if (isRoot(p)) {
            return 0;
        } else {
            return 1 + depth(parent(p));
        }
    }

    /**
     * @return the height of the tree.
     */
    private int heightBad() {   // works, but quadratic worst-case time
        int h = 0;
        for (Position<E> p : positions()) {
            if (isExternal(p)) { // only consider leaf positions
                h = Math.max(h, depth(p));
            }
        }
        return h;
    }

    public int height(Position<E> p) {
        int h = 0;
        for (Position<E> c : positions()) {
            h = Math.max(h, 1 + height(c));
        }
        return h;
    }

    private void preorderSubTree(Position<E> p, List<Position<E>> snapshot) {
        snapshot.add(snapshot.size(), p);
        for (Position<E> c : children(p)) {
            preorderSubTree(c, snapshot);
        }
    }

    public Iterable<Position<E>> preorder() {
        List<Position<E>> snapshot = new ArrayList<>(size());
        if (!isEmpty()) {
            preorderSubTree(root(), snapshot);
        }
        return snapshot;
    }

    private void postorderSubTree(Position<E> p, List<Position<E>> snapshot) {
        for (Position<E> c : children(p)) {
            preorderSubTree(c, snapshot);
        }
        snapshot.add(snapshot.size(), p);
    }

    public Iterable<Position<E>> postorder() {
        List<Position<E>> snapshot = new ArrayList<>(size());
        if (!isEmpty()) {
            postorderSubTree(root(), snapshot);
        }
        return snapshot;
    }

    public Iterable<Position<E>> breadthfirst() {
        List<Position<E>> snapshot = new ArrayList<>(size());
        if (!isEmpty()) {
            Queue<Position<E>> fringe = new LinkedQueue<>();
            fringe.enqueue(root());
            while (!fringe.isEmpty()) {
                Position<E> p = fringe.dequeue();
                snapshot.add(snapshot.size(), p);
                for (Position<E> c : children(p)) {
                    fringe.enqueue(c);
                }
            }
        }
        return snapshot;
    }

    private static String spaces(int n) {
        return " ".repeat(Math.max(0, n));
    }

    public static <E> void printPreorderIndent(Tree<E> T, Position<E> p, int d) {
        System.out.print(spaces(2 * d) + p.getElement());
        for (Position<E> c : T.children(p)) {
            printPreorderIndent(T, c, d + 1);
        }
    }

    /**
     * Prints labeled representation of subtree of T rooted at p having depth d.
     */
    public static <E> void printPreorderLabeled(Tree<E> T, Position<E> p, ArrayList<Integer> path) {
        int d = path.size();        // depth equals the length of the path
        System.out.print(spaces(2 * d));
        for (int i = 0; i < d; i++) {
            System.out.print(path.get(i) + (i == d - 1 ? " " : "."));
        }
        System.out.print(p.getElement());
        path.add(path.size(), 1);       // add path entry for the first child
        for (Position<E> c : T.children(p)) {
            printPreorderLabeled(T, c, path);
            path.set(d, 1 + path.get(d));   // increment last entry of path
        }
        path.remove(d);     // restore path to its incoming state
    }

    /**
     * Prints parenthesized representation of subtree of T rotted at p.
     */
    public static <E> void parenthesize(Tree<E> T, Position<E> p) {
        System.out.print(p.getElement());
        if (T.isInternal(p)) {
            boolean firstTime = true;
            for (Position<E> c : T.children(p)) {
                System.out.print((firstTime ? " (" : ", "));
                firstTime = false;
                parenthesize(T, c);
            }
            System.out.print(") ");
        }
    }
}
