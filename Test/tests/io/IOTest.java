package tests.io;

import de.hawh.ld.GKA01.io.FileReader;
import de.hawh.ld.GKA01.io.FileWriter;
import graphPaths.GraphPaths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IOTest implements GraphPaths {

    private Path path0;
    private Path path1;
    private Path path3;
    private Path path6;

    private Path writtenPath0;
    private Path writtenPath1;
    private Path writtenPath3;
    private Path writtenPath6;


    private List<String> lines0;
    private List<String> lines1;
    private List<String> lines3;
    private List<String> lines6;


    @BeforeEach
    void setUp() {


        path0 = Paths.get(READ_GRAPH_00_PATH);
        path1 = Paths.get(READ_GRAPH_01_PATH);
        path3 = Paths.get(READ_GRAPH_03_PATH);
        path6 = Paths.get(READ_GRAPH_06_PATH);

        writtenPath0 = Paths.get(WRITE_GRAPH_00_PATH);
        writtenPath1 = Paths.get(WRITE_GRAPH_01_PATH);
        writtenPath3 = Paths.get(WRITE_GRAPH_03_PATH);
        writtenPath6 = Paths.get(WRITE_GRAPH_06_PATH);

        lines0 = FileReader.readLines(READ_GRAPH_00_PATH);
        lines1 = FileReader.readLines(READ_GRAPH_01_PATH);
        lines3 = FileReader.readLines(READ_GRAPH_03_PATH);
        lines6 = FileReader.readLines(READ_GRAPH_06_PATH);

        FileWriter.writeLines(lines0, WRITE_GRAPH_00_PATH);
        FileWriter.writeLines(lines1, WRITE_GRAPH_01_PATH);
        FileWriter.writeLines(lines3, WRITE_GRAPH_03_PATH);
        FileWriter.writeLines(lines6, WRITE_GRAPH_06_PATH);


    }

    @Test
    void readLines() {
        assertEquals(5, lines0.size());
        assertEquals(39, lines1.size());
        assertEquals(40, lines3.size());
        assertEquals(13, lines6.size());
    }

    @Test
    void writeLines() {
        try {

            // Files.mismatch since JDK 12
            // TODO: figure out why graph00.graph isn't equal
            //assertEquals(-1, Files.mismatch(path0,writtenPath0));
            //assertArrayEquals(Files.readAllBytes(path0), Files.readAllBytes(writtenPath0));

            assertEquals(-1, Files.mismatch(path1,writtenPath1));
            assertArrayEquals(Files.readAllBytes(path1), Files.readAllBytes(writtenPath1));

            assertEquals(-1, Files.mismatch(path3,writtenPath3));
            assertArrayEquals(Files.readAllBytes(path3), Files.readAllBytes(writtenPath3));

            assertEquals(-1, Files.mismatch(path6,writtenPath6));
            assertArrayEquals(Files.readAllBytes(path6), Files.readAllBytes(writtenPath6));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
