package org.example.SearchEngine;

import org.example.Utility.SearchResult;

import java.util.ArrayList;
import java.util.List;

// BoyerMoore implements the SearchStrategy interface and provides the Boyer-Moore search algorithm for pattern matching.
public class BoyerMoore implements SearchStrategy {

    // ALPHABET_SIZE defines the range of characters to support, in this case, the full Unicode range
    private static final int ALPHABET_SIZE = 65536;

    // Tables for the Boyer-Moore algorithm: bad character rule and good suffix rule
    private short[] badCharacterTable;
    private int[] goodSuffixTable;

    // Constructor to initialize and preprocess the tables based on the given pattern
    public BoyerMoore(String pattern) {
        preprocessBadCharacter(pattern); // Preprocess the bad character rule
        preprocessGoodSuffix(pattern); // Preprocess the good suffix rule
    }

    // The search method implements the Boyer-Moore pattern matching algorithm.
    // It returns a list of SearchResult objects containing the matching results.
    @Override
    public List<SearchResult> search(String text, String pattern, int lineNumber) {
        List<SearchResult> results = new ArrayList<>();
        int textLen = text.length();
        int patternLen = pattern.length();
        int shift = 0;

        // Perform the search for the pattern within the text using the Boyer-Moore algorithm
        while (shift <= textLen - patternLen) {
            int j = patternLen - 1;

            // Compare the pattern from right to left
            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }

            // If a match is found, add the result and shift the pattern using the good suffix rule
            if (j < 0) {
                results.add(new SearchResult(lineNumber, text)); // Add the result with the current line number
                shift += goodSuffixTable[0]; // Shift the full pattern if matched
            } else {
                // Otherwise, calculate the shifts using the bad character and good suffix rules
                int badCharShift = Math.max(1, j - badCharacterTable[text.charAt(shift + j)]);
                int goodSuffixShift = goodSuffixTable[j];
                shift += Math.max(badCharShift, goodSuffixShift); // Use the larger shift
            }
        }

        return results;
    }

    // Preprocess the bad character table for the given pattern
    private void preprocessBadCharacter(String pattern) {
        int m = pattern.length();
        badCharacterTable = new short[ALPHABET_SIZE]; // Initialize with the full alphabet size
        for (int i = 0; i < ALPHABET_SIZE; i++) badCharacterTable[i] = -1; // Mark all characters as not occurring in the pattern
        for (int i = 0; i < m; i++) badCharacterTable[pattern.charAt(i)] = (short) i; // Set the index of the last occurrence of each character
    }

    // Preprocess the good suffix table for the given pattern
    private void preprocessGoodSuffix(String pattern) {
        int m = pattern.length();
        goodSuffixTable = new int[m];
        int lastPrefix = m;

        // Calculate the good suffix shifts
        for (int i = m - 1; i >= 0; i--) {
            if (isPrefix(pattern, i + 1)) lastPrefix = i + 1; // If the suffix starting at i is a prefix, update the lastPrefix
            goodSuffixTable[i] = lastPrefix - i + m - 1; // Update the good suffix table
        }

        // Further refine the good suffix table based on the suffix function
        for (int i = 0; i < m - 1; i++) {
            int j = m - 1 - suffix(pattern, i); // Calculate the suffix length starting from index i
            if (goodSuffixTable[j] > j - i) {
                goodSuffixTable[j] = j - i; // Update the table to reflect the better shift
            }
        }
    }

    // Helper function to check if a substring of the pattern is a prefix
    private boolean isPrefix(String pattern, int p) {
        int m = pattern.length();
        for (int i = p, j = 0; i < m; i++, j++) {
            if (pattern.charAt(i) != pattern.charAt(j)) return false; // If characters don't match, it's not a prefix
        }
        return true; // Return true if it matches
    }

    // Helper function to calculate the length of the longest suffix starting from index p
    private int suffix(String pattern, int p) {
        int length = 0;
        for (int i = p, j = pattern.length() - 1; i >= 0 && pattern.charAt(i) == pattern.charAt(j); i--, j--) {
            length++; // Count matching suffix characters
        }
        return length; // Return the length of the matching suffix
    }
}
