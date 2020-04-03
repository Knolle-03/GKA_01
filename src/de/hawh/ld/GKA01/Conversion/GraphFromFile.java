package de.hawh.ld.GKA01.Conversion;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphFromFile {

    private static final String DIRECTED = "#directed;";
    private static final String IDENTIFIER = "[A-za-z0-9ÄäÖöÜü]+";
    private static final String ATTRIBUTE_ONE = "(:[0-9]+)";
    private static final String EDGE = "( \\([A-Za-z0-9ÄäÖöÜü]+\\))";
    private static final String WEIGHT = "( :: [0-9]+)";
    private static final String DIGITS = "[0-9]+";
    private static final String FIRST_NODE = IDENTIFIER + ATTRIBUTE_ONE + "?";
    private static final String SECOND_NODE_AND_INFO = "(," + IDENTIFIER + ATTRIBUTE_ONE + "?" + EDGE + "?" + WEIGHT + "?" + ")?";
    //private final String OPTIONAL_INFO =

    private static final Pattern ORIENTATION_PATTERN = Pattern.compile(DIRECTED);
    private static final Pattern VALID_ROW_PATTERN = Pattern.compile(FIRST_NODE + SECOND_NODE_AND_INFO + ";");

    private static final Pattern ID_PATTERN = Pattern.compile(IDENTIFIER);
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("(?!" + IDENTIFIER + ")" + ATTRIBUTE_ONE);
    private static final Pattern EDGE_PATTERN = Pattern.compile("(?!" + IDENTIFIER + ")" + "(?!" + ATTRIBUTE_ONE + ")" + EDGE);
    private static final Pattern WEIGHT_PATTERN = Pattern.compile("(?!" + IDENTIFIER + ")" + "(?!" + ATTRIBUTE_ONE + ")" + "(?!" + EDGE + ")" + WEIGHT);
    private static final Pattern DIGITS_PATTERN = Pattern.compile(DIGITS);



    public static Graph populateGraph(List<String> lines) {

        MultiGraph multiGraph = new MultiGraph("multiGraph", false, true);


        boolean isDirected = isGraphDirected(lines.get(0));


        for (String line : lines) {

            if (VALID_ROW_PATTERN.matcher(line).matches()) {

                String part1;
                String part2 = "";

                if (line.contains(",")) {
                    part1 = line.substring(0, line.indexOf(","));
                    part2 = line.substring(line.indexOf(","));
                } else {
                    part1 = line;
                }

                String node1 = patternFromString(ID_PATTERN, part1);
                String attr1 = patternFromString(ATTRIBUTE_PATTERN, part1);
                String node2 = patternFromString(ID_PATTERN, part2);
                String attr2 = patternFromString(ATTRIBUTE_PATTERN, part2);
                String edge =  patternFromString(EDGE_PATTERN, part2) == null ?
                        null : patternFromString(ID_PATTERN, patternFromString(EDGE_PATTERN, part2));
                String weight =  patternFromString(WEIGHT_PATTERN, part2) == null ?
                        null : patternFromString(DIGITS_PATTERN, patternFromString(WEIGHT_PATTERN, part2));


                //valid row have at least one node
                multiGraph.addNode(node1);
                if (attr1 != null) multiGraph.getNode(node1).setAttribute("attr1", attr1);

                // add the second node if there is one
                // add attr2 if given
                if (node2 != null) {
                    multiGraph.addNode(node2);
                    if (attr2 != null) {
                        multiGraph.getNode(node2).addAttribute("attr2", attr2);
                    }

                    //connect nodes (with directed edge if the given graph is directed)
                    Edge currentEdge = multiGraph.addEdge(node1 + node2, node1, node2, isDirected);

                    // add edge name if given
                    if (edge != null) {
                        currentEdge.addAttribute("name", edge);
                        currentEdge.addAttribute("ui.label", edge);

                        // add weight to ui.label
                        if (weight != null) {
                            currentEdge.addAttribute("weight", Integer.parseInt(weight));
                            currentEdge.setAttribute("ui.label", edge + " :: " + weight);
                        }
                        //add weight as the only ui.label if to name is given
                    } else if (weight != null) {
                        currentEdge.addAttribute("weight", Integer.parseInt(weight));
                        currentEdge.setAttribute("ui.label", weight);
                    }
                }

            }
        }

        for (Node node : multiGraph.getEachNode()) {
            node.addAttribute("ui.label", node.getId());
        }

        return multiGraph;
    }


    private static boolean isGraphDirected (String firstLine) {
       return ORIENTATION_PATTERN.matcher(firstLine).matches();
    }

    private static String patternFromString(Pattern pattern, String string) {

        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return matcher.group(0);
        }

        return null;
    }

}
