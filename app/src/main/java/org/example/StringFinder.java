package org.example;

import org.example.Utility.SearchResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class StringFinder {

    public static List<SearchResult> findString(Stream<String> fileContent, String toFind) {
        List<SearchResult> results = new LinkedList<>();
        final int[] lineNumber = {0};

        fileContent.forEach(line ->{
            lineNumber[0]++;
            if (line.toLowerCase().contains(toFind.toLowerCase())) {
                results.add(new SearchResult(lineNumber[0], line));
            }
        });
        return results;
    }
}
