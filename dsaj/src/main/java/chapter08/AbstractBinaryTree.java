package chapter08;

import chapter07.ArrayList;
import chapter07.List;
import chapter07.Position;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {

    @Override
    public Position<E> sibling(Position<E> p) throws IllegalArgumentException {
        Position<E> parent = parent(p);
        if (parent == null) {
            return null;    // p must be the root
        }
        if (p == left(parent)) {
            return right(parent);
        } else {
            return left(parent);
        }
    }

    @Override
    public int numChildren(Position<E> p) throws IllegalArgumentException {
        int count = 0;
        if (left(p) != null) {
            count++;
        }
        if (right(p) != null) {
            count++;
        }
        return count;
    }

    @Override
    public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
        List<Position<E>> snapshot = new ArrayList<>(2);    // max capacity of 2;
        if (left(p) != null){
            snapshot.add(snapshot.size(), left(p));
        }
        if (right(p) != null){
            snapshot.add(snapshot.size(),right(p));
        }
        return snapshot;
    }

    private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        if (left(p) != null) {
            inorderSubtree(left(p), snapshot);
        }
        snapshot.add(snapshot.size(), p);
        if (right(p) != null) {
            inorderSubtree(right(p), snapshot);
        }
    }

    public Iterable<Position<E>> inorder() {
        List<Position<E>> snapshot = new ArrayList<>(size());
        if (!isEmpty()) {
            inorderSubtree(root(), snapshot);
        }
        return snapshot;
    }

    /**
     * Overrides positions to make inorder the default order for binary trees.
     */
    @Override
    public Iterable<Position<E>> positions() {
        return inorder();
    }

    public static <E> int layout(BinaryTree<E> T, Position<E> p, int d, int x) {
        if (T.left(p) != null) {
            x = layout(T, T.left(p), d+1, x);
        }
        // p.getElement().setX(x++);   // post-increment x
        // p.getElement().setY(d);
        x++;
        // the coordiantes of p.getElement() is (x, p)
        if(T.right(p) != null) {
                x = layout(T, T.right(p), d+1, x);
        }
        return  x;
    }
}
