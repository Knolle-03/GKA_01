package de.hawh.ld.GKA01.IO;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileWriter {

    public static void writeToFile(List<String> graphInfo, String fileName, String fileType){


        Path file = Paths.get(fileName + fileType);

        try {
            Files.write(file, graphInfo, Charset.forName("windows-1252"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    };

}
