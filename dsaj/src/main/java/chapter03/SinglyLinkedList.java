package chapter03;

public class SinglyLinkedList<E> implements Cloneable{

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

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        // we have ensured that the parameter was an instance of the SinglyLinkedList
        // class above (or an appropriate subclass), and so we can safely cast it to a
        // SinglyLinkedList, so that we may access its instance variables size and head.
        // There is subtlety involving the treatment of Java's generics framework. Although
        // our SinglyLinkedList class has a declared formal type parameter <E>, we cannot
        // detect at runtime whether the other list has a matching type. (For those interested,
        // look online for a discussion of erasure in Java.) So we revert to using a more
        // classic approach with nonparameterized type SinglyLinkedList at here, and
        // nonparameterized SNode declarations. If the two lists have incompatible types,
        // this will be detected when calling the equals method on corresponding elements.

        SinglyLinkedList other = (SinglyLinkedList) obj;    // use nonparameterized type
        if (size != other.size) {
            return false;
        }
        SNode walkA = head;
        SNode walkB = other.head;
        while (walkA != null) {
            if (!walkA.getElement().equals(walkB.getElement())) {
                return false;
            }
            walkA = walkA.getNext();
            walkB = walkA.getNext();
        }
        return true;
    }

    @Override
    protected SinglyLinkedList<E> clone() throws CloneNotSupportedException {
        // always use inherited Object.clone() to create the initial copy.
        SinglyLinkedList<E> other = (SinglyLinkedList<E>) super.clone();
        if (size > 0) {
            other.head = new SNode<>(head.getElement(), null);
            SNode<E> walk = head.getNext();
            SNode<E> otherTail = other.head;
            while (walk != null) {
                SNode<E> node = new SNode<>(walk.getElement(), null);
                otherTail.setNext(node);
                otherTail = node;
                walk = walk.getNext();
            }
        }
        return other;
    }
}
