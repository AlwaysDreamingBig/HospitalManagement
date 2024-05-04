package com.project.hospitalmanagement.controllers.admin.staff.Buttons;

import com.project.hospitalmanagement.controllers.admin.staff.StaffManagementController;
import com.project.hospitalmanagement.controllers.alert.alertMessage;
import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.certificationModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class onActionCertificationController implements Initializable {
    public TextField certificationName;
    public TextField CertificationOrganization;
    public TextField CertificationValidity;
    public TextArea CertificationDescription;
    public Integer CertificationID;
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

    public void setFields(certificationModel certification){

        if(certification != null){
            CertificationID = certification.getCertificationID();
            certificationName.setText(certification.getCertificationName());
            CertificationOrganization.setText(certification.getCertificationOrganization());
            CertificationValidity.setText(String.valueOf(certification.getCertificationValidityPeriod()));
            CertificationDescription.setText(certification.getCertificationDescription());
        }

    }

    public void save(){
        System.out.println("pppppppppppppppppppppppppppppppppppppppppppppp");
        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        if(UpdaterEnabled){ //Update certification details
            // SQL query to fetch department data
            String UpdateQuery = "UPDATE certifications " +
                    "SET CertificationName = ?, CertificationOrganization = ?, CertificationValidityPeriod = ?, CertificationDescription = ? " +
                    "WHERE CertificationID = ?";


            try {
                PreparedStatement statement = connectDB.prepareStatement(UpdateQuery);

                statement.setString(1, certificationName.getText());
                statement.setString(2, CertificationOrganization.getText());
                statement.setInt(3, Integer.parseInt(CertificationValidity.getText()));
                statement.setString(4, CertificationDescription.getText());
                statement.setInt(5, CertificationID);

                // Execute the update query
                int rowsAffected = statement.executeUpdate();

                // Check if any rows were updated
                if (rowsAffected > 0) {
                    alert.successMessage("Certification updated successfully.");
                    Stage stage = (Stage) save_btn.getScene().getWindow();
                    Model.getInstance().getpageFactory().closeStage(stage);
                    UpdaterEnabled = false;
                } else {
                    alert.errorMessage("No certification found with the given Description.");

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
        }else { //Add new certification to table

            // SQL query to insert new certification data
            String insertQuery = "INSERT INTO certifications (CertificationName, CertificationOrganization, CertificationValidityPeriod, CertificationDescription) " +
                    "VALUES (?, ?, ?, ?)";

            try {
                PreparedStatement statement = connectDB.prepareStatement(insertQuery);

                statement.setString(1, certificationName.getText());
                statement.setString(2, CertificationOrganization.getText());
                statement.setInt(3, Integer.parseInt(CertificationValidity.getText()));
                statement.setString(4, CertificationDescription.getText());

                // Execute the insert query
                int rowsAffected = statement.executeUpdate();

                // Check if any rows were inserted
                if (rowsAffected > 0) {
                    alert.successMessage("New certification added successfully.");
                    Stage stage = (Stage) save_btn.getScene().getWindow();
                    Model.getInstance().getpageFactory().closeStage(stage);
                } else {
                    alert.errorMessage("Failed to add new certification.");
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
