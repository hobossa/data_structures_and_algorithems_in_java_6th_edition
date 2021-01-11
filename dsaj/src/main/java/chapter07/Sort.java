package chapter07;

public class Sort {
    public static <T extends Comparable<? super T>> void insertionSort(PositionalList<T> list) {
        Position<T> marker = list.first();
        while (!marker.equals(list.last())) {
            Position<T> pivot = list.after(marker);
            T value = pivot.getElement();
            if (value.compareTo(marker.getElement()) > 0) { // pivot is already sorted
                marker = pivot;
            } else {
                Position<T> walk = marker;  // find leftmost item greater than value
                while (walk != list.first()
                        && list.before(walk).getElement().compareTo(value) > 0) {
                    walk = list.before(walk);
                }
                list.remove(pivot);             // remove pivot entry and
                list.addBefore(walk, value);    // reinsert value in front of walk.
            }
        }
    }
}
