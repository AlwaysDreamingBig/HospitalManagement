package com.project.hospitalmanagement.controllers.factories.adminpagefactory;

import com.project.hospitalmanagement.controllers.admin.appointments.editAppointmentController;
import com.project.hospitalmanagement.controllers.admin.mainwindow.MainWindowController;
import com.project.hospitalmanagement.controllers.admin.staff.Buttons.onActionCertificationController;
import com.project.hospitalmanagement.controllers.admin.staff.Buttons.onActionDepartmentController;
import com.project.hospitalmanagement.controllers.admin.staff.Buttons.onActionRequestController;
import com.project.hospitalmanagement.controllers.admin.staff.Buttons.onActionTrainingController;
import com.project.hospitalmanagement.controllers.models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminPageFactory {

    /*
     * General Parameters----------------------------------------------------------------------------------------------------------------
     */
    private final StringProperty adminSelectedMenuItem;

    private AnchorPane dashboard;
    private AnchorPane homeboard;
    private AnchorPane appointments;
    private AnchorPane staffManagement;
    private AnchorPane doctors;
    private AnchorPane patient;
    private AnchorPane rooms;
    private AnchorPane departments;
    private AnchorPane finances;
    private AnchorPane pharmacy;
    private AnchorPane records;
    private AnchorPane ambulance;
    private AnchorPane births;
    private AnchorPane profile;

    public AdminPageFactory(){
        this.adminSelectedMenuItem = new SimpleStringProperty("");
    };

    public StringProperty getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }

    // ---------------------------------------------------------------------- getters for the Main BorderPane center panels----------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------

    public AnchorPane getDashboard(){
        if (dashboard == null){
            try{
                dashboard = new FXMLLoader(getClass().getResource("/FXML/Admin/Dashboard/Dashboard.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return dashboard;
    }

    public AnchorPane getHomeBoard(){
        if (homeboard == null){
            try{
                homeboard = new FXMLLoader(getClass().getResource("/FXML/General/calendar/calendar.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return homeboard;
    }

    public AnchorPane getStaffManagement(){
        if (staffManagement == null){
            try{
                staffManagement = new FXMLLoader(getClass().getResource("/FXML/Admin/Staff/AdminStaff.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return staffManagement;
    }

    public AnchorPane getDoctors(){
        if (doctors == null){
            try{
                doctors = new FXMLLoader(getClass().getResource("/FXML/Admin/Doctors/DoctorView.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return doctors;
    }

    public AnchorPane getPatients(){
        if (patient == null){
            try{
                patient = new FXMLLoader(getClass().getResource("/FXML/Admin/Patients/PatientsView.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return patient;
    }

    public AnchorPane getProfile(){
        if (profile == null){
            try{
                profile = new FXMLLoader(getClass().getResource("/FXML/Admin/AdminProfile.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return profile;
    }

    public AnchorPane getAppointments(){
        if (appointments == null){
            try{
                appointments = new FXMLLoader(getClass().getResource("/FXML/Admin/Appointments/appointmentsView.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return appointments;
    }

    public AnchorPane getDepartments(){
        if (departments == null){
            try{
                departments = new FXMLLoader(getClass().getResource("/FXML/Admin/Departments/departmentView.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return departments;
    }

    public AnchorPane getSalaries(){
        if (profile == null){
            try{
                profile = new FXMLLoader(getClass().getResource("/FXML/Admin/Salary/salaryBills.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return profile;
    }

    public AnchorPane getRooms(){
        if (rooms == null){
            try{
                rooms = new FXMLLoader(getClass().getResource("/FXML/General/Rooms/rooms.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return rooms;
    }

    public AnchorPane getCallCenter(){
        if (profile == null){
            try{
                profile = new FXMLLoader(getClass().getResource("/FXML/General/CallCenter/callCenter.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return profile;
    }

    public AnchorPane getInventory(){
        if (profile == null){
            try{
                profile = new FXMLLoader(getClass().getResource("/FXML/General/inventory/inventory.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return profile;
    }

    public AnchorPane getOPharmacy(){
        if (pharmacy == null){
            try{
                pharmacy = new FXMLLoader(getClass().getResource("/FXML/General/Pharmacy/pharmacy.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return pharmacy;
    }

    public AnchorPane getFinances(){
        if (finances == null){
            try{
                finances = new FXMLLoader(getClass().getResource("/FXML/Admin/Finances/FinanceOverview.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return finances;
    }

    public AnchorPane getAmbulances(){
        if (ambulance == null){
            try{
                ambulance = new FXMLLoader(getClass().getResource("/FXML/Admin/Ambulances/ambulance.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return ambulance;
    }

    public AnchorPane getBirths(){
        if (births == null){
            try{
                births = new FXMLLoader(getClass().getResource("/FXML/General/Records/births.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return births;
    }


    public AnchorPane getDeaths(){
        if (profile == null){
            try{
                profile = new FXMLLoader(getClass().getResource("/FXML/General/Records/deaths.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return profile;
    }

    public AnchorPane getRecords(){
        if (records == null){
            try{
                records = new FXMLLoader(getClass().getResource("/FXML/General/Records/records.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return records;
    }

    /*
     * End of section----------------------------------------------------------------------------------------------------------------------
     */


    /*
     * ----------------------------------------------------------------- Utility functions------------------------------------------------------------------------------------------------------------------

     * ----------------------------------------------------------------                    ----------------------------------------------------------------
     * */

    public void createStage(FXMLLoader loader){
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Title window");
        stage.show();
    }

    /*
     *------------------------------------------------------------- Display windows on action-------------------------------------------------------------------------------------------------------------
     *///-----------------------------------------------                                              ---------------------//----------------------------------------------------------------
    //--------------------------------------------------                                               ----------------------------------//----------------------------------------------------------------//----------------------------------------------------------------
    //----------------------------------------------------------------//----------------------------------------------------------------//----------------------------------------------------------------


    public void showAdminWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/MainWindow/MainWindow.fxml"));
        MainWindowController adminController = new MainWindowController();
        loader.setController(adminController);
        createStage(loader);
    }


    public void showFinanceWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Finances/FinanceOverview.fxml"));
        createStage(loader);
    }

    public void showInventoryWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/General/Maininventory/Inventory.fxml"));
        createStage(loader);
    }

    public void showMedicinesWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/General/Pharmacy/medicines.fxml"));
        createStage(loader);
    }

    public void showBirthWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/General/Records/births.fxml"));
        createStage(loader);
    }

    public void showDeathWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/General/Records/deaths.fxml"));
        createStage(loader);
    }

    public void showDoctorWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Doctors/AdminDoctorOverview.fxml"));
        createStage(loader);
    }

    public void showPatientWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Patients/AdminPatientOverview.fxml"));
        createStage(loader);
    }
    //---------------------------------------------------------------- Staff Management Windows//----------------------------------------------------------------//----------------------------------------------------------------
    //----------------------------------------------------------------//----------------------------------------------------------------//----------------------------------------------------------------
    //----------------------------------------------------------------//----------------------------------------------------------------//----------------------------------------------------------------
    public void showStaffWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Staff/StaffList.fxml"));
        createStage(loader);
    }

    public void showDepartmentsWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Staff/StaffDepartment.fxml"));
        createStage(loader);
    }

    public void showCertificationsWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/General/Certification/CertificationList.fxml"));
        createStage(loader);
    }

    public void showRequestListWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Staff/StaffRequest.fxml"));
        createStage(loader);
    }

    public void showTrainingWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Staff/StaffTraining.fxml"));
        createStage(loader);
    }


    // ---------------------------------------------------------------------- Edit panels----------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------


    public void showEditDepartmentsWindow(departmentModel department){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Staff/Buttons/onActionDepartment.fxml"));
            loader.load();

            onActionDepartmentController controller = loader.getController();

            controller.setFields(department);

            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEditTrainingWindow(trainingModel training, boolean Updater){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Staff/Buttons/onActionTraining.fxml"));
            loader.load();

            onActionTrainingController controller = loader.getController();

            controller.setFields(training);
            controller.setUpdater(Updater);

            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEditRequestWindow(requestModel request, boolean Updater){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Staff/Buttons/onActionRequest.fxml"));
            loader.load();

            onActionRequestController controller = loader.getController();

            controller.setFields(request);
            controller.setUpdater(Updater);

            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showEditCertificationsWindow(certificationModel certificateModel, boolean Updater) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Staff/Buttons/onActionCertification.fxml"));
            loader.load();

            onActionCertificationController controller = loader.getController();

            controller.setFields(certificateModel);
            controller.setUpdater(Updater);

            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEditAppointmentWindow(appointmentsModel appointment){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Appointments/editAppointment.fxml"));
            loader.load();

            editAppointmentController controller = loader.getController();

            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // ---------------------------------------------------------------------- Add panels----------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------
    public void showAddAppointment(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Appointments/addAppointment.fxml"));
        createStage(loader);
    }


    public void showAddCertificationWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Staff/Buttons/onActionCertification.fxml"));
        createStage(loader);
    }

    public void showAddRequestWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Staff/Buttons/onActionRequest.fxml"));
        createStage(loader);
    }

    public void showAddTrainingWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Staff/Buttons/onActionTraining.fxml"));
        createStage(loader);
    }

    public void showAddDoctor(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Doctors/AddDoctor.fxml"));
        createStage(loader);
    }

    public void showAddPatient(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Patients/AddPatient.fxml"));
        createStage(loader);
    }

    public void showAddRoom(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/General/Rooms/allotNewRoom.fxml"));
        createStage(loader);
    }

    public void showAddDepartment(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Departments/createDepartment.fxml"));
        createStage(loader);
    }

    public void showAddInventoryItem(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/General/inventory/addItem.fxml"));
        createStage(loader);
    }

    public void showAddAmbulance(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Ambulances/registerAmbulance.fxml"));
        createStage(loader);
    }

    public void showAddNewOrder(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/General/inventory/newOrder.fxml"));
        createStage(loader);
    }


}
