package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;


public class Main {
    public static void main(String[] args) throws IOException {
        TextFileSearcher fileSearcher = new TextFileSearcher();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // start menu
            System.out.println("TextFileSearcher");
            System.out.println("Choose an option (Type 1-3) :\n1. Index files/folders\n2. Search a word\n3. Exit");

            // error handeling for when input is not an int
            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nWrong input, try again. Choose 1-3\n");
                continue;
            }

            switch (option) {
                case 1 -> {
                    // index files/folders
                    System.out.println("Enter the path to index a file or folder:");
                    String path = scanner.nextLine();
                    File file = new File(path);
                    if (file.exists()) {
                        if (file.isFile()) {
                            // indexing file
                            fileSearcher.indexFile(file);
                            System.out.println("File indexed. Total words indexed: " + fileSearcher.getWordCounter());
                        } else {
                            // indexing folder
                            fileSearcher.indexDirectory(file);
                            System.out.println("Directory indexed. Total words indexed: " + fileSearcher.getWordCounter());
                        }
                    } else {
                        // no file/folder found
                        System.out.println("No file or directory found. You're being redirected to the start menu.");
                        System.out.printf("Path input: %s \n", System.getProperty("user.dir"));
                    }
                }
                case 2 -> {
                    // search word
                    System.out.print("Enter a word to search: ");
                    String word = scanner.nextLine();
                    Set<String> result = fileSearcher.search(word);
                    if (result.isEmpty()) {
                        System.out.println("No files found containing the word: " + word);
                    } else {
                        System.out.println("Files containing the word: " + result);
                    }
                }
                case 3 -> {
                    // exit
                    System.out.println("\nExiting program..");
                    running = false;
                }
                default ->
                    // wrong input
                        System.out.println("\nWrong input, try again. Choose 1-3\n");
            }
        }
    }
}