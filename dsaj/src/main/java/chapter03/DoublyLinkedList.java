package chapter03;

public class DoublyLinkedList<E> {
    private DNode<E> head;
    private DNode<E> tail;
    private int size = 0;

    public DoublyLinkedList() {
        head = new DNode<>(null, null, null);
        tail = new DNode<>(null, head, null);
        head.setNext(tail);
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
        return head.getElement();
    }

    public E last() {
        if (isEmpty()) {
            return null;
        }
        return tail.getElement();
    }

    public void addFirst(E element) {
        addBetween(element, head, head.getNext());
    }

    public void addLast(E element) {
        addBetween(element, tail.getPrev(), tail);
    }

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        return remove(head.getNext());
    }

    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        return remove(tail.getPrev());
    }

    private void addBetween(E element, DNode<E> prev, DNode<E> next) {
        DNode<E> node = new DNode<>(element, prev, next);
        prev.setNext(node);
        next.setPrev(node);
        size++;
    }

    private E remove(DNode<E> node) {
        E element = node.getElement();
        DNode<E> prev = node.getPrev();
        DNode<E> next = node.getNext();
        prev.setNext(next);
        next.setPrev(prev);
        size--;
        return element;
    }
}
