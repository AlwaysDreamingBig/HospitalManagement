package com.project.hospitalmanagement.controllers.admin.mainwindow;

import com.project.hospitalmanagement.controllers.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public BorderPane admin_Window;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model.getInstance().getAdminPageFactory().getAdminSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal){
                case "Dashboard" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getDashboard());
                case "Homeboard" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getHomeBoard());
                case "Appointments" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getAppointments());
                case "Doctors" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getDoctors());
                case "Staff Management" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getStaffManagement());
                case "Patients" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getPatients());
                case "Rooms" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getRooms());
                case "Departments" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getDepartments());
                case "Finance Management" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getFinances());
                case "Pharmacy" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getOPharmacy());
                case "Records" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getRecords());
                case "Ambulances" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getAmbulances());
                case "Inventory" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getInventory());
                case "My profile" -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getProfile());

                default -> admin_Window.setCenter(Model.getInstance().getAdminPageFactory().getHomeBoard());
            }
        });
    }
}
