import java.util.Comparator;

/* @citation Adapted from: https://www.cs.princeton.edu/courses/archive/fall21/
cos226/lectures/21ElementarySorts.pdf. Accessed 10/1/2021. */
public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key>
            comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException();
        }

        // binary search for the first occurence of key
        int lo = 0;
        int hi = a.length - 1;
        int found = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int compare = comparator.compare(key, a[mid]);
            if (compare < 0) {
                hi = mid - 1;
            }
            else if (compare > 0) {
                lo = mid + 1;
            }
            else {
                hi = mid - 1; // search does not end even if match is found
                found = mid; // the leftmost match found so far
            }
        }
        return found;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key>
            comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException();
        }

        // binary search for the last occurence of key
        int lo = 0;
        int hi = a.length - 1;
        int found = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int compare = comparator.compare(key, a[mid]);
            if (compare < 0) {
                hi = mid - 1;
            }
            else if (compare > 0) {
                lo = mid + 1;
            }
            else {
                lo = mid + 1; // search does not end even if match is found
                found = mid; // the rightmost match found so far
            }
        }
        return found;
    }

    // unit testing (required)
    public static void main(String[] args) {
        /* @citation Copied from: https://www.cs.princeton.edu/courses/archive/
        fall21/cos226/assignments/autocomplete/checklist.php. Accessed 10/1/21.*/

        String[] a = { "A", "A", "C", "G", "G", "T" };
        int index = BinarySearchDeluxe.firstIndexOf(a, "G",
                                                    String.CASE_INSENSITIVE_ORDER);
        System.out.println(index); // 3

        int index1 = BinarySearchDeluxe.lastIndexOf(a, "G",
                                                    String.CASE_INSENSITIVE_ORDER);
        System.out.println(index1); // 4

        String[] b = { "A", "A", "A", "B", "B", "B", "C", "C", "C" };
        int index2 = BinarySearchDeluxe.firstIndexOf(b, "B",
                                                     String.CASE_INSENSITIVE_ORDER);
        System.out.println(index2); // 3

        int index3 = BinarySearchDeluxe.lastIndexOf(b, "B",
                                                    String.CASE_INSENSITIVE_ORDER);
        System.out.println(index3); // 5

        int index4 = BinarySearchDeluxe.firstIndexOf(b, "C",
                                                     String.CASE_INSENSITIVE_ORDER);
        System.out.println(index4); // 6

        int index5 = BinarySearchDeluxe.lastIndexOf(b, "C",
                                                    String.CASE_INSENSITIVE_ORDER);
        System.out.println(index5); // 8

        int index6 = BinarySearchDeluxe.firstIndexOf(b, "A",
                                                     String.CASE_INSENSITIVE_ORDER);
        System.out.println(index6); // 0

        int index7 = BinarySearchDeluxe.lastIndexOf(b, "A",
                                                    String.CASE_INSENSITIVE_ORDER);
        System.out.println(index7); // 2


    }
}
