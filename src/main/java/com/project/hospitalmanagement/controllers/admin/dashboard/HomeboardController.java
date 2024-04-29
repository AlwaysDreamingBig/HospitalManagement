package com.project.hospitalmanagement.controllers.admin.dashboard;

import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.appointmentsModel;
import com.project.hospitalmanagement.controllers.utilities.myDatabaseUtils.myDatabaseUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HomeboardController implements Initializable {
    public Label inventory_lbl;
    public AreaChart charts;
    public Label message_box;
    public FontAwesomeIconView message_icon;
    public Label doctor_nbr;
    public Label patients_nbr;
    public Label staff_nbr;
    public Label appt_history;
    public TableView<appointmentsModel> appointment_list;
    public ListView<String> inventory_order_list;
    public FontAwesomeIconView refresh_btn;
    public AnchorPane chart_pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addListener();

        refreshStaffInfo();

        displayAttendanceChartInAnchorPane(chart_pane);

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        String lastOrdersQuery = "SELECT * FROM orders ORDER BY OrderDate DESC LIMIT 6";
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

    public void addListener(){
        inventory_lbl.setOnMouseClicked(event -> openInventory());
        inventory_order_list.setOnMouseClicked(event -> openInventory());
        message_box.setOnMouseClicked(event -> openMessages());
        message_icon.setOnMouseClicked(event -> openMessages());
        refresh_btn.setOnMouseClicked(event -> refreshValues());
    }

    public void refreshStaffInfo(){
        int patientCount = myDatabaseUtils.countRows("patients");
        int doctorCount = myDatabaseUtils.countRows("doctors");
        int staffCount = myDatabaseUtils.countRows("staff");
        patients_nbr.setText(String.valueOf(patientCount));
        doctor_nbr.setText(String.valueOf(doctorCount));
        staff_nbr.setText(String.valueOf(staffCount + doctorCount));
    }

    private void openInventory(){
        Model.getInstance().getAdminPageFactory().showInventoryWindow();
    }

    private void openFinances(){
        Model.getInstance().getAdminPageFactory().showFinanceWindow();
    }

    private void openMessages(){
        Model.getInstance().getpageFactory().showMessageBox();
    }

    private void refreshValues(){
        refreshStaffInfo();
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


}
