import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearch {

    private static int totalMatches = 0; // To count how many files were found

    /**
     * Recursively search for files matching any of the target names.
     */
    public static void searchFiles(File directory, List<String> filenames, boolean caseSensitive) {
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Recurse into subdirectory
                        searchFiles(file, filenames, caseSensitive);
                    } else {
                        for (String filename : filenames) {
                            if (matches(file.getName(), filename, caseSensitive)) {
                                totalMatches++;
                                System.out.println("Found file: " + file.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        } else {
            System.err.println("Invalid directory: " + directory.getAbsolutePath());
        }
    }

    /**
     * Compares file names depending on case sensitivity.
     */
    private static boolean matches(String fileName, String searchTerm, boolean caseSensitive) {
        if (caseSensitive) {
            return fileName.equals(searchTerm);
        } else {
            return fileName.equalsIgnoreCase(searchTerm);
        }
    }

    /**
     * Main method — handles input and output.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java FileSearch <directory_path> <file1> [<file2> ...] [-i]");
            System.err.println("Use -i at the end for case-insensitive search.");
            System.exit(1);
        }

        String directoryPath = args[0];
        boolean caseSensitive = true;

        // Detect case-insensitive flag (-i)
        List<String> filenames = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-i")) {
                caseSensitive = false;
            } else {
                filenames.add(args[i]);
            }
        }

        File directory = new File(directoryPath);
        searchFiles(directory, filenames, caseSensitive);

        System.out.println("--------------------------------------------------");
        System.out.println("Total matches found: " + totalMatches);
    }
}
