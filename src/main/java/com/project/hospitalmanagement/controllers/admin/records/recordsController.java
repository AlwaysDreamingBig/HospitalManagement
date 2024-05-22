package com.project.hospitalmanagement.controllers.admin.records;

import com.project.hospitalmanagement.controllers.admin.doctors.doctorsController;
import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.birthModel;
import com.project.hospitalmanagement.controllers.models.deathModel;
import com.project.hospitalmanagement.controllers.models.doctorModel;
import com.project.hospitalmanagement.controllers.utilities.StaffActionButtonTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.sql.*;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class recordsController implements Initializable {
    public Label BirthsNumber;
    public Button BirthList;
    public Label DeathsNumber;
    public Button DeathList;
    public TableView<birthModel> Birth_tableView;
    public TableColumn<birthModel, String> BirthChildName;
    public TableColumn<birthModel, Date> BirthDate;
    public TableColumn<birthModel, String> BirthMother;
    public TableColumn<birthModel, String> BloodGroup;
    public TableView<deathModel> death_tableview;
    public TableColumn<deathModel, String> DeathChildName;
    public TableColumn<deathModel, Date> DeathDate;
    public TableColumn<deathModel, String> DeathChildMother;
    public TableColumn<deathModel, String> DeathWatcher;
    public AnchorPane graphPane;

    public ObservableList<birthModel> birthModelObservableList = FXCollections.observableArrayList();
    public ObservableList<deathModel> deathModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        createLineChart(graphPane);

        refreshBirths();

        refreshDeaths();

        addListener();
    }

    public void  addListener(){
        BirthList.setOnMouseClicked(event -> showBirthList());
        DeathList.setOnMouseClicked(event -> showDeathList());
    }

    public void showBirthList(){
        Model.getInstance().getAdminPageFactory().showBirthWindow();
    }

    public void showDeathList(){
        Model.getInstance().getAdminPageFactory().showDeathWindow();
    }

    public void refreshBirths() {

        birthModelObservableList.clear();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String doctorsViewQuery = "SELECT `BirthName`, `BirthDate`, `BirthMother`, `BirthBloodGp` FROM `births` LIMIT 15";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(doctorsViewQuery);

            while(queryOutput.next()) {


                String queryBirthName = queryOutput.getString("BirthName");
                java.sql.Date queryBirthDate = queryOutput.getDate("BirthDate");
                String queryBirthMother = queryOutput.getString("BirthMother");
                String queryBirthBloodGp = queryOutput.getString("BirthBloodGp");

                //Populate the observableList
                birthModelObservableList.add(new birthModel(queryBirthName, queryBirthDate, queryBirthMother, queryBirthBloodGp));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            BirthChildName.setCellValueFactory(new PropertyValueFactory<>("BirthName"));
            BirthDate.setCellValueFactory(new PropertyValueFactory<>("BirthDate"));
            BirthMother.setCellValueFactory(new PropertyValueFactory<>("BirthMother"));
            BloodGroup.setCellValueFactory(new PropertyValueFactory<>("BirthBloodGp"));

            Birth_tableView.setItems(birthModelObservableList);

            // Close the result set and the prepared statement
            queryOutput.close();

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

    public void refreshDeaths() {

        deathModelObservableList.clear();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String doctorsViewQuery = "SELECT `DeathName`, `DeathDate`, `DeathMother`, `DeathInWatch` FROM `deaths` LIMIT 15";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(doctorsViewQuery);

            while(queryOutput.next()) {


                String queryDeathName = queryOutput.getString("DeathName");
                java.sql.Date queryDeathDate = queryOutput.getDate("DeathDate");
                String queryDeathMother = queryOutput.getString("DeathMother");
                String queryDeathInWatch = queryOutput.getString("DeathInWatch");

                //Populate the observableList
                deathModelObservableList.add(new deathModel(queryDeathName, queryDeathDate, queryDeathMother, queryDeathInWatch));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            DeathChildName.setCellValueFactory(new PropertyValueFactory<>("DeathName"));
            DeathDate.setCellValueFactory(new PropertyValueFactory<>("DeathDate"));
            DeathChildMother.setCellValueFactory(new PropertyValueFactory<>("DeathMother"));
            DeathWatcher.setCellValueFactory(new PropertyValueFactory<>("DeathInWatch"));

            death_tableview.setItems(deathModelObservableList);

            // Close the result set and the prepared statement
            queryOutput.close();

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

    public void setBirthsDeathsChart(AnchorPane pane){

    }

    public void createLineChart(AnchorPane paneGraph) {
        ObservableList<XYChart.Series<Number, Number>> lineChartData = FXCollections.observableArrayList();

        // Create two series for deaths and births
        XYChart.Series<Number, Number> deathsSeries = new XYChart.Series<>();
        deathsSeries.setName("Deaths");
        XYChart.Series<Number, Number> birthsSeries = new XYChart.Series<>();
        birthsSeries.setName("Births");

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        // SQL query to count deaths per month
        String deathsQuery = "SELECT MONTH(DeathDate) AS month, COUNT(*) AS count FROM deaths GROUP BY MONTH(DeathDate)";
        // SQL query to count births per month
        String birthsQuery = "SELECT MONTH(BirthDate) AS month, COUNT(*) AS count FROM births GROUP BY MONTH(BirthDate)";

        try (Statement statement = connectDB.createStatement();
             ResultSet deathsResultSet = statement.executeQuery(deathsQuery)) {

            while (deathsResultSet.next()) {
                int month = deathsResultSet.getInt("month");
                int count = deathsResultSet.getInt("count");
                deathsSeries.getData().add(new XYChart.Data<>(month, count));
            }

            try (ResultSet birthsResultSet = statement.executeQuery(birthsQuery)) {
                while (birthsResultSet.next()) {
                    int month = birthsResultSet.getInt("month");
                    int count = birthsResultSet.getInt("count");
                    birthsSeries.getData().add(new XYChart.Data<>(month, count));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        lineChartData.add(deathsSeries);
        lineChartData.add(birthsSeries);



        // Create axes
        LineChart<Number, Number> lineChart = getNumberLineChart(lineChartData);

        // Add LineChart to the AnchorPane
        paneGraph.getChildren().add(lineChart);
        paneGraph.setStyle("-fx-background-color: #abcdef;");
    }

    private static LineChart<Number, Number> getNumberLineChart(ObservableList<XYChart.Series<Number, Number>> lineChartData) {
        NumberAxis xAxis = new NumberAxis(1, 12, 1);
        xAxis.setLabel("Month");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Count");

        // Create LineChart with explicit type parameters
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setData(lineChartData);
        lineChart.setTitle("Deaths and Births per Month");

        // Set chart dimensions
        lineChart.setPrefWidth(1100);
        lineChart.setPrefHeight(400);

        // Set legend position to bottom (horizontal)
        lineChart.setLegendSide(Side.RIGHT);
        return lineChart;
    }
}
