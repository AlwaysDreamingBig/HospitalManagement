package com.project.hospitalmanagement.controllers.models;


import java.util.Date;

public class ambulanceModel {

    Integer AmbulanceID;
    String AmbulanceManufacturer;
    String AmbulanceDesignation;
    Integer AmbulanceNumber;
    java.sql.Date AmbulanceMadeYear;
    String AmbulanceSeller;
    String AmbulanceContract;
    String AmbulanceDriver;
    Integer AmbulanceWarranty;

    public ambulanceModel(Integer ambulanceID, Integer ambulanceNumber, String ambulanceManufacturer, String ambulanceDesignation, Integer ambulanceWarranty, String ambulanceSeller, java.sql.Date ambulanceMadeYear, String ambulanceDriver, String ambulanceContract) {
        this.AmbulanceID = ambulanceID;
        this.AmbulanceManufacturer = ambulanceManufacturer;
        this.AmbulanceDesignation = ambulanceDesignation;
        this.AmbulanceNumber = ambulanceNumber;
        this.AmbulanceMadeYear = ambulanceMadeYear;
        this.AmbulanceSeller = ambulanceSeller;
        this.AmbulanceContract = ambulanceContract;
        this.AmbulanceDriver = ambulanceDriver;
        this.AmbulanceWarranty = ambulanceWarranty;
    }

    public Integer getAmbulanceID() {
        return AmbulanceID;
    }

    public String getAmbulanceManufacturer() {
        return AmbulanceManufacturer;
    }

    public String getAmbulanceDesignation() {
        return AmbulanceDesignation;
    }

    public Integer getAmbulanceNumber() {
        return AmbulanceNumber;
    }

    public Date getAmbulanceMadeYear() {
        return AmbulanceMadeYear;
    }

    public String getAmbulanceSeller() {
        return AmbulanceSeller;
    }

    public String getAmbulanceContract() {
        return AmbulanceContract;
    }

    public String getAmbulanceDriver() {
        return AmbulanceDriver;
    }

    public Integer getAmbulanceWarranty() {
        return AmbulanceWarranty;
    }

    public void setAmbulanceID(Integer ambulanceID) {
        AmbulanceID = ambulanceID;
    }

    public void setAmbulanceManufacturer(String ambulanceManufacturer) {
        AmbulanceManufacturer = ambulanceManufacturer;
    }

    public void setAmbulanceDesignation(String ambulanceDesignation) {
        AmbulanceDesignation = ambulanceDesignation;
    }

    public void setAmbulanceNumber(Integer ambulanceNumber) {
        AmbulanceNumber = ambulanceNumber;
    }

    public void setAmbulanceMadeYear(java.sql.Date ambulanceMadeYear) {
        AmbulanceMadeYear = ambulanceMadeYear;
    }

    public void setAmbulanceSeller(String ambulanceSeller) {
        AmbulanceSeller = ambulanceSeller;
    }

    public void setAmbulanceContract(String ambulanceContract) {
        AmbulanceContract = ambulanceContract;
    }

    public void setAmbulanceDriver(String ambulanceDriver) {
        AmbulanceDriver = ambulanceDriver;
    }

    public void setAmbulanceWarranty(Integer ambulanceWarranty) {
        AmbulanceWarranty = ambulanceWarranty;
    }
}

