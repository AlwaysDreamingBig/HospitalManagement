package com.project.hospitalmanagement.controllers.admin.staff;

import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.departmentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class StaffManagementController implements Initializable {
    public Button removeDepartment_btn;
    public Button editDepartment_btn;
    public ListView<String> trainingList;
    public Button addTraining_btn;
    public Button removeTraining_btn;
    public Button editTraining_btn;
    public ListView<String> RequestList;
    public Button addRequest_btn;
    public Button removeRequest_btn;
    public Button editRequest_btn;
    public ListView<String> certificationList;
    public Button addCertification_btn;
    public Button removeCertification_btn;
    public Button editCertification_btn;
    public Button StaffMembers_btn;
    public ImageView employeePicture;
    public TableView<departmentModel> departmentTable;
    public TableColumn<departmentModel, String> DepartmentName;
    public TableColumn<departmentModel, String> DepartmentHead;

    private String selectedCertification;
    private String selectedTraining;
    private String selectedRequest;
    ObservableList<departmentModel> departmentInfoObservableList = FXCollections.observableArrayList();

    ObservableList<String> trainingsInfoObservableList = FXCollections.observableArrayList();
    ObservableList<String> certificationsInfoObservableList = FXCollections.observableArrayList();
    ObservableList<String> requestsInfoObservableList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setDepartmentTableView();

        setTrainingListView();

        setRequestListView();

        setCertificationListView();

        certificationList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedCertificationInfo = newValue.toString();
                String[] parts = selectedCertificationInfo.split("\t-\t");
                if (parts.length == 2) {
                    selectedCertification = parts[1].trim();
                }
            }
        });

        trainingList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedCertificationInfo = newValue.toString();
                String[] parts = selectedCertificationInfo.split("\t-\t");
                if (parts.length == 2) {
                    selectedTraining = parts[1].trim();
                }
            }
        });

        RequestList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedCertificationInfo = newValue.toString();
                String[] parts = selectedCertificationInfo.split("\t-\t");
                if (parts.length == 2) {
                    selectedRequest = parts[1].trim();
                }
            }
        });


        addListener();


    }

    public void addListener(){

        editCertification_btn.setOnMouseClicked(event -> onEditCertification());
        editTraining_btn.setOnMouseClicked(event -> onEditTraining());
        editRequest_btn.setOnMouseClicked(event -> onEditRequest());

    }
    public void onEditCertification(){
        System.out.println("selectedCertificationName is " + selectedCertification);
    }

    public void onEditTraining(){
        System.out.println("selectedCertificationName is " + selectedTraining);
    }

    public void onEditRequest(){
        System.out.println("selectedCertificationName is " + selectedRequest);
    }

    public void setDepartmentTableView() {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch department data
        String departmentListQuery = "SELECT `DepartmentName`, `DepartmentHead` FROM departments";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(departmentListQuery);

            // Iterate through the result set and add department names to the list
            while (queryOutput.next()) {

                String queryDepartmentHead = queryOutput.getString("DepartmentHead");
                String queryDepartmentName = queryOutput.getString("DepartmentName");

                departmentInfoObservableList.add(new departmentModel(queryDepartmentName,queryDepartmentHead ));


            }

            DepartmentName.setCellValueFactory(new PropertyValueFactory<>("DepartmentName"));
            DepartmentHead.setCellValueFactory(new PropertyValueFactory<>("DepartmentHead"));

            // Close the result set and the prepared statement
            queryOutput.close();

            departmentTable.setItems(departmentInfoObservableList);

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


    public void setTrainingListView() {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch department data
        String trainingListQuery = "SELECT `TrainingDay`, `TrainingDescription` FROM trainings";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(trainingListQuery);


            // Iterate through the result set and add department names to the list
            while (queryOutput.next()) {
                Date queryTrainingDay = queryOutput.getDate("TrainingDay");
                String queryTrainingDescription = queryOutput.getString("TrainingDescription");

                String Line = queryTrainingDay + " \t-\t " + queryTrainingDescription;

                trainingsInfoObservableList.add(Line);
            }
            // Close the result set and the prepared statement
            queryOutput.close();

            trainingList.setItems(trainingsInfoObservableList);

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

    public void setRequestListView() {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch department data
        String trainingListQuery = "SELECT `RequestDetails`, `RequestStatus` FROM requests";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(trainingListQuery);


            // Iterate through the result set and add department names to the list
            while (queryOutput.next()) {
                String queryRequestStatus = queryOutput.getString("RequestStatus");
                String queryRequestDetails = queryOutput.getString("RequestDetails");

                String Line = queryRequestStatus + " \t-\t " + queryRequestDetails;

                if(Objects.equals(queryRequestStatus, "Pending") || Objects.equals(queryRequestStatus, "Rejected")){
                    Line = queryRequestStatus + " \t\t-\t " + queryRequestDetails;
                }


                requestsInfoObservableList.add(Line);
            }
            // Close the result set and the prepared statement
            queryOutput.close();

            RequestList.setItems(requestsInfoObservableList);

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

    public void setCertificationListView() {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch department data
        String trainingListQuery = "SELECT `CertificationName`, `CertificationValidityPeriod` FROM certifications";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(trainingListQuery);


            // Iterate through the result set and add department names to the list
            while (queryOutput.next()) {
                String queryCertificationValidityPeriod = queryOutput.getString("CertificationValidityPeriod");
                String queryCertificationName = queryOutput.getString("CertificationName");

                String Line = queryCertificationValidityPeriod + " Months \t-\t " + queryCertificationName;


                certificationsInfoObservableList.add(Line);
            }
            // Close the result set and the prepared statement
            queryOutput.close();

            certificationList.setItems(certificationsInfoObservableList);

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
