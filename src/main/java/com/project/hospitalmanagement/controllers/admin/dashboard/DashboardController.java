package com.project.hospitalmanagement.controllers.admin.dashboard;

import com.project.hospitalmanagement.controllers.admin.appointments.appointmentsController;
import com.project.hospitalmanagement.controllers.admin.doctors.doctorsController;
import com.project.hospitalmanagement.controllers.alert.alertMessage;
import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.*;
import com.project.hospitalmanagement.controllers.utilities.ActionButonOnDbTableCell;
import com.project.hospitalmanagement.controllers.utilities.ActionButtonTableCell;
import com.project.hospitalmanagement.controllers.utilities.myDatabaseUtils.myDatabaseUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {
    public TableColumn<appointmentsModel, Integer> AppointmentID;
    
    public TableColumn<appointmentsModel, String> Doctor;
    public TableColumn<appointmentsModel, String> PatientName;
    
    public TableColumn<appointmentsModel, Date> Date;
    public TableColumn<appointmentsModel, Time> Time;
    public TableColumn<appointmentsModel, String> Injury;
    
    public TableColumn<appointmentsModel, Void> Action;
    public TableColumn<doctorModel, ImageView> DoctorPicture;
    public TableColumn<doctorModel, String> DoctorName;
    public TableColumn<doctorModel, String> Availability;
    public TableColumn<operationModel, Time> Startingtime;
    public TableColumn<operationModel, String> patientName;
    public TableColumn<operationModel, String> chefDoctor;
    public TableColumn<operationModel, String> DoctorsTeam;
    public TableColumn<operationModel, Date> OperationDate;
    public TableColumn<operationModel, Void> MedicalHistory;
    public TableColumn<operationModel, String> Diagnostic;

    public TableColumn<operationModel, Void> action;
    public AnchorPane attendanceChart;
    public TableView<appointmentsModel> dailyAppointment;
    public TableView<doctorModel> doctorAvailability;
    public TableView<operationModel> weeklyOperations;
    public Label appointments_nbr;
    public Label doctor_nbr;
    public Label patients_nbr;
    public Label staff_nbr;
    public ListView<String> inventory_order_list;

    ObservableList<appointmentsModel> appointmentsModelObservableList = FXCollections.observableArrayList();
    ObservableList<doctorModel> doctorAvailabilityModelObservableList = FXCollections.observableArrayList();
    ObservableList<operationModel> operationsModelObservableList = FXCollections.observableArrayList();

    private final alertMessage alert = new alertMessage();


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        refreshStaffInfo();

        displayAttendanceChartInAnchorPane(attendanceChart);

        refreshOrders();

        refreshAppointments();

        refreshAvailability();


        refreshOperations();
    }

    public void refreshOrders(){
        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();


        //Populate order listView

        String lastOrdersQuery = "SELECT * FROM orders ORDER BY OrderDate DESC LIMIT 10";
        ObservableList<String> lastOrders = FXCollections.observableArrayList();

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(lastOrdersQuery);

            while(queryOutput.next()){

                String orderDetails = "Order ID: " + queryOutput.getInt("OrderID")
                        + ", Orderer Name: " + queryOutput.getString("OrdererName")
                        + ", Order Date: " + queryOutput.getDate("OrderDate")
                        + ", Order Status: " + queryOutput.getString("OrderStatus");

                lastOrders.add(orderDetails);
            }

            inventory_order_list.setItems(lastOrders);

        } catch (SQLException e) {
            System.err.println("Error executing query");
            e.printStackTrace();
        }
    }

    public void refreshAppointments(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();
        //Populate appointments tableView
        String appointmentsViewQuery = "SELECT `ID`, `PatientName`, `Doctor`, `Date`, `Time`, `Injury` FROM `appointments`";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(appointmentsViewQuery);

            while(queryOutput.next()){

                Integer queryID = queryOutput.getInt("ID");
                String queryPatientName = queryOutput.getString("PatientName");
                String queryDoctor = queryOutput.getString("Doctor");
                Date queryDate = queryOutput.getDate("Date");
                Time queryTime = queryOutput.getTime("Time");
                String queryInjury = queryOutput.getString("Injury");

                //Populate the observableList
                appointmentsModelObservableList.add(new appointmentsModel(queryID, queryPatientName, queryDoctor, queryDate, queryTime, queryInjury));
            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            AppointmentID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            PatientName.setCellValueFactory(new PropertyValueFactory<>("PatientName"));
            Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
            Time.setCellValueFactory(new PropertyValueFactory<>("Time"));
            Doctor.setCellValueFactory(new PropertyValueFactory<>("Doctor"));
            Injury.setCellValueFactory(new PropertyValueFactory<>("Injury"));

            Action.setCellFactory(param -> new ActionButonOnDbTableCell<>(this::DeleteAppointmentDetails, this::EditAppointmentDetails));


            dailyAppointment.setItems(appointmentsModelObservableList);


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

    public void refreshAvailability(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //Populate Availability tableView
        String doctorsViewQuery = "SELECT `DoctorName`, `DoctorAvailability`, `DoctorPicture` FROM `doctors`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(doctorsViewQuery);

            while(queryOutput.next()) {

                Image profilePicture;
                Blob queryDoctorPicture = queryOutput.getBlob("DoctorPicture");

                if (queryDoctorPicture == null) {
                    InputStream inputStream = getClass().getResourceAsStream("/Images/noPicture.jpg");
                    if (inputStream != null) {
                        System.out.println("Image found");
                    } else {
                        System.out.println("Image not found");
                    }
                    //assert inputStream != null;
                    assert inputStream != null;
                    profilePicture = new Image(inputStream);
                } else {
                    // Convert Blob to Image and put inside imageView
                    InputStream inputStream = queryDoctorPicture.getBinaryStream();
                    profilePicture = new Image(inputStream);
                }

                ImageView imageView = new ImageView(profilePicture);
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);

                Circle clip = new Circle(15, 15, 15);
                imageView.setClip(clip);

                // Create a DropShadow effect
                DropShadow dropShadow = new DropShadow();
                dropShadow.setRadius(5);
                dropShadow.setColor(Color.BLACK);
                imageView.setEffect(dropShadow);


                String queryDoctorName = queryOutput.getString("DoctorName");
                String queryDoctorAvailability = queryOutput.getString("DoctorAvailability");

                //Populate the observableList
                doctorAvailabilityModelObservableList.add(new doctorModel(imageView, queryDoctorName, queryDoctorAvailability));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            DoctorPicture.setCellValueFactory(new PropertyValueFactory<>("DoctorPicture"));
            DoctorName.setCellValueFactory(new PropertyValueFactory<>("DoctorName"));
            Availability.setCellValueFactory(new PropertyValueFactory<>("DoctorAvailability"));

            doctorAvailability.setItems(doctorAvailabilityModelObservableList);

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

    public void refreshOperations() {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();
        //Populate weekly operation tableView
        String operationsViewQuery = "SELECT `OperationID`, `OperationPatient`, `OperationChiefDoctor`, `OperationDoctorTeam`, `OperationDate`, `OperationTime`, `OperationPreviousDiagnostic` FROM `operations`";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(operationsViewQuery);

            while(queryOutput.next()){

                Integer queryOperationID = queryOutput.getInt("OperationID");
                String queryOperationPatient = queryOutput.getString("OperationPatient");
                String queryOperationChiefDoctor = queryOutput.getString("OperationChiefDoctor");
                String queryOperationDoctorTeam = queryOutput.getString("OperationDoctorTeam");
                Date queryOperationDate = queryOutput.getDate("OperationDate");
                Time queryOperationTime = queryOutput.getTime("OperationTime");
                String queryOperationPreviousDiagnostic = queryOutput.getString("OperationPreviousDiagnostic");

                //Populate the observableList
                operationsModelObservableList.add(new operationModel(queryOperationID, queryOperationPatient, queryOperationChiefDoctor, queryOperationDoctorTeam, queryOperationDate, queryOperationTime, queryOperationPreviousDiagnostic));
            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            patientName.setCellValueFactory(new PropertyValueFactory<>("OperationPatient"));
            chefDoctor.setCellValueFactory(new PropertyValueFactory<>("OperationChiefDoctor"));
            DoctorsTeam.setCellValueFactory(new PropertyValueFactory<>("OperationDoctorTeam"));
            OperationDate.setCellValueFactory(new PropertyValueFactory<>("OperationDate"));
            Startingtime.setCellValueFactory(new PropertyValueFactory<>("OperationTime"));
            Diagnostic.setCellValueFactory(new PropertyValueFactory<>("OperationPreviousDiagnostic"));

            action.setCellFactory(param -> new ActionButtonTableCell<>());


            weeklyOperations.setItems(operationsModelObservableList);


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

    public void refreshStaffInfo(){
        int patientCount = myDatabaseUtils.countRows("patients");
        int doctorCount = myDatabaseUtils.countRows("doctors");
        int staffCount = myDatabaseUtils.countRows("staff");
        int appointmentCount = myDatabaseUtils.countTodayRows("appointments", "Date");
        patients_nbr.setText(String.valueOf(patientCount));
        doctor_nbr.setText(String.valueOf(doctorCount));
        staff_nbr.setText(String.valueOf(staffCount + doctorCount));
        appointments_nbr.setText(String.valueOf(appointmentCount));
    }

    public static void displayAttendanceChartInAnchorPane(AnchorPane zizi) {

        //Create dataset
        int janAttendance = myDatabaseUtils.countOccPerMonthPerYear(2024, 1, "attendances", "AttendantEntryDate");
        int febAttendance = myDatabaseUtils.countOccPerMonthPerYear(2024, 2, "attendances", "AttendantEntryDate");
        int marAttendance = myDatabaseUtils.countOccPerMonthPerYear(2024, 3, "attendances", "AttendantEntryDate");
        int aprAttendance = myDatabaseUtils.countOccPerMonthPerYear(2024, 4, "attendances", "AttendantEntryDate");
        int mayAttendance = myDatabaseUtils.countOccPerMonthPerYear(2024, 5, "attendances", "AttendantEntryDate");
        int junAttendance = myDatabaseUtils.countOccPerMonthPerYear(2024, 6, "attendances", "AttendantEntryDate");
        int julAttendance = myDatabaseUtils.countOccPerMonthPerYear(2024, 7, "attendances", "AttendantEntryDate");
        int augAttendance = myDatabaseUtils.countOccPerMonthPerYear(2024, 8, "attendances", "AttendantEntryDate");
        int sepAttendance = myDatabaseUtils.countOccPerMonthPerYear(2024, 9, "attendances", "AttendantEntryDate");
        int octAttendance = myDatabaseUtils.countOccPerMonthPerYear(2024, 10, "attendances", "AttendantEntryDate");
        int novAttendance = myDatabaseUtils.countOccPerMonthPerYear(2024, 11, "attendances", "AttendantEntryDate");
        int decAttendance = myDatabaseUtils.countOccPerMonthPerYear(2024, 12, "attendances", "AttendantEntryDate");

        // Define the axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Create the bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Attendance per Month - 2024");
        xAxis.setLabel("Month");
        yAxis.setLabel("Number of Attendances");

        // Create the series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Attendance");

        // Add data to the series
        series.getData().add(new XYChart.Data<>("January", janAttendance));
        series.getData().add(new XYChart.Data<>("February", febAttendance));
        series.getData().add(new XYChart.Data<>("March", marAttendance));
        series.getData().add(new XYChart.Data<>("April", aprAttendance));
        series.getData().add(new XYChart.Data<>("May", mayAttendance));
        series.getData().add(new XYChart.Data<>("June", junAttendance));
        series.getData().add(new XYChart.Data<>("July", julAttendance));
        series.getData().add(new XYChart.Data<>("August", augAttendance));
        series.getData().add(new XYChart.Data<>("September", sepAttendance));
        series.getData().add(new XYChart.Data<>("October", octAttendance));
        series.getData().add(new XYChart.Data<>("November", novAttendance));
        series.getData().add(new XYChart.Data<>("December", decAttendance));

        // Add the series to the chart
        barChart.getData().add(series);

        // Set the position and size of the chart within the AnchorPane
        barChart.setLayoutX(0);
        barChart.setLayoutY(0);
        barChart.setPrefWidth(zizi.getPrefWidth());
        barChart.setPrefHeight(zizi.getPrefHeight());

        // Add the bar chart to the AnchorPane
        zizi.getChildren().add(barChart);
    }

    private void EditAppointmentDetails(appointmentsModel appointment) {
        if (appointment != null) {
            // get the different rows value
            Integer ID = appointment.getID();
            String Name = appointment.getPatientName();
            String Email = appointment.getEmail();
            Date Date = appointment.getDate();
            String Doctor = appointment.getDoctor();
            Integer Mobile = appointment.getMobile();
            String Specialization = appointment.getInjury();
            Time Time = appointment.getTime();


            Model.getInstance().getAdminPageFactory().showEditAppointmentWindow(appointment);


            System.out.println("Appointment's Name ." + Name);
        } else {
            System.out.println("Appointment's details could not be retrieved.");
        }
    }

    private void DeleteAppointmentDetails(appointmentsModel appointment) {
        if (appointment != null) {
            // get the different rows value
            Integer ID = appointment.getID();

            dataBase connection = new dataBase();
            Connection connectDB = connection.connectDB();

            // SQL query to fetch all columns of the selected certification
            String DeleteQuery = "DELETE FROM appointments WHERE ID = ?";

                try {
                    PreparedStatement statement = connectDB.prepareStatement(DeleteQuery);
                    statement.setInt(1, ID);
                    int deletedQuery = statement.executeUpdate();

                    // Check if a row was returned
                    if (deletedQuery > 0) {


                        alert.successMessage("Deleted from the table.");

                        refreshAppointments();

                    }else {
                        alert.errorMessage("Nothing was Deleted from the table .");
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

            //Now I call the Edit method to generate the Edit window with these infos
        } else {
            System.out.println("Appointment's details could not be retrieved.");
        }
    }

}
