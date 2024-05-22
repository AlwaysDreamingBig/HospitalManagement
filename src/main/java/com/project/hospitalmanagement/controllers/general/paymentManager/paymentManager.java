package com.project.hospitalmanagement.controllers.general.paymentManager;
import com.project.hospitalmanagement.controllers.database.dataBase;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class paymentManager {


    // Method to add a new payment record to the payments
    // functionID 0 for Doctor
    // functionID 1 for Staff
    // functionID 2 for Patient

    public static void addPaymentRecord(int functionID, String Name, int amount, String method, String remarks, String status) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        String sql = "INSERT INTO payments (PaymentPatientID, PaymentDate, PaymentAmount, PaymentMethod, " +
                "PaymentInvoiceID, PaymentTransactionID, PaymentEmployeeID, PaymentRemarks, PaymentStatus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Create a prepared statement
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);

            // Set parameter values
            if(functionID > 1){
                preparedStatement.setString(1, generatePatientEmployeeID(getPatientIDFromDatabase(Name))); // PaymentEmployeeID
            }else {
                preparedStatement.setNull(1, java.sql.Types.INTEGER); // PaymentPatientID
            }
            preparedStatement.setString(2, getCurrentDateTime()); // PaymentDate
            preparedStatement.setInt(3, amount); // PaymentAmount
            preparedStatement.setString(4, method); // PaymentMethod
            preparedStatement.setString(5, generateNextInvoiceID()); // PaymentInvoiceID
            preparedStatement.setString(6, generateNextTransactionID()); // PaymentTransactionID
            if(functionID == 0){
                preparedStatement.setString(7, generateDoctorEmployeeID(getDoctorIDFromDatabase(Name))); // PaymentEmployeeID
            } else if (functionID == 1) {
                preparedStatement.setString(7, generateStaffEmployeeID(getStaffIDFromDatabase(Name))); // PaymentEmployeeID
            } else if (functionID > 1) {
                preparedStatement.setNull(7, java.sql.Types.INTEGER);
            } else
            {
                preparedStatement.setString(7, null);
            }
            preparedStatement.setString(8, remarks); // PaymentRemarks
            preparedStatement.setString(9, status); // PaymentStatus

            // Execute the query
            preparedStatement.executeUpdate();
            System.out.println("Payment record added successfully!");

        } catch (SQLException e) {
            System.err.println("Error adding payment record: " + e.getMessage());
        }
    }


    // Method to generate the next InvoiceID
    private static String generateNextInvoiceID() {
        String lastInvoiceID = getLastIDFromDatabase("PaymentInvoiceID");
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

        String sql = "SELECT " + columnName + " FROM payments ORDER BY " + columnName + " DESC LIMIT 1";
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
    private static String generateDoctorEmployeeID(int doctorID) {
        // Generate "D" + DoctorID for PaymentEmployeeID
        return "D" + doctorID;
    }
    private static String generateStaffEmployeeID(int staffID) {
        // Generate "D" + DoctorID for PaymentEmployeeID
        return "S" + staffID;
    }
    private static String generatePatientEmployeeID(int petientID) {
        // Generate "D" + DoctorID for PaymentEmployeeID
        return "P" + petientID;
    }

    // Method to get DoctorID from the database based on DoctorName
    private static int getDoctorIDFromDatabase( String doctorName) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        String sql = "SELECT DoctorID FROM doctors WHERE DoctorName = ?";
        int doctorID = -1; // Default value if not found

        try {
            // Create a prepared statement
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            preparedStatement.setString(1, doctorName);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // If result found, get the DoctorID
            if (resultSet.next()) {
                doctorID = resultSet.getInt("DoctorID");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving DoctorID: " + e.getMessage());
        }finally {
            try {
                // Close the database connection
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return doctorID;
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

    // Method to get the current date and time in the required format
    private static String getCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
}


