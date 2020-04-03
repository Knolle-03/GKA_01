package de.hawh.ld.GKA01.IO;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileWriter {

    public static void writeLines(List<String> lines, String fileName){

        try {
            java.io.FileWriter writer = new java.io.FileWriter(fileName);
            for (String line : lines) {
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    };

}
