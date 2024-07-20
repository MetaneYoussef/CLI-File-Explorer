
package fr.uvsq.pglp.project.old;

import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

import java.io.File;
import java.util.List;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

/**
 * The main class of the application.
 */
public class App {

  /*
   * Current selected element NER.
   */
  public static int ner;

  private String filePath;
  private String status;
  private File file;
  private byte[] fileContent;
  private List<String> directoryContent;
  private String fileType;
  private static App instance;

  /**
   * Instance to manage App variables.
   */
  public static synchronized App getInstance() {
    if (instance == null) {
      instance = new App();
    }
    return instance;
  }

  /**
   * Method to change file, path and status.
   */
  public void setFilePathAndStatus(String filePath, String status, File file) {
    this.filePath = filePath;
    this.status = status;
    this.file = file;
  }

  public void setFileContent(byte[] fileContent) {
    this.fileContent = fileContent;
  }

  public void setDirectoryContent(List<String> directoryContent) {
    this.directoryContent = directoryContent;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getFilePath() {
    return filePath;
  }

  public String getStatus() {
    return status;
  }

  public File getFile() {
    return file;
  }

  public byte[] getFileContent() {
    return fileContent;
  }

  public List<String> getDirectoryContent() {
    return directoryContent;
  }

  public String getFileType() {
    return fileType;
  }

  /**
   * main method.
   *
   * @param args does this.
   */
  public static void main(String[] args) {
    /*
     * printing the first lines containing the directory listing
     */
    DirectoryNavigation ls = new DirectoryNavigation();
    String currentDir = ls.getCurrentDir();
    ls.listDirectory(currentDir);

    try {
      Terminal terminal = TerminalBuilder.terminal();
      LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
      String line;
      /*
       * the rest of the commands
       */
      while (true) {

        /*
         * Prompt
         */
        if (ner <= 0) {
          line = reader.readLine(ls.getCurrentDir().toString() + " > ");
        } else {
          line = reader.readLine(ls.getCurrentDir().toString() + ansi().bold()
              .a(" [Current Element : ").fg(YELLOW).a(ner).fg(DEFAULT).reset().a("]")
              + "> ");
        }

        if (line == null || line.equalsIgnoreCase("exit")) {
          System.out.println("\nClosing the program ...\n");
          break;
        } else if (line.split(" ").length > 0) {

          if (line.equalsIgnoreCase("ls")) {
            /*
             * **********************
             * Directory listing command
             ************************/
            Parser.parsels(line);

          } else if (line.equalsIgnoreCase("pwd")) {
            /*
             * **********************
             * Print working Directory command
             ************************/
            Parser.parsepwd(line);

          } else if (line.contains("find ")) {

            /*
             * **********************
             * File search Command
             ************************/
            Parser.parsefind(line);

          } else if (line.contains("visu")) {
            /*
             * ************************
             * visualizing command
             **************************/
            Parser.parseVisu(line);

          } else if (line.contains("+ ")) {
            /*
             * ************************
             * Annotation Adding command
             **************************/
            Parser.parsePlus(line);

          } else if ((line + " ").contains("- ")) {
            /*
             * ************************
             * Annotation Removing command
             **************************/
            Parser.parseMinus(line);

          } else if (line.equalsIgnoreCase("..")) {
            /*
             * *************************
             * Navigation to the parent directory
             ************************/
            Parser.parseLengthOneNavigationCommands(line);

          } else if ((line.contains("."))) {

            /*
             * *****************************
             * Navigation to the selected folder commmand
             *****************************/
            String currentLine = "";
            if (line.split(" ").length == 2) {
              String[] arguments = line.split("\\s+", 5);
              if (arguments[1].equalsIgnoreCase(".") && FileOperations.isNumber(arguments[0])) {
                ner = Integer.parseInt(arguments[0]);
                currentLine = line;
              } else {
                System.out.println("Usage: [<NER>] . ");
              }
            } else if (line.equalsIgnoreCase(".")) {
              currentLine = ner + " .";
            } else {
              System.out.println("Usage: [<NER>] . ");
            }

            if (!currentLine.isEmpty()) {
              Parser.parseLengthtwoNavigationCommand(currentLine);
            }

          } else if (line.contains("copy")) {
            /*
             * *************************
             * Copy Command
             ************************/
            String currentLine = "";
            if (line.split(" ").length == 2) {
              String[] arguments = line.split("\\s+", 5);
              if (arguments[1].equalsIgnoreCase("copy") && FileOperations.isNumber(arguments[0])) {
                ner = Integer.parseInt(arguments[0]);
                currentLine = line;
              } else {
                System.out.println("Usage: [<NER>] copy ");
              }
            } else if (line.equalsIgnoreCase("copy")) {
              currentLine = ner + " copy";
            } else {
              System.out.println("Usage: [<NER>] copy ");
            }

            if (!currentLine.isEmpty()) {
              Parser.parseLengthTwoCopyCommand(currentLine);
            }
          } else if (line.contains("cut")) {
            /*
             * *************************
             * Delete Command
             ************************/
            String currentLine = "";
            if (line.split(" ").length == 2) {
              String[] arguments = line.split("\\s+", 5);
              if (arguments[1].equalsIgnoreCase("cut") && FileOperations.isNumber(arguments[0])) {
                ner = Integer.parseInt(arguments[0]);
                currentLine = line;
              } else {
                System.out.println("Usage: [<NER>] cut ");
              }
            } else if (line.equalsIgnoreCase("cut")) {
              currentLine = ner + " cut";
            } else {
              System.out.println("Usage: [<NER>] cut ");
            }
            if (!currentLine.isEmpty()) {
              Parser.parseLengthTwoCutCommand(currentLine);
            }

          } else if (line.equalsIgnoreCase("paste")) {
            /*
             * **********************
             * Paste Command
             ************************/
            Parser.parseLengthOnepasteCommand(line);

          } else if (line.contains("mkdir")) {

            String[] arguments = line.split("\\s+", 5);
            if ((line.split(" ").length == 2) && (arguments[0].equalsIgnoreCase("mkdir"))
                && (arguments[1] != null)) {
              Parser.parseLengthTwoMakeDirectoryCommand(line);
            } else {
              System.out.println("Usage: mkdir <nom> ");
            }

          } else if ((line.split(" ").length == 1) && FileOperations.isNumber(line)) {

            /*
             * *****************************
             * Asign the NER new value
             *****************************/
            int nerToTest = Integer.parseInt(line);
            if (DirectoryNavigation.reachable(nerToTest)) {
              ner = Integer.parseInt(line);
            } else {
              System.out.println("Error : NER doesn't exist");
            }

          } else {
            System.out.println("Invalid command: " + line
                + ". Please enter one of the valid commands.");
          }

        } else {
          System.out.println("Invalid command: " + line
              + ". Please enter one of the valid commands.");
        }
      }

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

  }
}
