package com.project.hospitalmanagement.controllers.admin.staff;

import com.project.hospitalmanagement.controllers.alert.alertMessage;
import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.certificationModel;
import com.project.hospitalmanagement.controllers.models.departmentModel;
import com.project.hospitalmanagement.controllers.utilities.myDatabaseUtils.myDatabaseUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    public AnchorPane StaffManagementPane;
    public AnchorPane repartitionGraph;
    public Label employeeName;
    private final alertMessage alert = new alertMessage();
    private String selectedCertification;
    private String selectedTraining;
    private String selectedRequest;
    private String selectedDepartment;

    ObservableList<departmentModel> departmentInfoObservableList = FXCollections.observableArrayList();

    ObservableList<String> trainingsInfoObservableList = FXCollections.observableArrayList();
    ObservableList<String> certificationsInfoObservableList = FXCollections.observableArrayList();
    ObservableList<String> requestsInfoObservableList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        StaffManagementPane.setOnMouseClicked(event -> {
            // Check if the click occurred outside the RequestList
            if (!RequestList.getBoundsInParent().contains(event.getX(), event.getY())) {
                // If so, clear the selection
                departmentTable.getSelectionModel().clearSelection();
                selectedDepartment = null;
                RequestList.getSelectionModel().clearSelection();
                selectedRequest = null;
                certificationList.getSelectionModel().clearSelection();
                selectedCertification = null;
                trainingList.getSelectionModel().clearSelection();
                selectedTraining = null;
            }
        });


        setDepartmentTableView();

        setTrainingListView();

        setRequestListView();

        setCertificationListView();

        departmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedDepartment = newSelection.getDepartmentName();
            }

            certificationList.getSelectionModel().clearSelection();
            selectedCertification = null;
            RequestList.getSelectionModel().clearSelection();
            selectedRequest = null;
            trainingList.getSelectionModel().clearSelection();
            selectedTraining = null;
        });


        certificationList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedCertificationInfo = newValue.toString();
                String[] parts = selectedCertificationInfo.split("\t-\t");
                if (parts.length == 2) {
                    selectedCertification = parts[1].trim();
                }

                selectedDepartment = null;
                RequestList.getSelectionModel().clearSelection();
                selectedRequest = null;
                trainingList.getSelectionModel().clearSelection();
                selectedTraining = null;
            }
        });

        trainingList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedCertificationInfo = newValue.toString();
                String[] parts = selectedCertificationInfo.split("\t-\t");
                if (parts.length == 2) {
                    selectedTraining = parts[1].trim();
                }

                selectedDepartment = null;
                RequestList.getSelectionModel().clearSelection();
                selectedRequest = null;
                certificationList.getSelectionModel().clearSelection();
                selectedCertification = null;
            }
        });

        RequestList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedCertificationInfo = newValue.toString();
                String[] parts = selectedCertificationInfo.split("\t-\t");
                if (parts.length == 2) {
                    selectedRequest = parts[1].trim();
                }

                selectedDepartment = null;
                certificationList.getSelectionModel().clearSelection();
                selectedCertification = null;
                trainingList.getSelectionModel().clearSelection();
                selectedTraining = null;
            }
        });



        addListener();

        createPieChart(repartitionGraph);


    }

    public void addListener(){

        editCertification_btn.setOnMouseClicked(event -> onEditCertification());
        editTraining_btn.setOnMouseClicked(event -> onEditTraining());
        editRequest_btn.setOnMouseClicked(event -> onEditRequest());
        editDepartment_btn.setOnMouseClicked(event -> onEditDepartment());

        removeCertification_btn.setOnMouseClicked(event -> onDeleteCertification());
        removeRequest_btn.setOnMouseClicked(event -> onDeleteRequest());
        removeTraining_btn.setOnMouseClicked(event -> onDeleteTraining());
        removeDepartment_btn.setOnMouseClicked(event -> onDeleteDepartment());

        addCertification_btn.setOnMouseClicked(event -> onAddCertification());
        addRequest_btn.setOnAction(event -> onShowRequest());
        addTraining_btn.setOnAction(event -> onShowTraining());

        StaffMembers_btn.setOnMouseClicked(event -> onStaffMembers());

    }

    public void onShowCertifications(){
        Model.getInstance().getAdminPageFactory().showCertificationsWindow();
    }

    public void onShowTraining(){
        Model.getInstance().getAdminPageFactory().showTrainingWindow();
    }

    public void onShowRequest(){
        Model.getInstance().getAdminPageFactory().showRequestListWindow();
    }

    public void onStaffMembers(){
        Model.getInstance().getAdminPageFactory().showStaffWindow();
    }

    public void onAddCertification(){
        Model.getInstance().getAdminPageFactory().showAddCertificationWindow();
    }

    public void onDeleteDepartment(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch all columns of the selected certification
        String departmentUpdateQuery = "UPDATE departments SET DepartmentHead = NULL WHERE DepartmentName = ?";

        if(selectedDepartment != null){
            try {
                PreparedStatement statement = connectDB.prepareStatement(departmentUpdateQuery);
                statement.setString(1, selectedDepartment);
                int deletedQuery = statement.executeUpdate();

                // Check if a row was returned
                if (deletedQuery > 0) {

                    alert.successMessage("Head removed from the table");

                    setDepartmentTableView();

                }else {

                    alert.errorMessage("Nothing was Deleted from the table: select a line!");
                }
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
        }else{
            System.out.println("Nothing was Selected from the table ");
        }


    }

    public void onDeleteCertification(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch all columns of the selected certification
        String certificationDeleteQuery = "DELETE FROM certifications WHERE CertificationName = ?";

        if(selectedCertification != null){
            try {
                PreparedStatement statement = connectDB.prepareStatement(certificationDeleteQuery);
                statement.setString(1, selectedCertification);
                int deletedQuery = statement.executeUpdate();

                // Check if a row was returned
                if (deletedQuery > 0) {

                    System.out.println("Deleted from the table ");

                    setCertificationListView();

                }else {
                    System.out.println("Nothing was Deleted from the table ");
                }
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
        }else{
            System.out.println("Nothing was Selected from the table ");
        }

    }


    public void onDeleteTraining(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch all columns of the selected certification
        String trainingDeleteQuery = "DELETE FROM trainings WHERE TrainingDescription = ?";

        if(selectedTraining != null){
            try {
                PreparedStatement statement = connectDB.prepareStatement(trainingDeleteQuery);
                statement.setString(1, selectedTraining);
                int deletedQuery = statement.executeUpdate();

                // Check if a row was returned
                if (deletedQuery > 0) {

                    System.out.println("Deleted from the table ");

                    setTrainingListView();

                }else {
                    System.out.println("Nothing was Deleted from the table ");
                }
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
        }else{
            System.out.println("Nothing was Selected from the table ");
        }

    }

    public void onDeleteRequest(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch all columns of the selected certification
        String requestDeleteQuery = "DELETE FROM requests WHERE RequestDetails = ?";

        if(selectedRequest != null){
            try {
                PreparedStatement statement = connectDB.prepareStatement(requestDeleteQuery);
                statement.setString(1, selectedRequest);
                int deletedQuery = statement.executeUpdate();

                // Check if a row was returned
                if (deletedQuery > 0) {

                    System.out.println("Deleted from the table ");

                    setRequestListView();

                }else {
                    System.out.println("Nothing was Deleted from the table ");
                }
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
        }else{
            System.out.println("Nothing was Selected from the table ");
        }


    }

    public void onEditDepartment(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch all columns of the selected certification
        String certificationDetailsQuery = "SELECT * FROM departments WHERE DepartmentName = ?";

        if(selectedDepartment != null){
            try {
                PreparedStatement statement = connectDB.prepareStatement(certificationDetailsQuery);
                statement.setString(1, selectedDepartment);
                ResultSet queryOutput = statement.executeQuery();

                // Check if a row was returned
                if (queryOutput.next()) {
                    // Retrieve values from the result set and create a Certification object
                    String DepartmentName = queryOutput.getString("DepartmentName");
                    String DepartmentHead = queryOutput.getString("DepartmentHead");

                    System.out.println("CertificationValidityPeriod is " + DepartmentName);
                    System.out.println("CertificationDescription is " + DepartmentHead);


                }
                Model.getInstance().getAdminPageFactory().showEditDepartmentsWindow();

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

    public void onEditCertification(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch all columns of the selected certification
        String certificationDetailsQuery = "SELECT * FROM certifications WHERE CertificationName = ?";

        if(selectedCertification != null){
            try {
                PreparedStatement statement = connectDB.prepareStatement(certificationDetailsQuery);
                statement.setString(1, selectedCertification);
                ResultSet queryOutput = statement.executeQuery();

                // Check if a row was returned
                certificationModel certificateModel = null;
                if (queryOutput.next()) {
                    // Retrieve values from the result set and create a Certification object
                    int CertificationID = queryOutput.getInt("CertificationID");
                    String CertificationName = queryOutput.getString("CertificationName");
                    String CertificationOrganization = queryOutput.getString("CertificationOrganization");
                    int CertificationValidityPeriod = queryOutput.getInt("CertificationValidityPeriod");
                    String CertificationDescription = queryOutput.getString("CertificationDescription");

                    certificateModel = new certificationModel(CertificationID, CertificationName, CertificationOrganization, CertificationValidityPeriod, CertificationDescription);

                    Model.getInstance().getAdminPageFactory().showEditCertificationsWindow(certificateModel, true);

                }

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

    public void onEditTraining(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch all columns of the selected certification
        String trainingDetailsQuery = "SELECT * FROM trainings WHERE TrainingDescription = ?";

        if(selectedTraining != null){
            try {
                PreparedStatement statement = connectDB.prepareStatement(trainingDetailsQuery);
                statement.setString(1, selectedTraining);
                ResultSet queryOutput = statement.executeQuery();

                // Check if a row was returned
                if (queryOutput.next()) {
                    // Retrieve values from the result set and create a Certification object
                    String TrainingParticipant = queryOutput.getString("TrainingParticipant");
                    Date TrainingDay = queryOutput.getDate("TrainingDay");
                    Time TrainingStart = queryOutput.getTime("TrainingStart");
                    Time TrainingEnd = queryOutput.getTime("TrainingEnd");
                    String TrainingInstructor = queryOutput.getString("TrainingInstructor");
                    String TrainingLocation = queryOutput.getString("TrainingLocation");
                    String TrainingDescription = queryOutput.getString("TrainingDescription");
                    String TrainingDesignation = queryOutput.getString("TrainingDesignation");


                    System.out.println("selectedCertificationName is " + TrainingParticipant);
                    System.out.println("CertificationOrganization is " + TrainingDay);
                    System.out.println("CertificationValidityPeriod is " + TrainingStart);
                    System.out.println("CertificationDescription is " + TrainingEnd);
                    System.out.println("CertificationDescription is " + TrainingInstructor);
                    System.out.println("CertificationDescription is " + TrainingLocation);
                    System.out.println("CertificationDescription is " + TrainingDescription);
                    System.out.println("CertificationDescription is " + TrainingDesignation);

                }

                Model.getInstance().getAdminPageFactory().showEditTrainingWindow();
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

    public void onEditRequest(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch all columns of the selected certification
        String requestDetailsQuery = "SELECT * FROM requests WHERE RequestDetails = ?";

        if(selectedRequest != null){
            try {
                PreparedStatement statement = connectDB.prepareStatement(requestDetailsQuery);
                statement.setString(1, selectedRequest);
                ResultSet queryOutput = statement.executeQuery();

                // Check if a row was returned
                if (queryOutput.next()) {
                    // Retrieve values from the result set and create a Certification object
                    String RequestName = queryOutput.getString("RequestName");
                    String RequestType = queryOutput.getString("RequestType");
                    Timestamp RequestDate = queryOutput.getTimestamp("RequestDate");
                    String RequestStatus = queryOutput.getString("RequestStatus");
                    String RequestDetails = queryOutput.getString("RequestDetails");


                    System.out.println("selectedCertificationName is " + RequestName);
                    System.out.println("CertificationOrganization is " + RequestType);
                    System.out.println("CertificationValidityPeriod is " + RequestDate);
                    System.out.println("CertificationDescription is " + RequestStatus);
                    System.out.println("CertificationDescription is " + RequestDetails);

                }

                Model.getInstance().getAdminPageFactory().showEditRequestWindow();
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


    public void setDepartmentTableView() {

        departmentInfoObservableList.clear();

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

        trainingsInfoObservableList.clear();

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

        requestsInfoObservableList.clear();

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

        certificationsInfoObservableList.clear();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to fetch department data
        String certificationListQuery = "SELECT `CertificationName`, `CertificationValidityPeriod` FROM certifications";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(certificationListQuery);


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


    public void createPieChart(AnchorPane paneGraph) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        int doctorCount = myDatabaseUtils.countRows("doctors");

        pieChartData.add(new PieChart.Data("Doctors", doctorCount));

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query t
        String countFunctionsQuery = "SELECT StaffDepartment, COUNT(*) AS count FROM staff GROUP BY StaffDepartment";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(countFunctionsQuery);


            while (queryOutput.next()) {
                String staffFunction = queryOutput.getString("StaffDepartment");
                int count = queryOutput.getInt("count");
                pieChartData.add(new PieChart.Data(staffFunction, count));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create PieChart
        PieChart pieChart = new PieChart(pieChartData);
        //pieChart.setTitle("Staff Functions");

        // Set properties
        pieChart.setLabelsVisible(true);
        pieChart.setPrefSize(500, 500);
        pieChart.setLegendVisible(true); // Show legend


        // Set custom label formatter
        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.TOP);

        // Add PieChart to the AnchorPane
        paneGraph.getChildren().add(pieChart);
        paneGraph.setStyle("-fx-background-color: #abcdef;");

    }
}
