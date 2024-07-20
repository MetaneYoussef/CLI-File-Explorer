package fr.uvsq.pglp.project;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PwdCommandTest {

  @Test
  public void testExecute() {
    
    // Redirect System.out to capture console output
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Create a PwdCommand object
    PwdCommand command = new PwdCommand();

    // Execute the command
    command.execute("pwd");
    // Get the actual output
    String actualOutput = outContent.toString().replaceAll("\\s", "");

    // Get the expected output
    String expectedOutput =("\n" + ansi().bold().fg(YELLOW).a("PATH\n\n").fg(DEFAULT).reset() + System.getProperty("user.dir") + "\n").replaceAll("\\s", "");
    // Assert that the actual output matches the expected output
    assertEquals(expectedOutput, actualOutput);

    // Restore System.out
    System.setOut(System.out);
  }
}
