package com.project.hospitalmanagement.controllers.admin.inventory;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.inventoryModel;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class inventoryController implements Initializable{

    public TextField search_bar;
    public TableView<inventoryModel> inventory_tableView;
    public TableColumn<inventoryModel, Integer> InventoryID;
    public TableColumn<inventoryModel, String> InventoryObjectName;
    public TableColumn<inventoryModel, String> InventoryObjectCategory;
    public TableColumn<inventoryModel, String> InventoryObjectStock;
    public TableColumn<inventoryModel, Date> InventoryObjectPurchaseDate;
    public TableColumn<inventoryModel, String> InventoryObjectDescription;
    public TableColumn<inventoryModel, Integer> InventoryObjectPrice;
    public TableColumn<inventoryModel, String> InventoryObjectState;

    public TableColumn<inventoryModel, Void> Action;
    public AnchorPane BirthAndDeathChart;


    ObservableList<inventoryModel> inventoryModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String inventoryViewQuery = "SELECT `InventoryID`, `InventoryObjectName`, `InventoryObjectCategory`, `InventoryObjectStock`, `InventoryObjectPurchaseDate`, `InventoryObjectDescription`, `InventoryObjectPrice`, `InventoryObjectState` FROM `inventory`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(inventoryViewQuery);

            while(queryOutput.next()){

                Integer queryInventoryID = queryOutput.getInt("InventoryID");
                String queryInventoryObjectName = queryOutput.getString("InventoryObjectName");
                String queryInventoryObjectCategory = queryOutput.getString("InventoryObjectCategory");
                String queryInventoryObjectStock = queryOutput.getString("InventoryObjectStock");
                Date queryInventoryObjectPurchaseDate = queryOutput.getDate("InventoryObjectPurchaseDate");
                String queryInventoryObjectDescription = queryOutput.getString("InventoryObjectDescription");
                Integer queryInventoryObjectPrice = queryOutput.getInt("InventoryObjectPrice");
                String queryInventoryObjectState = queryOutput.getString("InventoryObjectState");

                //Populate the observableList
                inventoryModelObservableList.add(new inventoryModel(queryInventoryID, queryInventoryObjectName, queryInventoryObjectCategory, queryInventoryObjectStock, queryInventoryObjectPurchaseDate, queryInventoryObjectDescription, queryInventoryObjectPrice, queryInventoryObjectState));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            InventoryID.setCellValueFactory(new PropertyValueFactory<>("InventoryID"));
            InventoryObjectName.setCellValueFactory(new PropertyValueFactory<>("InventoryObjectName"));
            InventoryObjectCategory.setCellValueFactory(new PropertyValueFactory<>("InventoryObjectCategory"));
            InventoryObjectStock.setCellValueFactory(new PropertyValueFactory<>("InventoryObjectStock"));
            InventoryObjectPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("InventoryObjectPurchaseDate"));
            InventoryObjectDescription.setCellValueFactory(new PropertyValueFactory<>("InventoryObjectDescription"));
            InventoryObjectPrice.setCellValueFactory(new PropertyValueFactory<>("InventoryObjectPrice"));
            InventoryObjectState.setCellValueFactory(new PropertyValueFactory<>("InventoryObjectState"));

            //Action.setCellFactory(param -> new ActionButtonTableCell<>());

            inventory_tableView.setItems(inventoryModelObservableList);

            //Initial filtered list
            FilteredList<inventoryModel> filteredData = new FilteredList<>(inventoryModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(inventory -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();


                    if (inventory.getInventoryObjectName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;    //Means a match was found in Inventory column
                    } else if (inventory.getInventoryObjectCategory().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (inventory.getInventoryObjectDescription().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;   //no match found
                });
            });

            SortedList<inventoryModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(inventory_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            inventory_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(inventoryController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

}

