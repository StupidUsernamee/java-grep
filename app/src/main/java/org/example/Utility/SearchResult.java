package org.example.Utility;

public class SearchResult {

    private Integer lineNumber;

    private String line;

    public SearchResult(Integer lineNumber, String line) {
        this.lineNumber = lineNumber;
        this.line = line;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
