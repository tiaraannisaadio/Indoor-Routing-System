/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author tiaraannisa
 */
public class Building {

    private String id;
    private int level;
    private ArrayList<Room> rooms;
    private ArrayList<Corridor> corridors;
    private ArrayList<Stair> stairs;

    public Building(String id, int level) {
        this.id = id;
        this.level = level;
        this.rooms = new ArrayList<>();
        this.corridors = new ArrayList<>();
        this.stairs = new ArrayList<>();
    }

    public void addRoom(String idRoom, double x, double y, double z, Latitude lat, Longitude lon ) {
        rooms.add(new Room(idRoom, x, y, z, lat, lon));
    }

    public void addCorridor(String idCorridor, double x, double y, double z, String roofed, Latitude lat, Longitude lon) {
        corridors.add(new Corridor(idCorridor, x, y, z, roofed, lat, lon));
    }

    public void addStair(String idStair, double x, double y, double z,  Latitude lat, Longitude lon) {
        stairs.add(new Stair(idStair, x, y, z,  lat, lon));
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public void setCorridors(ArrayList<Corridor> corridors) {
        this.corridors = corridors;
    }

    public void setStairs(ArrayList<Stair> stairs) {
        this.stairs = stairs;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Corridor> getCorridors() {
        return corridors;
    }

    public ArrayList<Stair> getStairs() {
        return stairs;
    }
    
    
}
