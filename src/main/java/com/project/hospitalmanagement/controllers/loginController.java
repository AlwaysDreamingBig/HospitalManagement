package com.project.hospitalmanagement.controllers;

import com.project.hospitalmanagement.controllers.models.Model;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    public Button doctor_btn;
    public Button other_btn;
    public Button patient_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    public void addListener(){
        doctor_btn.setOnMouseClicked(event -> goToDoctorLoginWindow());
        other_btn.setOnMouseClicked(event -> goToAdminLoginWindow());
        patient_btn.setOnMouseClicked(event -> goToPatientLoginWindow());
    }

    public void goToAdminLoginWindow(){
        //Stage stage = (Stage) other_btn.getScene().getWindow();
        //Model.getInstance().getpageFactory().closeStage(stage);
        Model.getInstance().getpageFactory().showAdminLoginWindow();
    }

    public void goToDoctorLoginWindow(){
        Model.getInstance().getpageFactory().showDoctorLoginWindow();
    }

    public void goToPatientLoginWindow(){
        Model.getInstance().getpageFactory().showPatientLoginWindow();
    }

}
