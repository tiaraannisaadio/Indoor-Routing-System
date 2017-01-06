/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author tiaraannisa
 */
public class Corridor {

    private String id;
    private double x;
    private double y;
    private double z;
    private String roofed;
    private double lat;
    private double lon;
    private Latitude latitude;
    private Longitude longitude;

    public Corridor(String id, double x, double y, double z, String roofed, Latitude lat, Longitude lon) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.roofed = roofed;
        this.latitude = lat;
        this.longitude = lon;
        this.lat = latitude.getDegree() + latitude.getMinute() / 60 + latitude.getSecond() / 3600;
        this.lon = longitude.getDegree() + longitude.getMinute() / 60 + longitude.getSecond() / 3600;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLatitude(Latitude latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Longitude longitude) {
        this.longitude = longitude;
    }

    public void setRoofed(String roofed) {
        this.roofed = roofed;
    }

    public String getId() {
        return id;
    }

    public double getZ() {
        return z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getRoofed() {
        return roofed;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public Latitude getLatitude() {
        return latitude;
    }

    public Longitude getLongitude() {
        return longitude;
    }

}
