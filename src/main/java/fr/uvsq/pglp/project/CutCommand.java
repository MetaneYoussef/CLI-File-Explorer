package fr.uvsq.pglp.project;

// Implementation of the cut command
class CutCommand implements Command {
  @Override
  public void execute(String line) {
    parseLine(line);
  }

  /**
   * Parses the cut command.
   *
   * @param line the input line containing the command
   */
  private void parseLine(String line) {
    String currentLine = "";
    int neer = App.ner;
    if (line.split(" ").length == 2) {
      String[] arguments = line.split("\\s+", 5);
      if (arguments[1].equalsIgnoreCase("cut")) {
        try {
          neer = Integer.parseInt(arguments[0]);
          if (neer > 0) {
            App.ner = neer;
          } else {
            System.out.println("Ner should be bigger than 0");
            return;
          }
          currentLine = line;
        } catch (NumberFormatException e) {
          System.out.println("NER should be a number");
        }
        currentLine = line;
      } else {
        System.out.println("Usage: [<NER>] cut ");
      }
    } else if (line.equalsIgnoreCase("cut")) {
      currentLine = neer + " cut";
    } else {
      System.out.println("Usage: [<NER>] cut ");
    }
    if (!currentLine.isEmpty()) {
      parseLengthTwoCutCommand(currentLine);
    }
  }

  /**
   * Parses length two Cut command.
   *
   * @param line the command line to parse
   */
  public static void parseLengthTwoCutCommand(String line) {

    Repertoire dr = new Repertoire(System.getProperty("user.dir"));
    Entry selectedFile = dr.getEntryFromNer(App.ner);
    // TO DO : COPY FILE OR DIRECTORY

    if (selectedFile != null) {
      int tempner = selectedFile.getNer();
      new MinusCommand().minus(tempner, "");
      dr.delete(selectedFile);
      App.ner = -1;
    } else {
      System.out.println("");
    }

  }
}
