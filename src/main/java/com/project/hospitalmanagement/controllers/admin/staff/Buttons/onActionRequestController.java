package com.project.hospitalmanagement.controllers.admin.staff.Buttons;

import com.project.hospitalmanagement.controllers.alert.alertMessage;
import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.certificationModel;
import com.project.hospitalmanagement.controllers.models.requestModel;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class onActionRequestController implements Initializable {
    public TextField RequestName;
    public TextField RequestType;
    public DatePicker RequestDate;
    public ComboBox RequestStatus;
    public TextArea RequestDetails;
    public Integer RequestID;
    public Button save_btn;
    public Button cancel_btn;

    private boolean UpdaterEnabled = false;
    private final alertMessage alert = new alertMessage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    public void addListener(){
        save_btn.setOnMouseClicked(event -> save());
        cancel_btn.setOnMouseClicked(event -> cancel());
    }

    public void setFields(requestModel request){

        if(request != null){
            RequestID = request.getRequestID();
            RequestName.setText(request.getRequestName());
            RequestType.setText(request.getRequestType());

            // Convert java.sql.Date directly to LocalDate
            LocalDate localDate = request.getRequestDate().toLocalDate();
            RequestDate.setValue(localDate);

            RequestDetails.setText(request.getRequestDetails());
        }

    }

    public void save(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        if(UpdaterEnabled){ //Update Request details
            // SQL query to fetch department data
            String UpdateQuery = "UPDATE requests " +
                    "SET RequestName = ?, RequestType = ?, RequestDate = ?, RequestStatus = ?, RequestDetails = ? " +
                    "WHERE RequestID  = ?";


            try {
                PreparedStatement statement = connectDB.prepareStatement(UpdateQuery);

                statement.setString(1, RequestName.getText());
                statement.setString(2, RequestType.getText());

                LocalDate localDate = RequestDate.getValue();
                java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                statement.setDate(3, sqlDate);

                statement.setString(4, (String) RequestStatus.getSelectionModel().getSelectedItem());
                statement.setString(5, RequestDetails.getText());

                statement.setInt(6, RequestID);

                // Execute the update query
                int rowsAffected = statement.executeUpdate();

                // Check if any rows were updated
                if (rowsAffected > 0) {
                    alert.successMessage("Request updated successfully.");
                    Stage stage = (Stage) save_btn.getScene().getWindow();
                    Model.getInstance().getpageFactory().closeStage(stage);
                    UpdaterEnabled = false;
                } else {
                    alert.errorMessage("No request found with the given Description.");

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    // Close the database connection
                    connectDB.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else { //Add new REQUEST to table

            // SQL query to insert new certification data
            String insertQuery = "INSERT INTO requests (RequestName, RequestType, RequestDate, RequestStatus, RequestDetails) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try {
                PreparedStatement statement = connectDB.prepareStatement(insertQuery);

                statement.setString(1, RequestName.getText());
                statement.setString(2, RequestType.getText());

                LocalDate localDate = RequestDate.getValue();
                if (localDate != null) {
                    String formattedDate = localDate.toString();
                    java.sql.Date sqlDate = java.sql.Date.valueOf(formattedDate);
                    statement.setDate(3, sqlDate);
                } else {
                    // Set the current date
                    LocalDate currentDate = LocalDate.now();
                    java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate.toString());
                    statement.setDate(3, sqlDate);
                }



                statement.setString(4, (String) RequestStatus.getSelectionModel().getSelectedItem());
                statement.setString(5, RequestDetails.getText());

                // Execute the insert query
                int rowsAffected = statement.executeUpdate();

                // Check if any rows were inserted
                if (rowsAffected > 0) {
                    alert.successMessage("New request added successfully.");
                    Stage stage = (Stage) save_btn.getScene().getWindow();
                    Model.getInstance().getpageFactory().closeStage(stage);
                } else {
                    alert.errorMessage("Failed to add new request.");
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    // Close the database connection
                    connectDB.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void cancel(){
        Stage stage = (Stage) save_btn.getScene().getWindow();
        Model.getInstance().getpageFactory().closeStage(stage);
    }

    public void setUpdater(boolean value){
        UpdaterEnabled = value;
    }
}
