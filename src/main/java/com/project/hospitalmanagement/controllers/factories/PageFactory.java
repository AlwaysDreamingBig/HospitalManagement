package com.project.hospitalmanagement.controllers.factories;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class PageFactory {
    public PageFactory(){};

    public void createStage(FXMLLoader loader){
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Hospital Management System");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/login.jpg")));
        stage.show();
    }


    public void showAdminLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/AdminController.fxml"));
        createStage(loader);
    }

    public void showDoctorLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Doctor/DoctorController.fxml"));
        createStage(loader);
    }

    public void showPatientLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Patient/PatientController.fxml"));
        createStage(loader);
    }

    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
        createStage(loader);
    }

    public void showMessageBox(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Utilities/MessageBox.fxml"));
        createStage(loader);
    }


    public void closeStage(Stage stage){
        stage.close();
    }
}
