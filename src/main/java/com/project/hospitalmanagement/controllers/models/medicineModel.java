package com.project.hospitalmanagement.controllers.models;

import javafx.scene.image.ImageView;

import java.sql.Date;

public class medicineModel {

    ImageView MedicinePicture;
    Integer MedicineID;
    String MedicineName;
    String MedicineCategory;
    String MedicineCompany;
    Date MedicinePurchaseDate;
    Integer MedicinePrice;
    Date MedicineExpiringDate;
    Integer MedicineStock;
    String MedicineStatus;

    public medicineModel(ImageView medicinePicture, Integer medicineID, String medicineName, String medicineCategory, String medicineCompany, Date medicinePurchaseDate, Integer medicinePrice, Date medicineExpiringDate, Integer medicineStock, String medicineStatus) {
        this.MedicinePicture = medicinePicture;
        this.MedicineID = medicineID;
        this.MedicineName = medicineName;
        this.MedicineCategory = medicineCategory;
        this.MedicineCompany = medicineCompany;
        this.MedicinePurchaseDate = medicinePurchaseDate;
        this.MedicinePrice = medicinePrice;
        this.MedicineExpiringDate = medicineExpiringDate;
        this.MedicineStock = medicineStock;
        this.MedicineStatus = medicineStatus;
    }

    public ImageView getMedicinePicture() {
        return MedicinePicture;
    }

    public Integer getMedicineID() {
        return MedicineID;
    }

    public String getMedicineName() {
        return MedicineName;
    }

    public String getMedicineCategory() {
        return MedicineCategory;
    }

    public String getMedicineCompany() {
        return MedicineCompany;
    }

    public Date getMedicinePurchaseDate() {
        return MedicinePurchaseDate;
    }

    public Integer getMedicinePrice() {
        return MedicinePrice;
    }

    public Date getMedicineExpiringDate() {
        return MedicineExpiringDate;
    }

    public Integer getMedicineStock() {
        return MedicineStock;
    }

    public String getMedicineStatus() {
        return MedicineStatus;
    }

    public void setMedicinePicture(ImageView medicinePicture) {
        MedicinePicture = medicinePicture;
    }

    public void setMedicineID(Integer medicineID) {
        MedicineID = medicineID;
    }

    public void setMedicineName(String medicineName) {
        MedicineName = medicineName;
    }

    public void setMedicineCategory(String medicineCategory) {
        MedicineCategory = medicineCategory;
    }

    public void setMedicineCompany(String medicineCompany) {
        MedicineCompany = medicineCompany;
    }

    public void setMedicinePurchaseDate(Date medicinePurchaseDate) {
        MedicinePurchaseDate = medicinePurchaseDate;
    }

    public void setMedicinePrice(Integer medicinePrice) {
        MedicinePrice = medicinePrice;
    }

    public void setMedicineExpiringDate(Date medicineExpiringDate) {
        MedicineExpiringDate = medicineExpiringDate;
    }

    public void setMedicineStock(Integer medicineStock) {
        MedicineStock = medicineStock;
    }

    public void setMedicineStatus(String medicineStatus) {
        MedicineStatus = medicineStatus;
    }
}
