package de.hawh.ld.GKA01.io;

import java.io.IOException;
import java.util.List;




public class FileWriter {
    /**
     * Creates a graph from a list
     *
     * @param lines
     *            Lines to write to the file.
     * @param fileName
     *            Name of file the lines are written to.
     *
     */
    public static void writeLines(List<String> lines, String fileName){

        try(java.io.FileWriter writer = new java.io.FileWriter(fileName)) {

            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
