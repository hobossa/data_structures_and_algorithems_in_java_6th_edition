package chapter06;

public class Josephus {
    public static <E> E josephus(CircularQueue<E> queue, int k) {
        if (queue.isEmpty()) {
            return null;
        }
        while (queue.size() > 1) {
            for (int i = 0; i < k - 1; i++) {
                queue.rotate();
            }
            E e = queue.dequeue();  // remove the front element from the collection
            System.out.println("    " + e + " is out");
        }
        return queue.dequeue();     // the winner
    }

    public static <E> CircularQueue<E> buildQueue(E[] a) {
        CircularQueue<E> queue = new LinkedCircularQueue<>();
        for (E e : a) {
            queue.enqueue(e);
        }
        return queue;
    }

    public static void main(String[] args) {
        String[] a1 = {"Alice", "Bob", "Cindy", "Doug", "Ed", "Fred"};
        String[] a2 = {"Gene", "Hope", "Irene", "Jack", "Kim", "Lance"};
        String[] a3 = {"Mike", "Roberto"};
        System.out.println("First winner is " + josephus(buildQueue(a1), 3));
        System.out.println("Second winner is " + josephus(buildQueue(a2), 10));
        System.out.println("Third winner is " + josephus(buildQueue(a3), 7));
    }
}
