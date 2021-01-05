package chapter05;

import java.io.File;
import java.util.Objects;

public class DiskUsage {
    public static long diskUsage(File root) {
        long total = root.length();
        if (root.isDirectory()) {
            for (String childPath : Objects.requireNonNull(root.list())) {
                File child = new File(root, childPath);
                total += diskUsage(child);
            }
        }
        System.out.println(total + "\t" + root);
        return total;
    }

    public static void main(String[] args) {
        DiskUsage.diskUsage(new File("./"));
    }
}
