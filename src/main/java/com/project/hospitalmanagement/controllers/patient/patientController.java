package com.project.hospitalmanagement.controllers.patient;

import com.project.hospitalmanagement.controllers.alert.alertMessage;
import com.project.hospitalmanagement.controllers.database.dataBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Objects;

import static com.project.hospitalmanagement.controllers.utilities.utilitiesFunction.isValidEmail;
import static com.project.hospitalmanagement.controllers.utilities.utilitiesFunction.isValidPassword;

public class patientController {
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

            String checkUser = "SELECT * FROM patient WHERE email = '" + regEmail_fld.getText() + "'";
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

                    String insertUser = "INSERT INTO patient (name, email, password, date) VALUES(?, ?, ?, ?)";
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

    public void resetFields(){
        logPassword_fld.clear();
        logEmail_fld.clear();
        name_fld.clear();
        regPassword_fld1.clear();
        regPassword_fld2.clear();
        regEmail_fld.clear();
    }
}
