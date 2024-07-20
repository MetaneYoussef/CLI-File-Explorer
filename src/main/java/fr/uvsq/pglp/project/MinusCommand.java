package fr.uvsq.pglp.project;

import java.io.IOException;
import java.util.List;

// Implementation of the minus command
class MinusCommand implements Command {
  @Override
  public void execute(String line) {
    parseLine(line);
  }

  /**
   * Parses the - command.
   *
   * @param line the input line containing the command
   */
  private void parseLine(String line) {

    String[] commandArgs = line.split(" ");
    if (commandArgs.length < 1) {
      System.out.println("Usage: [<NER>] - [\"text\"] ");
    } else if (commandArgs.length == 1) {
      /*
       * case of -
       */
      minus(App.ner, "");
    } else if (commandArgs.length == 2) {
      /*
       * case of - text
       */
      if (commandArgs[0].equals("-")) {
        String note = commandArgs[1];
        minus(App.ner, note);
      } else {
        /*
         * case of ner -
         */
        try {
          App.ner = Integer.parseInt(commandArgs[0]);
          minus(App.ner, "");
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
          minus(App.ner, note.replace("\"", ""));
        } else {
          System.out.println("Usage: [<NER>] - [\"text\"] ");
        }

      } else {
        try {
          /*
           * case of ner - "text"
           */
          int neer = Integer.parseInt(commandArgs[0]);
          if (neer > 0) {
            App.ner = neer;
          } else {
            System.out.println("Usage: [<NER>] - [\"text\"] ");
            return;
          }
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
            minus(App.ner, note.replace("\"", ""));
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
   * This method removes notes associated to a file.
   *
   * @param ner  The ner of the file.
   * @param note The note to be added.
   * @return True if note is added, false otherwise.
   */
  public boolean minus(int ner, String note) {
    Repertoire dr = new Repertoire(System.getProperty("user.dir"));

    String notesPath = System.getProperty("user.dir") + BaseEntry.separator + "notes.txt";
    if (!BaseEntry.exists(notesPath)) {
      System.out.println("Annotation doesnt exist");
      return false;
    }

    try {
      if (ner > dr.getContent().size() || ner < 0) {
        System.err.println("Error : NER doesn't exist");
        return false;
      }
      Fichier notes = new Fichier(notesPath);
      List<String> lines = notes.read();

      String filename = dr.getEntryFromNer(ner).getName();

      if (note.equals("")) {
        lines.removeIf(line -> line.contains(String.valueOf(ner)));
      } else {
        lines.removeIf(line -> line.equals(ner + "\t" + filename + "\t" + note));
      }

      notes.write(lines);
      BaseEntry.updateAnnotation();

    } catch (IOException e) {
      System.out.println("Error deleting lines from notes.txt: " + e.getMessage());
      return false;
    }

    return true;

  }

}
