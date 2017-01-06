/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Connection.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

/**
 *
 * @author tiaraannisa
 */
public class Application {

    private BuildingGraph bGraph;
    private Area area;
    private int nNode = 0, nEdge = 0;
    private DBConnection db = new DBConnection();
    private Double[][] graphMatrix;
    private Long time;

    public Application() {
        area = new Area("Telkom University");
    }

    public void loadMap() throws SQLException {
        db.openConnetion();
        ResultSet rs;
        String query;
        String idBuilding = null;

        query = "select * from buildings";
        rs = db.getData(query);
        while (rs.next()) {
            area.addBuilding(rs.getString("Building_ID"), rs.getInt("level"));
        }

        query = "select * from rooms";
        rs = db.getData(query);
        nNode = nNode + rs.getRow();
        while (rs.next()) {
            idBuilding = rs.getString("Building_ID");
            Latitude lat = new Latitude(rs.getInt("lat_degree"), rs.getInt("lat_minute"), rs.getDouble("lat_second"), rs.getString("lat_dir"));
            Longitude lon = new Longitude(rs.getInt("long_degree"), rs.getInt("long_minute"), rs.getDouble("long_second"), rs.getString("long_dir"));
            area.getBuilding(idBuilding).addRoom(rs.getString("Room_ID"), rs.getDouble("X"), rs.getDouble("Y"), rs.getDouble("Z"), lat, lon);
        }

        query = "select * from corridors";
        rs = db.getData(query);
        nNode = nNode + rs.getRow();
        while (rs.next()) {
            Latitude lat = new Latitude(rs.getInt("lat_degree"), rs.getInt("lat_minute"), rs.getDouble("lat_second"), rs.getString("lat_dir"));
            Longitude lon = new Longitude(rs.getInt("long_degree"), rs.getInt("long_minute"), rs.getDouble("long_second"), rs.getString("long_dir"));
            if (rs.getString("Building_ID") != null) {
                idBuilding = rs.getString("Building_ID");
//                System.out.println(rs.getString("Corridor_ID") + idBuilding);
                area.getBuilding(idBuilding).addCorridor(rs.getString("Corridor_ID"), rs.getDouble("X"), rs.getDouble("Y"), rs.getDouble("Z"), rs.getString("Roofed"), lat, lon);
            } else {
                area.addCorridor(rs.getString("Corridor_ID"), rs.getDouble("X"), rs.getDouble("Y"), rs.getDouble("Z"), rs.getString("Roofed"), lat, lon);
            }
        }

        query = "select * from stairs";
        rs = db.getData(query);
        nNode = nNode + rs.getRow();
        while (rs.next()) {
            idBuilding = rs.getString("Building_ID");
            Latitude lat = new Latitude(rs.getInt("lat_degree"), rs.getInt("lat_minute"), rs.getDouble("lat_second"), rs.getString("lat_dir"));
            Longitude lon = new Longitude(rs.getInt("long_degree"), rs.getInt("long_minute"), rs.getDouble("long_second"), rs.getString("long_dir"));
            area.getBuilding(idBuilding).addStair(rs.getString("Stair_ID"), rs.getDouble("X"), rs.getDouble("Y"), rs.getDouble("Z"), lat, lon);

        }

//        bGraph = new BuildingGraph(rs.getRow());
    }

    public void setMatrix() {
        graphMatrix = new Double[nNode][nNode];
        for (int i = 0; i < nNode; i++) {
            for (int j = 0; j < nNode; j++) {
                graphMatrix[i][j] = 0.0;
            }
        }
    }

    public void fillMatrix() throws SQLException {
        db.openConnetion();
        ResultSet rs;
        String query;
        String idBuilding = null;

        query = "select * from edge;";
        rs = db.getData(query);
        nEdge = rs.getRow();
        while (rs.next()) {
            graphMatrix[rs.getInt("idNode1")][rs.getInt("idNode2")] = rs.getDouble("Length");
        }
    }

