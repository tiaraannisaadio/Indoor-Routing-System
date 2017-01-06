/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Connection.DBConnection;
import static Model.DijkstraAlgorithmSet.maxval;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import org.graphstream.algorithm.AStar;
import org.graphstream.algorithm.AStar.DistanceCosts;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import static org.graphstream.algorithm.Toolkit.*;

/**
 *
 * @author tiaraannisa
 */
public class BuildingGraph {

    private Graph graph;
    private Area area;
    private Double[][] matrix;
    private Viewer viewer;
    String styleSheet
            = "node.corridor1 {"
            + "	fill-color: red;"
            + "}"
            + "node.corridor2 {"
            + "	fill-color: green;"
            + "}"
            + "node.corridor3 {"
            + "	fill-color: blue;"
            + "}"
            + "node.room {"
            + "	fill-color: grey;"
            + "}"
            + "node.stair {"
            + "	fill-color: yellow;"
            //            + "size: 5px;"
            //            + "text-size: 6px;"
            + "}"
            //            + "node.marked {"
            //            + "	fill-color: black;"
            //            + "}"
            + "sprite {"
            + "shape: box;"
            + "size: 32px, 52px;"
            + "fill-mode: image-scaled;"
            + "fill-image: url('mapPinSmall.png');"
            + "}"
            + "node {"
            + "shape: box;"
            + "size: 10px, 10px;"
            //            + "text-align: center;"
            //            + "text-background-mode: plain; "
            //            + "text-offset: 2px, 0px; "
            //            + "text-padding: 2px; "
            //            + "text-background-color: #FFFFFFAA;"
            //            + "fill-color: #777;"
            + "text-size: 8px;"
            //            + "size: 10px, 10px;"
            //            + "text-mode: hidden;}" //            + "text-size: 6px;"
            //            + "z-index: 0;"
            + "}";

//    String styleSheet
//            = "node.marked {"
//            + "	fill-color: red;"
//            + "}";
    private ArrayList<Segments> segments;

    public BuildingGraph(Area area) {
        this.graph = new MultiGraph(area.getAreaName());
        this.area = area;
        segments = new ArrayList<>();
    }

    public void createGraph() throws SQLException {
        int i = 0, j;
        while (i < area.getBuildingList().size()) {
            Building b = area.getBuildingList().get(i);
//            graph.addNode(b.getId());
            j = 0;
            while (j < b.getRooms().size()) {
                Room room = b.getRooms().get(j);
                graph.addNode(room.getId());
                graph.getNode(room.getId()).setAttribute("x", room.getLon() + room.getZ() / 10000);
                graph.getNode(room.getId()).setAttribute("y", (room.getLat() - room.getZ() / 10000) * -1);
                graph.getNode(room.getId()).setAttribute("z", room.getZ());
                graph.getNode(room.getId()).addAttribute("latitude", room.getLat());
                graph.getNode(room.getId()).addAttribute("longitude", room.getLon());
//                graph.getNode(room.getId()).addAttribute("ui.class", "room");
                graph.getNode(room.getId()).addAttribute("ui.style", "fill-color: grey; ");//size: 5px;text-size: 6px;");
                j++;
            }
            j = 0;
            while (j < b.getCorridors().size()) {
                Corridor cor = b.getCorridors().get(j);
                graph.addNode(cor.getId());
                graph.getNode(cor.getId()).addAttribute("x", cor.getLon() + cor.getZ() / 10000);
                graph.getNode(cor.getId()).addAttribute("y", (cor.getLat() - cor.getZ() / 10000) * -1);
                graph.getNode(cor.getId()).addAttribute("z", cor.getZ());
                graph.getNode(cor.getId()).addAttribute("roofed", cor.getRoofed());
                graph.getNode(cor.getId()).addAttribute("latitude", cor.getLat());
                graph.getNode(cor.getId()).addAttribute("longitude", cor.getLon());
                graph.getNode(cor.getId()).addAttribute("ui.class", "corridor" + (int) cor.getZ());

//                if ((int) cor.getZ() == 1) {
//                    graph.getNode(cor.getId()).addAttribute("ui.style", "fill-color: red;");
//                } else if (((int) cor.getZ() == 2)) {
//                    graph.getNode(cor.getId()).addAttribute("ui.style", "fill-color: green;");
//                } else {
//                    graph.getNode(cor.getId()).addAttribute("ui.style", "fill-color: blue;");
//                }
                j++;
            }
            j = 0;
            while (j < b.getStairs().size()) {
                Stair stair = b.getStairs().get(j);
                graph.addNode(stair.getId());
                graph.getNode(stair.getId()).addAttribute("x", stair.getLon() + stair.getZ() / 10000);
                graph.getNode(stair.getId()).addAttribute("y", (stair.getLat() - stair.getZ() / 10000) * -1);
                graph.getNode(stair.getId()).addAttribute("z", stair.getZ());
                graph.getNode(stair.getId()).addAttribute("latitude", stair.getLat());
                graph.getNode(stair.getId()).addAttribute("longitude", stair.getLon());
                graph.getNode(stair.getId()).addAttribute("ui.class", "stair");
                j++;
            }
            i++;
        }

        for (int k = 0; k < area.getCorridorList().size(); k++) {
            Corridor cor = area.getCorridorList().get(k);
            graph.addNode(cor.getId());
            graph.getNode(cor.getId()).addAttribute("x", cor.getLon() + cor.getZ() / 10000);
            graph.getNode(cor.getId()).addAttribute("y", (cor.getLat() - cor.getZ() / 10000) * -1);
            graph.getNode(cor.getId()).addAttribute("z", cor.getZ());
            graph.getNode(cor.getId()).addAttribute("roofed", cor.getRoofed());
            graph.getNode(cor.getId()).addAttribute("latitude", cor.getLat());
            graph.getNode(cor.getId()).addAttribute("longitude", cor.getLon());
            if (cor.getRoofed().equals("y")) {
                graph.getNode(cor.getId()).addAttribute("ui.style", "fill-color: pink;size: 15px;text-size: 6px;");
            } else if (cor.getRoofed().equals("n")) {
                graph.getNode(cor.getId()).addAttribute("ui.style", "fill-color: purple;size: 15px;text-size: 6px;");
            }
        }

        loadSegments();
        createEdge();
    }

