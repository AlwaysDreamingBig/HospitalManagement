package com.project.hospitalmanagement.controllers.admin.billing;

import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.salaryBillModel;
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

public class salaryBillsController implements Initializable{

    public TextField search_bar;
    public TableView<salaryBillModel> salaryBills_tableView;

    public TableColumn<salaryBillModel, Void> Action;
    public TableColumn<salaryBillModel, Integer> SalaryBillID;
    public TableColumn<salaryBillModel, Integer> SalaryBillPaymentID;
    public TableColumn<salaryBillModel, String> SalaryBillStatus;
    public TableColumn<salaryBillModel, String> SalaryBillStaffName;
    public TableColumn<salaryBillModel, Date> SalaryBillDate;
    public TableColumn<salaryBillModel, Integer> SalaryBillAdvantages;
    public TableColumn<salaryBillModel, Integer> SalaryBillTotalPaid;

    ObservableList<salaryBillModel> salaryBillModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String salaryBillsViewQuery = "SELECT `SalaryBillID`, `SalaryBillPaymentID`, `SalaryBillStatus`, `SalaryBillStaffName`, `SalaryBillDate`, `SalaryBillAdvantages`, `SalaryBillTotalPaid` FROM `salaryBills`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(salaryBillsViewQuery);

            while(queryOutput.next()){

                Integer querySalaryBillID = queryOutput.getInt("SalaryBillID");
                Integer querySalaryBillPaymentID = queryOutput.getInt("SalaryBillPaymentID");
                String querySalaryBillStatus = queryOutput.getString("SalaryBillStatus");
                String querySalaryBillStaffName = queryOutput.getString("SalaryBillStaffName");
                java.sql.Date querySalaryBillDate = queryOutput.getDate("SalaryBillDate");
                Integer querySalaryBillAdvantages = queryOutput.getInt("SalaryBillAdvantages");
                Integer querySalaryBillTotalPaid = queryOutput.getInt("SalaryBillTotalPaid");

                //Populate the observableList
                salaryBillModelObservableList.add(new salaryBillModel(querySalaryBillID, querySalaryBillPaymentID, querySalaryBillStatus, querySalaryBillStaffName, querySalaryBillDate, querySalaryBillAdvantages, querySalaryBillTotalPaid));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            SalaryBillID.setCellValueFactory(new PropertyValueFactory<>("SalaryBillID"));
            SalaryBillPaymentID.setCellValueFactory(new PropertyValueFactory<>("SalaryBillPaymentID"));
            SalaryBillStatus.setCellValueFactory(new PropertyValueFactory<>("SalaryBillStatus"));
            SalaryBillStaffName.setCellValueFactory(new PropertyValueFactory<>("SalaryBillStaffName"));
            SalaryBillDate.setCellValueFactory(new PropertyValueFactory<>("SalaryBillDate"));
            SalaryBillAdvantages.setCellValueFactory(new PropertyValueFactory<>("SalaryBillAdvantages"));
            SalaryBillTotalPaid.setCellValueFactory(new PropertyValueFactory<>("SalaryBillTotalPaid"));

            //Action.setCellFactory(param -> new ActionButtonTableCell<>());

            salaryBills_tableView.setItems(salaryBillModelObservableList);

            //Initial filtered list
            FilteredList<salaryBillModel> filteredData = new FilteredList<>(salaryBillModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(salaryBill -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();


                    if (salaryBill.getSalaryBillPaymentID().toString().indexOf(searchKeyword) > -1){
                        return true;    //Means a match was found in SalaryBill column
                    } else if (salaryBill.getSalaryBillStatus().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (salaryBill.getSalaryBillStaffName().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (salaryBill.getSalaryBillDate().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;   //no match found
                });
            });

            SortedList<salaryBillModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(salaryBills_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            salaryBills_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(salaryBillsController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

}

