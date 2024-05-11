package com.project.hospitalmanagement.controllers.admin.staff.Buttons;

import com.project.hospitalmanagement.controllers.alert.alertMessage;
import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.departmentModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class onActionDepartmentController implements Initializable {
    public Button save_btn;
    public Button cancel_btn;
    public TextField DepartmentHead;
    public Integer DepartmentID;

    private final alertMessage alert = new alertMessage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    public void addListener(){
        save_btn.setOnMouseClicked(event -> save());
        cancel_btn.setOnMouseClicked(event -> cancel());
    }

    public void setFields(departmentModel department){

        if(department != null){
            DepartmentID = department.getDepartmentID();
            DepartmentHead.setText(department.getDepartmentHead());
        }

    }

    public void save(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();
         // SQL query to fetch department data
            String UpdateQuery = "UPDATE departments " +
                    "SET DepartmentHead = ? " +
                    "WHERE DepartmentID  = ?";


            try {
                PreparedStatement statement = connectDB.prepareStatement(UpdateQuery);

                statement.setString(1, DepartmentHead.getText());

                statement.setInt(2, DepartmentID);

                // Execute the update query
                int rowsAffected = statement.executeUpdate();

                // Check if any rows were updated
                if (rowsAffected > 0) {
                    alert.successMessage("Department Chief updated successfully.");
                    Stage stage = (Stage) save_btn.getScene().getWindow();
                    Model.getInstance().getpageFactory().closeStage(stage);

                } else {
                    alert.errorMessage("No Department found with the given Description.");

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    // Close the database connection
                    connectDB.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


    }

    public void cancel(){
        Stage stage = (Stage) save_btn.getScene().getWindow();
        Model.getInstance().getpageFactory().closeStage(stage);
    }

}
