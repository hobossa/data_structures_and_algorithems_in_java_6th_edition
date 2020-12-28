package chapter03;

public class SinglyLinkedList<E> {

    private SNode<E> head = null;
    private SNode<E> tail = null;
    private int size = 0;

    public SinglyLinkedList() {
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
        head = new SNode<E>(element, head.getNext());
        if (isEmpty()) {
            tail = head;
        }
        size++;
    }

    public void addLast(E element) {
        SNode<E> SNode = new SNode<E>(element, null);
        if (isEmpty()) {
            head = SNode;
        } else {
            tail.setNext(SNode);
        }
        tail = SNode;
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        E element = head.getElement();
        head = head.getNext();
        size--;
        if (isEmpty()) {
            tail = null;
        }
        return element;
    }
}
