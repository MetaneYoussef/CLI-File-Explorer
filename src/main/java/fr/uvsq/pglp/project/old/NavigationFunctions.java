package fr.uvsq.pglp.project.old;

import java.io.File;

/**
 * Navigation Functions ---> .. / [NER] . / pwd
 *
 */
public class NavigationFunctions {

  /**
   * Class to execute the .. / . / pwd commands.
   *
   * @param command is the selected command between .. / . / pwd
   */
  public static void navigate(String command) {
    if (command.equalsIgnoreCase("exit")) {
      System.out.println("Closing the program ...");
      return;
    } else if (command.equals("..") || command.equals("pwd") || command.equals(".")) {

      // Get the current directory
      String currentDirectory = System.getProperty("user.dir");
      // System.out.println("Current directory: " + currentDirectory);

      if (command.equals("..")) {

        // Getting parent directory
        File currentDir = new File(currentDirectory);
        File parentDir = currentDir.getParentFile();

        if (parentDir != null && parentDir.exists()) {
          // Change to the parent directory if it exists
          // System.out.println("Moving to the parent directory...");
          System.setProperty("user.dir", parentDir.getAbsolutePath());
          // System.out.println("New current directory: " +
          // System.getProperty("user.dir"));
        } else {
          System.out.println("It was not possible to change to the parent directory.");
        }
      } else if (command.equals(".")) {
        navigate2(0, command);
      }
    } else {
      System.out.println("Invalid command. Please enter one of the valid commands.");
    }
  }

  /**
   * Class to execute the .. / . / pwd commands.
   *
   * @param ner     is the number associated to the current document.
   * @param command is the selected command between .. / . / pwd
   */
  public static void navigate2(Integer ner, String command) {
    // Get the current directory
    String currentDirectory = System.getProperty("user.dir");
    // System.out.println("Current directory: " + currentDirectory);

    // Getting the directory and its files
    File currentDir = new File(currentDirectory);
    File[] files = currentDir.listFiles();

    if (files != null && ner >= 0 && ner <= files.length) {
      File selectedFile = (ner == 0) ? files[files.length - 1] : files[ner - 1];

      if (selectedFile.isDirectory()) {
        // System.out.println("Moving to the directory " + ((ner == 0) ? files.length :
        // ner)
        // + " (Directory): " + selectedFile.getName());
        System.setProperty("user.dir", selectedFile.getAbsolutePath());
        // System.out.println("New current directory: " +
        // System.getProperty("user.dir"));
      } else {
        System.out.println("The selected element " + ((ner == 0) ? files.length : ner)
            + " is not a directory.");
      }
    } else {
      System.out.println("Invalid NER.");
    }
  }
}
