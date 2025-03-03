package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class InputReader  {
    private static Scanner scanner;

    public static ArrayList<String> readFile(Path path) throws IOException {
        File file = path.toFile();
        scanner = new Scanner(file);
        ArrayList<String> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }
        return list;
    }
}
