package com.project.hospitalmanagement.controllers.admin.rooms;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.roomModel;
import com.project.hospitalmanagement.controllers.utilities.ActionButtonTableCell;
import com.project.hospitalmanagement.controllers.utilities.utilitiesFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class roomsController implements Initializable{

    public TextField search_bar;

    public TableColumn<roomModel, Void> Action;
    public TableView<roomModel> rooms_tableView;
    public TableColumn<roomModel, Integer> RoomStatus;
    public TableColumn<roomModel, Integer> RoomNumber;
    public TableColumn<roomModel, String> RoomType;
    public TableColumn<roomModel, String> RoomPatient;
    public TableColumn<roomModel, String> RoomPatientGender;
    public TableColumn<roomModel, java.sql.Date> RoomEntry;
    public TableColumn<roomModel, java.sql.Date> RoomDeparture;

    public TableColumn<roomModel, Void> action;
    public CheckBox standardCheckbox;
    public CheckBox suiteCheckbox;
    public CheckBox deluxeCheckbox;
    public CheckBox maleCheckbox;
    public CheckBox femaleCheckbox;
    public CheckBox vacantCheckbox;
    public CheckBox occupiedCheckbox;
    public CheckBox janMarchCheckbox;
    public CheckBox aprilJuneCheckbox;
    public CheckBox julySeptCheckbox;
    public CheckBox octDecCheckbox;

    public Button apply;
    public Button showAll;
    public Button addAllotment;

    ObservableList<roomModel> roomModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        refreshRooms();

        addListener();

    }

    public void addListener(){
        apply.setOnMouseClicked(event -> filterRibon());
        showAll.setOnMouseClicked(event -> refreshRooms());
        addAllotment.setOnMouseClicked(event -> onAddAllotment());
    }

    public void onAddAllotment(){
        Model.getInstance().getAdminPageFactory().showAddRoom();
    }

    public void refreshRooms() {

        unCheckBox();

        roomModelObservableList.clear();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String roomsViewQuery = "SELECT `RoomAllocationID`, `RoomNumber`, `RoomType`, `RoomPatient`, `RoomPatientGender`, `RoomEntry`, `RoomDeparture`, `RoomStatus`, `RoomPrice` FROM `room`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(roomsViewQuery);

            while(queryOutput.next()){

                Integer queryRoomID = queryOutput.getInt("RoomAllocationID");
                Integer queryRoomNumber = queryOutput.getInt("RoomNumber");
                String queryRoomStatus = queryOutput.getString("RoomStatus");
                String queryRoomType = queryOutput.getString("RoomType");
                String queryRoomPatient = queryOutput.getString("RoomPatient");
                String queryRoomPatientGender = queryOutput.getString("RoomPatientGender");
                Date queryRoomEntry = queryOutput.getDate("RoomEntry");
                Date queryRoomDeparture = queryOutput.getDate("RoomDeparture");


                //Populate the observableList
                roomModelObservableList.add(new roomModel(queryRoomNumber, queryRoomStatus, queryRoomType, queryRoomPatient, queryRoomPatientGender, queryRoomEntry, queryRoomDeparture));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            RoomNumber.setCellValueFactory(new PropertyValueFactory<>("RoomNumber"));
            RoomStatus.setCellValueFactory(new PropertyValueFactory<>("RoomStatus"));
            RoomType.setCellValueFactory(new PropertyValueFactory<>("RoomType"));
            RoomPatient.setCellValueFactory(new PropertyValueFactory<>("RoomPatient"));
            RoomPatientGender.setCellValueFactory(new PropertyValueFactory<>("RoomPatientGender"));
            RoomEntry.setCellValueFactory(new PropertyValueFactory<>("RoomEntry"));
            RoomDeparture.setCellValueFactory(new PropertyValueFactory<>("RoomDeparture"));
            action.setCellFactory(param -> new ActionButtonTableCell<>());

            rooms_tableView.setItems(roomModelObservableList);

            //Initial filtered list
            FilteredList<roomModel> filteredData = new FilteredList<>(roomModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(room -> {

                    // Check if the search input is empty or blank
                    if (newValue == null || newValue.isBlank()) {
                        return true; // Show all rooms
                    }

                    String searchKeyword = newValue.toLowerCase();

                    // Check room number
                    if (room.getRoomNumber() != null
                            && room.getRoomNumber().toString().contains(searchKeyword)) {
                        return true; // Means a match was found in Room Number column
                    }

                    // Check room patient
                    if (room.getRoomPatient() != null
                            && room.getRoomPatient().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }

                    // Check room type
                    if (room.getRoomType() != null
                            && room.getRoomType().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }

                    // Check room entry date
                    if (room.getRoomEntry() != null
                            && room.getRoomEntry().toString().contains(searchKeyword)) {
                        return true;
                    }

                    // Check room departure date
                    if (room.getRoomDeparture() != null
                            && room.getRoomDeparture().toString().contains(searchKeyword)) {
                        return true;
                    }

                    // No match found
                    return false;
                });
            });


            SortedList<roomModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(rooms_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            rooms_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(roomsController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public void filterRibon(){

        CheckBox[] RoomTypeCheckboxes = {standardCheckbox, suiteCheckbox, deluxeCheckbox};
        CheckBox[] RoomStatusCheckboxes = {occupiedCheckbox, vacantCheckbox};
        CheckBox[] RoomGenderCheckboxes = {maleCheckbox, femaleCheckbox};

        String[] Type = utilitiesFunction.getCheckedLabels(RoomTypeCheckboxes);
        String[] Status = utilitiesFunction.getCheckedLabels(RoomStatusCheckboxes);
        String[] Gender = utilitiesFunction.getCheckedLabels(RoomGenderCheckboxes);

        // Check if at least one checkbox is checked for each category
        if (Type.length == 0) {
            Type = new String[]{"Standard", "Suite", "Deluxe",""};
        }
        if (Status.length == 0) {
            Status = new String[]{"Occupied", "Vacant",""};
        }
        if (Gender.length == 0) {
            Gender = new String[]{"Male", "Female", ""};
        }

        utilitiesFunction.applyFilter(rooms_tableView, 2, Type);
        utilitiesFunction.applyFilter(rooms_tableView, 1, Status);
        utilitiesFunction.applyFilter(rooms_tableView, 4, Gender);


    }

    public void unCheckBox(){
        standardCheckbox.setSelected(false);
        suiteCheckbox.setSelected(false);
        deluxeCheckbox.setSelected(false);
        maleCheckbox.setSelected(false);
        femaleCheckbox.setSelected(false);
        occupiedCheckbox.setSelected(false);
        vacantCheckbox.setSelected(false);
        janMarchCheckbox.setSelected(false);
        aprilJuneCheckbox.setSelected(false);
        julySeptCheckbox.setSelected(false);
        octDecCheckbox.setSelected(false);
    }
}