    public void loadSegments() throws SQLException {
        DBConnection db = new DBConnection();
        db.openConnetion();
        ResultSet rs;
        String query;
        query = "select * from segments";
        rs = db.getData(query);
        int idx = 0;
        while (rs.next()) {
            segments.add(new Segments(rs.getString("source") + "&" + rs.getString("destination"), rs.getString("source"), rs.getString("destination")));
            Double length = null;
            if (rs.getString("source").charAt(0) == 'S' && rs.getString("destination").charAt(0) == 'S') {
                length = 7.0;
            } else {
//                System.out.println(idx + " " + graph.getNode(rs.getString("source")) + " " + graph.getNode(rs.getString("destination")));
                Double lat1 = (Double) graph.getNode(rs.getString("source")).getAttribute("latitude");
                Double lon1 = (Double) graph.getNode(rs.getString("source")).getAttribute("longitude");
                Double lat2 = (Double) graph.getNode(rs.getString("destination")).getAttribute("latitude");
                Double lon2 = (Double) graph.getNode(rs.getString("destination")).getAttribute("longitude");
                length = distanceMeasure(lat1, lon1, lat2, lon2);
            }
            segments.get(idx).setLength(length);
//            System.out.println(idx + rs.getString("source") + "&" + rs.getString("destination") + " " + rs.getString("source") + " " + rs.getString("destination") + " " + length);
            idx++;
        }

    }

    public void createEdge() {
//        System.out.println("----- create edges -----" + segments.size());
        for (Segments s : segments) {
            if (graph.getEdge(s.getId()) == null) {
                graph.addEdge(s.getId(), s.getSource(), s.getDestination(), false).addAttribute("length", s.getLength());
                if (graph.getNode(s.getSource()).getAttribute("z").equals(graph.getNode(s.getDestination()).getAttribute("z"))) {
                    graph.getEdge(s.getId()).addAttribute("z", (Double) graph.getNode(s.getSource()).getAttribute("z"));
                } else if ((Double) graph.getNode(s.getSource()).getAttribute("z") > (Double) graph.getNode(s.getDestination()).getAttribute("z")) {
                    graph.getEdge(s.getId()).addAttribute("z", (Double) graph.getNode(s.getSource()).getAttribute("z"));
                } else if ((Double) graph.getNode(s.getSource()).getAttribute("z") < (Double) graph.getNode(s.getDestination()).getAttribute("z")) {
                    graph.getEdge(s.getId()).addAttribute("z", (Double) graph.getNode(s.getDestination()).getAttribute("z"));
                }
//            System.out.println("EDGE " + s.getId() + " " + s.getSource() + " " + s.getDestination() + " " + s.getLength());
            }
        }
    }

