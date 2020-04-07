package de.hawh.ld.GKA01.io;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    /**
     * Creates a graph from a list
     *
     * @param path
     *            Path of the file to be read
     *
     * @return List of all Strings from the file:
     *
     */

    public static List<String> readLines(String path) {

        List<String> lines = new ArrayList<>();

        File file = new File(path);
        Scanner scanner = null;

        try {
            scanner = new Scanner(file, Charset.forName("windows-1252"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (scanner != null) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }

            scanner.close();
        }

        return lines;
    }



}
