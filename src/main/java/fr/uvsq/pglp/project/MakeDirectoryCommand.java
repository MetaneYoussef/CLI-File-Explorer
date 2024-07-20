package fr.uvsq.pglp.project;

import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.ansi;

import java.io.File;

/**
 * Class that executes the mkdir command.
 */
public class MakeDirectoryCommand implements Command {
  @Override
  public void execute(String line) {
    String[] parts = line.split("\\s+", 2);
    if (parts.length != 2) {
      System.out.println("Invalid syntax for mkdir command. Usage: mkdir <directory_name>");
      return;
    }

    String directoryName = parts[1];
    String currentDirectory = System.getProperty("user.dir");

    // We create a file object with the new directory path
    String newDirectoryPath = currentDirectory + File.separator + directoryName;
    File newDirectory = new File(newDirectoryPath);

    // We try to create the directory
    boolean created = newDirectory.mkdir();

    if (created) {
      System.out.println(ansi().bold().fg(CYAN).a("\n   Directory created successfully: ")
          .boldOff().fg(DEFAULT).a("  " + newDirectoryPath).a("\n").reset());
      updateAnnotation(); 
    } else {
      System.out.println("It was not possible to create the directory.");
    }
  }

  private void updateAnnotation() {
    Fichier.updateAnnotation();
  }
}