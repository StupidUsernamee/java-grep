package org.example;

import org.example.Utility.SearchResult;

public class StringFinder {

    public static SearchResult findString(String[] fileContent, String toFind) {
        for (int i = 0; i < fileContent.length; i++) {
            if (fileContent[i].toLowerCase().contains(toFind)) {
                return new SearchResult(i+1, fileContent[i]);
            }
        }
        return new SearchResult(-1, null);
    }
}
