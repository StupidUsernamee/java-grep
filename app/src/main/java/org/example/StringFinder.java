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

    private static SearchStrategy searchAlgorithm;

    public static List<SearchResult> findString(Stream<String> fileContent, String pattern) {

        searchAlgorithm = isRegexPattern(pattern) ? new Regex(pattern) : shouldUseKMP(pattern) ? new KMP(pattern) : new BoyerMoore(pattern);

        List<SearchResult> results = new ArrayList<>(5000); // Preallocate for efficiency
        final int[] lineNumber = {0};

        fileContent.forEach(line -> {
            lineNumber[0]++;
            results.addAll(searchAlgorithm.search(line, pattern, lineNumber[0]));
        });

        return results;
    }

    private static boolean shouldUseKMP(String pattern) {
        if (pattern.length() > 4) {
            System.out.println("KMP");
            return true;
        }
        return hasMenRepetition(pattern);
    }

    private static boolean hasMenRepetition(String pattern) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : pattern.toCharArray()) freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);

        int maxFrequency = freqMap.values().stream().max(Integer::compareTo).orElse(0);
        if (maxFrequency > (pattern.length() / 2)) {
            System.out.println("KMP");
            return true;
        } else {
            System.out.println("BoyerMoore");
            return false;
        }
    }

    private static boolean isRegexPattern(String pattern) {
        if (pattern.matches(".*[.*+?^${}()|\\[\\]\\\\].*")) {
            System.out.println("Regex");
            return true;
        }
        return false;
    }
}
