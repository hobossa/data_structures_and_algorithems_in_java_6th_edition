package chapter07;

public class LinkedPositionalList<E> implements PositionalList<E> {
    //-------- nested Node class --------
    private static class Node<E> implements Position<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public E getElement() throws IllegalStateException {
            if (next == null) {
                throw new IllegalStateException("Position no longer valid");
            }
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }   // -------- end of nested Node class --------

    private Node<E> header;     // header sentinel
    private Node<E> trailer;    // trailer sentinel
    private int size = 0;

    public LinkedPositionalList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return 0 == size;
    }

    @Override
    public Position<E> first() {
        return position(header.getNext());
    }

    @Override
    public Position<E> last() {
        return position(trailer.getPrev());
    }

    @Override
    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getPrev());
    }

    @Override
    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getNext());
    }

    @Override
    public Position<E> addFirst(E element) {
        return addBetween(element, header, header.getNext());
    }

    @Override
    public Position<E> addLast(E element) {
        return addBetween(element, trailer.getPrev(), trailer);
    }

    @Override
    public Position<E> addBefore(Position<E> p, E element) {
        Node<E> node = validate(p);
        return addBetween(element, node.getPrev(), node);
    }

    @Override
    public Position<E> addAfter(Position<E> p, E element) {
        Node<E> node = validate(p);
        return addBetween(element, node, node.getNext());
    }

    @Override
    public E set(Position<E> p, E element) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E answer = node.getElement();
        node.setElement(element);
        return answer;
    }

    @Override
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();

        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        size--;

        E answer = node.getElement();
        node.setElement(null);      // help with garbage collection
        node.setNext(null);         // add convention for defunct node
        node.setPrev(null);
        return answer;
    }

    /**
     * Validates the position and returns it as a node.
     *
     * @param p
     * @return return the given Position as a Node.
     * @throws IllegalArgumentException
     */
    private Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) {
            throw new IllegalArgumentException("Invalid p");
        }
        Node<E> node = (Node<E>) p;
        if (node.getNext() == null) {
            throw new IllegalArgumentException("p is no longer in the list");
        }
        return node;
    }

    /**
     * @param node
     * @return the given node as a Position (or null, if it is a sentinel.)
     */
    private Position<E> position(Node<E> node) {
        if (node == header || node == trailer) {
            return null;
        }
        return node;
    }

    /**
     * Adds element e to the lined list between the given nodes.
     * @param e
     * @param pred
     * @param succ
     * @return the new node.
     */
    private Position<E> addBetween(E e, Node<E> pred, Node<E> succ) {
        Node<E> newest = new Node<>(e, pred, succ);
        pred.setNext(newest);
        succ.setPrev(newest);
        size++;
        return newest;
    }
}
