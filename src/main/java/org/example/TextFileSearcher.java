package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * TextFileSearcher
 * Handles indexing and categorizing of files and folders
 *
 * @author Miriam Yanku
 */
public class TextFileSearcher {
    private Map<String, Set<String>> index = new HashMap<>();
    private int wordCounter;

    public TextFileSearcher() {
    }

    /**
     * Indexes files
     * @param file that needs to be indexed
     * @throws IOException when error happens during indexing
     */
    public void indexFile(File file) throws IOException {
        if (file.isFile()) {
            String content = new String(Files.readAllBytes(file.toPath()));
            String[] words = content.split("\\W+");

            for (String word : words) {
                word = word.toLowerCase();
                index.computeIfAbsent(word, k -> new HashSet<>()).add(file.getName());
                wordCounter++;
            }
        }
    }

    /**
     * Indexes folders
     * @param directory that needs to be indexed
     * @throws IOException when error happens during indexing
     */
    public void indexDirectory(File directory) throws IOException {
        if (directory.isDirectory()) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.isFile()) {
                    indexFile(file);
                }
            }
        }
    }

    /**
     * Searches for word in source
     * @param word that needs to be searched
     * @return collection of results
     */
    public Set<String> search(String word) {
        return index.getOrDefault(word.toLowerCase(), Collections.emptySet());
    }


    public int getWordCounter() {
        return wordCounter;
    }
}
