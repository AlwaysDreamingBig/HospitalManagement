package com.project.hospitalmanagement.controllers.admin.records;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.birthModel;
import com.project.hospitalmanagement.controllers.utilities.ActionButtonTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class birthsController implements Initializable{

    public TextField search_bar;
    public TableView<birthModel> births_tableView;
    public TableColumn<birthModel, ImageView> BirthPicture;
    public TableColumn<birthModel, Integer> BirthID;
    public TableColumn<birthModel, String> BirthName;
    public TableColumn<birthModel, String> BirthGender;
    public TableColumn<birthModel, Date> BirthDate;
    public TableColumn<birthModel, String> BirthBloodGp;
    public TableColumn<birthModel, String> BirthMother;
    public TableColumn<birthModel, String> BirthFather;
    public TableColumn<birthModel, Integer> BirthMobile;
    public TableColumn<birthModel, String> BirthAddress;

    public TableColumn<birthModel, Void> Action;

    ObservableList<birthModel> birthModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String birthsViewQuery = "SELECT `BirthPicture`, `BirthID`, `BirthName`, `BirthGender`, `BirthDate`, `BirthBloodGp`, `BirthMother`, `BirthFather`, `BirthMobile`, `BirthAddress` FROM `births`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(birthsViewQuery);

            while(queryOutput.next()){

                Image profilePicture;
                Blob queryDoctorPicture = queryOutput.getBlob("BirthPicture");

                if (queryDoctorPicture == null) {
                    InputStream inputStream = getClass().getResourceAsStream("/Images/babyNoPicture.jpg");
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
                    InputStream inputStream = queryDoctorPicture.getBinaryStream();
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


                Integer queryBirthID = queryOutput.getInt("BirthID");
                String queryBirthName = queryOutput.getString("BirthName");
                String queryBirthGender = queryOutput.getString("BirthGender");
                java.sql.Date queryBirthDate = queryOutput.getDate("BirthDate");
                String queryBirthBloodGp = queryOutput.getString("BirthBloodGp");
                String queryBirthMother = queryOutput.getString("BirthMother");
                String queryBirthFather = queryOutput.getString("BirthFather");
                Integer queryBirthMobile= queryOutput.getInt("BirthMobile");
                String queryBirthAddress = queryOutput.getString("BirthAddress");

                //Populate the observableList
                birthModelObservableList.add(new birthModel(imageView, queryBirthID, queryBirthName, queryBirthGender, queryBirthDate, queryBirthBloodGp, queryBirthMother, queryBirthFather, queryBirthMobile, queryBirthAddress));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            BirthPicture.setCellValueFactory(new PropertyValueFactory<>("BirthPicture"));
            BirthID.setCellValueFactory(new PropertyValueFactory<>("BirthID"));
            BirthName.setCellValueFactory(new PropertyValueFactory<>("BirthName"));
            BirthGender.setCellValueFactory(new PropertyValueFactory<>("BirthGender"));
            BirthDate.setCellValueFactory(new PropertyValueFactory<>("BirthDate"));
            BirthBloodGp.setCellValueFactory(new PropertyValueFactory<>("BirthBloodGp"));
            BirthMother.setCellValueFactory(new PropertyValueFactory<>("BirthMother"));
            BirthFather.setCellValueFactory(new PropertyValueFactory<>("BirthFather"));
            BirthMobile.setCellValueFactory(new PropertyValueFactory<>("BirthMobile"));
            BirthAddress.setCellValueFactory(new PropertyValueFactory<>("BirthAddress"));
            Action.setCellFactory(param -> new ActionButtonTableCell<>());

            births_tableView.setItems(birthModelObservableList);

            //Initial filtered list
            FilteredList<birthModel> filteredData = new FilteredList<>(birthModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(birth -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();


                    if (birth.getBirthName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;    //Means a match was found in Birth column
                    } else if (birth.getBirthGender().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (birth.getBirthMother().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (birth.getBirthDate().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (birth.getBirthBloodGp().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;   //no match found
                });
            });

            SortedList<birthModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(births_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            births_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(birthsController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

}

