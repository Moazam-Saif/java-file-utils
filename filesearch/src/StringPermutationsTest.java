import org.junit.jupiter.api.*;
import java.lang.reflect.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StringPermutationsTest {

    @Test
    void testGeneratePermutations_ValidInput_NoDuplicates() {
        assertDoesNotThrow(() -> StringPermutations.generatePermutations("ABC", false));
    }

    @Test
    void testGeneratePermutations_ValidInput_WithDuplicates() {
        assertDoesNotThrow(() -> StringPermutations.generatePermutations("AAB", true));
    }

    @Test
    void testGeneratePermutations_EmptyInput() {
        // Should not throw, but print an error message
        assertDoesNotThrow(() -> StringPermutations.generatePermutations("", true));
    }

    @Test
    void testNonRecursivePermutations_ValidInput() {
        assertDoesNotThrow(() -> StringPermutations.nonRecursivePermutations("ABC"));
    }

    @Test
    void testNonRecursivePermutations_EmptyInput() {
        assertDoesNotThrow(() -> StringPermutations.nonRecursivePermutations(""));
    }

    @Test
    void testPermuteHelperMethod() throws Exception {
        Method permute = StringPermutations.class.getDeclaredMethod(
                "permute", char[].class, int.class, Set.class, boolean.class);
        permute.setAccessible(true);

        Set<String> results = new LinkedHashSet<>();
        char[] chars = {'A', 'B', 'C'};
        permute.invoke(null, chars, 0, results, true);

        assertTrue(results.contains("ABC"));
        assertTrue(results.size() > 0);
    }

    @Test
    void testShouldSkip() throws Exception {
        Method shouldSkip = StringPermutations.class.getDeclaredMethod(
                "shouldSkip", char[].class, int.class, int.class);
        shouldSkip.setAccessible(true);

        char[] chars = {'A', 'A', 'B'};
        boolean skip = (boolean) shouldSkip.invoke(null, chars, 0, 1);
        assertTrue(skip);
    }
}
