package Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class DijkstraAlgorithmSet {

    private Double distances[];
    private Set<Integer> settled;
    private Set<Integer> unsettled;
    private int number_of_nodes;
    private Double adjacencyMatrix[][];
    static Double maxval = 999.0;
    static ArrayList<Integer> path;
    static ArrayList<Integer> path2;
    private ArrayList<Integer> visited;
    private ArrayList<Double[]> dijkTable;
    private ArrayList<Double> iterDist;

    public DijkstraAlgorithmSet(int number_of_nodes) {
        this.number_of_nodes = number_of_nodes;
        distances = new Double[number_of_nodes + 1];
        settled = new HashSet<Integer>();
        unsettled = new HashSet<Integer>();
        path = new ArrayList<>();
        path2 = new ArrayList<>();
        visited = new ArrayList<>();
        dijkTable = new ArrayList<>();
        iterDist = new ArrayList<>();
        adjacencyMatrix = new Double[number_of_nodes + 1][number_of_nodes + 1];
//        for (int i = 0; i < number_of_nodes + 1; i++) {
//            iterDist.add(maxval);
//        }
        visited.add(null);
    }

    public void dijkstra_algorithm(Double adjacency_matrix[][], int source, int dest) {
        int evaluationNode;
//        int countedge = 1;
//        System.out.println(Integer.MAX_VALUE + " number_of_nodes = " + number_of_nodes);
        for (int i = 1; i <= number_of_nodes; i++) {
            for (int j = 1; j <= number_of_nodes; j++) {
                adjacencyMatrix[i][j] = adjacency_matrix[i][j];
//                if (adjacencyMatrix[i][j] != 0.0) {
//                    System.out.println(countedge++ + " adjacencyMatrix[" + i + "][" + j + "] = " + adjacencyMatrix[i][j]);
//                }
            }
        }

        Double[] newDist = new Double[number_of_nodes + 1];
        for (int i = 1; i <= number_of_nodes; i++) {
            distances[i] = maxval;
            newDist[i] = distances[i];
        }
        dijkTable.add(newDist);

        unsettled.add(source);
        distances[source] = 0.0;
        while (!unsettled.isEmpty()) {
//            System.out.println(count++);
            evaluationNode = getNodeWithMinimumDistanceFromUnsettled();
//            System.out.println("evaluationNode " + evaluationNode);
            unsettled.remove(evaluationNode);
            settled.add(evaluationNode);

//                System.out.println("iterNode.size() : " + iterNode.size());
//                for (int i = 0; i < iterNode.size(); i++) {
//                    if (i > settled.size() - 1) {
//                        iterNode.remove(i);
//                    }
//                }
            evaluateNeighbours(evaluationNode);
            visited.add(evaluationNode);
//            System.out.println("------");
//            System.out.println("evaluationNode = " + evaluationNode);
        }
//        System.out.println("source, dest = " + source + ", " + dest);
        createPath(source, dest);
    }

    private int getNodeWithMinimumDistanceFromUnsettled() {
        Double min;
        int node = 0;

        Iterator<Integer> iterator = unsettled.iterator();
        node = iterator.next();
        min = distances[node];
        iterDist.clear();
        iterDist.add(0.0);
        for (int i = 1; i <= distances.length; i++) {
            if (unsettled.contains(i)) {
                if (distances[i] <= min) {
                    min = distances[i];
                    node = i;
                }
            }
//            iterDist.add(min);
//            System.out.print(min+"\t");
        }
//        System.out.println("");
        return node;
    }

    private void evaluateNeighbours(int evaluationNode) {
        Double edgeDistance = -1.0;
        Double newDistance = -1.0;
        Double[] newDist = new Double[number_of_nodes + 1];
        for (int destinationNode = 1; destinationNode <= number_of_nodes; destinationNode++) {
            if (!settled.contains(destinationNode)) {
                if (adjacencyMatrix[evaluationNode][destinationNode] != maxval) {
//                    System.out.println(evaluationNode + " " + destinationNode + " " + adjacencyMatrix[evaluationNode][destinationNode]);
                    edgeDistance = adjacencyMatrix[evaluationNode][destinationNode];
//                    System.out.println(distances[evaluationNode] + " " + edgeDistance);
                    newDistance = distances[evaluationNode] + edgeDistance;
                    if (newDistance < distances[destinationNode]) {
                        distances[destinationNode] = newDistance;
                    }
                    unsettled.add(destinationNode);
                }
            }
        }
        for (int i = 1; i <= number_of_nodes; i++) {
            newDist[i] = distances[i];
        }
        dijkTable.add(newDist);
    }

    public static void main(String... arg) {
        Double adjacency_matrix[][];
        int number_of_vertices;
        int source = 0;
        Scanner scan = new Scanner(System.in);
        try {
            System.out.println("Enter the number of vertices");
            number_of_vertices = scan.nextInt();
            adjacency_matrix = new Double[number_of_vertices + 1][number_of_vertices + 1];

            System.out.println("Enter the Weighted Matrix for the graph");
            for (int i = 1; i <= number_of_vertices; i++) {
                for (int j = 1; j <= number_of_vertices; j++) {
                    adjacency_matrix[i][j] = Double.parseDouble(scan.next());
                    if (i == j) {
                        adjacency_matrix[i][j] = 0.0;
                        continue;
                    }
                    if (adjacency_matrix[i][j] == 0.0) {
                        adjacency_matrix[i][j] = maxval;
                    }
                }
            }

            System.out.println("Enter the source ");
            source = scan.nextInt();
            int dest = scan.nextInt();
            DijkstraAlgorithmSet dijkstrasAlgorithm = new DijkstraAlgorithmSet(number_of_vertices);
            dijkstrasAlgorithm.dijkstra_algorithm(adjacency_matrix, source, dest);

            System.out.println("The Shorted Path to all nodes are ");
            for (int i = 1; i <= dijkstrasAlgorithm.distances.length - 1; i++) {
                System.out.println(source + " to " + i + " is " + dijkstrasAlgorithm.distances[i]);
            }
        } catch (InputMismatchException inputMismatch) {
            System.out.println("Wrong Input Format");
        }
        scan.close();
    }

//    public String result() {
//        return "\n\nDistance\t: \n" + this.dist + " meters";
//    }
    public Double[] getDistances() {
        return distances;
    }

    public Set<Integer> getSettled() {
        return settled;
    }

    public Set<Integer> getUnsettled() {
        return unsettled;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    private void createPath(int source, int dest) {
//        System.out.println("source, dest = " + source + ", " + dest);
//        for (int i = 0; i < dijkTable.size(); i++) {
//            for (int j = 0; j < dijkTable.get(i).length; j++) {
//                if (i == 0 && j == 0) {
//                    System.out.print("vstd\t");
//                } else if (j == 0) {
//                    System.out.print(visited.get(i) + "\t");
//                } else {
//                    System.out.print(dijkTable.get(i)[j] + "\t");
//                }
//            }
//            System.out.println("");
//        }
//
//        System.out.println("");

        Double tmp = distances[dest];
        int idx = dijkTable.size() - 1;
        int idxV = dest;

        path2.add(dest);
        while (visited.get(idx) != source) {
//            System.out.println("1 " + tmp);
//            System.out.println("1 " + idx);
//            System.out.println("1 " + idxV);
//            System.out.println(tmp == dijkTable.get(idx)[idxV]);

            while (tmp == dijkTable.get(idx)[idxV]) {
                idx--;
            }
            idx++;
//            System.out.println(idx);
            idxV = visited.get(idx);
            tmp = dijkTable.get(idx)[idxV];
            path2.add(idxV);
//            System.out.println("2 " + tmp);
//            System.out.println("2 " + idx);
//            System.out.println("2 " + idxV);
//            System.out.println("");
        }
//        System.out.println(idxV);

//        System.out.println(
//                "------------ PATH -------------");
        idx = path2.size() - 1;
        for (Integer n : path2) {
            path.add(path2.get(idx--));
        }
//        for (Integer n : path) {
//            System.out.println(n);
//        }

//        System.out.println(
//                "--------------------------------");
    }

}
