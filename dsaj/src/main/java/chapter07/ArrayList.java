package chapter07;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E>, Iterable<E> {
    // -------- nested ArrayIterator class --------
    private class ArrayIterator implements Iterator<E> {
        private int j = 0;
        private boolean removable = false;

        @Override
        public boolean hasNext() {
            return j < size;        // size if field of outer instance.
            // as a inner class ArrayList has access to fields of outer instance.
        }

        @Override
        public E next() throws NoSuchElementException {
            if (j == size) {
                throw new NoSuchElementException("No next element");
            }
            removable = true;
            return data[j++];
        }

        @Override
        public void remove() throws IllegalStateException{
            if (!removable) {
                throw new IllegalStateException("nothing to remove");
            }
            ArrayList.this.remove(j-1);
            j--;
            removable = false;
        }
    } // -------- end of nested ArrayIterator class --------

    public static final int CAPACITY = 32;
    private E[] data;
    private int size = 0;

    public ArrayList() {
        this(CAPACITY);
    }

    public ArrayList(int capacity) {
        data = (E[]) new Object[capacity];
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
    public E get(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        return data[i];
    }

    @Override
    public E set(int i, E e) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        E temp = data[i];
        data[i] = e;
        return temp;
    }

    @Override
    public void add(int i, E e) throws IndexOutOfBoundsException, IllegalStateException {
        checkIndex(i, size + 1);
        if (size == data.length) {
            resize(2 * data.length);
        }
        for (int k = size; k > i; k--) {
            data[k] = data[k - 1];
        }
        data[i] = e;
        size++;
    }

    @Override
    public E remove(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        E e = data[i];
        for (int j = i; j < size - 1; j++) {
            data[j] = data[j + 1];
        }
        data[size - 1] = null;
        size--;
        return e;
    }

    public Iterator<E> iterator() {
        return new ArrayIterator();
    }

    protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("Illegal index: " + i);
        }
    }

    protected void resize(int capacity) {
        E[] temp = (E[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = data[i];
        }
        data = temp;
    }
}
