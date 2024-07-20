package fr.uvsq.pglp.project;

import java.io.File;

/**
 * LengthOneNavigationCommand class for the command list.
 */
public class LengthOneNavigationCommand implements Command {
  @Override
  public void execute(String line) {
    String currentLine = "";
    int currentNer = App.ner;
    if (line.equalsIgnoreCase(".")) {
      currentLine = currentNer + " .";
    } else {
      if (line.split(" ").length == 2) {
        String[] arguments = line.split("\\s+", 5);
        if (arguments[1].equalsIgnoreCase(".") && isNumeric(arguments[0])) {
          currentNer = Integer.parseInt(arguments[0]);
          currentLine = line;
        } else {
          System.out.println("Usage: [<NER>] . ");
        }
      } else {
        System.out.println("Usage: [<NER>] . ");
      }
    }
    if (!currentLine.isEmpty()) {
      String[] arguments = currentLine.split("\\s+", 2);
      try {
        App.ner = Integer.parseInt(arguments[0]);
        // Get the current directory
        String currentDirectory = System.getProperty("user.dir");
        // System.out.println("Current directory: " + currentDirectory);

        // Getting the directory and its files
        /*
        Repertoire myCurrentDirectory = new Repertoire(currentDirectory, 0);
        try {
          List<Entry> mySize = myCurrentDirectory.getContent();
        } catch (IOException e) {
          System.out.println("Usage : [<NER>] .");
        }
        */
        File currentDir = new File(currentDirectory);
        File[] files = currentDir.listFiles();
  
        if (files != null && App.ner >= 0 && App.ner <= files.length) {
          File selectedFile = (App.ner == 0) ? files[files.length - 1] : files[App.ner - 1];
  
          if (selectedFile.isDirectory()) {
            // System.out.println("Moving to the directory " + ((ner == 0) ? files.length :
            // ner)
            // + " (Directory): " + selectedFile.getName());
            System.setProperty("user.dir", selectedFile.getAbsolutePath());
            // System.out.println("New current directory: " +
            // System.getProperty("user.dir"));
          } else {
            System.out.println("The selected element " + ((App.ner == 0) ? files.length : App.ner)
                + " is not a directory.");
          }
        } else {
          System.out.println("Invalid NER.");
        }
        App.ner = 0;
        Repertoire.updateAnnotation();
      } catch (NumberFormatException e) {
        System.out.println("Usage : [<NER>] .");
      }
    }
  }

  // Method to check if a string is numeric
  private boolean isNumeric(String str) {
    return str.matches("\\d+");
  }
}
