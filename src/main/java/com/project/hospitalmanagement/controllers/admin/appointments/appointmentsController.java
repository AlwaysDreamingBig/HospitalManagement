package com.project.hospitalmanagement.controllers.admin.appointments;

import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.appointmentsModel;
import com.project.hospitalmanagement.controllers.utilities.StaffActionButtonTableCell;
import com.project.hospitalmanagement.controllers.utilities.utilitiesFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class appointmentsController implements Initializable {
    @FXML

    public TextField search_bar;
    public Button search_btn;
    public TableView<appointmentsModel> appointment_tableView;
    public TableColumn<appointmentsModel, Void> pic;
    public TableColumn<appointmentsModel, Integer> appointment_ID;
    public TableColumn<appointmentsModel, String> patient_name;

    public TableColumn<appointmentsModel, String> gender;

    public TableColumn<appointmentsModel, String> email;

    public TableColumn<appointmentsModel, Integer> mobile;

    public TableColumn<appointmentsModel, String> date;
    public TableColumn<appointmentsModel, Time> hour;

    public TableColumn<appointmentsModel, String> doctor;
    public TableColumn<appointmentsModel, String> injury;

    public TableColumn<appointmentsModel, Void> action;

    public Button addAppointment_btn;
    public Button refresh_btn;

    ObservableList<appointmentsModel> appointmentsModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        refreshAppointments();

        addListener();

    }

    public void addListener(){
        addAppointment_btn.setOnMouseClicked(event -> openAddAppointment());
        refresh_btn.setOnMouseClicked(event -> refreshAppointments());
    }

    private void openAddAppointment(){
        Model.getInstance().getAdminPageFactory().showAddAppointment();
    }


    public void refreshAppointments() {
        // Clear existing data in the observable list
        appointmentsModelObservableList.clear();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String appointmentsViewQuery = "SELECT `ID`, `PatientName`, `Gender`, `Email`, `Mobile`, `Date`, `Time`, `Doctor`, `Injury` FROM `appointments`";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(appointmentsViewQuery);

            while(queryOutput.next()){

                Integer queryID = queryOutput.getInt("ID");
                String queryPatientName = queryOutput.getString("PatientName");
                String queryGender = queryOutput.getString("Gender");
                String queryEmail = queryOutput.getString("Email");
                Integer queryMobile = queryOutput.getInt("Mobile");
                Date queryDate = queryOutput.getDate("Date");
                Time queryTime = queryOutput.getTime("Time");
                String queryDoctor = queryOutput.getString("Doctor");
                String queryInjury = queryOutput.getString("Injury");

                //Populate the observableList
                appointmentsModelObservableList.add(new appointmentsModel(queryID, queryPatientName, queryGender, queryEmail, queryMobile, queryDate, queryTime, queryDoctor, queryInjury));
            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            appointment_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            patient_name.setCellValueFactory(new PropertyValueFactory<>("PatientName"));
            gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
            email.setCellValueFactory(new PropertyValueFactory<>("Email"));
            mobile.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
            date.setCellValueFactory(new PropertyValueFactory<>("Date"));
            hour.setCellValueFactory(new PropertyValueFactory<>("Time"));
            doctor.setCellValueFactory(new PropertyValueFactory<>("Doctor"));
            injury.setCellValueFactory(new PropertyValueFactory<>("Injury"));

            action.setCellFactory(param -> new StaffActionButtonTableCell<>(this::displayAppointmentDetails));


            appointment_tableView.setItems(appointmentsModelObservableList);

            //Initial filtered list
            FilteredList<appointmentsModel> filteredData = new FilteredList<>(appointmentsModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(appointment -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (appointment.getDoctor().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;    //Means a match was found in Doctor column
                    } else if (appointment.getPatientName().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (appointment.getEmail().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (appointment.getInjury().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (appointment.getDate().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;   //no match found
                });
            });

            SortedList<appointmentsModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(appointment_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            appointment_tableView.setItems(sortedData);

        }catch (SQLException e){
            Logger.getLogger(appointmentsController.class.getName()).log(Level.SEVERE, null, e);
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

    private void displayAppointmentDetails(appointmentsModel appointment) {
        if (appointment != null) {

            Integer ID = appointment.getID();
            String PatientName = appointment.getPatientName();
            String Email = appointment.getEmail();
            java.sql.Date Date = appointment.getDate();
            String Gender = appointment.getGender();
            Integer Mobile = appointment.getMobile();
            String DoctorName = appointment.getDoctor();
            Time Time = appointment.getTime();
            String Injury = appointment.getInjury();

            //Now I call the Edit method to generate the Edit window with these infos
        } else {
            System.out.println("Appointment's details could not be retrieved.");
        }
    }
}

