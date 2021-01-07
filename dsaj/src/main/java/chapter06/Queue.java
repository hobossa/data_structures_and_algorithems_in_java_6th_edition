package chapter06;

public interface Queue<E> {
    int size();
    boolean isEmpty();
    /** Inserts an element at the rear of the queue. */
    void enqueue(E e);
    /** Removes and returns the first element of the queue (null if empty). */
    E dequeue();
    /** Returns, but does not remove, the first element of the queue (null if empty).  */
    E first();
}
