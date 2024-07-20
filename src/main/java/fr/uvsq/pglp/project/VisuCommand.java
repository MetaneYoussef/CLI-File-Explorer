package fr.uvsq.pglp.project;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

// Implementation of the visu command
class VisuCommand implements Command {

  @Override
  public void execute(String line) {
    parseLine(line);
  }

  private void parseLine(String line) {
    String[] commandArgs = line.split(" ");

    if (commandArgs.length > 2) {
      System.out.println("Usage: [<NER>] visu");
    } else if (commandArgs.length == 1) {
      visualize(App.ner);
    } else if (commandArgs.length == 2) {
      if (commandArgs[1].equalsIgnoreCase("visu")) {
        try {
          if (Integer.parseInt(commandArgs[0]) > 0) {
            int neer = Integer.parseInt(commandArgs[0]);
            if (neer > 0) {
              App.ner = neer;
            } else {
              System.out.println("Usage: [<NER>] - [\"text\"] ");
              return;
            }
            visualize(App.ner);
          } else {
            System.out.println("Error : Ner should be bigger than 0");
          }
        } catch (NumberFormatException e) {
          System.out.println("Error : NER Should Be a number");
        }
      } else {
        System.out.println("Usage: [<NER>] visu");
      }
    }
  }

  /**
   * Reads and prints the contents of a file specified by the given ner.
   *
   * @param ner The number representing the file to be read.
   */
  private void visualize(int ner) {
    Repertoire dr = new Repertoire(System.getProperty("user.dir"));

    if (dr.getEntryFromNer(ner) == null) {
      System.out.println("Error : NER doesn't exist");
    } else {
      Entry selectedFile = dr.getEntryFromNer(ner);
      String[] textFileExtensions = { ".txt", ".csv", ".json",
          ".xml", ".md", ".html",
          ".css", ".js", ".py",
          ".java", ".c", ".cpp", "adoc" };
      boolean isTextFile = false;
      for (String extension : textFileExtensions) {
        if (selectedFile.getName().endsWith(extension)) {
          isTextFile = true;
        }
      }
      if (BaseEntry.isDirectory(selectedFile.getPath())) {
        System.out.println("Error : Selected NER is a directory ");
      } else if (!isTextFile) {
        System.out.println(selectedFile.getName() + " is not a text file");
        printSize((Fichier) selectedFile);
      } else {
        try {
          List<String> line = ((Fichier) selectedFile).read();
          for (String l : line) {
            System.out.println(l);
          }
        } catch (IOException e) {
          System.out.println("Error reading file: " + e.getMessage());
        }
      }

    }

  }

  private void printSize(Fichier selectedFile) {
    long sizeInBytes = selectedFile.getSize();
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

}
