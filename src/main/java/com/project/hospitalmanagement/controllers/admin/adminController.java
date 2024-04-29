package com.project.hospitalmanagement.controllers.admin;

import com.project.hospitalmanagement.controllers.alert.alertMessage;
import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.utilities.utilitiesFunction;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

public class adminController implements Initializable {
    public TextField hid_fld;
    public Button login_btn;
    public ChoiceBox portal_choiceb;
    public Hyperlink goto_register;
    public TextField email_fld;
    public CheckBox show_password;
    public PasswordField password_fld;
    public TextField viewPassword_field;
    private utilitiesFunction utility;

    //Alert variables
    private final alertMessage alert = new alertMessage();

    //Connection to the database variables & functions
    //private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void loginAccount(){
        if(hid_fld.getText().isEmpty() ||password_fld.getText().isEmpty() ||email_fld.getText().isEmpty() ){

            alert.errorMessage("Please fill the text areas ton connect");

        }else{

            String loginSQL = "SELECT * FROM admin WHERE email = ? AND password = ? and admin_hid = ?";

            //connect = dataBase.connectDB();
            // Get database connection
            dataBase connection = new dataBase();
            Connection connect = connection.connectDB();

            try {
                assert connect != null;
                prepare = connect.prepareStatement(loginSQL);
                prepare.setString(1, email_fld.getText());
                prepare.setString(2, password_fld.getText());
                prepare.setString(3, hid_fld.getText());
                result = prepare.executeQuery();

                if (result.next())  {
                    alert.successMessage("Connection successful");
                    Stage stage = (Stage) login_btn.getScene().getWindow();
                    Model.getInstance().getpageFactory().closeStage(stage);
                    Model.getInstance().getAdminPageFactory().showAdminWindow();
                }else {
                    alert.errorMessage("Incorrect login password or email address");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //functions for switching between forms
    public void resetFields(){
        email_fld.clear();
        password_fld.clear();
        hid_fld.clear();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utilitiesFunction.bindCheckboxToShowPassword(password_fld, show_password, viewPassword_field);
    }
}
