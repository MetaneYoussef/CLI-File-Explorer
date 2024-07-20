package fr.uvsq.pglp.project;

import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

import java.util.ArrayList;
import java.util.List;

// Implementation of the find command
class FindCommand implements Command {
  @Override
  public void execute(String line) {
    parseLine(line);
  }

  /**
   * Parses the find command.
   *
   * @param line the input line containing the command
   */
  private void parseLine(String line) {
    String[] commandArgs = line.split(" ");
    if (commandArgs.length == 2) {
      if (commandArgs[0].equalsIgnoreCase("find")) {
        find(commandArgs[1]);
      } else {
        System.out.println("Usage : find <nom fichier>");
      }
    } else {
      System.out.println("Usage : find <nom fichier>");
    }
  }

  /**
   * Finds files with the given file name in the current directory and its
   * subdirectories.
   *
   * @param fileName The name of the file to search for.
   * @return A list of file paths that match the given file name.
   */

  private ArrayList<String> find(String fileName) {
    try {
      Repertoire dr = new Repertoire(System.getProperty("user.dir"));
      List<Entry> entries = dr.getContent();
      ArrayList<String> correspondingFiles = new ArrayList<>();
      if (entries != null) {
        for (Entry file : entries) {
          if (BaseEntry.isDirectory(file.getPath())) {
            correspondingFiles.addAll(find(fileName, file.getPath()));
          } else {
            if (fileName.equalsIgnoreCase(file.getName())) {
              correspondingFiles.add(file.getPath());
            }
          }
        }
      }
      for (String file : correspondingFiles) {
        System.out.println((file.replace(new Fichier(file).getName(), ""))
            + ansi().fg(YELLOW).bold().a(new Fichier(file).getName()).reset());
      }
      return correspondingFiles;
    } catch (Exception e) {
      System.out.println("Error : Directory not found");
      return null;
    }

  }

  /**
   * Finds files with the given file name in the specified directory and its
   * subdirectories.
   *
   * @param fileName The name of the file to search for.
   * @param dir      The directory to search in.
   * @return A list of file paths that match the given file name.
   */
  private ArrayList<String> find(String fileName, String dir) {
    Repertoire dr = new Repertoire(dir);
    try {
      dr = new Repertoire(dir);
      List<Entry> entries = dr.getContent();
      ArrayList<String> correspondingFiles = new ArrayList<>();
      if (entries != null) {
        for (Entry file : entries) {
          if (BaseEntry.isDirectory(file.getPath())) {
            correspondingFiles.addAll(find(fileName, file.getPath()));
          } else {
            if (fileName.equalsIgnoreCase(file.getName())) {
              correspondingFiles.add(file.getPath());
            }
          }
        }
      }
      return correspondingFiles;
    } catch (Exception e) {
      System.out.println("Error : Directory not found");
      return null;
    }

  }

}