    public void makeGraph() throws SQLException {
        bGraph = new BuildingGraph(area);
        bGraph.createGraph();
    }

    public String getResult(String algo, String source, String destination) throws SQLException {
        String res = null;
        bGraph.resetGraphStyle();
//        System.out.println("algo : " + algo);
        if ("dijkstra".equals(algo)) {
            res = bGraph.dijkstraAlgorithm(source, destination);
        } else if ("dijkstra_close".equals(algo)) {
//            System.out.println("masuk 1");
            bGraph.removeOpen();
//            System.out.println("masuk 2");
            res = bGraph.dijkstraAlgorithm(source, destination);
            bGraph.addOpen();
        } else if ("fw".equals(algo)) {
            res = getPath(source, destination);
            final long startTime = System.nanoTime();
            res += bGraph.floydWarshalAlgo(source, destination);
            final long duration = System.nanoTime() - startTime;
            setTime(duration);
        } else if ("fw_close".equals(algo)) {
            bGraph.removeOpen();
            res = getPath(source, destination);
            final long startTime = System.nanoTime();
            res += bGraph.floydWarshalAlgo(source, destination);
            final long duration = System.nanoTime() - startTime;
            setTime(duration);
            bGraph.addOpen();
        } else if ("astar".equals(algo)) {
            res = bGraph.astarAlgo(source, destination);
        } else if ("astar_close".equals(algo)) {
            bGraph.removeOpen();
            res = bGraph.astarAlgo(source, destination);
            bGraph.addOpen();
        } else if ("bf".equals(algo)) {
            res = getPath(source, destination);
            final long startTime = System.nanoTime();
            res += bGraph.bfAlgorithm(source, destination);
            final long duration = System.nanoTime() - startTime;
            setTime(duration);
        } else if ("bf_close".equals(algo)) {
            bGraph.removeOpen();
            res = getPath(source, destination);
            final long startTime = System.nanoTime();
            res += bGraph.bfAlgorithm(source, destination);
            final long duration = System.nanoTime() - startTime;
            setTime(duration);
            bGraph.addOpen();
        }
        return res;
//        bGraph.floydWarshalAlgo(source, destination);
//        return null;
    }

    public Viewer printAll() {
//        bGraph.showGraph().addDefaultView(false);
        return bGraph.showGraph();

//        int i = 0, j;
//        while (i < area.getBuildingList().size()) {
//            Building b = area.getBuildingList().get(i);
//            System.out.println(b.getId());
//            j = 0;
//            while (j < b.getRooms().size()) {
//                System.out.println(b.getRooms().get(j).getId());
//                System.out.println("");
//                j++;
//            }
//            j = 0;
//            while (j < b.getCorridors().size()) {
//                System.out.println(b.getCorridors().get(j).getId());
//                System.out.println("");
//                j++;
//            }
//            j = 0;
//            while (j < b.getStairs().size()) {
//                System.out.println(b.getStairs().get(j).getId());
//                System.out.println("");
//                j++;
//            }
//            i++;
//        }
    }

    public void zoomIn() {
        bGraph.zoomIn();
    }

    public void zoomOut() {
        bGraph.zoomOut();
    }

    public void showByZ(String s) {
        if (s.equals("ALL")) {
            bGraph.unhideAll();
        } else {
            bGraph.unhideZ(Double.parseDouble(s) * 1.0);
        }
    }

    public String getPath(String source, String destination) {
        return bGraph.getPath(source, destination);
    }

    public Long getTimeExecute() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void upView() {
        bGraph.upView();
    }

    public void rightView() {
        bGraph.rightView();
    }

    public void leftView() {
        bGraph.leftView();
    }

    public void downView() {
        bGraph.downView();
    }

    public void setView() {
        bGraph.setView();
    }

    public void show2D() {
        bGraph.show2D();
    }

    public void show3D() {
        bGraph.show3D();
    }

}
