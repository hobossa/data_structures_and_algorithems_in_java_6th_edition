package chapter03;

public class CircularlyLinkedList<E> {
    // Don't need a head, because head = tail.getNext()
    private SNode<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return 0 == size;
    }

    public E first() {
        if (isEmpty()) {
            return null;
        }
        return tail.getNext().getElement();
    }

    public E last() {
        if (isEmpty()) {
            return null;
        }
        return tail.getElement();
    }

    public void rotate() {
        if (!isEmpty()) {
            tail = tail.getNext();
        }
    }

    public void addFirst(E element) {
        if (isEmpty()) {
            tail = new SNode<>(element, null);
            tail.setNext(tail);
        } else {
            SNode<E> SNode = new SNode<>(element, tail.getNext());
            tail.setNext(SNode);
        }
        size++;
    }

    public void addLast(E element) {
        addFirst(element);
        //rotate();
        tail = tail.getNext();
    }

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        E element = tail.getNext().getElement();
        if (tail == tail.getNext()) {
            tail = null;
        } else {
            tail.setNext(tail.getNext().getNext());
        }
        size--;
        return element;
    }
}
