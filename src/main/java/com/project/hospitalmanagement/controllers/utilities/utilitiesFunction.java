package com.project.hospitalmanagement.controllers.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import  com.project.hospitalmanagement.controllers.database.dataBase;

public class utilitiesFunction {
    public static void bindCheckboxToShowPassword(PasswordField passwordField, CheckBox showPasswordCheckbox, TextField viewPassword) {
        showPasswordCheckbox.setOnAction(event -> {
            if (showPasswordCheckbox.isSelected()) {
                viewPassword.setText(passwordField.getText());
                viewPassword.setVisible(true);
                passwordField.setVisible(false);
            }
            else {
                //viewPassword.setText(passwordField.getText());
                passwordField.setText(viewPassword.getText());
                viewPassword.setVisible(false);
                passwordField.setVisible(true);
            }
        });
    }

    public static boolean isValidPassword(String password) {
        // Check length
        if (password.length() < 8) {
            return false;
        }

        // Check for at least one uppercase letter
        boolean hasUpperCase = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
                break;
            }
        }
        if (!hasUpperCase) {
            return false;
        }

        // Check for at least one number
        boolean hasNumber = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
                break;
            }
        }
        if (!hasNumber) {
            return false;
        }

        // If all conditions are met, return true
        return true;
    }

    public static boolean isValidEmail(String email) {
        // Regular expression for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Create a matcher object
        Matcher matcher = pattern.matcher(email);

        // Return true if the email matches the pattern, otherwise false
        return matcher.matches();
    }

    // Function to open file explorer and upload selected picture as profile picture for a user
    public static void uploadProfilePicture(Stage primaryStage, String username, String database, String databaseColumn, String databaseColomnID) {
        // Create file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Picture");
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            // Get database connection
            dataBase connection = new dataBase();
            Connection connectDB = connection.connectDB();

            if (connectDB != null) {
                // Prepare statement to update profile picture for user
                String query = "UPDATE " + database + " SET " + databaseColumn + " = ? WHERE " + databaseColomnID + " = ?";
                try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
                    // Read the picture file into a byte array
                    try (FileInputStream inputStream = new FileInputStream(file)) {
                        byte[] imageData = inputStream.readAllBytes();
                        // Set the byte array as parameter in the prepared statement
                        preparedStatement.setBytes(1, imageData);
                        // Set the username of the user whose profile picture is being updated
                        preparedStatement.setString(2, username);
                        // Execute the query to update profile picture
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Profile picture uploaded successfully.");
                        } else {
                            System.out.println("Failed to upload profile picture.");
                        }
                    }
                } catch (SQLException | FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connectDB.close(); // Close connection
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Failed to connect to database.");
            }
        } else {
            System.out.println("No file selected.");
        }
    }


}
