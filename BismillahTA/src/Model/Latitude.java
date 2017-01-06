/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author IFLABASPRAK2
 */
public class Latitude {

    private int degree;
    private int minute;
    private Double second;
    private String direction;

    public Latitude(int degree, int minute, Double second, String direction) {
        this.degree = degree;
        this.minute = minute;
        this.second = second;
        this.direction = direction;
    }

    public int getDegree() {
        return degree;
    }

    public int getMinute() {
        return minute;
    }

    public Double getSecond() {
        return second;
    }

    public String getDirection() {
        return direction;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(Double second) {
        this.second = second;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
