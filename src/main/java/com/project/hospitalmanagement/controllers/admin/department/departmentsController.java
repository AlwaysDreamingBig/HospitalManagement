package com.project.hospitalmanagement.controllers.admin.department;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.departmentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class departmentsController implements Initializable{

    public TextField search_bar;
    public TableView<departmentModel> departments_tableView;


    public TableColumn<departmentModel, Void> Action;
    public TableColumn<departmentModel, Integer> DepartmentID;
    public TableColumn<departmentModel, String> DepartmentName;
    public TableColumn<departmentModel, String> DepartmentHead;
    public TableColumn<departmentModel, String> DepartmentStatus;
    public TableColumn<departmentModel, Integer> DepartmentSize;
    public TableColumn<departmentModel, String> DepartmentDescription;
    public TableColumn<departmentModel, Date> DepartmentCreation;

    ObservableList<departmentModel> departmentModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String departmentsViewQuery = "SELECT `DepartmentID`, `DepartmentName`, `DepartmentHead`, `DepartmentStatus`, `DepartmentSize`, `DepartmentDescription`, `DepartmentCreation` FROM `departments`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(departmentsViewQuery);

            while(queryOutput.next()){

                Integer queryDepartmentID = queryOutput.getInt("DepartmentID");
                String queryDepartmentName = queryOutput.getString("DepartmentName");
                String queryDepartmentHead = queryOutput.getString("DepartmentHead");
                String queryDepartmentStatus = queryOutput.getString("DepartmentStatus");
                Integer queryDepartmentSize = queryOutput.getInt("DepartmentSize");
                String queryDepartmentDescription = queryOutput.getString("DepartmentDescription");
                java.sql.Date queryDepartmentCreation = queryOutput.getDate("DepartmentCreation");

                //Populate the observableList
                departmentModelObservableList.add(new departmentModel(queryDepartmentID, queryDepartmentName, queryDepartmentHead, queryDepartmentStatus, queryDepartmentSize, queryDepartmentDescription, queryDepartmentCreation));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            DepartmentID.setCellValueFactory(new PropertyValueFactory<>("DepartmentID"));
            DepartmentName.setCellValueFactory(new PropertyValueFactory<>("DepartmentName"));
            DepartmentHead.setCellValueFactory(new PropertyValueFactory<>("DepartmentHead"));
            DepartmentStatus.setCellValueFactory(new PropertyValueFactory<>("DepartmentStatus"));
            DepartmentSize.setCellValueFactory(new PropertyValueFactory<>("DepartmentSize"));
            DepartmentDescription.setCellValueFactory(new PropertyValueFactory<>("DepartmentDescription"));
            DepartmentCreation.setCellValueFactory(new PropertyValueFactory<>("DepartmentCreation"));

            //Action.setCellFactory(param -> new ActionButtonTableCell<>());

            departments_tableView.setItems(departmentModelObservableList);

            //Initial filtered list
            FilteredList<departmentModel> filteredData = new FilteredList<>(departmentModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(department -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();


                    if (department.getDepartmentName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;    //Means a match was found in Department column
                    } else if (department.getDepartmentHead().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (department.getDepartmentCreation().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;   //no match found
                });
            });

            SortedList<departmentModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(departments_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            departments_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(departmentsController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

}

