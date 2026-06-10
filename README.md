# java-file-utils

Two standalone Java CLI utilities: a recursive file search tool and a string permutation generator, each with a JUnit 5 test suite.

---

## File Guide

### Read these — they define the code

| File | Why it matters |
|---|---|
| `src/FileSearch.java` | The recursive directory walker. Contains `searchFiles()` (the core logic), `matches()` (case comparison), and `main()` (argument parsing and output). Start here for the file search program. |
| `src/FileSearchTest.java` | JUnit 5 tests for `FileSearch`. Uses a real temp directory created in `@BeforeEach` and torn down in `@AfterEach`. Tests case sensitivity, invalid paths, and private methods via reflection. |
| `src/StringPermutations.java` | Two independent permutation algorithms: recursive backtracking (`permute()`) and iterative Heap's algorithm (`nonRecursivePermutations()`). Also contains duplicate-skipping logic in `shouldSkip()`. |
| `src/StringPermutationsTest.java` | JUnit 5 tests for `StringPermutations`. Covers valid input, empty input, duplicate handling, and private helper methods via reflection. |

### Ignore these — generated or IDE-only

| File / Folder | Reason to ignore |
|---|---|
| `bin/` | Compiled `.class` files. Regenerated on every build. |
| `testDir/` | Temporary directory created at runtime by `FileSearchTest`. Deleted after each test run; should never be committed. |
| `.classpath` | Eclipse build-path descriptor. Not portable outside Eclipse. |
| `.project` | Eclipse project metadata. IDE bookkeeping only. |
| `.settings/` | Eclipse compiler and encoding preferences. No effect on `javac` or test runners. |

---

## FileSearch

Recursively walks a directory tree and prints the absolute path of every file whose name matches one or more targets.

### Usage

```bash
java FileSearch <directory> <file1> [file2 ...] [-i]
```

| Argument | Description |
|---|---|
| `<directory>` | Root of the search |
| `<file1> [file2 ...]` | Exact filenames to look for |
| `-i` | Case-insensitive matching (optional) |

### Example

```bash
java FileSearch /home/user/docs report.pdf notes.txt -i
```

```
Found file: /home/user/docs/archive/Report.pdf
Found file: /home/user/docs/notes.txt
--------------------------------------------------
Total matches found: 2
```

### How it works

`searchFiles()` calls `File.listFiles()` and recurses into subdirectories. Matching is delegated to the private `matches()` method, which calls either `equals` or `equalsIgnoreCase` depending on the `-i` flag.

> **Known issue:** `totalMatches` is a `static` field, so it accumulates across multiple calls within the same JVM. This causes incorrect totals if `searchFiles()` is called more than once — including across tests. It should be a local variable returned from the method.

---

## StringPermutations

Generates all permutations of a string using two independent algorithms, then prints a time complexity comparison.

### Usage

```bash
java StringPermutations
```

Prompts for the input string and whether to include duplicate permutations.

### Example

```
Enter a string: ABC
Include duplicate permutations? (true/false): false

Recursive Permutations (without duplicates):
ABC  ACB  BAC  BCA  CAB  CBA
Total permutations: 6

Non-Recursive Permutations (Heap's Algorithm):
ABC  BAC  CAB  ACB  BCA  CBA
Total permutations: 6
```

### Algorithms

| Approach | Method | Duplicate control |
|---|---|---|
| Recursive | Backtracking with swap/unswap | `shouldSkip()` detects repeated characters at each position |
| Iterative | Heap's Algorithm | Always generates all permutations; duplicates not filtered |

Both run in **O(n!)** time. Heap's algorithm avoids call-stack depth but produces the same output volume. Input longer than ~10 characters will produce enormous output.

---

## Running Tests

Uses **JUnit 5**, no external dependencies beyond the test framework.

In Eclipse: right-click a test file → *Run As* → *JUnit Test*.

```bash
javac -cp .:junit-platform.jar src/*.java
java  -cp .:junit-platform.jar org.junit.platform.console.ConsoleLauncher \
      --select-class=FileSearchTest
java  -cp .:junit-platform.jar org.junit.platform.console.ConsoleLauncher \
      --select-class=StringPermutationsTest
```

---

## Requirements

- Java 21+
- JUnit 5 (Jupiter)
