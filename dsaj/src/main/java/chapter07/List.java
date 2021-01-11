package chapter07;

public interface List<E> {
    int size();

    boolean isEmpty();

    E get(int i) throws IndexOutOfBoundsException;

    /**
     * Replaces the element at index i with e, and returns the replaced element.
     */
    E set(int i, E e) throws IndexOutOfBoundsException;

    /**
     * Inserts element e to be at index i, shifting all subsequent elements later.
     */
    void add(int i, E e) throws IndexOutOfBoundsException;

    E remove(int i) throws IndexOutOfBoundsException;
}
