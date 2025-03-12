package org.example.SearchEngine;

import org.example.Utility.SearchResult;

import java.util.List;

public interface SearchStrategy {
    List<SearchResult> search(String text, String pattern, int lineNumber);
}
