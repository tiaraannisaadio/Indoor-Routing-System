/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Scanner;
import org.graphstream.algorithm.AStar;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

/**
 *
 * @author IFLABASPRAK2
 */
public class AstarTesting {

    static private Graph graph = new MultiGraph("astar");

    public static String astarAlgo(String source, String dest) {
        System.out.println("*****" + "A* algorithm node " + source + " to "
                + dest + "*****");
        String result = "Shortest Path\t: \n";
        Double distance = 0.0;
        System.out.println("graph.getNodeCount() = " + graph.getNodeCount());
        AStar astar = new AStar(graph);
        astar.setCosts(new AStar.DistanceCosts());
        astar.compute(source, dest);

        for (Node n : astar.getShortestPath().getEachNode()) {
            result += n.getId() + "\n";
//            n.addAttribute("ui.style", "fill-color: black;");
        }
        for (Edge e : astar.getShortestPath().getEachEdge()) {
            distance += (Double) e.getAttribute("length");
//            e.addAttribute("ui.style", "fill-color: orange; size: 5px;");
        }
        result += "\n\nDistance\t: \n" + distance + " meters";
//        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of vertices");
        int numberofvertices = scanner.nextInt();

        Double[][] adjacencymatrix = new Double[numberofvertices + 1][numberofvertices + 1];
        System.out.println("Enter the adjacency matrix");
        for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
            for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
                adjacencymatrix[sourcenode][destinationnode] = Double.parseDouble(scanner.next());
            }
        }
        int idEdge = 0;
        for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
            for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
                if (graph.getNode(sourcenode + "") == null) {
                    graph.addNode(sourcenode + "");
                } else if (graph.getNode(destinationnode + "") == null) {
                    graph.addNode(destinationnode + "");
                }
                graph.addEdge("e" + idEdge++, sourcenode + "", destinationnode + "").addAttribute("length", adjacencymatrix[sourcenode][destinationnode]);
            }
        }

        System.out.println("Enter the source vertex");
        int source = scanner.nextInt();
        int dest = scanner.nextInt();

        System.out.println("");
        System.out.println(astarAlgo(source + "", dest + ""));
    }
}
