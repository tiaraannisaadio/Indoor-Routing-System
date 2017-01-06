/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author IFLABASPRAK2
 */
public class Area {

    private String areaName;
    private ArrayList<Building> buildingList;
    private ArrayList<Corridor> corridorList;

    public Area(String areaName) {
        this.areaName = areaName;
        buildingList = new ArrayList<>();
        corridorList = new ArrayList<>();
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setBuildingList(ArrayList<Building> buildingList) {
        this.buildingList = buildingList;
    }

    public void addBuilding(String id, int level) {
        this.buildingList.add(new Building(id, level));
    }

    public void addCorridor(String id, Double x, Double y, Double z, String roofed, Latitude lat, Longitude lon) {
        this.corridorList.add(new Corridor(id, x, y, z, roofed, lat, lon));
    }

    public void setCorridorList(ArrayList<Corridor> corridorList) {
        this.corridorList = corridorList;
    }

    public String getAreaName() {
        return areaName;
    }

    public ArrayList<Building> getBuildingList() {
        return buildingList;
    }

    public ArrayList<Corridor> getCorridorList() {
        return corridorList;
    }

    public Building getBuilding(String idBuilding) {
        int idx = 0;
        while (!buildingList.get(idx).getId().equals(idBuilding)) {
            idx++;
        }
        return buildingList.get(idx);
    }

//    )
}
