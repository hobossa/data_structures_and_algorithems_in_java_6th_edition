package chapter06;

public class ArrayQueue<E> implements Queue<E> {
    public static final int CAPACITY = 1000; // default array capacity
    private E[] data;
    private int f = 0;      // index of the front element
    private int sz = 0;

    public ArrayQueue() {
        this(CAPACITY);
    }

    public ArrayQueue(int capacity) {
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
    public void enqueue(E e) throws IllegalStateException {
        if (sz == data.length) {
            throw new IllegalStateException("Queue is full");
        }
        int avail = (f + sz) % data.length;
        data[avail] = e;
        sz++;
    }

    @Override
    public E dequeue() {
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
    public E first() {
        if (isEmpty()) {
            return null;
        }
        return data[f];
    }
}
