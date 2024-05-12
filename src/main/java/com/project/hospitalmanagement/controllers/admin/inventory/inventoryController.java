package com.project.hospitalmanagement.controllers.admin.inventory;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.inventoryModel;
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
    public CheckBox emptyCheckbox;
    public CheckBox warningCheckbox;
    public CheckBox availableCheckbox;
    public CheckBox unavailableCheckbox;
    public CheckBox brokenCheckbox;
    public CheckBox orderedCheckbox;

    public Button apply;
    public Button showAll;
    public ListView<String> ordersList;
    public Button addItem;

    public Button refresh;
    public Button newOrder;


    ObservableList<inventoryModel> inventoryModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addListener();
        refreshInventory();

    }

    public void addListener(){
        refresh.setOnMouseClicked(event -> refreshInventory());
        apply.setOnMouseClicked(event -> filterRibon());
        showAll.setOnMouseClicked(event -> refreshInventory());
        addItem.setOnMouseClicked(event -> onAddItem());
        newOrder.setOnMouseClicked(event -> onNewOrder());
    }

    public void onAddItem(){
        Model.getInstance().getAdminPageFactory().showAddInventoryItem();
    }
    public void onNewOrder(){
        Model.getInstance().getAdminPageFactory().showAddNewOrder();
    }


    public void refreshInventory(){

        unCheckBox();

        inventoryModelObservableList.clear();

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

    public void filterRibon(){

        CheckBox[] StockNumberCheckboxes = {emptyCheckbox, warningCheckbox};
        CheckBox[] StateCheckboxes = {availableCheckbox, unavailableCheckbox,orderedCheckbox, brokenCheckbox};


        String[] Stock = utilitiesFunction.getCheckedLabels(StockNumberCheckboxes);
        String[] State = utilitiesFunction.getCheckedLabels(StateCheckboxes);

        // Check if at least one checkbox is checked for each category
        if (Stock.length == 0) {
            Stock = new String[]{"Empty", "Warning",""};
        }
        if (State.length == 0) {
            State = new String[]{"Available", "Unavailable", "Broken", "Ordered",""};
        }

        utilitiesFunction.applyIntegerFilter(inventory_tableView, 3, Stock);
        utilitiesFunction.applyFilter(inventory_tableView, 7, State);

    }

    public void unCheckBox(){
        emptyCheckbox.setSelected(false);
        warningCheckbox.setSelected(false);
        unavailableCheckbox.setSelected(false);
        availableCheckbox.setSelected(false);
        brokenCheckbox.setSelected(false);
        orderedCheckbox.setSelected(false);

    }

}

