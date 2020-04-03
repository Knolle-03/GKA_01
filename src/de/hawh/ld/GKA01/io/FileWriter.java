package de.hawh.ld.GKA01.io;

import java.io.IOException;
import java.util.List;

public class FileWriter {

    public static void writeLines(List<String> lines, String fileName){

        try {
            java.io.FileWriter writer = new java.io.FileWriter(fileName);
            for (String line : lines) {
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    };

}
