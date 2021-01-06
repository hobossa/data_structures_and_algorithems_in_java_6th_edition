package chapter05;

import java.util.*;

public class PuzzleSolve {
    public static void puzzleSolve(int k, LinkedHashSet<Character> s, Set<Character> u) {
        if (k < 0) {
            return;
        }
        if (k == 0) {
            System.out.println(s.toString());
        }

        for (Character c : u) {
            if (s.contains(c)) {
                continue;
            }
            s.add(c);
            puzzleSolve(k-1, s, u);
            s.remove(c);
        }
    }

    public static void main(String[] args) {

        LinkedHashSet<Character> s = new LinkedHashSet<>();
        Set<Character> u = new HashSet<>();
        u.add('a');
        u.add('b');
        u.add('c');
        u.add('d');
        puzzleSolve(3, s, u);
    }
}
