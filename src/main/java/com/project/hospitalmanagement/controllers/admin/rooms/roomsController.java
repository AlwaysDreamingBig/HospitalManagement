package com.project.hospitalmanagement.controllers.admin.rooms;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.roomModel;
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

public class roomsController implements Initializable{

    public TextField search_bar;

    public TableColumn<roomModel, Void> Action;
    public TableView<roomModel> rooms_tableView;
    public TableColumn<roomModel, Integer> RoomAllocationID;
    public TableColumn<roomModel, Integer> RoomNumber;
    public TableColumn<roomModel, String> RoomType;
    public TableColumn<roomModel, String> RoomPatient;
    public TableColumn<roomModel, String> RoomPatientGender;
    public TableColumn<roomModel, java.sql.Date> RoomEntry;
    public TableColumn<roomModel, java.sql.Date> RoomDeparture;

    ObservableList<roomModel> roomModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String roomsViewQuery = "SELECT `RoomAllocationID`, `RoomNumber`, `RoomType`, `RoomPatient`, `RoomPatientGender`, `RoomEntry`, `RoomDeparture` FROM `rooms`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(roomsViewQuery);

            while(queryOutput.next()){

                Integer queryRoomID = queryOutput.getInt("RoomAllocationID");
                Integer queryRoomNumber = queryOutput.getInt("RoomNumber");
                String queryRoomType = queryOutput.getString("RoomType");
                String queryRoomPatient = queryOutput.getString("RoomPatient");
                String queryRoomPatientGender = queryOutput.getString("RoomPatientGender");
                Date queryRoomEntry = queryOutput.getDate("RoomEntry");
                Date queryRoomDeparture = queryOutput.getDate("RoomDeparture");

                //Populate the observableList
                roomModelObservableList.add(new roomModel(queryRoomID, queryRoomNumber, queryRoomType, queryRoomPatient, queryRoomPatientGender, queryRoomEntry, queryRoomDeparture));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            RoomAllocationID.setCellValueFactory(new PropertyValueFactory<>("roomAllocationID"));
            RoomNumber.setCellValueFactory(new PropertyValueFactory<>("RoomName"));
            RoomType.setCellValueFactory(new PropertyValueFactory<>("RoomSpecialization"));
            RoomPatient.setCellValueFactory(new PropertyValueFactory<>("RoomDepartment"));
            RoomPatientGender.setCellValueFactory(new PropertyValueFactory<>("RoomEmail"));
            RoomEntry.setCellValueFactory(new PropertyValueFactory<>("RoomMobile"));
            RoomDeparture.setCellValueFactory(new PropertyValueFactory<>("RoomEntryDate"));
            //Action.setCellFactory(param -> new ActionButtonTableCell<>());

            rooms_tableView.setItems(roomModelObservableList);

            //Initial filtered list
            FilteredList<roomModel> filteredData = new FilteredList<>(roomModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(room -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();


                    if (room.getRoomNumber().toString().indexOf(searchKeyword) > -1){
                        return true;    //Means a match was found in Room column
                    } else if (room.getRoomType().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (room.getRoomEntry().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (room.getRoomDeparture().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;   //no match found
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

}

