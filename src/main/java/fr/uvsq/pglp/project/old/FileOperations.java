package fr.uvsq.pglp.project.old;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Class to execute the copy, paste and cut commands.
 */
public class FileOperations {

  /**
   * Class to execute the copy command.
   *
   * @param ner is the number associated to the current document.
   */
  public static FileDetails copy(int ner) {
    // Get the current directory
    String currentDirectory = System.getProperty("user.dir");

    // Getting the directory and its files
    File currentDir = new File(currentDirectory);
    File[] fileList = currentDir.listFiles();
    if (fileList != null && ner >= 0 && ner <= fileList.length) {
      File selectedFile = (ner == 0) ? fileList[fileList.length - 1] : fileList[ner - 1];
      return new FileDetails(selectedFile, selectedFile.getAbsolutePath());
    } else {
      System.out.println("NER out of range or void directory.");
      return null;
    }
  }

  /**
   * Paste the selected file or directory.
   *
   * @param selectedFile is the file or directory to be pasted.
   * @param status       The status of the paste operation.
   * @return The absolute path of the pasted file.
   */
  public static String paste(File selectedFile, String status) {
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

  /**
   * Class to execute the delete of a file or directory.
   *
   * @param file is the file or directory to be deleted
   */

  public static boolean delete(File file) {

    if (!file.exists()) {
      System.out.println("The file or directory does not exist.");
      return false;
    }

    if (file.isDirectory()) {
      // Delete a directory
      return deleteDirectory(file);
    } else {
      // Delete a file
      return file.delete();
    }

  }

  /**
   * Class to execute the delete of a directory.
   *
   * @param directory is the directory to be deleted recursively
   */
  public static boolean deleteDirectory(File directory) {
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        // Delete all files contained in the directory
        if (file.isDirectory()) {
          deleteDirectory(file);
        } else {
          file.delete();
        }
      }
    }
    // Delete just the directory when it is void
    return directory.delete();
  }

  /**
   * Class to execute the copy file or directory recursively.
   *
   * @param source      is the directory that will be copied
   * @param destination is the path of the destination directory
   */
  public static void copyDirectory(Path source, Path destination) throws IOException {
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

  /**
   * Class to execute the paste file.
   *
   * @param fileContent is the file content stocked in memory
   */
  public static void pasteFile(byte[] fileContent, String fileName) throws IOException {
    // Get the current directory
    String currDirectory = System.getProperty("user.dir");

    Path newFilePath = Paths.get(currDirectory, fileName);
    Files.write(newFilePath, fileContent);
  }

  /**
   * Class to execute the paste directory.
   *
   * @param directoryContent is the directory content stocked in memory
   */
  public static void pasteDirectory(List<String> directoryContent, String destinationDirectory,
      String originalPath) throws IOException {
    Path destinationPath = Paths.get(destinationDirectory);
    Path originalPath1 = Paths.get(originalPath);
    String fileName = originalPath1.getFileName().toString();

    if (!Files.exists(Paths.get(destinationDirectory, fileName))) {
      Files.createDirectories(Paths.get(destinationDirectory, fileName));
    } else {
      Files.createDirectories(Paths.get(destinationDirectory, fileName + "-copy"));
    }

    for (String content : directoryContent) {
      Path sourcePath = Paths.get(content);
      Path targetPath = destinationPath.resolve(sourcePath.getFileName());
      String relativePath = content.replace(originalPath, "");
      Path target = Paths.get(destinationDirectory, fileName, relativePath);

      if (!relativePath.equals("")) {
        try {
          createDirectoriesRecursively(target.getParent().toString());
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          String fileName2 = targetPath.getFileName().toString();
          if (fileName2.matches(".*\\.[^.]+$")) {
            byte[] fileContent = content.getBytes();
            Files.write(target, fileContent);
          } else if (!fileName2.matches(".*\\.[^.]+$")) {
            if (!Files.exists(target)) {
              Files.createDirectories(target);
            }
          }
        }
      }
    }
  }

  /**
   * Class to create directories recursively.
   *
   * @param directoryPath is the method that creates all parent directories for a
   *                      path.
   */
  public static void createDirectoriesRecursively(String directoryPath) {
    Path path = Paths.get(directoryPath);

    // Verify if the parent directory exists
    Path parent = path.getParent();
    if (parent != null && !Files.exists(parent)) {
      // If it does not exist, we call the function recursively
      createDirectoriesRecursively(parent.toString());
    }

    // Creates the directory if it does not exist
    if (!Files.exists(path)) {
      try {
        Files.createDirectory(path);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Class to evaluate if a string is a number.
   *
   * @param str is the string to be checked if is a number.
   */
  public static boolean isNumber(String str) {
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
