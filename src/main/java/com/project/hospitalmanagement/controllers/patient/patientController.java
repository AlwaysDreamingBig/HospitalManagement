package com.project.hospitalmanagement.controllers.patient;

import com.project.hospitalmanagement.controllers.alert.alertMessage;
import com.project.hospitalmanagement.controllers.database.dataBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.project.hospitalmanagement.controllers.utilities.utilitiesFunction.*;
import static com.project.hospitalmanagement.controllers.utilities.utilitiesFunction.bindCheckboxToShowPassword;

public class patientController implements Initializable {
    public TextField logEmail_fld;
    public AnchorPane login_form;
    public PasswordField logPassword_fld;
    public TextField viewLogssword_fld;
    public Button login_btn;
    public Hyperlink goto_register;
    public CheckBox showLogPassword;
    public AnchorPane reg_form;
    public FontAwesomeIconView icon;
    public TextField name_fld;
    public TextField viewRegPassword1;
    public TextField viewRegPassword2;
    public PasswordField regPassword_fld1;
    public Button register_btn;
    public Hyperlink goto_login;
    public Label regError_msg;
    public CheckBox showRegPassword;
    public PasswordField regPassword_fld2;
    public TextField regEmail_fld;

    //Connection to the database variables & functions
    //private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void registerAccount(){
        alertMessage alert = new alertMessage();
        if(name_fld.getText().isEmpty() ||regEmail_fld.getText().isEmpty() ||regPassword_fld1.getText().isEmpty() ||regPassword_fld2.getText().isEmpty()){
            regError_msg.setText("You must enter all the fields");
            alert.errorMessage("Well you think ya funny aah");
        } else if (!isValidPassword(regPassword_fld1.getText())) {
            alert.errorMessage("Password most be : at least 8 words, contains 1 uppercase, 1 number");
        }else if (!isValidEmail(regEmail_fld.getText())) {
            alert.errorMessage("Wrong email format");
        }
        else if (!Objects.equals(regPassword_fld1.getText(), regPassword_fld2.getText())) {
            alert.errorMessage("Wrong password: not corresponding");
        }
        else{

            String checkUser = "SELECT * FROM patients WHERE PatientEmail = '" + regEmail_fld.getText() + "'";
            //connect = dataBase.connectDB();
            dataBase connection = new dataBase();
            Connection connect = connection.connectDB();

            try{
                assert connect != null;
                prepare = connect.prepareStatement(checkUser);
                result = prepare.executeQuery();

                if(result.next()){
                    alert.errorMessage("An account already exists for this email address");
                }else{

                    String insertUser = "INSERT INTO patients (PatientName, PatientEmail, PatientPassword, PatientAccountDate) VALUES(?, ?, ?, ?)";
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare = connect.prepareStatement(insertUser);
                    prepare.setString(1, name_fld.getText());
                    prepare.setString(2, regEmail_fld.getText());
                    prepare.setString(3, regPassword_fld1.getText());
                    prepare.setString(4, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert.successMessage("Registered successfully");

                    resetFields();
                    login_form.setVisible(true);
                    reg_form.setVisible(false);

                }
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void loginAccount(){
        alertMessage alert = new alertMessage();

        if(logEmail_fld.getText().isEmpty() ||logPassword_fld.getText().isEmpty() ){
            alert.errorMessage("Well just enter the email and password maybe ?");
        }else{

            String loginSQL = "SELECT * FROM patients WHERE PatientEmail = ? AND PatientPassword = ?";

            //connect = dataBase.connectDB();
            dataBase connection = new dataBase();
            Connection connect = connection.connectDB();

            try {
                assert connect != null;
                prepare = connect.prepareStatement(loginSQL);
                prepare.setString(1, logEmail_fld.getText());
                prepare.setString(2, logPassword_fld.getText());
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

    public void switchForm(ActionEvent event) {

        if(event.getSource() == goto_register){
            reg_form.setVisible(true);
            login_form.setVisible(false);
            resetFields();
        } else if (event.getSource() == goto_login) {
            reg_form.setVisible(false);
            login_form.setVisible(true);
            resetFields();
        }

    }

    public void resetFields(){
        logPassword_fld.clear();
        logEmail_fld.clear();
        name_fld.clear();
        regPassword_fld1.clear();
        regPassword_fld2.clear();
        regEmail_fld.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindCheckboxToShowPassword(logPassword_fld, showLogPassword, viewLogssword_fld);
        bindCheckboxToShowMultiPasswords(regPassword_fld1, viewRegPassword1, regPassword_fld2, viewRegPassword2, showRegPassword);
    }
}
