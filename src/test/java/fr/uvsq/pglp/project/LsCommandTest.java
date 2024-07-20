package fr.uvsq.pglp.project;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LsCommandTest {

  @Test
  public void testExecute() {
    // Redirect System.out to capture console output
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Create a temporary directory for testing
    File tempDir = new File(System.getProperty("java.io.tmpdir") + "/testDir");
    tempDir.mkdir();
    String tempDirPath = tempDir.getAbsolutePath();

    // Change the current directory to the temporary directory
    System.setProperty("user.dir", tempDirPath);

    // Create some files in the temporary directory
    File file1 = new File(tempDirPath + "/file1.txt");
    File file2 = new File(tempDirPath + "/file2.txt");
    File file3 = new File(tempDirPath + "/file3.txt");
    try {
      file1.createNewFile();
      file2.createNewFile();
      file3.createNewFile();
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Create an LsCommand object
    LsCommand command = new LsCommand();

    // Execute the command
    command.execute("");

    // Get the actual output
    String actualOutput = outContent.toString();

    // Get the expected output with ANSI color formatting
    Ansi expectedOutput = Ansi.ansi()
    .bold()
    .fg(CYAN)
    .a("\n   Directory :")
    .boldOff()
    .fg(DEFAULT)
    .a("  " + tempDirPath)
    .a("\n")
    .reset()
    .a("\n")
    .fg(YELLOW).a(String.format("%-8s %-10s %-30s %-50s", "", "NER", "Name", "Annotations")).reset()
    .a("\n")
    .a(String.format("%-8s %-10s %-30s %-50s", "", "------", "------", "------")).a("\n")
    .a(String.format("%-8s %-10d %-30s", "", 1, "file1.txt")).a("\n")
    .a(String.format("%-8s %-10d %-30s", "", 2, "file2.txt")).a("\n")
    .a(String.format("%-8s %-10d %-30s", "", 3, "file3.txt")).a("\n");

    // Assert that the actual output matches the expected output
    assertEquals(expectedOutput.toString().replaceAll("\\s", ""), actualOutput.replaceAll("\\s", ""));

    // Clean up: delete the temporary directory and files
    file1.delete();
    file2.delete();
    file3.delete();
    tempDir.delete();

    // Restore System.out
    System.setOut(System.out);
  }
}
