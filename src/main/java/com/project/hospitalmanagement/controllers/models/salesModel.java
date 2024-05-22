package com.project.hospitalmanagement.controllers.models;

import java.math.BigDecimal;

import java.time.LocalDateTime;

public class salesModel {
    Integer SaleID;
    LocalDateTime SaleDate;
    Integer PatientID;
    String EmployeeID;
    Integer TotalAmount;
    String PaymentMethod;
    String Item;
    Integer PrescriptionID;
    String SaleStatus;
    BigDecimal Discount;
    BigDecimal Tax;
    String Remarks;
    String PaymentTransactionID;
    String InvoiceID;

    public salesModel(Integer saleID, LocalDateTime saleDate, Integer patientID, String employeeID, Integer totalAmount, String paymentMethod, String item, Integer prescriptionID, String saleStatus, BigDecimal discount, BigDecimal tax, String remarks, String paymentTransactionID, String invoiceID) {
        SaleID = saleID;
        SaleDate = saleDate;
        PatientID = patientID;
        EmployeeID = employeeID;
        TotalAmount = totalAmount;
        PaymentMethod = paymentMethod;
        Item = item;
        PrescriptionID = prescriptionID;
        SaleStatus = saleStatus;
        Discount = discount;
        Tax = tax;
        Remarks = remarks;
        PaymentTransactionID = paymentTransactionID;
        InvoiceID = invoiceID;
    }

    public salesModel(Integer saleID, String employeeID, String remarks) {
        SaleID = saleID;
        EmployeeID = employeeID;
        Remarks = remarks;
    }

    public Integer getSaleID() {
        return SaleID;
    }

    public LocalDateTime getSaleDate() {
        return SaleDate;
    }

    public Integer getPatientID() {
        return PatientID;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public Integer getTotalAmount() {
        return TotalAmount;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public String getItem() {
        return Item;
    }

    public Integer getPrescriptionID() {
        return PrescriptionID;
    }

    public String getSaleStatus() {
        return SaleStatus;
    }

    public BigDecimal getDiscount() {
        return Discount;
    }

    public BigDecimal getTax() {
        return Tax;
    }

    public String getRemarks() {
        return Remarks;
    }

    public String getPaymentTransactionID() {
        return PaymentTransactionID;
    }

    public String getInvoiceID() {
        return InvoiceID;
    }

    public void setSaleID(Integer saleID) {
        SaleID = saleID;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        SaleDate = saleDate;
    }

    public void setPatientID(Integer patientID) {
        PatientID = patientID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public void setTotalAmount(Integer totalAmount) {
        TotalAmount = totalAmount;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public void setItem(String item) {
        Item = item;
    }

    public void setPrescriptionID(Integer prescriptionID) {
        PrescriptionID = prescriptionID;
    }

    public void setSaleStatus(String saleStatus) {
        SaleStatus = saleStatus;
    }

    public void setDiscount(BigDecimal discount) {
        Discount = discount;
    }

    public void setTax(BigDecimal tax) {
        Tax = tax;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public void setPaymentTransactionID(String paymentTransactionID) {
        PaymentTransactionID = paymentTransactionID;
    }

    public void setInvoiceID(String invoiceID) {
        InvoiceID = invoiceID;
    }
}
