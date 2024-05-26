package com.project.hospitalmanagement.controllers.admin.mainwindow;

import com.project.hospitalmanagement.controllers.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowMenuController implements Initializable {


    public Button EXtend_dashboard_btn;
    public Button doctors_btn;
    public Button staffManagement_btn;
    public Button patients_btn;
    public Button appointment_btn;
    public Button room_btn;
    public Button department_btn;
    public Button finance_btn;
    public Button pharmacy_btn;
    public Button records_btn;
    public Button ambulance_btn;
    public Button inventory_btn;
    public Button homeboard_btn;
    public ImageView profilePicture;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();

    }

    public void addListener(){
        EXtend_dashboard_btn.setOnAction(event -> onDashboard());
        homeboard_btn.setOnAction(event -> onHomeboard());
        appointment_btn.setOnAction(event -> onAppointments());
        staffManagement_btn.setOnAction(event -> onStaffManagement());
        doctors_btn.setOnAction(event -> onDoctors());
        patients_btn.setOnAction(event -> onPatients());
        room_btn.setOnAction(event -> onRooms());
        department_btn.setOnAction(event -> onDepartments());
        finance_btn.setOnAction(event -> onFinances());
        pharmacy_btn.setOnAction(event -> onPharmacy());
        records_btn.setOnAction(event -> onRecords());
        ambulance_btn.setOnAction(event -> onAmbulances());
        inventory_btn.setOnAction(event -> onInventory());

    }

    private void onDashboard(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Dashboard");
    }

    private void onHomeboard(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Homeboard");
    }

    private void onStaffManagement(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Staff Management");
    }

    private void onFinances(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Finance Management");
    }

    private void onAmbulances(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Ambulances");
    }

    private void onDoctors(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Doctors");
    }

    private void onPatients(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Patients");
    }

    private void onAppointments(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Appointments");
    }

    private void onRooms(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Rooms");
    }

    private void onPharmacy(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Pharmacy");
    }

    private void onInventory(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Inventory");
    }

    private void onDepartments(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Departments");
    }

    private void onRecords(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("Records");
    }
    private void onProfile(){
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().set("My profile");
    }
}
