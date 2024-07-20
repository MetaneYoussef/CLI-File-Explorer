package fr.uvsq.pglp.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LengthOneNavigationCommandTest {
    private String initialDirectory;
    private File testDir;
    private File subDir1;
    private File subDir2;
    private File subDir3;

    @BeforeEach
    public void setUp() {
        // Save the current directory
        initialDirectory = System.getProperty("user.dir");

        // Create a directories and files structure to execute the tests
        // Based on 1 directory (testDir) that has 3 files
        // (subDir1, subDir2, subDir3)
        testDir = new File(initialDirectory + File.separator + "testDir");
        testDir.mkdir();

        subDir1 = new File(testDir, "subDir1");
        subDir1.mkdir();

        subDir2 = new File(testDir, "subDir2");
        subDir2.mkdir();

        subDir3 = new File(testDir, "subDir3");
        subDir3.mkdir();
    }

    @AfterEach
    public void tearDown() {
        // Delete the files and directories created for each the test
        subDir1.delete();
        subDir2.delete();
        subDir3.delete();
        testDir.delete();
        // Restore the original directory after each test
        System.setProperty("user.dir", initialDirectory);
    }

    @Test
    // Navigation to last item in a directory
    public void testNavigateToLastItemInDirectory() {
        // Set a specific directory for tests
        System.setProperty("user.dir", initialDirectory + File.separator + "testDir");

        // Create a test command
        LengthOneNavigationCommand command = new LengthOneNavigationCommand();

        // Trying to navigate to the directory executing the command
        command.execute(".");

        // Verify if we have navigated to the last item
        assertEquals(subDir3.getAbsolutePath(), System.getProperty("user.dir"));
    }

    @Test
    // Navigate the directory by entering a NER number
    public void testNavigateToDirectoryByNER() throws IOException {
        // Set a specific directory for tests
        System.setProperty("user.dir", initialDirectory + File.separator + "testDir");

        // Create a test command
        LengthOneNavigationCommand command = new LengthOneNavigationCommand();

        // Trying to navigate to the directory executing the command
        command.execute("1 .");

        // Check if it has been moved to the item indicated by NER
        assertEquals(subDir1.getAbsolutePath(), System.getProperty("user.dir"));
    }

    @Test
    // Try to navigate the directory by entering an invalid NER number
    public void testNavigateToInvalidNER() {
        // Set a specific directory for tests
        System.setProperty("user.dir", initialDirectory + File.separator + "testDir");

        // Create a test command
        LengthOneNavigationCommand command = new LengthOneNavigationCommand();

        // Trying to navigate with an invalid NER executing the command
        command.execute("10 .");

        // Verify that it remains in the same directory
        assertEquals(initialDirectory + File.separator + "testDir", System.getProperty("user.dir"));
    }
}
