package org.example.SearchEngine;

import org.example.Utility.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex implements SearchStrategy {
    private final Pattern compiledPattern;

    public Regex(String pattern) {
        // Correct literal detection regex with proper escapes and without the extra trailing quote
        boolean isLiteral = !pattern.matches(".*[\\.\\*\\+\\?\\^\\$\\{\\}\\(\\)\\|\\[\\]\\\\].*");
        this.compiledPattern = isLiteral ? Pattern.compile(pattern, Pattern.LITERAL) : Pattern.compile(pattern);
    }

    @Override
    public List<SearchResult> search(String text, String pattern, int lineNumber) {
        List<SearchResult> results = new ArrayList<>();
        // Directly create the matcher with the given text
        Matcher matcher = compiledPattern.matcher(text);

        if (matcher.find()) {
            results.add(new SearchResult(lineNumber, text));
        }

        return results;
    }
}
