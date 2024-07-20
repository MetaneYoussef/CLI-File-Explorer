package fr.uvsq.pglp.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.text.DecimalFormat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * This class contains unit tests for the VisuCommand class.
 */
public class VisuCommandTest {

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
        Files.createDirectory(tempDir.resolve("subdirectory"));
        Files.createFile(tempDir.resolve("subdirectory").resolve("file4.txt"));
        Files.createFile(tempDir.resolve("subdirectory").resolve("subdirectory2"));
    }

    @AfterEach
    public void tearDown() {
        // Restore the original directory after each test
        System.setProperty("user.dir", initialDirectory);
    }

    @Test
    public void testValidFileRead() {
        // Set a specific directory for test
        System.setProperty("user.dir", tempDir.toString());
        int file1ner = 0;
        try {
            Repertoire dr = new Repertoire(System.getProperty("user.dir"));
            List<Entry> entries = dr.getContent();
            for (Entry entry : entries) {
                if (entry.getName().equals("file1.txt")) {
                    file1ner = entry.getNer();
                }
            }
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            new Parser().parse(file1ner + " visu");

            String expectedOutput = "Hello There i am a test file";
            assertEquals(expectedOutput, outContent.toString().trim());
            // Reset System.out
            System.setOut(System.out);
        } catch (Exception e) {
            fail("Error Creating File: " + e.getMessage());
        }

    }

    @Test
    public void testValidDirectoryCheck() {
        // Set a specific directory for test
        System.setProperty("user.dir", tempDir.toString());
        int subdirectoryner = 0;
        try {
            Repertoire dr = new Repertoire(System.getProperty("user.dir"));
            List<Entry> entries = dr.getContent();
            for (Entry entry : entries) {
                if (entry.getName().equals("subdirectory")) {
                    subdirectoryner = entry.getNer();
                }
            }
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            new Parser().parse(subdirectoryner + " visu");

            String expectedOutput = "Error : Selected NER is a directory";
            assertEquals(expectedOutput, outContent.toString().trim());
            // Reset System.out
            System.setOut(System.out);
        } catch (Exception e) {
            fail("Error Creating File: " + e.getMessage());
        }
    }

    @Test
    public void testValidExecutableCheck() {
        // Set a specific directory for test
        System.setProperty("user.dir", tempDir.toString());
        int file4ner = 0;
        final DecimalFormat dFormat = new DecimalFormat("0.00");

        try {
            Repertoire dr = new Repertoire(System.getProperty("user.dir"));
            List<Entry> entries = dr.getContent();
            for (Entry entry : entries) {
                if (entry.getName().equals("file4.exe")) {
                    file4ner = entry.getNer();
                }
            }
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            new Parser().parse(file4ner + " visu");

            String expectedOutput = "file4.exe is not a text file" + "\n" + "the size of file4.exe is : "
                    + dFormat.format(dr.getEntryFromNer(file4ner).getSize()) + " Bytes";

            assertFalse(expectedOutput.trim().equals(outContent.toString().trim()));
            // Reset System.out
            System.setOut(System.out);
        } catch (Exception e) {
            fail("Error Creating File: " + e.getMessage());
        }
    }

}
