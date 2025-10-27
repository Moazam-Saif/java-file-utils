import java.util.*;

public class StringPermutations {

    // Recursive method to generate permutations
    public static void generatePermutations(String str, boolean includeDuplicates) {
        if (str == null || str.isEmpty()) {
            System.out.println("Error: Input string cannot be empty.");
            return;
        }

        System.out.println("\nRecursive Permutations (" +
                (includeDuplicates ? "with" : "without") + " duplicates):");
        Set<String> permutations = new LinkedHashSet<>(); // Use Set to avoid duplicates
        permute(str.toCharArray(), 0, permutations, includeDuplicates);

        // Display all permutations
        for (String s : permutations) {
            System.out.println(s);
        }

        System.out.println("Total permutations: " + permutations.size());
    }

    // Recursive helper method
    private static void permute(char[] chars, int index, Set<String> result, boolean includeDuplicates) {
        if (index == chars.length - 1) {
            result.add(new String(chars));
            return;
        }

        for (int i = index; i < chars.length; i++) {
            if (!includeDuplicates && shouldSkip(chars, index, i)) {
                continue;
            }
            swap(chars, index, i);
            permute(chars, index + 1, result, includeDuplicates);
            swap(chars, index, i); // backtrack
        }
    }

    // Check if a duplicate swap should be skipped
    private static boolean shouldSkip(char[] chars, int start, int current) {
        for (int i = start; i < current; i++) {
            if (chars[i] == chars[current]) {
                return true;
            }
        }
        return false;
    }

    // Swap helper method
    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    // Non-recursive permutation using Heap's algorithm
    public static void nonRecursivePermutations(String str) {
        if (str == null || str.isEmpty()) {
            System.out.println("Error: Input string cannot be empty.");
            return;
        }

        System.out.println("\nNon-Recursive Permutations (Heap's Algorithm):");
        List<String> results = new ArrayList<>();
        char[] chars = str.toCharArray();
        int n = chars.length;
        int[] c = new int[n];

        results.add(new String(chars));

        int i = 0;
        while (i < n) {
            if (c[i] < i) {
                if (i % 2 == 0)
                    swap(chars, 0, i);
                else
                    swap(chars, c[i], i);

                results.add(new String(chars));
                c[i]++;
                i = 0;
            } else {
                c[i] = 0;
                i++;
            }
        }

        for (String s : results) {
            System.out.println(s);
        }
        System.out.println("Total permutations: " + results.size());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter a string: ");
            String input = sc.nextLine();

            System.out.print("Include duplicate permutations? (true/false): ");
            boolean includeDuplicates = sc.nextBoolean();

            // Generate recursive permutations
            generatePermutations(input, includeDuplicates);

            // Generate non-recursive permutations
            nonRecursivePermutations(input);

            // Complexity analysis
            System.out.println("\n--- Time Complexity Analysis ---");
            System.out.println("Recursive approach: O(n!) - generates all possible permutations");
            System.out.println("Non-recursive (Heap's algorithm): O(n!) - similar complexity");
            System.out.println("Both methods have exponential growth; recursion may have higher overhead for large strings.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
