import java.util.Comparator;

public class Term implements Comparable<Term> {
    private String query; // word or phrase
    private long weight; // frequency of use in searches

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null || weight < 0) {
            throw new IllegalArgumentException();
        }
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    // returns positive, negative, or zero if Term b has a greater, lesser, or
    // equal weight than Term a
    private static class ReverseWeightOrder implements Comparator<Term> {
        /* @citation Adapted from: https://edstem.org/us/courses/7744/lessons/
        21591/slides/135463. Accessed 10/1/2021. */
        public int compare(Term a, Term b) {
            return Long.compare(b.weight, a.weight);
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        return new PrefixOrder(r);
    }

    private static class PrefixOrder implements Comparator<Term> {
        /* @citation Adapted from: https://edstem.org/us/courses/7744/lessons/
        21591/slides/135463. Accessed 10/1/2021. */
        private int substring; // saves the given prefix length

        // initializes the PrefixOrder with the prefix length
        public PrefixOrder(int r) {
            if (r < 0) {
                throw new IllegalArgumentException();
            }
            substring = r;
        }

        // compares the prefixes in lexicographic order
        public int compare(Term a, Term b) {
            int lenA = a.query.length();
            int lenB = b.query.length();

            // compare only up to the smallest string
            int min = Math.min(substring, Math.min(lenA, lenB));
            for (int i = 0; i < min; i++) {
                int diff = a.query.charAt(i) - b.query.charAt(i);
                if (diff < 0) {
                    return -1;
                }
                else if (diff > 0) {
                    return 1;
                }
            }

            // return which string is smaller if both have matching prefixes
            // and the smallest string is not the prefix
            if (min != substring) {
                if (lenA < lenB) {
                    return -1;
                }
                else if (lenA > lenB) {
                    return 1;
                }
            }
            return 0;

        }
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);

    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return weight + "\t" + query;

    }

    // unit testing (required)
    public static void main(String[] args) {
        Term test1 = new Term("dog", 1);
        Term test2 = new Term("dogcatcher", 2);
        System.out.println(test1.compareTo(test2)); // -7

        Term test3 = new Term("cat", 3);
        Term test4 = new Term("do", 4);

        Comparator<Term> comparator = Term.byReverseWeightOrder();
        System.out.println(comparator.compare(test1, test2)); // 1
        System.out.println(comparator.compare(test3, test2)); // -1

        Comparator<Term> comparator2 = Term.byPrefixOrder(3);
        System.out.println(comparator2.compare(test1, test2)); // 0
        System.out.println(comparator2.compare(test2, test4)); // 1
        System.out.println(comparator2.compare(test3, test1)); // -1

        System.out.println(test1); // 1 \t dog


    }

}
