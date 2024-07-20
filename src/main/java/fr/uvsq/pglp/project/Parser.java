package fr.uvsq.pglp.project;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Parser class that acts as an invoker.
 */
public class Parser {
  private Map<String, Command> commandMap;
  public static File file;

  /**
   * Parser command definition.
   */
  public Parser() {
    commandMap = new HashMap<>();
    // Configuration of available commands
    commandMap.put("ls", new LsCommand());
    commandMap.put("pwd", new PwdCommand());
    commandMap.put("find", new FindCommand());
    commandMap.put("visu", new VisuCommand());
    commandMap.put("+", new PlusCommand());
    commandMap.put("-", new MinusCommand());
    commandMap.put(".", new LengthOneNavigationCommand());
    commandMap.put("..", new LengthTwoNavigationCommand());
    commandMap.put("copy", new CopyCommand());
    commandMap.put("cut", new CutCommand());
    commandMap.put("paste", new PasteCommand());
    commandMap.put("mkdir", new MakeDirectoryCommand());
    commandMap.put("NER", new NerCommand());
  }

  /**
   * Parser command execute call.
   */
  public void parse(String line) {
    if (line == null || line.equalsIgnoreCase("exit")) {
      System.out.println("\nClosing the program ...\n");
      return;
    }

    String[] parts = line.split("\\s+");
    if (parts.length == 1 && isNumeric(parts[0])) {
      // Check if line has a single argument and that argument is a number (NER
      // command)
      Command command = commandMap.get("NER");
      if (command != null) {
        command.execute(parts[0]);
      } else {
        System.out.println("Invalid command: " + line
            + ". Please enter one of the valid commands.");
      }
      return;
    }

    String commandName = parts[0];
    String argument = parts.length > 1 ? parts[1] : null;

    // Verify if the command is in the first position of the line
    Command command = commandMap.get(commandName);
    if (command != null) {
      command.execute(line);
    } else if (argument != null) {
      // If the command is not in the first position, we check if it is in the second
      // position
      commandName = argument.split("\\s+")[0];
      command = commandMap.get(commandName);
      if (command != null) {
        command.execute(line);
      } else {
        System.out.println("Invalid command: " + line
            + ". Please enter one of the valid commands.");
      }
    } else {
      System.out.println("Invalid command: " + line + ". Please enter one of the valid commands.");
    }
  }

  // Method to check if a string is numeric
  private boolean isNumeric(String str) {
    return str.matches("\\d+");
  }
}
