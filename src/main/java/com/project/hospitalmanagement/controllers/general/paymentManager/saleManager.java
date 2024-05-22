package com.project.hospitalmanagement.controllers.general.paymentManager;

import com.project.hospitalmanagement.controllers.database.dataBase;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class saleManager {

    public static void addSaleRecord(String patientName, String staffName, String item, String method, BigDecimal discount, BigDecimal tax, String remarks, String status) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        String sql = "INSERT INTO Sales (" +
                "SaleDate, PatientID, EmployeeID, TotalAmount, PaymentMethod, Item, PrescriptionID," +
                "SaleStatus, Discount, Tax, Remarks, InvoiceID, PaymentTransactionID" +
                ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Create a prepared statement
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);

            // Set parameter values
            preparedStatement.setString(1, getCurrentDateTime()); // SaleDate
            preparedStatement.setInt(2, getPatientIDFromDatabase(patientName));
            preparedStatement.setString(3, generateStaffEmployeeID(getStaffIDFromDatabase(staffName))); // EmployeeID
            preparedStatement.setInt(4, getItemPriceFromDatabase(item)); // PaymentAmount
            preparedStatement.setString(5, method); // Method
            preparedStatement.setString(6, item); // item
            preparedStatement.setNull(7, java.sql.Types.INTEGER); // PrescriptionID
            preparedStatement.setString(8, status); // status
            preparedStatement.setBigDecimal(9, discount);
            preparedStatement.setBigDecimal(10, tax);
            preparedStatement.setString(11, remarks); // Remarks
            preparedStatement.setString(12, generateNextTransactionID()); // TransactionID
            preparedStatement.setString(13, generateNextInvoiceID()); // InvoiceID

            // Execute the query
            preparedStatement.executeUpdate();
            System.out.println("Sale record added successfully!");

        } catch (SQLException e) {
            System.err.println("Error adding sale record: " + e.getMessage());
        }
    }


    // Method to generate the next InvoiceID
    private static String generateNextInvoiceID() {
        String lastInvoiceID = getLastIDFromDatabase("InvoiceID");
        int lastInvoiceNumber = Integer.parseInt(lastInvoiceID.substring(3));
        int nextInvoiceNumber = lastInvoiceNumber + 1;
        return "INV" + String.format("%03d", nextInvoiceNumber);
    }

    // Method to generate the next TransactionID
    private static String generateNextTransactionID() {
        String lastTransactionID = getLastIDFromDatabase("PaymentTransactionID");
        int lastTransactionNumber = Integer.parseInt(lastTransactionID.substring(5));
        int nextTransactionNumber = lastTransactionNumber + 1;
        return "TRANS" + String.format("%03d", nextTransactionNumber);
    }

    // Method to get the last ID (either InvoiceID or TransactionID) from the database
    private static String getLastIDFromDatabase(String columnName) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        String sql = "SELECT " + columnName + " FROM sales ORDER BY " + columnName + " DESC LIMIT 1";
        String lastID = "";

        try {
            // Create a prepared statement
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // If result found, get the last ID
            if (resultSet.next()) {
                lastID = resultSet.getString(columnName);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving last ID: " + e.getMessage());
        }finally {
            try {
                // Close the database connection
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lastID;
    }

    // Methods to generate a PaymentEmployeeID
    private static String generateStaffEmployeeID(int staffID) {
        // Generate "D" + DoctorID for PaymentEmployeeID
        return "S" + staffID;
    }

    private static int getStaffIDFromDatabase( String staffName) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        String sql = "SELECT StaffID FROM staff WHERE StaffName = ?";
        int staffID = -1; // Default value if not found

        try {
            // Create a prepared statement
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, staffName);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // If result found, get the DoctorID
            if (resultSet.next()) {
                staffID = resultSet.getInt("StaffID");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving StaffID: " + e.getMessage());
        }finally {
            try {
                // Close the database connection
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return staffID;
    }

    private static int getPatientIDFromDatabase( String patientName) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        String sql = "SELECT PatientID FROM patients WHERE PatientName = ?";
        int patientID = -1; // Default value if not found

        try {
            // Create a prepared statement
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, patientName);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // If result found, get the DoctorID
            if (resultSet.next()) {
                patientID = resultSet.getInt("PatientID");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving StaffID: " + e.getMessage());
        }finally {
            try {
                // Close the database connection
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return patientID;
    }

    private static int getItemPriceFromDatabase( String medicineName) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        String sql = "SELECT MedicinePrice FROM medicines WHERE MedicineName = ?";
        int medicinePrice = -1; // Default value if not found

        try {
            // Create a prepared statement
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, medicineName);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // If result found, get the DoctorID
            if (resultSet.next()) {
                medicinePrice = resultSet.getInt("MedicinePrice");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving MedicinePrice: " + e.getMessage());
        }finally {
            try {
                // Close the database connection
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return medicinePrice;
    }

    // Method to get the current date and time in the required format
    private static String getCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
}
