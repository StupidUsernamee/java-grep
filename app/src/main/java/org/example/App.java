package org.example;

import org.example.Utility.SearchResult;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) throws IOException {
//        if (args.length < 2) {
//            System.out.println("Usage: java -jar Jgrep.jar <file_path> <search_term>");
//            return;
//        }


        Path filePath = Path.of("/home/amirm/w/java/Jgrep/java-grep/app/src/main/java/org/example/test_file");
        String searchTerm = "Hello";

        try (Stream<String> fileContent = InputReader.readFile(filePath)) {
            List<SearchResult> searchResultList = StringFinder.findString(fileContent ,searchTerm);

            if (searchResultList.isEmpty()) {
                System.out.println("No match found");
                return;
            }

            searchResultList.forEach( searchResult -> {
                System.out.println(searchResult.getLineNumber() + "\t" + searchResult.getLine());
            });
        }
    }
}
