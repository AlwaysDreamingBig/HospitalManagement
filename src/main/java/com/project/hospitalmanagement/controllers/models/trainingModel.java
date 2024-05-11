package com.project.hospitalmanagement.controllers.models;

import java.sql.Date;
import java.sql.Time;

public class trainingModel {

    Integer TrainingID;
    String TrainingParticipants;
    Date TrainingDay;
    Time TrainingStart;
    Time TrainingEnd;
    String TrainingInstructor;
    String TrainingLocation;
    String TrainingDescription;
    String TrainingDesignation;

    public trainingModel(Integer trainingID, String trainingParticipants, Date trainingDay, Time trainingStart, Time trainingEnd, String trainingInstructor, String trainingLocation, String trainingDescription, String trainingDesignation) {
        this.TrainingID = trainingID;
        this.TrainingParticipants = trainingParticipants;
        this.TrainingDay = trainingDay;
        this.TrainingStart = trainingStart;
        this.TrainingEnd = trainingEnd;
        this.TrainingInstructor = trainingInstructor;
        this.TrainingLocation = trainingLocation;
        this.TrainingDescription = trainingDescription;
        this.TrainingDesignation = trainingDesignation;
    }

    public trainingModel(Date trainingDay, String trainingDescription) {
        this.TrainingDay = trainingDay;
        this.TrainingDescription = trainingDescription;
    }

    public trainingModel(Integer trainingID, Date trainingDay, String trainingDescription) {
        this.TrainingID = trainingID;
        this.TrainingDay = trainingDay;
        this.TrainingDescription = trainingDescription;

    }

    public Integer getTrainingID() {
        return TrainingID;
    }

    public String getTrainingParticipants() {
        return TrainingParticipants;
    }

    public Date getTrainingDay() {
        return TrainingDay;
    }

    public Time getTrainingStart() {
        return TrainingStart;
    }

    public Time getTrainingEnd() {
        return TrainingEnd;
    }

    public String getTrainingInstructor() {
        return TrainingInstructor;
    }

    public String getTrainingLocation() {
        return TrainingLocation;
    }

    public String getTrainingDescription() {
        return TrainingDescription;
    }

    public String getTrainingDesignation() {
        return TrainingDesignation;
    }

    public void setTrainingID(Integer trainingID) {
        TrainingID = trainingID;
    }

    public void setTrainingParticipant(String trainingParticipants) {
        TrainingParticipants = trainingParticipants;
    }

    public void setTrainingDay(Date trainingDay) {
        TrainingDay = trainingDay;
    }

    public void setTrainingStart(Time trainingStart) {
        TrainingStart = trainingStart;
    }

    public void setTrainingEnd(Time trainingEnd) {
        TrainingEnd = trainingEnd;
    }

    public void setTrainingInstructor(String trainingInstructor) {
        TrainingInstructor = trainingInstructor;
    }

    public void setTrainingLocation(String trainingLocation) {
        TrainingLocation = trainingLocation;
    }

    public void setTrainingDescription(String trainingDescription) {
        TrainingDescription = trainingDescription;
    }

    public void setTrainingDesignation(String trainingDesignation) {
        TrainingDesignation = trainingDesignation;
    }
}
