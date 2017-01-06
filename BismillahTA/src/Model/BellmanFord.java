package Model;

import java.util.ArrayList;
import java.util.Scanner;

public class BellmanFord {

    private Double distances[];
    private int numberofvertices;
    public static final Double MAX_VALUE = 999.0;
    static ArrayList<Integer> path;
    private Double distance;
    private Double matrix[][];
    int count = 0;

    public BellmanFord(int numberofvertices) {
        this.numberofvertices = numberofvertices;
        distances = new Double[numberofvertices + 1];
        path = new ArrayList<>();
    }

    public void BellmanFordEvaluation(Double adjacencymatrix[][], int source, int dest) {
//        System.out.println(count++);
        matrix = adjacencymatrix;
        for (int node = 1; node <= numberofvertices; node++) {
            distances[node] = MAX_VALUE;
        }

        distances[source] = 0.0;
        for (int node = 1; node <= numberofvertices - 1; node++) {
            for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
                for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
                    if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
                        if (distances[destinationnode] > distances[sourcenode]
                                + adjacencymatrix[sourcenode][destinationnode]) {
                            distances[destinationnode] = distances[sourcenode]
                                    + adjacencymatrix[sourcenode][destinationnode];
                        }
                    }
                }
            }
        }

        for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
            for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
                if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
                    if (distances[destinationnode] > distances[sourcenode]
                            + adjacencymatrix[sourcenode][destinationnode]) {
                        System.out.println("The Graph contains negative egde cycle");
                    }
                }
            }
        }
        for (int vertex = 1; vertex <= numberofvertices; vertex++) {
            System.out.println("distance of source  " + source + " to "
                    + vertex + " is " + distances[vertex]);
        }
        distance = distances[dest];
    }

    public static void main(String... arg) {
        int numberofvertices = 0;
        int source;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of vertices");
        numberofvertices = scanner.nextInt();

        Double adjacencymatrix[][] = new Double[numberofvertices + 1][numberofvertices + 1];
        System.out.println("Enter the adjacency matrix");
        for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
            for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
                adjacencymatrix[sourcenode][destinationnode] = Double.parseDouble(scanner.next());
                if (sourcenode == destinationnode) {
                    adjacencymatrix[sourcenode][destinationnode] = 0.0;
                    continue;
                }
                if (adjacencymatrix[sourcenode][destinationnode] == 0.0) {
                    adjacencymatrix[sourcenode][destinationnode] = MAX_VALUE;
                }
            }
        }

        System.out.println("Enter the source vertex");
        source = scanner.nextInt();
        int dest = scanner.nextInt();

        BellmanFord bellmanford = new BellmanFord(numberofvertices);
        bellmanford.BellmanFordEvaluation(adjacencymatrix, source, dest);
        bellmanford.createPath(source, dest);
        scanner.close();
    }

    public Double getDistance() {
        return distance;
    }

    public void createPath(int source, int dest) {
        System.out.println("distance = " + distance);
        System.out.println(
                "------------ PATH -------------");

        for (Integer n : path) {
            System.out.println(n);
        }

        System.out.println(
                "--------------------------------");
    }
}
