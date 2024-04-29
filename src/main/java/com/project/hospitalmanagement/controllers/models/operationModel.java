package com.project.hospitalmanagement.controllers.models;

import java.sql.Date;
import java.sql.Time;

public class operationModel {

    Integer OperationID;
    String OperationPatient;
    String OperationChiefDoctor;
    String 	OperationDoctorTeam;
    Date OperationDate;
    Time OperationTime;
    String OperationPreviousDiagnostic;

    public operationModel(Integer operationID, String operationPatient, String operationChiefDoctor, String operationDoctorTeam, Date operationDate, Time operationTime, String operationPreviousDiagnostic) {
        this.OperationID = operationID;
        this.OperationPatient = operationPatient;
        this.OperationChiefDoctor = operationChiefDoctor;
        this.OperationDoctorTeam = operationDoctorTeam;
        this.OperationDate = operationDate;
        this.OperationTime = operationTime;
        this.OperationPreviousDiagnostic = operationPreviousDiagnostic;
    }

    public Integer getOperationID() {
        return OperationID;
    }

    public void setOperationID(Integer operationID) {
        OperationID = operationID;
    }

    public String getOperationPatient() {
        return OperationPatient;
    }

    public void setOperationPatient(String operationPatient) {
        OperationPatient = operationPatient;
    }

    public String getOperationChiefDoctor() {
        return OperationChiefDoctor;
    }

    public void setOperationChiefDoctor(String operationChiefDoctor) {
        OperationChiefDoctor = operationChiefDoctor;
    }

    public String getOperationDoctorTeam() {
        return OperationDoctorTeam;
    }

    public void setOperationDoctorTeam(String operationDoctorTeam) {
        OperationDoctorTeam = operationDoctorTeam;
    }

    public Date getOperationDate() {
        return OperationDate;
    }

    public void setOperationDate(Date operationDate) {
        OperationDate = operationDate;
    }

    public Time getOperationTime() {
        return OperationTime;
    }

    public void setOperationTime(Time operationTime) {
        OperationTime = operationTime;
    }

    public String getOperationPreviousDiagnostic() {
        return OperationPreviousDiagnostic;
    }

    public void setOperationPreviousDiagnostic(String operationPreviousDiagnostic) {
        OperationPreviousDiagnostic = operationPreviousDiagnostic;
    }
}
