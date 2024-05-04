package com.project.hospitalmanagement.controllers.general.certifications;

import com.project.hospitalmanagement.controllers.admin.doctors.doctorsController;
import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.certificationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class certificationsListController implements Initializable {
    public GridPane certifications_pane;

    ObservableList<certificationModel> listData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuDisplayCertificationsList();
    }

    public void menuDisplayCertificationsList() {

        listData.clear();

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String certificationsListQuery = "SELECT `CertificationID`, `CertificationName`, `CertificationOrganization`, `CertificationValidityPeriod`, `CertificationDescription` FROM `certifications`";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(certificationsListQuery);

            certificationModel certification;



            while(queryOutput.next()) {

                Image certificationImage;
                //Blob queryDoctorPicture = queryOutput.getBlob("CertificationPicture");

                Random random = new Random();
                int nombreAleatoire = random.nextInt(10) + 1;
                String imagePath = "/Images/certif" + nombreAleatoire + ".jpg";

                InputStream inputStream = getClass().getResourceAsStream(imagePath);
                if (inputStream != null) {
                    System.out.println("Image found");
                } else {
                    System.out.println("Image not found for index " + nombreAleatoire);
                }
                //assert inputStream != null;
                assert inputStream != null;
                certificationImage = new Image(inputStream);

                ImageView imageView = new ImageView(certificationImage);

                // Create a DropShadow effect
                DropShadow dropShadow = new DropShadow();
                dropShadow.setRadius(5);
                dropShadow.setColor(Color.BLACK);
                imageView.setEffect(dropShadow);

                certification = new certificationModel(imageView,
                        queryOutput.getInt("CertificationID"),
                        queryOutput.getString("CertificationName"),
                        queryOutput.getString("CertificationOrganization"),
                        queryOutput.getInt("CertificationValidityPeriod"),
                        queryOutput.getString("CertificationDescription"));

                listData.add(certification);
            }

        } catch (SQLException e) {
            Logger.getLogger(doctorsController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }finally {
            try {
                // Close the database connection
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        int row = 0;
        int column = 0;

        certifications_pane.getChildren().clear();
        certifications_pane.getRowConstraints().clear();
        certifications_pane.getColumnConstraints().clear();

        for (certificationModel listDatum : listData) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/FXML/General/Certification/Certification.fxml"));
                AnchorPane pane = load.load();
                certificationController certtif = load.getController();
                certtif.createCertification(listDatum);


                if (column == 2) {
                    column = 0;
                    row += 1;
                }

                certifications_pane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
