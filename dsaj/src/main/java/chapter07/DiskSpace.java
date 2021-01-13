package chapter07;

public class DiskSpace {
    /**
     * Returns total disk space for subtree of T rooted at p.
     */
    public static int diskSpace(Tree<Integer> T, Position<Integer> p) {
        int subtotal = p.getElement();      // we assume element represents space usage
        for(Position<Integer> c : T.children(p)) {
            subtotal += diskSpace(T, c);
        }
        return subtotal;
    }
}
