package com.project.hospitalmanagement.controllers.models;


import java.util.Date;

public class billModel {
    Integer BillID;
    Integer BillAdmissionID;
    String BillStatus;
    String BillPatientName;
    java.sql.Date BillDate;
    Integer BillDiscount;
    Integer BillTotalPaid;

    public billModel(Integer billID, Integer billAdmissionID, String billStatus, String billPatientName, java.sql.Date billDate, Integer billDiscount, Integer billTotalPaid) {
        this.BillID = billID;
        this.BillAdmissionID = billAdmissionID;
        this.BillStatus = billStatus;
        this.BillPatientName = billPatientName;
        this.BillDate = billDate;
        this.BillDiscount = billDiscount;
        this.BillTotalPaid = billTotalPaid;
    }

    public Integer getBillID() {
        return BillID;
    }

    public Integer getBillAdmissionID() {
        return BillAdmissionID;
    }

    public String getBillStatus() {
        return BillStatus;
    }

    public String getBillPatientName() {
        return BillPatientName;
    }

    public Date getBillDate() {
        return BillDate;
    }

    public Integer getBillDiscount() {
        return BillDiscount;
    }

    public Integer getBillTotalPaid() {
        return BillTotalPaid;
    }

    public void setBillID(Integer billID) {
        BillID = billID;
    }

    public void setBillAdmissionID(Integer billAdmissionID) {
        BillAdmissionID = billAdmissionID;
    }

    public void setBillStatus(String billStatus) {
        BillStatus = billStatus;
    }

    public void setBillPatientName(String billPatientName) {
        BillPatientName = billPatientName;
    }

    public void setBillDate(java.sql.Date billDate) {
        BillDate = billDate;
    }

    public void setBillDiscount(Integer billDiscount) {
        BillDiscount = billDiscount;
    }

    public void setBillTotalPaid(Integer billTotalPaid) {
        BillTotalPaid = billTotalPaid;
    }
}

