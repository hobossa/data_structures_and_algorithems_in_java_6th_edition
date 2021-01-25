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

    /**
     * Returns the lowest index at which substring pattern begins in text (or else -1)
     */
    public static int findKMP(char[] text, char[] pattern) {
        int n = text.length;
        int m = pattern.length;
        if (m == 0) {
            return 0;
        }
        int[] fail = computeFailKMP(pattern);
        int j = 0;      // index into text
        int k = 0;      // index into pattern
        while (j < n) {
            if (text[j] == pattern[k]) {    // pattern[0..k] matched thus far
                if (k == m - 1) {           // match is complete
                    return j - m + 1;
                }
                j++;                        // otherwise, try to extend match
                k++;
            } else if (k > 0) {
                k = fail[k - 1];            // reuse suffix of P[0..k-1]
            } else {
                j++;
            }
        }
        return -1;                          // reached end without match
    }

    /**
     * computeFailKMP is a "bootstrapping" process that compares the pattern
     * to itself as in the KMP algorithm. Each time we have two characters
     * that match, we set f(j)=k+1. Note that since we have j>k throughout
     * the execution of the algorithm, f(k-1) is always well defined when we
     * need to use it.
     */
    private static int[] computeFailKMP(char[] pattern) {
        int m = pattern.length;
        int[] fail = new int[m];    // by default, all overlaps are zero
        int j = 1;
        int k = 0;
        while (j < m) { // compute fail[j] during this pass, if nonzero
            if (pattern[j] == pattern[k]) { // k+1 characters match thus far
                fail[j] = k + 1;
                j++;
                k++;
            } else if (k > 0) {
                k = fail[k - 1];  // k follows a matching prefix
            } else {
                j++;        // no match found starting at j
            }
        }
        return fail;
    }
}

