
package fr.uvsq.pglp.project.old;

import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * . class dedicated to parsing the commands form the jline command line
 */
public class Parser {

  /**
   * Parses the ls command.
   *
   * @param line the input line containing the command
   */
  public static void parsels(String line) {
    DirectoryNavigation ls = new DirectoryNavigation();
    ls.listDirectory(ls.getCurrentDir());
  }

  /**
   * Parses the ls command.
   *
   * @param line the input line containing the command
   */
  public static void parsepwd(String line) {
    DirectoryNavigation ls = new DirectoryNavigation();
    System.out.println("\n" + ansi().bold().fg(YELLOW).a("PATH\n\n").fg(DEFAULT).reset()
        + ls.getCurrentDir() + "\n");
  }

  /**
   * Parses the find command.
   *
   * @param line the input line containing the command
   */
  public static void parsefind(String line) {
    String[] commandArgs = line.split(" ");
    if (commandArgs.length == 2) {
      if (commandArgs[0].equalsIgnoreCase("find")) {
        FileManipulation.find(commandArgs[1]);
      } else {
        System.out.println("Usage : find <nom fichier>");
      }
    } else {
      System.out.println("Usage : find <nom fichier>");
    }
  }

  /**
   * Parses the Visu command.
   *
   * @param line the input line containing the command
   */
  public static void parseVisu(String line) {
    String[] commandArgs = line.split(" ");

    if (commandArgs.length > 2) {
      System.out.println("Usage: [<NER>] visu");
    } else if (commandArgs.length == 1) {
      FileManipulation.visulizing(App.ner);
    } else if (commandArgs.length == 2) {
      if (commandArgs[1].equalsIgnoreCase("visu")) {
        try {
          if (Integer.parseInt(commandArgs[0]) > 0) {
            App.ner = Integer.parseInt(commandArgs[0]);
            FileManipulation.visulizing(App.ner);
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
   * Parses the + command.
   *
   * @param line the input line containing the command
   */
  public static void parsePlus(String line) {

    Annotation.updateAnnotation();
    String[] commandArgs = line.split(" ");
    if (commandArgs.length < 2) {
      System.out.println("Usage: [<NER>] + \"text\" ");
    } else if (commandArgs.length >= 2) {
      /*
       * case of <ner> + "text"
       */
      try {
        App.ner = Integer.parseInt(commandArgs[0]);

        if (commandArgs.length == 2) {
          System.out.println("Usage: [<NER>] + \"text\" ");
        } else {
          if (commandArgs[2].startsWith("\"")
              && commandArgs[commandArgs.length - 1].endsWith("\"")) {
            String note = commandArgs[2];
            if (!note.endsWith("\"")) {
              int i = 3;
              while (!note.endsWith("\"") && i < commandArgs.length) {
                note += " " + commandArgs[i];
                i++;
              }
              i = 0;
            }
            Annotation.plus(App.ner, note.replace("\"", ""));
            note = "";
          } else {
            System.out.println("Usage: [<NER>] + \"text\" ");
          }
        }
      } catch (NumberFormatException e) {

        /*
         * case of + "text"
         */
        if (commandArgs[0].equals("+")) {
          if (commandArgs[1].startsWith("\"")) {
            String note = commandArgs[1];
            if (!note.endsWith("\"") && commandArgs[commandArgs.length - 1].endsWith("\"")) {
              int i = 3;
              while (!note.endsWith("\"") && i < commandArgs.length) {
                note += " " + commandArgs[i];
                i++;
              }
              i = 0;
            }
            Annotation.plus(App.ner, note.replace("\"", ""));
            note = "";
          } else {
            System.out.println("Usage: [<NER>] + \"text\" ");
          }
        } else {
          System.out.println("Error : NER Should Be a number\nUsage: [<NER>] + \"text\" ");

        }
      }
    }
  }

  /**
   * Parses the - command.
   *
   * @param line the input line containing the command
   */
  public static void parseMinus(String line) {
    String[] commandArgs = line.split(" ");
    if (commandArgs.length < 1) {
      System.out.println("Usage: [<NER>] - [\"text\"] ");
    } else if (commandArgs.length == 1) {
      /*
       * case of -
       */
      Annotation.minus(App.ner, "");
    } else if (commandArgs.length == 2) {
      /*
       * case of - text
       */
      if (commandArgs[0].equals("-")) {
        String note = commandArgs[1];
        Annotation.minus(App.ner, note);
      } else {
        /*
         * case of ner -
         */
        try {
          App.ner = Integer.parseInt(commandArgs[0]);
          Annotation.minus(App.ner, "");
        } catch (NumberFormatException e) {
          System.out.println("Usage: [<NER>] - [\"text\"] ");
        }
      }
    } else if (commandArgs.length >= 2) {

      /*
       * case of - "text"
       */
      if (commandArgs[0].equals("-")) {
        if (commandArgs[1].startsWith("\"") && commandArgs[commandArgs.length - 1].endsWith("\"")) {
          String note = commandArgs[1];
          if (!note.endsWith("\"")) {
            int i = 3;
            while (!note.endsWith("\"") && i < commandArgs.length) {
              note += " " + commandArgs[i];
              i++;
            }
            i = 0;
          }
          Annotation.minus(App.ner, note.replace("\"", ""));
        } else {
          System.out.println("Usage: [<NER>] - [\"text\"] ");
        }

      } else {
        try {
          /*
           * case of ner - "text"
           */
          App.ner = Integer.parseInt(commandArgs[0]);
          if (commandArgs[2].startsWith("\"")) {
            String note = commandArgs[2];
            if (!note.endsWith("\"")) {
              int i = 3;
              while (!note.endsWith("\"") && i < commandArgs.length) {
                note += " " + commandArgs[i];
                i++;
              }
              i = 0;
            }
            Annotation.minus(App.ner, note.replace("\"", ""));
          } else {
            System.out.println("Usage: [<NER>] - [\"text\"] ");
          }

        } catch (NumberFormatException e) {
          System.out.println("Usage: [<NER>] - [\"text\"] ");

        }
      }
    }
  }

  /**
   * Parses length one navigation commands.
   *
   * @param line the command line to parse
   */
  public static void parseLengthOneNavigationCommands(String line) {
    NavigationFunctions.navigate(line);
    Annotation.updateAnnotation();
    App.ner = -1;
  }

  /**
   * Parses length one Copy command.
   *
   * @param line the command line to parse
   */
  public static void parseLengthOneCopyCommand(String line) {
    FileDetails selectedFile = FileOperations.copy(App.ner);
    App appFunctions = App.getInstance();
    appFunctions.setFilePathAndStatus(selectedFile.getPath(), line, selectedFile.getFile());
  }

  /**
   * Parses length one Cut command.
   *
   * @param line the command line to parse
   */
  public static void pasreLengthOneCutCommand(String line) {
    FileDetails selectedFile = FileOperations.copy(App.ner);
    App appFunctions = App.getInstance();
    appFunctions.setFilePathAndStatus(selectedFile.getPath(), line, selectedFile.getFile());
    try {
      if (selectedFile.getFile().isDirectory()) {
        Path directoryPath = Paths.get(appFunctions.getFilePath());
        List<String> directoryContent = ContentToMemory.readDirectoryToMemory(directoryPath);
        appFunctions.setDirectoryContent(directoryContent);
        appFunctions.setFileType("directory");
      } else {
        Path filePath = Paths.get(appFunctions.getFilePath());
        byte[] fileContent = ContentToMemory.readFileToMemory(filePath);
        appFunctions.setFileContent(fileContent);
        appFunctions.setFileType("file");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      DirectoryNavigation dr = new DirectoryNavigation();
      int tempner = dr.getNerFromFile(selectedFile.getFile().getName());
      Annotation.minus(tempner, "");
      FileOperations.delete(appFunctions.getFile());
      Annotation.updateAnnotation();
      App.ner = -1;
    }

  }

  /**
   * Parses length one Paste command.
   *
   * @param line the command line to parse
   */
  public static void parseLengthOnepasteCommand(String line) {
    App appFunctions = App.getInstance();
    if (appFunctions.getFile() != null) {
      String selectedFilePath = FileOperations
          .paste(appFunctions.getFile(), appFunctions.getStatus());
      if (selectedFilePath == null) {
        System.out.println("It was not possible to paste the file or directory");
      }
      appFunctions.setFileType("");
      Annotation.updateAnnotation();
    } else {
      System.out.println("There is no file or directory to paste");
    }
  }

  /**
   * Parses length two Navigation command.
   *
   * @param line the command line to parse
   */
  public static void parseLengthtwoNavigationCommand(String line) {
    String[] arguments = line.split("\\s+", 2);
    try {
      App.ner = Integer.parseInt(arguments[0]);
      NavigationFunctions.navigate2(App.ner, arguments[1]);
      App.ner = -1;
      Annotation.updateAnnotation();
    } catch (NumberFormatException e) {
      System.out.println("Usage : [<NER>] .");
    }

  }

  /**
   * Parses length two Mkdir command.
   *
   * @param line the command line to parse
   */
  public static void parseLengthTwoMakeDirectoryCommand(String line) {
    String[] arguments = line.split("\\s+", 2);
    FolderManipulation.mkDirectory(arguments[1]);
    Annotation.updateAnnotation();
  }

  /**
   * Parses length two Copy command.
   *
   * @param line the command line to parse
   */
  public static void parseLengthTwoCopyCommand(String line) {
    String[] arguments = line.split("\\s+", 2);
    try {
      App.ner = Integer.parseInt(arguments[0]);
      FileDetails selectedFile = FileOperations.copy(App.ner);
      App appFunctions = App.getInstance();
      appFunctions.setFilePathAndStatus(selectedFile.getPath(),
          arguments[1].toString(), selectedFile.getFile());
    } catch (NumberFormatException e) {
      System.out.println("Usage : [<NER>] copy");
    }
  }

  /**
   * Parses length two Cut command.
   *
   * @param line the command line to parse
   */
  public static void parseLengthTwoCutCommand(String line) {

    FileDetails selectedFile = FileOperations.copy(App.ner);
    if (selectedFile != null) {
      DirectoryNavigation dr = new DirectoryNavigation();
      int tempner = dr.getNerFromFile(selectedFile.getFile().getName());
      Annotation.minus(tempner, "");
      FileOperations.delete(selectedFile.getFile());
      Annotation.updateAnnotation();
      App.ner = -1;
    } else {
      System.out.println("");
    }

  }
}
