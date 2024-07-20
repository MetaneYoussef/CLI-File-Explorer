package fr.uvsq.pglp.project;

import java.io.IOException;

// Implementation of the plus command
class PlusCommand implements Command {
  @Override
  public void execute(String line) {
    parseLine(line);
  }

  /**
   * Parses the + command.
   *
   * @param line the input line containing the command
   */
  private void parseLine(String line) {

    // Annotation.updateAnnotation();
    String[] commandArgs = line.split(" ");
    if (commandArgs.length < 2) {
      System.out.println("Usage: [<NER>] + \"text\" ");
    } else if (commandArgs.length >= 2) {
      /*
       * case of <ner> + "text"
       */
      try {
        int neer = Integer.parseInt(commandArgs[0]);
        if (neer > 0) {
          App.ner = neer;
        } else {
          System.out.println("Usage: [<NER>] - [\"text\"] ");
          return;
        }

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
            this.plus(App.ner, note.replace("\"", ""));
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
            this.plus(App.ner, note.replace("\"", ""));
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
   * This method adds notes associated to a file.
   *
   * @param ner  The ner of the file.
   * @param note The note to be added.
   * @return True if note is added, false otherwise.
   */
  private boolean plus(int ner, String note) {
    Repertoire dr = new Repertoire(System.getProperty("user.dir"));
    try {
      if (ner > dr.getContent().size() || ner < 0) {
        System.err.println("Error : NER doesn't exist");
        return false;
      }

      Entry file = dr.getEntryFromNer(ner);
      file.addAnnotation(note);
    } catch (IOException e) {
      System.out.println("Error writing to notes.txt: " + e.getMessage());
      return false;
    }

    return true;
  }

}
