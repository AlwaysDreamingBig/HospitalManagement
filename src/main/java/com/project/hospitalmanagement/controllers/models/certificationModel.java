package com.project.hospitalmanagement.controllers.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class certificationModel {

    Integer CertificationID;
    String CertificationName;
    String CertificationOrganization;
    Integer CertificationValidityPeriod;
    String CertificationDescription;
    ImageView  CertificationImage;

    public certificationModel(ImageView certificationImage, Integer certificationID, String certificationName, String certificationOrganization, Integer certificationValidityPeriod, String certificationDescription) {
        this.CertificationID = certificationID;
        this.CertificationName = certificationName;
        this.CertificationOrganization = certificationOrganization;
        this.CertificationValidityPeriod = certificationValidityPeriod;
        this.CertificationDescription = certificationDescription;
        this.CertificationImage = certificationImage;
    }

    public certificationModel(Integer certificationID, String certificationName, String certificationOrganization, Integer certificationValidityPeriod, String certificationDescription) {
        this.CertificationID = certificationID;
        this.CertificationName = certificationName;
        this.CertificationOrganization = certificationOrganization;
        this.CertificationValidityPeriod = certificationValidityPeriod;
        this.CertificationDescription = certificationDescription;

    }


    public Image getCertificationImage() {
        return CertificationImage.getImage();
    }
    public Integer getCertificationID() {
        return CertificationID;
    }

    public String getCertificationName() {
        return CertificationName;
    }

    public ImageView getCertificationImagevIEW() {
        return CertificationImage;
    }

    public String getCertificationOrganization() {
        return CertificationOrganization;
    }

    public Integer getCertificationValidityPeriod() {
        return CertificationValidityPeriod;
    }

    public String getCertificationDescription() {
        return CertificationDescription;
    }

    public void setCertificationID(Integer certificationID) {
        CertificationID = certificationID;
    }

    public void setCertificationName(String certificationName) {
        CertificationName = certificationName;
    }

    public void setCertificationOrganization(String certificationOrganization) {
        CertificationOrganization = certificationOrganization;
    }

    public void setCertificationValidityPeriod(Integer certificationValidityPeriod) {
        CertificationValidityPeriod = certificationValidityPeriod;
    }

    public void setCertificationDescription(String certificationDescription) {
        CertificationDescription = certificationDescription;
    }

    public void setCertificationImage(ImageView certificationImage) {
        CertificationImage = certificationImage;
    }
}
