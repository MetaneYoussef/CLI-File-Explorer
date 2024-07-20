package fr.uvsq.pglp.project;

import java.io.IOException;
import org.jline.reader.EndOfFileException;
import org.jline.reader.UserInterruptException;

/**
 * Main class of the project.
*/
public class App {
  public static int ner;

  /**
  * Main method of the project.
  *
  * @param args arguments of the main method
  */
  public static void main(String[] args) {
    try {
      CommandLineFacade commandLineFacade = new CommandLineFacade();
      commandLineFacade.readLine();
    } catch (UserInterruptException e) {
      System.exit(0);
    } catch (IOException | EndOfFileException e) {
      System.exit(0);
    }
  }
}
