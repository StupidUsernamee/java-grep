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

        String path = "/home/amirm/w/java/Jgrep/java-grep/app/src/main/java/TestHub/huge_test_file.txt";
        Path filePath = Path.of(path);
        if (!filePath.toFile().exists()) {
            System.out.println("file does not exist.");
            System.exit(0);
        }
        String searchTerm = "rain";

        try (Stream<String> fileContent = InputReader.readFile(filePath)) {
            System.out.println("Search started.");
            long start = System.currentTimeMillis();
            List<SearchResult> searchResultList = StringFinder.findString(fileContent ,searchTerm);
            long end = System.currentTimeMillis();
            System.out.println("Search finished.");
            long execTime = end - start;
            if (searchResultList.isEmpty()) {
                System.out.println("No match found");
                return;
            }


            int count = searchResultList.size();

            searchResultList.forEach( searchResult -> {
                //System.out.println(searchResult.getLineNumber() + "\t" + searchResult.getLine());
            });

            System.out.println(count + " found.");
            System.out.println("it took: " + execTime);
        }
    }
}
