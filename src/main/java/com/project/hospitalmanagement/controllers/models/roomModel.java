package com.project.hospitalmanagement.controllers.models;


import java.sql.Date;

public class roomModel {

    Integer RoomAllocationID;
    Integer RoomNumber;
    String RoomType;
    String RoomPatient;
    String RoomPatientGender;
    java.sql.Date RoomEntry;
    java.sql.Date RoomDeparture;
    Integer RoomID;
    String RoomStatus;
    String RoomPrice;



    public roomModel(Integer roomAllocationID, Integer roomNumber, String roomStatus, String roomType, String roomPatient, String roomPatientGender, java.sql.Date roomEntry, java.sql.Date roomDeparture) {
        this.RoomAllocationID = roomAllocationID;
        this.RoomStatus = roomStatus;
        this.RoomNumber = roomNumber;
        this.RoomType = roomType;
        this.RoomPatient = roomPatient;
        this.RoomPatientGender = roomPatientGender;
        this.RoomEntry = roomEntry;
        this.RoomDeparture = roomDeparture;
    }

    public roomModel(Integer roomNumber, String roomStatus, String roomType, String roomPatient, String roomPatientGender, java.sql.Date roomEntry, java.sql.Date roomDeparture) {
        this.RoomStatus = roomStatus;
        this.RoomNumber = roomNumber;
        this.RoomType = roomType;
        this.RoomPatient = roomPatient;
        this.RoomPatientGender = roomPatientGender;
        this.RoomEntry = roomEntry;
        this.RoomDeparture = roomDeparture;
    }


    public roomModel(Integer roomID, String roomType, String roomStatus, String roomPrice){
        this.RoomID = roomID;
        this.RoomType = roomType;
        this.RoomStatus = roomStatus;
        this.RoomPrice = roomPrice;
    }

    public Integer getRoomID() {
        return RoomID;
    }

    public String getRoomStatus() {
        return RoomStatus;
    }

    public String getRoomPrice() {
        return RoomPrice;
    }

    public Integer getRoomAllocationID() {
        return RoomAllocationID;
    }

    public Integer getRoomNumber() {
        return RoomNumber;
    }

    public String getRoomType() {
        return RoomType;
    }

    public String getRoomPatient() {
        return RoomPatient;
    }

    public String getRoomPatientGender() {
        return RoomPatientGender;
    }

    public java.sql.Date getRoomEntry() {
        return RoomEntry;
    }

    public java.sql.Date getRoomDeparture() {
        return RoomDeparture;
    }

    public void setRoomAllocationID(Integer roomAllocationID) {
        RoomAllocationID = roomAllocationID;
    }

    public void setRoomNumber(Integer roomNumber) {
        RoomNumber = roomNumber;
    }

    public void setRoomType(String roomType) {
        RoomType = roomType;
    }

    public void setRoomPatient(String roomPatient) {
        RoomPatient = roomPatient;
    }

    public void setRoomPatientGender(String roomPatientGender) {
        RoomPatientGender = roomPatientGender;
    }

    public void setRoomEntry(java.sql.Date roomEntry) {
        RoomEntry = roomEntry;
    }

    public void setRoomDeparture(Date roomDeparture) {
        RoomDeparture = roomDeparture;
    }

    public void setRoomID(Integer roomID) {
        RoomID = roomID;
    }

    public void setRoomStatus(String roomStatus) {
        RoomStatus = roomStatus;
    }

    public void setRoomPrice(String roomPrice) {
        RoomPrice = roomPrice;
    }
}

