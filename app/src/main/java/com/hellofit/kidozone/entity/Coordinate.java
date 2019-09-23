package com.hellofit.kidozone.entity;

/***
 *  The entity of coordinate in the project
 *  To store the coordinate data of user or school
 *  @author Mingzhe Liu
 *  @version 1.0
 */

public class Coordinate {
    private double longtitude;
    private double latitude;

    public Coordinate() {
    }

    public Coordinate(double longtitude, double latitude) {
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
