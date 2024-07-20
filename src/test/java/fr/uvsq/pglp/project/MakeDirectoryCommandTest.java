
package fr.uvsq.pglp.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * This class contains unit tests for the FolderManipulation class.
 */
public class MakeDirectoryCommandTest {

  @TempDir
  // We create a temporary directory that exists for the duration of a test
  static Path tempDir;

  /**
   * Set up method to create the initial conditions for each test.
   *
   * @throws IOException if an I/O error occurs.
   */
  @BeforeEach
  public void setUp() {
    // We create the previous conditions for each test
    // In this case the directory src
    File existingDirectory = tempDir.resolve("src").toFile();
    existingDirectory.mkdir();
  }

  @Test
  public void testMkDirectorySuccess() {
    // Get the expected directory path when we add testFolder to the current path
    String folderName = "testFolder";
    String expectedDirectoryPath = System.getProperty("user.dir") + File.separator + folderName;

    // Create a PwdCommand object
    MakeDirectoryCommand command = new MakeDirectoryCommand();

    // Call the method to try to create a new directory Execute the command
    command.execute("mkdir testFolder");

    // Verify if the directory has been created successfully
    File createdDirectory = new File(expectedDirectoryPath);
    assertFalse(createdDirectory.exists());
    assertFalse(createdDirectory.isDirectory());
    createdDirectory.delete();
  }

  @Test
  public void testMkDirectoryFail() {
    String existingDirectoryName = "src";

    // Create a PwdCommand object
    MakeDirectoryCommand command = new MakeDirectoryCommand();

    // Call the method to try to create an existing directory Execute the command
    command.execute("mkdir src");

    // Verify that the directory has not been created
    File existingDirectory = tempDir.resolve(existingDirectoryName).toFile();
    assertTrue(existingDirectory.exists());
    assertTrue(existingDirectory.isDirectory());
  }

}