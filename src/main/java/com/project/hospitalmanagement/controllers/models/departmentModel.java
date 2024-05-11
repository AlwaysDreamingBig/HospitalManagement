package com.project.hospitalmanagement.controllers.models;

import java.sql.Date;

public class departmentModel {

    Integer DepartmentID;
    String DepartmentName;
    String DepartmentHead;
    String DepartmentStatus;
    Integer DepartmentSize;
    String DepartmentDescription;
    java.sql.Date DepartmentCreation;

    public departmentModel(Integer departmentID, String departmentName, String departmentHead, String departmentStatus, Integer departmentSize, String departmentDescription, Date departmentCreation) {
        this.DepartmentID = departmentID;
        this.DepartmentName = departmentName;
        this.DepartmentHead = departmentHead;
        this.DepartmentStatus = departmentStatus;
        this.DepartmentSize = departmentSize;
        this.DepartmentDescription = departmentDescription;
        this.DepartmentCreation = departmentCreation;
    }

    public departmentModel(Integer departmentID, String departmentName, String departmentHead){
        this.DepartmentID = departmentID;
        this.DepartmentName = departmentName;
        this.DepartmentHead = departmentHead;
    }

    public departmentModel( String departmentName, String departmentHead){
        this.DepartmentName = departmentName;
        this.DepartmentHead = departmentHead;
    }

    public Integer getDepartmentID() {
        return DepartmentID;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public String getDepartmentHead() {
        return DepartmentHead;
    }

    public String getDepartmentStatus() {
        return DepartmentStatus;
    }

    public Integer getDepartmentSize() {
        return DepartmentSize;
    }

    public String getDepartmentDescription() {
        return DepartmentDescription;
    }

    public Date getDepartmentCreation() {
        return DepartmentCreation;
    }

    public void setDepartmentID(Integer departmentID) {
        DepartmentID = departmentID;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public void setDepartmentHead(String departmentHead) {
        DepartmentHead = departmentHead;
    }

    public void setDepartmentStatus(String departmentStatus) {
        DepartmentStatus = departmentStatus;
    }

    public void setDepartmentSize(Integer departmentSize) {
        DepartmentSize = departmentSize;
    }

    public void setDepartmentDescription(String departmentDescription) {
        DepartmentDescription = departmentDescription;
    }
}
