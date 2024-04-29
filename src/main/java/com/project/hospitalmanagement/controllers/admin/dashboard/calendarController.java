package com.project.hospitalmanagement.controllers.admin.dashboard;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.geometry.HPos;
import javafx.geometry.VPos;


import java.net.URL;
import java.util.ResourceBundle;

public class calendarController implements Initializable {
    public GridPane Calendar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add Hour labels
        for (int i = 6; i <= 22; i++) {
            Label hourLabel = new Label(i + ":00");
            hourLabel.setAlignment(Pos.CENTER);
            GridPane.setHalignment(hourLabel, HPos.CENTER);
            GridPane.setValignment(hourLabel, VPos.TOP);
            Calendar.add(hourLabel, 0, (i - 5) * 2 - 1);
        }

        // Add day labels
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setAlignment(Pos.CENTER);
            GridPane.setHalignment(dayLabel, HPos.CENTER);
            GridPane.setValignment(dayLabel, VPos.CENTER);
            Calendar.add(dayLabel, i + 1, 0);
        }

        addEvent("Monday", 6, 0, 7, 30); // Adds an event on Monday from 8:00 to 10:30

    }

    // Function to add an event to the calendar
    public void addEvent(String day, int startHour, int startMinute, int endHour, int endMinute) {
        // Calculate column and row indices based on the day and time
        int dayIndex = getDayIndex(day);
        int startRowIndex = (startHour - 6) * 2 + (startMinute >= 30 ? 2 : 1);
        int endRowIndex = (endHour - 6) * 2 + (endMinute >= 30 ? 2 : 1);

        AnchorPane test = new AnchorPane();
        test.setBackground(Background.fill(Color.GREEN));

        // Add the rectangle to the calendar grid at the appropriate position
        Calendar.add(test, dayIndex, startRowIndex, 1, endRowIndex - startRowIndex);
    }

    // Helper function to get the index of the day label
    private int getDayIndex(String day) {
        switch (day.toLowerCase()) {
            case "monday":
                return 1;
            case "tuesday":
                return 2;
            case "wednesday":
                return 3;
            case "thursday":
                return 4;
            case "friday":
                return 5;
            case "saturday":
                return 6;
            case "sunday":
                return 7;
            default:
                return -1; // Invalid day
        }
    }

}
