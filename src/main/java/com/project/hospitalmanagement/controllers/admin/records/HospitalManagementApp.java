package com.project.hospitalmanagement.controllers.admin.records;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.util.Arrays;
import java.util.List;

public class HospitalManagementApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hospital Management App");

        // Create Births & Deaths tab
        Tab birthsDeathsTab = new Tab("Births & Deaths");

        // Create Notifications area
        TextArea notificationsArea = new TextArea();
        notificationsArea.setPromptText("Notifications will appear here");
        notificationsArea.setEditable(false);

        // Create List of Births & Deaths
        ListView<String> birthsList = new ListView<>();
        birthsList.getItems().addAll("John Doe - Birth", "Jane Doe - Death");
        birthsList.setPrefHeight(200);

        // Create Refresh Button
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> {
            // Simulate fetching new notifications and updates
            notificationsArea.setText("New birth recorded.\nNew death recorded.");
            birthsList.getItems().addAll("New Birth - Baby Smith", "New Death - Mary Johnson");
            showNotification("New Events", "New birth and death recorded");
            updateStatistics();
        });

        // Create Statistics and Analytics area with Bar Chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> birthsDeathsChart = new BarChart<>(xAxis, yAxis);
        birthsDeathsChart.setTitle("Monthly Births & Deaths");
        xAxis.setLabel("Month");
        yAxis.setLabel("Number of Events");

        // Add Sample Data to Bar Chart
        XYChart.Series<String, Number> birthsSeries = new XYChart.Series<>();
        birthsSeries.setName("Births");
        birthsSeries.getData().add(new XYChart.Data<>("Jan", 10));
        birthsSeries.getData().add(new XYChart.Data<>("Feb", 8));
        XYChart.Series<String, Number> deathsSeries = new XYChart.Series<>();
        deathsSeries.setName("Deaths");
        deathsSeries.getData().add(new XYChart.Data<>("Jan", 5));
        deathsSeries.getData().add(new XYChart.Data<>("Feb", 7));
        birthsDeathsChart.getData().addAll(birthsSeries, deathsSeries);


        // Create Collaboration and Communication Tools area with TextArea and Button
        TextArea chatArea = new TextArea();
        chatArea.setPromptText("Type your message here...");
        chatArea.setPrefHeight(100);
        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            // Simulate sending a message
            String message = chatArea.getText();
            notificationsArea.appendText("\nSent: " + message);
            chatArea.clear();
        });

        // Create Main Layout
        VBox mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(notificationsArea, birthsList, refreshButton, birthsDeathsChart, new HBox(chatArea, sendButton));

        // Set Content for Births & Deaths Tab
        birthsDeathsTab.setContent(mainLayout);

        // Create Tab Pane
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(birthsDeathsTab);

        // Create Scene
        Scene scene = new Scene(tabPane, 800, 600);

        // Set Scene and Show Stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showInformation();
    }

    private void updateStatistics() {
        // Simulate updating statistics and analytics
        // In a real-world application, fetch data from database or external system
        // Update Bar Chart with new data
    }

    public static void main(String[] args) {
        launch(args);
    }
}
