package com.hellofit.kidozone.entity;

/***
 *  The entity of School in the project
 *  Basically this class will be create in the map page
 *  To create the entity of school which is queried from Web Server
 *  @author  Mingzhe Liu
 *  @version  1.0
 */

public class SchoolInfo {
    private String schoolName;
    private String schoolPhone;
    private String schoolAddress;
    private String schoolSuburb;
    private String schoolPostcode;
    private Coordinate schoolCoordinate;

    public SchoolInfo() {}

    public SchoolInfo(String schoolName, String schoolPhone, String schoolAddress, String schoolSuburb, String schoolPostcode, Coordinate schoolCoordinate) {
        this.schoolName = schoolName;
        this.schoolPhone = schoolPhone;
        this.schoolAddress = schoolAddress;
        this.schoolSuburb = schoolSuburb;
        this.schoolPostcode = schoolPostcode;
        this.schoolCoordinate = schoolCoordinate;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolSuburb() {
        return schoolSuburb;
    }

    public void setSchoolSuburb(String schoolSuburb) {
        this.schoolSuburb = schoolSuburb;
    }

    public String getSchoolPostcode() {
        return schoolPostcode;
    }

    public void setSchoolPostcode(String schoolPostcode) {
        this.schoolPostcode = schoolPostcode;
    }

    public Coordinate getSchoolCoordinate() {
        return schoolCoordinate;
    }

    public void setSchoolCoordinate(Coordinate schoolCoordinate) {
        this.schoolCoordinate = schoolCoordinate;
    }
}
