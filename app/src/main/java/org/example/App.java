package org.example;

import org.example.Utility.SearchResult;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) throws IOException {

        // Defining the path to the file to search.
        String path = "path/to/the/file";
        Path filePath = Path.of(path);

        // Check if the file exists, if not print an error message and exit the program.
        if (!filePath.toFile().exists()) {
            System.out.println("file does not exist.");
            System.exit(0);
        }

        // Initialize the pattern variable to hold the search term entered by the user.
        String pattern = "";

        // Create a scanner to read the user's input from the console.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inter pattern: ");
        pattern = scanner.nextLine(); // Reading the pattern.
        System.out.println(); // Just adds a newline for clarity.
        scanner.close(); // Close the scanner.

        // Try-with-resources to open the file and process it.
        try (Stream<String> fileContent = InputReader.readFile(filePath)) {
            System.out.println("Search started.");
            long start = System.currentTimeMillis(); // Record the start time of the search.

            // Using StringFinder to find matching lines in the file.
            List<SearchResult> searchResultList = StringFinder.findString(fileContent, pattern);

            long end = System.currentTimeMillis(); // Record the end time of the search.
            System.out.println("Search finished.");
            long execTime = end - start; // Calculate the time taken for the search.

            // If no matches are found, print a message and return.
            if (searchResultList.isEmpty()) {
                System.out.println("No match found");
                return;
            }

            // Count the number of results and print the total number of matches.
            int count = searchResultList.size();

            // Loop through the search results and print them if needed (currently commented out).
            searchResultList.forEach(searchResult -> {
                //System.out.println(searchResult.getLineNumber() + "\t" + searchResult.getLine());
            });

            // Output the number of results found and the time taken for the search.
            System.out.println(count + " found.");
            System.out.println("it took: " + execTime);
        }
    }
}
