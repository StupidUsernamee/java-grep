package org.example.SearchEngine;

import org.example.Utility.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class BoyerMoore implements SearchStrategy {

    private static final int ALPHABET_SIZE = 65536; // Support full Unicode range
    private short[] badCharacterTable;
    private int[] goodSuffixTable;

    public BoyerMoore(String pattern) {
        preprocessBadCharacter(pattern);
        preprocessGoodSuffix(pattern);
    }

    @Override
    public List<SearchResult> search(String text, String pattern, int lineNumber) {
        List<SearchResult> results = new ArrayList<>();
        int textLen = text.length();
        int patternLen = pattern.length();
        int shift = 0;

        while (shift <= textLen - patternLen) {
            int j = patternLen - 1;

            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }

            if (j < 0) {
                results.add(new SearchResult(lineNumber, text));
                shift += goodSuffixTable[0]; // Shift full pattern if matched
            } else {
                int badCharShift = Math.max(1, j - badCharacterTable[text.charAt(shift + j)]);
                int goodSuffixShift = goodSuffixTable[j];
                shift += Math.max(badCharShift, goodSuffixShift);
            }
        }

        return results;
    }

    private void preprocessBadCharacter(String pattern) {
        int m = pattern.length();
        badCharacterTable = new short[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++) badCharacterTable[i] = -1;
        for (int i = 0; i < m; i++) badCharacterTable[pattern.charAt(i)] = (short) i;
    }

    private void preprocessGoodSuffix(String pattern) {
        int m = pattern.length();
        goodSuffixTable = new int[m];
        int lastPrefix = m;

        for (int i = m - 1; i >= 0; i--) {
            if (isPrefix(pattern, i + 1)) lastPrefix = i + 1;
            goodSuffixTable[i] = lastPrefix - i + m - 1;
        }

        for (int i = 0; i < m - 1; i++) {
            int j = m - 1 - suffix(pattern, i);
            if (goodSuffixTable[j] > j - i) {
                goodSuffixTable[j] = j - i;
            }
        }
    }

    private boolean isPrefix(String pattern, int p) {
        int m = pattern.length();
        for (int i = p, j = 0; i < m; i++, j++) {
            if (pattern.charAt(i) != pattern.charAt(j)) return false;
        }
        return true;
    }

    private int suffix(String pattern, int p) {
        int length = 0;
        for (int i = p, j = pattern.length() - 1; i >= 0 && pattern.charAt(i) == pattern.charAt(j); i--, j--) {
            length++;
        }
        return length;
    }
}
