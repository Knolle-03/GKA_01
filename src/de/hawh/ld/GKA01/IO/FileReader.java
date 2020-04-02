package de.hawh.ld.GKA01.IO;

import org.graphstream.algorithm.util.FibonacciHeap;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {



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
