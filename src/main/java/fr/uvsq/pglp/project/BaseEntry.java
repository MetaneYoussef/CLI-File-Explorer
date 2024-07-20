package fr.uvsq.pglp.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

abstract class BaseEntry implements Entry {
  private String path = null;
  private int ner = 0;
  private ArrayList<String> annotations = new ArrayList<String>();
  static final String separator = File.separator;

  public BaseEntry(String path, int ner) throws IllegalArgumentException {
    if (exists(path)) {
      this.path = path;
    } else {
      throw new IllegalArgumentException("Path does not exist");
    }

    if (ner < 0) {
      throw new IllegalArgumentException("NER must be positive");
    } else {
      this.ner = ner;
    }

    this.annotations = getAnnotations();
  }

  @Override
  public ArrayList<String> getAnnotations() {
    annotations = new ArrayList<String>();
    try {
      if (!exists(this.getBasePath() + "/notes.txt")) {
        return annotations;
      }
      BufferedReader reader = new BufferedReader(new FileReader(getBasePath() + "/notes.txt"));

      // Read line by line until finding the annotation for this directory
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split("\t");
        if (parts.length >= 3) {
          int entryNer = Integer.parseInt(parts[0]);
          if (entryNer == getNer()) {
            annotations.add(parts[2]); // add the annotation
          }
        }
      }
      reader.close();
    } catch (IOException e) {
      System.out.println("Error reading annotations for " + getName() + " : " + e.getMessage());
    }
    return annotations;
  }

  @Override
  public void addAnnotation(String annotation) {
    try {
      // Append annotation to the notes.txt file
      BufferedWriter writer = 
          new BufferedWriter(new FileWriter(getBasePath() + "/notes.txt", true));
      writer.write(getNer() + "\t" + getName() + "\t" + annotation + "\n");
      writer.close();
      this.annotations.add(annotation);
      updateAnnotation();
    } catch (IOException e) {
      System.out.println("Error writing annotation for " + getName() + " : " + e.getMessage());
    }
  }

  @Override
  public String getName() {
    return path.substring(path.lastIndexOf(File.separator) + 1);
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public String getBasePath() {
    int lastSeparatorIndex = path.lastIndexOf(File.separator);
    if (lastSeparatorIndex != -1) {
      return path.substring(0, lastSeparatorIndex);
    } else {
      return "";
    }  
  }

  @Override
  public int getNer() {
    return ner;
  }

  @Override
  public void setNer(int ner) {
    this.ner = ner;
  }

  public static boolean exists(String path) {
    Path p = Path.of(path);
    return p.toFile().exists();
  }

  public static boolean isDirectory(String path) {
    Path p = Path.of(path);
    return p.toFile().isDirectory();
  }

  public static boolean updateAnnotation() {
    String currentDir = System.getProperty("user.dir");
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

      Files.write(
          Paths.get(currentDir + File.separator + "notes.txt"),
          lines,
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      System.out.println("Error updating annotations in notes.txt: " + e.getMessage());
      return false;
    }
    return true;
  }

}
