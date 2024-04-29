package com.project.hospitalmanagement.controllers.models;


import java.sql.Time;

public class appointmentsModel {
    Integer ID;
    String PatientName, Gender, Email;
    Integer Mobile;
    java.sql.Date Date;
    Time Time;
    String Doctor, Injury;
    String Operation;

    public appointmentsModel(Integer ID, String patientName, String gender, String email, Integer mobile, java.sql.Date date, java.sql.Time time, String doctor, String injury) {
        this.ID = ID;
        this.PatientName = patientName;
        this.Gender = gender;
        this.Email = email;
        this.Mobile = mobile;
        this.Date = date;
        this.Time = time;
        this.Doctor = doctor;
        this.Injury = injury;
    }

    public appointmentsModel(Integer ID, String patientName, String doctor, java.sql.Date date, java.sql.Time time, String injury) {
        this.ID = ID;
        this.PatientName = patientName;
        this.Date = date;
        this.Time = time;
        this.Doctor = doctor;
        this.Injury = injury;
    }

    public Integer getID() {
        return ID;
    }

    public String getPatientName() {
        return PatientName;
    }

    public String getGender() {
        return Gender;
    }

    public String getEmail() {
        return Email;
    }

    public Integer getMobile() {
        return Mobile;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public java.sql.Time getTime() {
        return Time;
    }

    public String getDoctor() {
        return Doctor;
    }

    public String getInjury() {
        return Injury;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMobile(Integer mobile) {
        Mobile = mobile;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }

    public void setTime(java.sql.Time time) {
        Time = time;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public void setInjury(String injury) {
        Injury = injury;
    }

}

