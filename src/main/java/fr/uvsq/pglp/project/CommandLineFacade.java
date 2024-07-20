package fr.uvsq.pglp.project;

import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

import fr.uvsq.pglp.project.old.DirectoryNavigation;
import java.io.IOException;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

/**
 * This class is a facade for the command line interface.
 */
public class CommandLineFacade {
  private Terminal terminal;
  private LineReader lineReader;

  public CommandLineFacade() throws IOException {
    this.terminal = TerminalBuilder.builder().build();
    this.lineReader = LineReaderBuilder.builder().terminal(terminal).build();
  }

  /**
   * This method reads lines from the terminal and processes them.
   *
   * @throws IOException            if an I/O error occurs
   * @throws EndOfFileException     if the end of the input stream is reached
   * @throws UserInterruptException if the user interrupts the process
   */
  public void readLine() throws UserInterruptException, EndOfFileException {
    String line;
    String base = System.getProperty("user.dir").toString();
    new DirectoryNavigation().listDirectory(base);
    Parser parser = new Parser();

    while (true) {
      if (App.ner <= 0) {
        line = lineReader.readLine(
            System.getProperty("user.dir").toString() + ansi().bold() + "> ");
      } else {
        line = lineReader.readLine(
            System.getProperty("user.dir").toString()
                + ansi()
                    .bold()
                    .a(" [Current Element : ")
                    .fg(YELLOW)
                    .a(App.ner)
                    .fg(DEFAULT)
                    .reset()
                    .a("]")
                + "> ");
      }
      if (line.equals("exit")) {
        break;
      } else {
        parser.parse(line);
      }
    }
  }
}
