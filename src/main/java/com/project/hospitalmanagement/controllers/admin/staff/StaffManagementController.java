package com.project.hospitalmanagement.controllers.admin.staff;

import com.project.hospitalmanagement.controllers.alert.alertMessage;
import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.*;
import com.project.hospitalmanagement.controllers.utilities.myDatabaseUtils.myDatabaseUtils;
import com.project.hospitalmanagement.controllers.utilities.utilitiesFunction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class StaffManagementController implements Initializable {
    public Button removeDepartment_btn;
    public Button editDepartment_btn;
    public ListView<String> trainingList;
    public ListView<String> RequestList;
    public ListView<String> certificationList;
    public Button StaffMembers_btn;
    public TableView<departmentModel> departmentTable;
    public TableColumn<departmentModel, String> DepartmentName;
    public TableColumn<departmentModel, String> DepartmentHead;
    public AnchorPane StaffManagementPane;
    public AnchorPane repartitionGraph;
    private final alertMessage alert = new alertMessage();
    public FontAwesomeIconView refreshCertification_btn;
    public FontAwesomeIconView refreshRequests_btn;
    public FontAwesomeIconView refreshDepartment_btn;
    public FontAwesomeIconView refreshTraining_btn;
    public FontAwesomeIconView addCertification_btn;
    public FontAwesomeIconView removeCertification_btn;
    public FontAwesomeIconView editCertification_btn;
    public FontAwesomeIconView addRequest_btn;
    public FontAwesomeIconView removeRequest_btn;
    public FontAwesomeIconView editRequest_btn;
    public FontAwesomeIconView seeRequests_btn;
    public FontAwesomeIconView addTraining_btn;
    public FontAwesomeIconView removeTraining_btn;
    public FontAwesomeIconView editTraining_btn;
    public FontAwesomeIconView seeTrainingss_btn;
    public FontAwesomeIconView seeCertification_btn;
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
        addRequest_btn.setOnMouseClicked(event -> onAddRequest());
        addTraining_btn.setOnMouseClicked(event -> onAddTraining());

        refreshCertification_btn.setOnMouseClicked(event -> onRefreshCertification());
        refreshRequests_btn.setOnMouseClicked(event -> onRefreshRequest());
        refreshTraining_btn.setOnMouseClicked(event -> onRefreshTraining());
        refreshDepartment_btn.setOnMouseClicked(event -> onRefreshDepartment());

        StaffMembers_btn.setOnMouseClicked(event -> onStaffMembers());

        applyClickEffect();

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

    public void onAddRequest(){
        Model.getInstance().getAdminPageFactory().showAddRequestWindow();
    }

    public void onAddTraining(){
        Model.getInstance().getAdminPageFactory().showAddTrainingWindow();
    }

    public void onRefreshRequest(){
        setRequestListView();
    }

    public void onRefreshTraining(){
        setTrainingListView();
    }

    public void onRefreshDepartment(){
        setDepartmentTableView();
    }

    public void onRefreshCertification(){
        setCertificationListView();
    }

    public void applyClickEffect(){
        utilitiesFunction.applyClickEffect(editCertification_btn);
        utilitiesFunction.applyClickEffect(editRequest_btn);
        utilitiesFunction.applyClickEffect(editTraining_btn);
        utilitiesFunction.applyClickEffect(addCertification_btn);
        utilitiesFunction.applyClickEffect(addTraining_btn);
        utilitiesFunction.applyClickEffect(addRequest_btn);
        utilitiesFunction.applyClickEffect(removeCertification_btn);
        utilitiesFunction.applyClickEffect(removeRequest_btn);
        utilitiesFunction.applyClickEffect(removeTraining_btn);
        utilitiesFunction.applyClickEffect(refreshCertification_btn);
        utilitiesFunction.applyClickEffect(refreshDepartment_btn);
        utilitiesFunction.applyClickEffect(refreshRequests_btn);
        utilitiesFunction.applyClickEffect(refreshTraining_btn);
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


                    alert.successMessage("Deleted from the table.");

                    setCertificationListView();

                }else {
                    alert.errorMessage("Nothing was Deleted from the table .");
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

                    alert.successMessage("Deleted from the table.");

                    setTrainingListView();

                }else {

                    alert.errorMessage("Nothing was Deleted from the table .");
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

                departmentModel department = null;

                // Check if a row was returned
                if (queryOutput.next()) {
                    // Retrieve values from the result set and create a Certification object
                    int DepartmentId = queryOutput.getInt("DepartmentID");
                    String DepartmentName = queryOutput.getString("DepartmentName");
                    String DepartmentHead = queryOutput.getString("DepartmentHead");

                    System.out.println("CertificationValidityPeriod is " + DepartmentName);
                    System.out.println("CertificationDescription is " + DepartmentHead);

                    department = new departmentModel(DepartmentId, DepartmentName, DepartmentHead);
                }
                Model.getInstance().getAdminPageFactory().showEditDepartmentsWindow(department);

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

                trainingModel training = null;
                // Check if a row was returned
                if (queryOutput.next()) {
                    // Retrieve values from the result set and create a Certification object
                    Integer TrainingID = queryOutput.getInt("TrainingID");
                    String TrainingParticipant = queryOutput.getString("TrainingParticipant");
                    Date TrainingDay = queryOutput.getDate("TrainingDay");
                    Time TrainingStart = queryOutput.getTime("TrainingStart");
                    Time TrainingEnd = queryOutput.getTime("TrainingEnd");
                    String TrainingInstructor = queryOutput.getString("TrainingInstructor");
                    String TrainingLocation = queryOutput.getString("TrainingLocation");
                    String TrainingDescription = queryOutput.getString("TrainingDescription");
                    String TrainingDesignation = queryOutput.getString("TrainingDesignation");

                    training = new trainingModel(TrainingID, TrainingParticipant, TrainingDay, TrainingStart, TrainingEnd, TrainingInstructor, TrainingLocation, TrainingDescription, TrainingDesignation);


                }

                Model.getInstance().getAdminPageFactory().showEditTrainingWindow(training, true);
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

                requestModel request = null;

                // Check if a row was returned
                if (queryOutput.next()) {
                    // Retrieve values from the result set and create a Certification object
                    int RequestID = queryOutput.getInt("RequestID");
                    String RequestName = queryOutput.getString("RequestName");
                    String RequestType = queryOutput.getString("RequestType");
                    Date RequestDate = queryOutput.getDate("RequestDate");
                    String RequestStatus = queryOutput.getString("RequestStatus");
                    String RequestDetails = queryOutput.getString("RequestDetails");

                    request = new requestModel(RequestID, RequestName, RequestType, RequestDate, RequestStatus, RequestDetails);

                }

                Model.getInstance().getAdminPageFactory().showEditRequestWindow(request, true);
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
