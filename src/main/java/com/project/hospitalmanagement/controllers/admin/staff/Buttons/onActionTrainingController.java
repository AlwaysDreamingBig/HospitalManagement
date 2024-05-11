package com.project.hospitalmanagement.controllers.admin.staff.Buttons;

import com.project.hospitalmanagement.controllers.alert.alertMessage;
import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.Model;
import com.project.hospitalmanagement.controllers.models.departmentModel;
import com.project.hospitalmanagement.controllers.models.trainingModel;
import com.project.hospitalmanagement.controllers.utilities.utilitiesFunction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.sql.Types.NULL;

public class onActionTrainingController implements Initializable {
    public Button save_btn;
    public Button cancel_btn;
    public Integer TrainingID;
    private boolean UpdaterEnabled = false;
    private final alertMessage alert = new alertMessage();
    public TextField participantName;
    public TextField trainingInstructor;
    public TextField trainingLocation;
    public DatePicker trainingDate;
    public TextArea trainingDesignation;
    public TextArea trainingDescription;
    public String trainingParticipants;
    public ListView<String> participantList;
    public FontAwesomeIconView removeParticipant_btn;
    public FontAwesomeIconView addParticipant_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    public void addListener(){
        save_btn.setOnMouseClicked(event -> save());
        cancel_btn.setOnMouseClicked(event -> cancel());
        removeParticipant_btn.setOnMouseClicked(event -> removeParticipant(participantList));
        addParticipant_btn.setOnMouseClicked(event -> addParticipant(participantName.getText(), participantList));

    }

    public void setFields(trainingModel training){

        if(training != null){
            TrainingID = training.getTrainingID();
            trainingDescription.setText(training.getTrainingDescription());
            trainingDesignation.setText(training.getTrainingDesignation());
            trainingInstructor.setText(training.getTrainingInstructor());
            trainingLocation.setText(training.getTrainingLocation());

            trainingParticipants = training.getTrainingParticipants();
            utilitiesFunction.populateListViewFromString(trainingParticipants, participantList);

            // Convert java.sql.Date directly to LocalDate
            LocalDate localDate = training.getTrainingDay().toLocalDate();
            trainingDate.setValue(localDate);

        }

    }

    public void save(){

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();


        if(UpdaterEnabled){ //Update Request details - edit button
            // SQL query to fetch department data
            String UpdateQuery = "UPDATE trainings " +
                    "SET TrainingParticipant = ?, TrainingDay = ?, TrainingInstructor = ?, TrainingLocation = ?, TrainingDescription = ?, TrainingDesignation = ? " +
                    "WHERE TrainingID = ?";

            try {
                PreparedStatement statement = connectDB.prepareStatement(UpdateQuery);

                trainingParticipants = utilitiesFunction.getStringFromListView(participantList);

                statement.setString(1, trainingParticipants);

                LocalDate localDate = trainingDate.getValue();
                java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                statement.setDate(2, sqlDate);

                statement.setString(3, trainingInstructor.getText());
                statement.setString(4, trainingLocation.getText());
                statement.setString(5, trainingDescription.getText());
                statement.setString(6, trainingDesignation.getText());

                statement.setInt(7, TrainingID);

                // Execute the update query
                int rowsAffected = statement.executeUpdate();

                // Check if any rows were updated
                if (rowsAffected > 0) {
                    alert.successMessage("Training updated successfully.");
                    Stage stage = (Stage) save_btn.getScene().getWindow();
                    Model.getInstance().getpageFactory().closeStage(stage);

                } else {
                    alert.errorMessage("No training found with the given Description.");

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

        }else { //Add new REQUEST to table

            // SQL query to insert new certification data
            String insertQuery = "INSERT INTO trainings (TrainingParticipant, TrainingDay, TrainingStart, TrainingEnd, TrainingInstructor, TrainingLocation, TrainingDescription, TrainingDesignation) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?))";

            try {
                PreparedStatement statement = connectDB.prepareStatement(insertQuery);

                trainingParticipants = utilitiesFunction.getStringFromListView(participantList);
                statement.setString(1, trainingParticipants);

                LocalDate localDate = trainingDate.getValue();
                if (localDate != null) {
                    String formattedDate = localDate.toString();
                    java.sql.Date sqlDate = java.sql.Date.valueOf(formattedDate);
                    statement.setDate(2, sqlDate);
                } else {
                    // Set the current date
                    LocalDate currentDate = LocalDate.now();
                    java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate.toString());
                    statement.setDate(2, sqlDate);
                }

                statement.setString(3, String.valueOf(NULL));
                statement.setString(4, String.valueOf(NULL));


                statement.setString(5, trainingInstructor.getText());
                statement.setString(6, trainingLocation.getText());
                statement.setString(7, trainingDescription.getText());
                statement.setString(8, trainingDesignation.getText());

                // Execute the insert query
                int rowsAffected = statement.executeUpdate();

                // Check if any rows were inserted
                if (rowsAffected > 0) {
                    alert.successMessage("New request added successfully.");
                    Stage stage = (Stage) save_btn.getScene().getWindow();
                    Model.getInstance().getpageFactory().closeStage(stage);
                } else {
                    alert.errorMessage("Failed to add new request.");
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
        }



    }

    public void cancel(){
        Stage stage = (Stage) save_btn.getScene().getWindow();
        Model.getInstance().getpageFactory().closeStage(stage);
    }

    public void setUpdater(boolean value){
        UpdaterEnabled = value;
    }

    public void addParticipant(String participant, ListView<String> participantList){
        utilitiesFunction.addStringToListView(participant, participantList);
    }

    public void removeParticipant(ListView<String> participantList){
        utilitiesFunction.removeSelectedFromListView( participantList);
    }


}
