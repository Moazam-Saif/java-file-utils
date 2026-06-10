# java-file-utils

A pair of Java command-line utilities: a recursive file search tool and a string permutation generator, each with a companion JUnit 5 test suite.

## Overview

This project contains two independent, self-contained programs that share a common theme of recursive traversal — one over a file system, the other over character arrangements.

## Suggested Name

`java-file-utils` — replacing the unlabeled `filesearch` directory.

## Project Structure

```
src/
├── FileSearch.java             # Recursive file-system search CLI
├── FileSearchTest.java         # JUnit 5 tests for FileSearch
├── StringPermutations.java     # Recursive + iterative permutation generator CLI
└── StringPermutationsTest.java # JUnit 5 tests for StringPermutations
```

---

## FileSearch

Recursively walks a directory tree looking for files whose names match one or more target names. Supports both case-sensitive and case-insensitive matching.

### Usage

```bash
java FileSearch <directory_path> <file1> [<file2> ...] [-i]
```

| Argument | Description |
|---|---|
| `<directory_path>` | Root directory to search from |
| `<file1> [file2 ...]` | One or more exact filenames to look for |
| `-i` | Optional flag for case-insensitive matching |

### Examples

```bash
# Case-sensitive search for two files
java FileSearch /home/user/docs report.pdf notes.txt

# Case-insensitive search
java FileSearch /home/user/docs README.md -i
```

### Output

```
Found file: /home/user/docs/project/report.pdf
Found file: /home/user/docs/archive/notes.txt
--------------------------------------------------
Total matches found: 2
```

### How It Works

- Traverses directories recursively via `File.listFiles()`.
- Prints the absolute path of every matching file.
- Reports the total match count at the end.
- Prints to `System.err` when given an invalid or non-existent directory.

---

## StringPermutations

Generates all permutations of an input string using two independent algorithms — a classic recursive backtracking approach and an iterative implementation of Heap's algorithm.

### Usage

Run interactively:

```bash
java StringPermutations
```

You will be prompted for:
1. The input string.
2. Whether to include duplicate permutations (`true` / `false`).

### Example Session

```
Enter a string: ABC
Include duplicate permutations? (true/false): false

Recursive Permutations (without duplicates):
ABC
ACB
BAC
BCA
CAB
CBA
Total permutations: 6

Non-Recursive Permutations (Heap's Algorithm):
ABC
BAC
...
Total permutations: 6
```

### Algorithms

| Approach | Algorithm | Duplicate handling |
|---|---|---|
| Recursive | Backtracking with swap | Optional — skips duplicate characters at each index |
| Iterative | Heap's Algorithm | Generates all, including duplicates |

### Time Complexity

Both approaches run in **O(n!)** time. Heap's algorithm avoids the call-stack overhead of recursion but produces the same number of outputs. For strings longer than ~10 characters, output volume grows extremely fast.

---

## Running Tests

Both test classes use **JUnit 5** and can be run in Eclipse or via the command line.

In Eclipse: right-click the test file → *Run As* → *JUnit Test*.

```bash
# Compile
javac -cp .:junit-platform.jar src/*.java

# Run FileSearch tests
java -cp .:junit-platform.jar org.junit.platform.console.ConsoleLauncher \
     --select-class=FileSearchTest

# Run StringPermutations tests
java -cp .:junit-platform.jar org.junit.platform.console.ConsoleLauncher \
     --select-class=StringPermutationsTest
```

### Test Coverage

**FileSearch**
- Case-sensitive recursive match finds files in subdirectories.
- Case-insensitive match finds files by uppercase name.
- Invalid directory path exits gracefully without throwing.
- Private `matches()` method verified via reflection.

**StringPermutations**
- Valid 3-character input generates permutations without error.
- Duplicate handling (`AAB`) works correctly with and without the dedup flag.
- Empty string input is handled gracefully.
- Private `permute()` and `shouldSkip()` helpers verified via reflection.

## Requirements

- Java 21+
- JUnit 5 (Jupiter)

## Possible Improvements

- Accept a glob or regex pattern in `FileSearch` instead of only exact names.
- Return results from `FileSearch.searchFiles()` as a `List<File>` instead of printing directly, making it more testable and reusable.
- Make `StringPermutations.generatePermutations()` return the set rather than printing, for programmatic use.
- Remove the `static` counter `totalMatches` in `FileSearch` — it accumulates across calls in the same JVM, which breaks test isolation.
