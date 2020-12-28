package chapter03;

public class SinglyLinkedList<E> {

    private Node<E> head = null;
    private Node<E> tail = null;
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
        head = new Node<E>(element, head.getNext());
        if (isEmpty()) {
            tail = head;
        }
        size++;
    }

    public void addLast(E element) {
        Node<E> node = new Node<E>(element, null);
        if (isEmpty()) {
            head = node;
        } else {
            tail.setNext(node);
        }
        tail = node;
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
