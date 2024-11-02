package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * TextFileSearcher
 * handles indexing and categorizing of files and folders
 *
 * @author Miriam Yanku
 */
public class TextFileSearcher {
    private Map<String, Set<String>> index = new HashMap<>();
    private String source;
    private int wordCounter;

    public TextFileSearcher() {
    }

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

    public void indexDirectory(File directory) throws IOException {
        if (directory.isDirectory()) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.isFile()) {
                    indexFile(file);
                }
            }
        }
    }

    public Set<String> search(String word) {
        return index.getOrDefault(word.toLowerCase(), Collections.emptySet()); // Return files containing the word
    }

    public Map<String, Set<String>> getIndex() {
        return index;
    }

    public String getSource() {
        return source;
    }

    public int getWordCounter() {
        return wordCounter;
    }
}
