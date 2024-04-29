package com.project.hospitalmanagement.controllers.admin.billing;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.billModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class billsController implements Initializable{

    public TextField search_bar;
    public TableView<billModel> bills_tableView;

    public TableColumn<billModel, Void> Action;
    public TableColumn<billModel, Integer> BillID;
    public TableColumn<billModel, Integer> BillAdmissionID;
    public TableColumn<billModel, String> BillStatus;
    public TableColumn<billModel, String> BillPatientName;
    public TableColumn<billModel, Date> BillDate;
    public TableColumn<billModel, Integer> BillDiscount;
    public TableColumn<billModel, Integer> BillTotalPaid;

    ObservableList<billModel> billModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String billsViewQuery = "SELECT `BillID`, `BillAdmissionID`, `BillStatus`, `BillPatientName`, `BillDate`, `BillDiscount`, `BillTotalPaid` FROM `bills`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(billsViewQuery);

            while(queryOutput.next()){

                Integer queryBillID = queryOutput.getInt("BillID");
                Integer queryBillAdmissionID = queryOutput.getInt("BillAdmissionID");
                String queryBillStatus = queryOutput.getString("BillStatus");
                String queryBillPatientName = queryOutput.getString("BillPatientName");
                java.sql.Date queryBillDate = queryOutput.getDate("BillDate");
                Integer queryBillDiscount = queryOutput.getInt("BillDiscount");
                Integer queryBillTotalPaid = queryOutput.getInt("BillTotalPaid");

                //Populate the observableList
                billModelObservableList.add(new billModel(queryBillID, queryBillAdmissionID, queryBillStatus, queryBillPatientName, queryBillDate, queryBillDiscount, queryBillTotalPaid));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            BillID.setCellValueFactory(new PropertyValueFactory<>("BillID"));
            BillAdmissionID.setCellValueFactory(new PropertyValueFactory<>("BillAdmissionID"));
            BillStatus.setCellValueFactory(new PropertyValueFactory<>("BillStatus"));
            BillPatientName.setCellValueFactory(new PropertyValueFactory<>("BillPatientName"));
            BillDate.setCellValueFactory(new PropertyValueFactory<>("BillDate"));
            BillDiscount.setCellValueFactory(new PropertyValueFactory<>("BillDiscount"));
            BillTotalPaid.setCellValueFactory(new PropertyValueFactory<>("BillTotalPaid"));

            //Action.setCellFactory(param -> new ActionButtonTableCell<>());

            bills_tableView.setItems(billModelObservableList);

            //Initial filtered list
            FilteredList<billModel> filteredData = new FilteredList<>(billModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(bill -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();


                    if (bill.getBillAdmissionID().toString().indexOf(searchKeyword) > -1){
                        return true;    //Means a match was found in Bill column
                    } else if (bill.getBillStatus().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (bill.getBillPatientName().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (bill.getBillDate().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;   //no match found
                });
            });

            SortedList<billModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(bills_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            bills_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(billsController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

}

