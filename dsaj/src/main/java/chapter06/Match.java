package chapter06;

public class Match {
    public static boolean isDelimitersMatched(String expression) {
        final String opening = "({[";
        final String closing = ")}]";
        Stack<Character> buffer = new LinkedStack<>();
        for (char c : expression.toCharArray()) {
            if (opening.indexOf(c) != -1) {
                buffer.push(c);
            } else if (closing.indexOf(c) != -1) {
                if (buffer.isEmpty()) {
                    return false;   // nothing to match
                }
                if (closing.indexOf(c) != opening.indexOf(buffer.pop())) {
                    return false;   // mismatched delimiter
                }
            }
        }
        return buffer.isEmpty();    // were all opening delimiters matched?
    }


    public static boolean isHTMLMatched(String html) {
        Stack<String> buffer = new LinkedStack<>();
        int j = html.indexOf('<');      // find first '<' character (if any)
        while (j != -1) {
            int k = html.indexOf('>', j + 1); // find next '>' character
            if (k == -1) {
                return false;
            }
            String tag = html.substring(j + 1, k);    // strip away < >
            if (!tag.startsWith("/")) {      // this is an opening tag
                buffer.push(tag);
            } else {        // this is a closing tag
                if (buffer.isEmpty()) {
                    return false;
                }
                if (!tag.substring(1).equals(buffer.pop())) {
                    return false;
                }
            }
            j = html.indexOf('<', k + 1);
        }
        return buffer.isEmpty();        // were all opening tags matched?
    }
}