    public Viewer showGraph() {
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }
//        for (Edge e : graph.getEachEdge()) {
//            e.addAttribute("label", "" + (Double) e.getNumber("length"));
//        }

        graph.setStrict(false);
        graph.setAutoCreate(true);
        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        viewer = graph.display(false);
//        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.disableAutoLayout();
        viewer.addDefaultView(false);
        viewer.getDefaultView().setAutoscrolls(true);

//        viewer.getDefaultView().getCamera().setViewPercent(0.25);
        viewer.getDefaultView().setSize(1080, 710);

//        View view = viewer.addDefaultView(false);  
//        Clicks clicks = new Clicks(graph);
        return viewer;
//        viewer.enableAutoLayout();
    }

    /**
     * @return the graph
     */
    public Graph getGraph() {
        return graph;
    }

    public void resetGraphStyle() throws SQLException {
        graph.clear();
        createGraph();
        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }
    }

    /**
     * @param graph the graph to set
     *
     *
     *
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public void addNode(String idNode) {
        graph.addNode(idNode);
    }

    public Node getNode(String idNode) {
        return graph.getNode(idNode);
    }

    public void modifyNode(String idNode, String key, Object attribute) {
        graph.getNode(idNode).addAttribute(key, attribute);
    }

    public Double distanceMeasure(Double lat1, Double lon1, Double lat2, Double lon2) {  // generally used geo measurement function
        Double r = 6378.137; // Radius of earth in KM
//        System.out.println(lat1+" "+lon1+" "+lat2+" "+lon2);
        Double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        Double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double d = r * c;
        return d * 1000; // meters
    }

    public String dijkstraAlgo(String source, String dest) {
        System.out.println("*****" + "dijkstra algorithm node " + source + " to "
                + dest + "*****");
        // Edge lengths are stored in an attribute called "length"
        // The length of a path is the sum of the lengths of its edges
        Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "length");

        // Now compute the shortest path from source to all the other nodes
        // but taking the number of nodes in the path as its length
        dijkstra = new Dijkstra(Dijkstra.Element.NODE, null, null);
        dijkstra.init(graph);
        dijkstra.setSource(graph.getNode(source));
        dijkstra.compute();

        // Print the lengths of the new shortest paths
//        for (Node node : tree) {
        System.out.printf("%s->%s:%10.2f%n", dijkstra.getSource(), graph.getNode(dest),
                dijkstra.getPathLength(graph.getNode(dest)));
//        }

        // Color in blue all the nodes on the shortest path form source to dest
        for (Node node : dijkstra.getPathNodes(graph.getNode(dest))) {
            System.out.println((String) node.getId() + node.getAttribute("ui.style"));
//            node.addAttribute("ui.style", "marked");
            node.addAttribute("ui.style", "fill-color: black;");
        }
        // Color in red all the edges in the shortest path tree
        for (Edge edge : dijkstra.getPathEdges(graph.getNode(dest))) {
            edge.addAttribute("ui.style", "fill-color: green;");
        }
        // Print all the shortest paths between source and dest
        Iterator<Path> pathIterator = dijkstra.getAllPathsIterator(graph.getNode(dest));
        String result = "";
        while (pathIterator.hasNext()) {
            result += pathIterator.next();
//            Iterator<? extends Node> iNode = pathIterator.next().getEachNode().iterator();
//            while (iNode.hasNext()) {
//                Node next = pathIterator.next().peekNode();
//                next.setAttribute("ui.class", "marked");
//                sleep();
//            }
        }

//        SpriteManager sman = new SpriteManager(graph);
//        Sprite s1 = sman.addSprite("S1");
//        Sprite s2 = sman.addSprite("S2");
//        double p1[] = nodePosition(graph.getNode(source));
//        double p2[] = nodePosition(graph.getNode(dest));
//        s1.setPosition(p1[0], p1[1], p1[2]);
//        s2.setPosition(p2[0], p2[1], p2[2]);
        Double dist = dijkstra.getPathLength(graph.getNode(dest));
        dijkstra.clear();
        return "Shortest Path\t: \n" + result + "\n\nDistance\t: \n" + dist + " meters";
    }

    protected void sleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }

    public String dijkstraAlgorithm(String source, String dest) {
        System.out.println("*****" + "dijkstra algorithm node " + source + " to "
                + dest + "*****");
        createMatrix();
        int sourceIdx = graph.getNode(source).getIndex() + 1;
        int destIdx = graph.getNode(dest).getIndex() + 1;
        Double distance;
//        System.out.println("index : " + sourceIdx + " & " + destIdx);
        ArrayList<Integer> path = new ArrayList<>();
        String result = "Shortest Path\t: \n";
        System.out.println("graph.getNodeCount() = " + graph.getNodeCount());
        DijkstraAlgorithmSet dijk = new DijkstraAlgorithmSet(graph.getNodeCount());
        dijk.dijkstra_algorithm(matrix, sourceIdx, destIdx);
        path = dijk.getPath();
        distance = dijk.getDistances()[destIdx];
//        for (int i = 1; i <= dijk.getDistances().length - 1; i++) {
//            System.out.println(sourceIdx + " to " + i + " is " + dijk.getDistances()[i]);
//        }
        for (Integer i : path) {
            result += graph.getNode(i - 1).getId() + "\n";
            graph.getNode(i - 1).addAttribute("ui.style", "fill-color: black;");
        }
        for (int i = 0; i < path.size() - 1; i++) {
            String idx1 = graph.getNode(path.get(i) - 1).getId() + "&" + graph.getNode(path.get(i + 1) - 1).
                    getId();
            String idx2 = graph.getNode(path.get(i + 1) - 1).getId() + "&" + graph.getNode(path.get(i) - 1).
                    getId();
            if (graph.getEdge(idx1) != null) {
                graph.getEdge(idx1).addAttribute("ui.style", "fill-color: orange; size: 5px;");
            } else if (graph.getEdge(idx2) != null) {
                graph.getEdge(idx2).addAttribute("ui.style", "fill-color: orange; size: 5px;");
            }
        }
        result += "\n\nDistance\t: \n" + distance + " meters";
//        System.out.println(result);
        return result;
    }

    public String floydWarshalAlgo(String source, String dest) {
        System.out.println("*****" + "Floyd-Warshal algorithm node " + source + " to "
                + dest + "*****");
        createMatrix();
        int sourceIdx = graph.getNode(source).getIndex() + 1;
        int destIdx = graph.getNode(dest).getIndex() + 1;
        Double distance;
        String result = "";
        System.out.println("graph.getNodeCount() = " + graph.getNodeCount());
        FloydWarshall fw = new FloydWarshall(graph.getNodeCount(), sourceIdx, destIdx);
        fw.floydwarshall(matrix, sourceIdx, destIdx);
        distance = fw.getDistance();
//        path.add(sourceIdx);
//        for (Integer i : fw.getPath()) {
//            path.add(i);
//        }
//        path.add(destIdx);
//        for (Integer i : path) {
//            result += graph.getNode(i - 1).getId() + "\n";
//            graph.getNode(i - 1).addAttribute("ui.style", "fill-color: black;");
//        }
//        for (int i = 0; i < path.size() - 1; i++) {
//            String idx1 = graph.getNode(path.get(i) - 1).getId() + "&" + graph.getNode(path.get(i + 1) - 1).
//                    getId();
//            String idx2 = graph.getNode(path.get(i + 1) - 1).getId() + "&" + graph.getNode(path.get(i) - 1).
//                    getId();
//            if (graph.getEdge(idx1) != null) {
//                System.out.println("idx1 = " + idx1);
//                graph.getEdge(idx1).addAttribute("ui.style", "fill-color: orange; size: 5px;");
//            } else if (graph.getEdge(idx2) != null) {
//                System.out.println("idx2 = " + idx2);
//                graph.getEdge(idx2).addAttribute("ui.style", "fill-color: orange; size: 5px;");
//            } else {
//                System.out.println("idx1 = " + idx1);
//                System.out.println("idx2 = " + idx2);
//            }
//        }
        result += "\n\nDistance\t: \n" + distance + " meters";
//        System.out.println(result);
        return result;
    }

    public String astarAlgo(String source, String dest) {
        System.out.println("*****" + "A* algorithm node " + source + " to "
                + dest + "*****");
        String result = "Shortest Path\t: \n";
        Double distance = 0.0;
        System.out.println("graph.getNodeCount() = " + graph.getNodeCount());
        AStar astar = new AStar(graph);
        astar.setCosts(new DistanceCosts());
        astar.compute(source, dest);

        for (Node n : astar.getShortestPath().getEachNode()) {
            result += n.getId() + "\n";
            n.addAttribute("ui.style", "fill-color: black;");
        }
        for (Edge e : astar.getShortestPath().getEachEdge()) {
            distance += (Double) e.getAttribute("length");
            e.addAttribute("ui.style", "fill-color: orange; size: 5px;");
        }
        result += "\n\nDistance\t: \n" + distance + " meters";
//        System.out.println(result);
        return result;
    }

    public String getPath(String source, String dest) {
        createMatrix();
        int sourceIdx = graph.getNode(source).getIndex() + 1;
        int destIdx = graph.getNode(dest).getIndex() + 1;
//        System.out.println("index : " + sourceIdx + " & " + destIdx);
        ArrayList<Integer> path = new ArrayList<>();
        String result = "Shortest Path\t: \n";

        DijkstraAlgorithmSet dijk = new DijkstraAlgorithmSet(graph.getNodeCount());
        dijk.dijkstra_algorithm(matrix, sourceIdx, destIdx);
        path = dijk.getPath();
//        for (int i = 1; i <= dijk.getDistances().length - 1; i++) {
//            System.out.println(sourceIdx + " to " + i + " is " + dijk.getDistances()[i]);
//        }
        for (Integer i : path) {
            result += graph.getNode(i - 1).getId() + "\n";
            graph.getNode(i - 1).addAttribute("ui.style", "fill-color: black;");
        }
        for (int i = 0; i < path.size() - 1; i++) {
            String idx1 = graph.getNode(path.get(i) - 1).getId() + "&" + graph.getNode(path.get(i + 1) - 1).
                    getId();
            String idx2 = graph.getNode(path.get(i + 1) - 1).getId() + "&" + graph.getNode(path.get(i) - 1).
                    getId();
            if (graph.getEdge(idx1) != null) {
                graph.getEdge(idx1).addAttribute("ui.style", "fill-color: orange; size: 5px;");
            } else if (graph.getEdge(idx2) != null) {
                graph.getEdge(idx2).addAttribute("ui.style", "fill-color: orange; size: 5px;");
            }
        }
//        System.out.println(result);
        return result;
    }

    public String bfAlgorithm(String source, String dest) {
        System.out.println("*****" + "Bellman-Ford algorithm node " + source + " to "
                + dest + "*****");
        createMatrix();
        int sourceIdx = graph.getNode(source).getIndex() + 1;
        int destIdx = graph.getNode(dest).getIndex() + 1;
        Double distance;
        String result = "";
        System.out.println("graph.getNodeCount() = " + graph.getNodeCount());
        BellmanFord bf = new BellmanFord(graph.getNodeCount());
        bf.BellmanFordEvaluation(matrix, sourceIdx, destIdx);
        distance = bf.getDistance();
        result += "\n\nDistance\t: \n" + distance + " meters";
//        System.out.println(result);
        return result;
    }

    public void createMatrix() {
        int n = graph.getNodeCount() + 1;
        matrix = new Double[n][n];
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
//                System.out.println(graph.getNode(i).hasEdgeBetween(j));
                String idEdge1 = graph.getNode(i - 1).getId() + "&" + graph.getNode(j - 1).getId();
                String idEdge2 = graph.getNode(j - 1).getId() + "&" + graph.getNode(i - 1).getId();
                if (i == j) {
                    matrix[i][j] = 0.0;
                    continue;
                } else if (graph.getEdge(idEdge1) != null) {
//                matrix[i][j] = (Double) (graph.getNode(i).hasEdgeBetween(j) ? distanceMeasure(graph.getNode(i).getAttribute("latitude"), graph.getNode(i).getAttribute("longitude"), graph.getNode(j).getAttribute("latitude"), graph.getNode(j).getAttribute("longitude")) : 0);
                    matrix[i][j] = (Double) graph.getEdge(idEdge1).getAttribute("length");
                } else if (graph.getEdge(idEdge2) != null) {
                    matrix[i][j] = (Double) graph.getEdge(idEdge2).getAttribute("length");
                } else {
                    matrix[i][j] = 0.0;
                }

                if (matrix[i][j] == 0.0) {
                    matrix[i][j] = 999.0;
                }
            }
        }

//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.println(graph.getNode(i) + " --> " + graph.getNode(j) + " = " + matrix[i][j]);
//            }
//            System.out.println("");
//        }
//        
//        for (int i = -1; i < n; i++) {
//            for (int j = -1; j < n; j++) {
//                if (i == -1 && j == -1) {
//                    System.out.print("\t");
//                } else if (i == -1 && j > -1) {
//                    System.out.print(graph.getNode(j) + "\t");
//                } else if (i > -1 && j == -1) {
//                    System.out.print(graph.getNode(i) + "\t");
//                } else {
//                    System.out.print(matrix[i][j] + "\t");
//                }
//                System.out.print("\t");
//                System.out.println(graph.getNode(i) + " --> " + graph.getNode(j) + " = " + matrix[i][j]);
//            }
//            System.out.println("");
//        }
    }

    void zoomIn() {
        viewer.getDefaultView().getCamera().setViewPercent(viewer.getDefaultView().getCamera().getViewPercent() / 1.5);
    }

    void zoomOut() {
//        viewer.getDefaultView().getCamera().setAutoFitView(true);
//        viewer.getDefaultView().setSize(1080, 710);
        viewer.getDefaultView().getCamera().setViewPercent(viewer.getDefaultView().getCamera().getViewPercent() * 1.5);
    }

    void removeOpen() {
        for (Corridor c : area.getCorridorList()) {
            if (c.getRoofed().equals("n")) {
                graph.removeNode(c.getId());
            }
        }
    }

    void addOpen() {
        for (Corridor cor : area.getCorridorList()) {
            if (cor.getRoofed().equals("n")) {
                graph.addNode(cor.getId());
                graph.getNode(cor.getId()).addAttribute("x", cor.getLon() + cor.getZ() / 10000);
                graph.getNode(cor.getId()).addAttribute("y", (cor.getLat() - cor.getZ() / 10000) * -1);
                graph.getNode(cor.getId()).addAttribute("z", cor.getZ());
                graph.getNode(cor.getId()).addAttribute("roofed", cor.getRoofed());
                graph.getNode(cor.getId()).addAttribute("latitude", cor.getLat());
                graph.getNode(cor.getId()).addAttribute("longitude", cor.getLon());
                if (cor.getRoofed().equals("y")) {
                    graph.getNode(cor.getId()).addAttribute("ui.style", "fill-color: pink;size: 15px;text-size: 6px;");
                } else if (cor.getRoofed().equals("n")) {
                    graph.getNode(cor.getId()).addAttribute("ui.style", "fill-color: purple;size: 15px;text-size: 6px;");
                }
            }
        }
        createEdge();
    }

    void unhideAll() {
        for (Node n : graph.getEachNode()) {
            if (n.hasAttribute("ui.hide")) {
                n.removeAttribute("ui.hide");
            }
        }
        for (Edge e : graph.getEachEdge()) {
            if (e.hasAttribute("ui.hide")) {
                e.removeAttribute("ui.hide");
            }
        }
    }

    void unhideZ(Double z) {
        unhideAll();
//        System.out.println("z = " + z);
        for (Node n : graph.getEachNode()) {
//            System.out.println((Double) n.getAttribute("z"));
            if (!Objects.equals((Double) n.getAttribute("z"), z)) {
                n.addAttribute("ui.hide");
            }
        }
        for (Edge e : graph.getEachEdge()) {
            if (!Objects.equals((Double) e.getAttribute("z"), z)) {
                e.addAttribute("ui.hide");
            }
        }
    }

    void upView() {
        Double x = viewer.getDefaultView().getCamera().getViewCenter().x;
        Double y = viewer.getDefaultView().getCamera().getViewCenter().y + 0.0001;
        Double z = viewer.getDefaultView().getCamera().getViewCenter().z;
        viewer.getDefaultView().getCamera().setViewCenter(x, y, z);
    }

    void rightView() {
        Double x = viewer.getDefaultView().getCamera().getViewCenter().x + 0.0001;
        Double y = viewer.getDefaultView().getCamera().getViewCenter().y;
        Double z = viewer.getDefaultView().getCamera().getViewCenter().z;
        viewer.getDefaultView().getCamera().setViewCenter(x, y, z);
    }

    void leftView() {
        Double x = viewer.getDefaultView().getCamera().getViewCenter().x - 0.0001;
        Double y = viewer.getDefaultView().getCamera().getViewCenter().y;
        Double z = viewer.getDefaultView().getCamera().getViewCenter().z;
        viewer.getDefaultView().getCamera().setViewCenter(x, y, z);
    }

    void downView() {
        Double x = viewer.getDefaultView().getCamera().getViewCenter().x;
        Double y = viewer.getDefaultView().getCamera().getViewCenter().y - 0.0001;
        Double z = viewer.getDefaultView().getCamera().getViewCenter().z;
        viewer.getDefaultView().getCamera().setViewCenter(x, y, z);
    }

    void setView() {
        viewer.getDefaultView().getCamera().setViewCenter(107.01447222222222, -6.009419444444445, 0.0);
    }

    void show2D() {
        int i = 0, j;
        while (i < area.getBuildingList().size()) {
            Building b = area.getBuildingList().get(i);
//            graph.addNode(b.getId());
            j = 0;
            while (j < b.getRooms().size()) {
                Room room = b.getRooms().get(j);
//                graph.addNode(room.getId());
                graph.getNode(room.getId()).removeAttribute("x");
                graph.getNode(room.getId()).removeAttribute("y");
                graph.getNode(room.getId()).setAttribute("x", room.getLon());
                graph.getNode(room.getId()).setAttribute("y", room.getLat() * -1);
                j++;
            }
            j = 0;
            while (j < b.getCorridors().size()) {
                Corridor cor = b.getCorridors().get(j);
                graph.getNode(cor.getId()).removeAttribute("x");
                graph.getNode(cor.getId()).removeAttribute("y");
                graph.getNode(cor.getId()).addAttribute("x", cor.getLon());
                graph.getNode(cor.getId()).addAttribute("y", cor.getLat() * -1);

                j++;
            }
            j = 0;
            while (j < b.getStairs().size()) {
                Stair stair = b.getStairs().get(j);
                graph.getNode(stair.getId()).removeAttribute("x");
                graph.getNode(stair.getId()).removeAttribute("y");
                graph.getNode(stair.getId()).addAttribute("x", stair.getLon());
                graph.getNode(stair.getId()).addAttribute("y", stair.getLat() * -1);
                j++;
            }
            i++;
        }

        for (int k = 0; k < area.getCorridorList().size(); k++) {
            Corridor cor = area.getCorridorList().get(k);
            graph.getNode(cor.getId()).removeAttribute("x");
            graph.getNode(cor.getId()).removeAttribute("y");
            graph.getNode(cor.getId()).addAttribute("x", cor.getLon());
            graph.getNode(cor.getId()).addAttribute("y", cor.getLat() * -1);
        }

//        loadSegments();
//        createEdge();
    }

    void show3D() {
        int i = 0, j;
        while (i < area.getBuildingList().size()) {
            Building b = area.getBuildingList().get(i);
//            graph.addNode(b.getId());
            j = 0;
            while (j < b.getRooms().size()) {
                Room room = b.getRooms().get(j);
//                graph.addNode(room.getId());
                graph.getNode(room.getId()).setAttribute("x", room.getLon() + room.getZ() / 10000);
                graph.getNode(room.getId()).setAttribute("y", (room.getLat() - room.getZ() / 10000) * -1);
                j++;
            }
            j = 0;
            while (j < b.getCorridors().size()) {
                Corridor cor = b.getCorridors().get(j);
                graph.getNode(cor.getId()).addAttribute("x", cor.getLon() + cor.getZ() / 10000);
                graph.getNode(cor.getId()).addAttribute("y", (cor.getLat() - cor.getZ() / 10000) * -1);

                j++;
            }
            j = 0;
            while (j < b.getStairs().size()) {
                Stair stair = b.getStairs().get(j);
                graph.getNode(stair.getId()).addAttribute("x", stair.getLon() + stair.getZ() / 10000);
                graph.getNode(stair.getId()).addAttribute("y", (stair.getLat() - stair.getZ() / 10000) * -1);
                j++;
            }
            i++;
        }

        for (int k = 0; k < area.getCorridorList().size(); k++) {
            Corridor cor = area.getCorridorList().get(k);
            graph.getNode(cor.getId()).addAttribute("x", cor.getLon() + cor.getZ() / 10000);
            graph.getNode(cor.getId()).addAttribute("y", (cor.getLat() - cor.getZ() / 10000) * -1);
        }

//        loadSegments();
//        createEdge();
    }
}
