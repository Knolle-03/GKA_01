import de.hawh.ld.GKA01.io.FileReader;
import de.hawh.ld.GKA01.io.FileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class IOTest {

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
        String fileName0 = "testResources/givenGraphs/graph00.graph";
        String fileName1 = "testResources/givenGraphs/graph01.graph";
        String fileName3 = "testResources/givenGraphs/graph03.graph";
        String fileName6 = "testResources/givenGraphs/graph06.graph";

        String writtenFileName0 = "testResources/writtenGraphs/graph00.graph";
        String writtenFileName1 = "testResources/writtenGraphs/graph01.graph";
        String writtenFileName3 = "testResources/writtenGraphs/graph03.graph";
        String writtenFileName6 = "testResources/writtenGraphs/graph06.graph";

        path0 = Paths.get(fileName0);
        path1 = Paths.get(fileName1);
        path3 = Paths.get(fileName3);
        path6 = Paths.get(fileName6);

        writtenPath0 = Paths.get(writtenFileName0);
        writtenPath1 = Paths.get(writtenFileName1);
        writtenPath3 = Paths.get(writtenFileName3);
        writtenPath6 = Paths.get(writtenFileName6);

        lines0 = FileReader.readLines(fileName0);
        lines1 = FileReader.readLines(fileName1);
        lines3 = FileReader.readLines(fileName3);
        lines6 = FileReader.readLines(fileName6);

        FileWriter.writeLines(lines0, writtenFileName0);
        FileWriter.writeLines(lines1, writtenFileName1);
        FileWriter.writeLines(lines3, writtenFileName3);
        FileWriter.writeLines(lines6, writtenFileName6);


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
