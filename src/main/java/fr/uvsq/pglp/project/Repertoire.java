package fr.uvsq.pglp.project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/** Directory that uses Entry. */
public class Repertoire extends BaseEntry {

  private List<Entry> entries = new ArrayList<>();
  private int currentNer; // Added field to store the current NER value
  static final String FILE_SEPARATOR = System.getProperty("file.separator");

  /** Directory constructor. */
  public Repertoire(String path) {
    super(path, 0);
    try {
      this.entries = this.getContent();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** Directory constructor. */
  public Repertoire(String path, int ner) {
    super(path, ner);
    try {
      this.entries = this.getContent();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the content of the directory.
   *
   * @return List of entries
   * @throws IOException If an I/O error occurs
   */
  public List<Entry> getContent() throws IOException {
    List<Entry> entries = new ArrayList<>();
    int ner = 1;
    File currentDir = new File(this.getPath());
    File[] files = currentDir.listFiles();
    for (File file : files) {
      if (file.isDirectory()) {
        entries.add(new Repertoire(file.getAbsolutePath(), ner));
      } else {
        entries.add(new Fichier(file.getAbsolutePath(), ner));
      }
      ner++;
    }
    return entries;
  }

  /**
   * Delete entry.
   *
   */
  public void delete(Entry entry) {
    deleting(entry);
    updateAnnotation();
    updateEntryList();
  }

  /**
   * Remove an entry from the directory.
   *
   * @param entry Entry to remove
   */
  private void deleting(Entry entry) {
    if (entry instanceof Repertoire) {
      for (Entry e : (Repertoire) entry) {
        deleting(e);
      }
    }
    try {
      Files.delete(Path.of(entry.getPath()));
    } catch (IOException e) {
      System.out.println("Error removing entry: " + e.getMessage());
    }
  }

  /** Update the annotation of the directory. */
  private void updateEntryList() {
    try {
      this.entries = this.getContent();
    } catch (IOException e) {
      System.out.println("Error updating entry list: " + e.getMessage());
    }
  }

  @Override
  public int getSize() {
    int size = 0;
    for (Entry entry : entries) {
      size += entry.getSize();
    }
    return size;
  }

  /**
   * Create a new directory.
   *
   * @param directoryName Name of the new directory
   * @return True if the directory was created successfully, false otherwise
   * @throws IOException Gg
   */
  public boolean mkdir(String directoryName) throws IOException {
    String separator = getFileSeparator();
    String newDirectoryPath = getPath() + separator + directoryName;
    File newDirectory = new File(newDirectoryPath);
    boolean created = newDirectory.mkdir();
    if (created) {
      updateEntryList();
    }
    return created;
  }

  /**
   * Get the file separator for the current operating system.
   *
   * @return The file separator
   */
  public static String getFileSeparator() {
    return System.getProperty("file.separator");
  }

  /**
   * Set the current NER value.
   *
   * @param ner The NER value to set
   */
  public void setcurrentNer(int ner) {
    this.currentNer = ner;
  }

  /**
   * Get the current NER value.
   *
   * @return The current NER value
   */
  public int getcurrentNer() {
    return this.currentNer;
  }

  /**
   * get the entry from its NER.
   *
   * @param ner NER of the entry
   * @return Entry
   */
  public Entry getEntryFromNer(int ner) {
    for (Entry entry : entries) {
      if (entry.getNer() == ner) {
        return entry;
      }
    }
    return null;
  }

  @Override
  public Iterator<Entry> iterator() {
    return new DirectoryIterator(entries); // Assuming entries is a list of child entries
  }

  // Inner class implementing Iterator
  private static class DirectoryIterator implements Iterator<Entry> {

    private List<Entry> entries;
    private int current = 0;

    public DirectoryIterator(List<Entry> entries) {
      this.entries = entries;
    }

    @Override
    public boolean hasNext() {
      return current < entries.size();
    }

    @Override
    public Entry next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return entries.get(current++);
    }
  }
}
