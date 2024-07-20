package fr.uvsq.pglp.project;

import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

/** PwdCommand class for the pwd command. */
public class PwdCommand implements Command {
  @Override
  public void execute(String line) {
    String currentDir = System.getProperty("user.dir");
    System.out.println(
        "\n" + ansi().bold().fg(YELLOW).a("PATH\n\n").fg(DEFAULT).reset() + currentDir + "\n");
  }
}
