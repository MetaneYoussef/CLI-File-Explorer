
package fr.uvsq.pglp.project.old;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that executes the read file or directory functions for cut command.
 */
public class ContentToMemory {

  public static byte[] readFileToMemory(Path filePath) throws IOException {
    return Files.readAllBytes(filePath);
  }

  /**
   * Class to execute the read directory function.
   */
  public static List<String> readDirectoryToMemory(Path directoryPath) throws IOException {
    List<String> filesInDirectory = new ArrayList<>();
    Files.walk(directoryPath)
        // .filter(Files::isRegularFile)
        .forEach(file -> filesInDirectory.add(file.toString()));
    return filesInDirectory;
  }

}
