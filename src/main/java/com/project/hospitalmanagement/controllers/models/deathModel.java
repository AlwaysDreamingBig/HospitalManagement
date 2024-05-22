package com.project.hospitalmanagement.controllers.models;

import javafx.scene.image.ImageView;

import java.util.Date;

public class deathModel {

    Integer DeathID;
    String DeathName;
    String DeathGender;
    java.sql.Date DeathDate;
    String DeathInWatch;
    String DeathMother;
    String DeathFather;
    Integer DeathMobile;
    String DeathNature;
    ImageView DeathPicture;

    public deathModel(ImageView imageView, Integer deathID, String deathName, String deathGender, java.sql.Date deathDate, String deathInWatch, String deathMother, String deathFather, Integer deathMobile, String deathNature) {
        this.DeathPicture = imageView;
        this.DeathID = deathID;
        this.DeathName = deathName;
        this.DeathGender = deathGender;
        this.DeathDate = deathDate;
        this.DeathInWatch = deathInWatch;
        this.DeathMother = deathMother;
        this.DeathFather = deathFather;
        this.DeathMobile = deathMobile;
        this.DeathNature = deathNature;
    }

    public deathModel(String deathName, java.sql.Date deathDate, String deathMother, String deathInWatch) {
        this.DeathName = deathName;
        this.DeathDate = deathDate;
        this.DeathInWatch = deathInWatch;
        this.DeathMother = deathMother;
    }

    public Integer getDeathID() {
        return DeathID;
    }

    public String getDeathName() {
        return DeathName;
    }

    public String getDeathGender() {
        return DeathGender;
    }

    public Date getDeathDate() {
        return DeathDate;
    }

    public String getDeathMother() {
        return DeathMother;
    }

    public String getDeathFather() {
        return DeathFather;
    }

    public Integer getDeathMobile() {
        return DeathMobile;
    }

    public ImageView getDeathPicture() {
        return DeathPicture;
    }

    public void setDeathID(Integer deathID) {
        DeathID = deathID;
    }

    public void setDeathName(String deathName) {
        DeathName = deathName;
    }

    public void setDeathGender(String deathGender) {
        DeathGender = deathGender;
    }

    public String getDeathInWatch() {
        return DeathInWatch;
    }

    public String getDeathNature() {
        return DeathNature;
    }

    public void setDeathDate(java.sql.Date deathDate) {
        DeathDate = deathDate;
    }

    public void setDeathMother(String deathMother) {
        DeathMother = deathMother;
    }

    public void setDeathFather(String deathFather) {
        DeathFather = deathFather;
    }

    public void setDeathMobile(Integer deathMobile) {
        DeathMobile = deathMobile;
    }

    public void setDeathInWatch(String deathInWatch) {
        DeathInWatch = deathInWatch;
    }

    public void setDeathNature(String deathNature) {
        DeathNature = deathNature;
    }

    public void setDeathPicture(ImageView deathPicture) {
        DeathPicture = deathPicture;
    }
}

