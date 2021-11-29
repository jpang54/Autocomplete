import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Autocomplete {
    private Term[] arr; // defensive copy of client array

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException();
        }

        // copy client array and sort it in lexicographic order
        int termLen = terms.length;
        arr = new Term[termLen];
        for (int i = 0; i < termLen; i++) {
            if (terms[i] == null) {
                throw new IllegalArgumentException();
            }
            arr[i] = terms[i];
        }
        Arrays.sort(arr);
    }

    // Returns all terms that start with the given prefix, in descending order
    // of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }

        int prefixLen = prefix.length();

        // BinarySearchDeluxe requires Term argument
        Term term1 = new Term(prefix, 0);

        Comparator<Term> prefixComparator = Term.byPrefixOrder(prefixLen);
        int firstIndex = BinarySearchDeluxe.firstIndexOf(arr, term1,
                                                         prefixComparator);
        // no matches, return empty array
        if (firstIndex == -1) return new Term[0];

        int lastIndex = BinarySearchDeluxe.lastIndexOf(arr, term1,
                                                       prefixComparator);
        // copy all matches into new array
        Term[] matches = new Term[lastIndex - firstIndex + 1];
        for (int i = 0; i <= lastIndex - firstIndex; i++) {
            matches[i] = arr[i + firstIndex];
        }
        Comparator<Term> reverseComparator = Term.byReverseWeightOrder();
        Arrays.sort(matches, reverseComparator);

        return matches;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }

        int prefixLen = prefix.length();

        // BinarySearchDeluxe requires Term argument
        Term term1 = new Term(prefix, 0);
        Comparator<Term> prefixComparator = Term.byPrefixOrder(prefixLen);

        int firstIndex = BinarySearchDeluxe.firstIndexOf(arr, term1,
                                                         prefixComparator);
        if (firstIndex == -1) return 0; // check if no matches

        int lastIndex = BinarySearchDeluxe.lastIndexOf(arr, term1,
                                                       prefixComparator);

        return lastIndex - firstIndex + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        /* @citation Copied from: https://www.cs.princeton.edu/courses/archive/
        fall21/cos226/assignments/autocomplete/specification.php.
        Accessed 10/1/2021. */

        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }

}
