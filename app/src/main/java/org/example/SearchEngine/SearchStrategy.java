package org.example.SearchEngine;

import org.example.Utility.SearchResult;

import java.util.List;

// The SearchStrategy interface defines the contract for any search algorithm implementation.
public interface SearchStrategy {

    // The 'search' method takes in a line of text, a search pattern, and the line number.
    // It returns a list of SearchResult objects containing the matching results.
    List<SearchResult> search(String text, String pattern, int lineNumber);
}
