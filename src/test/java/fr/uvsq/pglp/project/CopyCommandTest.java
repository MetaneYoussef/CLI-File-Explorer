package fr.uvsq.pglp.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
public class CopyCommandTest {

    @TempDir
    // We create a temporary directory that exists for the duration of a test
    Path tempDir;
    // Save the current directory
    private String initialDirectory;

    @BeforeEach
    public void setUp() throws IOException {
        Parser.file = null;
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
    // Copy a valid file with a specific ner
    public void testCopyValidFile() throws IOException {
        // Set a specific directory for test
        System.setProperty("user.dir", tempDir.toString());

        // Create a test command
        CopyCommand command = new CopyCommand();

        // Copy the file with NER 1 executing the command
        command.execute("1 copy");

        // The detail is not null and we verify that the path is correct
        assertNotNull(Parser.file);
        assertEquals(tempDir.resolve("file1.txt").toString(), Parser.file.getPath().toString());
    }

    @Test
    // Copy the last item of a directory
    public void testCopyLastItemValidFile() throws IOException {
        // Set a specific directory for test
        System.setProperty("user.dir", tempDir.toString());

        // Create a test command
        CopyCommand command = new CopyCommand();

        // Copy the last file with 0 as input by default executing the command
        command.execute("4 copy");

        // The detail is not null and we verify that the path is correct
        assertNotNull(Parser.file);
        assertEquals(tempDir.resolve("subdirectory").toString(), Parser.file.getPath().toString());
    }

    @Test
    // Copy a file with an invalid ner
    public void testCopyInvalidFile() {
        // Set a specific directory for test
        System.setProperty("user.dir", tempDir.toString());

        // Create a test command
        CopyCommand command = new CopyCommand();

        // Copy an item that does not exist in the directory (NER 10) executing the command
        command.execute("10 copy");

        // Verify that the method returns null
        assertNull(Parser.file);
    }
}