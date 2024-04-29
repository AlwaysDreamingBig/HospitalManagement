package com.project.hospitalmanagement.controllers.models;


import java.sql.Date;

public class inventoryModel {

    Integer InventoryID;
    String InventoryObjectName;
    String InventoryObjectCategory;
    String InventoryObjectStock;
    java.sql.Date InventoryObjectPurchaseDate;
    String InventoryObjectDescription;
    Integer InventoryObjectPrice;
    String InventoryObjectState;

    public inventoryModel(Integer inventoryID, String inventoryObjectName, String inventoryObjectCategory, String inventoryObjectStock, Date inventoryObjectPurchaseDate, String inventoryObjectDescription, Integer inventoryObjectPrice, String inventoryObjectState) {
        this.InventoryID = inventoryID;
        this.InventoryObjectName = inventoryObjectName;
        this.InventoryObjectCategory = inventoryObjectCategory;
        this.InventoryObjectStock = inventoryObjectStock;
        this.InventoryObjectPurchaseDate = inventoryObjectPurchaseDate;
        this.InventoryObjectDescription = inventoryObjectDescription;
        this.InventoryObjectPrice = inventoryObjectPrice;
        this.InventoryObjectState = inventoryObjectState;
    }

    public Integer getInventoryID() {
        return InventoryID;
    }

    public String getInventoryObjectName() {
        return InventoryObjectName;
    }

    public String getInventoryObjectCategory() {
        return InventoryObjectCategory;
    }

    public String getInventoryObjectStock() {
        return InventoryObjectStock;
    }

    public Date getInventoryObjectPurchaseDate() {
        return InventoryObjectPurchaseDate;
    }

    public String getInventoryObjectDescription() {
        return InventoryObjectDescription;
    }

    public Integer getInventoryObjectPrice() {
        return InventoryObjectPrice;
    }

    public String getInventoryObjectState() {
        return InventoryObjectState;
    }

    public void setInventoryID(Integer inventoryID) {
        InventoryID = inventoryID;
    }

    public void setInventoryObjectName(String inventoryObjectName) {
        InventoryObjectName = inventoryObjectName;
    }

    public void setInventoryObjectCategory(String inventoryObjectCategory) {
        InventoryObjectCategory = inventoryObjectCategory;
    }

    public void setInventoryObjectStock(String inventoryObjectStock) {
        InventoryObjectStock = inventoryObjectStock;
    }

    public void setInventoryObjectPurchaseDate(Date inventoryObjectPurchaseDate) {
        InventoryObjectPurchaseDate = inventoryObjectPurchaseDate;
    }

    public void setInventoryObjectDescription(String inventoryObjectDescription) {
        InventoryObjectDescription = inventoryObjectDescription;
    }

    public void setInventoryObjectPrice(Integer inventoryObjectPrice) {
        InventoryObjectPrice = inventoryObjectPrice;
    }

    public void setInventoryObjectState(String inventoryObjectState) {
        InventoryObjectState = inventoryObjectState;
    }
}

