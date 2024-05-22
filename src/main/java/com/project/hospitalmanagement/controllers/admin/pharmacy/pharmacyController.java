package com.project.hospitalmanagement.controllers.admin.pharmacy;

import com.project.hospitalmanagement.controllers.admin.doctors.doctorsController;
import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.general.lists.staffListCell;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.salesModel;
import com.project.hospitalmanagement.controllers.models.staffModel;
import com.project.hospitalmanagement.controllers.utilities.utilitiesFunction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.project.hospitalmanagement.controllers.general.lists.pharmacyList;

public class pharmacyController implements Initializable {
    public FontAwesomeIconView toSalesReport;
    public FontAwesomeIconView toPaymentReport;
    public FontAwesomeIconView toMedicinesList;
    public Label pharmacyHeadName;
    public Button editPharmacyHead;
    public Label weeklySummary;
    public Label dailySummary;
    public Label weeklyMarginPercent;
    public Label weeklyMarginValue;
    public Label dailyMarginPercent;
    public Label dailyMarginValue;
    public ListView<String> calendar;
    public TableView<salesModel> pharmacyOrders;
    public Button todayStaff_btn;
    public Button allStaff_btn;
    public ListView<staffModel> staffList;
    public Label stockValue;
    public Button test_btn;
    public TableColumn<salesModel, Integer> OrderID;
    public TableColumn<salesModel, String> Orderer;
    public TableColumn<salesModel, String> OrderDescription;

    ObservableList<salesModel> orderModelObservableList = FXCollections.observableArrayList();
    ObservableList<staffModel> list = FXCollections.observableArrayList();



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        refreshOrder();

        addListener();

        applyClickEffect();

    }

    public void addListener(){
        toMedicinesList.setOnMouseClicked(event -> goToMedicinesList());
        toPaymentReport.setOnMouseClicked(event -> generatePaymentReport());
        toSalesReport.setOnMouseClicked(event -> generateSalesReport());
        allStaff_btn.setOnMouseClicked(event -> showAllStaff());
        todayStaff_btn.setOnMouseClicked(event -> showAffectedStaff());

    }

    public void generateSalesReport(){

    }

    public void generatePaymentReport(){

    }

    public void goToMedicinesList(){
        Model.getInstance().getAdminPageFactory().showMedicinesWindow();
    }

    public void showAllStaff(){

        list.clear();

        pharmacyList myPharmacyList = new pharmacyList();

        myPharmacyList.retrievePharmacyAllStaff();

        list = myPharmacyList.getPharmacyAllStaffNames();

        staffList.setCellFactory(param -> new staffListCell()); // Set custom cell factory

        staffList.setItems(list);

    }

    public void applyClickEffect(){
        utilitiesFunction.applyClickEffect(toSalesReport);
        utilitiesFunction.applyClickEffect(toMedicinesList);
        utilitiesFunction.applyClickEffect(toPaymentReport);
    }

    public void showAffectedStaff(){
        pharmacyList myPharmacyList = new pharmacyList();

        myPharmacyList.retrievePharmacyAffectedStaff();

        list = myPharmacyList.getPharmacyAffectedStaffNames();

        staffList.setCellFactory(param -> new staffListCell()); // Set custom cell factory

        staffList.setItems(list);

    }

    public void refreshOrder() {

        orderModelObservableList.clear();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String ordersViewQuery = "SELECT `SaleID`, `EmployeeID`, `Remarks` FROM `sales` ORDER BY SaleDate DESC" +
                " LIMIT 5;";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(ordersViewQuery);

            while(queryOutput.next()) {


                Integer querySaleID = queryOutput.getInt("SaleID");
                String queryEmployeeID = queryOutput.getString("EmployeeID");
                String queryRemarks = queryOutput.getString("Remarks");


                //Populate the observableList
                orderModelObservableList.add(new salesModel(querySaleID, queryEmployeeID, queryRemarks));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            OrderID.setCellValueFactory(new PropertyValueFactory<>("SaleID"));
            Orderer.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
            OrderDescription.setCellValueFactory(new PropertyValueFactory<>("Remarks"));

            pharmacyOrders.setItems(orderModelObservableList);

            // Close the result set and the prepared statement
            queryOutput.close();

        } catch (SQLException e) {
            Logger.getLogger(doctorsController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }finally {
            try {
                // Close the database connection
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


}
