
package fr.uvsq.pglp.project.old;

import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;

/**
 * The DirectoryListing class provides a method to list the files in a
 * directory.
 */
public class DirectoryNavigation {

  /**
   * Lists the files in the specified directory.
   *
   * @param folderPath The path of the directory to list.
   */
  public void listDirectory(String folderPath) {
    /*
     * printing the directory
     */
    AnsiConsole.systemInstall();
    System.out.println(
        ansi().bold().fg(CYAN).a("\n   Directory :")
            .boldOff().fg(DEFAULT).a("  " + folderPath).a("\n").reset());
    AnsiConsole.systemUninstall();

    System.out.println(ansi().fg(YELLOW)
        .a(String.format("%-8s %-10s %-30s %-50s",
            "",
            "NER",
            "Name",
            "Annotations"))
        .reset());
    System.out.println(String.format("%-8s %-10s %-30s %-50s", "", "------", "------", "------"));

    /*
     * printing the list of files
     */
    File currentDir = new File(folderPath);
    File[] files = currentDir.listFiles();
    int ner = 1;

    if (new File(currentDir + File.separator + "notes.txt").exists()) {
      try {
        List<String> lines = Files.readAllLines(
            Paths.get(currentDir + File.separator + "notes.txt"));
        Color oldColor = Color.values()[0];
        for (int i = 0; i < files.length; i++) {
          String searchString = files[i].getName();
          List<String> correspondingLines = new ArrayList<>();
          ;
          for (String line : lines) {
            if (line.split("\t")[1].equals(searchString)) {
              correspondingLines.add(line);
            }
          }

          if (correspondingLines.size() > 0) {
            Color color = Color.values()[new Random().nextInt(9)];
            while (color == oldColor) {
              color = Color.values()[new Random().nextInt(9)];
            }
            oldColor = color;
            System.out.println(ansi().bold().fg(color).a(
                String.format("%-8s %-10d %-30s %-50s", "",
                    ner,
                    files[i].getName(),
                    correspondingLines.get(0).split("\t")[2].replace("\"", "")))
                .reset());
            for (int j = 1; j < correspondingLines.size(); j++) {
              System.out.println(ansi().bold().fg(color).a(
                  String.format("%50s %-50s", "",
                      correspondingLines.get(j).split("\t")[2])
                      .replace("\"",
                          ""))
                  .reset());
            }
          } else {
            System.out.println(String.format("%-8s %-10d %-30s", "", ner, files[i].getName()));
          }

          ner++;
        }
      } catch (IOException e) {
        for (int i = 0; i < files.length; i++) {
          System.out.println(String.format("%-8s %-10d %-30s", "", ner, files[i].getName()));
          ner++;
        }
      }
    } else {
      /*
       * I start my changes here
       */
      for (int i = 0; i < files.length; i++) {
        System.out.println(String.format("%-8s %-10d %-30s", "", ner, files[i].getName()));
        ner++;
      }
      /*
       * Final line of my changes. Have a nice day
       */
    }

  }

  /**
   * Retrieves the file corresponding to the given NER.
   *
   * @param ner The NER value.
   * @return The file corresponding to the NER.
   */
  public File getFileFromNer(int ner) {
    File currentDir = new File(getCurrentDir());
    File[] files = currentDir.listFiles();
    if (ner <= files.length && ner > 0) {
      return files[ner - 1];
    } else {
      return null;
    }
  }

  /**
   * Retrieves the NER value from the given filename.
   *
   * @param filename The name of the file.
   * @return The NER value corresponding to the filename.
   */
  public int getNerFromFile(String filename) {
    File currentDir = new File(getCurrentDir());
    File[] files = currentDir.listFiles();
    int ner = 1;
    for (File file : files) {
      if (file.getName().equals(filename)) {
        return ner;
      }
      ner++;
    }
    return -1;
  }

  /**
   * Class to evaluate if the NER number entry is reachable in the directory or
   * not.
   *
   * @param ner the NER number searched in directory.
   */
  public static boolean reachable(int ner) {
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

  public String getCurrentDir() {
    return System.getProperty("user.dir");
  }

}
