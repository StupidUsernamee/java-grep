package org.example;

import org.example.Utility.SearchResult;

import java.io.IOException;
import java.nio.file.Path;

public class App {


    public static void main(String[] args) throws IOException {
        String[] fContent = InputReader.readFile(Path.of("/Users/amirp/w/Jgrep/java-grep/app/src/main/java/org/example/test_file")).toArray(new String[0]);
        SearchResult result = StringFinder.findString(fContent, "hello");
        System.out.println(result.getLineNumber() + "\t" + result.getLine());
    }
}
