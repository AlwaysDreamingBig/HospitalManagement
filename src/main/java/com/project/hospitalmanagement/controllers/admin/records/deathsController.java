package com.project.hospitalmanagement.controllers.admin.records;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.deathModel;
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

public class deathsController implements Initializable{

    public TextField search_bar;
    public TableView<deathModel> deaths_tableView;
    public TableColumn<deathModel, ImageView> DeathPicture;
    public TableColumn<deathModel, Integer> DeathID;
    public TableColumn<deathModel, String> DeathName;
    public TableColumn<deathModel, String> DeathGender;
    public TableColumn<deathModel, Date> DeathDate;
    public TableColumn<deathModel, String> DeathInWatch;
    public TableColumn<deathModel, String> DeathMother;
    public TableColumn<deathModel, String> DeathFather;
    public TableColumn<deathModel, Integer> DeathMobile;
    public TableColumn<deathModel, String> DeathNature;

    public TableColumn<deathModel, Void> Action;

    ObservableList<deathModel> deathModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String deathsViewQuery = "SELECT `DeathPicture`, `DeathID`, `DeathName`, `DeathGender`, `DeathDate`, `DeathInWatch`, `DeathMother`, `DeathFather`, `DeathMobile`, `DeathNature` FROM `deaths`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(deathsViewQuery);

            while(queryOutput.next()){

                Image profilePicture;
                Blob queryDoctorPicture = queryOutput.getBlob("DeathPicture");

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


                Integer queryDeathID = queryOutput.getInt("DeathID");
                String queryDeathName = queryOutput.getString("DeathName");
                String queryDeathGender = queryOutput.getString("DeathGender");
                java.sql.Date queryDeathDate = queryOutput.getDate("DeathDate");
                String queryDeathInWatch = queryOutput.getString("DeathInWatch");
                String queryDeathMother = queryOutput.getString("DeathMother");
                String queryDeathFather = queryOutput.getString("DeathFather");
                Integer queryDeathMobile= queryOutput.getInt("DeathMobile");
                String queryDeathNature = queryOutput.getString("DeathNature");

                //Populate the observableList
                deathModelObservableList.add(new deathModel(imageView, queryDeathID, queryDeathName, queryDeathGender, queryDeathDate, queryDeathInWatch, queryDeathMother, queryDeathFather, queryDeathMobile, queryDeathNature));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            DeathPicture.setCellValueFactory(new PropertyValueFactory<>("DeathPicture"));
            DeathID.setCellValueFactory(new PropertyValueFactory<>("DeathID"));
            DeathName.setCellValueFactory(new PropertyValueFactory<>("DeathName"));
            DeathGender.setCellValueFactory(new PropertyValueFactory<>("DeathGender"));
            DeathDate.setCellValueFactory(new PropertyValueFactory<>("DeathDate"));
            DeathInWatch.setCellValueFactory(new PropertyValueFactory<>("DeathInWatch"));
            DeathMother.setCellValueFactory(new PropertyValueFactory<>("DeathMother"));
            DeathFather.setCellValueFactory(new PropertyValueFactory<>("DeathFather"));
            DeathMobile.setCellValueFactory(new PropertyValueFactory<>("DeathMobile"));
            DeathNature.setCellValueFactory(new PropertyValueFactory<>("DeathNature"));
            Action.setCellFactory(param -> new ActionButtonTableCell<>());

            deaths_tableView.setItems(deathModelObservableList);

            //Initial filtered list
            FilteredList<deathModel> filteredData = new FilteredList<>(deathModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(death -> {

                    // Check if the search input is empty or blank
                    if (newValue == null || newValue.isBlank()) {
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    // Check death name
                    if (death.getDeathName() != null
                            && death.getDeathName().toLowerCase().contains(searchKeyword)) {
                        return true;    // Means a match was found in Death Name column
                    }

                    // Check death gender
                    if (death.getDeathGender() != null
                            && death.getDeathGender().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }

                    // Check death mother
                    if (death.getDeathMother() != null
                            && death.getDeathMother().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }

                    // Check death date
                    if (death.getDeathDate() != null
                            && death.getDeathDate().toString().contains(searchKeyword)) {
                        return true;
                    }

                    // Check death in watch
                    if (death.getDeathInWatch() != null
                            && death.getDeathInWatch().toLowerCase().contains(searchKeyword)) {
                        return true;
                    }

                    // No match found
                    return false;
                });
            });

            SortedList<deathModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(deaths_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            deaths_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(deathsController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

}
