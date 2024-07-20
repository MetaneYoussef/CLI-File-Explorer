package fr.uvsq.pglp.project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Fichier extends BaseEntry {

  private byte[] content;

  public Fichier(String path) throws IOException {
    super(path, 0);
    this.content = Files.readAllBytes(Path.of(path));
  }

  public Fichier(String path, int ner) throws IOException {
    super(path, ner);
    this.content = Files.readAllBytes(Path.of(path));
  }

  @Override
  public int getSize() {
    return content.length;
  }

  public List<String> read() throws IOException {
    return Files.readAllLines(Path.of(getPath()));
  }

  public void write(List<String> lines) throws IOException {
    Files.write(Path.of(getPath()), lines);
  }

  public byte[] getContent() {
    return content;
  }
  
  public Fichier[] listFiles() {
    List<Fichier> files = new ArrayList<>();
    File currentDir = new File(getPath());
    if (currentDir.isDirectory()) {
      File[] fileList = currentDir.listFiles();
      if (fileList != null) {
        for (File file : fileList) {
          if (file.isFile()) {
            try {
              files.add(new Fichier(file.getAbsolutePath(), getNer()));
            } catch (IllegalArgumentException | IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
    return files.toArray(new Fichier[0]);
  }

  @Override
  public Iterator<Entry> iterator() {
    throw new UnsupportedOperationException("Unimplemented method 'iterator'");
  }
}
