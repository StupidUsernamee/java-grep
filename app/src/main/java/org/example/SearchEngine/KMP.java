package org.example.SearchEngine;

import org.example.Utility.SearchResult;
import java.util.ArrayList;
import java.util.List;

public class KMP implements SearchStrategy {

    private int[] lps;
    private final String pattern;

    public KMP(String pattern) {
        this.pattern = pattern;
        this.lps = computeLPS(pattern); // Precompute LPS once in the constructor
    }

    @Override
    public List<SearchResult> search(String text, String pattern, int lineNumber) {
        List<SearchResult> results = new ArrayList<>();
        int textLen = text.length();
        int patternLen = pattern.length();
        int i = 0, j = 0; // i → text index, j → pattern index

        while (i < textLen) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == patternLen) { // Found a match
                results.add(new SearchResult(lineNumber, text));
                j = lps[j - 1]; // Use LPS to shift pattern efficiently
            } else if (i < textLen && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1]; // Use LPS table to avoid unnecessary comparisons
                } else {
                    i++;
                }
            }
        }

        return results;
    }

    private int[] computeLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1]; // Fall back using LPS table
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }
}
