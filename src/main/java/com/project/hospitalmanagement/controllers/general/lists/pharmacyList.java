package com.project.hospitalmanagement.controllers.general.lists;

import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.staffModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.sql.*;

public class pharmacyList {


    // SQL query to retrieve names from pharmacy table
    private static final String AFFECTED_STAFF_SQL_QUERY = "SELECT `StaffName`,`StaffPicture` FROM staff WHERE Affected = 'YES'";
    private static final String ALL_STAFF_SQL_QUERY = "SELECT `StaffName`,`StaffPicture` FROM staff WHERE PharmacyStaff = 'YES'";


    private ObservableList<staffModel> pharmacyAffectedStaffList;
    private ObservableList<staffModel> pharmacyAllStaffList;

    public void retrievePharmacyAffectedStaff() {
        pharmacyAffectedStaffList = FXCollections.observableArrayList();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        try {
            // Connect to the database
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(AFFECTED_STAFF_SQL_QUERY);


            // Process the result set
            while (queryOutput.next()) {

                Image profilePicture;
                Blob queryStaffPicture = queryOutput.getBlob("StaffPicture");

                if (queryStaffPicture == null) {
                    InputStream inputStream = getClass().getResourceAsStream("/Images/staffPicture.jpg");
                    if (inputStream != null) {
                        System.out.println("Image found");
                    } else {
                        System.out.println("Image not found");
                    }
                    //assert inputStream != null;
                    assert inputStream != null;
                    profilePicture = new Image(inputStream);
                } else {
                    // Convert Blob to Image and put inside imageView
                    InputStream inputStream = queryStaffPicture.getBinaryStream();
                    profilePicture = new Image(inputStream);
                }

                ImageView imageView = new ImageView(profilePicture);
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);

                // Create a DropShadow effect
                DropShadow dropShadow = new DropShadow();
                dropShadow.setRadius(5);
                dropShadow.setColor(Color.BLACK);
                imageView.setEffect(dropShadow);

                String name = queryOutput.getString("StaffName");

                    // If pictureBlob is null, add only the name to the list
                pharmacyAffectedStaffList.add(new staffModel(name, imageView));

            }

            // Close connections
            queryOutput.close();
            statement.close();
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrievePharmacyAllStaff() {
        pharmacyAllStaffList = FXCollections.observableArrayList();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        try {
            // Connect to the database
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(ALL_STAFF_SQL_QUERY);


            // Process the result set
            while (queryOutput.next()) {

                Image profilePicture;
                Blob queryStaffPicture = queryOutput.getBlob("StaffPicture");

                if (queryStaffPicture == null) {
                    InputStream inputStream = getClass().getResourceAsStream("/Images/staffPicture.jpg");
                    if (inputStream != null) {
                        System.out.println("Image found");
                    } else {
                        System.out.println("Image not found");
                    }
                    //assert inputStream != null;
                    assert inputStream != null;
                    profilePicture = new Image(inputStream);
                } else {
                    // Convert Blob to Image and put inside imageView
                    InputStream inputStream = queryStaffPicture.getBinaryStream();
                    profilePicture = new Image(inputStream);
                }

                ImageView imageView = new ImageView(profilePicture);
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);

                // Create a DropShadow effect
                DropShadow dropShadow = new DropShadow();
                dropShadow.setRadius(5);
                dropShadow.setColor(Color.BLACK);
                imageView.setEffect(dropShadow);

                String name = queryOutput.getString("StaffName");
                pharmacyAllStaffList.add(new staffModel(name, imageView));
            }

            // Close connections
            queryOutput.close();
            statement.close();
            connection.connectDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<staffModel> getPharmacyAllStaffNames() {
        if (pharmacyAllStaffList == null) {
            retrievePharmacyAllStaff();
        }
        return pharmacyAllStaffList;
    }

    public ObservableList<staffModel> getPharmacyAffectedStaffNames() {
        if (pharmacyAffectedStaffList == null) {
            retrievePharmacyAffectedStaff();
        }
        return pharmacyAffectedStaffList;
    }
}
