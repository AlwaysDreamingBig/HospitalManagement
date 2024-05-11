package com.project.hospitalmanagement.controllers.models;

import java.sql.Date;
import java.sql.Timestamp;

public class requestModel {

    Integer RequestID;
    String RequestName;
    String RequestType;
    Date RequestDate;
    String RequestStatus;
    String RequestDetails;

    public requestModel(Integer requestID, String requestName, String requestType, Date requestDate, String requestStatus, String requestDetails) {
        this.RequestID = requestID;
        this.RequestName = requestName;
        this.RequestType = requestType;
        this.RequestDate = requestDate;
        this.RequestStatus = requestStatus;
        this.RequestDetails = requestDetails;
    }

    public requestModel( String requestName, String requestType, Date requestDate, String requestStatus, String requestDetails) {
        this.RequestName = requestName;
        this.RequestType = requestType;
        this.RequestDate = requestDate;
        this.RequestStatus = requestStatus;
        this.RequestDetails = requestDetails;
    }

    public Integer getRequestID() {
        return RequestID;
    }

    public String getRequestName() {
        return RequestName;
    }

    public String getRequestType() {
        return RequestType;
    }

    public Date getRequestDate() {
        return RequestDate;
    }

    public String getRequestStatus() {
        return RequestStatus;
    }

    public String getRequestDetails() {
        return RequestDetails;
    }

    public void setRequestID(Integer requestID) {
        RequestID = requestID;
    }

    public void setRequestName(String requestName) {
        RequestName = requestName;
    }

    public void setRequestType(String requestType) {
        RequestType = requestType;
    }

    public void setRequestDate(Date requestDate) {
        RequestDate = requestDate;
    }

    public void setRequestStatus(String requestStatus) {
        RequestStatus = requestStatus;
    }

    public void setRequestDetails(String requestDetails) {
        RequestDetails = requestDetails;
    }
}
