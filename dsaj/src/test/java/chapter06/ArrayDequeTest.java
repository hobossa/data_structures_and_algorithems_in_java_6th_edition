package chapter06;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void size() {
        Deque<Integer> deque = new ArrayDeque<>();
        assertEquals(0, deque.size());
        deque.addLast(1);
        assertEquals(1, deque.size());
        deque.addLast(2);
        assertEquals(2, deque.size());
        deque.addLast(3);
        assertEquals(3, deque.size());
    }

    @Test
    public void isEmpty() {
        Deque<Integer> deque = new ArrayDeque<>();
        assertTrue(deque.isEmpty());
        deque.addFirst(1);
        assertFalse(deque.isEmpty());
    }

    @Test
    public void first() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        assertEquals(Integer.valueOf(1), deque.first());
    }

    @Test
    public void last() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        assertEquals(Integer.valueOf(1), deque.last());
    }

    @Test
    public void addFirst() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        assertEquals(Integer.valueOf(1), deque.first());
    }

    @Test
    public void addLast() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        assertEquals(Integer.valueOf(1), deque.last());
    }

    @Test
    public void removeFirst() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        assertEquals(Integer.valueOf(1), deque.removeFirst());
    }

    @Test
    public void removeLast() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        assertEquals(Integer.valueOf(1), deque.removeLast());
    }
}