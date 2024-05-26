package com.project.hospitalmanagement.controllers.admin.appointments;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class editAppointmentController implements Initializable {
    public ImageView Picture;

    public TextField Name;

    public TextField Email;
    public RadioButton GenderMale;
    public RadioButton GenderFemale;

    public DatePicker Date;

    public TextField Time;

    public TextField Mobile;

    public TextField Doctor;
    public TextArea Condition;
    public Button Cancel;
    public Button Save;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}


