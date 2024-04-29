package com.project.hospitalmanagement.controllers.admin.calls;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.callModel;
import com.project.hospitalmanagement.controllers.utilities.ActionButtonTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class callsController implements Initializable{

    @FXML
    private TextField search_bar;
    @FXML
    private TableView<callModel> calls_tableView;
    @FXML
    private Button UploadPicture;
    @FXML
    private TableColumn<callModel, Integer> CallID;
    @FXML
    private TableColumn<callModel, Date> CallDate;
    @FXML
    private TableColumn<callModel, String> CallerName;
    @FXML
    private TableColumn<callModel, String> CallerGender;
    @FXML
    private TableColumn<callModel, String> CallerDriver;
    @FXML
    private TableColumn<callModel, Integer> AssignedAmbulance;
    @FXML
    private TableColumn<callModel, Date> AmbulanceDeparture;
    @FXML
    private TableColumn<callModel, Date> AmbulanceArrival;
    @FXML
    private TableColumn<callModel, String> CallerAddress;
    @FXML
    private TableColumn<callModel, String> CallerIssue;
    @FXML
    private TableColumn<callModel, Void> Action;


    ObservableList<callModel> callModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String callsViewQuery = "SELECT `CallID`, `CallDate`, `CallerName`, `CallerGender`, `CallerDriver`, `AssignedAmbulance`, `AmbulanceDeparture`, `AmbulanceArrival`, `CallerAddress`, `CallerIssue` FROM `calls`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(callsViewQuery);

            while(queryOutput.next()){

                Integer queryCallID = queryOutput.getInt("CallID");
                java.util.Date queryCallDate = queryOutput.getDate("CallDate");
                String queryCallerName = queryOutput.getString("CallerName");
                String queryCallerGender = queryOutput.getString("CallerGender");
                String queryCallerDriver = queryOutput.getString("CallerDriver");
                Integer queryAssignedAmbulance = queryOutput.getInt("AssignedAmbulance");
                java.sql.Date queryAmbulanceDeparture = queryOutput.getDate("AmbulanceDeparture");
                java.sql.Date queryAmbulanceArrival = queryOutput.getDate("AmbulanceArrival");
                String queryCallCallerAddress = queryOutput.getString("CallerAddress");
                String queryCallerIssue = queryOutput.getString("CallerIssue");

                //Populate the observableList
                callModelObservableList.add(new callModel(queryCallID, queryCallDate, queryCallerName, queryCallerGender, queryCallerDriver, queryAssignedAmbulance, queryAmbulanceDeparture, queryAmbulanceArrival, queryCallCallerAddress, queryCallerIssue));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            CallID.setCellValueFactory(new PropertyValueFactory<>("CallID"));
            CallDate.setCellValueFactory(new PropertyValueFactory<>("CallDate"));
            CallerName.setCellValueFactory(new PropertyValueFactory<>("CallerName"));
            CallerGender.setCellValueFactory(new PropertyValueFactory<>("CallerGender"));
            CallerDriver.setCellValueFactory(new PropertyValueFactory<>("CallerDriver"));
            AssignedAmbulance.setCellValueFactory(new PropertyValueFactory<>("AssignedAmbulance"));
            AmbulanceDeparture.setCellValueFactory(new PropertyValueFactory<>("AmbulanceDeparture"));
            AmbulanceArrival.setCellValueFactory(new PropertyValueFactory<>("AmbulanceArrival"));
            CallerAddress.setCellValueFactory(new PropertyValueFactory<>("CallerAddress"));
            CallerIssue.setCellValueFactory(new PropertyValueFactory<>("CallerIssue"));
            Action.setCellFactory(param -> new ActionButtonTableCell<>());

            calls_tableView.setItems(callModelObservableList);

            //Initial filtered list
            FilteredList<callModel> filteredData = new FilteredList<>(callModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(call -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();


                    if (call.getCallerName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;    //Means a match was found in Call column
                    } else if (call.getCallerDriver().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (call.getAssignedAmbulance().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (call.getCallDate().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;   //no match found
                });
            });

            SortedList<callModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(calls_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            calls_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(callsController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

}

