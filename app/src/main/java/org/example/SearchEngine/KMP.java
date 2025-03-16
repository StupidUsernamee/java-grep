package org.example.SearchEngine;

import org.example.Utility.SearchResult;
import java.util.ArrayList;
import java.util.List;

// KMP implements the SearchStrategy interface and provides the Knuth-Morris-Pratt (KMP) search algorithm for pattern matching.
public class KMP implements SearchStrategy {

    // Array to store the Longest Prefix Suffix (LPS) values for the pattern
    private int[] lps;
    private final String pattern; // The pattern to be searched for

    // Constructor that initializes the pattern and precomputes the LPS array
    public KMP(String pattern) {
        this.pattern = pattern;
        this.lps = computeLPS(pattern); // Precompute LPS once in the constructor for efficiency
    }

    // The search method implements the KMP pattern matching algorithm.
    // It returns a list of SearchResult objects containing the matching results.
    @Override
    public List<SearchResult> search(String text, String pattern, int lineNumber) {
        List<SearchResult> results = new ArrayList<>();
        int textLen = text.length();
        int patternLen = pattern.length();
        int i = 0, j = 0; // i → text index, j → pattern index

        // Perform the search for the pattern within the text using the KMP algorithm
        while (i < textLen) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++; // Move to the next character in the text
                j++; // Move to the next character in the pattern
            }

            // If we have found a match, add the result and shift the pattern using the LPS array
            if (j == patternLen) {
                results.add(new SearchResult(lineNumber, text)); // Add the result with the current line number
                j = lps[j - 1]; // Use the LPS array to shift the pattern efficiently
            } else if (i < textLen && text.charAt(i) != pattern.charAt(j)) {
                // If there's a mismatch, use the LPS table to avoid unnecessary comparisons
                if (j != 0) {
                    j = lps[j - 1]; // Use LPS to skip characters and avoid re-checking already matched part
                } else {
                    i++; // If no LPS fallback, just move to the next character in the text
                }
            }
        }

        return results;
    }

    // Helper method to compute the LPS (Longest Prefix Suffix) array for the given pattern
    private int[] computeLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m]; // Array to store the LPS values
        int len = 0; // Length of the previous longest prefix suffix
        int i = 1; // Start from index 1, as the first character always has LPS value 0

        // Build the LPS array
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++; // Increase the length of the matched prefix-suffix
                lps[i] = len; // Set the LPS value for index i
                i++; // Move to the next character in the pattern
            } else {
                if (len != 0) {
                    len = lps[len - 1]; // Fallback using the LPS table
                } else {
                    lps[i] = 0; // If no match, set the LPS value to 0
                    i++; // Move to the next character
                }
            }
        }

        return lps; // Return the computed LPS array
    }
}
