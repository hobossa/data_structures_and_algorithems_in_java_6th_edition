package chapter13;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PatternMatching {
    /**
     * Returns the lowest index at which substring pattern begins in text (or else -1)
     */
    public static int findBrute(char[] text, char[] pattern) {
        int n = text.length;
        int m = pattern.length;
        for (int i = 0; i < n - m; i++) {
            int k = 0;
            while (k < m && text[i + k] == pattern[k]) {
                k++;
            }
            if (k == m) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the lowest index at which substring pattern begins in text (or else -1)
     */
    public static int findBoyerMoore(char[] text, char[] pattern) {
        int n = text.length;
        int m = pattern.length;
        if (m == 0) {
            return 0;
        }
        // the 'last' map in which the last[c] is the index of the last(rightmost)
        // occurrence of c in the pattern.
        // Otherwise, we conventionally define last(c) = -1.
        Map<Character, Integer> last = new HashMap<>();
        // for (int i = 0; i < n; i++) {
        //    last.put(text[i], -1);      // set -1 as default for all text characters.
        // }
        for (int i = 0; i < m; i++) {
            last.put(pattern[i], i);    // rightmost occurrence in pattern is last
        }
        Function<Character, Integer> getLast = (Character c) -> {
            Integer lN = last.get(c);
            return (null == lN ? -1 : lN);
        };

        int i = m - 1;      // an index into the text
        int k = m - 1;      // an index into the pattern
        while (i < n) {
            if (text[i] == pattern[k]) {
                if (k == 0) {
                    return i;
                }
                i--;
                k--;
            } else {
                i += m - Math.min(k, 1 + getLast.apply(text[i]));
                k = m - 1;
            }
        }
        return -1;
    }
}
