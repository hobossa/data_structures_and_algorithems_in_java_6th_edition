package chapter07;

public interface PositionalList<E> extends Iterable<E> {
    int size();

    boolean isEmpty();

    Position<E> first();

    Position<E> last();

    /**
     * @param p
     * @return the Position immediately before Position p (or null, if p is first)
     * @throws IllegalArgumentException if p is an invalid Position instance.
     */
    Position<E> before(Position<E> p) throws IllegalArgumentException;

    Position<E> after(Position<E> p) throws IllegalArgumentException;

    /**
     * Insert element e at the front of the list
     *
     * @param e
     * @return the new Position of e.
     */
    Position<E> addFirst(E e);

    Position<E> addLast(E e);

    Position<E> addBefore(Position<E> p, E e);

    Position<E> addAfter(Position<E> p, E e);

    E set(Position<E> p, E e) throws IllegalArgumentException;

    E remove(Position<E> p) throws IllegalArgumentException;

    Iterable<Position<E>> positions();
}
