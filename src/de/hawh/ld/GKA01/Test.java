package de.hawh.ld.GKA01;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;


public class Test {

    private Graph graph;
    private SpriteManager sprites;

    public Test() {
        this(new SingleGraph("defaultGraph"));
    }

    public Test(Graph graph) {
        this.graph = graph;
        this.sprites = new SpriteManager(graph);
    }

    public boolean populateGraphFromFile(String fileName) throws IOException {


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

    public boolean writeGraphToFile(String writeFile) throws IOException {

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

    public boolean breadthFirstSearch (Node source, Node target) {
        attachSpriteToNodeInBFS(source, 0);
        int step = 1;
        List<Node> adjacentReachableNodes = getAdjacentReachableMarkedNodes(source, step,   )

    }

    private boolean breadthFirstSearch(Node source, Node target, int step) {

    }



    private void attachSpriteToNodeInBFS(Node node, int step) {
        String spriteID = "Step counter for: " + node.getId() + " count: " + step;
        Sprite sprite = sprites.addSprite(spriteID);
        sprite.attachToNode(node.getId());
        sprite.setPosition(StyleConstants.Units.PX, 30, 180, 0);
        sprite.setAttribute("ui.label", step);
    }

    private List<Node> getAdjacentReachableMarkedNodes(Node source , int step) {

        Iterable<? extends Edge> iterable = source.getEachLeavingEdge();

        List<Node> adjacentReachableMarkedNodes = new ArrayList<>();
        for (Edge edge : iterable) {
            Node node = edge.getOpposite(source);
            if (node.getAttribute("ui.label") != "marked"){
                System.out.println(node);
                node.setAttribute("ui.class", "marked");
                attachSpriteToNodeInBFS(node, step);
                node.setAttribute("step", step);
                adjacentReachableMarkedNodes.add(node);
            }

        }


        return adjacentReachableMarkedNodes;
    }

    protected static void sleep() {
        try { Thread.sleep(1000); } catch (Exception ignored) {}
    }








}
