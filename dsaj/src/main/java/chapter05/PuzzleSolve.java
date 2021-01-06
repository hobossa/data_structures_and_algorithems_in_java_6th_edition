package chapter05;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PuzzleSolve {
    public static void puzzleSolve(int k, List<Character> s, Set<Character> u) {
        if (k <= 0) {
            return;
        }
        System.out.println(String.valueOf(k) + " " + s.toString() + " " + u.toString());
        for (Character c : u) {
            List<Character> ss = new LinkedList<>(s);
            ss.add(c);
            Set<Character> uu = new HashSet<>(u);
            uu.remove(c);
            puzzleSolve(k-1, ss, uu);
        }
    }

    public static void main(String[] args) {
        List<Character> s = new LinkedList<>();
        Set<Character> u = new HashSet<>();
        u.add('a');
        u.add('b');
        u.add('c');
        puzzleSolve(3, s, u);
    }
}
