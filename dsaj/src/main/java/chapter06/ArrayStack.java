package chapter06;

public class ArrayStack<E> implements Stack<E> {
    public static final int CAPACITY = 1000; // default array capacity
    private E[] data;
    private int t = -1;     // index of top element in Stack

    public ArrayStack(int capacity) {
        data = (E[]) new Object[capacity];
    }

    public ArrayStack() {
        this(CAPACITY);
    }

    @Override
    public int size() {
        return t + 1;
    }

    @Override
    public boolean isEmpty() {
        return t == -1;
    }

    @Override
    public void push(E e) throws IllegalStateException {
        if (data.length == size()) {
            throw new IllegalStateException("Stack is full");
        }
        data[++t] = e;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        E e = data[t];
        data[t] = null; // do not forget to deference to help garbage collection.
        t--;
        return e;
    }

    @Override
    public E top() {
        if (isEmpty()) {
            return null;
        }
        return data[t];
    }
}
