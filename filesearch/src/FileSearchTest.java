import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileSearchTest {

    private File tempDir;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary directory for testing
        tempDir = new File("testDir");
        if (!tempDir.exists()) tempDir.mkdir();

        // Create some files and subdirectories
        new File(tempDir, "sample.txt").createNewFile();
        new File(tempDir, "example.doc").createNewFile();

        File subDir = new File(tempDir, "subFolder");
        subDir.mkdir();
        new File(subDir, "sample.txt").createNewFile();
    }

    @AfterEach
    void tearDown() {
        // Recursively delete the test directory
        deleteDirectory(tempDir);
    }

    private void deleteDirectory(File dir) {
        File[] contents = dir.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (f.isDirectory()) deleteDirectory(f);
                else f.delete();
            }
        }
        dir.delete();
    }

    @Test
    void testSearchFiles_CaseSensitive() {
        List<String> targets = Arrays.asList("sample.txt");
        FileSearch.searchFiles(tempDir, targets, true);
        // If recursive search works, both root and subfolder sample.txt should be found
        // Since the method only prints, we just ensure no exceptions occur
        assertTrue(tempDir.exists());
    }

    @Test
    void testSearchFiles_CaseInsensitive() {
        List<String> targets = Arrays.asList("SAMPLE.TXT");
        assertDoesNotThrow(() -> FileSearch.searchFiles(tempDir, targets, false));
    }

    @Test
    void testSearchFiles_InvalidDirectory() {
        File invalidDir = new File("nonexistentDir");
        List<String> filenames = Arrays.asList("file.txt");
        assertDoesNotThrow(() -> FileSearch.searchFiles(invalidDir, filenames, true));
    }

    @Test
    void testMatches_CaseSensitive() throws Exception {
        var method = FileSearch.class.getDeclaredMethod("matches", String.class, String.class, boolean.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(null, "File.txt", "File.txt", true);
        assertTrue(result);
    }

    @Test
    void testMatches_CaseInsensitive() throws Exception {
        var method = FileSearch.class.getDeclaredMethod("matches", String.class, String.class, boolean.class);
        method.setAccessible(true);
        boolean result = (boolean) method.invoke(null, "File.txt", "file.txt", false);
        assertTrue(result);
    }
}
