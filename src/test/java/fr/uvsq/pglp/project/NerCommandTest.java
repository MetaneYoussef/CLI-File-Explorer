package fr.uvsq.pglp.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NerCommandTest {
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
  public void testExecute() {
    // Set a specific directory for test
    System.setProperty("user.dir", tempDir.toString());

    // Redirect System.out to capture console output
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Create a test command
    NerCommand command = new NerCommand();

    // Execute the command with a valid NER value
    command.execute("2");

    // Get the actual output
    String actualOutput = outContent.toString().trim();

    // Define the expected output
    String expectedOutput = "Current NER set to: 2";

    // Assert that the actual output matches the expected output
    assertEquals(expectedOutput, actualOutput);

    // Clean up: Restore System.out
    System.setOut(System.out);
  }
}
