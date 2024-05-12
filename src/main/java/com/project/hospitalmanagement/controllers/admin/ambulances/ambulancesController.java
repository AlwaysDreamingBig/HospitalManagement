package com.project.hospitalmanagement.controllers.admin.ambulances;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.ambulanceModel;
import com.project.hospitalmanagement.controllers.utilities.ActionButtonTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ambulancesController implements Initializable{

    public TextField search_bar;

    public Button UploadPicture;
    public TableColumn<ambulanceModel, Integer> AmbulanceID;
    public TableColumn<ambulanceModel, Integer> AmbulanceNumber;
    public TableColumn<ambulanceModel, String> AmbulanceManufacturer;
    public TableColumn<ambulanceModel, String> AmbulanceDesignation;
    public TableColumn<ambulanceModel, Integer> AmbulanceWarranty;
    public TableColumn<ambulanceModel, String> AmbulanceSeller;
    public TableColumn<ambulanceModel, Date> AmbulanceMadeYear;
    public TableColumn<ambulanceModel, String> AmbulanceDriver;
    public TableColumn<ambulanceModel, String> AmbulanceContract;

    public TableColumn<ambulanceModel, Void> Action;
    public TableView<ambulanceModel> ambulance_tableView;
    public Button registerAmbulance;
    public Button refreshAmbulance;


    ObservableList<ambulanceModel> ambulanceModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        refreshAmbulances();

        addListener();

    }
    public void addListener(){
        refreshAmbulance.setOnMouseClicked(event -> refreshAmbulances());
        registerAmbulance.setOnMouseClicked(event -> onRegisterAmbulance());
    }

    public void onRegisterAmbulance(){
        Model.getInstance().getAdminPageFactory().showAddAmbulance();
    }
    public void refreshAmbulances(){
         ambulanceModelObservableList.clear();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String ambulancesViewQuery = "SELECT `AmbulanceID`, `AmbulanceNumber`, `AmbulanceManufacturer`, `AmbulanceDesignation`, `AmbulanceWarranty`, `AmbulanceSeller`, `AmbulanceMadeYear`, `AmbulanceDriver`, `AmbulanceContract` FROM `ambulances`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(ambulancesViewQuery);

            while(queryOutput.next()){

                Integer queryAmbulanceID = queryOutput.getInt("AmbulanceID");
                Integer queryAmbulanceNumber = queryOutput.getInt("AmbulanceNumber");
                String queryAmbulanceManufacturer = queryOutput.getString("AmbulanceManufacturer");
                String queryAmbulanceDesignation = queryOutput.getString("AmbulanceDesignation");
                Integer queryAmbulanceWarranty = queryOutput.getInt("AmbulanceWarranty");
                String queryAmbulanceSeller = queryOutput.getString("AmbulanceSeller");
                java.sql.Date queryAmbulanceMadeYear = queryOutput.getDate("AmbulanceMadeYear");
                String queryAmbulanceDriver = queryOutput.getString("AmbulanceDriver");
                String queryAmbulanceContract = queryOutput.getString("AmbulanceContract");

                //Populate the observableList
                ambulanceModelObservableList.add(new ambulanceModel(queryAmbulanceID, queryAmbulanceNumber, queryAmbulanceManufacturer, queryAmbulanceDesignation, queryAmbulanceWarranty, queryAmbulanceSeller, queryAmbulanceMadeYear, queryAmbulanceDriver, queryAmbulanceContract));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            AmbulanceID.setCellValueFactory(new PropertyValueFactory<>("AmbulanceID"));
            AmbulanceNumber.setCellValueFactory(new PropertyValueFactory<>("AmbulanceNumber"));
            AmbulanceManufacturer.setCellValueFactory(new PropertyValueFactory<>("AmbulanceManufacturer"));
            AmbulanceDesignation.setCellValueFactory(new PropertyValueFactory<>("AmbulanceDesignation"));
            AmbulanceWarranty.setCellValueFactory(new PropertyValueFactory<>("AmbulanceWarranty"));
            AmbulanceSeller.setCellValueFactory(new PropertyValueFactory<>("AmbulanceSeller"));
            AmbulanceMadeYear.setCellValueFactory(new PropertyValueFactory<>("AmbulanceMadeYear"));
            AmbulanceDriver.setCellValueFactory(new PropertyValueFactory<>("AmbulanceDriver"));
            AmbulanceContract.setCellValueFactory(new PropertyValueFactory<>("AmbulanceContract"));
            Action.setCellFactory(param -> new ActionButtonTableCell<>());

            ambulance_tableView.setItems(ambulanceModelObservableList);

            //Initial filtered list
            FilteredList<ambulanceModel> filteredData = new FilteredList<>(ambulanceModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(ambulance -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();


                    if (ambulance.getAmbulanceManufacturer().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;    //Means a match was found in Ambulance column
                    } else if (ambulance.getAmbulanceMadeYear().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (ambulance.getAmbulanceSeller().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (ambulance.getAmbulanceContract().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (ambulance.getAmbulanceDriver().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;   //no match found
                });
            });

            SortedList<ambulanceModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(ambulance_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            ambulance_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(ambulancesController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

}

