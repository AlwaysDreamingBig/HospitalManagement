package com.project.hospitalmanagement.controllers.models;


import java.util.Date;

public class callModel {

    Integer CallID;
    java.util.Date CallDate;
    String CallerName;
    String CallerGender;
    String CallerDriver;
    Integer AssignedAmbulance;
    java.sql.Date AmbulanceDeparture;
    java.sql.Date AmbulanceArrival;
    String CallerAddress;
    String CallerIssue;

    public callModel(Integer callID, Date callDate, String callerName, String callerGender, String callerDriver, Integer assignedAmbulance, java.sql.Date ambulanceDeparture, java.sql.Date ambulanceArrival, String callerAddress, String callerIssue) {
        this.CallID = callID;
        this.CallDate = callDate;
        this.CallerName = callerName;
        this.CallerGender = callerGender;
        this.CallerDriver = callerDriver;
        this.AssignedAmbulance = assignedAmbulance;
        this.AmbulanceDeparture = ambulanceDeparture;
        this.AmbulanceArrival = ambulanceArrival;
        this.CallerAddress = callerAddress;
        this.CallerIssue = callerIssue;
    }

    public Integer getCallID() {
        return CallID;
    }

    public Date getCallDate() {
        return CallDate;
    }

    public String getCallerName() {
        return CallerName;
    }

    public String getCallerGender() {
        return CallerGender;
    }

    public String getCallerDriver() {
        return CallerDriver;
    }

    public Integer getAssignedAmbulance() {
        return AssignedAmbulance;
    }

    public Date getAmbulanceDeparture() {
        return AmbulanceDeparture;
    }

    public Date getAmbulanceArrival() {
        return AmbulanceArrival;
    }

    public String getCallerAddress() {
        return CallerAddress;
    }

    public String getCallerIssue() {
        return CallerIssue;
    }

    public void setCallID(Integer callID) {
        CallID = callID;
    }

    public void setCallDate(Date callDate) {
        CallDate = callDate;
    }

    public void setCallerName(String callerName) {
        CallerName = callerName;
    }

    public void setCallerGender(String callerGender) {
        CallerGender = callerGender;
    }

    public void setCallerDriver(String callerDriver) {
        CallerDriver = callerDriver;
    }

    public void setAssignedAmbulance(Integer assignedAmbulance) {
        AssignedAmbulance = assignedAmbulance;
    }

    public void setAmbulanceDeparture(java.sql.Date ambulanceDeparture) {
        AmbulanceDeparture = ambulanceDeparture;
    }

    public void setAmbulanceArrival(java.sql.Date ambulanceArrival) {
        AmbulanceArrival = ambulanceArrival;
    }

    public void setCallerAddress(String callerAddress) {
        CallerAddress = callerAddress;
    }

    public void setCallerIssue(String callerIssue) {
        CallerIssue = callerIssue;
    }
}
