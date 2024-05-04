package com.project.hospitalmanagement.controllers.admin.staff;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.staffModel;
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
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class staffController implements Initializable{

    public TextField search_bar;
    public TableView<staffModel> staffs_tableView;
    public TableColumn<staffModel, ImageView> Picture;
    public TableColumn<staffModel, Integer> StaffID ;
    public TableColumn<staffModel, String> StaffName;
    public TableColumn<staffModel, String> StaffFunction;
    public TableColumn<staffModel, String> StaffDepartment;
    public TableColumn<staffModel, String> StaffEmail;
    public TableColumn<staffModel, Integer> StaffMobile;
    public TableColumn<staffModel, Date> StaffEntryDate;
    public TableColumn<staffModel, Date> StaffEndDate;
    public TableColumn<staffModel, Integer> StaffSupervisor;
    public TableColumn<staffModel, Integer> StaffSalary;

    public TableColumn<staffModel, Void> Action;


    ObservableList<staffModel> staffModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        refreshStaff();

    }
    public void refreshStaff(){

        staffModelObservableList.clear();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String staffsViewQuery = "SELECT `StaffPicture`, `StaffID`, `StaffName`, `StaffFunction`, `StaffDepartment`, `StaffEmail`, `StaffMobile`, `StaffEntryDate`, `StaffEndDate`, `StaffSupervisor`, `StaffSalary` FROM `staff`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(staffsViewQuery);

            while(queryOutput.next()){

                Image profilePicture;
                Blob queryDoctorPicture = queryOutput.getBlob("StaffPicture");

                if (queryDoctorPicture == null) {
                    InputStream inputStream = getClass().getResourceAsStream("/Images/staffPicture.jpg");
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


                Integer queryStaffID = queryOutput.getInt("StaffID");
                String queryStaffName = queryOutput.getString("StaffName");
                String queryStaffSpecialization = queryOutput.getString("StaffFunction");
                String queryStaffDepartment = queryOutput.getString("StaffDepartment");
                String queryStaffEmail = queryOutput.getString("StaffEmail");
                Integer queryStaffMobile = queryOutput.getInt("StaffMobile");
                java.sql.Date queryStaffEntryDate = queryOutput.getDate("StaffEntryDate");
                java.sql.Date queryStaffEndDate = queryOutput.getDate("StaffEndDate");
                String queryStaffAccountStatus = queryOutput.getString("StaffSupervisor");
                Integer queryStaffSalary = queryOutput.getInt("StaffSalary");

                //Populate the observableList
                staffModelObservableList.add(new staffModel(imageView, queryStaffID, queryStaffName, queryStaffSpecialization, queryStaffDepartment, queryStaffEmail, queryStaffMobile, queryStaffEntryDate, queryStaffEndDate, queryStaffAccountStatus, queryStaffSalary));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            Picture.setCellValueFactory(new PropertyValueFactory<>("StaffPicture"));
            StaffID.setCellValueFactory(new PropertyValueFactory<>("StaffID"));
            StaffName.setCellValueFactory(new PropertyValueFactory<>("StaffName"));
            StaffFunction.setCellValueFactory(new PropertyValueFactory<>("StaffSpecialization"));
            StaffDepartment.setCellValueFactory(new PropertyValueFactory<>("StaffDepartment"));
            StaffEmail.setCellValueFactory(new PropertyValueFactory<>("StaffEmail"));
            StaffMobile.setCellValueFactory(new PropertyValueFactory<>("StaffMobile"));
            StaffEntryDate.setCellValueFactory(new PropertyValueFactory<>("StaffEntryDate"));
            StaffEndDate.setCellValueFactory(new PropertyValueFactory<>("StaffEndDate"));
            StaffSupervisor.setCellValueFactory(new PropertyValueFactory<>("StaffSupervisor"));
            StaffSalary.setCellValueFactory(new PropertyValueFactory<>("StaffSalary"));
            Action.setCellFactory(param -> new StaffActionButtonTableCell<>(this::displayStaffDetails));

            staffs_tableView.setItems(staffModelObservableList);

            //Initial filtered list
            FilteredList<staffModel> filteredData = new FilteredList<>(staffModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(staff -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();


                    if (staff.getStaffName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;    //Means a match was found in Staff column
                    } else if (staff.getStaffDepartment().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (staff.getStaffFunction().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (staff.getStaffEntryDate().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (staff.getStaffSupervisor().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    }else if (staff.getStaffEndDate().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;   //no match found
                });
            });

            SortedList<staffModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(staffs_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            staffs_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(staffController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

    private void displayStaffDetails(staffModel staff) {
        if (staff != null) {

            Integer ID = staff.getStaffID();
            String PatientName = staff.getStaffName();
            String Email = staff.getStaffEmail();


            //Now I call the Edit method to generate the Edit window with these infos
        } else {
            System.out.println("Appointment's details could not be retrieved.");
        }
    }

}

