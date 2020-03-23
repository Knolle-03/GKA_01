package de.hawh.ld.GKA01;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.IOException;

import static de.hawh.ld.GKA01.Test.*;

public class Main {



    public static void main(String[] args) throws IOException {
        String fileName;

        String styleSheet =
                "node {" +
                        "	fill-color: black;" +
                        "}" +
                        "node.marked {" +
                        "	fill-color: red;" +
                        "}";

        for (int i = 3; i <= 3; i++) {
            String number = String.format("%02d", i);
            fileName = "resources/graph" + number + ".graph";
            //System.out.println(fileName);


            Graph graph = new MultiGraph(fileName);
            graph.setAttribute("ui.stylesheet", styleSheet);
            graph.setStrict(false);
            graph.setAutoCreate(true);
            if (populateGraphFromFile(graph, fileName)) {

                for (Node node : graph.getEachNode()) {
                    node.setAttribute("ui.label", node.getId());
                    System.out.println("Index: " + node.getIndex() + "\t" + "Identifier:" + node.getId());
                }







                //writeGraphToFile(graph, "writtenGraph" + number);
                graph.display();
                for (int j = 0; j < 5 ; j++) {
                    sleep();
                }
                breadthFirstSearch(graph.getNode("Kiel"), graph.getNode("Hamburg"));
            } else {
                System.out.println("population went south");
            }

        }
    }

}
