package fr.uvsq.pglp.project;

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

/** LsCommand class for the ls command. */
public class LsCommand implements Command {
  @Override
  public void execute(String line) {
    // Extract directory path from the command line
    String[] parts = line.split("\\s+");
    String folderPath;
    if (parts.length == 1) {
      folderPath = System.getProperty("user.dir");
    } else if (parts.length == 2) {
      folderPath = parts[1];
    } else {
      System.out.println("Invalid ls command. Usage: ls [<directory>]");
      return;
    }

    // List files in the specified directory
    File currentDir = new File(folderPath);
    

    // Check if the specified path is a directory
    if (!currentDir.exists() || !currentDir.isDirectory()) {
      System.out.println("Invalid directory path: " + folderPath);
      return;
    }

    /*
     * printing the directory
     */
    System.out.println(
        ansi()
            .bold()
            .fg(CYAN)
            .a("\n   Directory :")
            .boldOff()
            .fg(DEFAULT)
            .a("  " + folderPath)
            .a("\n")
            .reset());
    System.out.println(
        ansi()
            .fg(YELLOW)
            .a(String.format("%-8s %-10s %-30s %-50s", "", "NER", "Name", "Annotations"))
            .reset());
    System.out.println(String.format("%-8s %-10s %-30s %-50s", "", "------", "------", "------"));

    // Initialize variables for annotation handling
    int ner = 1;
    Color oldColor = Color.values()[0];


    // List files in the directory
    File[] files = currentDir.listFiles();
    if (new File(currentDir + File.separator + "notes.txt").exists()) {
      try {
        List<String> lines =
            Files.readAllLines(Paths.get(currentDir + File.separator + "notes.txt"));
        for (int i = 0; i < files.length; i++) {
          String searchString = files[i].getName();
          List<String> correspondingLines = new ArrayList<>();
          for (String lineEntry : lines) {
            if (lineEntry.split("\t")[1].equals(searchString)) {
              correspondingLines.add(lineEntry);
            }
          }

          // Print annotation if available
          if (!correspondingLines.isEmpty()) {
            Color color = Color.values()[new Random().nextInt(9)];
            while (color == oldColor) {
              color = Color.values()[new Random().nextInt(9)];
            }
            oldColor = color;
            System.out.println(
                ansi()
                    .bold()
                    .fg(color)
                    .a(
                        String.format(
                            "%-8s %-10d %-30s %-50s",
                            "",
                            ner,
                            files[i].getName(),
                            correspondingLines.get(0).split("\t")[2].replace("\"", "")))
                    .reset());
            for (int j = 1; j < correspondingLines.size(); j++) {
              System.out.println(
                  ansi()
                      .bold()
                      .fg(color)
                      .a(
                          String.format("%50s %-50s", "", correspondingLines.get(j).split("\t")[2])
                              .replace("\"", ""))
                      .reset());
            }
          } else {
            // Print regular file if no annotation available
            System.out.println(String.format("%-8s %-10d %-30s", "", ner, files[i].getName()));
          }
          ner++;
        }
      } catch (IOException e) {
        // Handle IO exception
        e.printStackTrace();
      }
    } else {
      // Print files without annotations
      for (int i = 0; i < files.length; i++) {
        System.out.println(String.format("%-8s %-10d %-30s", "", ner, files[i].getName()));
        ner++;
      }
    }
  }
}
