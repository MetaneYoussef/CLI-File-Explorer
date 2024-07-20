package fr.uvsq.pglp.project;

import java.io.File;
import java.io.IOException;

/**
 * LengthTwoNavigationCommand class for the command list.
 */
public class LengthTwoNavigationCommand implements Command {
  @Override
  public void execute(String line) {
    if (line.equals("..")) {
      // Get the current directory
      String currentDirectory = System.getProperty("user.dir");

      // System.out.println("Current directory: " + currentDirectory);

      // Getting parent directory
      
      File currentDir = new File(currentDirectory);
      File parentDir = currentDir.getParentFile();
      if (parentDir != null && parentDir.exists()) {
        // Change to the parent directory if it exists
        // System.out.println("Moving to the parent directory...");
        System.setProperty("user.dir", parentDir.getAbsolutePath());
        //Annotation.updateAnnotation();
        Repertoire.updateAnnotation();
        App.ner = 0;
        // System.out.println("New current directory: " +
        // System.getProperty("user.dir"));
      } else {
        System.out.println("It was not possible to change to the parent directory.");
      }
      
      /*
      Repertoire myCurrentDirectory = new Repertoire(currentDirectory, 0);
      try {
        // Getting the parent directory
        Repertoire parentRepertoire = getParentDirectory(myCurrentDirectory);
        if (parentRepertoire != null) {
          // Change to the parent directory if it exists
          // System.out.println("Moving to the parent directory...");
          System.setProperty("user.dir", parentRepertoire.getPath());
          //Annotation.updateAnnotation();
          Repertoire.updateAnnotation();
          App.ner = 0;
          // System.out.println("New current directory: " +
          // System.getProperty("user.dir"));
        } else {
          System.out.println("It was not possible to change to the parent directory.");
        }
      } catch (IOException e) {
        System.out.println("It was not possible to change to the parent directory.");
      }
      */
    } else {
      System.out.println("Invalid command. Please enter one of the valid commands.");
    }
  }

  /**
   * Get the parent directory of the current directory.
   *
   * @param currentDirectory is the current directory
   */
  private Repertoire getParentDirectory(Repertoire currentDirectory) throws IOException {
    String parentDirectory = currentDirectory.getBasePath();
    if (parentDirectory != null) {
      return new Repertoire(parentDirectory, 0);
    } else {
      return null;
    }
  }
}