package fr.uvsq.pglp.project.old;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * This class contains all the commands related to Annotations.
 */
public class Annotation {
  /**
   * This method adds notes associated to a file.
   *
   * @param ner  The ner of the file.
   * @param note The note to be added.
   * @return True if note is added, false otherwise.
   */
  public static boolean plus(int ner, String note) {
    DirectoryNavigation dr = new DirectoryNavigation();
    File currentDir = new File(dr.getCurrentDir());
    if (ner > currentDir.listFiles().length && ner > 0) {
      System.err.println("Error : NER doesn't exist");
      return false;
    }

    try {
      String filename = dr.getFileFromNer(ner).getName();
      Files.write(Paths.get(currentDir + File.separator + "notes.txt"),
          (ner + "\t" + filename + "\t" + note + "\n").getBytes(),
          StandardOpenOption.CREATE,
          StandardOpenOption.APPEND);
    } catch (IOException e) {
      System.out.println("Error writing to notes.txt: " + e.getMessage());
      return false;
    }

    return true;
  }

  /**
   * This method removes notes associated to a file.
   *
   * @param ner  The ner of the file.
   * @param note The note to be added.
   * @return True if note is added, false otherwise.
   */
  public static boolean minus(int ner, String note) {
    File currentDir = new File(new DirectoryNavigation().getCurrentDir());
    if (ner > currentDir.listFiles().length && ner > 0) {
      System.err.println("Error : NER doesn't exist");
      return false;
    }
    File notes = new File(currentDir + File.separator + "notes.txt");
    if (!notes.exists()) {
      System.out.println("Annotation doesnt exist");
      return false;
    }

    try {
      List<String> lines = Files.readAllLines(Paths.get(currentDir + File.separator + "notes.txt"));
      DirectoryNavigation dr = new DirectoryNavigation();
      String filename = dr.getFileFromNer(ner).getName();

      if (note.equals("")) {
        lines.removeIf(line -> line.contains(String.valueOf(ner)));
      } else {
        lines.removeIf(line -> line.equals(ner + "\t" + filename + "\t" + note));
      }

      Files.write(Paths.get(currentDir + File.separator + "notes.txt"),
          lines,
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING);

    } catch (IOException e) {
      System.out.println("Error deleting lines from notes.txt: " + e.getMessage());
      return false;
    }

    return true;
  }

  /**
   * Updates the annotation.
   *
   * @return True if the annotation is updated successfully, false otherwise.
   */
  public static boolean updateAnnotation() {
    DirectoryNavigation dr = new DirectoryNavigation();
    String currentDir = dr.getCurrentDir();
    File[] fileList = new File(currentDir).listFiles();
    if (!(new File(currentDir + File.separator + "notes.txt").exists())) {
      return false;
    }
    try {
      List<String> lines = Files.readAllLines(Paths.get(currentDir + File.separator + "notes.txt"));

      for (int i = 0; i < lines.size(); i++) {
        String filename = lines.get(i).split("\t")[1];
        int noteNer = Integer.parseInt(lines.get(i).split("\t")[0]);
        for (int ner = 1; ner <= fileList.length; ner++) {
          if (fileList[ner - 1].getName().equals(filename) && ner != noteNer) {
            lines.set(i, lines.get(i).replace(lines.get(i).split("\t")[0], String.valueOf(ner)));
          }
        }
      }

      Files.write(Paths.get(currentDir + File.separator + "notes.txt"),
          lines,
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      System.out.println("Error deleting lines from notes.txt: " + e.getMessage());
      return false;
    }
    return true;
  }
}
