package de.hawh.ld.GKA01.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    /**
     * Get the lines of a File
     *
     * @param path
     *            Path of the file to be read
     *
     * @return List of all Strings from the file:
     *
     */

    public static List<String> readLines(String path) {

        try {
            return Files.readAllLines(Path.of(path), Charset.forName("windows-1252"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }



}
