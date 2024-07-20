package fr.uvsq.pglp.project.old;

import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.ansi;

import java.io.File;

/**
 * Class that executes the mkdir command.
 */
public class FolderManipulation {

  /**
   * Class to execute the mkdir command.
   *
   * @param folderName is the name asigned to the folder that will be created
   */
  public static void mkDirectory(String folderName) {

    // Get the name that we want to use for the new folder
    String directoryName = folderName;

    // Get the current directory
    String currentDirectory = System.getProperty("user.dir");

    // We create a file object with the new directory path
    String newDirectoryPath = currentDirectory + File.separator + directoryName;
    File newDirectory = new File(newDirectoryPath);

    // We try to create the directory
    boolean created = newDirectory.mkdir();

    if (created) {
      System.out.println(ansi().bold().fg(CYAN).a("\n   Directory created successfully: ")
          .boldOff().fg(DEFAULT).a("  " + newDirectoryPath).a("\n").reset());
    } else {
      System.out.println("It was not possible to create the directory.");
    }
  }
}
