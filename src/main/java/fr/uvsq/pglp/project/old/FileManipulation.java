package fr.uvsq.pglp.project.old;

import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class handles file manipulation operations.
 */
public class FileManipulation {

  /**
   * Reads and prints the contents of a file specified by the given ner.
   *
   * @param ner The number representing the file to be read.
   */
  public static void visulizing(int ner) {
    DirectoryNavigation dn = new DirectoryNavigation();
    if (dn.getFileFromNer(ner) == null) {
      System.out.println("Error : NER doesn't exist");
    } else {

      File selectedFile = dn.getFileFromNer(ner);
      String[] textFileExtensions = { ".txt", ".csv", ".json",
          ".xml", ".md", ".html",
          ".css", ".js", ".py",
          ".java", ".c", ".cpp", "adoc" };
      boolean isTextFile = Arrays.stream(textFileExtensions)
          .anyMatch(selectedFile.getName()::endsWith);
      if (selectedFile.isDirectory()) {
        System.out.println("Error : Selected NER is a directory ");
      } else if (!isTextFile) {
        System.out.println(selectedFile.getName() + " is not a text file");
        printSize(selectedFile);
      } else {
        try (Scanner scanner = new Scanner(selectedFile)) {
          while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
          }
        } catch (FileNotFoundException e) {
          System.out.println("Error reading file: " + e.getMessage());
        }
      }

    }

  }

  private static void printSize(File selectedFile) {
    long sizeInBytes = selectedFile.length();
    double sizeInKo = sizeInBytes / (1024.0);
    double sizeInMo = sizeInKo / (1024.0);
    double sizeInGo = sizeInMo / (1024.0);
    final DecimalFormat dFormat = new DecimalFormat("0.00");
    if (sizeInBytes < 1023.0) {
      System.out.println("the size of " + selectedFile.getName()
          + " is : " + dFormat.format(sizeInBytes) + " Bytes");
    } else if (sizeInKo < 1023.0) {
      System.out.println("the size of " + selectedFile.getName()
          + " is : " + dFormat.format(sizeInKo) + " Kb");
    } else if (sizeInMo < 1023.0) {
      System.out.println("the size of " + selectedFile.getName()
          + " is : " + dFormat.format(sizeInMo) + " Mb");
    } else {
      System.out.println("the size of " + selectedFile.getName()
          + " is : " + dFormat.format(sizeInGo) + " Gb");
    }
  }

  /**
   * Finds files with the given file name in the current directory and its
   * subdirectories.
   *
   * @param fileName The name of the file to search for.
   * @return A list of file paths that match the given file name.
   */

  public static ArrayList<String> find(String fileName) {
    File[] files = new File(new DirectoryNavigation().getCurrentDir()).listFiles();
    ArrayList<String> correspondingFiles = new ArrayList<>();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          correspondingFiles.addAll(find(fileName, file.getPath()));
        } else {
          if (fileName.equalsIgnoreCase(file.getName())) {
            correspondingFiles.add(file.getPath());
          }
        }
      }
    }
    for (String file : correspondingFiles) {
      System.out.println((file.replace(new File(file).getName(), ""))
          + ansi().fg(YELLOW).bold().a(new File(file).getName()).reset());
    }
    return correspondingFiles;
  }

  /**
   * Finds files with the given file name in the specified directory and its
   * subdirectories.
   *
   * @param fileName The name of the file to search for.
   * @param dir      The directory to search in.
   * @return A list of file paths that match the given file name.
   */
  public static ArrayList<String> find(String fileName, String dir) {
    File[] files = new File(dir).listFiles();
    ArrayList<String> correspondingFiles = new ArrayList<>();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          correspondingFiles.addAll(find(fileName, file.getPath()));
        } else {
          if (fileName.equalsIgnoreCase(file.getName())) {
            correspondingFiles.add(file.getPath());
          }
        }
      }
    }
    return correspondingFiles;
  }
}
