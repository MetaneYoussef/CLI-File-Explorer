package fr.uvsq.pglp.project;

import java.io.File;

/**
 * CopyCommand class for the command list.
 */
public class CopyCommand implements Command {
  @Override
  public void execute(String line) {
    String currentLine = "";
    int currentNer = App.ner;
    if (line.equalsIgnoreCase("copy")) {
      currentLine = currentNer + " copy";
    } else {
      if (line.split(" ").length == 2) {
        String[] arguments = line.split("\\s+", 5);
        if (arguments[1].equalsIgnoreCase("copy") && isNumeric(arguments[0])) {
          currentNer = Integer.parseInt(arguments[0]);
          currentLine = line;
        } else {
          System.out.println("Usage: [<NER>] copy ");
        }
      } else {
        System.out.println("Usage: [<NER>] copy ");
      }
    }
    if (!currentLine.isEmpty()) {
      String[] arguments = currentLine.split("\\s+", 2);
      try {
        App.ner = Integer.parseInt(arguments[0]);

        // Get the current directory
        String currentDirectory = System.getProperty("user.dir");

        // Getting the directory and its files
        File currentDir = new File(currentDirectory);
        File[] fileList = currentDir.listFiles();
        if (fileList != null && App.ner >= 0 && App.ner <= fileList.length) {
          File selectedFile = 
              (App.ner == 0) ? fileList[fileList.length - 1] : fileList[App.ner - 1];
          Parser.file = selectedFile; 
        } else {
          System.out.println("NER out of range or void directory.");
          Parser.file = null;
        }
      } catch (NumberFormatException e) {
        System.out.println("Usage : [<NER>] copy");
      }
    }
  }
  
  // Method to check if a string is numeric
  private boolean isNumeric(String str) {
    if (str == null || str.isEmpty()) {
      return false;
    }
    for (char c : str.toCharArray()) {
      if (!Character.isDigit(c)) {
        return false;
      }
    }
    return true;
  } 
}
