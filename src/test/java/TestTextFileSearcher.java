import org.example.TextFileSearcher;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Set;

import static junit.framework.TestCase.*;

/**
 * Test class to test TextFileSearcher
 */
public class TestTextFileSearcher {
    private TextFileSearcher textFileSearcher;
    private File testFile;
    private File testDirectory;

    @Before
    public void setUp() throws IOException {
        textFileSearcher = new TextFileSearcher();

        // create singular test file
        testFile = new File("testTextFile.txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("The quick brown fox jumps over the lazy dog");
        }

        // setting up test directory with files
        testDirectory = new File("testDirectory");
        testDirectory.mkdir();

        File file1 = new File(testDirectory, "opinion.txt");
        try (FileWriter writer = new FileWriter(file1)) {
            writer.write("I think cheese does not taste nice.");
        }

        File file2 = new File(testDirectory, "opinion2.txt");
        try (FileWriter writer = new FileWriter(file2)) {
            writer.write("I think everybody should have a hobby.");
        }
    }

    @After
    public void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }

        if (testDirectory.exists()) {
            for (File file : testDirectory.listFiles()) {
                file.delete();
            }
            testDirectory.delete();
        }
    }

    @Test
    public void indexFile_IndexesWordsCorrectly() throws IOException {
        textFileSearcher.indexFile(testFile);

        Set<String> results = textFileSearcher.search("dog");
        assertTrue("Index should contain 'dog' from test file", results.contains(testFile.getName()));

        results = textFileSearcher.search("fox");
        assertTrue("Index should contain 'fox' from test file", results.contains(testFile.getName()));

    }

    @Test
    public void indexDirectory_IndexesAllFilesCorrectly() throws IOException {
        textFileSearcher.indexDirectory(testDirectory);

        Set<String> results = textFileSearcher.search("think");
        assertTrue("Index should contain 'think' from multiple files in the directory",
                results.contains("opinion.txt") && results.contains("opinion2.txt"));
    }

    @Test
    public void search_ReturnsCorrectlyForUnknownWord() throws IOException {
        textFileSearcher.indexFile(testFile);

        Set<String> results = textFileSearcher.search("nonexistent");
        assertTrue("Should return a empty set when no word has been found", results.isEmpty());
    }

    @Test
    public void getWordCounter_ReturnsCorrectWordCount() throws IOException {
        textFileSearcher.indexFile(testFile);
        assertEquals("Word counter has to count the words of the file",
                9, textFileSearcher.getWordCounter());
    }
}

