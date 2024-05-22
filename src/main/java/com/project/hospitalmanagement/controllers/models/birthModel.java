package com.project.hospitalmanagement.controllers.models;

import javafx.scene.image.ImageView;

import java.util.Date;

public class birthModel {

    Integer BirthID;
    String BirthName;
    String BirthGender;
    java.sql.Date BirthDate;
    String BirthBloodGp;
    String BirthMother;
    String BirthFather;
    Integer BirthMobile;
    String BirthAddress;
    ImageView BirthPicture;

    public birthModel(ImageView imageView, Integer birthID, String birthName, String birthGender, java.sql.Date birthDate, String birthBloodGp, String birthMother, String birthFather, Integer birthMobile, String birthAddress) {
        this.BirthPicture = imageView;
        this.BirthID = birthID;
        this.BirthName = birthName;
        this.BirthGender = birthGender;
        this.BirthDate = birthDate;
        this.BirthBloodGp = birthBloodGp;
        this.BirthMother = birthMother;
        this.BirthFather = birthFather;
        this.BirthMobile = birthMobile;
        this.BirthAddress = birthAddress;
    }

    public birthModel(String birthName,java.sql.Date birthDate, String birthMother, String birthBloodGp) {
        this.BirthName = birthName;
        this.BirthDate = birthDate;
        this.BirthBloodGp = birthBloodGp;
        this.BirthMother = birthMother;
    }

    public Integer getBirthID() {
        return BirthID;
    }

    public String getBirthName() {
        return BirthName;
    }

    public String getBirthGender() {
        return BirthGender;
    }

    public Date getBirthDate() {
        return BirthDate;
    }

    public String getBirthBloodGp() {
        return BirthBloodGp;
    }

    public String getBirthMother() {
        return BirthMother;
    }

    public String getBirthFather() {
        return BirthFather;
    }

    public Integer getBirthMobile() {
        return BirthMobile;
    }

    public String getBirthAddress() {
        return BirthAddress;
    }

    public ImageView getBirthPicture() {
        return BirthPicture;
    }

    public void setBirthID(Integer birthID) {
        BirthID = birthID;
    }

    public void setBirthName(String birthName) {
        BirthName = birthName;
    }

    public void setBirthGender(String birthGender) {
        BirthGender = birthGender;
    }

    public void setBirthDate(java.sql.Date birthDate) {
        BirthDate = birthDate;
    }

    public void setBirthBloodGp(String birthBloodGp) {
        BirthBloodGp = birthBloodGp;
    }

    public void setBirthMother(String birthMother) {
        BirthMother = birthMother;
    }

    public void setBirthFather(String birthFather) {
        BirthFather = birthFather;
    }

    public void setBirthMobile(Integer birthMobile) {
        BirthMobile = birthMobile;
    }

    public void setBirthAddress(String birthAddress) {
        BirthAddress = birthAddress;
    }

    public void setBirthPicture(ImageView birthPicture) {
        BirthPicture = birthPicture;
    }
}
