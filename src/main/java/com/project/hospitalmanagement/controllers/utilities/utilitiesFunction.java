package com.project.hospitalmanagement.controllers.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.project.hospitalmanagement.controllers.alert.alertMessage;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import  com.project.hospitalmanagement.controllers.database.dataBase;
import javafx.util.Duration;


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

    public static void bindCheckboxToShowMultiPasswords(
            PasswordField passwordField1,
            TextField viewPassword1,
            PasswordField passwordField2,
            TextField viewPassword2,
            CheckBox showPasswordCheckbox) {

        showPasswordCheckbox.setOnAction(event -> {
            if (showPasswordCheckbox.isSelected()) {
                // For first password field
                viewPassword1.setText(passwordField1.getText());
                viewPassword1.setVisible(true);
                passwordField1.setVisible(false);

                // For second password field
                viewPassword2.setText(passwordField2.getText());
                viewPassword2.setVisible(true);
                passwordField2.setVisible(false);
            } else {
                // For first password field
                passwordField1.setText(viewPassword1.getText());
                passwordField1.setVisible(true);
                viewPassword1.setVisible(false);

                // For second password field
                passwordField2.setText(viewPassword2.getText());
                passwordField2.setVisible(true);
                viewPassword2.setVisible(false);
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

    public static String getStringFromListView(ListView<String> MyList) {

        StringBuilder participants = new StringBuilder();
        for (String participant : MyList.getItems()) {
            participants.append(participant).append(",");
        }

        // Remove the trailing comma if participants exist
        if (participants.length() > 0) {
            participants.deleteCharAt(participants.length() - 1);
        }

        return participants.toString();
    }


    public static void populateListViewFromString(String itemsString, ListView<String> listView) {
        // Populate the ListView with names
        ObservableList<String> items = FXCollections.observableArrayList();
        String[] participantNames = itemsString.split(",");
        items.addAll(participantNames);
        listView.setItems(items);
    }

    public static void addStringToListView(String item, ListView<String> listView) {
        if(item != null){
            ObservableList<String> items = listView.getItems();
            items.add(item);
            listView.setItems(items);
        }else {
             alertMessage alert = new alertMessage();
            alert.errorMessage("No value to add in the list.");
        }

    }

    public static void removeSelectedFromListView(ListView<String> listView) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            ObservableList<String> items = listView.getItems();
            items.remove(selectedIndex);
        }else {
            alertMessage alert = new alertMessage();
            alert.errorMessage("Select item to remove from the list.");
        }
    }

    public static void applyClickEffect(Node node) {
        node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Create a ScaleTransition
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), node);
                scaleTransition.setToX(0.9);
                scaleTransition.setToY(0.9);
                scaleTransition.play();
            }
        });

        node.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Reverse the transition to give a button click effect
                ScaleTransition reverseTransition = new ScaleTransition(Duration.millis(200), node);
                reverseTransition.setToX(1);
                reverseTransition.setToY(1);
                reverseTransition.play();
            }
        });

        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Apply hover effect when mouse enters
                ScaleTransition hoverTransition = new ScaleTransition(Duration.millis(200), node);
                hoverTransition.setToX(1.1);
                hoverTransition.setToY(1.1);
                hoverTransition.play();
            }
        });

        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Reverse hover effect when mouse exits
                ScaleTransition reverseHoverTransition = new ScaleTransition(Duration.millis(200), node);
                reverseHoverTransition.setToX(1);
                reverseHoverTransition.setToY(1);
                reverseHoverTransition.play();
            }
        });
    }

    public static <T> void applyFilter(TableView<T> tableView, int columnIndex, String... filterValues) {
        // Get the column to filter
        TableColumn<T, ?> columnToFilter = tableView.getColumns().get(columnIndex);

        // Create a new filtered list
        ObservableList<T> filteredList = FXCollections.observableArrayList();

        // Apply filters based on filterValues
        for (T item : tableView.getItems()) {
            // Get the cell value as a String
            Object cellData = columnToFilter.getCellData(item);
            if (cellData != null) {
                String cellValue = cellData.toString().toLowerCase();
                for (String filter : filterValues) {
                    if (cellValue.equals(filter.toLowerCase())) {
                        filteredList.add(item);
                        break;
                    }

                }
            }
        }

        // Clear the table and set the filtered list as items
        tableView.setItems(filteredList);
    }

    public static <T> void applyIntegerFilter(TableView<T> tableView, int columnIndex, String... filterValues) {
        // Get the column to filter
        TableColumn<T, ?> columnToFilter = tableView.getColumns().get(columnIndex);

        // Create a new filtered list
        ObservableList<T> filteredList = FXCollections.observableArrayList();

        // Apply filters based on filterValues
        for (T item : tableView.getItems()) {
            // Get the cell value as a String
            Object cellData = columnToFilter.getCellData(item);
            if (cellData != null) {
                String cellValue = cellData.toString().toLowerCase();
                int integerCellValue = Integer.parseInt(cellValue);
                for (String filter : filterValues) {

                    //Integer columns
                    if (integerCellValue == 0 && filter.equalsIgnoreCase("empty")) {
                        filteredList.add(item);
                        break;
                    }
                    if ((integerCellValue > 0 && integerCellValue < 10) && filter.equalsIgnoreCase("warning")) {
                        filteredList.add(item);
                        break;
                    }
                    if (filter.equalsIgnoreCase("")) {
                        filteredList.add(item);
                        break;
                    }

                }
            }
        }

        // Clear the table and set the filtered list as items
        tableView.setItems(filteredList);
    }

    public static String[] getCheckedLabels(CheckBox[] checkboxes) {
        List<String> checkedLabels = new ArrayList<>();
        int isOneBoxChecked = 0;
        for (CheckBox checkbox : checkboxes) {
            if (checkbox.isSelected()) {
                checkedLabels.add(checkbox.getText());
                isOneBoxChecked++;
            }
        }

        if (isOneBoxChecked == 0) {
            return new String[0];
        } else {
            return checkedLabels.toArray(new String[0]);
        }
    }


}
