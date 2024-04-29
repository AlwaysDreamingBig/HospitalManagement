package com.project.hospitalmanagement.controllers.models;


import javafx.scene.image.ImageView;

import java.sql.Date;

public class staffModel {

    Integer StaffID;
    String StaffName;
    String StaffGender;
    String StaffEmail;
    Integer StaffMobile;
    java.sql.Date StaffEntryDate;
    java.sql.Date StaffEndDate;
    String StaffDepartment;
    String StaffFunction;
    String StaffDegree;
    String StaffAddress;
    String StaffSupervisor;
    String StaffAccountStatus;
    Integer StaffSalary;
    ImageView StaffPicture;

    public staffModel(ImageView staffPicture, Integer staffID, String staffName, String staffFunction, String staffDepartment, String staffEmail, Integer staffMobile, java.sql.Date staffEntryDate, java.sql.Date staffEndDate, String staffSupervisor, Integer staffSalary, String staffAccountStatus, String staffGender, String staffDegree, String staffAddress) {
        this.StaffPicture = staffPicture;
        this.StaffID = staffID;
        this.StaffName = staffName;
        this.StaffGender = staffGender;
        this.StaffEmail = staffEmail;
        this.StaffMobile = staffMobile;
        this.StaffEntryDate = staffEntryDate;
        this.StaffEndDate = staffEndDate;
        this.StaffDepartment = staffDepartment;
        this.StaffFunction = staffFunction;
        this.StaffDegree = staffDegree;
        this.StaffAddress = staffAddress;
        this.StaffSupervisor = staffSupervisor;
        this.StaffAccountStatus = staffAccountStatus;
        this.StaffSalary = staffSalary;
    }

    public staffModel(ImageView staffPicture, Integer staffID, String staffName, String staffFunction, String staffDepartment, String staffEmail, Integer staffMobile, java.sql.Date staffEntryDate, java.sql.Date staffEndDate, String staffSupervisor, Integer staffSalary) {
        this.StaffPicture = staffPicture;
        this.StaffID = staffID;
        this.StaffName = staffName;
        this.StaffFunction = staffFunction;
        this.StaffEmail = staffEmail;
        this.StaffMobile = staffMobile;
        this.StaffEntryDate = staffEntryDate;
        this.StaffEndDate = staffEndDate;
        this.StaffDepartment = staffDepartment;
        this.StaffSupervisor = staffSupervisor;
        this.StaffSalary = staffSalary;

    }

    public ImageView getStaffPicture() {
        return StaffPicture;
    }

    public Integer getStaffID() {
        return StaffID;
    }

    public String getStaffName() {
        return StaffName;
    }

    public String getStaffGender() {
        return StaffGender;
    }

    public String getStaffEmail() {
        return StaffEmail;
    }

    public Integer getStaffMobile() {
        return StaffMobile;
    }

    public java.sql.Date getStaffEntryDate() {
        return StaffEntryDate;
    }

    public java.sql.Date getStaffEndDate() {
        return StaffEndDate;
    }

    public String getStaffDepartment() {
        return StaffDepartment;
    }

    public String getStaffFunction() {
        return StaffFunction;
    }

    public String getStaffDegree() {
        return StaffDegree;
    }

    public String getStaffAddress() {
        return StaffAddress;
    }

    public String getStaffSupervisor() {
        return StaffSupervisor;
    }

    public String getStaffAccountStatus() {
        return StaffAccountStatus;
    }

    public Integer getStaffSalary() {
        return StaffSalary;
    }

    public void setStaffPicture(ImageView staffPicture) {
        StaffPicture = staffPicture;
    }

    public void setStaffID(Integer staffID) {
        StaffID = staffID;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public void setStaffGender(String staffGender) {
        StaffGender = staffGender;
    }

    public void setStaffEmail(String staffEmail) {
        StaffEmail = staffEmail;
    }

    public void setStaffMobile(Integer staffMobile) {
        StaffMobile = staffMobile;
    }

    public void setStaffEntryDate(Date staffEntryDate) {
        StaffEntryDate = staffEntryDate;
    }

    public void setStaffEndDate(java.sql.Date staffEndDate) {
        StaffEndDate = staffEndDate;
    }

    public void setStaffDepartment(String staffDepartment) {
        StaffDepartment = staffDepartment;
    }

    public void setStaffFunction(String staffFunction) {
        StaffFunction = staffFunction;
    }

    public void setStaffDegree(String staffDegree) {
        StaffDegree = staffDegree;
    }

    public void setStaffAddress(String staffAddress) {
        StaffAddress = staffAddress;
    }

    public void setStaffSupervisor(String staffSupervisor) {
        StaffSupervisor = staffSupervisor;
    }

    public void setStaffAccountStatus(String staffAccountStatus) {
        StaffAccountStatus = staffAccountStatus;
    }

    public void setStaffSalary(Integer staffSalary) {
        StaffSalary = staffSalary;
    }
}