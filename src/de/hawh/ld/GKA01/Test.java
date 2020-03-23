package de.hawh.ld.GKA01;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.*;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;


public class Test {



    public static boolean populateGraphFromFile(Graph graph, String fileName) throws IOException {


        Scanner scanner = new Scanner(new File(fileName), Charset.forName("windows-1252"));
        int counter = 0;
        scanner.useDelimiter("\n");
        boolean isDirected = false;
        while (scanner.hasNextLine()) {



            String currentLine = scanner.nextLine();
            if (currentLine.length() == 0) continue;
            if (currentLine.contains("directed")) {
                isDirected = true;
                continue;
            }
            counter++;
            currentLine = currentLine.replaceAll("[ ;)]", "");
            currentLine = currentLine.replaceFirst("[:]", "");

            //System.out.println(currentLine);
            String [] parts = currentLine.split("[,:(]");
            //System.out.println(Arrays.toString(parts));

            if (parts.length == 1) {
                if (parts[0].contains("#")) {
                    continue;
                } else {
                    graph.addNode(parts[0]);
                }
            }

            if (parts.length == 2) {
                graph.addEdge(String.valueOf(counter), parts[0], parts[1], isDirected);
            }

            if (parts.length == 3) {
                graph.addEdge(String.valueOf(counter), parts[0], parts[1], isDirected).setAttribute("ui.label", parts[2]);
            }
        }
        return true;
    }

    public static boolean writeGraphToFile(Graph graph, String writeFile) throws IOException {

        List<List<String>> dataSets = new ArrayList<>();


        for (Edge edge : graph.getEachEdge()) {
            List<String> singleSet = new ArrayList<>();
            singleSet.add(String.valueOf(edge.getNode0()));
            singleSet.add(String.valueOf(edge.getNode1()));
            if (edge.getAttributeCount() > 0){
                singleSet.add(edge.getAttribute("ui.label"));
            }
            dataSets.add(singleSet);
        }

        //System.out.println(dataSets);

        String path = "resources/";
        String extension = ".graph";
        String file = path + writeFile + extension;
        String toBeWritten = "";
        FileOutputStream out = new FileOutputStream(file);

        if (graph.getEdge(1).isDirected()) out.write("# directed;\n".getBytes());

        for (List<String> dataSet : dataSets) {

            if (dataSet.size() == 2) {
                toBeWritten = dataSet.get(0) + "," + dataSet.get(1) + ";\n";
            }

            if (dataSet.size() == 3) {
                toBeWritten = dataSet.get(0) + "," + dataSet.get(1) + " :: " + dataSet.get(2) + ";\n";
            }

            out.write(toBeWritten.getBytes());
        }

        out.close();

        return true;
    }

    public static void breadthFirstSearch (Node source, Node target) {
        Iterator<? extends Node> k = source.getBreadthFirstIterator();
        int i = 0;
        System.out.println(source.getId());

        while (k.hasNext()) {
            Node next = k.next();
            next.setAttribute("ui.class", "marked");
            next.setAttribute("step", i + 1);
            sleep();
            if (next.equals(target)) break;
            i++;
        }
    }

    protected static void sleep() {
        try { Thread.sleep(1000); } catch (Exception ignored) {}
    }








}
