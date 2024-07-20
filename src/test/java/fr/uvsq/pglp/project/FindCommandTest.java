package fr.uvsq.pglp.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FindCommandTest {

    /**
     * Set up the previous conditions for each test.
     *
     * @throws IOException if an I/O error occurs.
     */
    @BeforeEach
    public void setUp() throws IOException {
        String currentDir = System.getProperty("user.dir");
        Files.createFile(new File(currentDir + File.separator + "file1.txt").toPath());
        Files.createFile(new File(currentDir + File.separator + "file2.txt").toPath());
        Files.createFile(new File(currentDir + File.separator + "file3.txt").toPath());
        Files.createFile(new File(currentDir + File.separator + "file4.exe").toPath());

        Files.createDirectory(new File(currentDir + File.separator + "testDirectory").toPath());
        Files.createFile(new File(currentDir + File.separator + "testDirectory"
                + File.separator + "file1_1.txt").toPath());

        File file1 = new File(currentDir + File.separator + "file1.txt");
        PrintWriter writer = new PrintWriter(file1);
        writer.println("Hello There i am a test file");
        writer.close();
    }

    /**
     * Cleans up the test environment after each test.
     */
    @AfterEach
    public void cleanup() {
        try {
            String currentDir = System.getProperty("user.dir");
            File file1 = new File(currentDir + File.separator + "file1.txt");
            file1.delete();
            File file2 = new File(currentDir + File.separator + "file2.txt");
            file2.delete();
            File file3 = new File(currentDir + File.separator + "file3.txt");
            file3.delete();
            File file4 = new File(currentDir + File.separator + "file4.exe");
            file4.delete();
            File notes = new File(currentDir + File.separator + "notes.txt");
            notes.delete();
            File subfile = new File(currentDir + File.separator + "testDirectory"
                    + File.separator + "file1_1.txt");
            subfile.delete();
            File testdir = new File(currentDir + File.separator + "testDirectory");
            testdir.delete();

        } catch (Exception e) {
            fail("Error Cleaning: " + e.getMessage());
        }
    }

    @Test
    public void testFindCommand() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String file1path = System.getProperty("user.dir") + File.separator + "[33;1mfile1.txt[m";
        new Parser().parse("find file1.txt");

        assertEquals(file1path, outContent.toString().trim());

    }

    @Test
    public void testValidFileNameSearchInSubdirectories() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String file1path = System.getProperty("user.dir") + File.separator + "testDirectory" + File.separator
                + "[33;1mfile1_1.txt[m";
        new Parser().parse("find file1_1.txt");

        assertEquals(file1path, outContent.toString().trim());
    }

    @Test
    public void testInvalidFileNameSearch() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        new Parser().parse("find file5.txt");

        assertEquals("", outContent.toString().trim());
    }
}
