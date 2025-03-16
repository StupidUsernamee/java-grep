package org.example.SearchEngine;

import org.example.Utility.SearchResult;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// The Regex class implements the SearchStrategy interface and provides the Regex-based search algorithm
// for pattern matching using Java's built-in regular expression functionality.
public class Regex implements SearchStrategy {

    private final Pattern compiledPattern; // The compiled regular expression pattern

    // Constructor that compiles the pattern based on whether it's a literal or a regular expression
    public Regex(String pattern) {
        // Check if the pattern contains special regex characters to determine if it's a literal pattern
        boolean isLiteral = !pattern.matches(".*[\\.\\*\\+\\?\\^\\$\\{\\}\\(\\)\\|\\[\\]\\\\].*");

        // Compile the pattern with or without escaping special regex characters depending on whether it's a literal
        this.compiledPattern = isLiteral ? Pattern.compile(pattern, Pattern.LITERAL) : Pattern.compile(pattern);
    }

    // The search method performs a regex search on the given text
    // It returns a list of SearchResult objects containing the matching results
    @Override
    public List<SearchResult> search(String text, String pattern, int lineNumber) {
        List<SearchResult> results = new ArrayList<>();

        // Create a Matcher object to find matches of the pattern in the text
        Matcher matcher = compiledPattern.matcher(text);

        // If a match is found, add the result to the list
        if (matcher.find()) {
            results.add(new SearchResult(lineNumber, text)); // Add the result with the current line number
        }

        return results; // Return the list of results
    }
}
