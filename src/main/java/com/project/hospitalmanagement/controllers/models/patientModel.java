package com.project.hospitalmanagement.controllers.models;

import javafx.scene.image.ImageView;

import java.sql.Date;

public class patientModel {

    ImageView PatientPicture;
    Integer PatientID;
    String PatientName;
    String PatientGender;
    Date PatientBirthDate;
    String PatientEmail;
    Integer PatientMobile;
    String PatientBloodGrp;
    String PatientAssignedDr;
    String PatientTreatment;
    String PatientAddress;
    String PatientAccountStatus;

    public patientModel(ImageView patientPicture, Integer patientID, String patientName, String patientGender, Date patientBirthDate, String patientEmail, Integer patientMobile, String patientBloodGrp, String patientAssignedDr, String patientTreatment, String patientAddress, String patientAccountStatus) {
        this.PatientPicture = patientPicture;
        this.PatientID = patientID;
        this.PatientName = patientName;
        this.PatientGender = patientGender;
        this.PatientBirthDate = patientBirthDate;
        this.PatientEmail = patientEmail;
        this.PatientMobile = patientMobile;
        this.PatientBloodGrp = patientBloodGrp;
        this.PatientAssignedDr = patientAssignedDr;
        this.PatientTreatment = patientTreatment;
        this.PatientAddress = patientAddress;
        this.PatientAccountStatus = patientAccountStatus;
    }

    public patientModel(ImageView patientPicture, Integer patientID, String patientName, String patientGender, Date patientBirthDate, String patientEmail, Integer patientMobile, String patientBloodGrp, String patientAssignedDr, String patientTreatment) {
        this.PatientPicture = patientPicture;
        this.PatientID = patientID;
        this.PatientName = patientName;
        this.PatientGender = patientGender;
        this.PatientBirthDate = patientBirthDate;
        this.PatientEmail = patientEmail;
        this.PatientMobile = patientMobile;
        this.PatientBloodGrp = patientBloodGrp;
        this.PatientAssignedDr = patientAssignedDr;
        this.PatientTreatment = patientTreatment;
    }

    public ImageView getPatientPicture() {
        return PatientPicture;
    }

    public Integer getPatientID() {
        return PatientID;
    }

    public String getPatientName() {
        return PatientName;
    }

    public String getPatientGender() {
        return PatientGender;
    }

    public Date getPatientBirthDate() {
        return PatientBirthDate;
    }

    public String getPatientEmail() {
        return PatientEmail;
    }

    public Integer getPatientMobile() {
        return PatientMobile;
    }

    public String getPatientBloodGrp() {
        return PatientBloodGrp;
    }

    public String getPatientAssignedDr() {
        return PatientAssignedDr;
    }

    public String getPatientTreatment() {
        return PatientTreatment;
    }

    public String getPatientAddress() {
        return PatientAddress;
    }

    public String getPatientAccountStatus() {
        return PatientAccountStatus;
    }

    public void setPatientPicture(ImageView patientPicture) {
        PatientPicture = patientPicture;
    }

    public void setPatientID(Integer patientID) {
        PatientID = patientID;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public void setPatientGender(String patientGender) {
        PatientGender = patientGender;
    }

    public void setPatientBirthDate(Date patientBirthDate) {
        PatientBirthDate = patientBirthDate;
    }

    public void setPatientEmail(String patientEmail) {
        PatientEmail = patientEmail;
    }

    public void setPatientMobile(Integer patientMobile) {
        PatientMobile = patientMobile;
    }

    public void setPatientBloodGrp(String patientBloodGrp) {
        PatientBloodGrp = patientBloodGrp;
    }

    public void setPatientAssignedDr(String patientAssignedDr) {
        PatientAssignedDr = patientAssignedDr;
    }

    public void setPatientTreatment(String patientTreatment) {
        PatientTreatment = patientTreatment;
    }

    public void setPatientAddress(String patientAddress) {
        PatientAddress = patientAddress;
    }

    public void setPatientAccountStatus(String patientAccountStatus) {
        PatientAccountStatus = patientAccountStatus;
    }
}

