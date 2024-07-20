package fr.uvsq.pglp.project;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * PasteCommand class for the command list.
 */
public class PasteCommand implements Command {
  @Override
  public void execute(String line) {
    if (line.equalsIgnoreCase("paste")) {
      if (Parser.file != null) {
        String selectedFilePath = paste(Parser.file);
        if (selectedFilePath == null) {
          System.out.println("It was not possible to paste the file or directory");
        }
        //Annotation.updateAnnotation();
      } else {
        System.out.println("There is no file or directory to paste");
      }
    } else {
      System.out.println("Usage: paste ");
    }
  }

  private String paste(File selectedFile) {
    // Checking if the file o directory still exists
    if (!selectedFile.exists()) {
      System.out.println("The file or directory does not exist anymore.");
      return null;
    }

    // Get the current directory
    String currDirectory = System.getProperty("user.dir");

    // Getting the directory to compare
    File currentDirectory = new File(currDirectory);
    File destinationFile = new File(currentDirectory, selectedFile.getName());

    if (destinationFile.exists()) {
      // If the directory or file already exists, we add the sufix "-copy"
      String fileNameWithoutExtension = selectedFile.getName().contains(".")
          ? selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf('.'))
          : selectedFile.getName();
      String fileExtension = selectedFile.getName().contains(".")
          ? selectedFile.getName().substring(selectedFile.getName().lastIndexOf('.'))
          : "";

      String newFileName = fileNameWithoutExtension + "-copy" + fileExtension;
      destinationFile = new File(currentDirectory, newFileName);
    }

    // Trying to paste the directory or file
    try {
      if (selectedFile.isDirectory()) {
        copyDirectory(selectedFile.toPath(), destinationFile.toPath());
      } else {
        Files.copy(selectedFile.toPath(), destinationFile.toPath());
      }
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    return destinationFile.getAbsolutePath();
  }

  private void copyDirectory(Path source, Path destination) throws IOException {
    // If directory does not exist, it is created
    if (!Files.exists(destination)) {
      Files.createDirectories(destination);
    }
    // Recursively traverse the source directory
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
      for (Path entry : stream) {
        Path target = destination.resolve(entry.getFileName());

        if (Files.isDirectory(entry)) {
          // If a directory is detected, call the function copyDirectory recursively
          copyDirectory(entry, target);
        } else {
          // If a file is detected, we copy the file in the destination directory
          Files.copy(entry, target, StandardCopyOption.REPLACE_EXISTING);
        }
      }
    }
  }
}
