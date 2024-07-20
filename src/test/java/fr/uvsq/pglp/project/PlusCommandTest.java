package fr.uvsq.pglp.project;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class PlusCommandTest {
  @TempDir
  // We create a temporary directory that exists for the duration of a test
  Path tempDir;

  // Save the current directory
  private String initialDirectory;

  @BeforeEach
  public void setUp() throws IOException {
    Parser.file = null;
    // Save the current directory
    initialDirectory = System.getProperty("user.dir");

    // We create the previous conditions for each test
    // in this case 3 files (file1.txt, file2.txt and file3.txt)
    // and 1 directory (subdirectory) that contains 1 file (file4.txt) and a
    // directory (subdirectory2)
    Files.createFile(tempDir.resolve("file1.txt"));
    File file1 = new File(tempDir + File.separator + "file1.txt");
    PrintWriter writer = new PrintWriter(file1);
    writer.println("Hello There i am a test file");
    writer.close();
    Files.createFile(tempDir.resolve("file2.txt"));
    Files.createFile(tempDir.resolve("file3.txt"));
    Files.createFile(tempDir.resolve("file4.exe"));
    Files.createDirectory(tempDir.resolve("testDirectory"));
    Files.createFile(tempDir.resolve("testDirectory").resolve("file4.txt"));
    Files.createFile(tempDir.resolve("testDirectory").resolve("subdirectory2"));
  }

  @AfterEach
  public void tearDown() {
    // Restore the original directory after each test
    System.setProperty("user.dir", initialDirectory);
  }

  @Test
  public void testPlusNormalAddition() {
    // Set a specific directory for test
    System.setProperty("user.dir", tempDir.toString());
    try {
      Repertoire dr = new Repertoire(System.getProperty("user.dir"));
      List<Entry> entries = dr.getContent();

      int file1ner = -1;
      for (Entry entry : entries) {
        if (entry.getName().equals("file1.txt")) {
          file1ner = entry.getNer();
        }
      }
      new Parser().parse(file1ner + " + " + "\"Test note\"");
      try {
        List<String> lines =
            Files.readAllLines(
                Paths.get(System.getProperty("user.dir") + File.separator + "notes.txt"));
        assertTrue(lines.contains(file1ner + "\tfile1.txt\tTest note"));
      } catch (IOException e) {
        fail("Error reading from notes.txt: " + e.getMessage());
      }

    } catch (IOException e) {
      fail("Error: " + e.getMessage());
    }
  }

  @Test
  public void testAddingNewNoteToExistingNotes() {
    // Set a specific directory for test
    System.setProperty("user.dir", tempDir.toString());
    try {
      Repertoire dr = new Repertoire(System.getProperty("user.dir"));
      List<Entry> entries = dr.getContent();

      int file1ner = -1;
      int file2ner = -1;
      int file3ner = -1;
      for (Entry entry : entries) {
        if (entry.getName().equals("file1.txt")) {
          file1ner = entry.getNer();
        } else if (entry.getName().equals("file2.txt")) {
          file2ner = entry.getNer();
        } else if (entry.getName().equals("file3.txt")) {
          file3ner = entry.getNer();
        }
      }
      new Parser().parse(file1ner + " + " + "\"First note\"");
      new Parser().parse(file2ner + " + " + "\"Second note\"");
      new Parser().parse(file3ner + " + " + "\"Third note\"");

      new Parser().parse(file1ner + " + " + "\"New note\"");

      try {
        List<String> lines =
            Files.readAllLines(
                Paths.get(System.getProperty("user.dir") + File.separator + "notes.txt"));
        assertTrue(lines.contains(file1ner + "\tfile1.txt\tFirst note"));
        assertTrue(lines.contains(file2ner + "\tfile2.txt\tSecond note"));
        assertTrue(lines.contains(file3ner + "\tfile3.txt\tThird note"));
        assertTrue(lines.contains(file1ner + "\tfile1.txt\tNew note"));
      } catch (IOException e) {
        fail("Error reading from notes.txt: " + e.getMessage());
      }

    } catch (IOException e) {
      fail("Error: " + e.getMessage());
    }
  }
}
