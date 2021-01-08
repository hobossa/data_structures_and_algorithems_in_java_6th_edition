package chapter06;

public class ArrayDeque<E> implements Deque<E> {
    public static final int CAPACITY = 1000; // default array capacity
    private E[] data;
    private int f = 0;      // index of the front element
    private int sz = 0;

    public ArrayDeque() {
        this(CAPACITY);
    }

    public ArrayDeque(int capacity) {
        data = (E[]) new Object[capacity];
    }


    @Override
    public int size() {
        return sz;
    }

    @Override
    public boolean isEmpty() {
        return 0 == sz;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            return null;
        }
        return data[f];
    }

    @Override
    public E last() {
        if (isEmpty()) {
            return null;
        }
        int avail = (f + sz - 1) % data.length;
        return data[avail];
    }

    @Override
    public void addFirst(E e) throws IllegalStateException {
        if (sz == data.length) {
            throw new IllegalStateException("Deque is full");
        }
        f = (f + data.length - 1) % data.length;
        data[f] = e;
        sz++;
    }

    @Override
    public void addLast(E e) throws IllegalStateException {
        if (sz == data.length) {
            throw new IllegalStateException("Deque is full");
        }
        int tail = (f + sz) % data.length;
        data[tail] = e;
        sz++;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        E e = data[f];
        data[f] = null;
        f = (f + 1) % data.length;
        sz--;
        return e;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        int tail = (f + sz - 1) % data.length;
        E e = data[tail];
        data[tail] = null;
        sz--;
        return e;
    }
}
