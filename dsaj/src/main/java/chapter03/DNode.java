package chapter03;

public class DNode<E> {
    private E element;
    private DNode<E> prev;
    private DNode<E> next;

    public DNode(E element, DNode<E> prev, DNode<E> next) {
        this.element = element;
        this.prev = prev;
        this.next = next;
    }

    public E getElement() {
        return element;
    }

    public DNode<E> getPrev() {
        return prev;
    }

    public DNode<E> getNext() {
        return next;
    }

    public void setPrev(DNode<E> prev) {
        this.prev = prev;
    }

    public void setNext(DNode<E> next) {
        this.next = next;
    }
}
