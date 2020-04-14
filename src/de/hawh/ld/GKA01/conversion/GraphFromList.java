package de.hawh.ld.GKA01.conversion;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphFromList {

    //["#directed;"]
    //node1,[":"attr1],[","node2,[" :"attr2],[" ("edge")"],[" :: "weight]];
    // missing semicolons, p etc. result in an invalid row
    // and therefore not show up in the graph


    private static final String DIRECTED = "#directed;";
    private static final String ID = "[A-Za-z0-9\u00C0-\u00FF]+";
    private static final String ATTRIBUTE = "(:[0-9]+)";
    private static final String EDGE = "( \\([A-Za-z0-9\u00C0-\u00FF]+\\))";
    private static final String WEIGHT = "( :: [0-9]+)";
    private static final String DIGITS = "[0-9]+";
    private static final String FIRST_NODE = ID + ATTRIBUTE + "?";
    private static final String SECOND_NODE_AND_INFO = "(," + ID + ATTRIBUTE + "?" + EDGE + "?" + WEIGHT + "?" + ")";

    //patterns to match the components of a given string
    private static final Pattern DIRECTED_PATTERN = Pattern.compile(DIRECTED);
    private static final Pattern ID_PATTERN = Pattern.compile(ID);
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile(ATTRIBUTE);
    private static final Pattern EDGE_PATTERN = Pattern.compile(EDGE);
    private static final Pattern WEIGHT_PATTERN = Pattern.compile(WEIGHT);
    private static final Pattern DIGITS_PATTERN = Pattern.compile(DIGITS);
    private static final Pattern ONE_NODE_PATTERN = Pattern.compile(FIRST_NODE + ";");
    private static final Pattern TWO_NODES_PATTERN = Pattern.compile(FIRST_NODE + SECOND_NODE_AND_INFO + ";");

    /**
     * Creates a graph from a list
     *
     *
     * @param lines
     *            Lines read by the FileReader in the io package.
     * @param fileName
     *            Name of read file. Used as ID of the graph.
     *
     * @return A populated Graph from all valid lines
     */
    public static Graph populateGraph(List<String> lines, String fileName) {
        // graph to be populated
        Graph graph = new MultiGraph(fileName, false, true);

        List<String> nonValidLines = new ArrayList<>();

        boolean isDirected = isGraphDirected(lines.get(0));



        for (String line : lines) {


            if (ONE_NODE_PATTERN.matcher(line).matches()) {

                String node1 = patternFromString(ID_PATTERN, line);
                String attr1 = patternFromString(ATTRIBUTE_PATTERN, line);

                //valid row have at least one node
                graph.addNode(node1);
                if (attr1 != null) graph.getNode(node1).setAttribute("attr1", attr1);


            } else if (TWO_NODES_PATTERN.matcher(line).matches()) {

                String leftSide;
                String rightSide = "";

                if (line.contains(",")) {
                    leftSide = line.substring(0, line.indexOf(","));
                    rightSide = line.substring(line.indexOf(","));
                } else {
                    leftSide = line;
                }

                String node1 = patternFromString(ID_PATTERN, leftSide);
                String attr1 = patternFromString(ATTRIBUTE_PATTERN, leftSide);
                String node2 = patternFromString(ID_PATTERN, rightSide);
                String attr2 = patternFromString(ATTRIBUTE_PATTERN, rightSide);
                String edge = patternFromString(EDGE_PATTERN, rightSide) == null ? null : patternFromString(ID_PATTERN, patternFromString(EDGE_PATTERN, rightSide));
                String weight = patternFromString(WEIGHT_PATTERN, rightSide) == null ? null : patternFromString(DIGITS_PATTERN, patternFromString(WEIGHT_PATTERN, rightSide));


                Edge currentEdge = graph.addEdge(node1 + "-" + node2, node1, node2, isDirected);
                if (attr1 != null) graph.getNode(node1).setAttribute("attr1", attr1);
                if (attr2 != null) graph.getNode(node2).addAttribute("attr2", attr2);


                // add edge name if given
                if (edge != null) {
                    currentEdge.addAttribute("name", edge);
                    currentEdge.addAttribute("ui.label", edge);

                    // add weight to ui.label
                    if (weight != null) {
                        currentEdge.addAttribute("weight", Double.parseDouble(weight));
                        currentEdge.setAttribute("ui.label", edge + " :: " + weight);
                    }
                    //add weight as the only ui.label if no name is given
                } else if (weight != null) {
                    currentEdge.addAttribute("weight", Double.parseDouble(weight));
                    currentEdge.setAttribute("ui.label", weight);
                }
            } else if (!line.equals("#directed;")) {
                nonValidLines.add(line);
            }
        // add ID as ui.label to every Node for better visualization
        for (Node node : graph.getEachNode()) {
            node.addAttribute("ui.label", node.getId());
        }



        }

        //print non valid lines for debugging
        if (nonValidLines.size() > 0) {
            System.out.println("non valid lines from: " + fileName);
            for (String nonValidLine : nonValidLines) {
                System.out.println(nonValidLine);
            }
            System.out.println("===================");
        }


        return graph;
    }

    /**
     * Test if given graph is directed
     *
     * @param firstLine
     *            Either matches the DIRECTED_PATTERN in which case the graph is directed, or not.
     */
    private static boolean isGraphDirected (String firstLine) {
       return DIRECTED_PATTERN.matcher(firstLine).matches();
    }
    /**
     * match different patterns against a given string.
     *
     * @param pattern
     *            Pattern to be matched by
     * @param string
     *             this String
     */
    private static String patternFromString(Pattern pattern, String string) {

        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return matcher.group(0);
        }

        return null;
    }

}
