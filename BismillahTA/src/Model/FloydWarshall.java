package Model;

import java.util.ArrayList;
import java.util.Scanner;

public class FloydWarshall {

    private Double distancematrix[][];
    private Double matrix[][];
    private int numberofvertices;
    public static final Double INFINITY = 999.0;
    static ArrayList<Integer> path;
    private Double distance;
    private int fwsource;
    private int fwdestination;
    int count = 0;

    public FloydWarshall(int numberofvertices, int source, int dest) {
        distancematrix = new Double[numberofvertices + 1][numberofvertices + 1];
        matrix = new Double[numberofvertices + 1][numberofvertices + 1];
        this.numberofvertices = numberofvertices;
        path = new ArrayList<>();
        this.fwsource = source;
        this.fwdestination = dest;
    }

    public void floydwarshall(Double adjacencymatrix[][], int source1, int dest) {
//        System.out.println("count = " + count++);
        if (count == 1) {
            matrix = adjacencymatrix;
        }
        for (int source = 1; source < numberofvertices; source++) {
            for (int destination = 1; destination < numberofvertices; destination++) {
                if (source == destination) {
                    adjacencymatrix[source][destination] = 0.0;
                    continue;
                }
                if (adjacencymatrix[source][destination] == 0.0) {
                    adjacencymatrix[source][destination] = INFINITY;
                }
            }
        }
//        distancematrix = new Double[numberofvertices + 1][numberofvertices + 1];
        for (int source = 1; source <= numberofvertices; source++) {
            for (int destination = 1; destination <= numberofvertices; destination++) {
                distancematrix[source][destination] = adjacencymatrix[source][destination];
            }
        }

        for (int intermediate = 1; intermediate <= numberofvertices; intermediate++) {
            for (int source = 1; source <= numberofvertices; source++) {
                for (int destination = 1; destination <= numberofvertices; destination++) {
                    System.out.print(intermediate
                            + "\t" + source
                            + "\t" + destination
                            + "\t" + distancematrix[source][destination]
                            + "\t" + distancematrix[source][intermediate]
                            + "\t" + distancematrix[intermediate][destination]
                            + "\t" + (distancematrix[source][intermediate] + distancematrix[intermediate][destination] < distancematrix[source][destination])
                            + "\t");
                    if (distancematrix[source][intermediate] + distancematrix[intermediate][destination]
                            < distancematrix[source][destination]) {
                        distancematrix[source][destination] = distancematrix[source][intermediate]
                                + distancematrix[intermediate][destination];
                    }
                    System.out.println(distancematrix[source][destination]);
                }
            }
        }

//        if (count == 1) {
        distance = distancematrix[source1][dest];
//        }
//        Double tmp = distancematrix[source1][dest];
//        Double[][] tmpdistancematrix = new Double[numberofvertices + 1][numberofvertices + 1];
//        for (int source = 1; source <= numberofvertices; source++) {
//            for (int destination = 1; destination <= numberofvertices; destination++) {
//                tmpdistancematrix[source][destination] = adjacencymatrix[source][destination];
//            }
//        }
//
//        for (int intermediate = 1; intermediate <= numberofvertices; intermediate++) {
//            for (int source = 1; source <= numberofvertices; source++) {
//                for (int destination = 1; destination <= numberofvertices; destination++) {
//                    if (tmpdistancematrix[source][intermediate] + tmpdistancematrix[intermediate][destination]
//                            < tmpdistancematrix[source][destination]) {
//                        tmpdistancematrix[source][destination] = tmpdistancematrix[source][intermediate]
//                                + tmpdistancematrix[intermediate][destination];
//                        if (source == source1 && destination == dest && tmp.equals(tmpdistancematrix[source][destination])) {
//                            System.out.println("k " + count + " = " + intermediate);
//                            System.out.println(source == source1);
//                            System.out.println(destination == dest);
//                            System.out.println((tmp.equals(tmpdistancematrix[source][destination])) + ", " + tmp + ", " + tmpdistancematrix[source][destination]);
//                            System.out.println("");
//                            path.add(intermediate);
//                            floydwarshall(matrix, intermediate, dest);
//                            System.out.println(intermediate);
//                        }
//                    }
//                }
//            }
//        }
        for (int source = 1; source <= numberofvertices; source++) {
            System.out.print("\t" + source);
        }

        System.out.println();
        for (int source = 1; source <= numberofvertices; source++) {
            System.out.print(source + "\t");
            for (int destination = 1; destination <= numberofvertices; destination++) {
                System.out.print(distancematrix[source][destination] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String... arg) {
        Double adjacency_matrix[][];
        int numberofvertices;

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of vertices");
        numberofvertices = scan.nextInt();

        adjacency_matrix = new Double[numberofvertices + 1][numberofvertices + 1];
        System.out.println("Enter the Weighted Matrix for the graph");
        for (int source = 1; source <= numberofvertices; source++) {
            for (int destination = 1; destination <= numberofvertices; destination++) {
                adjacency_matrix[source][destination] = Double.parseDouble(scan.next());
                if (source == destination) {
                    adjacency_matrix[source][destination] = 0.0;
                    continue;
                }
                if (adjacency_matrix[source][destination] == 0.0) {
                    adjacency_matrix[source][destination] = INFINITY;
                }
            }
        }

        System.out.println("The Transitive Closure of the Graph");
        FloydWarshall floydwarshall = new FloydWarshall(numberofvertices, 4, 3);
        floydwarshall.floydwarshall(adjacency_matrix, 4, 3);
        floydwarshall.createPath(4, 3);
        scan.close();
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

    public ArrayList<Integer> getPath() {
        return path;
    }

    public Double getDistance() {
        return distance;
    }

}
