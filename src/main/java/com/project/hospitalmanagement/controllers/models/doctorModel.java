package com.project.hospitalmanagement.controllers.models;


import javafx.scene.image.ImageView;

import java.util.Date;

public class doctorModel {

    Integer DoctorID, DoctorMNID;
    String DoctorName;
    String DoctorGender;
    String DoctorEmail;
    Integer DoctorMobile;
    java.sql.Date DoctorEntryDate;
    java.sql.Date DoctorEndDate;
    String DoctorDepartment;
    String DoctorSpecialization;
    String DoctorDegree;
    String DoctorAddress;
    String DoctorSupervisor;
    String DoctorAccountStatus;
    Integer DoctorSalary;

    String DoctorAvailability;
    ImageView DoctorPicture;


    public doctorModel(ImageView doctorPicture, Integer doctorID, Integer doctorMNID, String doctorName, String doctorSpecialization, String doctorDepartment, String doctorEmail, Integer doctorMobile, java.sql.Date doctorEntryDate, java.sql.Date doctorEndDate, String doctorAccountStatus, Integer doctorSalary, String doctorGender, String doctorSupervisor, String doctorDegree, String doctorAddress) {
        this.DoctorPicture = doctorPicture;
        this.DoctorID = doctorID;
        this.DoctorMNID = doctorMNID;
        this.DoctorName = doctorName;
        this.DoctorGender = doctorGender;
        this.DoctorEmail = doctorEmail;
        this.DoctorMobile = doctorMobile;
        this.DoctorEntryDate = doctorEntryDate;
        this.DoctorEndDate = doctorEndDate;
        this.DoctorDepartment = doctorDepartment;
        this.DoctorSpecialization = doctorSpecialization;
        this.DoctorDegree = doctorDegree;
        this.DoctorAddress = doctorAddress;
        this.DoctorSupervisor = doctorSupervisor;
        this.DoctorAccountStatus = doctorAccountStatus;
        this.DoctorSalary = doctorSalary;
    }

    public doctorModel(ImageView doctorPicture, Integer doctorID, String doctorName, String doctorSpecialization, String doctorDepartment, String doctorEmail, Integer doctorMobile, java.sql.Date doctorEntryDate, java.sql.Date doctorEndDate, String doctorAccountStatus, Integer doctorSalary) {
        this.DoctorPicture = doctorPicture;
        this.DoctorID = doctorID;
        this.DoctorName = doctorName;
        this.DoctorEmail = doctorEmail;
        this.DoctorMobile = doctorMobile;
        this.DoctorEntryDate = doctorEntryDate;
        this.DoctorEndDate = doctorEndDate;
        this.DoctorDepartment = doctorDepartment;
        this.DoctorSpecialization = doctorSpecialization;
        this.DoctorAccountStatus = doctorAccountStatus;
        this.DoctorSalary = doctorSalary;

    }

    public doctorModel(ImageView doctorPicture, String doctorName, String doctorAvailability){
        this.DoctorPicture = doctorPicture;
        this.DoctorName = doctorName;
        this.DoctorAvailability = doctorAvailability;
    }


    public ImageView getDoctorPicture() {
        return DoctorPicture;
    }

    public Integer getDoctorID() {
        return DoctorID;
    }

    public Integer getDoctorMNID() {
        return DoctorMNID;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public String getDoctorGender() {
        return DoctorGender;
    }

    public String getDoctorEmail() {
        return DoctorEmail;
    }

    public Integer getDoctorMobile() {
        return DoctorMobile;
    }

    public java.sql.Date getDoctorEntryDate() {
        return DoctorEntryDate;
    }

    public Date getDoctorEndDate() {
        return DoctorEndDate;
    }

    public String getDoctorDepartment() {
        return DoctorDepartment;
    }

    public String getDoctorSpecialization() {
        return DoctorSpecialization;
    }

    public String getDoctorDegree() {
        return DoctorDegree;
    }


    public String getDoctorAvailability() {
        return DoctorAvailability;
    }

    public void setDoctorAvailability(String doctorAvailability) {
        DoctorAvailability = doctorAvailability;
    }

    public String getDoctorAddress() {
        return DoctorAddress;
    }

    public String getDoctorSupervisor() {
        return DoctorSupervisor;
    }

    public String getDoctorAccountStatus() {
        return DoctorAccountStatus;
    }

    public Integer getDoctorSalary() {
        return DoctorSalary;
    }

    public void setDoctorPicture(ImageView doctorPicture) {
        DoctorPicture = doctorPicture;
    }

    public void setDoctorID(Integer doctorID) {
        DoctorID = doctorID;
    }

    public void setDoctorMNID(Integer doctorMNID) {
        DoctorMNID = doctorMNID;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public void setDoctorGender(String doctorGender) {
        DoctorGender = doctorGender;
    }

    public void setDoctorEmail(String doctorEmail) {
        DoctorEmail = doctorEmail;
    }

    public void setDoctorMobile(Integer doctorMobile) {
        DoctorMobile = doctorMobile;
    }

    public void setDoctorEntryDate(java.sql.Date doctorEntryDate) {
        DoctorEntryDate = doctorEntryDate;
    }

    public void setDoctorEndDate(java.sql.Date doctorEndDate) {
        DoctorEndDate = doctorEndDate;
    }

    public void setDoctorDepartment(String doctorDepartment) {
        DoctorDepartment = doctorDepartment;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        DoctorSpecialization = doctorSpecialization;
    }

    public void setDoctorDegree(String doctorDegree) {
        DoctorDegree = doctorDegree;
    }

    public void setDoctorAddress(String doctorAddress) {
        DoctorAddress = doctorAddress;
    }

    public void setDoctorSupervisor(String doctorSupervisor) {
        DoctorSupervisor = doctorSupervisor;
    }

    public void setDoctorAccountStatus(String doctorAccountStatus) {
        DoctorAccountStatus = doctorAccountStatus;
    }

    public void setDoctorSalary(Integer doctorSalary) {
        DoctorSalary = doctorSalary;
    }
}
