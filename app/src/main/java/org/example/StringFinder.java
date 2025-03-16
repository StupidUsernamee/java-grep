package org.example;

import org.example.SearchEngine.BoyerMoore;
import org.example.SearchEngine.KMP;
import org.example.SearchEngine.Regex;
import org.example.SearchEngine.SearchStrategy;
import org.example.Utility.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.HashMap;
import java.util.Map;

public class StringFinder {

    // Variable to hold the search strategy (algorithm) to be used.
    private static SearchStrategy searchAlgorithm;

    // Main method to find the pattern in the provided file content stream.
    // Returns a list of SearchResult objects containing the search results.
    public static List<SearchResult> findString(Stream<String> fileContent, String pattern) {

        // Determine which search algorithm to use based on the pattern type (Regex, KMP, or Boyer-Moore).
        searchAlgorithm = isRegexPattern(pattern) ? new Regex(pattern)
                : shouldUseKMP(pattern) ? new KMP(pattern)
                : new BoyerMoore(pattern);  // Default is BoyerMoore

        List<SearchResult> results = new ArrayList<>(5000); // Preallocate list with an initial capacity of 5000 for efficiency.
        final int[] lineNumber = {0};  // Array used to hold the line number as it's updated inside a lambda.

        // Iterate through each line of the file content and search for the pattern.
        fileContent.forEach(line -> {
            lineNumber[0]++;  // Increment the line number for each line processed.
            // Add all the search results found in the current line to the results list.
            results.addAll(searchAlgorithm.search(line, pattern, lineNumber[0]));
        });

        return results;  // Return the accumulated list of search results.
    }

    // Determine whether the Knuth-Morris-Pratt (KMP) algorithm should be used.
    private static boolean shouldUseKMP(String pattern) {
        // Use KMP if the pattern length is greater than 4.
        if (pattern.length() > 4) {
            System.out.println("KMP");
            return true;
        }
        // Otherwise, check if the pattern has frequent repetition of characters.
        return hasMenRepetition(pattern);
    }

    // Check if the pattern has significant character repetition (to determine if KMP should be used).
    private static boolean hasMenRepetition(String pattern) {
        Map<Character, Integer> freqMap = new HashMap<>();
        // Populate the frequency map of characters in the pattern.
        for (char c : pattern.toCharArray())
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);

        // Get the maximum frequency of any character.
        int maxFrequency = freqMap.values().stream().max(Integer::compareTo).orElse(0);

        // If the most frequent character occurs more than half of the pattern length, use KMP.
        if (maxFrequency > (pattern.length() / 2)) {
            System.out.println("KMP");
            return true;
        } else {
            // Otherwise, use Boyer-Moore.
            System.out.println("BoyerMoore");
            return false;
        }
    }

    // Check if the pattern is a regular expression (Regex).
    private static boolean isRegexPattern(String pattern) {
        // If the pattern matches the regex pattern syntax, consider it a regex.
        if (pattern.matches(".*[.*+?^${}()|\\[\\]\\\\].*")) {
            System.out.println("Regex");
            return true;
        }
        return false;
    }
}
