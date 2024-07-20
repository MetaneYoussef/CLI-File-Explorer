package fr.uvsq.pglp.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LengthTwoNavigationCommandTest {
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
    // Navigation to a valid parent directory
    public void testNavigateToParentDirectory() {
        // Set a specific directory for tests
        System.setProperty("user.dir", initialDirectory + File.separator + "testDir");

        // Create a test command
        LengthTwoNavigationCommand command = new LengthTwoNavigationCommand();

        // Navigate to parent directory executing the command
        command.execute("..");

        // Check if it has been moved to the parent directory
        String expectedDirectory = new File(initialDirectory + File.separator + "testDir").getParent();
        assertEquals(expectedDirectory, System.getProperty("user.dir"));
    }

    @Test
    // Navigation to an invalid parent directory
    public void testNavigateToInvalidParentDirectory() {
        // Set the current directory as the root directory to ensure it does not have a
        // parent directory
        System.setProperty("user.dir", File.separator);

        // Create a test command
        LengthTwoNavigationCommand command = new LengthTwoNavigationCommand();

        // Navigate to parent directory executing the command
        command.execute("..");

        // Check if it has not been moved to any directory (stays in the root directory)
        assertEquals(File.separator, System.getProperty("user.dir"));
    } 
}

