package com.project.hospitalmanagement.controllers.doctor;

import com.project.hospitalmanagement.controllers.alert.alertMessage;
import com.project.hospitalmanagement.controllers.database.dataBase;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

import static com.project.hospitalmanagement.controllers.utilities.utilitiesFunction.*;

public class doctorController implements Initializable {
    public TextField doctor_id;
    public Button login_btn;
    public ChoiceBox portal_choiceB;
    public Hyperlink goto_register;
    public TextField logEmail_fld;
    public Label logError_message;
    public TextField name_fld;
    public TextField regEmail_fld;
    
    public TextField nmid_fld;
    public Button register_btn;
    public Hyperlink goto_login;
    public Label regError_message;
    public AnchorPane registerForm;
    public AnchorPane loginForm;

    //Alert variables
    private final alertMessage alert = new alertMessage();
    public PasswordField logPassword_fld;
    public TextField viewLogPassword_fld;
    public CheckBox showLogPassword;
    public PasswordField regPassword_fld;
    public TextField viewRegPassword_fld;
    public CheckBox showRegPassword;

    //Connection to the database variables & functions
    //private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;


    public void registerAccount(){
        if(name_fld.getText().isEmpty() ||regEmail_fld.getText().isEmpty() ||regPassword_fld.getText().isEmpty() ||nmid_fld.getText().isEmpty()){
            regError_message.setText("You must enter all the fields");
            alert.errorMessage("Well you think ya funny aah");
        } else if (!isValidPassword(regPassword_fld.getText())) {
            alert.errorMessage("Password most be : at least 8 words, contains 1 uppercase, 1 number");
        }else if (!isValidEmail(regEmail_fld.getText())) {
            alert.errorMessage("Wrong email format");
        }
        else{

            String checkUser = "SELECT * FROM doctor WHERE email = '" + regEmail_fld.getText() + "' OR nmid = '" + nmid_fld.getText() + "'";
            //connect = dataBase.connectDB();
            dataBase connection = new dataBase();
            Connection connect = connection.connectDB();

            try{
                assert connect != null;
                prepare = connect.prepareStatement(checkUser);
                result = prepare.executeQuery();

                if(result.next()){
                    alert.errorMessage("An account already exists for these informations");
                }else{

                    String insertUser = "INSERT INTO doctor (name, email, password, nmid) VALUES(?, ?, ?, ?)";
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare = connect.prepareStatement(insertUser);
                    prepare.setString(1, name_fld.getText());
                    prepare.setString(2, regEmail_fld.getText());
                    prepare.setString(3, regPassword_fld.getText());
                    prepare.setString(4, nmid_fld.getText());

                    prepare.executeUpdate();

                    alert.successMessage("Registered successfully");

                    resetFields();
                    loginForm.setVisible(true);
                    registerForm.setVisible(false);

                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void loginAccount(){
        if(doctor_id.getText().isEmpty() ||logEmail_fld.getText().isEmpty() ||logPassword_fld.getText().isEmpty() ){
            regError_message.setText("You must enter all the fields");
            alert.errorMessage("Well you think ya funny aah");
        }else{

            String loginSQL = "SELECT * FROM doctor WHERE email = ? AND password = ? AND doctor_id = ?";

            //connect = dataBase.connectDB();
            dataBase connection = new dataBase();
            Connection connect = connection.connectDB();

            try {
                assert connect != null;
                prepare = connect.prepareStatement(loginSQL);
                prepare.setString(1, logEmail_fld.getText());
                prepare.setString(2, logPassword_fld.getText());
                prepare.setString(3, doctor_id.getText());
                result = prepare.executeQuery();

                if (result.next())  {
                    alert.successMessage("Connection successful");
                }else {
                    alert.errorMessage("Incorrect login password or email address or doctor ID");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //functions for switching between forms
    public void resetFields(){
        doctor_id.setText("");
        logPassword_fld.setText("");
        logEmail_fld.setText("");
        logError_message.setText("");
        name_fld.setText("");
        regError_message.setText("");
        nmid_fld.clear();
        regEmail_fld.setText("");
        regPassword_fld.setText("");
    }
    public void switchForm(ActionEvent event) {

        if(event.getSource() == goto_register){
            registerForm.setVisible(true);
            loginForm.setVisible(false);
            resetFields();
        } else if (event.getSource() == goto_login) {
            registerForm.setVisible(false);
            loginForm.setVisible(true);
            resetFields();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindCheckboxToShowPassword(logPassword_fld, showLogPassword, viewLogPassword_fld);
        bindCheckboxToShowPassword(regPassword_fld, showRegPassword, viewRegPassword_fld);
    }
}
