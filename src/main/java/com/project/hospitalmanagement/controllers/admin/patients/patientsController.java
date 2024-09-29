package com.project.hospitalmanagement.controllers.admin.patients;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.patientModel;
import com.project.hospitalmanagement.controllers.utilities.StaffActionButtonTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class patientsController implements Initializable{

    public TextField search_bar;
    public TableView<patientModel> patients_tableView;
    public TableColumn<patientModel, ImageView> PatientPicture;
    public TableColumn<patientModel, Integer> PatientID;
    public TableColumn<patientModel, String> PatientName;
    public TableColumn<patientModel, String> PatientGender;
    public TableColumn<patientModel, Date> PatientBirthDate;
    public TableColumn<patientModel, String> PatientEmail;
    public TableColumn<patientModel, Integer> PatientMobile;
    public TableColumn<patientModel, String> PatientBloodGrp;
    public TableColumn<patientModel, String> PatientAssignedDr;
    public TableColumn<patientModel, String> PatientTreatment;

    public TableColumn<patientModel, Void> Action;
    public Button addPatient_btn;
    public Button refresh_btn;


    ObservableList<patientModel> patientModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        refreshPatient();

        addListener();

    }

    public void addListener(){
        addPatient_btn.setOnMouseClicked(event -> openAddPatient());
        refresh_btn.setOnMouseClicked(event -> refreshPatient());
    }

    public void openAddPatient(){
        Model.getInstance().getAdminPageFactory().showAddPatient();
    }
    public void refreshPatient() {

        patientModelObservableList.clear();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String patientsViewQuery = "SELECT `PatientPicture`, `PatientID`, `PatientName`, `PatientGender`, `PatientBirthDate`, `PatientEmail`, `PatientMobile`, `PatientBloodGrp`, `PatientAssignedDr`, `PatientTreatment` FROM `patients`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(patientsViewQuery);

            while(queryOutput.next()){

                Image profilePicture;
                Blob queryDoctorPicture = queryOutput.getBlob("PatientPicture");

                if (queryDoctorPicture == null) {
                    InputStream inputStream = getClass().getResourceAsStream("/Images/patientPicture.jpg");
                    if (inputStream != null) {
                        System.out.println("Image found");
                    } else {
                        System.out.println("Image not found");
                    }
                    //assert inputStream != null;
                    assert inputStream != null;
                    profilePicture = new Image(inputStream);
                } else {
                    // Convert Blob to Image and put inside imageView
                    InputStream inputStream = queryDoctorPicture.getBinaryStream();
                    profilePicture = new Image(inputStream);
                }

                ImageView imageView = new ImageView(profilePicture);
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);

                // Create a DropShadow effect
                DropShadow dropShadow = new DropShadow();
                dropShadow.setRadius(5);
                dropShadow.setColor(Color.BLACK);
                imageView.setEffect(dropShadow);

                Circle clip = new Circle(15, 15, 15);
                imageView.setClip(clip);


                Integer queryPatientID = queryOutput.getInt("PatientID");
                String queryPatientName = queryOutput.getString("PatientName");
                String queryPatientGender = queryOutput.getString("PatientGender");
                Date queryPatientBirthDate = queryOutput.getDate("PatientBirthDate");
                String queryPatientEmail = queryOutput.getString("PatientEmail");
                Integer queryPatientMobile = queryOutput.getInt("PatientMobile");
                String queryPatientBloodGrp = queryOutput.getString("PatientBloodGrp");
                String queryPatientAssignedDr = queryOutput.getString("PatientAssignedDr");
                String queryPatientTreatment = queryOutput.getString("PatientTreatment");

                //Populate the observableList
                patientModelObservableList.add(new patientModel(imageView, queryPatientID, queryPatientName, queryPatientGender, queryPatientBirthDate, queryPatientEmail, queryPatientMobile, queryPatientBloodGrp, queryPatientAssignedDr, queryPatientTreatment));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            PatientPicture.setCellValueFactory(new PropertyValueFactory<>("PatientPicture"));
            PatientID.setCellValueFactory(new PropertyValueFactory<>("PatientID"));
            PatientName.setCellValueFactory(new PropertyValueFactory<>("PatientName"));
            PatientGender.setCellValueFactory(new PropertyValueFactory<>("PatientGender"));
            PatientBirthDate.setCellValueFactory(new PropertyValueFactory<>("PatientBirthDate"));
            PatientEmail.setCellValueFactory(new PropertyValueFactory<>("PatientEmail"));
            PatientMobile.setCellValueFactory(new PropertyValueFactory<>("PatientMobile"));
            PatientBloodGrp.setCellValueFactory(new PropertyValueFactory<>("PatientBloodGrp"));
            PatientAssignedDr.setCellValueFactory(new PropertyValueFactory<>("PatientAssignedDr"));
            PatientTreatment.setCellValueFactory(new PropertyValueFactory<>("PatientTreatment"));

            Action.setCellFactory(param -> new StaffActionButtonTableCell<>(this::displayPatientDetails));

            patients_tableView.setItems(patientModelObservableList);

            //Initial filtered list
            FilteredList<patientModel> filteredData = new FilteredList<>(patientModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(patient -> {

                    if (newValue == null || newValue.isBlank()) {
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    // Check patient name
                    if (patient.getPatientName() != null
                            && patient.getPatientName().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }

                    // Check patient treatment
                    if (patient.getPatientTreatment() != null
                            && patient.getPatientTreatment().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }

                    // Check patient assigned doctor
                    if (patient.getPatientAssignedDr() != null
                            && patient.getPatientAssignedDr().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }

                    // No match found
                    return false;
                });
            });

            SortedList<patientModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(patients_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            patients_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(patientsController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

    private void displayPatientDetails(patientModel patient) {
        if (patient != null) {

            Integer ID = patient.getPatientID();
            String PatientName = patient.getPatientName();
            String Email = patient.getPatientEmail();
            java.sql.Date Birthday = patient.getPatientBirthDate();
            String Gender = patient.getPatientGender();
            Integer Mobile = patient.getPatientMobile();

            //Now I call the Edit method to generate the Edit window with these infos
        } else {
            System.out.println("Appointment's details could not be retrieved.");
        }
    }
}

