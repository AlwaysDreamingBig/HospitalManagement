package com.project.hospitalmanagement.controllers.general.certifications;

import com.project.hospitalmanagement.controllers.models.certificationModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class certificationController implements Initializable {
    public ImageView certifImage;
    public Label certifTitle;
    public Label certifDomain;
    public Label certifDescription;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void createCertification(certificationModel certification){

        certifTitle.setText(certification.getCertificationName());
        certifDomain.setText(certification.getCertificationOrganization());
        certifDescription.setText(certification.getCertificationDescription());

        // Well I was lazy to add a new column in database
        certifImage.setImage(certification.getCertificationImage());


        // Create a DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5);
        dropShadow.setColor(Color.BLUE);
        certifImage.setEffect(dropShadow);
        
    }


}
