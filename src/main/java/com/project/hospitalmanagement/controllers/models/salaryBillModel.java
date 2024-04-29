package com.project.hospitalmanagement.controllers.models;


import java.sql.Date;

public class salaryBillModel {
    Integer SalaryBillID;
    Integer SalaryBillPaymentID;
    String SalaryBillStatus;
    String SalaryBillStaffName;
    java.sql.Date SalaryBillDate;
    Integer SalaryBillAdvantages;
    Integer SalaryBillTotalPaid;

    public salaryBillModel(Integer salaryBillID, Integer salaryBillPaymentID, String salaryBillStatus, String salaryBillStaffName, Date salaryBillDate, Integer salaryBillAdvantages, Integer salaryBillTotalPaid) {
        this.SalaryBillID = salaryBillID;
        this.SalaryBillPaymentID = salaryBillPaymentID;
        this.SalaryBillStatus = salaryBillStatus;
        this.SalaryBillStaffName = salaryBillStaffName;
        this.SalaryBillDate = salaryBillDate;
        this.SalaryBillAdvantages = salaryBillAdvantages;
        this.SalaryBillTotalPaid = salaryBillTotalPaid;
    }

    public Integer getSalaryBillID() {
        return SalaryBillID;
    }

    public Integer getSalaryBillPaymentID() {
        return SalaryBillPaymentID;
    }

    public String getSalaryBillStatus() {
        return SalaryBillStatus;
    }

    public String getSalaryBillStaffName() {
        return SalaryBillStaffName;
    }

    public Date getSalaryBillDate() {
        return SalaryBillDate;
    }

    public Integer getSalaryBillAdvantages() {
        return SalaryBillAdvantages;
    }

    public Integer getSalaryBillTotalPaid() {
        return SalaryBillTotalPaid;
    }

    public void setSalaryBillID(Integer salaryBillID) {
        SalaryBillID = salaryBillID;
    }

    public void setSalaryBillPaymentID(Integer salaryBillAdmissionID) {
        SalaryBillPaymentID = salaryBillAdmissionID;
    }

    public void setSalaryBillStatus(String salaryBillStatus) {
        SalaryBillStatus = salaryBillStatus;
    }

    public void setSalaryBillPatientName(String salaryBillPatientName) {
        SalaryBillStaffName = salaryBillPatientName;
    }

    public void setSalaryBillDate(Date salaryBillDate) {
        SalaryBillDate = salaryBillDate;
    }

    public void setSalaryBillAdvantages(Integer salaryBillDiscount) {
        SalaryBillAdvantages = salaryBillDiscount;
    }

    public void setSalaryBillTotalPaid(Integer salaryBillTotalPaid) {
        SalaryBillTotalPaid = salaryBillTotalPaid;
    }
}

