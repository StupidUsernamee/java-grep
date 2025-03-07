package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class InputReader  {
    private static Scanner scanner;

    public static Stream<String> readFile(Path path) throws IOException {
        return new BufferedReader(new FileReader(path.toFile())).lines();
    }
}
