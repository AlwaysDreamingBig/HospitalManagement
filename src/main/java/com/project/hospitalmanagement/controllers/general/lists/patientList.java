package com.project.hospitalmanagement.controllers.general.lists;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class patientList {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    private ObservableList<String> affectedPatientsList;

    public void retrieveAffectedPatients(String administratorName) {
        affectedPatientsList = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();

            // SQL query modified to use a prepared statement to prevent SQL injection
            String sqlQuery = "SELECT PatientName FROM patients WHERE AssignedDoctor = ?";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, administratorName);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("PatientName");
                affectedPatientsList.add(name);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getAffectedPatients() {
        return affectedPatientsList;
    }
}
