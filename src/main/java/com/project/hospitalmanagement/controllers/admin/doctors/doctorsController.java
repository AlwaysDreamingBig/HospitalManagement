package com.project.hospitalmanagement.controllers.admin.doctors;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.doctorModel;
import com.project.hospitalmanagement.controllers.utilities.StaffActionButtonTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
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

public class doctorsController implements Initializable{

    public TextField search_bar;
    public TableView<doctorModel> doctors_tableView;
    public TableColumn<doctorModel, ImageView> Picture;
    public TableColumn<doctorModel, Integer> DoctorID ;
    public TableColumn<doctorModel, String> DoctorName;
    public TableColumn<doctorModel, String> DoctorSpecialization;
    public TableColumn<doctorModel, String> DoctorDepartment;
    public TableColumn<doctorModel, String> DoctorEmail;
    public TableColumn<doctorModel, Integer> DoctorMobile;
    public TableColumn<doctorModel, Date> DoctorEntryDate;
    public TableColumn<doctorModel, Date> DoctorEndDate;
    public TableColumn<doctorModel, String> DoctorAccountStatus;
    public TableColumn<doctorModel, Integer> DoctorSalary;

    public TableColumn<doctorModel, Void> Action;

    public Button UploadPicture;
    public Button addDoctor_btn;
    public Button refresh_btn;


    ObservableList<doctorModel> doctorModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        refreshDoctor();

        addListener();


    }

    public void refreshDoctor() {

        doctorModelObservableList.clear();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String doctorsViewQuery = "SELECT `DoctorPicture`, `DoctorID`, `DoctorName`, `DoctorSpecialization`, `DoctorDepartment`, `DoctorEmail`, `DoctorMobile`, `DoctorEntryDate`, `DoctorEndDate`, `DoctorAccountStatus`, `DoctorSalary` FROM `doctors`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(doctorsViewQuery);

            while(queryOutput.next()) {

                Image profilePicture;
                Blob queryDoctorPicture = queryOutput.getBlob("DoctorPicture");

                if (queryDoctorPicture == null) {
                    InputStream inputStream = getClass().getResourceAsStream("/Images/doctorPicture.jpg");
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


                Integer queryDoctorID = queryOutput.getInt("DoctorID");
                String queryDoctorName = queryOutput.getString("DoctorName");
                String queryDoctorSpecialization = queryOutput.getString("DoctorSpecialization");
                String queryDoctorDepartment = queryOutput.getString("DoctorDepartment");
                String queryDoctorEmail = queryOutput.getString("DoctorEmail");
                Integer queryDoctorMobile = queryOutput.getInt("DoctorMobile");
                java.sql.Date queryDoctorEntryDate = queryOutput.getDate("DoctorEntryDate");
                java.sql.Date queryDoctorEndDate = queryOutput.getDate("DoctorEndDate");
                String queryDoctorAccountStatus = queryOutput.getString("DoctorAccountStatus");
                Integer queryDoctorSalary = queryOutput.getInt("DoctorSalary");

                //Populate the observableList
                doctorModelObservableList.add(new doctorModel(imageView, queryDoctorID, queryDoctorName, queryDoctorSpecialization, queryDoctorDepartment, queryDoctorEmail, queryDoctorMobile, queryDoctorEntryDate, queryDoctorEndDate, queryDoctorAccountStatus, queryDoctorSalary));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            Picture.setCellValueFactory(new PropertyValueFactory<>("DoctorPicture"));
            DoctorID.setCellValueFactory(new PropertyValueFactory<>("DoctorID"));
            DoctorName.setCellValueFactory(new PropertyValueFactory<>("DoctorName"));
            DoctorSpecialization.setCellValueFactory(new PropertyValueFactory<>("DoctorSpecialization"));
            DoctorDepartment.setCellValueFactory(new PropertyValueFactory<>("DoctorDepartment"));
            DoctorEmail.setCellValueFactory(new PropertyValueFactory<>("DoctorEmail"));
            DoctorMobile.setCellValueFactory(new PropertyValueFactory<>("DoctorMobile"));
            DoctorEntryDate.setCellValueFactory(new PropertyValueFactory<>("DoctorEntryDate"));
            DoctorEndDate.setCellValueFactory(new PropertyValueFactory<>("DoctorEndDate"));
            DoctorAccountStatus.setCellValueFactory(new PropertyValueFactory<>("DoctorAccountStatus"));
            DoctorSalary.setCellValueFactory(new PropertyValueFactory<>("DoctorSalary"));
            Action.setCellFactory(param -> new StaffActionButtonTableCell<>(this::displayDoctorDetails));

            doctors_tableView.setItems(doctorModelObservableList);

            //Initial filtered list
            FilteredList<doctorModel> filteredData = new FilteredList<>(doctorModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(doctor -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();


                    if (doctor.getDoctorName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;    //Means a match was found in Doctor column
                    } else if (doctor.getDoctorDepartment().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (doctor.getDoctorSpecialization().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (doctor.getDoctorEntryDate().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (doctor.getDoctorEndDate().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;   //no match found
                });
            });

            SortedList<doctorModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(doctors_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            doctors_tableView.setItems(sortedData);

            // Close the result set and the prepared statement
            queryOutput.close();

        } catch (SQLException e) {
            Logger.getLogger(doctorsController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }finally {
            try {
                // Close the database connection
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void addListener(){
        addDoctor_btn.setOnMouseClicked(event -> openAddDoctor());
        refresh_btn.setOnMouseClicked(event -> refreshDoctor());
    }

    private void openAddDoctor(){
        Model.getInstance().getAdminPageFactory().showAddDoctor();
    }


    private void displayDoctorDetails(doctorModel doctor) {
        if (doctor != null) {
            // get the different rows value
            String Name = doctor.getDoctorName();
            String Email = doctor.getDoctorEmail();
            Date Date = doctor.getDoctorEntryDate();
            String Degree = doctor.getDoctorDegree();
            Integer Mobile = doctor.getDoctorMobile();
            String Specialization = doctor.getDoctorSpecialization();
            String Department = doctor.getDoctorDepartment();

            System.out.println("Doctor's Name" + Name);

            //Now I call the Edit method to generate the Edit window with these infos
        } else {
            System.out.println("Doctor's details could not be retrieved.");
        }
    }
}

