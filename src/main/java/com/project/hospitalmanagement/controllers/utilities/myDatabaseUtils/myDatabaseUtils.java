package com.project.hospitalmanagement.controllers.utilities.myDatabaseUtils;

import com.project.hospitalmanagement.controllers.database.dataBase;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.sql.*;

public class myDatabaseUtils {

    public static int countRows(String tableName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int rowCount = 0;

        try {
            // Establish database connection
            connection = dataBase.connectDB();

            // SQL query to count rows in the table
            String query = "SELECT COUNT(*) FROM " + tableName;

            // Create prepared statement
            preparedStatement = connection.prepareStatement(query);

            // Execute query
            resultSet = preparedStatement.executeQuery();

            // Process result set
            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error executing query");
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources");
                e.printStackTrace();
            }
        }

        return rowCount;
    }

    public static int countTodayRows(String tableName, String tableColumn) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int rowCount = 0;

        // Get today's date
        java.util.Date today = new Date(System.currentTimeMillis());
        System.err.println("today is " + today);
        java.sql.Date sqlDate = new java.sql.Date(today.getTime());
        System.err.println("sqlDate is " + sqlDate);

        try {
            // Establish database connection
            connection = dataBase.connectDB();

            // SQL query to count rows in the table
            String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + tableColumn + " = ?";

            // Create prepared statement
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setDate(1, sqlDate);

            // Execute query
            resultSet = preparedStatement.executeQuery();

            // Process result set
            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error executing query");
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources");
                e.printStackTrace();
            }
        }

        return rowCount;
    }


    public static int countOccPerMonthPerYear(int year, int month, String tableName, String line) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int lineCount = 0;

        try {
            // Establish database connection
            connection = dataBase.connectDB();

            // SQL query to count rows in the table
            String query = "SELECT COUNT(*) AS `NumberOfOccur` " +
                    "FROM `" + tableName + "` " +
                    "WHERE YEAR(`" + line + "`) = ? AND MONTH(`" + line + "`) = ?";

            // Create prepared statement
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month);

            // Execute query
            resultSet = preparedStatement.executeQuery();

            // Process result set
            if (resultSet.next()) {
                lineCount = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error executing query");
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources");
                e.printStackTrace();
            }
        }

        return lineCount;
    }

}

