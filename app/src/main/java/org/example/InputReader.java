package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class InputReader {
    // Static scanner instance (currently unused in the code, can be removed if not needed).
    private static Scanner scanner;

    // Method to read the file content as a stream of lines.
    // Takes the file path as input and returns a Stream<String> of the file's lines.
    public static Stream<String> readFile(Path path) throws IOException {
        // Create a BufferedReader to read the file and return its lines as a stream.
        return new BufferedReader(new FileReader(path.toFile())).lines();
    }
}
