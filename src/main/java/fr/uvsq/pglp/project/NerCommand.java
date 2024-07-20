package fr.uvsq.pglp.project;

import java.io.File;

/**
 * Class that executes the Ner command.
 */
public class NerCommand implements Command {

  @Override
  public void execute(String line) {
    int nerToTest = Integer.parseInt(line);
    if (reachable(nerToTest)) {
      Repertoire repertoire = 
          new Repertoire(System.getProperty("user.dir"), 0); // assuming initial NER is 0
      repertoire.setcurrentNer(nerToTest); // setting the current NER
      App.ner = nerToTest;
      System.out.println("Current NER set to: " + repertoire.getcurrentNer());
    } else {
      System.out.println("Invalid NER value.");
    }
  }

  /**
   * Check if a given NER value is reachable.
   *
   * @param ner The NER value to check
   * @return True if the NER value is reachable, false otherwise
   */
  private boolean reachable(int ner) {
    // Get the current directory
    String currentDirectory = System.getProperty("user.dir");

    // Getting the directory and its files
    File currentDir = new File(currentDirectory);
    File[] fileList = currentDir.listFiles();
    if (fileList != null && ner >= 0 && ner <= fileList.length) {
      return true;
    } else {
      return false;
    }
  }
}
