package fr.uvsq.pglp.project;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * This class contains unit tests for FileOperations class.
 */
public class PasteCommandTest {

    @TempDir
    // We create a temporary directory that exists for the duration of a test
    Path tempDir;
    // Save the current directory
    private String initialDirectory;

    @BeforeEach
    public void setUp() throws IOException {
        // Save the current directory
        initialDirectory = System.getProperty("user.dir");

        // We create the previous conditions for each test
        // in this case 3 files (file1.txt, file2.txt and file3.txt)
        // and 1 directory (subdirectory) that contains 1 file (file4.txt) and a
        // directory (subdirectory2)
        Files.createFile(tempDir.resolve("file1.txt"));
        Files.createFile(tempDir.resolve("file2.txt"));
        Files.createFile(tempDir.resolve("file3.txt"));
        Files.createDirectory(tempDir.resolve("subdirectory"));
        Files.createFile(tempDir.resolve("subdirectory").resolve("file4.txt"));
        Files.createFile(tempDir.resolve("subdirectory").resolve("subdirectory2"));
    }

    @AfterEach
    public void tearDown() {
        // Restore the original directory after each test
        System.setProperty("user.dir", initialDirectory);
    }

    @Test
    // Paste a file in the current directory
    public void testPasteCopiedFile() throws IOException {
        // Create the File that will be pasted
        File testFile = new File(tempDir.resolve("subdirectory").resolve("file4.txt").toString());
        Parser.file = testFile;

        // Set a specific directory for test
        System.setProperty("user.dir", tempDir.toString());

        // Create a test command
        PasteCommand command = new PasteCommand();

        // Try to paste the file in the path defined for the test executing the command
        command.execute("paste");

        // Verify that the retourned path is not null and that the pasted file exists
        String currentDir = System.getProperty("user.dir");
        String pastePath = currentDir + File.separator + "file4.txt";
        assertTrue(new File(pastePath).exists());
    }

    @Test
    // Paste a directory in the current directory
    public void testPasteCopiedDirectory() throws IOException {
        // Create the File that will be pasted
        File testFile = new File(tempDir.resolve("subdirectory").resolve("subdirectory2").toString());
        Parser.file = testFile;

        // Set a specific directory for test
        System.setProperty("user.dir", tempDir.toString());

        // Create a test command
        PasteCommand command = new PasteCommand();

        // Try to paste the directory in the path defined for the test executing the command
        command.execute("paste");

        // Verify that the retourned path is not null and that the pasted directory
        // exists
        String currentDir = System.getProperty("user.dir");
        String pastePath = currentDir + File.separator + "subdirectory2";
        assertTrue(new File(pastePath).exists());
    }

    @Test
    // Paste a repeated file in the current directory
    public void testPasteCopiedRepeatedFile() throws IOException {
        // Create the File that will be pasted
        File testFile = new File(tempDir + File.separator + "file1.txt");
        Parser.file = testFile;

        // Set a specific directory for test
        System.setProperty("user.dir", tempDir.toString());

        // Create a test command
        PasteCommand command = new PasteCommand();

        // Try to paste the directory in the path defined for the test executing the command
        command.execute("paste");

        // Get the file name and its extension to make the comparisons
        String fileNameWithoutExtension = testFile.getName().contains(".")
                ? testFile.getName().substring(0, testFile.getName().lastIndexOf('.'))
                : testFile.getName();
        String fileExtension = testFile.getName().contains(".")
                ? testFile.getName().substring(testFile.getName().lastIndexOf('.'))
                : "";
        String newFileName = fileNameWithoutExtension + "-copy" + fileExtension;

        // Verify that the file has been pasted and has a "-copy" added
        assertTrue(new File(tempDir + File.separator + newFileName).exists());
    }
}
