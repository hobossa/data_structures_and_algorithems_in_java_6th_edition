package chapter10;

import chapter09.Entry;

public class CostPerformanceDatabase {
    SortedMap<Integer, Integer> map = new SortedTableMap<>();

    public CostPerformanceDatabase() {
    }

    /**
     * Returns the (cost, performance) entry with largest cost not exceeding c.
     */
    public Entry<Integer,Integer> best(int cost) {
        return map.floorEntry(cost);
    }

    /**
     * Add a new entry with given cost c and performance p.
     */
    public void add(int c, int p) {
        Entry<Integer, Integer> other = map.floorEntry(c);  // other is at least as cheap
        if (other != null && other.getValue() >= p) {
            return;         // if its performance is as good, (c,p) is dominated, so ignore
        }
        map.put(c, p);      // else, add(c,p) to database
        // and now remove any entries that are dominated by the new one
        other = map.higherEntry(c);     // other is more expensive than c
        while (other != null && other.getValue()<=p) {
            map.remove(other.getKey());
            other = map.higherEntry(c);
        }

    }
}
