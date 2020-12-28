package chapter03;

class SNode<E> {
    private E element;
    private SNode<E> next;

    public SNode(E element, SNode<E> next) {
        this.element = element;
        this.next = next;
    }

    public E getElement() {
        return element;
    }

    public SNode<E> getNext() {
        return next;
    }

    public void setNext(SNode<E> next) {
        this.next = next;
    }
}
