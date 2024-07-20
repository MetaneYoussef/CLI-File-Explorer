package fr.uvsq.pglp.project.old;

import java.io.File;

/**
 * Class that contains the file and its path.
 */
public class FileDetails {

  private File file;
  private String path;

  public FileDetails(File file, String path) {
    this.file = file;
    this.path = path;
  }

  public File getFile() {
    return file;
  }

  public String getPath() {
    return path;
  }

}
